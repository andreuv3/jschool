/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.modelo.entidade;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import jschool.util.Formatador;

/**
 *
 * @author andre
 */
public class DiaLetivo implements IEntidade{
    
    private int id;
    private Turma turma;
    private Date dataAula;

    public DiaLetivo() {
        this.dataAula = new Date();
    }

    public DiaLetivo(Turma turma, Date dataAula) {
        this.turma = turma;
        this.dataAula = dataAula;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Date getDataAula() {
        return dataAula;
    }

    public void setDataAula(Date dataAula) {
        this.dataAula = dataAula;
    }
    
    public String getDiaSemana(){
        return Formatador.nomeDiaDaSemana(this.dataAula);
    }
}
