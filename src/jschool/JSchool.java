/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool;

import javax.swing.UIManager;
import jschool.gui.JFrameLogin;
import jschool.infraestrutura.GerenciadorGUI;
import jschool.infraestrutura.Mensagem;

/**
 *
 * @author andre
 */
public class JSchool {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            
            //Definir estilo da interface gráfica
            UIManager.setLookAndFeel(GerenciadorGUI.getLookAndFeelClass());

            //Inicio do programa
            JFrameLogin frameLogin = new JFrameLogin();
            frameLogin.setVisible(true);
            
        } catch (Exception ex) {
            Mensagem.erro(ex.getMessage());
        }
    }
   
}
