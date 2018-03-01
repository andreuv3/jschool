/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.infraestrutura;

import java.io.FileInputStream;
import java.util.Properties;
import jschool.enumeracoes.TipoUsuario;
import jschool.modelo.entidade.Usuario;

/**
 *
 * @author andre
 */
public class Ambiente {
    
    private static Usuario usuarioLogado;
    
    public static void setUsuarioLogado(Usuario usuarioLogado){
        Ambiente.usuarioLogado = usuarioLogado;
    }
    
    public static Usuario getUsuarioLogado(){
        return Ambiente.usuarioLogado;
    }
 
    public static String nomeBancoDados(){
        try{
            Properties prop = new Properties();
            prop.load(new FileInputStream("db.properties"));
            return prop.getProperty("db.name");
        } catch(Exception ex){
            return "";
        }
    }
    
    public static TipoUsuario getTipoUsuarioLogado(){
        
        if(usuarioLogado == null){
            throw new RuntimeException("Nenhum usuário está logado");
        }
        
        return usuarioLogado.getTipo();
        
    }
}
