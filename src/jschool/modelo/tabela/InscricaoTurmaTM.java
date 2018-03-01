/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.modelo.tabela;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import jschool.enumeracoes.Situacao;
import jschool.modelo.entidade.Aluno;
import jschool.modelo.entidade.Curso;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.entidade.InscricaoTurma;
import jschool.modelo.entidade.Professor;

/**
 *
 * @author andre
 */
public class InscricaoTurmaTM extends AbstractTableModel {

    private List<IEntidade> linhas;
    //Nomes das colunas da tabela
    private String[] colunas = new String[]{"Codigo", "Aluno", "Descrição", "Curso", "Professor", "Situação"};
    //Constantes representando o índice das colunas
    private static final int CODIGO = 0;
    private static final int DESCRICAO = 1;
    private static final int ALUNO = 2;
    private static final int CURSO = 3;
    private static final int PROFESSOR = 4;
    private static final int SITUACAO = 5;

    public InscricaoTurmaTM() {
        linhas = new ArrayList<>();
    }

    public InscricaoTurmaTM(List<IEntidade> linhas) {
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
            case DESCRICAO:
                return String.class;
            case ALUNO:
                return Aluno.class;
            case CURSO:
                return Curso.class;
            case PROFESSOR:
                return Professor.class;
            case SITUACAO:
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
        InscricaoTurma it = (InscricaoTurma) linhas.get(rowIndex);

        switch (columnIndex) {
            case CODIGO:
                return it.getId();
            case DESCRICAO:
                return it.getTurma().getDescricao();
            case ALUNO:
                return it.getAluno().getNome();
            case CURSO:
                return it.getTurma().getCurso().getNome();
            case PROFESSOR:
                return it.getTurma().getProfessor().getNome();
            case SITUACAO:
                return it.getStatus().getDescricaoSituacao();
            default:
                //Não deve ocorrer, pois só existem 4 colunas
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    //Define o conteúdo de uma célula
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //Pega o nome referente a linha especificada.
        InscricaoTurma it = (InscricaoTurma) linhas.get(rowIndex);

        switch (columnIndex) {
            case CODIGO:
                it.setId((int) aValue);
            case DESCRICAO:
                it.getTurma().setDescricao((String) aValue);
            case ALUNO:
                it.setAluno((Aluno) aValue);
            case CURSO:
                it.getTurma().setCurso((Curso) aValue);
            case PROFESSOR:
                it.getTurma().setProfessor((Professor) aValue);
            case SITUACAO:
                it.setStatus((Situacao) aValue);
            default:
                //Não deve ocorrer, pois só existem 2 colunas
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }

        //fireTableCellUpdated(rowIndex, columnIndex); //Notifica a atualização da célula
    }

    //Retorna o nome referente a linha especificada
    public InscricaoTurma getInscricaoTurma(int indiceLinha) {
        return (InscricaoTurma) linhas.get(indiceLinha);
    }

    //Adiciona o nome especificado ao modelo
    public void addInscricaoTurma(InscricaoTurma it) {
        //Adiciona o registro.
        linhas.add(it);

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
    public void addListaInscricaoTurma(List<IEntidade> InscricaoTurma) {
        //Pega o tamanho antigo da tabela, que servirá
        //como índice para o primeiro dos novos registros
        int indice = getRowCount();

        //Adiciona os registros.
        linhas.addAll(InscricaoTurma);

        //Notifica a mudança.
        fireTableRowsInserted(indice, indice + InscricaoTurma.size());
    }

    //Remove todos os registros.
    public void limpar() {
        //Remove todos os elementos da lista de nomes.
        linhas.clear();

        //Notifica a mudança.
        fireTableDataChanged();
    }
}
