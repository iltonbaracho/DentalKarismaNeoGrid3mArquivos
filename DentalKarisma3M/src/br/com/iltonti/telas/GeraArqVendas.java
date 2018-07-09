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
public class GeraArqVendas {

    public void gerarArquivo() throws SQLException {

        Connection conexao = null;
        PreparedStatement pstNfVen = null;
        PreparedStatement pstItVen = null;
        ResultSet rsNfVen = null;
        ResultSet rsItVen = null;
        // Declaração de variáveis fixas, formam inclusive cabeçalho do arquivo
        String tipoRegistro = "01";
        String identificacao = "VENDAS";
        String versao = "051";
        //String numRelatorio = "12345678901234567890";
        String cnpjEmissor = "01837045000188";
        // 01837045000188 - Dental Karisma  /  03303105000108 VR doctor
        String cnpjDestinatario = "03887830009046";
        // Faz conexão com o BD
        conexao = ModuloConexao3M.conector();
        //Instancia Classe para criar formatos de datas para gravar arquivos
        DataHoraFormatos dataHora = new DataHoraFormatos();

        try {
            String nfVen = "SELECT '02' as TiReg, '01' as TiFat, MNFe.Numero, MNFe.Identificacao_Documento as Serie"
                    + ", Replace(Replace(Replace(MV.Tipo_operacao, 'VND', '01'), 'Dev', '02'), 'Can', '03') as TipoNF"
                    + ", Replace(Replace(Replace(Convert(VarChar(16),MNFe.[Data_Emissao],120),' ',''),'-',''),':',''), MP1.[Codigo_Vendedor], MNFe.[Cli_For_Codigo], F.UF, F.Cep, C.Estado, C.CEP"
                    + ", 'CIF' as TipoFrete, '07' as Dias"
                    + " FROM View_Movimento_Prod_serv as MP1 inner join [View_Movimento_NFe_Relatorio] as MNFe on MP1.ordem_movimento = MNFe.ordem_movimento"
                    + " inner join movimento as MV on MV.ordem = MP1.Ordem_Movimento inner join Filiais as F on MV.Ordem_Filial = F.Ordem"
                    + " inner join [View_Cli_For_Movimento] as C on MV.[Ordem_Cli_For] = C.Ordem"
                    + " where codigo_fabricante = '156' and MV.Sequencia = MNFe.Sequencia and F.codigo = '1'";

            // Objeto de conversação Statement  
            pstNfVen = conexao.prepareStatement(nfVen);
            //Tabela temporária ResultSet    
            rsNfVen = pstNfVen.executeQuery();
            // FileWriter para gerar arquivo no caminho especificado 
            FileWriter arqVendas = new FileWriter("C:\\Shop9\\Arq3M\\" + identificacao
                    + "_" + cnpjEmissor + "_" + dataHora.dataHoraGravar + "01.txt");
            // PrintWriter pra escrever no arquivo (em texto!)  
            PrintWriter gravaArquivo = new PrintWriter(arqVendas);
            //grava o cabeçalho do arquivo
            gravaArquivo.println(tipoRegistro + "|" + identificacao + "|" + versao
                    + "|" + dataHora.dataHoraGravarNumRelatorio + "|" + dataHora.dataHoraGravar
                    + "|" + dataHora.anoMesDiadataHoraGravar + "|" + dataHora.anoMesDiadataHoraGravar
                    + "|" + cnpjEmissor + "|" + cnpjDestinatario);

            while (rsNfVen.next()) {  //Lê e escreve...  
                String tipoRegistoVenda = rsNfVen.getString(1);
                String tipoFatVenda = rsNfVen.getString(2);
                String numNFVenda = rsNfVen.getString(3);
                String serieVenda = rsNfVen.getString(4);
                String tipoNFVenda = rsNfVen.getString(5);
                String dataEmissaoNFVenda = rsNfVen.getString(6);
                String codVendedorVenda = rsNfVen.getString(7);
                String codClienteVenda = rsNfVen.getString(8);
                String ufEmissorVenda = rsNfVen.getString(9);
                String cepEmissorVenda = rsNfVen.getString(10);
                String ufDestinatarioVenda = rsNfVen.getString(11);
                String cepDestinatarioVenda = rsNfVen.getString(12);
                String tipoFreteVenda = rsNfVen.getString(13);
                String diasPagamentoVenda = rsNfVen.getString(14);
                gravaArquivo.println(tipoRegistoVenda + "|" + tipoFatVenda + "|"
                        + numNFVenda + "|" + serieVenda + "|" + tipoNFVenda + "|"
                        + dataEmissaoNFVenda + "|" + codVendedorVenda + "|" + codClienteVenda + "|"
                        + ufEmissorVenda + "|" + cepEmissorVenda + "|" + ufDestinatarioVenda
                        + "|" + cepDestinatarioVenda + "|" + tipoFreteVenda + "|" + diasPagamentoVenda);

                String itVen = "SELECT '03' as TiReg, MNFe.Numero, MNFe.Identificacao_Documento as Serie"
                        + ", Replace(Replace(Replace(MV.Tipo_operacao, 'VND', '01'), 'Dev', '02'), 'Can', '03') as TipoNF , MP1.[Codigo]"
                        + ",Replace(MP1.[Quantidade], ',','.'), Replace(MP1.[Preco_Unitario], ',','.'),'N' as Boni"
                        + ",Replace(MP1.[Preco_Total_Sem_Desconto], ',','.'),Replace(MP1.[Preco_Total_Com_Desconto], ',','.')"
                        + ",Replace(MP1.[IPI_Valor], ',','.'), Replace(MP1.[PIS_Normal_Valor], ',','.'), Replace(MP1.[ICMS_Subst_Valor], ',','.')"
                        + ",Replace(MP1.[ICMS_Normal_Valor], ',','.'), Replace(MP1.[Desconto_Valor], ',','.')"
                        + " FROM View_Movimento_Prod_serv as MP1 inner join [View_Movimento_NFe_Relatorio] as MNFe on MP1.ordem_movimento = MNFe.ordem_movimento"
                        + " inner join movimento as MV on MV.ordem = MP1.Ordem_Movimento inner join Filiais as F on MV.Ordem_Filial = F.Ordem"
                        + " inner join [View_Cli_For_Movimento] as C on MV.[Ordem_Cli_For] = C.Ordem "
                        + " where codigo_fabricante = '156' and MV.Sequencia = MNFe.Sequencia "
                        + " and MNFe.Numero = '"
                        + numNFVenda
                        + "' and F.codigo = '1' and F.codigo = '1'";

                pstItVen = conexao.prepareStatement(itVen);
                rsItVen = pstItVen.executeQuery();

                while (rsItVen.next()) {  //Lê e escreve...  
                    String tipoRegistoItens = rsItVen.getString(1);
                    String numNFItens = rsItVen.getString(2);
                    String serieItens = rsItVen.getString(3);
                    String tipoNFItens = rsItVen.getString(4);
                    String codItens = rsItVen.getString(5);
                    String qtdeVendItens = rsItVen.getString(6);
                    String precoUnItens = rsItVen.getString(7);
                    String boniItens = rsItVen.getString(8);
                    String valorTotBruItens = rsItVen.getString(9);
                    String valorTotLiqItens = rsItVen.getString(10);
                    String valorIpiItens = rsItVen.getString(11);
                    String valorPisItens = rsItVen.getString(12);
                    String valorSubstItens = rsItVen.getString(13);
                    String valorIcmsItens = rsItVen.getString(14);
                    String valorDescontosItens = rsItVen.getString(15);
                    gravaArquivo.println(tipoRegistoItens + "|" + numNFItens + "|"
                            + serieItens + "|" + tipoNFItens + "|" + codItens + "|"
                            + qtdeVendItens + "|" + precoUnItens + "|" + boniItens + "|"
                            + valorTotBruItens + "|" + valorTotLiqItens + "|" + valorIpiItens
                            + "|" + valorPisItens + "|" + valorSubstItens + "|" + valorIcmsItens + "|" + valorDescontosItens);
                }
            }
            // Fecha fluxos...  
            rsNfVen.close();
            rsItVen.close();
            pstNfVen.close();
            pstItVen.close();
            conexao.close();
            gravaArquivo.close();
            arqVendas.close();
            // Trata possíveis exceções!  
        } catch (SQLException | java.io.IOException ex) {
            Logger.getLogger(GeraArqVendas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
