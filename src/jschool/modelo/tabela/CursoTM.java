/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.modelo.tabela;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import jschool.modelo.entidade.Curso;
import jschool.modelo.entidade.IEntidade;
import jschool.enumeracoes.Situacao;

/**
 *
 * @author andre
 */
public class CursoTM extends AbstractTableModel{
    private List<IEntidade> linhas;
    //Nomes das colunas da tabela
    private String[] colunas = new String[]{"Codigo", "Nome", "Duração(h)", "Situação"};
    //Constantes representando o índice das colunas
    private static final int CODIGO = 0;
    private static final int NOME = 1;
    private static final int DURACAO = 2;
    private static final int SITUACAO = 3;
    
    public CursoTM() {
        linhas = new ArrayList<IEntidade>();
    }

    public CursoTM(List<IEntidade> linhas) {
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
            case NOME:
                return String.class;
            case DURACAO:
                return Double.class;
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
        Curso c = (Curso)linhas.get(rowIndex);

        switch (columnIndex) {
            case CODIGO:
                return c.getId();
            case NOME:
                return c.getNome();
            case DURACAO:
                return c.getHoraDuracao();
            case SITUACAO:
                return c.getSituacao();
            default:
                //Não deve ocorrer, pois só existem 4 colunas
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    //Define o conteúdo de uma célula
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //Pega o nome referente a linha especificada.
        Curso c = (Curso)linhas.get(rowIndex);
        
        switch (columnIndex) {
            case CODIGO:
                c.setId((int) aValue);
            case NOME:
                c.setNome((String) aValue);
            case DURACAO:
                c.setHoraDuracao((double) aValue);
            case SITUACAO:
                c.setSituacao((Situacao) aValue);
            default:
                //Não deve ocorrer, pois só existem 2 colunas
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
        
        //fireTableCellUpdated(rowIndex, columnIndex); //Notifica a atualização da célula
    }

    //Retorna o nome referente a linha especificada
    public Curso getCurso(int indiceLinha) {
        return (Curso)linhas.get(indiceLinha);
    }

    //Adiciona o nome especificado ao modelo
    public void addCurso(Curso c) {
        //Adiciona o registro.
        linhas.add(c);

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
    public void addListaCursos(List<IEntidade> cursos) {
        //Pega o tamanho antigo da tabela, que servirá
        //como índice para o primeiro dos novos registros
        int indice = getRowCount();

        //Adiciona os registros.
        linhas.addAll(cursos);

        //Notifica a mudança.
        fireTableRowsInserted(indice, indice + cursos.size());
    }

    //Remove todos os registros.
    public void limpar() {
        //Remove todos os elementos da lista de nomes.
        linhas.clear();

        //Notifica a mudança.
        fireTableDataChanged();
    }
}
