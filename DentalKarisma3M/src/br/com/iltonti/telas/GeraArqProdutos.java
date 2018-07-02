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
public class GeraArqProdutos {

    public void gerarArquivo() {

        Connection conexao = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        // Declaração de variáveis fixas, formam inclusive cabeçalho do arquivo
        String tipoRegistro = "01";
        String identificacao = "RELPRO";
        String versao = "051";
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
                + "replace((case when Qtde_Produtos_Atual < 0 then 0 else qtde_estoque_atual end),',','.') as QtdeProdutos\n"
                + "  FROM [dbo].[View_Produtos_Atual_Filial_Prod_Serv]"
                + "  where codigo_filial =1 and Codigo_Prod_Serv <> '0'"
                + "  order by Nome_Prod_Serv";
        try {
            // Objeto de conversação Statement  
            pst = conexao.prepareStatement(sql1);
            //Tabela temporária ResultSet    
            rs = pst.executeQuery();
            // FileWriter para gerar arquivo no caminho especificado 
            FileWriter arqProdutos = new FileWriter("C:\\Shop9\\Arq3M\\" + identificacao
                    + "_" + cnpjEmissor + "_" + dataHora.dataHoraGravar + "01.txt");
            // PrintWriter pra escrever no arquivo (em texto!)  
            PrintWriter gravaArquivo = new PrintWriter(arqProdutos);
            //grava o cabeçalho do arquivo
            gravaArquivo.println(tipoRegistro + "|" + identificacao + "|" + versao
                    + "|" + dataHora.dataHoraGravarNumRelatorio + "|" + dataHora.dataHoraGravar
                    + "|" + cnpjEmissor + "|" + cnpjDestinatario);

            while (rs.next()) {  //Lê e escreve...  
                String tipoRegistoProdutos = "02";                
                String codigoItem = rs.getString(1);
                Double quantProdutos = rs.getDouble(2);
                Double quantProdutosTransito = rs.getDouble(2);
                gravaArquivo.println(tipoRegistoProdutos + "|" + cnpjEmissor + "|"
                        + codigoItem + "|" + codigoItem // Tem de alterar para código de barras 
                        + "|" + "01" // deveria ser, buscar do campo promocional, por enquanto tá fixo
                        + "|" + "3.00000" //Indica a quantidade de unidades do produto que a embalagem contém.
                        + "|" + "10.00" // Preço de tabela cadastrado para venda do código de barras da menor unidade de consumo.
                        + "|" + "Nome do Produto" // Informa a descrição do produto relacionado ao código interno de identificação.
                        + "|" + "01");  // Informa se o produto em questão está Ativo ou Inativo no distribuidor
            }
            // Fecha fluxos...  
            rs.close();
            pst.close();
            conexao.close();
            gravaArquivo.close();
            arqProdutos.close();
            // Trata possíveis exceções!  
        } catch (SQLException | java.io.IOException ex) {
            Logger.getLogger(GeraArqProdutos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
