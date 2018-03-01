/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.infraestrutura;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import jschool.gui.JFrameInconsistencias;

/**
 *
 * @author andre
 */
public class Mensagem {
    
    public static void sucesso(String mensagem){
        JOptionPane.showMessageDialog(null, mensagem, "Aviso", JOptionPane.INFORMATION_MESSAGE);  
    }
    
    public static void erro(String mensagem){
        JOptionPane.showMessageDialog(null, mensagem, "Aviso", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void aviso(String mensagem){
        JOptionPane.showMessageDialog(null, mensagem, "Aviso", JOptionPane.WARNING_MESSAGE);
    }
    
    public static void inconsistencias(ArrayList<String> inconsistencias){
        String mensagem = "";
        
        for(String inconsistencia : inconsistencias){
            mensagem += "\n -" + inconsistencia;
        }
        
        JOptionPane.showMessageDialog(null, inconsistencias, "Inconsistências", JOptionPane.WARNING_MESSAGE);
    }
    
    public static void frameInconsistencias(ArrayList<String> inconsistencias){
        JFrameInconsistencias frameInconsistencias = new JFrameInconsistencias(inconsistencias);
        frameInconsistencias.setVisible(true);
    }
    
}
