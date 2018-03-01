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
public class Aluno extends Pessoa{
    
    private String numeroMatricula;
    private Date dataMatricula;

    public Aluno(){
        super();
    }
    
    public Aluno(int id){
        super.setId(id);
    }
    
    public String getNumeroMatricula() {
        return numeroMatricula;
    }

    public void setNumeroMatricula(String numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    public Date getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(Date dataMatricula) {
        this.dataMatricula = dataMatricula;
    }
}
