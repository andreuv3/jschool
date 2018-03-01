/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.modelo.tabela;

import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.AbstractTableModel;
import jschool.modelo.entidade.Frequencia;
import jschool.modelo.entidade.IEntidade;

/**
 *
 * @author andre
 */
public class FrequenciaAlunoTM extends AbstractTableModel {

    private ArrayList<IEntidade> linhas;
    
    private String[] colunas = {"Data", "Presente"};
    
    private static final int DATA = 0;
    private static final int PRESENTE = 1;
    
    public FrequenciaAlunoTM(){
        this.linhas = new ArrayList<>();
    }
    
    public FrequenciaAlunoTM(ArrayList<IEntidade> linhas){
        this.linhas = linhas;
    }
    
    @Override
    public int getRowCount() {
        return linhas.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        return colunas[columnIndex];
    }

    //Retorna o tipo de dado de cada coluna
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case DATA:
                return Date.class;
            case PRESENTE:
                return Boolean.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    //Retorno o conteúdo de uma célula
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        //Pega o nome referente a linha especificada.
        Frequencia f = (Frequencia)linhas.get(rowIndex);

        switch (columnIndex) {
            case DATA:
                return f.getData();
            case PRESENTE:
                return f.isPresente();
            default:
                return null;
        }
    }

    //Define o conteúdo de uma célula
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //Pega o nome referente a linha especificada.
        Frequencia f = (Frequencia)linhas.get(rowIndex);
        
        switch (columnIndex) {
            case DATA:
                f.setData((Date) aValue);
            case PRESENTE:
                f.setPresente((Boolean) aValue);
        }
        
        //fireTableCellUpdated(rowIndex, columnIndex); //Notifica a atualização da célula
    }

    //Retorna o nome referente a linha especificada
    public Frequencia getFrequencia(int indiceLinha) {
        return (Frequencia)linhas.get(indiceLinha);
    }

    //Adiciona o nome especificado ao modelo
    public void addFrequencia(Frequencia f) {
        //Adiciona o registro
        linhas.add(f);

        //Pega a quantidade de registros e subtrai 1 para
        //achar o último índice. A subtração é necessária
        //porque os índices começam em zero.
        int ultimoIndice = getRowCount() - 1;

        //Notifica a mudança.
        fireTableRowsInserted(ultimoIndice, ultimoIndice);
    }

    //Remove o nome da linha especificada.
    public void removeNome(int indiceLinha) {
        //Remove o registro.
        linhas.remove(indiceLinha);

        //Notifica a mudança.
        fireTableRowsDeleted(indiceLinha, indiceLinha);
    }

    //Adiciona uma lista de nomes no final da lista.
    public void addListaFrequencia(ArrayList<IEntidade> frequencias) {
        //Pega o tamanho antigo da tabela, que servirá
        //como índice para o primeiro dos novos registros
        int indice = getRowCount();

        //Adiciona os registros.
        linhas.addAll(frequencias);

        //Notifica a mudança.
        fireTableRowsInserted(indice, indice + frequencias.size());
    }
    
    public ArrayList<IEntidade> getListaFrequencias(){
        return linhas;
    }

    //Remove todos os registros.
    public void limpar() {
        //Remove todos os elementos da lista de nomes.
        linhas.clear();

        //Notifica a mudança.
        fireTableDataChanged();
    }
    
}