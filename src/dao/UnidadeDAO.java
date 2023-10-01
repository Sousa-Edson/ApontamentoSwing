/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Unidade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UnidadeDAO {
     private final Connection conexao;

    public UnidadeDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // Método para adicionar uma unidade ao banco de dados
    public Unidade adicionarUnidade(Unidade unidade) throws SQLException {
        String sql = "INSERT INTO unidades (nome, sigla) VALUES (?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, unidade.getNome());
            stmt.setString(2, unidade.getSimbolo());
            stmt.executeUpdate();

            // Recupere a chave gerada pelo banco de dados
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    unidade.setCodigo(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Falha ao obter o ID gerado !");
                }
            }
            return unidade;

        }
    }

    // Método para listar todas as unidades do banco de dados
    public List<Unidade> listarUnidades() throws SQLException {
        List<Unidade> unidades = new ArrayList<>();
        String sql = "SELECT * FROM unidades";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Unidade unidade = new Unidade();
                unidade.setCodigo(rs.getInt("codigo"));
                unidade.setNome(rs.getString("nome"));
                unidade.setSimbolo(rs.getString("sigla"));
                unidades.add(unidade);
            }
        }

        return unidades;
    }

    // Método para atualizar uma unidade no banco de dados
    public void atualizarUnidade(Unidade unidade) throws SQLException {
        String sql = "UPDATE unidades SET nome = ?, sigla = ? WHERE codigo = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, unidade.getNome());
            stmt.setString(2, unidade.getSimbolo());
            stmt.setInt(3, unidade.getCodigo());
            stmt.executeUpdate();
        }
    }

    // Método para deletar uma unidade do banco de dados
    public void deletarUnidade(int id) throws SQLException {
        String sql = "DELETE FROM unidades WHERE codigo = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Método para encontrar uma unidade do banco de dados
    public Unidade encontrarUnidadePorId(int id) throws SQLException {
        String sql = "SELECT * FROM unidades WHERE codigo = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Unidade unidade = new Unidade();
                    unidade.setCodigo(rs.getInt("codigo"));
                    unidade.setNome(rs.getString("nome"));
                    unidade.setSimbolo(rs.getString("sigla"));
                    return unidade;
                } else {
                    // Retorna null se a unidade não for encontrada
                    return null;
                }
            }
        }
    }
}
