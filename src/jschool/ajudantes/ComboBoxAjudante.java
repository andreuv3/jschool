package jschool.ajudantes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import jschool.dao.EstadoDAO;
import jschool.enumeracoes.Sexo;
import jschool.enumeracoes.Situacao;
import jschool.enumeracoes.SituacaoTurma;
import jschool.enumeracoes.TipoMedia;
import jschool.enumeracoes.TipoUsuario;
import jschool.modelo.entidade.Estado;
import jschool.modelo.entidade.IEntidade;

/**
 *
 * @author andre
 */
public class ComboBoxAjudante {
    
    public static void preencherComboBoxSexo(JComboBox comboBox){
        
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        modelo.addElement("(selecione)");
        modelo.addElement(Sexo.MASCULINO);
        modelo.addElement(Sexo.FEMININO);

        comboBox.setModel(modelo);
    }
    
    public static void preencherComboBoxTipoUsuario(JComboBox comboBox){
        
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        
        modelo.addElement("(selecione)");
        
        for(TipoUsuario tipo : TipoUsuario.values()){
            modelo.addElement(tipo);
        }
        
        comboBox.setModel(modelo);
    }
    
    public static void preencherComboBoxSituacao(JComboBox comboBox){
        
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        
        modelo.addElement("(selecione)");
        
        for(Situacao st : Situacao.values()){
            modelo.addElement(st);
        }
        
        comboBox.setModel(modelo);
    }
    
    public static void preencherComboBoxSituacaoTurma(JComboBox comboBox){
        
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        
        modelo.addElement("(selecione)");
        
        for(SituacaoTurma stTurma : SituacaoTurma.values()){
            modelo.addElement(stTurma);
        }
        
        comboBox.setModel(modelo);
    }
    
    public static void preencherComboBoxTipoMedia(JComboBox comboBox){
        
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        
        modelo.addElement("(selecione)");
        
        for(TipoMedia media : TipoMedia.values()){
            modelo.addElement(media);
        }
        
        comboBox.setModel(modelo);
    }
        
    public static void preencherComboBoxDiasSemana(JComboBox comboBox){
        
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        
        modelo.addElement("(selecione)");
        
        HashMap<Integer, String> diasSemana = new HashMap<>();
        diasSemana.put(Calendar.SUNDAY, "Domingo");
        diasSemana.put(Calendar.MONDAY, "Segunda");
        diasSemana.put(Calendar.TUESDAY, "Terça");
        diasSemana.put(Calendar.WEDNESDAY, "Quarta");
        diasSemana.put(Calendar.THURSDAY, "Quinta");
        diasSemana.put(Calendar.FRIDAY, "Sexta");
        diasSemana.put(Calendar.SATURDAY, "Sábado");
        
        for(Integer chave : diasSemana.keySet()){
            modelo.addElement(diasSemana.get(chave));
        }
        
        comboBox.setModel(modelo);
    }
    
    public static void preencherComboBoxEstados(JComboBox comboBox){
        
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        
        ArrayList<IEntidade> estados = new EstadoDAO().consultarTodos();
        
        for(IEntidade estado : estados){
            modelo.addElement((Estado) estado);
        }
        
        comboBox.setModel(modelo);
    }
}
