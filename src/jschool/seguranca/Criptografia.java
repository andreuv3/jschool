/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.seguranca;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 *
 * @author andre
 */
public class Criptografia {
 
    public static String criptografar(String palavra){
        
        String palavraCriptografada = "";
        
        MessageDigest md = null;
        BigInteger hash;
        
        try{
            
            md = MessageDigest.getInstance("MD5");
            hash = new BigInteger(1, md.digest(palavra.getBytes("UTF-8")));
            palavraCriptografada = hash.toString(16);
        
        }
        catch(Exception e){
            return null;
        }
        
        return palavraCriptografada;
        
    }
    
}
