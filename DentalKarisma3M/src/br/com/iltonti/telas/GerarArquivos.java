/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.iltonti.telas;

/**
 *
 * @author IltonSB
 */
public class GerarArquivos {
    public static void main(String[] args) {
        GeraArqVendedores geraVendedores = new GeraArqVendedores();
        geraVendedores.gerarArquivo();
        GeraArqClientes geraClientes = new GeraArqClientes();
        geraClientes.gerarArquivo();
        GeraArqEstoque geraEstoques = new GeraArqEstoque();
        geraEstoques.gerarArquivo();
        GeraArqProdutos geraProdutos = new GeraArqProdutos();
        geraProdutos.gerarArquivo();
    }
}