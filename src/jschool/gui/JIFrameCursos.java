/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.gui;

import java.util.ArrayList;
import jschool.modelo.entidade.Curso;
import jschool.dao.CursoDAO;
import jschool.enumeracoes.TipoUsuario;
import jschool.infraestrutura.Ambiente;
import jschool.modelo.entidade.IEntidade;
import jschool.infraestrutura.Mensagem;
import jschool.modelo.tabela.CursoTM;
import jschool.validacao.CursoValidador;

/**
 *
 * @author andre
 */
public class JIFrameCursos extends javax.swing.JInternalFrame {

    private Curso curso;
    private ArrayList<IEntidade> cursos;

    /**
     * Creates new form JIFrameCursos
     */
    public JIFrameCursos() {

        this.curso = new Curso();
        this.cursos = new ArrayList<>();

        initComponents();
        aplicarFiltros();
        limparCampos();
        validarPermisoes();
    }
    
    private void validarPermisoes(){
        if(Ambiente.getTipoUsuarioLogado() != TipoUsuario.ADMINISTRADOR){
            jbtIncluir.setEnabled(false);
            jbtInativar.setEnabled(false);
            jbtConfirmar.setEnabled(false);
        }
    }

    private void limparCampos() {
        jtfNome.setText("");
        jtfCodigo.setText("0");
        jtfHorasDuracao.setText("0");
        jtfVagasRecomendadas.setText("0");
        jtaEmenta.setText("");
    }

    private void aplicarFiltros() {
        CursoTM tabelaCursos = new CursoTM();
        
        if(jtfNomeConsulta.getText().trim().isEmpty()){
            this.cursos = new CursoDAO().consultarTodos();
        } else {
            this.cursos = new CursoDAO().consultar(jtfNomeConsulta.getText());
        }
       
        if(this.cursos != null){
            tabelaCursos.addListaCursos(this.cursos);
        }
        
        jtCursos.setModel(tabelaCursos);
    }

    private void salvarDados() {
        ArrayList<String> inconsistencias;
        boolean resultado;
        
        int idCurso = Integer.parseInt(jtfCodigo.getText());
        this.curso.setNome(jtfNome.getText());
        this.curso.setEmenta(jtaEmenta.getText());
        
        if(!jtfHorasDuracao.getText().trim().isEmpty()){
            this.curso.setHoraDuracao(Double.parseDouble(jtfHorasDuracao.getText()));
        } else{
            this.curso.setHoraDuracao(0);
        }
        
        if(!jtfVagasRecomendadas.getText().trim().isEmpty()){
            this.curso.setVagasRecomendadas(Integer.parseInt(jtfVagasRecomendadas.getText()));
        }
        
        if (idCurso <= 0) {
            inconsistencias = new CursoValidador().validarCadastro(curso);
            if (inconsistencias.size() > 0) {
                Mensagem.frameInconsistencias(inconsistencias);
                return;
            }
            
            resultado = new CursoDAO().salvar(curso);
        } else {
            this.curso.setId(Integer.parseInt(jtfCodigo.getText()));
            
            inconsistencias = new CursoValidador().validarAlteracoes(curso);
            if (inconsistencias.size() > 0) {
                Mensagem.frameInconsistencias(inconsistencias);
                return;
            }
            
            resultado = new CursoDAO().atualizar(curso);
        }

        if (resultado) {
            Mensagem.sucesso("Dados registrados com sucesso");
        } else {
            Mensagem.erro("Problema ao registrar dados");
        }
        
        limparCampos();
        jtpCursos.setSelectedComponent(jpConsulta);
        aplicarFiltros();
    }
    
    private void inativarCurso(){
        if (jtCursos.getSelectedRow() == -1) {
                Mensagem.aviso("Selecione um curso");
                return;
            }

            int idCurso = Integer.parseInt(jtCursos.getModel().getValueAt(jtCursos.getSelectedRow(), 0).toString());
            this.curso = (Curso) new CursoDAO().consultarId(idCurso);
            if (curso == null) {
                Mensagem.erro("Problema ao recuperar curso");
                return;
            }

            boolean resultado = new CursoDAO().alterarSituacao(this.curso);
            if (resultado) {
                Mensagem.sucesso("Situação do curso alterada com sucesso");
                aplicarFiltros();
            } else {
                Mensagem.erro("Não foi possível alterar a situação do curso");
            }
    }
    
    private void alterarCurso(){
        if (jtCursos.getSelectedRow() == -1) {
            Mensagem.aviso("Selecione um curso");
            return;
        }

        int idCurso = Integer.parseInt(jtCursos.getModel().getValueAt(jtCursos.getSelectedRow(), 0).toString());
        this.curso = (Curso) new CursoDAO().consultarId(idCurso);
        if (curso == null) {
            Mensagem.erro("Problema ao recuperar curso");
            return;
        }

        jtfCodigo.setText(Integer.toString(this.curso.getId()));
        jtfSituacao.setText(this.curso.getSituacao());
        jtfNome.setText(this.curso.getNome());
        jtfHorasDuracao.setText(Double.toString(this.curso.getHoraDuracao()));
        jtfVagasRecomendadas.setText(Integer.toString(this.curso.getVagasRecomendadas()));
        jtaEmenta.setText(this.curso.getEmenta());

        jtpCursos.setSelectedComponent(jpDados);
        jtfNome.requestFocus();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtpCursos = new javax.swing.JTabbedPane();
        jpConsulta = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jtfNomeConsulta = new javax.swing.JTextField();
        jbtPesquisar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtCursos = new javax.swing.JTable();
        jbtSair = new javax.swing.JButton();
        jbtIncluir = new javax.swing.JButton();
        jbtAlterar = new javax.swing.JButton();
        jbtInativar = new javax.swing.JButton();
        jpDados = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jtfCodigo = new javax.swing.JTextField();
        jtfNome = new javax.swing.JTextField();
        jtfHorasDuracao = new javax.swing.JTextField();
        jtfVagasRecomendadas = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaEmenta = new javax.swing.JTextArea();
        jbtConfirmar = new javax.swing.JButton();
        jtfCancelar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jtfSituacao = new javax.swing.JTextField();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setClosable(true);
        setTitle("Curso");

        jtpCursos.setEnabled(false);

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

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jbtPesquisar, jtfNomeConsulta});

        jtCursos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtCursos.setGridColor(new java.awt.Color(175, 158, 158));
        jtCursos.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jtCursos);

        jbtSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/delete24.png"))); // NOI18N
        jbtSair.setText("Sair");
        jbtSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtSairActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout jpConsultaLayout = new javax.swing.GroupLayout(jpConsulta);
        jpConsulta.setLayout(jpConsultaLayout);
        jpConsultaLayout.setHorizontalGroup(
            jpConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpConsultaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
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
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtSair)
                    .addComponent(jbtInativar)
                    .addComponent(jbtAlterar)
                    .addComponent(jbtIncluir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtpCursos.addTab("Consulta", jpConsulta);

        jLabel1.setText("Código");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 204));
        jLabel2.setText("Nome");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 204));
        jLabel3.setText("Horas duração");

        jLabel4.setText("Vagas recomendadas");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 204));
        jLabel5.setText("Ementa");

        jtfCodigo.setEditable(false);

        jtaEmenta.setColumns(10);
        jtaEmenta.setLineWrap(true);
        jtaEmenta.setRows(5);
        jScrollPane1.setViewportView(jtaEmenta);

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

        jLabel8.setText("Situação");

        jtfSituacao.setEditable(false);

        javax.swing.GroupLayout jpDadosLayout = new javax.swing.GroupLayout(jpDados);
        jpDados.setLayout(jpDadosLayout);
        jpDadosLayout.setHorizontalGroup(
            jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDadosLayout.createSequentialGroup()
                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDadosLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(31, 31, 31)
                        .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtfNome)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                            .addGroup(jpDadosLayout.createSequentialGroup()
                                .addComponent(jtfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtfSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jtfVagasRecomendadas, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                            .addComponent(jtfHorasDuracao)))
                    .addGroup(jpDadosLayout.createSequentialGroup()
                        .addGap(260, 260, 260)
                        .addComponent(jbtConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfCancelar)))
                .addContainerGap(170, Short.MAX_VALUE))
        );

        jpDadosLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jtfCodigo, jtfHorasDuracao, jtfVagasRecomendadas});

        jpDadosLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jbtConfirmar, jtfCancelar});

        jpDadosLayout.setVerticalGroup(
            jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDadosLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jtfSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jtfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtfHorasDuracao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtfVagasRecomendadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDadosLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtConfirmar)
                    .addComponent(jtfCancelar))
                .addContainerGap())
        );

        jtpCursos.addTab("Dados cadastrais", jpDados);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpCursos)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpCursos)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtConfirmarActionPerformed
        salvarDados();
    }//GEN-LAST:event_jbtConfirmarActionPerformed

    private void jbtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtSairActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbtSairActionPerformed

    private void jbtIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtIncluirActionPerformed
        jtpCursos.setSelectedComponent(jpDados);
        jtfNome.requestFocus();
    }//GEN-LAST:event_jbtIncluirActionPerformed

    private void jtfCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfCancelarActionPerformed
        limparCampos();
        jtpCursos.setSelectedComponent(jpConsulta);
    }//GEN-LAST:event_jtfCancelarActionPerformed

    private void jbtAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtAlterarActionPerformed
        try{
            alterarCurso();
        } catch(Exception ex){
            Mensagem.erro(ex.getMessage());
        }
    }//GEN-LAST:event_jbtAlterarActionPerformed

    private void jbtInativarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtInativarActionPerformed
        try {
            inativarCurso();
        } catch (Exception ex) {
            Mensagem.erro("Problema alterar a situação do curso: " + ex.getMessage());
        }
    }//GEN-LAST:event_jbtInativarActionPerformed

    private void jbtPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtPesquisarActionPerformed
        aplicarFiltros();
    }//GEN-LAST:event_jbtPesquisarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbtAlterar;
    private javax.swing.JButton jbtConfirmar;
    private javax.swing.JButton jbtInativar;
    private javax.swing.JButton jbtIncluir;
    private javax.swing.JButton jbtPesquisar;
    private javax.swing.JButton jbtSair;
    private javax.swing.JPanel jpConsulta;
    private javax.swing.JPanel jpDados;
    private javax.swing.JTable jtCursos;
    private javax.swing.JTextArea jtaEmenta;
    private javax.swing.JButton jtfCancelar;
    private javax.swing.JTextField jtfCodigo;
    private javax.swing.JTextField jtfHorasDuracao;
    private javax.swing.JTextField jtfNome;
    private javax.swing.JTextField jtfNomeConsulta;
    private javax.swing.JTextField jtfSituacao;
    private javax.swing.JTextField jtfVagasRecomendadas;
    private javax.swing.JTabbedPane jtpCursos;
    // End of variables declaration//GEN-END:variables
}
