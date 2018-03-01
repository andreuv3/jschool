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
public enum TipoMedia {
    
    ARITIMETICA(1, "Aritimética"), PONDERADA(2, "Ponderada"), HARMONICA(3, "Harmônica");

    private final int situacao;
    private final String descricao;

    TipoMedia(int situacao, String descricao) {
        this.situacao = situacao;
        this.descricao = descricao;
    }

    public int getValorTipoMedia() {
        return situacao;
    }
    
    public String getDescricaoTipoMedia(){
        return this.descricao;
    }
    
    @Override
    public String toString(){
        return this.descricao;
    }
}
