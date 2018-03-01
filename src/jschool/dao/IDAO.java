/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.dao;

import java.util.ArrayList;
import jschool.modelo.entidade.IEntidade;

/**
 *
 * @author andre
 */
public interface IDAO {
    
    public boolean salvar(IEntidade e);

    public boolean atualizar(IEntidade e);

    public boolean excluir(int id);

    public ArrayList<IEntidade> consultarTodos();

    public ArrayList<IEntidade> consultar(String criterio);

    public IEntidade consultarId(int id);
}
