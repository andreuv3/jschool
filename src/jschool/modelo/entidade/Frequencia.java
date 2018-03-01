/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.modelo.entidade;

import java.util.Date;

/**
 *
 * @author andre
 */
public class Frequencia implements IEntidade{
    
    private int id;
    private Date data;
    private InscricaoTurma matricula;
    private boolean presente;
    
    public Frequencia(){
        this.data = new Date();
        this.matricula = new InscricaoTurma();
        this.presente = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }

    public InscricaoTurma getMatricula() {
        return matricula;
    }

    public void setMatricula(InscricaoTurma matricula) {
        this.matricula = matricula;
    }
}
