/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.modelo.entidade;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author andre
 */
public class Aula implements IEntidade {
    
    private String curso;
    private String sala;
    private String descricao;
    private Date data;
    private Calendar horarioInicial;
    private Calendar horarioFinal;
    
    public Aula(){
        this.data = new Date();
        this.horarioInicial = Calendar.getInstance();
        this.horarioFinal = Calendar.getInstance();
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Calendar getHorarioInicial() {
        return horarioInicial;
    }
    
    public void setHorarioInicial(Calendar horarioInicial) {
        this.horarioInicial = horarioInicial;
    }

    public void setHorarioInicial(long horarioInicial) {
        this.horarioInicial.setTimeInMillis(horarioInicial);
    }
    
    public String getHorarioInicialToStrig(){
        SimpleDateFormat f = new SimpleDateFormat("hh:mm");
        return f.format(horarioInicial.getTime());
    }

    public Calendar getHorarioFinal() {
        return horarioFinal;
    }
    
    public String getHorarioFinalToStrig(){
        SimpleDateFormat f = new SimpleDateFormat("hh:mm");
        return f.format(horarioFinal.getTime());
    }

    public void setHorarioFinal(long horarioFinal) {
        this.horarioFinal.setTimeInMillis(horarioFinal);
    }
    
    public void setHorarioFinal(Calendar horarioFinal) {
        this.horarioFinal = horarioFinal;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
