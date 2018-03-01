/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.infraestrutura;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author andre
 */
public class ConexaoFactory {
    
    public static Connection getConnection(){
        
        try {
            // Carrega informações do arquivo de propriedades
            Properties prop = new Properties();
            prop.load(new FileInputStream("db.properties"));
            String dbdriver = prop.getProperty("db.driver");
            String dburl = prop.getProperty("db.url");
            String dbuser = prop.getProperty("db.user");
            String dbpassword = "matrix";
            
            Class.forName(dbdriver);
            
            return DriverManager.getConnection(dburl, dbuser, dbpassword);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean closeConnection(Connection conexao){
        
        boolean sucesso = false;
        
        try {
            if(conexao != null && !conexao.isClosed()){
                conexao.close();
                sucesso = true;
            }
        } catch (SQLException e) {
            sucesso = false;
        }
        
        return sucesso;
    }
}
