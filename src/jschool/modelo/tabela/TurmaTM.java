/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.modelo.tabela;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import jschool.enumeracoes.SituacaoTurma;
import jschool.modelo.entidade.Curso;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.entidade.Professor;
import jschool.modelo.entidade.Turma;

/**
 *
 * @author andre
 */
public class TurmaTM extends AbstractTableModel{
    
    private List<IEntidade> linhas;
    //Nomes das colunas da tabela
    private String[] colunas = new String[]{"Código", "Curso", "Professor", "Data inicial", "Situação", "Descrição"};
    //Constantes representando o índice das colunas
    private static final int CODIGO = 0;
    private static final int CURSO = 1;
    private static final int PROFESSOR = 2;
    private static final int DATA_INICIAL = 3;
    private static final int SITUACAO = 4;
    private static final int DESCRICAO = 5;
    
    public TurmaTM() {
        linhas = new ArrayList<>();
    }

    public TurmaTM(List<IEntidade> linhas) {
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
            case CURSO:
                return Curso.class;
            case PROFESSOR:
                return Professor.class;
            case DATA_INICIAL:
                return Date.class;
            case SITUACAO:
                return String.class;
            case DESCRICAO:
                return String.class;
            default:
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
        Turma t = (Turma)linhas.get(rowIndex);

        switch (columnIndex) {
            case CODIGO:
                return t.getId();
            case CURSO:
                return t.getCurso().getNome();
            case PROFESSOR:
                return t.getProfessor().getNome();
            case DATA_INICIAL:
                return t.getDataInicio();
            case SITUACAO:
                return t.getSituacao().getDescricaoSituacaoTurma();
            case DESCRICAO:
                return t.getDescricao();
            default:
                //Não deve ocorrer, pois só existem 4 colunas
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    //Define o conteúdo de uma célula
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //Pega o nome referente a linha especificada.
        Turma t = (Turma)linhas.get(rowIndex);
        
        switch (columnIndex) {
            case CODIGO:
                t.setId((int) aValue);
            case CURSO:
                t.setCurso((Curso) aValue);
            case PROFESSOR:
                t.setProfessor((Professor) aValue);
            case DATA_INICIAL:
                t.setDataInicio((Date) aValue);
            case SITUACAO:
                t.setSituacao((SituacaoTurma) aValue);
            case DESCRICAO:
                t.setDescricao((String) aValue);
            default:
                //Não deve ocorrer, pois só existem 2 colunas
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
        
        //fireTableCellUpdated(rowIndex, columnIndex); //Notifica a atualização da célula
    }

    //Retorna o nome referente a linha especificada
    public Turma getTurma(int indiceLinha) {
        return (Turma)linhas.get(indiceLinha);
    }

    //Adiciona o nome especificado ao modelo
    public void addTurma(Turma t) {
        //Adiciona o registro.
        linhas.add(t);

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
    public void addListaTurmas(List<IEntidade> turmas) {
        //Pega o tamanho antigo da tabela, que servirá
        //como índice para o primeiro dos novos registros
        int indice = getRowCount();

        //Adiciona os registros.
        linhas.addAll(turmas);

        //Notifica a mudança.
        fireTableRowsInserted(indice, indice + turmas.size());
    }

    //Remove todos os registros.
    public void limpar() {
        //Remove todos os elementos da lista de nomes.
        linhas.clear();

        //Notifica a mudança.
        fireTableDataChanged();
    }
}
