/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.validacao;

import java.util.ArrayList;
import jschool.modelo.entidade.IEntidade;

/**
 *
 * @author andre
 */
public interface IValidador {
    
    public ArrayList<String> validarCadastro(IEntidade entidade);
    
    public ArrayList<String> validarAlteracoes(IEntidade entidade);
    
}
