package jschool.gui;

import java.util.ArrayList;
import jschool.ajudantes.ComboBoxAjudante;
import jschool.dao.UsuarioDAO;
import jschool.enumeracoes.TipoUsuario;
import jschool.infraestrutura.Mensagem;
import jschool.modelo.entidade.Usuario;
import jschool.modelo.tabela.UsuarioTM;
import jschool.validacao.UsuarioValidador;

/**
 *
 * @author andre
 */
public class JIFrameUsuarios extends javax.swing.JInternalFrame {

    private Usuario usuario;
    
    public JIFrameUsuarios() {
        initComponents();
        ComboBoxAjudante.preencherComboBoxTipoUsuario(jcbTipo);
        aplicarFiltros();
        
        this.usuario = new Usuario();
    }

    private void limparCampos() {
        jtfCodigo.setText("");
        jtfNome.setText("");
        jpfSenha.setText("");
        jpfConfirmacaoSenha.setText("");
        jtfPessoa.setText("");
        ComboBoxAjudante.preencherComboBoxTipoUsuario(jcbTipo);
        jcbTipo.setEnabled(true);
        this.usuario = new Usuario();
    }

    private void carregarUsuario() {
        if (jtUsuarios.getSelectedRow() == -1) {
            Mensagem.aviso("Selecione um usuário");
            return;
        }

        int idUsuario = Integer.parseInt(jtUsuarios.getModel().getValueAt(jtUsuarios.getSelectedRow(), 0).toString());
        this.usuario = (Usuario) new UsuarioDAO().consultarId(idUsuario);
        if (this.usuario == null) {
            Mensagem.erro("Problema ao recuperar usuario");
            return;
        }
        
        jtfCodigo.setText(String.valueOf(this.usuario.getId()));
        jtfNome.setText(this.usuario.getNome());
        jcbTipo.setSelectedItem(this.usuario.getTipo());
        if(this.usuario.getPessoa() != null){
            jtfPessoa.setText(this.usuario.getPessoa().getNome());
        }
        
        jcbTipo.setEnabled(false);
        jtpProfessores.setSelectedComponent(jpDadosCadastrais);
    }

    private void aplicarFiltros() {

        UsuarioTM tabelaUsuario = new UsuarioTM();

        if (jtfNomeConsulta.getText().trim().isEmpty()) {
            tabelaUsuario.addListaUsuarios(new UsuarioDAO().consultarTodos());
        } else {
            tabelaUsuario.addListaUsuarios(new UsuarioDAO().consultar(jtfNomeConsulta.getText()));
        }

        jtUsuarios.setModel(tabelaUsuario);
    }

    private void salvarUsuario() {
        
        ArrayList<String> inconsistencias = new ArrayList();
        boolean resultado;
        
        this.usuario.setNome(jtfNome.getText());
        this.usuario.setSenha(new String(jpfSenha.getPassword()));
        this.usuario.setConfirmacaoSenha(new String(jpfConfirmacaoSenha.getPassword()));
        if(!jcbTipo.getModel().getSelectedItem().equals("(selecione)")){
             this.usuario.setTipo((TipoUsuario) jcbTipo.getModel().getSelectedItem());
        }
       
        if(jtfCodigo.getText().trim().isEmpty()){
            inconsistencias = new UsuarioValidador().validarCadastro(usuario);
            
            if(inconsistencias.size() > 0){
                Mensagem.frameInconsistencias(inconsistencias);
            } else {
                resultado = new UsuarioDAO().salvar(usuario);
                if(resultado){
                    Mensagem.sucesso("Dados registrados com sucesso");
                    limparCampos();
                    jtpProfessores.setSelectedComponent(jpConsulta);
                    aplicarFiltros();
                } else{
                    Mensagem.erro("Problema ao salvar aluno");
                }
            }
            
        } else {
            
            this.usuario.setId(Integer.parseInt(jtfCodigo.getText()));
            
            inconsistencias = new UsuarioValidador().validarAlteracoes(usuario);
            
            if(inconsistencias.size() > 0){
                Mensagem.frameInconsistencias(inconsistencias);
            } else {
                resultado = new UsuarioDAO().atualizar(usuario);
                if(resultado){
                    Mensagem.sucesso("Dados registrados com sucesso");
                    limparCampos();
                    jtpProfessores.setSelectedComponent(jpConsulta);
                    aplicarFiltros();
                } else{
                    Mensagem.erro("Problema ao salvar aluno");
                }
            }
        }
    }
    
    private void carregarPessoa(){
        if(jcbTipo.getModel().getSelectedItem().equals("(selecione)")){
            Mensagem.aviso("Selecione o tipo de usuário");
            return;
        }
        
        if((TipoUsuario) jcbTipo.getModel().getSelectedItem() == TipoUsuario.ADMINISTRADOR){
            Mensagem.aviso("Este tipo de usuário não permite selecionar uma pessoa");
            return;
        }
        
        if((TipoUsuario) jcbTipo.getModel().getSelectedItem() == TipoUsuario.PROFESSOR){
            JDLookUpProfessores lookUpProfessores = new JDLookUpProfessores(null, true, true);
            lookUpProfessores.setVisible(true);
            
            if(lookUpProfessores.getSelecaoConfirmada()){
                this.usuario.setPessoa(lookUpProfessores.getProfessorSelecionado());
                jtfPessoa.setText(this.usuario.getPessoa().getNome());
            }
        } else {
            JDLookUpAlunos lookUpAlunos = new JDLookUpAlunos(null, true);
            lookUpAlunos.setVisible(true);
            
            if(lookUpAlunos.getSelecaoConfirmada()){
                this.usuario.setPessoa(lookUpAlunos.getAlunoSelecionado());
                jtfPessoa.setText(this.usuario.getPessoa().getNome());
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtpProfessores = new javax.swing.JTabbedPane();
        jpConsulta = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jtfNomeConsulta = new javax.swing.JTextField();
        jbtPesquisar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtUsuarios = new javax.swing.JTable();
        jbtIncluir = new javax.swing.JButton();
        jbtAlterar = new javax.swing.JButton();
        jbtSair = new javax.swing.JButton();
        jpDadosCadastrais = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtfCodigo = new javax.swing.JTextField();
        jtfNome = new javax.swing.JTextField();
        jbtConfirmar = new javax.swing.JButton();
        jbtCancelar = new javax.swing.JButton();
        jpfSenha = new javax.swing.JPasswordField();
        jLabel12 = new javax.swing.JLabel();
        jcbTipo = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jpfConfirmacaoSenha = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        jtfPessoa = new javax.swing.JTextField();
        jlbPesquisarPessoa = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Usuários");
        setPreferredSize(new java.awt.Dimension(763, 463));

        jtpProfessores.setEnabled(false);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros"));

        jLabel6.setText("Usuário");

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
                .addComponent(jbtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        jtUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtUsuarios.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jtUsuarios);

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
                        .addComponent(jbtSair)))
                .addContainerGap())
        );

        jpConsultaLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jbtAlterar, jbtIncluir, jbtSair});

        jpConsultaLayout.setVerticalGroup(
            jpConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpConsultaLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtSair)
                    .addComponent(jbtAlterar)
                    .addComponent(jbtIncluir))
                .addContainerGap())
        );

        jtpProfessores.addTab("Consulta", jpConsulta);

        jLabel1.setText("Código");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 255));
        jLabel2.setText("Login");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 255));
        jLabel3.setText("Senha");

        jtfCodigo.setEnabled(false);

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

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 255));
        jLabel12.setText("Tipo");

        jcbTipo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbTipoItemStateChanged(evt);
            }
        });
        jcbTipo.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jcbTipoPropertyChange(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 255));
        jLabel4.setText("Confirmação senha");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 255));
        jLabel5.setText("Pessoa");

        jtfPessoa.setEditable(false);

        jlbPesquisarPessoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/search16.png"))); // NOI18N
        jlbPesquisarPessoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbPesquisarPessoaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jpDadosCadastraisLayout = new javax.swing.GroupLayout(jpDadosCadastrais);
        jpDadosCadastrais.setLayout(jpDadosCadastraisLayout);
        jpDadosCadastraisLayout.setHorizontalGroup(
            jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpDadosCadastraisLayout.createSequentialGroup()
                .addContainerGap(275, Short.MAX_VALUE)
                .addComponent(jbtConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtCancelar)
                .addGap(247, 247, 247))
            .addGroup(jpDadosCadastraisLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel12)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpfConfirmacaoSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                    .addComponent(jpfSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                    .addComponent(jtfNome, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                    .addComponent(jtfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfPessoa)
                    .addComponent(jcbTipo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlbPesquisarPessoa)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpDadosCadastraisLayout.setVerticalGroup(
            jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDadosCadastraisLayout.createSequentialGroup()
                .addGap(16, 16, 16)
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
                    .addComponent(jpfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jpfConfirmacaoSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jcbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlbPesquisarPessoa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(jtfPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 167, Short.MAX_VALUE)
                .addGroup(jpDadosCadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtConfirmar)
                    .addComponent(jbtCancelar))
                .addContainerGap(43, Short.MAX_VALUE))
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

        try {
            aplicarFiltros();
        } catch (Exception e) {
            Mensagem.erro("Não foi possível salvar o usuário");
        }

    }//GEN-LAST:event_jbtPesquisarActionPerformed

    private void jbtIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtIncluirActionPerformed
        try {
            jtpProfessores.setSelectedComponent(jpDadosCadastrais);
        } catch (Exception e) {
            Mensagem.erro("Não foi possível salvar o usuário");
        }
    }//GEN-LAST:event_jbtIncluirActionPerformed

    private void jbtAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtAlterarActionPerformed

        try {
            carregarUsuario();
        } catch (Exception e) {
            Mensagem.erro("Não foi possível salvar o usuário");
        }

    }//GEN-LAST:event_jbtAlterarActionPerformed

    private void jbtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtSairActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbtSairActionPerformed

    private void jbtConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtConfirmarActionPerformed
        try {
            salvarUsuario();
        } catch (Exception e) {
            Mensagem.erro("Não foi possível salvar o usuário");
        }
    }//GEN-LAST:event_jbtConfirmarActionPerformed

    private void jbtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtCancelarActionPerformed
        limparCampos();
        jtpProfessores.setSelectedComponent(jpConsulta);
    }//GEN-LAST:event_jbtCancelarActionPerformed

    private void jlbPesquisarPessoaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbPesquisarPessoaMouseClicked
        
        try {
            carregarPessoa();
        } catch (Exception e) {
            Mensagem.erro("Problema ao carregar pessoa");
        }
        
    }//GEN-LAST:event_jlbPesquisarPessoaMouseClicked

    private void jcbTipoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jcbTipoPropertyChange
        
    }//GEN-LAST:event_jcbTipoPropertyChange

    private void jcbTipoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbTipoItemStateChanged
        if(jcbTipo.getModel().getSelectedItem().equals("(selecione)") || 
           (TipoUsuario) jcbTipo.getModel().getSelectedItem() == TipoUsuario.ADMINISTRADOR){
            this.usuario.setPessoa(null);
            jtfPessoa.setText("");
            jlbPesquisarPessoa.setEnabled(false);
        } else{
            jlbPesquisarPessoa.setEnabled(true);
        }
    }//GEN-LAST:event_jcbTipoItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtAlterar;
    private javax.swing.JButton jbtCancelar;
    private javax.swing.JButton jbtConfirmar;
    private javax.swing.JButton jbtIncluir;
    private javax.swing.JButton jbtPesquisar;
    private javax.swing.JButton jbtSair;
    private javax.swing.JComboBox<String> jcbTipo;
    private javax.swing.JLabel jlbPesquisarPessoa;
    private javax.swing.JPanel jpConsulta;
    private javax.swing.JPanel jpDadosCadastrais;
    private javax.swing.JPasswordField jpfConfirmacaoSenha;
    private javax.swing.JPasswordField jpfSenha;
    private javax.swing.JTable jtUsuarios;
    private javax.swing.JTextField jtfCodigo;
    private javax.swing.JTextField jtfNome;
    private javax.swing.JTextField jtfNomeConsulta;
    private javax.swing.JTextField jtfPessoa;
    private javax.swing.JTabbedPane jtpProfessores;
    // End of variables declaration//GEN-END:variables
}
