/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.infraestrutura;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author andre
 */
public class GerenciadorGUI {
    
    /*
        LookAndFeels:
            Metal           = javax.swing.plaf.metal.MetalLookAndFeel
            Nimbus          = javax.swing.plaf.nimbus.NimbusLookAndFeel
            CDE/Motif       = com.sun.java.swing.plaf.motif.MotifLookAndFeel
            
            - Somente no Windows:
            Windows         = com.sun.java.swing.plaf.windows.WindowsLookAndFeel
            Windows Classic = com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel
    */
    
    private static Properties properties;
    private static final String PROPERTIES_FILE = "gui.properties";
    
    public static String getLookAndFeelClass() throws FileNotFoundException, IOException, Exception {
        
        //Carrega arquivo de propriedades
        properties = new Properties();
        properties.load(new FileInputStream(PROPERTIES_FILE));
        
        //Se o LookAndFeel for nulo lança uma nova exceção
        if (properties.getProperty("gui.style") == null){
            throw new Exception("LookAndFeel nulo");
        }
        
        //Se o LookAndFeel for nulo lança uma nova exceção
        if (properties.getProperty("gui.style").trim().isEmpty()){
            throw new Exception("LookAndFeel vazio");
        }
        
        return properties.getProperty("gui.style");
    }

    public static void setLookAndFeel(String lookAndFeelClass) throws FileNotFoundException, IOException, Exception {        
        
        //Carrega o arquivo, faz a alteração na propriedade e salva
        properties = new Properties();
        properties.load(new FileInputStream(PROPERTIES_FILE));
        properties.setProperty("gui.style", lookAndFeelClass);
        properties.store(new FileOutputStream(PROPERTIES_FILE), "Alteracao do LookAndFeel");
        
    }
}