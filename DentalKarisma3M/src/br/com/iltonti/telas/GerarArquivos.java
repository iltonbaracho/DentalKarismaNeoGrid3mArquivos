/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and ope

n the template in the editor.
 */
package br.com.iltonti.telas;

import Relatorios.ReportUtils;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author IltonSB
 */
public class GerarArquivos {
    public static void main(String[] args) throws SQLException, InterruptedException {
        
        // Gerar o relatorio PDF
        // ReportUtils geraRelFaturamento = new ReportUtils();
        //geraRelFaturamento.gerarRelFatu();
        
        //String mensagem1 = "Programa Arq3M em execução! Pressione OK e aguarde a mensagem de Fim !";
        //JOptionPane.showMessageDialog(null, mensagem1);
        GeraArqVendedores geraVendedores = new GeraArqVendedores();
        //JOptionPane.showMessageDialog(null, "Gerando ArqVendedores !");
        geraVendedores.gerarArquivo();
       
        GeraArqClientes geraClientes = new GeraArqClientes();
        //JOptionPane.showMessageDialog(null, "Gerando ArqClientes !");
        geraClientes.gerarArquivo();
        
        GeraArqEstoque geraEstoques = new GeraArqEstoque();
        //JOptionPane.showMessageDialog(null, "Gerando ArqEstoque !");
        geraEstoques.gerarArquivo();
       
        GeraArqProdutos geraProdutos = new GeraArqProdutos();
        //JOptionPane.showMessageDialog(null, "Gerando ArqProdutos !");
        geraProdutos.gerarArquivo();
       
        GeraArqVendas geraVendas = new GeraArqVendas();
        //JOptionPane.showMessageDialog(null, "Gerando ArqVendas !");
        geraVendas.gerarArquivo();
        //String mensagem2 = "Programa Arq3M encerrado! Fim do Programa! Pressione os OK´s.";
        //JOptionPane.showMessageDialog(null, mensagem2);
        System.out.println("Fim do Programa");
    }
}
