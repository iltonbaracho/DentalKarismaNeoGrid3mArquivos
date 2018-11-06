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
        String cnpjEmissor = "03303105000108";
        // 03303105000108 - Dental Karisma  /  03303105000108 VR doctor
        String cnpjDestinatario = "03887830009046";
        // Faz conexão com o BD
        conexao = ModuloConexao3M.conector();
        //Instancia Classe para criar formatos de datas para gravar arquivos
        DataHoraFormatos dataHora = new DataHoraFormatos();

        String sql1 = "SELECT distinct "
                + "Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(SubString"
                + "(f.Nome,1,50),'é','e'),'á','a'),'ã','a'),'ç','c'),'#',''),'(', ''),')', ' '),'ó', 'o'),':', ' '),'.', ' '),'í', 'i'), "
                + "f.Codigo FROM Funcionarios as f " +
"inner join View_Movimento_Prod_serv as MP1 on f.codigo = mp1.codigo_vendedor " +
"where f.codigo > 0 and mp1.codigo_fabricante = '156' and mp1.inativo = '0' " +
"and mp1.data_efetivacao_estoque  between DATEADD(DAY, -1 , GETDATE()) AND getdate() " +
" and mp1.estoque_desefetivado = '0' " +
" and mp1.Efetivado_Financeiro = '1' and mp1.estoque_efetivado = '1'";
        try {
            // Objeto de conversação Statement  
            pst = conexao.prepareStatement(sql1);
            //Tabela temporária ResultSet    
            rs = pst.executeQuery();
            // FileWriter para gerar arquivo no caminho especificado 
            FileWriter arqVendedores = new FileWriter("C:\\NeoGridClient\\documents\\out\\" + identificacao
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
