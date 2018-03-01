/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author andre
 */
public class Formatador {

    public static String removerPontuacaoCnpj(String cnpj) {
        if (cnpj == null) {
            return null;
        }
        if (cnpj.trim().isEmpty() || cnpj.isEmpty()) {
            return null;
        }

        return cnpj.trim().replace(".", "").replace("/", "").replace("-", "");
    }

    public static String removerPontuacaoCpf(String cpf) {
        if (cpf == null) {
            return null;
        }
        if (cpf.trim().isEmpty() || cpf.isEmpty()) {
            return null;
        }

        return cpf.trim().replace(".", "").replace("-", "");
    }

    public static String removerPontuacaoTelefone(String telefone) {
        if (telefone == null) {
            return "";
        }

        if (telefone.trim().isEmpty() || telefone.isEmpty()) {
            return "";
        }

        return telefone.trim().replace("(", "").replace(")", "").replace("-", "");
    }

    public static String removerPontuacaoNumeroCpts(String numeroCtps) {
        if (numeroCtps == null) {
            return null;
        }
        if (numeroCtps.trim().isEmpty() || numeroCtps.isEmpty()) {
            return null;
        }

        return numeroCtps.trim().replace(".", "").replace("-", "");
    }

    public static String dateParaString(Date data) {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        return f.format(data);
    }

    public static Date stringParaDate(String dataString) {
        try {

            if (dataString == null || dataString.trim().isEmpty()) {
                return null;
            }

            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            Date data = formato.parse(dataString);

            return data;
        } catch (ParseException ex) {
            return null;
        }
    }

    public static String ajustaDataDMA(String data) {
        String dataFormatada = null;
        try {
            Date dataAMD = new SimpleDateFormat("yyyy-MM-dd").parse(data);
            dataFormatada = new SimpleDateFormat("dd/MM/yyyy").format(dataAMD);
        } catch (ParseException e) {
            System.err.println(e);
        }
        return (dataFormatada);
    }

    public static String ajustaDataAMD(String data) {
        String dataFormatada = null;
        try {

            if (data == null || data.trim().isEmpty()) {
                return null;
            }

            Date dataDMA = new SimpleDateFormat("dd/MM/yyyy").parse(data);
            dataFormatada = new SimpleDateFormat("yyyy-MM-dd").format(dataDMA);
        } catch (ParseException e) {
            System.err.println(e);
        }
        return (dataFormatada);
    }

    public static String nomeDiaDaSemana(Date data) {

        Calendar calendario = new GregorianCalendar();
        calendario.setTime(data);

        String nomeDiaSemana = "";
        int dia = calendario.get(calendario.DAY_OF_WEEK);

        switch (dia) {
            case Calendar.SUNDAY:
                nomeDiaSemana = "Domingo";
                break;
            case Calendar.MONDAY:
                nomeDiaSemana = "Segunda";
                break;
            case Calendar.TUESDAY:
                nomeDiaSemana = "Terça";
                break;
            case Calendar.WEDNESDAY:
                nomeDiaSemana = "Quarta";
                break;
            case Calendar.THURSDAY:
                nomeDiaSemana = "Quinta";
                break;
            case Calendar.FRIDAY:
                nomeDiaSemana = "Sexta";
                break;
            case Calendar.SATURDAY:
                nomeDiaSemana = "sábado";
                break;
        }
        
        return nomeDiaSemana;
    }
}
