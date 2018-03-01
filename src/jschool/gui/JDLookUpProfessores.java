/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.gui;

import java.util.ArrayList;
import jschool.dao.ProfessorDAO;
import jschool.infraestrutura.Mensagem;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.entidade.Professor;
import jschool.modelo.tabela.ProfessorLookUpTM;
import jschool.util.Strings;

/**
 *
 * @author andre
 */
public class JDLookUpProfessores extends javax.swing.JDialog {
    
    private Professor professorSelecionado;
    private ArrayList<IEntidade> professores;
    private boolean selecaoConfirmada;
    private boolean professoresAtivos;

    /**
     * Creates new form JDLookUpProfessor
     */
    public JDLookUpProfessores(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.professorSelecionado = new Professor();
        this.professores = new ArrayList<>();
        this.selecaoConfirmada = false;
        this.professoresAtivos = false;
        
        aplicarFiltros();
    }
    
    public JDLookUpProfessores(java.awt.Frame parent, boolean modal, boolean professoresAtivos) {
        super(parent, modal);
        initComponents();
        this.professorSelecionado = new Professor();
        this.professores = new ArrayList<>();
        this.selecaoConfirmada = false;
        this.professoresAtivos = professoresAtivos;
        
        aplicarFiltros();
    }
    
    public Professor getProfessorSelecionado(){
        return this.professorSelecionado;
    }
    
    public boolean getSelecaoConfirmada(){
        return this.selecaoConfirmada;
    }
    
    private void aplicarFiltros(){
        
        ProfessorDAO professorDao = new ProfessorDAO();
        ProfessorLookUpTM professorTableModel = new ProfessorLookUpTM();
        
        if(!Strings.IsNullOrEmpty(jtfProfessor.getText())){
            this.professores = professorDao.consultar(jtfProfessor.getText(), true);
        } else {
            this.professores = professorDao.consultarTodos(true);
        }
        
        professorTableModel.addListaProfessores(this.professores);
        
        jtbProfessores.setModel(professorTableModel);
        
    }
    
    private void selecionarProfessor(){
        if(jtbProfessores.getSelectedRow() == -1){
            Mensagem.aviso("Selecione um professor");
            return;
        }
        
        int idProfessor = Integer.parseInt(jtbProfessores.getModel().getValueAt(jtbProfessores.getSelectedRow(), 0).toString());
        if(idProfessor > 0){
            
            this.professorSelecionado = (Professor) new ProfessorDAO().consultarId(idProfessor);
            
            if(this.professorSelecionado == null){
                throw new RuntimeException("Não foi possível recuperar o professor");
            }
            
            this.selecaoConfirmada = true;
            this.dispose();
        } else {
            throw new RuntimeException("Não foi possível recuperar a cidade");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jtbCidades = new javax.swing.JTable();
        jbtConfirmar = new javax.swing.JButton();
        jbtCancelar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jtfCidade = new javax.swing.JTextField();
        jcbEstados = new javax.swing.JComboBox<>();
        jbtPesquisar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtbProfessores = new javax.swing.JTable();
        jbtConfirmar1 = new javax.swing.JButton();
        jbtCancelar1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jtfProfessor = new javax.swing.JTextField();
        jbtPesquisar1 = new javax.swing.JButton();

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Professores");
        setResizable(false);

        jtbProfessores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtbProfessores.setToolTipText("");
        jtbProfessores.setFillsViewportHeight(true);
        jScrollPane2.setViewportView(jtbProfessores);

        jbtConfirmar1.setText("Confirmar");
        jbtConfirmar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtConfirmar1ActionPerformed(evt);
            }
        });

        jbtCancelar1.setText("Cancelar");
        jbtCancelar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtCancelar1ActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros"));

        jLabel3.setText("Professor");

        jbtPesquisar1.setText("Pesquisar");
        jbtPesquisar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtPesquisar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfProfessor, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtPesquisar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtfProfessor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtPesquisar1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(183, 183, 183)
                        .addComponent(jbtConfirmar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtCancelar1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jbtCancelar1, jbtConfirmar1});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtConfirmar1)
                    .addComponent(jbtCancelar1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbtConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtConfirmarActionPerformed
        try{

            selecionarProfessor();

        } catch(Exception ex){
            Mensagem.erro(ex.getMessage());
        }
    }//GEN-LAST:event_jbtConfirmarActionPerformed

    private void jbtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtCancelarActionPerformed
        
        this.dispose();
    }//GEN-LAST:event_jbtCancelarActionPerformed

    private void jbtPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtPesquisarActionPerformed
        try {
            aplicarFiltros();
        } catch (Exception e) {
            Mensagem.erro(e.getMessage());
        }
    }//GEN-LAST:event_jbtPesquisarActionPerformed

    private void jbtConfirmar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtConfirmar1ActionPerformed
        try{

            selecionarProfessor();

        } catch(Exception ex){
            Mensagem.erro(ex.getMessage());
        }
    }//GEN-LAST:event_jbtConfirmar1ActionPerformed

    private void jbtCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtCancelar1ActionPerformed
        this.selecaoConfirmada = false;
        this.dispose();
    }//GEN-LAST:event_jbtCancelar1ActionPerformed

    private void jbtPesquisar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtPesquisar1ActionPerformed
        try {
            aplicarFiltros();
        } catch (Exception e) {
            Mensagem.erro(e.getMessage());
        }
    }//GEN-LAST:event_jbtPesquisar1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbtCancelar;
    private javax.swing.JButton jbtCancelar1;
    private javax.swing.JButton jbtConfirmar;
    private javax.swing.JButton jbtConfirmar1;
    private javax.swing.JButton jbtPesquisar;
    private javax.swing.JButton jbtPesquisar1;
    private javax.swing.JComboBox<String> jcbEstados;
    private javax.swing.JTable jtbCidades;
    private javax.swing.JTable jtbProfessores;
    private javax.swing.JTextField jtfCidade;
    private javax.swing.JTextField jtfProfessor;
    // End of variables declaration//GEN-END:variables
}
