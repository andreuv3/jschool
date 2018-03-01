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
public enum SituacaoTurma {
    
    ABERTA(1, "Aberta"), EM_ANDAMENTO(2, "Em andamento"), ENCERRADA(3, "Encerrada");

    private final int situacao;
    private final String descricao;

    SituacaoTurma(int situacao, String descricao) {
        this.situacao = situacao;
        this.descricao = descricao;
    }

    public int getValorSituacaoTurma() {
        return situacao;
    }
    
    public String getDescricaoSituacaoTurma(){
        return this.descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
    
    
}
