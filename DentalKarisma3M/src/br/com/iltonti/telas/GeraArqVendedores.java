/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.iltonti.telas;

import br.com.iltonti.dal.ModuloConexao3M;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author IltonSB
 */
public class GeraArqVendedores {

    public void gerarArquivo() {

        Connection conexao = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        // Declaração de variáveis fixas, formam inclusive cabeçalho do arquivo
        String tipoRegistro = "01";
        String identificacao = "RELVEN";
        String versao = "050";
        //String numRelatorio = "12345678901234567890";
        String cnpjEmissor = "01837045000188";
        // 01837045000188 - Dental Karisma  /  03303105000108 VR doctor
        String cnpjDestinatario = "03887830009046";
        // Faz conexão com o BD
        conexao = ModuloConexao3M.conector();
        //Instancia Classe para criar formatos de datas para gravar arquivos
        DataHoraFormatos dataHora = new DataHoraFormatos();

        String sql1 = "SELECT "
                + "Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(SubString"
                + "(Nome,1,50),'é','e'),'á','a'),'ã','a'),'ç','c'),'#',''),'(', ''),')', ' '),'ó', 'o'),':', ' '),'.', ' '),'í', 'i'),"
                + "[Codigo] FROM [dbo].[Funcionarios]"
                + "  where vendedor = 1 and filial_Todas = 1 and Inativo = 0"
                + " and codigo > 0";
        try {
            // Objeto de conversação Statement  
            pst = conexao.prepareStatement(sql1);
            //Tabela temporária ResultSet    
            rs = pst.executeQuery();
            // FileWriter para gerar arquivo no caminho especificado 
            FileWriter arqVendedores = new FileWriter("C:\\Shop9\\Arq3M\\" + identificacao
                    + "_" + cnpjEmissor + "_" + dataHora.dataHoraGravar + "01.txt");
            // PrintWriter pra escrever no arquivo (em texto!)  
            PrintWriter gravaArquivo = new PrintWriter(arqVendedores);
            //grava o cabeçalho do arquivo
            gravaArquivo.println(tipoRegistro + "|" + identificacao + "|" + versao
                    + "|" + dataHora.dataHoraGravarNumRelatorio + "|" + dataHora.dataHoraGravar + "|" + cnpjEmissor + "|" + cnpjDestinatario);

            while (rs.next()) {  //Lê e escreve...  
                String tipoRegistoVendedor = "02";
                String nomeVendedor = rs.getString(1);
                String codigoVendedor = rs.getString(2);
                String nomeSupervisor = "JoseMaria";
                String codigoSupervisor = "3";
                String nomeGerente = "Carla";
                String codigoGerente = "2";
                String statusVendedor = "A";
                gravaArquivo.println(tipoRegistoVendedor + "|" + nomeVendedor + "|"
                        + codigoVendedor + "|" + nomeSupervisor + "|" + codigoSupervisor + "|"
                        + nomeGerente + "|" + codigoGerente + "|" + statusVendedor + "|" + dataHora.dataHoraGravar);
            }
            // Fecha fluxos...  
            rs.close();
            pst.close();
            conexao.close();
            gravaArquivo.close();
            arqVendedores.close();
            // Trata possíveis exceções!  
        } catch (SQLException | java.io.IOException ex) {
            Logger.getLogger(GeraArqVendedores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
