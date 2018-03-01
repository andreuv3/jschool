/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.gui;

import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import jschool.dao.CidadeDAO;
import jschool.dao.EstadoDAO;
import jschool.modelo.entidade.Estado;
import jschool.modelo.entidade.IEntidade;
import jschool.infraestrutura.Mensagem;
import jschool.modelo.entidade.Cidade;
import jschool.util.Strings;
import jschool.modelo.tabela.CidadeTM;

/**
 *
 * @author andre
 */
public class JDLookUpCidades extends javax.swing.JDialog {
    
    private ArrayList<IEntidade> cidades;
    private Cidade cidadeSelecionada;
    private boolean selecaoConfirmada;
    
    /**
     * Creates new form JDLookUpCidades
     */
    public JDLookUpCidades(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        carregarEstados();
        this.cidades = new ArrayList<>();
        this.cidadeSelecionada = new Cidade();
        this.selecaoConfirmada = false;
    }
    
    public Cidade getCidadeSelecionada(){
        return this.cidadeSelecionada;
    }
    
    public boolean getSelecaoConfirmada(){
        return this.selecaoConfirmada;
    }
    
    private void carregarEstados() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();

        ArrayList<IEntidade> estados = new EstadoDAO().consultarTodos();
        for (int i = 0; i < estados.size(); i++) {
            Estado e = (Estado) estados.get(i);
            modelo.addElement(e);
        }

        jcbEstados.setModel(modelo);
    }
    
    private void aplicarFiltros(){
        
        CidadeDAO cidadeDAO = new CidadeDAO();
        CidadeTM tabelaCidades = new CidadeTM();
        
        String nomeCidade = jtfCidade.getText();
        Estado estadoSelecionado = (Estado)jcbEstados.getModel().getSelectedItem();
        
        if(!Strings.IsNullOrEmpty(nomeCidade) && estadoSelecionado.getId() > 0){
            tabelaCidades.addListaCidades(cidadeDAO.consultarCidades(nomeCidade, estadoSelecionado));
        } else if (!Strings.IsNullOrEmpty(nomeCidade) && estadoSelecionado.getId() <= 0){
            tabelaCidades.addListaCidades(cidadeDAO.consultar(nomeCidade));
        } else if (Strings.IsNullOrEmpty(nomeCidade) && estadoSelecionado.getId() > 0){
            tabelaCidades.addListaCidades(cidadeDAO.consultarCidadesPorEstado(estadoSelecionado));
        } else{
            tabelaCidades.addListaCidades(cidadeDAO.consultarTodos());
        }
        
        jtbCidades.setModel(tabelaCidades);
    }
    
    private void selecionarCidade() throws Exception{
        
        if(jtbCidades.getSelectedRow() == -1){
            Mensagem.aviso("Selecione uma cidade");
            return;
        }
        
        int idCidade = Integer.parseInt(jtbCidades.getModel().getValueAt(jtbCidades.getSelectedRow(), 0).toString());
        if(idCidade > 0){
            
            this.cidadeSelecionada = (Cidade) new CidadeDAO().consultarId(idCidade);
            
            if(this.cidadeSelecionada == null){
                throw new Exception("Não foi possível recuperar a cidade");
            }
            
            this.selecaoConfirmada = true;
            this.dispose();
        } else {
            throw new Exception("Não foi possível recuperar a cidade");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jtfCidade = new javax.swing.JTextField();
        jcbEstados = new javax.swing.JComboBox<>();
        jbtPesquisar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbCidades = new javax.swing.JTable();
        jbtConfirmar = new javax.swing.JButton();
        jbtCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cidades");
        setMaximumSize(new java.awt.Dimension(535, 360));
        setModal(true);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros"));

        jLabel1.setText("Cidade");

        jLabel2.setText("UF");

        jbtPesquisar.setText("Pesquisar");
        jbtPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtPesquisarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbEstados, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtPesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jtfCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbEstados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtPesquisar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtbCidades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtbCidades.setToolTipText("");
        jtbCidades.setFillsViewportHeight(true);
        jScrollPane1.setViewportView(jtbCidades);

        jbtConfirmar.setText("Confirmar");
        jbtConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtConfirmarActionPerformed(evt);
            }
        });

        jbtCancelar.setText("Cancelar");
        jbtCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtConfirmar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtCancelar)
                .addGap(218, 218, 218))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jbtCancelar, jbtConfirmar});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtConfirmar)
                    .addComponent(jbtCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbtPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtPesquisarActionPerformed
        try {
            aplicarFiltros();
        } catch (Exception e) {
            Mensagem.erro(e.getMessage());
        }
    }//GEN-LAST:event_jbtPesquisarActionPerformed

    private void jbtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtCancelarActionPerformed
        this.selecaoConfirmada = false;
        this.dispose();
    }//GEN-LAST:event_jbtCancelarActionPerformed

    private void jbtConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtConfirmarActionPerformed
        try{
            selecionarCidade();
        } catch(Exception ex){
            Mensagem.erro(ex.getMessage());
        }
    }//GEN-LAST:event_jbtConfirmarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtCancelar;
    private javax.swing.JButton jbtConfirmar;
    private javax.swing.JButton jbtPesquisar;
    private javax.swing.JComboBox<String> jcbEstados;
    private javax.swing.JTable jtbCidades;
    private javax.swing.JTextField jtfCidade;
    // End of variables declaration//GEN-END:variables
}
