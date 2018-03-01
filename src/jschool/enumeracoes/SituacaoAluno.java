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
public enum SituacaoAluno {
    
    CURSANDO(1, "Cursando"), APROVADO(2, "Aprovado"), REPROVADO(3, "Reprovado");

    private final int situacao;
    private final String descricao;

    SituacaoAluno(int situacao, String descricao) {
        this.situacao = situacao;
        this.descricao = descricao;
    }

    public int getValorSituacao() {
        return situacao;
    }
    
    public String getDescricaoSituacao(){
        if(situacao == 1){
            return "Cursando";
        } else if (situacao == 2){
            return "Aprovado";
        } else if(situacao == 3){
            return "Reprovado";
        } else {
            return "Situação inválida";
        }
    }
}
