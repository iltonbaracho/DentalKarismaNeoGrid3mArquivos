/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.iltonti.telas;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author IltonSB
 */
public class DataHoraFormatos {
    
                //Peagando data e hora e armazenando na variavel, para gravar no arquivo
        Date dataHoraEmissao = new Date();
        SimpleDateFormat anoMesDiaDataHora = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat formataDataHora = new SimpleDateFormat("yyyyMMddHHmm");
        SimpleDateFormat formataDataHoraNumRelatorio = new SimpleDateFormat("ddMMyyyyHHmmssss");
    //    System.out.println(formataDataHora.format(dataHoraEmissao));
        String anoMesDiadataHoraGravar = (anoMesDiaDataHora.format(dataHoraEmissao));
        String dataHoraGravar = (formataDataHora.format(dataHoraEmissao));
        String dataHoraGravarNumRelatorio = (formataDataHoraNumRelatorio.format(dataHoraEmissao));
        Integer diasGera = 1;
      //  System.out.println(dataHoraGravar);
        //System.out.println(dataHoraGravarNumRelatorio);

    
    
}
