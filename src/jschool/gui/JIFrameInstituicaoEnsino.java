/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.gui;

import java.util.ArrayList;
import jschool.dao.InstituicaoDAO;
import jschool.modelo.entidade.Instituicao;
import jschool.util.Formatador;
import jschool.infraestrutura.Mensagem;
import jschool.validacao.InstituicaoValidador;

/**
 *
 * @author andre
 */
public class JIFrameInstituicaoEnsino extends javax.swing.JInternalFrame {

    private Instituicao instituicao;
    
    /**
     * Creates new form JIFrameInstituicaoEnsino
     */
    public JIFrameInstituicaoEnsino() {
        initComponents();
        this.instituicao = new Instituicao();
        carregarInstituicao();
    }

    private void carregarInstituicao() {
        
        instituicao = (Instituicao) new InstituicaoDAO().consultarId(0);
        if (instituicao != null) {
            jtfId.setText(Integer.toString(instituicao.getId()));
            jtfNome.setText(instituicao.getNome());
            jtfCnpj.setText(instituicao.getCnpj());
            jtfIe.setText(instituicao.getInscricaoEstadual());
            jtfTelefone.setText(instituicao.getTelefone());
            jtfEmail.setText(instituicao.getEmail());
            jtfSite.setText(instituicao.getSite());
            jtfLogradouro.setText(instituicao.getLogradouro());
            jtfNumero.setText(instituicao.getNumero());
            jtfBairro.setText(instituicao.getBairro());
            jtfComplemento.setText(instituicao.getComplemento());
            if (instituicao.getCidade() != null) {
                jtfNomeCidade.setText(instituicao.getCidade().getNome());
            }
        } else {
            Mensagem.erro("Problema ao carregar instituição de ensino");
            jbtConfirmar.setEnabled(false);
        }
    }

    private void salvarInstituicao() {
        
        instituicao.setId(Integer.parseInt(jtfId.getText()));
        instituicao.setNome(jtfNome.getText());
        instituicao.setCnpj(Formatador.removerPontuacaoCnpj(jtfCnpj.getText()));
        instituicao.setInscricaoEstadual(jtfIe.getText());
        instituicao.setTelefone(Formatador.removerPontuacaoTelefone(jtfTelefone.getText()));
        instituicao.setEmail(jtfEmail.getText());
        instituicao.setSite(jtfSite.getText());
        instituicao.setLogradouro(jtfLogradouro.getText());
        instituicao.setNumero(jtfNumero.getText());
        instituicao.setComplemento(jtfComplemento.getText());
        instituicao.setBairro(jtfBairro.getText());

        ArrayList<String> inconsistencias = new InstituicaoValidador().validarAlteracoes(instituicao);
        if (inconsistencias.size() > 0) {
            Mensagem.frameInconsistencias(inconsistencias);
        } else {
            boolean resultado = new InstituicaoDAO().salvar(instituicao);
            if (resultado) {
                Mensagem.sucesso("Alterações registradas com sucesso");
            } else {
                Mensagem.erro("Problema ao registrar alterações");
            }

            this.dispose();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jtfNome = new javax.swing.JTextField();
        jtfIe = new javax.swing.JTextField();
        jtfEmail = new javax.swing.JTextField();
        jtfSite = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jtfLogradouro = new javax.swing.JTextField();
        jtfNumero = new javax.swing.JTextField();
        jtfBairro = new javax.swing.JTextField();
        jtfComplemento = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jbtConfirmar = new javax.swing.JButton();
        jbtCancelar = new javax.swing.JButton();
        jtfCnpj = new javax.swing.JFormattedTextField();
        jtfTelefone = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        jtfId = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jtfNomeCidade = new javax.swing.JTextField();
        jlbLookUpCidade = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        setClosable(true);
        setTitle("Instituição de Ensino");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("Nome");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 204));
        jLabel2.setText("CNPJ");

        jLabel3.setText("IE");

        jLabel4.setText("Telefone");

        jLabel5.setText("Email");

        jLabel6.setText("Site");

        jLabel7.setText("Logradouro");

        jLabel8.setText("Número");

        jLabel9.setText("Bairro");

        jLabel10.setText("Complemento");

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

        try {
            jtfCnpj.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###/####-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            jtfTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel12.setText("Código");

        jtfId.setEditable(false);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jtfNomeCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jlbLookUpCidade))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel1)
                            .addComponent(jLabel12)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfEmail)
                            .addComponent(jtfSite)
                            .addComponent(jtfLogradouro)
                            .addComponent(jtfBairro)
                            .addComponent(jtfComplemento)
                            .addComponent(jtfNome)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtfTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtfId, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 142, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbtConfirmar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtCancelar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtfCnpj, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                    .addComponent(jtfIe, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                    .addComponent(jtfNumero))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jbtCancelar, jbtConfirmar});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jtfCnpj, jtfIe});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jtfId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jtfCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jtfIe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jtfTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jtfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jtfSite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel8)
                        .addComponent(jtfNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jtfLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jtfBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19)
                        .addComponent(jtfNomeCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jlbLookUpCidade, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtConfirmar)
                    .addComponent(jbtCancelar))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jbtCancelar, jbtConfirmar});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbtCancelarActionPerformed

    private void jbtConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtConfirmarActionPerformed
        try {
            salvarInstituicao();
        } catch (Exception ex) {
            Mensagem.erro(ex.getMessage());
        }
    }//GEN-LAST:event_jbtConfirmarActionPerformed

    private void jlbLookUpCidadeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbLookUpCidadeMouseClicked
        
        try{

            JDLookUpCidades lookUpCidades = new JDLookUpCidades(null, true);
            lookUpCidades.setVisible(true);

            if(lookUpCidades.getSelecaoConfirmada()){
                this.instituicao.setCidade(lookUpCidades.getCidadeSelecionada());
                jtfNomeCidade.setText(this.instituicao.getCidade().getNome());
            }

        } catch(Exception ex){
            Mensagem.erro(ex.getMessage());
        }
    }//GEN-LAST:event_jlbLookUpCidadeMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton jbtCancelar;
    private javax.swing.JButton jbtConfirmar;
    private javax.swing.JLabel jlbLookUpCidade;
    private javax.swing.JTextField jtfBairro;
    private javax.swing.JFormattedTextField jtfCnpj;
    private javax.swing.JTextField jtfComplemento;
    private javax.swing.JTextField jtfEmail;
    private javax.swing.JTextField jtfId;
    private javax.swing.JTextField jtfIe;
    private javax.swing.JTextField jtfLogradouro;
    private javax.swing.JTextField jtfNome;
    private javax.swing.JTextField jtfNomeCidade;
    private javax.swing.JTextField jtfNumero;
    private javax.swing.JTextField jtfSite;
    private javax.swing.JFormattedTextField jtfTelefone;
    // End of variables declaration//GEN-END:variables
}
