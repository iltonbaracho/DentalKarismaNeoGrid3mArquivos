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
public class GeraArqEstoque {

    public void gerarArquivo() {

        Connection conexao = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        // Declaração de variáveis fixas, formam inclusive cabeçalho do arquivo
        String tipoRegistro = "01";
        String identificacao = "RELEST";
        String versao = "050";
        //String numRelatorio = "12345678901234567890";
        String cnpjEmissor = "01837045000188";
        // 01837045000188 - Dental Karisma  /  03303105000108 VR doctor
        String cnpjDestinatario = "03887830009046";
        String cnpjIndustria = "03303105000108";
        // Faz conexão com o BD
        conexao = ModuloConexao3M.conector();
        //Instancia Classe para criar formatos de datas para gravar arquivos
        DataHoraFormatos dataHora = new DataHoraFormatos();

        String sql1 = "SELECT Codigo_Prod_Serv,"
                + "replace((case when Qtde_Estoque_Atual < 0 then 0 else qtde_estoque_atual end),',','.') as QtdeEstoque\n"
                + "  FROM [dbo].[View_Estoque_Atual_Filial_Prod_Serv]"
                + "  where codigo_filial =1 and Codigo_Prod_Serv <> '0'"
                + "  order by Nome_Prod_Serv";
        try {
            // Objeto de conversação Statement  
            pst = conexao.prepareStatement(sql1);
            //Tabela temporária ResultSet    
            rs = pst.executeQuery();
            // FileWriter para gerar arquivo no caminho especificado 
            FileWriter arqEstoque = new FileWriter("C:\\Shop9\\Arq3M\\" + identificacao
                    + "_" + cnpjIndustria + "_" + cnpjEmissor + "_" + dataHora.dataHoraGravar + "01.txt");
            // PrintWriter pra escrever no arquivo (em texto!)  
            PrintWriter gravaArquivo = new PrintWriter(arqEstoque);
            //grava o cabeçalho do arquivo
            gravaArquivo.println(tipoRegistro + "|" + identificacao + "|" + versao
                    + "|" + dataHora.dataHoraGravarNumRelatorio + "|" + dataHora.dataHoraGravar
                    + "|" + dataHora.anoMesDiadataHoraGravar + "|" + dataHora.anoMesDiadataHoraGravar
                    + "|" + cnpjEmissor + "|" + cnpjDestinatario);

            while (rs.next()) {  //Lê e escreve...  
                String tipoRegistoCliente = "02";
                String dataHoraEstoque = dataHora.dataHoraGravar;
                String codigoItem = rs.getString(1);
                Double quantEstoque = rs.getDouble(2);
                Double quantEstoqueTransito = rs.getDouble(2);
                gravaArquivo.println(tipoRegistoCliente + "|" + dataHoraEstoque + "|"
                        + codigoItem + "|" + quantEstoque + "|" + quantEstoqueTransito);
            }
            // Fecha fluxos...  
            rs.close();
            pst.close();
            conexao.close();
            gravaArquivo.close();
            arqEstoque.close();
            // Trata possíveis exceções!  
        } catch (SQLException | java.io.IOException ex) {
            Logger.getLogger(GeraArqEstoque.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}