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
public enum TipoUsuario {
    
    ADMINISTRADOR(1, "Administrador"), PROFESSOR(2, "Professor"), ALUNO(3, "Aluno");
    
    private final int tipoUsuario;
    private final String descricao;

    TipoUsuario(int tipoUsuario, String descricacao) {
        this.tipoUsuario = tipoUsuario;
        this.descricao = descricacao;
    }

    public int getValorTipoUsuario() {
        return tipoUsuario;
    }
    
    public String getStringTipoUsuario() {
        return this.descricao;
    }
}
