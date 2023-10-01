/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author edson
 */
import enums.TipoMovimento;
import model.Movimentacao;
import model.Produto;
import model.Unidade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovimentacaoDAO {

    private Connection conexao;

    public MovimentacaoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // Método para adicionar uma movimentação ao banco de dados
    public Movimentacao adicionarMovimentacao(Movimentacao movimentacao) throws SQLException {
        String sql = "INSERT INTO movimentacoes (codigo, tipo_movimento, produto_id, quantidade) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, movimentacao.getCodigo());
            stmt.setString(2, movimentacao.getTipoMovimento().toString());
            stmt.setInt(3, movimentacao.getProduto().getCodigo());
            stmt.setInt(4, movimentacao.getQuantidade());
            stmt.executeUpdate();

            // Recupere a chave gerada pelo banco de dados
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    movimentacao.setCodigo(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Falha ao obter o ID gerado !");
                }
            }

        }
        return movimentacao;
    }

    // Método para listar todas as movimentações do banco de dados
    public List<Movimentacao> listarMovimentacoes() throws SQLException {
        List<Movimentacao> movimentacoes = new ArrayList<>();
        String sql = "SELECT m.*, p.nome AS nome_produto, p.unidade_id,u.sigla ,u.nome ,p.codigo AS codigo_produto "
                + "FROM movimentacoes m "
                + "INNER JOIN produtos p ON m.produto_id = p.codigo "
                + "INNER JOIN unidades u ON p.unidade_id = u.codigo";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Movimentacao movimentacao = new Movimentacao();
                movimentacao.setCodigo(rs.getInt("codigo"));
                movimentacao.setTipoMovimento(TipoMovimento.valueOf(rs.getString("tipo_movimento")));

                Produto produto = new Produto();
                produto.setCodigo(rs.getInt("codigo_produto"));
                produto.setNome(rs.getString("nome_produto"));

                // Configurar a unidade do produto, se necessário
                Unidade unidade = new Unidade();
                unidade.setCodigo(rs.getInt("p.unidade_id"));
                unidade.setNome(rs.getString("u.nome"));
                unidade.setSimbolo(rs.getString("u.sigla"));
                produto.setUnidade(unidade);

                movimentacao.setProduto(produto);
                movimentacao.setQuantidade(rs.getInt("quantidade"));

                movimentacoes.add(movimentacao);
            }
        }

        return movimentacoes;
    }

    // Método para encontrar uma movimentação pelo seu código
    public Movimentacao encontrarMovimentacaoPorCodigo(int codigo) throws SQLException {
        String sql = "SELECT m.*, p.nome AS nome_produto, p.unidade_id, p.codigo AS codigo_produto "
                + "FROM movimentacoes m "
                + "INNER JOIN produtos p ON m.produto_id = p.codigo "
                + "WHERE m.codigo = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Movimentacao movimentacao = new Movimentacao();
                    movimentacao.setCodigo(rs.getInt("codigo"));
                    movimentacao.setTipoMovimento(TipoMovimento.valueOf(rs.getString("tipo_movimento")));

                    Produto produto = new Produto();
                    produto.setCodigo(rs.getInt("codigo_produto"));
                    produto.setNome(rs.getString("nome_produto"));
                    // Configurar a unidade do produto, se necessário
                    // produto.setUnidade(unidade);

                    movimentacao.setProduto(produto);
                    movimentacao.setQuantidade(rs.getInt("quantidade"));

                    return movimentacao;
                } else {
                    // Retorna null se a movimentação não for encontrada
                    return null;
                }
            }
        }
    }

    // Método para atualizar uma movimentação no banco de dados
    public void atualizarMovimentacao(Movimentacao movimentacao) throws SQLException {
        String sql = "UPDATE movimentacoes SET tipo_movimento = ?, produto_id = ?, quantidade = ? WHERE codigo = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, movimentacao.getTipoMovimento().toString());
            stmt.setInt(2, movimentacao.getProduto().getCodigo());
            stmt.setInt(3, movimentacao.getQuantidade());
            stmt.setInt(4, movimentacao.getCodigo());
            stmt.executeUpdate();
        }
    }

    // Método para deletar uma movimentação do banco de dados pelo seu código
    public void deletarMovimentacaoPorCodigo(int codigo) throws SQLException {
        String sql = "DELETE FROM movimentacoes WHERE codigo = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            stmt.executeUpdate();
        }
    }
}
