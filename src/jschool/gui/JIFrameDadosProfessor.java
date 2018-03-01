package jschool.gui;

import java.util.ArrayList;
import jschool.ajudantes.ComboBoxAjudante;
import jschool.dao.ProfessorDAO;
import jschool.enumeracoes.Sexo;
import jschool.infraestrutura.Ambiente;
import jschool.infraestrutura.Mensagem;
import jschool.modelo.entidade.Professor;
import jschool.util.Formatador;
import jschool.validacao.ProfessorValidador;

/**
 *
 * @author andre
 */
public class JIFrameDadosProfessor extends javax.swing.JInternalFrame {

    private Professor professor;

    public JIFrameDadosProfessor() {
        initComponents();
        ComboBoxAjudante.preencherComboBoxSexo(jcbSexo);
        carregarProfessor();
    }

    private void carregarProfessor() {
        this.professor = (Professor) new ProfessorDAO().consultarId(Ambiente.getUsuarioLogado().getPessoa().getId());
        jtfCodigo.setText(Integer.toString(this.professor.getId()));
        jtfSituacao.setText(this.professor.getSituacaoString());
        jtfNome.setText(this.professor.getNome());
        jffTelefone.setText(this.professor.getTelefone());
        jffCelular.setText(this.professor.getCelular());
        jtfEmail.setText(this.professor.getEmail());
        jtfSite.setText(this.professor.getSite());
        jtfLogradouro.setText(this.professor.getLogradouro());
        jtfNumeroEndereco.setText(this.professor.getNumero());
        jtfComplemento.setText(this.professor.getComplemento());
        jtfRg.setText(this.professor.getRg());
        jffCpf.setText(this.professor.getCpf());
        jffNumeroCtps.setText(this.professor.getNumeroCtps());
        jdcDataAdmissao.setDate(professor.getDataAdmissao());
        jdcDataNascimento.setDate(professor.getDataNascimento());
        jdcDataDesligamento.setDate(professor.getDataDemisao());

        if (this.professor.getCidade() != null) {
            jtfNomeCidade.setText(professor.getCidade().getNome());
        }

        if (this.professor.getSexo() != null) {
            jcbSexo.getModel().setSelectedItem(this.professor.getSexo());
        }
    }

    private void salvarProfessor() {

        ArrayList<String> inconsistencias = new ArrayList();

        this.professor.setId(Integer.parseInt(jtfCodigo.getText()));
        this.professor.setNome(jtfNome.getText());
        this.professor.setTelefone(Formatador.removerPontuacaoTelefone(jffTelefone.getText()));
        this.professor.setCelular(Formatador.removerPontuacaoTelefone(jffCelular.getText()));
        this.professor.setEmail(jtfEmail.getText());
        this.professor.setSite(jtfSite.getText());
        this.professor.setLogradouro(jtfLogradouro.getText());
        this.professor.setNumero(jtfNumeroEndereco.getText());
        this.professor.setComplemento(jtfComplemento.getText());
        this.professor.setBairro(jtfBairro.getText());
        this.professor.setCpf(Formatador.removerPontuacaoCpf(jffCpf.getText()));
        this.professor.setRg(jtfRg.getText());
        this.professor.setNumeroCtps(Formatador.removerPontuacaoNumeroCpts(jffNumeroCtps.getText()));
        this.professor.setDataNascimento(jdcDataNascimento.getDate());
        this.professor.setDataAdmissao(jdcDataAdmissao.getDate());
        if (!jcbSexo.getModel().getSelectedItem().equals("(selecione)")) {
            this.professor.setSexo((Sexo) jcbSexo.getModel().getSelectedItem());
        } else {
            this.professor.setSexo(null);
        }

        inconsistencias = new ProfessorValidador().validarAlteracoes(professor);
        if (inconsistencias.size() > 0) {
            Mensagem.frameInconsistencias(inconsistencias);
        } else {
            if (new ProfessorDAO().atualizar(professor)) {
                Mensagem.sucesso("Dados registrados com sucesso!");
                this.dispose();
            } else {
                Mensagem.erro("Problema ao salvar professor.");
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtfCodigo = new javax.swing.JTextField();
        jtfNome = new javax.swing.JTextField();
        jffTelefone = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jtfSituacao = new javax.swing.JTextField();
        jffCpf = new javax.swing.JFormattedTextField();
        jtfRg = new javax.swing.JTextField();
        jdcDataNascimento = new com.toedter.calendar.JDateChooser();
        jcbSexo = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jffNumeroCtps = new javax.swing.JFormattedTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jdcDataAdmissao = new com.toedter.calendar.JDateChooser();
        jdcDataDesligamento = new com.toedter.calendar.JDateChooser();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jtfNomeCidade = new javax.swing.JTextField();
        jlbLookUpCidade = new javax.swing.JLabel();
        jtfComplemento = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jtfBairro = new javax.swing.JTextField();
        jtfLogradouro = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jtfNumeroEndereco = new javax.swing.JTextField();
        jtfSite = new javax.swing.JTextField();
        jtfEmail = new javax.swing.JTextField();
        jffCelular = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jbtConfirmar = new javax.swing.JButton();
        jtfCancelar = new javax.swing.JButton();

        setClosable(true);
        setTitle("Meus dados cadastrais");
        setToolTipText("");

        jLabel1.setText("Código");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 255));
        jLabel2.setText("Nome");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 255));
        jLabel3.setText("Telefone");

        jtfCodigo.setEnabled(false);

        try {
            jffTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel11.setText("Situação");

        jLabel12.setText("CPF");

        jLabel13.setText("RG");

        jtfSituacao.setEnabled(false);

        try {
            jffCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel14.setText("Data de nascimento");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 255));
        jLabel20.setText("Sexo");

        try {
            jffNumeroCtps.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.#####.##-#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 255));
        jLabel15.setText("Número CTPS");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 255));
        jLabel16.setText("Data admissão");

        jdcDataDesligamento.setEnabled(false);

        jLabel17.setText("Data desligamento");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 255));
        jLabel19.setText("Cidade");

        jtfNomeCidade.setText("Pesquisar cidade...");

        jlbLookUpCidade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/search16.png"))); // NOI18N
        jlbLookUpCidade.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbLookUpCidadeMouseClicked(evt);
            }
        });

        jLabel10.setText("Complemento");

        jLabel21.setText("Bairro");

        jLabel9.setText("Logradouro");

        jLabel8.setText("Nº");

        try {
            jffCelular.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel4.setText("Celular");

        jLabel5.setText("E-mail");

        jLabel7.setText("Site");

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
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel19)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9)
                    .addComponent(jLabel21))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfBairro)
                    .addComponent(jtfComplemento)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jtfNomeCidade, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jlbLookUpCidade))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jffCelular, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                                .addComponent(jffTelefone, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addGap(0, 163, Short.MAX_VALUE))
                    .addComponent(jtfNome)
                    .addComponent(jtfEmail)
                    .addComponent(jtfSite)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jtfLogradouro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfNumeroEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel20)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jdcDataDesligamento, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jdcDataAdmissao, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jffNumeroCtps, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jdcDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfRg, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jffCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfCancelar)
                .addGap(174, 174, 174))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jcbSexo, jdcDataAdmissao, jdcDataDesligamento, jdcDataNascimento, jffCpf, jffNumeroCtps, jtfRg, jtfSituacao});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jbtConfirmar, jtfCancelar});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jtfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jffTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jffCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jtfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jtfSite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jtfLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jtfNumeroEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(jtfBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlbLookUpCidade, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel19)
                                .addComponent(jtfNomeCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jtfSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jffCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jtfRg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jdcDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(jcbSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jffNumeroCtps, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addComponent(jdcDataAdmissao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jdcDataDesligamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtConfirmar)
                    .addComponent(jtfCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jlbLookUpCidadeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbLookUpCidadeMouseClicked
        try {

            JDLookUpCidades lookUpCidades = new JDLookUpCidades(null, true);
            lookUpCidades.setVisible(true);

            if (lookUpCidades.getSelecaoConfirmada()) {
                this.professor.setCidade(lookUpCidades.getCidadeSelecionada());
                jtfNomeCidade.setText(this.professor.getCidade().getNome());
            }

        } catch (Exception ex) {
            Mensagem.erro(ex.getMessage());
        }
    }//GEN-LAST:event_jlbLookUpCidadeMouseClicked

    private void jbtConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtConfirmarActionPerformed
        try {
            salvarProfessor();
        } catch (Exception ex) {
            Mensagem.erro("Problema ao salvar professor: " + ex.getMessage());
        }
    }//GEN-LAST:event_jbtConfirmarActionPerformed

    private void jtfCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_jtfCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton jbtConfirmar;
    private javax.swing.JComboBox<String> jcbSexo;
    private com.toedter.calendar.JDateChooser jdcDataAdmissao;
    private com.toedter.calendar.JDateChooser jdcDataDesligamento;
    private com.toedter.calendar.JDateChooser jdcDataNascimento;
    private javax.swing.JFormattedTextField jffCelular;
    private javax.swing.JFormattedTextField jffCpf;
    private javax.swing.JFormattedTextField jffNumeroCtps;
    private javax.swing.JFormattedTextField jffTelefone;
    private javax.swing.JLabel jlbLookUpCidade;
    private javax.swing.JTextField jtfBairro;
    private javax.swing.JButton jtfCancelar;
    private javax.swing.JTextField jtfCodigo;
    private javax.swing.JTextField jtfComplemento;
    private javax.swing.JTextField jtfEmail;
    private javax.swing.JTextField jtfLogradouro;
    private javax.swing.JTextField jtfNome;
    private javax.swing.JTextField jtfNomeCidade;
    private javax.swing.JTextField jtfNumeroEndereco;
    private javax.swing.JTextField jtfRg;
    private javax.swing.JTextField jtfSite;
    private javax.swing.JTextField jtfSituacao;
    // End of variables declaration//GEN-END:variables
}
