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
import jschool.modelo.entidade.Cidade;
import jschool.modelo.entidade.Curso;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.entidade.Professor;
import jschool.enumeracoes.Sexo;

/**
 *
 * @author andre
 */
public class ProfessorLookUpTM extends AbstractTableModel{
    private List<IEntidade> linhas;
    //Nomes das colunas da tabela
    private String[] colunas = new String[]{"Codigo", "Nome"};
    //Constantes representando o índice das colunas
    private static final int CODIGO = 0;
    private static final int NOME = 1;
    
    public ProfessorLookUpTM() {
        linhas = new ArrayList<IEntidade>();
    }

    public ProfessorLookUpTM(List<IEntidade> linhas) {
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
        Professor p = (Professor)linhas.get(rowIndex);

        switch (columnIndex) {
            case CODIGO:
                return p.getId();
            case NOME:
                return p.getNome();
            default:
                //Não deve ocorrer, pois só existem 4 colunas
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    //Define o conteúdo de uma célula
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //Pega o nome referente a linha especificada.
        Professor p = (Professor)linhas.get(rowIndex);
        
        switch (columnIndex) {
            case CODIGO:
                p.setId((int) aValue);
            case NOME:
                p.setNome((String) aValue);
            default:
                //Não deve ocorrer, pois só existem 2 colunas
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
        
        //fireTableCellUpdated(rowIndex, columnIndex); //Notifica a atualização da célula
    }

    //Retorna o nome referente a linha especificada
    public Curso getProfessor(int indiceLinha) {
        return (Curso)linhas.get(indiceLinha);
    }

    //Adiciona o nome especificado ao modelo
    public void addProfessor(Curso c) {
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
    public void addListaProfessores(List<IEntidade> professores) {
        //Pega o tamanho antigo da tabela, que servirá
        //como índice para o primeiro dos novos registros
        int indice = getRowCount();

        //Adiciona os registros.
        linhas.addAll(professores);

        //Notifica a mudança.
        fireTableRowsInserted(indice, indice + professores.size());
    }

    //Remove todos os registros.
    public void limpar() {
        //Remove todos os elementos da lista de nomes.
        linhas.clear();

        //Notifica a mudança.
        fireTableDataChanged();
    }
}