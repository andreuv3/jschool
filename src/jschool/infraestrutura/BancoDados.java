/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.infraestrutura;

import java.sql.Connection;

/**
 *
 * @author andre
 */
public class BancoDados {
    
    private Connection conexao;
    
    public BancoDados(){
    }
    
    public BancoDados(Connection conexao){
        this.conexao = conexao;
    }
    
    public Connection getConexao(){
        return this.conexao;
    }
    
    public boolean abrirConexao(){
        try{
            this.conexao = ConexaoFactory.getConnection();
            return true;
        } catch(Exception e){
            return false;
        }
    }
    
    public boolean fecharConexao(){
        try{
            ConexaoFactory.closeConnection(this.conexao);
            return true;
        } catch(Exception e){
            return false;
        }
    }
}
