/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.modelo.tabela;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import jschool.modelo.entidade.Cidade;
import jschool.modelo.entidade.Estado;
import jschool.modelo.entidade.IEntidade;

/**
 *
 * @author andre
 */
public class CidadeTM extends AbstractTableModel{
    
    private List<IEntidade> linhas;
    //Nomes das colunas da tabela
    private String[] colunas = new String[]{"Codigo", "Nome", "Estado"};
    //Constantes representando o índice das colunas
    private static final int CODIGO = 0;
    private static final int CIDADE = 1;
    private static final int ESTADO = 2;
    
    public CidadeTM() {
        linhas = new ArrayList<>();
    }

    public CidadeTM(List<IEntidade> linhas) {
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
            case CIDADE:
                return String.class;
            case ESTADO:
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
        Cidade c = (Cidade)linhas.get(rowIndex);

        switch (columnIndex) {
            case CODIGO:
                return c.getId();
            case CIDADE:
                return c.getNome();
            case ESTADO:
                return c.getEstado().getNome();
            default:
                //Não deve ocorrer, pois só existem 4 colunas
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    //Define o conteúdo de uma célula
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //Pega o nome referente a linha especificada.
        Cidade c = (Cidade)linhas.get(rowIndex);
        
        switch (columnIndex) {
            case CODIGO:
                c.setId((int) aValue);
            case CIDADE:
                c.setNome((String) aValue);
            case ESTADO:
                c.setEstado((Estado) aValue);
            default:
                //Não deve ocorrer, pois só existem 2 colunas
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
        
        //fireTableCellUpdated(rowIndex, columnIndex); //Notifica a atualização da célula
    }

    //Retorna o nome referente a linha especificada
    public Cidade getCidade(int indiceLinha) {
        return (Cidade)linhas.get(indiceLinha);
    }

    //Adiciona o nome especificado ao modelo
    public void addCidade(Cidade c) {
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
    public void addListaCidades(List<IEntidade> cidades) {
        //Pega o tamanho antigo da tabela, que servirá
        //como índice para o primeiro dos novos registros
        int indice = getRowCount();

        //Adiciona os registros.
        linhas.addAll(cidades);

        //Notifica a mudança.
        fireTableRowsInserted(indice, indice + cidades.size());
    }

    //Remove todos os registros.
    public void limpar() {
        //Remove todos os elementos da lista de nomes.
        linhas.clear();

        //Notifica a mudança.
        fireTableDataChanged();
    }
}
