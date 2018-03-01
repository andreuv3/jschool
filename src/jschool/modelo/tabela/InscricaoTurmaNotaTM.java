/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.modelo.tabela;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import jschool.enumeracoes.SituacaoAluno;
import jschool.modelo.entidade.Aluno;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.entidade.InscricaoTurma;

/**
 *
 * @author andre
 */
public class InscricaoTurmaNotaTM extends AbstractTableModel {

    private List<IEntidade> linhas;
    
    private String[] colunas = new String[]{"Codigo", "Aluno", "Nota", "Situação"};
    
    private static final int CODIGO = 0;
    private static final int ALUNO = 1;
    private static final int NOTA = 2;
    private static final int SITUACAO = 3;

    public InscricaoTurmaNotaTM() {
        linhas = new ArrayList<>();
    }

    public InscricaoTurmaNotaTM(List<IEntidade> linhas) {
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
            case ALUNO:
                return Aluno.class;
            case NOTA:
                return Double.class;
            case SITUACAO:
                return String.class;
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
        InscricaoTurma it = (InscricaoTurma) linhas.get(rowIndex);

        switch (columnIndex) {
            case CODIGO:
                return it.getId();
            case ALUNO:
                return it.getAluno().getNome();
            case NOTA:
                return it.getNota();
            case SITUACAO:
                return it.getSituacaoAluno().getDescricaoSituacao();
            default:
                return null;
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
            case ALUNO:
                it.setAluno((Aluno) aValue);
            case NOTA:
                it.setNota((double) aValue);
            case SITUACAO:
                it.setSituacaoAluno((SituacaoAluno) aValue);
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
