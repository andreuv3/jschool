/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.gui;

import java.util.ArrayList;
import java.util.Date;
import jschool.dao.AlunoDAO;
import jschool.dao.InscricaoTurmaDAO;
import jschool.enumeracoes.TipoUsuario;
import jschool.infraestrutura.Ambiente;
import jschool.infraestrutura.Mensagem;
import jschool.modelo.entidade.Aluno;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.entidade.InscricaoTurma;
import jschool.modelo.tabela.InscricaoTurmaTM;
import jschool.validacao.InscricaoTurmaValidador;

/**
 *
 * @author andre
 */
public class JIFrameMatriculas extends javax.swing.JInternalFrame {

    private ArrayList<IEntidade> inscricoes;
    private InscricaoTurma inscricao;

    /**
     * Creates new form JIFrameMatriculas
     */
    public JIFrameMatriculas() {
        initComponents();
        this.inscricao = new InscricaoTurma();
        this.inscricoes = new ArrayList();

        aplicarFiltros();
        validarPermissoes();
    }

    private void validarPermissoes() {
        if (Ambiente.getTipoUsuarioLogado() != TipoUsuario.ADMINISTRADOR) {
            this.jbtInativar.setEnabled(false);
        }
    }

    private void aplicarFiltros() {

        InscricaoTurmaDAO inscricaoDao = new InscricaoTurmaDAO();
        InscricaoTurmaTM inscricaoTableModel = new InscricaoTurmaTM();

        if (Ambiente.getTipoUsuarioLogado() == TipoUsuario.ALUNO) {
            this.inscricoes = inscricaoDao.consultar((Aluno) new AlunoDAO().consultarId(Ambiente.getUsuarioLogado().getPessoa().getId()));
        } else {
            if (!"".equals(jtfAlunoPesquisa.getText()) || !"".equals(jtfCursoPesquisa.getText())) {
                this.inscricoes = inscricaoDao.pesquisar(jtfAlunoPesquisa.getText(), jtfCursoPesquisa.getText());
            } else {
                this.inscricoes = inscricaoDao.consultarTodos();
            }
        }
        
        if (this.inscricoes != null) {
                inscricaoTableModel.addListaInscricaoTurma(this.inscricoes);
            }

        jtInscricoes.setModel(inscricaoTableModel);
    }

    private void limparCampos() {
        jtfCodigo.setText("");
        jtfSituacaoMatricula.setText("");
        jtfAluno.setText("Procure o aluno...");
        jtfTurma.setText("Procure a turma...");
        jtfSituacaoAluno.setText("");
        jdcDataInscricao.setDate(null);
        jbtConfirmar.setEnabled(true);
        this.inscricao = new InscricaoTurma();
    }

    private void alterarMatricula() {

        if (jtInscricoes.getSelectedRow() == -1) {
            Mensagem.aviso("Selecione uma inscrição");
            return;
        }

        int idInscricao = Integer.parseInt(jtInscricoes.getModel().getValueAt(jtInscricoes.getSelectedRow(), 0).toString());

        this.inscricao = (InscricaoTurma) new InscricaoTurmaDAO().consultarId(idInscricao);
        if (this.inscricao == null) {
            Mensagem.erro("Problema ao recuperar inscrição");
            return;
        }

        jtfCodigo.setText(String.valueOf(this.inscricao.getId()));
        jtfSituacaoMatricula.setText(this.inscricao.getStatus().getDescricaoSituacao());
        jtfAluno.setText(this.inscricao.getAluno().getNome());
        jtfTurma.setText(this.inscricao.getTurma().getCurso().getNome() + " - " + this.inscricao.getTurma().getProfessor().getNome());
        jtfSituacaoAluno.setText(this.inscricao.getSituacaoAluno().getDescricaoSituacao());
        jdcDataInscricao.setDate(this.inscricao.getDataInscricao());

        jbtConfirmar.setEnabled(false);
        jtpProfessores.setSelectedComponent(jpDadosCadastrais);

    }

    private void salvarMatricula() {

        ArrayList<String> inconsistencias = new ArrayList();
        boolean sucesso;

        InscricaoTurmaValidador validador = new InscricaoTurmaValidador();

        if (this.inscricao.getId() <= 0) {
            inconsistencias = validador.validarCadastro(inscricao);
        } else {
            inconsistencias = validador.validarAlteracoes(inscricao);
        }

        if (inconsistencias.size() > 0) {
            Mensagem.frameInconsistencias(inconsistencias);
            return;
        }

        if (this.inscricao.getId() <= 0) {
            sucesso = new InscricaoTurmaDAO().salvar(inscricao);
        } else {
            sucesso = new InscricaoTurmaDAO().atualizar(inscricao);
        }

        if (sucesso) {
            Mensagem.sucesso("Dados registrados com sucesso");
            limparCampos();
            aplicarFiltros();
            jtpProfessores.setSelectedComponent(jpConsulta);
        } else {
            Mensagem.erro("Problema ao registrar dados");
        }
    }

    private void alterarStatusMatricula() {

        if (jtInscricoes.getSelectedRow() == -1) {
            Mensagem.aviso("Selecione uma inscrição");
            return;
        }

        int idInscricao = Integer.parseInt(jtInscricoes.getModel().getValueAt(jtInscricoes.getSelectedRow(), 0).toString());

        this.inscricao = (InscricaoTurma) new InscricaoTurmaDAO().consultarId(idInscricao);
        if (this.inscricao == null) {
            Mensagem.erro("Problema ao recuperar inscrição");
            return;
        }

        boolean sucesso = new InscricaoTurmaDAO().alterarSituacao(this.inscricao);
        if (sucesso) {
            Mensagem.sucesso("Situação alterada com sucesso");
        } else {
            Mensagem.erro("Problema ao alterar situação");
        }

        limparCampos();
        aplicarFiltros();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtpProfessores = new javax.swing.JTabbedPane();
        jpConsulta = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jtfAlunoPesquisa = new javax.swing.JTextField();
        jbtPesquisar = new javax.swing.JButton();
        jtfCursoPesquisa = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtInscricoes = new javax.swing.JTable();
        jbtIncluir = new javax.swing.JButton();
        jbtAlterar = new javax.swing.JButton();
        jbtInativar = new javax.swing.JButton();
        jbtSair = new javax.swing.JButton();
        jpDadosCadastrais = new javax.swing.JPanel();
        jbtConfirmar = new javax.swing.JButton();
        jtfCancelar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jtfCodigo = new javax.swing.JTextField();
        jdcDataInscricao = new com.toedter.calendar.JDateChooser();
        jtfSituacaoAluno = new javax.swing.JTextField();
        jtfTurma = new javax.swing.JTextField();
        jtfAluno = new javax.swing.JTextField();
        jtfSituacaoMatricula = new javax.swing.JTextField();
        jlbPesquisarAluno = new javax.swing.JLabel();
        jlbPesquisarTurma = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Matrículas");

        jtpProfessores.setEnabled(false);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros"));

        jLabel6.setText("Aluno");

        jbtPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/search16.png"))); // NOI18N
        jbtPesquisar.setText("Pesquisar");
        jbtPesquisar.setMaximumSize(new java.awt.Dimension(51, 23));
        jbtPesquisar.setMinimumSize(new java.awt.Dimension(51, 23));
        jbtPesquisar.setPreferredSize(new java.awt.Dimension(51, 23));
        jbtPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtPesquisarActionPerformed(evt);
            }
        });

        jLabel1.setText("Curso");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jtfAlunoPesquisa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfCursoPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 3, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfAlunoPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jbtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfCursoPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)))
        );

        jtInscricoes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtInscricoes.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jtInscricoes);

        jbtIncluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/add24.png"))); // NOI18N
        jbtIncluir.setText("Incluir");
        jbtIncluir.setIconTextGap(6);
        jbtIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtIncluirActionPerformed(evt);
            }
        });

        jbtAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/edit24.png"))); // NOI18N
        jbtAlterar.setText("Alterar/Visualizar");
        jbtAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtAlterarActionPerformed(evt);
            }
        });

        jbtInativar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/switch24.png"))); // NOI18N
        jbtInativar.setText("Alterar Situação");
        jbtInativar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtInativarActionPerformed(evt);
            }
        });

        jbtSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/delete24.png"))); // NOI18N
        jbtSair.setText("Sair");
        jbtSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtSairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpConsultaLayout = new javax.swing.GroupLayout(jpConsulta);
        jpConsulta.setLayout(jpConsultaLayout);
        jpConsultaLayout.setHorizontalGroup(
            jpConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jpConsultaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpConsultaLayout.createSequentialGroup()
                        .addGap(0, 252, Short.MAX_VALUE)
                        .addComponent(jbtIncluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtAlterar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtInativar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtSair)))
                .addContainerGap())
        );

        jpConsultaLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jbtAlterar, jbtInativar, jbtIncluir, jbtSair});

        jpConsultaLayout.setVerticalGroup(
            jpConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpConsultaLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtSair)
                    .addComponent(jbtInativar)
                    .addComponent(jbtAlterar)
                    .addComponent(jbtIncluir))
                .addContainerGap())
        );

        jtpProfessores.addTab("Consulta", jpConsulta);

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

        jLabel2.setText("Código");

        jLabel3.setText("Situação");

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 255));
        jLabel4.setText("Aluno");

        jLabel5.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 255));
        jLabel5.setText("Turma");

        jLabel7.setText("Situação aluno");

        jLabel8.setText("Data inscrição");

        jtfCodigo.setEnabled(false);

        jdcDataInscricao.setEnabled(false);

        jtfSituacaoAluno.setEnabled(false);

        jtfTurma.setText("Procure a turma...");
        jtfTurma.setEnabled(false);

        jtfAluno.setText("Procure o aluno...");
        jtfAluno.setEnabled(false);

        jtfSituacaoMatricula.setEnabled(false);

        jlbPesquisarAluno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/search16.png"))); // NOI18N
        jlbPesquisarAluno.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbPesquisarAlunoMouseClicked(evt);
            }
        });

        jlbPesquisarTurma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/search16.png"))); // NOI18N
        jlbPesquisarTurma.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbPesquisarTurmaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jpDadosCadastraisLayout = new javax.swing.GroupLayout(jpDadosCadastrais);
        jpDadosCadastrais.setLayout(jpDadosCadastraisLayout);
        jpDadosCadastraisLayout.setHorizontalGroup(
            jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpDadosCadastraisLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDadosCadastraisLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfSituacaoAluno)
                            .addComponent(jtfSituacaoMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfTurma, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlbPesquisarAluno)
                            .addComponent(jlbPesquisarTurma)))
                    .addGroup(jpDadosCadastraisLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jdcDataInscricao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpDadosCadastraisLayout.createSequentialGroup()
                .addContainerGap(303, Short.MAX_VALUE)
                .addComponent(jbtConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfCancelar)
                .addGap(269, 269, 269))
        );

        jpDadosCadastraisLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jbtConfirmar, jtfCancelar});

        jpDadosCadastraisLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jdcDataInscricao, jtfCodigo, jtfSituacaoAluno, jtfSituacaoMatricula});

        jpDadosCadastraisLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jtfAluno, jtfTurma});

        jpDadosCadastraisLayout.setVerticalGroup(
            jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDadosCadastraisLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtfSituacaoMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jtfAluno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jlbPesquisarAluno))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlbPesquisarTurma, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(jtfTurma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfSituacaoAluno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                    .addComponent(jdcDataInscricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 245, Short.MAX_VALUE)
                .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtConfirmar)
                    .addComponent(jtfCancelar))
                .addContainerGap())
        );

        jtpProfessores.addTab("Dados Cadastrais", jpDadosCadastrais);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpProfessores)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpProfessores, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtPesquisarActionPerformed
        aplicarFiltros();
    }//GEN-LAST:event_jbtPesquisarActionPerformed

    private void jbtIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtIncluirActionPerformed
        try {
            this.inscricao = new InscricaoTurma();
            jtfSituacaoMatricula.setText("Ativa");
            jtfSituacaoAluno.setText("Cursando");
            jdcDataInscricao.setDate(new Date());

            if (Ambiente.getTipoUsuarioLogado() == TipoUsuario.ALUNO) {
                this.inscricao.setAluno((Aluno) new AlunoDAO().consultarId(Ambiente.getUsuarioLogado().getPessoa().getId()));
                this.jtfAluno.setText(this.inscricao.getAluno().getNome());
                this.jlbPesquisarAluno.setEnabled(false);
                this.jlbPesquisarAluno.setVisible(false);
            }

            jtpProfessores.setSelectedComponent(jpDadosCadastrais);
        } catch (Exception e) {
            Mensagem.erro("Problema ao incluir matrícula");
        }
    }//GEN-LAST:event_jbtIncluirActionPerformed

    private void jbtInativarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtInativarActionPerformed
        try {
            alterarStatusMatricula();
        } catch (Exception ex) {
            Mensagem.erro("Problema alterar a situação do professor: " + ex.getMessage());
        }
    }//GEN-LAST:event_jbtInativarActionPerformed

    private void jbtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtSairActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbtSairActionPerformed

    private void jbtConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtConfirmarActionPerformed
        try {
            salvarMatricula();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Mensagem.erro("Problema ao salvar inscrição");
        }
    }//GEN-LAST:event_jbtConfirmarActionPerformed

    private void jtfCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfCancelarActionPerformed
        limparCampos();
        jtpProfessores.setSelectedComponent(jpConsulta);
    }//GEN-LAST:event_jtfCancelarActionPerformed

    private void jlbPesquisarTurmaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbPesquisarTurmaMouseClicked
        try {
            JDLookUpTurmas lookUpTurmas = new JDLookUpTurmas(null, true);
            lookUpTurmas.setVisible(true);

            if (lookUpTurmas.getSelecaoConfirmada()) {
                this.inscricao.setTurma(lookUpTurmas.getTurmaSelecionada());
                jtfTurma.setText(this.inscricao.getTurma().getCurso().getNome() + " - " + this.inscricao.getTurma().getProfessor().getNome());
            }

        } catch (Exception e) {
            Mensagem.erro("Problema ao recuperar turma");
        }
    }//GEN-LAST:event_jlbPesquisarTurmaMouseClicked

    private void jlbPesquisarAlunoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbPesquisarAlunoMouseClicked
        try {

            JDLookUpAlunos lookUpAlunos = new JDLookUpAlunos(null, true);
            lookUpAlunos.setVisible(true);

            if (lookUpAlunos.getSelecaoConfirmada()) {
                this.inscricao.setAluno(lookUpAlunos.getAlunoSelecionado());
                jtfAluno.setText(this.inscricao.getAluno().getNome());
            }

        } catch (Exception e) {
            Mensagem.erro("Problema ao recuperar aluno");
        }
    }//GEN-LAST:event_jlbPesquisarAlunoMouseClicked

    private void jbtAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtAlterarActionPerformed
        try {
            alterarMatricula();
        } catch (Exception ex) {
            Mensagem.erro(ex.getMessage());
        }
    }//GEN-LAST:event_jbtAlterarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtAlterar;
    private javax.swing.JButton jbtConfirmar;
    private javax.swing.JButton jbtInativar;
    private javax.swing.JButton jbtIncluir;
    private javax.swing.JButton jbtPesquisar;
    private javax.swing.JButton jbtSair;
    private com.toedter.calendar.JDateChooser jdcDataInscricao;
    private javax.swing.JLabel jlbPesquisarAluno;
    private javax.swing.JLabel jlbPesquisarTurma;
    private javax.swing.JPanel jpConsulta;
    private javax.swing.JPanel jpDadosCadastrais;
    private javax.swing.JTable jtInscricoes;
    private javax.swing.JTextField jtfAluno;
    private javax.swing.JTextField jtfAlunoPesquisa;
    private javax.swing.JButton jtfCancelar;
    private javax.swing.JTextField jtfCodigo;
    private javax.swing.JTextField jtfCursoPesquisa;
    private javax.swing.JTextField jtfSituacaoAluno;
    private javax.swing.JTextField jtfSituacaoMatricula;
    private javax.swing.JTextField jtfTurma;
    private javax.swing.JTabbedPane jtpProfessores;
    // End of variables declaration//GEN-END:variables
}
