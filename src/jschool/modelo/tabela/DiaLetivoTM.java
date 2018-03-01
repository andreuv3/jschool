/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.modelo.tabela;

import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.AbstractTableModel;
import jschool.modelo.entidade.DiaLetivo;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.entidade.Usuario;

/**
 *
 * @author andre
 */
public class DiaLetivoTM extends AbstractTableModel {

    private ArrayList<IEntidade> linhas;
    
    private String[] colunas = {"Código", "Data", "Dia da semana"};
    
    private static final int CODIGO = 0;
    private static final int DATA = 1;
    private static final int DIA_SEMANA = 2;
    
    public DiaLetivoTM(){
        this.linhas = new ArrayList<>();
    }
    
    public DiaLetivoTM(ArrayList<IEntidade> linhas){
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
            case CODIGO:
                return Integer.class;
            case DATA:
                return Date.class;
            case DIA_SEMANA:
                return String.class;
            default:
                //Não deve ocorrer, pois só existem 2 colunas
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
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
        DiaLetivo dl = (DiaLetivo)linhas.get(rowIndex);

        switch (columnIndex) {
            case CODIGO:
                return dl.getId();
            case DATA:
                return dl.getDataAula();
            case DIA_SEMANA:
                return dl.getDiaSemana();
            default:
                //Não deve ocorrer, pois só existem 4 colunas
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    //Define o conteúdo de uma célula
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //Pega o nome referente a linha especificada.
        DiaLetivo dl = (DiaLetivo)linhas.get(rowIndex);
        
        switch (columnIndex) {
            case CODIGO:
                dl.setId((int) aValue);
            case DATA:
                dl.setDataAula((Date) aValue);
            default:
                //Não deve ocorrer, pois só existem 2 colunas
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
        
        //fireTableCellUpdated(rowIndex, columnIndex); //Notifica a atualização da célula
    }

    //Retorna o nome referente a linha especificada
    public Usuario getDiaLetivo(int indiceLinha) {
        return (Usuario)linhas.get(indiceLinha);
    }

    //Adiciona o nome especificado ao modelo
    public void addDiaLetivo(DiaLetivo dl) {
        //Adiciona o registro.
        linhas.add(dl);

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
    public void addListaDiasLetivos(ArrayList<IEntidade> diasLetivos) {
        //Pega o tamanho antigo da tabela, que servirá
        //como índice para o primeiro dos novos registros
        int indice = getRowCount();

        //Adiciona os registros.
        linhas.addAll(diasLetivos);

        //Notifica a mudança.
        fireTableRowsInserted(indice, indice + diasLetivos.size());
    }

    //Remove todos os registros.
    public void limpar() {
        //Remove todos os elementos da lista de nomes.
        linhas.clear();

        //Notifica a mudança.
        fireTableDataChanged();
    }
    
}