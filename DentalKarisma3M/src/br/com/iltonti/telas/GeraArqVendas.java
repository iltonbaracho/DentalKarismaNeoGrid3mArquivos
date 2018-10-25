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
        String cnpjDestinatario = "45985371000108";
        // Faz conexão com o BD
        conexao = ModuloConexao3M.conector();
        //Instancia Classe para criar formatos de datas para gravar arquivos
        DataHoraFormatos dataHora = new DataHoraFormatos();

        try {
  String nfVen = "SELECT distinct '02' as TiReg, '01' as TiFat, mvFiscais.Numero " +
",REPLICATE('0', 3 - LEN(mvFiscais.Serie)) + RTrim(mvFiscais.Serie) as Serie " +
", Replace(Replace(Replace(MV.Tipo_operacao, 'VND', '01'), 'Dev', '02'), 'Can', '03') as TipoNF " +
", Replace(Replace(Replace(Convert(VarChar(16),mvFiscais.Data_Emissao,120),' ',''),'-',''),':',''), " +
"fun.Codigo, c.CNPJ_Sem_Literais, F.UF, F.Cep, C.Estado, C.CEP, 'CIF' as TipoFrete, '07' as Dias, c.CPF_Sem_Literais, c.fisica_juridica " +
"FROM Movimento_Prod_serv as MP1 " +
"inner join movimento as MV on MV.ordem = MP1.Ordem_Movimento " +
"inner join Filiais as F on MV.Ordem_Filial = F.Ordem " +
"inner join funcionarios as fun on fun.ordem = mp1.ordem_vendedor " +
"inner join Cli_For as C on MV.Ordem_Cli_For = C.Ordem " +
"inner join Prod_Serv as p on p.ordem = mp1.ordem_prod_serv " +
"inner join Movimento_documentos_fiscais as mvFiscais on mv.ordem = mvFiscais.ordem_movimento " +
"where F.codigo = '1' and mv.apagado <> '1' and mv.desefetivado_financeiro = '0' " +
"and mv.desefetivado_estoque = '0' and mp1.estoque_efetivado = '1' " +
"and mv.efetivado_financeiro = '1' and mp1.Estoque_Desefetivado = '0' " +
"and mp1.Data_efetivacao_estoque between DATEADD(DAY, -90 , GETDATE()) - Day(DATEADD(DAY, -90 , GETDATE())) +1 AND eomonth(getdate(), -1) " +
"and ( MV.Tipo_operacao = 'VND' or MV.Tipo_operacao = 'DEV' or MV.Tipo_operacao = 'CAN') and p.ordem_fabricante = '98' and p.inativo = '0' " +
"and (p.codigo_adicional1 <> '' or p.codigo_adicional1 <> '0') and C.CEP <> '' and C.CEP <>'0' " +
"order by mvFiscais.Numero ";

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
                String ufEmissorVenda = rsNfVen.getString(9);
                String cepEmissorVenda = rsNfVen.getString(10);
                String ufDestinatarioVenda = rsNfVen.getString(11);
                String cepDestinatarioVenda = rsNfVen.getString(12);
                String tipoFreteVenda = rsNfVen.getString(13);
                String diasPagamentoVenda = rsNfVen.getString(14);
                String fisicaJuridica = rsNfVen.getString(16);
                String codClienteVenda;
                //Condição para saber se é pessoa Fisica ou juridica, usar CPF ou CNPJ
                if ("F".equals(fisicaJuridica) ) {
                    codClienteVenda = rsNfVen.getString(15);
                  //  result = "verd " + codigoCliente;
                } else {
                    codClienteVenda = rsNfVen.getString(8);
                    //result = "fals" + codigoCliente;
                }
                gravaArquivo.println(tipoRegistoVenda + "|" + tipoFatVenda + "|"
                        + numNFVenda + "|" + serieVenda + "|" + tipoNFVenda + "|"
                        + dataEmissaoNFVenda + "|" + codVendedorVenda + "|" + codClienteVenda + "|"
                        + ufEmissorVenda + "|" + cepEmissorVenda + "|" + ufDestinatarioVenda
                        + "|" + cepDestinatarioVenda + "|" + tipoFreteVenda + "|" + diasPagamentoVenda);

                String itVen = "SELECT '03' as TiReg, mvFiscais.Numero " +
",REPLICATE('0', 3 - LEN(mvFiscais.Serie)) + RTrim(mvFiscais.Serie) as Serie " +
", Replace(Replace(Replace(MV.Tipo_operacao, 'VND', '01'), 'Dev', '02'), 'Can', '03') as TipoNF, P.Codigo " +
",format(MP1.Quantidade, '#.00000'),Replace(MP1.Preco_Unitario, ',','.') " +
",'N' as Boni, Replace(MP1.Preco_Total_Sem_Desconto, ',','.') " +
", Replace(MP1.Preco_Total_Com_Desconto, ',','.'), Replace(MP1.IPI_Valor, ',','.'), " +
"Replace(MP1.PIS_Normal_Valor, ',','.'), Replace(MP1.ICMS_Subst_Valor, ',','.') " +
", Replace(MP1.ICMS_Normal_Valor, ',','.'), Replace(MP1.Desconto_Valor, ',','.') " +
"FROM Movimento_Prod_serv as MP1 " +
"inner join movimento as MV on MV.ordem = MP1.Ordem_Movimento " +
"inner join Filiais as F on MV.Ordem_Filial = F.Ordem " +
"inner join Cli_For as C on MV.[Ordem_Cli_For] = C.Ordem " +
"inner join Prod_Serv as p on p.ordem = mp1.ordem_prod_serv " +
"inner join Movimento_documentos_fiscais as mvFiscais on mv.ordem = mvFiscais.ordem_movimento " +
"where F.codigo = '1' and mv.apagado <> '1' and mv.desefetivado_financeiro = '0' " +
" and mvFiscais.Numero = '"+numNFVenda+ 
"' and mv.desefetivado_estoque = '0' and mp1.estoque_efetivado = '1' " +
"and mv.efetivado_financeiro = '1' and mp1.Estoque_Desefetivado = '0' " +
"and mp1.Data_efetivacao_estoque between  DATEADD(DAY, -90 , GETDATE()) - Day(DATEADD(DAY, -90 , GETDATE())) +1 AND eomonth(getdate(), -1) " +
"and ( MV.Tipo_operacao = 'VND' or MV.Tipo_operacao = 'DEV' or MV.Tipo_operacao = 'CAN') and p.ordem_fabricante = '98' " +
"and (p.codigo_adicional1 <> '' or p.codigo_adicional1 <> '0') and (C.CEP <> '' or C.CEP <>'0') " +
"order by mp1.Ordem_prod_serv ";

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
         //   rsItVen.close();
            pstNfVen.close();
         //   pstItVen.close();
            conexao.close();
            gravaArquivo.close();
            arqVendas.close();
            // Trata possíveis exceções!  
        } catch (SQLException | java.io.IOException ex) {
            Logger.getLogger(GeraArqVendas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
