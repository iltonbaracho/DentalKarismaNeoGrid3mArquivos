/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.iltonti.dal;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Ilton
 */
public class ModuloConexao3M {
    // metodo responsavel por estabelecer a conexao com o banco
    public static Connection conector(){
        java.sql.Connection conexao = null;
        // a linha abaixo chama o driver
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//        System.out.println("Ponto 1 Mod Con");
        // informações do banco de dados
        String url = "jdbc:sqlserver://localhost;database=S9_Real;";
        String user = "sa";
        String psswd = "Senha123";       
  //      System.out.println("Ponto 2 Mod Con");
        //estabelecendo conexão com o bd
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url,user,psswd);
            System.out.println("Conexão Efetuada com sucesso !");
            //JOptionPane.showMessageDialog(null, "Conexão Efetuada com sucesso !");
    //        System.out.println("Ponto 3 Mod Con");
            return conexao;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }  
    
}
