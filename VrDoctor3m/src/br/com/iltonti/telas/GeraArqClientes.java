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
        String cnpjEmissor = "03303105000108";
        // 03303105000108 - Dental Karisma  /  03303105000108 VR doctor
        String cnpjDestinatario = "03887830009046";
        // Faz conexão com o BD
        conexao = ModuloConexao3M.conector();
        //Instancia Classe para criar formatos de datas para gravar arquivos
        DataHoraFormatos dataHora = new DataHoraFormatos();
        String sql1 = "select distinct c.fisica_juridica, c.CEP, c.Estado,"
                + "Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace( Replace(Replace(Replace(Replace(SubString"
                + "(c.Cidade,1,50),'é','e'),'á','a'),'ã','a'),'ç','c'),'#',''),'(', ''),')', ' '),'ó', 'o'),':', ' '),'.', ' '),'í', 'i'),',', ' '),"                
                + "Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace( Replace(Replace(Replace(Replace(SubString"
                + "(c.Endereco,1,50),'é','e'),'á','a'),'ã','a'),'ç','c'),'#',''),'(', ''),')', ' '),'ó', 'o'),':', ' '),'.', ' '),'í', 'i'),',', ' '),"
                + "Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(SubString"
                + "(c.Bairro,1,50),'é','e'),'á','a'),'ã','a'),'ç','c'),'#',''),'(', ''),')', ' '),'ó', 'o'),':', ' '),'.', ' '),'í', 'i'),"
                + "Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(SubString"
                + "(c.Nome,1,50),'é','e'),'á','a'),'ã','a'),'ç','c'),'#',''),'(', ''),')', ' '),'ó', 'o'),':', ' '),'.', ' '),'í', 'i')"                
                + ", c.fax,"
                + "Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(SubString"
                + "(c.Nome,1,50),'é','e'),'á','a'),'ã','a'),'ç','c'),'#',''),'(', ''),')', ' '),'ó', 'o'),':', ' '),'.', ' '),'í', 'i')"
                + " , c.cnpj_sem_literais, c.cpf_sem_literais  from cli_for as c"
                + " inner join Movimento as mv on c.ordem = mv.ordem_cli_for"
                + " inner join Movimento_Prod_serv as mp on mv.ordem = mp.Ordem_Movimento "
                + " inner join prod_serv as p on mp.Ordem_Prod_Serv = p.ordem"
                + " inner join Filiais as F on MV.Ordem_Filial = F.Ordem "
                + " where p.ordem_fabricante = '98' and F.codigo = '2' and c.cep <> '' and c.cep <> '0'"
                + " and mp.data_efetivacao_estoque between DATEADD(DAY, -1 , GETDATE()) AND getdate() "
                + " and c.codigo > 0 and  (c.cnpj_sem_literais <> '' or c.cpf_sem_literais <> '') and (c.cnpj_sem_literais <> '0' or c.cpf_sem_literais <> '0')"
                + "and cpf_sem_literais not in ('0') order by 7";
        //DATEADD(DAY, -90 , GETDATE()) - Day(DATEADD(DAY, -90 , GETDATE())) +1 AND eomonth(getdate(), -1) 
        try {
            // Objeto de conversação Statement  
            pst = conexao.prepareStatement(sql1);
            //Tabela temporária ResultSet    
            rs = pst.executeQuery();
            // FileWriter para gerar arquivo no caminho especificado 
            FileWriter arqClientes = new FileWriter("C:\\NeoGridClient\\documents\\out\\" + identificacao
                    + "_" + cnpjEmissor + "_" + dataHora.dataHoraGravar + "01.txt");
            // PrintWriter pra escrever no arquivo (em texto!)  
            PrintWriter gravaArquivo = new PrintWriter(arqClientes);
            //grava o cabeçalho do arquivo
            gravaArquivo.println(tipoRegistro + "|" + identificacao + "|" + versao
                    + "|" + dataHora.dataHoraGravarNumRelatorio + "|" + dataHora.dataHoraGravar + "|" + cnpjEmissor + "|" + cnpjDestinatario);

            while (rs.next()) {  //Lê e escreve...  
                String tipoRegistoCliente = "02";
                String fisicaJuridica = rs.getString(1);
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
                String codigoCliente;
                //Condição para saber se é pessoa Fisica ou juridica, usar CPF ou CNPJ
                if ("F".equals(fisicaJuridica) ) {
                    codigoCliente = rs.getString(11);
                  //  result = "verd " + codigoCliente;
                } else {
                    codigoCliente = rs.getString(10);
                    //result = "fals" + codigoCliente;
                }
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
