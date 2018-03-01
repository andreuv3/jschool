/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.enumeracoes;

/**
 *
 * @author andre
 */
public enum Situacao {

    ATIVO(1, "Ativo"), INATIVO(0, "Inativo");

    private final int situacao;
    private final String descricao;

    Situacao(int situacao, String descricao) {
        this.situacao = situacao;
        this.descricao = descricao;
    }

    public int getValorSituacao() {
        return situacao;
    }
    
    public String getDescricaoSituacao(){
        if(situacao == 1){
            return "Ativo";
        } else if (situacao == 0){
            return "Inativo";
        } else {
            return "Situação inválida";
        }
    }
}
