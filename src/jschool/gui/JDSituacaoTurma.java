/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.gui;

import jschool.ajudantes.ComboBoxAjudante;
import jschool.dao.TurmaDAO;
import jschool.enumeracoes.SituacaoTurma;
import jschool.infraestrutura.Mensagem;

/**
 *
 * @author andre
 */
public class JDSituacaoTurma extends javax.swing.JDialog {
    
    private int idTurma;

    public JDSituacaoTurma(java.awt.Frame parent, boolean modal, int idTurma) {
        super(parent, modal);
        initComponents();
        ComboBoxAjudante.preencherComboBoxSituacaoTurma(jcbSituacao);
        this.idTurma = idTurma;
    }
    
    private void salvarSituacao(){
        
        if(jcbSituacao.getModel().getSelectedItem().equals("(selecione)")){
            Mensagem.aviso("Selecione uma situação");
            return;
        }
        
        if(new TurmaDAO().alterarSituacaoTurma(idTurma, (SituacaoTurma) jcbSituacao.getSelectedItem())){
            Mensagem.sucesso("Situação alterada com sucesso");
            this.dispose();
        } else {
            Mensagem.erro("Problema ao alterar situação");
        }
        
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jcbSituacao = new javax.swing.JComboBox<>();
        jbtConfirmar = new javax.swing.JButton();
        jtfCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Situação da turma");
        setResizable(false);

        jLabel1.setText("Situação");

        jbtConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/add24.png"))); // NOI18N
        jbtConfirmar.setText("Confirmar");
        jbtConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtConfirmarActionPerformed(evt);
            }
        });

        jtfCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/delete24.png"))); // NOI18N
        jtfCancelar.setText("Cancelar");
        jtfCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbSituacao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(jbtConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfCancelar)
                .addGap(20, 20, 20))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jbtConfirmar, jtfCancelar});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jcbSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtConfirmar)
                    .addComponent(jtfCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbtConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtConfirmarActionPerformed
        try {
            salvarSituacao();
        } catch (Exception ex) {
            Mensagem.erro("Problema ao salvar professor: " + ex.getMessage());
        }
    }//GEN-LAST:event_jbtConfirmarActionPerformed

    private void jtfCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_jtfCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton jbtConfirmar;
    private javax.swing.JComboBox<String> jcbSituacao;
    private javax.swing.JButton jtfCancelar;
    // End of variables declaration//GEN-END:variables
}
