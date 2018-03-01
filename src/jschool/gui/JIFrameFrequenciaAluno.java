package jschool.gui;

import jschool.dao.AlunoDAO;
import jschool.dao.FrequenciaDAO;
import jschool.infraestrutura.Ambiente;
import jschool.infraestrutura.Mensagem;
import jschool.modelo.entidade.Aluno;
import jschool.modelo.entidade.InscricaoTurma;
import jschool.modelo.tabela.FrequenciaAlunoTM;

/**
 *
 * @author andre
 */
public class JIFrameFrequenciaAluno extends javax.swing.JInternalFrame {

    private InscricaoTurma matricula;
   
    public JIFrameFrequenciaAluno() {
        initComponents();
        this.matricula = new InscricaoTurma();
    }

    private void carregarFrequencias(){
        JDMatriculaAluno dialogoMatricula = new JDMatriculaAluno(null, true, (Aluno) new AlunoDAO().consultarId(Ambiente.getUsuarioLogado().getPessoa().getId()));
        dialogoMatricula.setVisible(true);
        if(dialogoMatricula.getSelecaoConfirmada()){
            this.matricula = dialogoMatricula.getMatriculaSelecionada();
            
            FrequenciaAlunoTM tabelaFrequencia = new FrequenciaAlunoTM();
            tabelaFrequencia.addListaFrequencia(new FrequenciaDAO().consultar(this.matricula));
            jtbFrequencias.setModel(tabelaFrequencia);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtfNomeTurma = new javax.swing.JTextField();
        jbtCarregarTurma = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbFrequencias = new javax.swing.JTable();
        jbtSair = new javax.swing.JButton();

        setTitle("Suas frequências");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Matrícula", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Turma");

        jtfNomeTurma.setEditable(false);
        jtfNomeTurma.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jbtCarregarTurma.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbtCarregarTurma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/search16.png"))); // NOI18N
        jbtCarregarTurma.setText("Carregar");
        jbtCarregarTurma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtCarregarTurmaActionPerformed(evt);
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
                .addComponent(jtfNomeTurma, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtCarregarTurma)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtfNomeTurma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtCarregarTurma))
                .addContainerGap())
        );

        jtbFrequencias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jtbFrequencias);

        jbtSair.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbtSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/delete24.png"))); // NOI18N
        jbtSair.setText("Sair");
        jbtSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtSairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jbtSair, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbtSair)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtCarregarTurmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtCarregarTurmaActionPerformed
        try {
            carregarFrequencias();
        } catch (Exception e) {
            Mensagem.erro("Problema ao carregar turma");
        }
    }//GEN-LAST:event_jbtCarregarTurmaActionPerformed

    private void jbtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtSairActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbtSairActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtCarregarTurma;
    private javax.swing.JButton jbtSair;
    private javax.swing.JTable jtbFrequencias;
    private javax.swing.JTextField jtfNomeTurma;
    // End of variables declaration//GEN-END:variables
}
