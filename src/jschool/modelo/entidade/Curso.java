/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.modelo.entidade;

import jschool.enumeracoes.Situacao;

/**
 *
 * @author andre
 */
public class Curso implements IEntidade{
    
    private int id;
    private String nome;
    private String ementa;
    private double horaDuracao;
    private int vagasRecomendadas;
    private Situacao situacao;
    
    public Curso(){
        this.horaDuracao = 0;
        this.vagasRecomendadas = 0;
        this.situacao = Situacao.ATIVO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmenta() {
        return ementa;
    }

    public void setEmenta(String ementa) {
        this.ementa = ementa;
    }

    public double getHoraDuracao() {
        return horaDuracao;
    }

    public void setHoraDuracao(double horaDuracao) {
        this.horaDuracao = horaDuracao;
    }

    public int getVagasRecomendadas() {
        return vagasRecomendadas;
    }

    public void setVagasRecomendadas(int vagasRecomendadas) {
        this.vagasRecomendadas = vagasRecomendadas;
    }

    public String getSituacao() {
        switch(this.situacao){
            case ATIVO:
                return "Ativo";
            case INATIVO:
                return "Inativo";
            default:
                return "";
        }
    }

    public void setSituacao(Situacao ativo) {
        this.situacao = ativo;
    }
    
    public Situacao getSituacaoValor(){
        return this.situacao;
    }
}
