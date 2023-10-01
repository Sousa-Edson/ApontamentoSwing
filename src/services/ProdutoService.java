/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author edson
 */
import dao.ProdutoDAO;
import model.Produto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProdutoService {

    private ProdutoDAO produtoDAO;

    public ProdutoService(Connection conexao) {
        this.produtoDAO = new ProdutoDAO(conexao);
    }

    public void adicionarProduto(Produto produto) throws SQLException {
        produtoDAO.adicionarProduto(produto);
        System.out.println("*** SALVO => " + produto);

    }

    public List<Produto> listarProdutos() throws SQLException {
        return produtoDAO.listarProdutos();
    }

    public Produto encontrarProdutoPorCodigo(int codigo) throws SQLException {
        return produtoDAO.encontrarProdutoPorCodigo(codigo);
    }

    public void atualizarProduto(Produto produto) throws SQLException {
        produtoDAO.atualizarProduto(produto);
    }

    public void deletarProdutoPorCodigo(int codigo) throws SQLException {
        produtoDAO.deletarProdutoPorCodigo(codigo);
    }
}
