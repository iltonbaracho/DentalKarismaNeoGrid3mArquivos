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
public class GeraArqClientes {

    public void gerarArquivo() {

        Connection conexao = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        // Declaração de variáveis fixas, formam inclusive cabeçalho do arquivo
        String tipoRegistro = "01";
        String identificacao = "RELCLI";
        String versao = "050";
        //String numRelatorio = "12345678901234567890";
        String cnpjEmissor = "01837045000188";
        // 01837045000188 - Dental Karisma  /  03303105000108 VR doctor
        String cnpjDestinatario = "03887830009046";
        // Faz conexão com o BD
        conexao = ModuloConexao3M.conector();
        //Instancia Classe para criar formatos de datas para gravar arquivos
        DataHoraFormatos dataHora = new DataHoraFormatos();

        String sql1 = "select codigo, CEP, Estado, Cidade, "
                + "Replace(Replace(Replace(Replace(Replace(Replace(Replace( Replace(Replace(Replace(Replace(SubString"
                + "(Endereco,1,50),'é','e'),'á','a'),'ã','a'),'ç','c'),'#',''),'(', ''),')', ' '),'ó', 'o'),':', ' '),'.', ' '),'í', 'i'),"
                + "Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(SubString"
                + "(Bairro,1,50),'é','e'),'á','a'),'ã','a'),'ç','c'),'#',''),'(', ''),')', ' '),'ó', 'o'),':', ' '),'.', ' '),'í', 'i'),"
                + "Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(SubString"
                + "(Nome,1,50),'é','e'),'á','a'),'ã','a'),'ç','c'),'#',''),'(', ''),')', ' '),'ó', 'o'),':', ' '),'.', ' '),'í', 'i'),"
                + " fax, Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(SubString"
                + "(Nome,1,50),'é','e'),'á','a'),'ã','a'),'ç','c'),'#',''),'(', ''),')', ' '),'ó', 'o'),':', ' '),'.', ' '),'í', 'i')"
                + " from cli_for where codigo > 0 and bloqueado <> 0";
        try {
            // Objeto de conversação Statement  
            pst = conexao.prepareStatement(sql1);
            //Tabela temporária ResultSet    
            rs = pst.executeQuery();
            // FileWriter para gerar arquivo no caminho especificado 
            FileWriter arqClientes = new FileWriter("C:\\Shop9\\Arq3M\\" + identificacao
                    + "_" + cnpjEmissor + "_" + dataHora.dataHoraGravar + "01.txt");
            // PrintWriter pra escrever no arquivo (em texto!)  
            PrintWriter gravaArquivo = new PrintWriter(arqClientes);
            //grava o cabeçalho do arquivo
            gravaArquivo.println(tipoRegistro + "|" + identificacao + "|" + versao
                    + "|" + dataHora.dataHoraGravarNumRelatorio + "|" + dataHora.dataHoraGravar + "|" + cnpjEmissor + "|" + cnpjDestinatario);

            while (rs.next()) {  //Lê e escreve...  
                String tipoRegistoCliente = "02";
                String codigoCliente = rs.getString(1);
                String cepCliente = rs.getString(2);
                String ufCliente = rs.getString(3);
                String cidadeCliente = rs.getString(4);
                String enderecoCliente = rs.getString(5);
                String bairroCliente = rs.getString(6);
                String nomeCliente = rs.getString(7);
                String codigoSegmentoCliente = "169";
                String frequenciaVisita = "03";
                String telefoneCliente = rs.getString(8);
                String contatoCliente = rs.getString(9);
                gravaArquivo.println(tipoRegistoCliente + "|" + codigoCliente + "|"
                        + cepCliente + "|" + ufCliente + "|" + cidadeCliente + "|"
                        + enderecoCliente + "|" + bairroCliente + "|" + nomeCliente + "|"
                        + codigoSegmentoCliente + "|" + frequenciaVisita + "|" + telefoneCliente
                        + "|" + contatoCliente);
            }
            // Fecha fluxos...  
            rs.close();
            pst.close();
            conexao.close();
            gravaArquivo.close();
            arqClientes.close();
            // Trata possíveis exceções!  
        } catch (SQLException | java.io.IOException ex) {
            Logger.getLogger(GeraArqClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
