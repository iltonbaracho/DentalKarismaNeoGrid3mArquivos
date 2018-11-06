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
        String cnpjIndustria = "45985371000108";
        // Faz conexão com o BD
        conexao = ModuloConexao3M.conector();
        //Instancia Classe para criar formatos de datas para gravar arquivos
        DataHoraFormatos dataHora = new DataHoraFormatos();

        String sql1 = "SELECT distinct p.Codigo, p.codigo_adicional1, p.Codigo, f.codigo " +
"		, Replace(Replace(mp.Utilizou_preco_promocional ,'1','02'),'0','01') as promo " +
"		, Replace(pc.preco, ',','.') as Preco " +
"		, Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(SubString " +
"		(p.Nome,1,50),'é','e'),'á','a'),'ã','a'),'ç','c'),'#',''),'(', ''),')', ' '),'ó', 'o'),':', ' '),'.', ' '),'í', 'i'),'+',' ') as NomePro " +
"		, replace(replace(p.Inativo, '1','02'),'0','01')		" +
"  FROM Prod_Serv as p inner join Movimento_Prod_serv as mp " +
"  on p.ordem = mp.ordem_prod_serv inner join prod_serv_precos as pc on p.Ordem = pc.ordem_prod_serv " +
"  inner join Estoque_Atual as E on e.ordem_prod_serv = p.ordem  " +
"  inner join Filiais as F on e.Ordem_Filial = F.Ordem  " +
"  where pc.Ordem_Tabela_Preco = '2' and p.inativo = '0'  " +
"  and p.ordem_fabricante = '98' and F.codigo = '1' " +
"  and mp.data_efetivacao_estoque between DATEADD(DAY, -1 , GETDATE()) AND getdate() " +
"  and p.codigo_adicional1 <> '' and p.codigo_adicional1 <> '0'" +
"  order by p.codigo";
        try {
            // Objeto de conversação Statement  
            pst = conexao.prepareStatement(sql1);
            //Tabela temporária ResultSet    
            rs = pst.executeQuery();
            // FileWriter para gerar arquivo no caminho especificado 
            FileWriter arqProdutos = new FileWriter("C:\\NeoGridClient\\documents\\out\\" + identificacao
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
                String codigoProduto = rs.getString(2);
                String tipoItem = rs.getString(3);
                String precoItem = rs.getString(4);
                String descricaoItem = rs.getString(5);
                String statusItem = rs.getString(6);
                gravaArquivo.println(tipoRegistoProdutos + "|" + cnpjIndustria + "|"
                        + codigoItem + "|" + codigoProduto // Tem de alterar para código de barras 
                        + "|" + tipoItem + "|" + "1.00000" //Indica a quantidade de unidades do produto que a embalagem contém.
                        + "|" + precoItem// Preço de tabela cadastrado para venda do código de barras da menor unidade de consumo.
                        + "|" + descricaoItem // Informa a descrição do produto relacionado ao código interno de identificação.
                        + "|" + statusItem);  // Informa se o produto em questão está Ativo ou Inativo no distribuidor
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
