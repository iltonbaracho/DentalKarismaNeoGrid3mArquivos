/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Relatorios;

import br.com.iltonti.dal.ModuloConexao3M;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author ilton
 */
public class ReportUtils {
    
    public void gerarRelFatu() {
        

        Connection conexao = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        conexao = ModuloConexao3M.conector();
        
        //imrpimindo relatorio com o Jasper
        try {
            JasperPrint print = JasperFillManager.fillReport("C:\\Shop9\\Arq3m\\relFatura.jasper", null, conexao);
        // exibe o relatorio
            JasperViewer.viewReport(print, false);
        // EXPORTA o relatorio para PDF
            JasperExportManager.exportReportToPdfFile(print, "C:\\Shop9\\Arq3m\\RelatorioFatura.pdf");           
            
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        }
        
    }
