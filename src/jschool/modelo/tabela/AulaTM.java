/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.modelo.tabela;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.table.AbstractTableModel;
import jschool.modelo.entidade.Aula;
import jschool.modelo.entidade.IEntidade;

/**
 *
 * @author andre
 */
public class AulaTM extends AbstractTableModel {

    private ArrayList<IEntidade> linhas;

    private String[] colunas = {"Data", "Curso", "Descrição", "Sala", "Início", "Fim"};

    private static final int DATA = 0;
    private static final int CURSO = 1;
    private static final int DESCRICAO = 2;
    private static final int SALA = 3;
    private static final int INICIO = 4;
    private static final int FIM = 5;

    public AulaTM() {
        this.linhas = new ArrayList<>();
    }

    public AulaTM(ArrayList<IEntidade> linhas) {
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
            case CURSO:
                return String.class;
            case DESCRICAO:
                return String.class;
            case SALA:
                return String.class;
            case INICIO:
                return Calendar.class;
            case FIM:
                return Calendar.class;
            default:
                return String.class;
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
        Aula a = (Aula) linhas.get(rowIndex);

        switch (columnIndex) {
            case DATA:
                return a.getData();
            case CURSO:
                return a.getCurso();
            case DESCRICAO:
                return a.getDescricao();
            case SALA:
                return a.getSala();
            case INICIO:
                return a.getHorarioInicialToStrig();
            case FIM:
                return a.getHorarioFinalToStrig();
            default:
                return null;
        }
    }

    //Define o conteúdo de uma célula
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //Pega o nome referente a linha especificada.
        Aula a = (Aula) linhas.get(rowIndex);

        switch (columnIndex) {
            case DATA:
                a.setData((Date) aValue);
            case CURSO:
                a.setCurso((String) aValue);
            case DESCRICAO:
                a.setDescricao((String) aValue);
            case SALA:
                a.setSala((String) aValue);
            case INICIO:
                a.setHorarioInicial((Calendar) aValue);
            case FIM:
                a.setHorarioFinal((Calendar) aValue);
        }

        //fireTableCellUpdated(rowIndex, columnIndex); //Notifica a atualização da célula
    }

    //Retorna o nome referente a linha especificada
    public Aula getAula(int indiceLinha) {
        return (Aula) linhas.get(indiceLinha);
    }

    //Adiciona o nome especificado ao modelo
    public void addAula(Aula a) {
        //Adiciona o registro.
        linhas.add(a);

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
    public void addListaAulas(ArrayList<IEntidade> aulas) {
        //Pega o tamanho antigo da tabela, que servirá
        //como índice para o primeiro dos novos registros
        int indice = getRowCount();

        //Adiciona os registros.
        linhas.addAll(aulas);

        //Notifica a mudança.
        fireTableRowsInserted(indice, indice + aulas.size());
    }

    //Remove todos os registros.
    public void limpar() {
        //Remove todos os elementos da lista de nomes.
        linhas.clear();

        //Notifica a mudança.
        fireTableDataChanged();
    }

}
