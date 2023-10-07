/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author edson
 */
import model.Produto;
import model.Unidade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    private final Connection conexao;

    public ProdutoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // Método para adicionar um produto ao banco de dados
    public Produto adicionarProduto(Produto produto) throws SQLException {
        String sql = "INSERT INTO produtos ( nome, unidade_id) VALUES (?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getUnidade().getCodigo());
            stmt.executeUpdate();

            // Recupere a chave gerada pelo banco de dados
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    produto.setCodigo(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Falha ao obter o ID gerado !");
                }
            }

        }
        return produto;
    }

    // Método para listar todos os produtos do banco de dados
    public List<Produto> listarProdutos() throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT p.*, u.nome AS nome_unidade, u.sigla AS sigla_unidade FROM produtos p "
                + "INNER JOIN unidades u ON p.unidade_id = u.codigo";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                produtos.add(new Produto(rs.getInt("codigo"), rs.getString("nome"),
                        new Unidade(rs.getInt("unidade_id"), rs.getString("nome_unidade"), rs.getString("sigla_unidade"))));
            }
        }
        return produtos;
    }

    // Método para encontrar um produto pelo seu código
    public Produto encontrarProdutoPorCodigo(int codigo) throws SQLException {
        String sql = "SELECT p.*, u.nome AS nome_unidade, u.sigla AS sigla_unidade FROM produtos p "
                + "INNER JOIN unidades u ON p.unidade_id = u.codigo "
                + "WHERE p.codigo = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Produto produto = new Produto();
                    produto.setCodigo(rs.getInt("codigo"));
                    produto.setNome(rs.getString("nome"));

                    Unidade unidade = new Unidade();
                    unidade.setCodigo(rs.getInt("unidade_id"));
                    unidade.setNome(rs.getString("nome_unidade"));
                    unidade.setSimbolo(rs.getString("sigla_unidade"));

                    produto.setUnidade(unidade);

                    return produto;
                } else {
                    // Retorna null se o produto não for encontrado
                    return null;
                }
            }
        }
    }

    // Método para encontrar produtos pelo nome
    public List<Produto> encontrarProdutosPorNome(String nome) throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT p.*, u.nome AS nome_unidade, u.sigla AS sigla_unidade FROM produtos p "
                + "INNER JOIN unidades u ON p.unidade_id = u.codigo "
                + "WHERE p.nome LIKE ?"; // Usamos LIKE para fazer a pesquisa por parte do nome

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            // Defina o valor do parâmetro nome usando um padrão com "%" para corresponder a qualquer parte do nome
            stmt.setString(1, "%" + nome + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                produtos.add(new Produto(rs.getInt("codigo"), rs.getString("nome"),
                        new Unidade(rs.getInt("unidade_id"), rs.getString("nome_unidade"), rs.getString("sigla_unidade"))));
            }
        }
        return produtos;
    }

    // Método para atualizar um produto no banco de dados
    public void atualizarProduto(Produto produto) throws SQLException {
        String sql = "UPDATE produtos SET nome = ?, unidade_id = ? WHERE codigo = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getUnidade().getCodigo());
            stmt.setInt(3, produto.getCodigo());
            stmt.executeUpdate();
        }
    }

    // Método para deletar um produto do banco de dados pelo seu código
    public void deletarProdutoPorCodigo(int codigo) throws SQLException {
        String sql = "DELETE FROM produtos WHERE codigo = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            stmt.executeUpdate();
        }
    }
}
