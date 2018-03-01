/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.infraestrutura;

/**
 *
 * @author andre
 */
public class Tema {
    
    private String nome;
    private String nomeClasse;

    public Tema(){
    }
    
    public Tema(String nome, String nomeClasse){
        this.nome = nome;
        this.nomeClasse = nomeClasse;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeClass() {
        return nomeClasse;
    }

    public void setNomeClass(String nomeClass) {
        this.nomeClasse = nomeClass;
    }
    
    @Override
    public String toString(){
        return this.nome;
    } 
}
