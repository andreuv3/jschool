/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.gui;

import java.util.ArrayList;
import jschool.dao.InscricaoTurmaDAO;
import jschool.dao.TurmaDAO;
import jschool.infraestrutura.Mensagem;
import jschool.modelo.entidade.Aluno;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.entidade.InscricaoTurma;
import jschool.modelo.entidade.Turma;
import jschool.modelo.tabela.InscricaoTurmaTM;

/**
 *
 * @author andre
 */
public class JDMatriculaAluno extends javax.swing.JDialog {

    private Aluno aluno;
    private InscricaoTurma matriculaSelecionada;
    private boolean selecaoConfirmada;
    private ArrayList<IEntidade> matriculas;

    public JDMatriculaAluno(java.awt.Frame parent, boolean modal, Aluno aluno) {
        super(parent, modal);
        initComponents();
        this.aluno = aluno;
        
        aplicarFiltros();
    }
    
    public InscricaoTurma getMatriculaSelecionada(){
        return this.matriculaSelecionada;
    }
    
    public boolean getSelecaoConfirmada(){
        return this.selecaoConfirmada;
    }
    
    private void aplicarFiltros(){
        InscricaoTurmaTM tabelaMatricula = new InscricaoTurmaTM();
        tabelaMatricula.addListaInscricaoTurma(new InscricaoTurmaDAO().consultar(this.aluno));
        jtbMatriculas.setModel(tabelaMatricula);
    }
    
    private void selecionarMatricula(){
        if(jtbMatriculas.getSelectedRow() == -1){
            Mensagem.aviso("Selecione uma matrícula");
            return;
        }
        
        int idMatricula = Integer.parseInt(jtbMatriculas.getModel().getValueAt(jtbMatriculas.getSelectedRow(), 0).toString());
        if(idMatricula > 0){
            
            this.matriculaSelecionada = (InscricaoTurma) new InscricaoTurmaDAO().consultarId(idMatricula);
            
            if(this.matriculaSelecionada == null){
                throw new RuntimeException("Não foi possível recuperar a turma");
            }
            
            this.selecaoConfirmada = true;
            this.dispose();
        } else {
            throw new RuntimeException("Não foi possível recuperar a matricula");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtbMatriculas = new javax.swing.JTable();
        jbtConfirmar = new javax.swing.JButton();
        jbtCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Suas matrículas");
        setModal(true);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Selecione uma matrícula");

        jtbMatriculas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtbMatriculas.setToolTipText("");
        jtbMatriculas.setFillsViewportHeight(true);
        jtbMatriculas.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jtbMatriculas);

        jbtConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/add24.png"))); // NOI18N
        jbtConfirmar.setText("Confirmar");
        jbtConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtConfirmarActionPerformed(evt);
            }
        });

        jbtCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/delete24.png"))); // NOI18N
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
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbtConfirmar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtCancelar)
                        .addGap(105, 105, 105))))
            .addGroup(layout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jbtCancelar, jbtConfirmar});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtConfirmar)
                    .addComponent(jbtCancelar))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbtConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtConfirmarActionPerformed
        try {
            selecionarMatricula();
        } catch (Exception ex) {
            Mensagem.erro(ex.getMessage());
        }
    }//GEN-LAST:event_jbtConfirmarActionPerformed

    private void jbtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtCancelarActionPerformed
        this.selecaoConfirmada = false;
        this.dispose();
    }//GEN-LAST:event_jbtCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbtCancelar;
    private javax.swing.JButton jbtConfirmar;
    private javax.swing.JTable jtbMatriculas;
    // End of variables declaration//GEN-END:variables
}
