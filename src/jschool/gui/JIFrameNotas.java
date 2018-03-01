package jschool.gui;

import jschool.dao.InscricaoTurmaDAO;
import jschool.enumeracoes.TipoUsuario;
import jschool.infraestrutura.Ambiente;
import jschool.infraestrutura.Mensagem;
import jschool.modelo.entidade.InscricaoTurma;
import jschool.modelo.entidade.Turma;
import jschool.modelo.tabela.InscricaoTurmaNotaTM;

/**
 *
 * @author andre
 */
public class JIFrameNotas extends javax.swing.JInternalFrame {

    private Turma turma;
    private InscricaoTurmaNotaTM tabelaNotas;

    public JIFrameNotas() {
        initComponents();
        this.tabelaNotas = new InscricaoTurmaNotaTM();
        this.jbtAlterar.setEnabled(false);
        this.jbtAlterar.setEnabled(false);

        validarPermissoes();
    }

    private void carregarAlunos() {
        this.tabelaNotas = new InscricaoTurmaNotaTM();
        tabelaNotas.addListaInscricaoTurma(new InscricaoTurmaDAO().consultar(this.turma));
        jtbNotas.setModel(tabelaNotas);
    }

    private void validarPermissoes() {
        if (Ambiente.getTipoUsuarioLogado() != TipoUsuario.PROFESSOR) {
            jbtAlterar.setEnabled(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtfCurso = new javax.swing.JTextField();
        jtfProfessor = new javax.swing.JTextField();
        jtfSituacao = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jdcDataInicial = new com.toedter.calendar.JDateChooser();
        jdcDataFinal = new com.toedter.calendar.JDateChooser();
        jbtCarregarTurma = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jtfCodigo = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtbNotas = new javax.swing.JTable();
        jbtAlterar = new javax.swing.JButton();
        jbtSair = new javax.swing.JButton();

        setClosable(true);
        setTitle("Notas");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Turma"));

        jLabel1.setText("Curso");

        jLabel2.setText("Professor");

        jLabel3.setText("Situação");

        jtfCurso.setEnabled(false);

        jtfProfessor.setEnabled(false);

        jtfSituacao.setEnabled(false);

        jLabel4.setText("Data inicial");

        jLabel5.setText("Data final");

        jdcDataInicial.setEnabled(false);

        jdcDataFinal.setEnabled(false);

        jbtCarregarTurma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/search24.png"))); // NOI18N
        jbtCarregarTurma.setText("Carregar turma");
        jbtCarregarTurma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtCarregarTurmaActionPerformed(evt);
            }
        });

        jLabel6.setText("Código");

        jtfCodigo.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtfCurso)
                                    .addComponent(jtfProfessor))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel3))
                                .addGap(23, 23, 23))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jtfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jdcDataInicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jdcDataFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jtfSituacao)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtCarregarTurma)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jdcDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel6)
                        .addComponent(jtfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jdcDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jtfSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jtfCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jtfProfessor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtCarregarTurma))
        );

        jtbNotas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jtbNotas);

        jbtAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/edit24.png"))); // NOI18N
        jbtAlterar.setText("Alterar");
        jbtAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtAlterarActionPerformed(evt);
            }
        });

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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbtAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtSair)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jbtAlterar, jbtSair});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtSair)
                    .addComponent(jbtAlterar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtCarregarTurmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtCarregarTurmaActionPerformed

        try {
            JDLookUpTurmaProfessor lookUpTurmas = new JDLookUpTurmaProfessor(null, true);
            lookUpTurmas.setVisible(true);

            if (lookUpTurmas.getSelecaoConfirmada()) {
                this.turma = lookUpTurmas.getTurmaSelecionada();
                jtfCodigo.setText(String.valueOf(this.turma.getId()));
                jtfCurso.setText(this.turma.getCurso().getNome());
                jtfProfessor.setText(this.turma.getProfessor().getNome());
                jtfSituacao.setText(this.turma.getSituacao().getDescricaoSituacaoTurma());
                jdcDataInicial.setDate(this.turma.getDataInicio());
                jdcDataFinal.setDate(this.turma.getDataFim());

                this.jbtAlterar.setEnabled(true);
                this.jbtAlterar.setEnabled(true);

                carregarAlunos();
                validarPermissoes();
            }
        } catch (Exception e) {
            Mensagem.erro("Erro ao selecionar turma");
        }
    }//GEN-LAST:event_jbtCarregarTurmaActionPerformed

    private void jbtAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtAlterarActionPerformed
        try {

            int linha = jtbNotas.getSelectedRow();
            if (linha < 0) {
                Mensagem.aviso("Selecione um aluno");
                return;
            }

            InscricaoTurma inscricaoTurma = tabelaNotas.getInscricaoTurma(linha);
            if (inscricaoTurma == null) {
                Mensagem.erro("Problema ao recuperar inscrição");
                return;
            }

            JDLancamentoNota dialogoNota = new JDLancamentoNota(null, true, inscricaoTurma);
            dialogoNota.setVisible(true);
            carregarAlunos();

        } catch (Exception e) {
            Mensagem.erro("Problema ao incluir nota");
        }
    }//GEN-LAST:event_jbtAlterarActionPerformed

    private void jbtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtSairActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbtSairActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbtAlterar;
    private javax.swing.JButton jbtCarregarTurma;
    private javax.swing.JButton jbtSair;
    private com.toedter.calendar.JDateChooser jdcDataFinal;
    private com.toedter.calendar.JDateChooser jdcDataInicial;
    private javax.swing.JTable jtbNotas;
    private javax.swing.JTextField jtfCodigo;
    private javax.swing.JTextField jtfCurso;
    private javax.swing.JTextField jtfProfessor;
    private javax.swing.JTextField jtfSituacao;
    // End of variables declaration//GEN-END:variables
}
