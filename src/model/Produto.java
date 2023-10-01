/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author edson
 */
public class Produto {

    private static int contador = 0;
    int codigo;
    String nome;

    Unidade unidade;

    public Produto() {
    }

    public Produto(String nome, Unidade unidade) {
        this.codigo = ++contador;
        this.nome = nome;
        this.unidade = unidade;
    }

    public Produto(int codigo, String nome, Unidade unidade) {
        this.codigo = codigo;
        this.nome = nome;
        this.unidade = unidade;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Produto{"
                + "codigo=" + codigo
                + ", nome='" + nome + '\''
                + ", unidade=" + unidade
                + '}';
    }
}
