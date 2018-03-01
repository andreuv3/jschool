package jschool.gui;

import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import jschool.ajudantes.ComboBoxAjudante;
import jschool.dao.ProfessorDAO;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.entidade.Professor;
import jschool.enumeracoes.Sexo;
import jschool.util.Formatador;
import jschool.infraestrutura.Mensagem;
import jschool.modelo.tabela.ProfessorTM;
import jschool.validacao.ProfessorValidador;

/**
 *
 * @author andre
 */
public class JIFrameProfessores extends javax.swing.JInternalFrame {

    private Professor professor;
    private ArrayList<IEntidade> professores;

    public JIFrameProfessores() {
        initComponents();
        ComboBoxAjudante.preencherComboBoxSexo(jcbSexo);
        aplicarFiltros();
        
        this.professor = new Professor();
        this.professores = new ArrayList();
    }
    
    private void carregarSexo() {
        
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        modelo.addElement("(selecione)");
        modelo.addElement(Sexo.MASCULINO);
        modelo.addElement(Sexo.FEMININO);

        jcbSexo.setModel(modelo);
    }

    private void aplicarFiltros() {
        ProfessorTM tabelaProfessores = new ProfessorTM();

        if (jtfNomeConsulta.getText().trim().isEmpty()) {
            this.professores = new ProfessorDAO().consultarTodos(false);
        } else {
            this.professores = new ProfessorDAO().consultar(jtfNomeConsulta.getText(), false);
        }

        if (this.professores != null) {
            tabelaProfessores.addListaProfessores(this.professores);
        }

        jtProfessores.setModel(tabelaProfessores);
    }

    private void limparCampos() {
        jtfCodigo.setText("");
        jtfNome.setText("");
        jffTelefone.setText("");
        jffCelular.setText("");
        jtfEmail.setText("");
        jtfSite.setText("");
        jtfLogradouro.setText("");
        jtfNumeroEndereco.setText("");
        jtfBairro.setText("");
        jtfComplemento.setText("");
        jtfSituacao.setText("");
        jtfRg.setText("");
        jffCpf.setText("");
        jdcDataNascimento.setDate(null);
        jdcDataAdmissao.setDate(null);
        jdcDataDesligamento.setDate(null);
        jffNumeroCtps.setText("");
        jtfNomeCidade.setText("Pesquisar cidade...");
        ComboBoxAjudante.preencherComboBoxSexo(jcbSexo);
        //carregarSexo();
    }

    private void salvarProfessor() {

        ArrayList<String> inconsistencias = new ArrayList();
        boolean resultado;
        
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
        if(!jcbSexo.getModel().getSelectedItem().equals("(selecione)")){
            this.professor.setSexo((Sexo) jcbSexo.getModel().getSelectedItem());
        }
        
        if (jtfCodigo.getText().trim().isEmpty()) {
            inconsistencias = new ProfessorValidador().validarCadastro(professor);
            if (inconsistencias.size() > 0) {
                Mensagem.frameInconsistencias(inconsistencias);
            } else {
                resultado = new ProfessorDAO().salvar(professor);
                if (resultado) {
                    Mensagem.sucesso("Dados registrados com sucesso!");
                    limparCampos();
                    jtpProfessores.setSelectedComponent(jpConsulta);
                    aplicarFiltros();
                } else {
                    Mensagem.erro("Problema ao salvar professor.");
                }
            }
        } else {
            this.professor.setId(Integer.parseInt(jtfCodigo.getText()));

            inconsistencias = new ProfessorValidador().validarAlteracoes(professor);
            if (inconsistencias.size() > 0) {
                Mensagem.frameInconsistencias(inconsistencias);
            } else {
                resultado = new ProfessorDAO().atualizar(professor);
                if (resultado) {
                    Mensagem.sucesso("Dados registrados com sucesso!");
                    limparCampos();
                    jtpProfessores.setSelectedComponent(jpConsulta);
                    aplicarFiltros();
                } else {
                    Mensagem.erro("Problema ao salvar professor.");
                }
            }
        }
    }
    
    private void alterarProfessor(){
        
        if (jtProfessores.getSelectedRow() == -1) {
            Mensagem.aviso("Selecione um professor");
            return;
        }

        int idProfessor = Integer.parseInt(jtProfessores.getModel().getValueAt(jtProfessores.getSelectedRow(), 0).toString());
        this.professor = (Professor) new ProfessorDAO().consultarId(idProfessor);
        if (this.professor == null) {
            Mensagem.erro("Problema ao recuperar professor");
            return;
        }

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

        jtpProfessores.setSelectedComponent(jpDadosCadastrais);
    }
    
    private void inativarProfessor(){
        if (jtProfessores.getSelectedRow() == -1) {
            Mensagem.aviso("Selecione um professor");
            return;
        }

        int idProfessor = Integer.parseInt(jtProfessores.getModel().getValueAt(jtProfessores.getSelectedRow(), 0).toString());
        this.professor = (Professor) new ProfessorDAO().consultarId(idProfessor);
        if (professor == null) {
            Mensagem.erro("Problema ao recuperar professor");
            return;
        }

        boolean resultado = new ProfessorDAO().alterarSituacao(this.professor);
        if (resultado) {
            Mensagem.sucesso("Situação do professor alterada com sucesso");
            aplicarFiltros();
        } else {
            Mensagem.erro("Não foi possível alterar a situação do professor");
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField2 = new javax.swing.JTextField();
        jtpProfessores = new javax.swing.JTabbedPane();
        jpConsulta = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jtfNomeConsulta = new javax.swing.JTextField();
        jbtPesquisar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtProfessores = new javax.swing.JTable();
        jbtIncluir = new javax.swing.JButton();
        jbtAlterar = new javax.swing.JButton();
        jbtInativar = new javax.swing.JButton();
        jbtSair = new javax.swing.JButton();
        jpDadosCadastrais = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jtfCodigo = new javax.swing.JTextField();
        jtfNome = new javax.swing.JTextField();
        jtfEmail = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jtfSite = new javax.swing.JTextField();
        jffTelefone = new javax.swing.JFormattedTextField();
        jffCelular = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jtfNumeroEndereco = new javax.swing.JTextField();
        jtfLogradouro = new javax.swing.JTextField();
        jtfComplemento = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jtfSituacao = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jffCpf = new javax.swing.JFormattedTextField();
        jtfRg = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jffNumeroCtps = new javax.swing.JFormattedTextField();
        jLabel20 = new javax.swing.JLabel();
        jcbSexo = new javax.swing.JComboBox<>();
        jbtConfirmar = new javax.swing.JButton();
        jtfCancelar = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jtfBairro = new javax.swing.JTextField();
        jdcDataNascimento = new com.toedter.calendar.JDateChooser();
        jdcDataAdmissao = new com.toedter.calendar.JDateChooser();
        jdcDataDesligamento = new com.toedter.calendar.JDateChooser();
        jtfNomeCidade = new javax.swing.JTextField();
        jlbLookUpCidade = new javax.swing.JLabel();

        jTextField2.setText("jTextField2");

        setClosable(true);
        setTitle("Professores");
        setMaximumSize(new java.awt.Dimension(857, 549));
        setMinimumSize(new java.awt.Dimension(857, 549));
        setPreferredSize(new java.awt.Dimension(857, 549));

        jtpProfessores.setEnabled(false);

        jpConsulta.setMaximumSize(new java.awt.Dimension(843, 463));
        jpConsulta.setMinimumSize(new java.awt.Dimension(763, 463));
        jpConsulta.setPreferredSize(new java.awt.Dimension(763, 463));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros"));

        jLabel6.setText("Nome");

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jtfNomeConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 3, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfNomeConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jbtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jtProfessores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtProfessores.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jtProfessores);

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
                        .addGap(0, 0, Short.MAX_VALUE)
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

        jLabel1.setText("Código");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 255));
        jLabel2.setText("Nome");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 255));
        jLabel3.setText("Telefone");

        jLabel4.setText("Celular");

        jLabel5.setText("E-mail");

        jtfCodigo.setEnabled(false);

        jLabel7.setText("Site");

        try {
            jffTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            jffCelular.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel8.setText("Nº");

        jLabel9.setText("Logradouro");

        jLabel10.setText("Complemento");

        jLabel11.setText("Situação");

        jtfSituacao.setEnabled(false);

        jLabel12.setText("CPF");

        jLabel13.setText("RG");

        try {
            jffCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel14.setText("Data de nascimento");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 255));
        jLabel15.setText("Número CTPS");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 255));
        jLabel16.setText("Data admissão");

        jLabel17.setText("Data desligamento");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 255));
        jLabel19.setText("Cidade");

        try {
            jffNumeroCtps.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.#####.##-#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 255));
        jLabel20.setText("Sexo");

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

        jLabel21.setText("Bairro");

        jdcDataDesligamento.setEnabled(false);

        jtfNomeCidade.setText("Pesquisar cidade...");

        jlbLookUpCidade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/search16.png"))); // NOI18N
        jlbLookUpCidade.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbLookUpCidadeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jpDadosCadastraisLayout = new javax.swing.GroupLayout(jpDadosCadastrais);
        jpDadosCadastrais.setLayout(jpDadosCadastraisLayout);
        jpDadosCadastraisLayout.setHorizontalGroup(
            jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDadosCadastraisLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfBairro)
                    .addComponent(jtfComplemento)
                    .addGroup(jpDadosCadastraisLayout.createSequentialGroup()
                        .addComponent(jtfNomeCidade, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jlbLookUpCidade))
                    .addGroup(jpDadosCadastraisLayout.createSequentialGroup()
                        .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jffCelular, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                                .addComponent(jffTelefone, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addGap(0, 250, Short.MAX_VALUE))
                    .addComponent(jtfNome)
                    .addComponent(jtfEmail)
                    .addComponent(jtfSite)
                    .addGroup(jpDadosCadastraisLayout.createSequentialGroup()
                        .addComponent(jtfLogradouro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfNumeroEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel20)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17))
                .addGap(12, 12, 12)
                .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jffCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfRg)
                    .addComponent(jdcDataNascimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jcbSexo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jffNumeroCtps)
                    .addComponent(jdcDataAdmissao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jdcDataDesligamento, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpDadosCadastraisLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfCancelar)
                .addGap(239, 239, 239))
        );

        jpDadosCadastraisLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jcbSexo, jdcDataAdmissao, jdcDataDesligamento, jdcDataNascimento, jffCpf, jffNumeroCtps, jtfRg, jtfSituacao});

        jpDadosCadastraisLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jbtConfirmar, jtfCancelar});

        jpDadosCadastraisLayout.setVerticalGroup(
            jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDadosCadastraisLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDadosCadastraisLayout.createSequentialGroup()
                        .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jtfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jffTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jffCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jtfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jtfSite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jtfLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jtfNumeroEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addGap(4, 4, 4)
                        .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(jtfBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(4, 4, 4)
                        .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlbLookUpCidade, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel19)
                                .addComponent(jtfNomeCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jpDadosCadastraisLayout.createSequentialGroup()
                        .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jtfSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jffCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jtfRg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jdcDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(jcbSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jffNumeroCtps, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addComponent(jdcDataAdmissao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jdcDataDesligamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtConfirmar)
                    .addComponent(jtfCancelar))
                .addContainerGap(185, Short.MAX_VALUE))
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
        jtpProfessores.setSelectedComponent(jpDadosCadastrais);
        jtfNome.requestFocus();
    }//GEN-LAST:event_jbtIncluirActionPerformed

    private void jbtAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtAlterarActionPerformed
        try{
            alterarProfessor();
        } catch(Exception ex){
            Mensagem.erro(ex.getMessage());
        }        
    }//GEN-LAST:event_jbtAlterarActionPerformed

    private void jbtInativarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtInativarActionPerformed
        try {
            inativarProfessor();
        } catch (Exception ex) {
            Mensagem.erro("Problema alterar a situação do professor: " + ex.getMessage());
        }
    }//GEN-LAST:event_jbtInativarActionPerformed

    private void jbtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtSairActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbtSairActionPerformed

    private void jbtConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtConfirmarActionPerformed
        try {
            salvarProfessor();
        } catch (Exception ex) {
            Mensagem.erro("Problema ao salvar professor: " + ex.getMessage());
        }
    }//GEN-LAST:event_jbtConfirmarActionPerformed

    private void jtfCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfCancelarActionPerformed
        limparCampos();
        jtpProfessores.setSelectedComponent(jpConsulta);
    }//GEN-LAST:event_jtfCancelarActionPerformed

    private void jlbLookUpCidadeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbLookUpCidadeMouseClicked
        try{

            JDLookUpCidades lookUpCidades = new JDLookUpCidades(null, true);
            lookUpCidades.setVisible(true);
            
            if(lookUpCidades.getSelecaoConfirmada()){
                this.professor.setCidade(lookUpCidades.getCidadeSelecionada());
                jtfNomeCidade.setText(this.professor.getCidade().getNome());
            }
            
        } catch(Exception ex){
            Mensagem.erro(ex.getMessage());
        }
    }//GEN-LAST:event_jlbLookUpCidadeMouseClicked

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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JButton jbtAlterar;
    private javax.swing.JButton jbtConfirmar;
    private javax.swing.JButton jbtInativar;
    private javax.swing.JButton jbtIncluir;
    private javax.swing.JButton jbtPesquisar;
    private javax.swing.JButton jbtSair;
    private javax.swing.JComboBox<String> jcbSexo;
    private com.toedter.calendar.JDateChooser jdcDataAdmissao;
    private com.toedter.calendar.JDateChooser jdcDataDesligamento;
    private com.toedter.calendar.JDateChooser jdcDataNascimento;
    private javax.swing.JFormattedTextField jffCelular;
    private javax.swing.JFormattedTextField jffCpf;
    private javax.swing.JFormattedTextField jffNumeroCtps;
    private javax.swing.JFormattedTextField jffTelefone;
    private javax.swing.JLabel jlbLookUpCidade;
    private javax.swing.JPanel jpConsulta;
    private javax.swing.JPanel jpDadosCadastrais;
    private javax.swing.JTable jtProfessores;
    private javax.swing.JTextField jtfBairro;
    private javax.swing.JButton jtfCancelar;
    private javax.swing.JTextField jtfCodigo;
    private javax.swing.JTextField jtfComplemento;
    private javax.swing.JTextField jtfEmail;
    private javax.swing.JTextField jtfLogradouro;
    private javax.swing.JTextField jtfNome;
    private javax.swing.JTextField jtfNomeCidade;
    private javax.swing.JTextField jtfNomeConsulta;
    private javax.swing.JTextField jtfNumeroEndereco;
    private javax.swing.JTextField jtfRg;
    private javax.swing.JTextField jtfSite;
    private javax.swing.JTextField jtfSituacao;
    private javax.swing.JTabbedPane jtpProfessores;
    // End of variables declaration//GEN-END:variables
}
