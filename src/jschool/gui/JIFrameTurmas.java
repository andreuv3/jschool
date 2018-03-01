/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.gui;

import java.util.ArrayList;
import java.util.Calendar;
import jschool.ajudantes.ComboBoxAjudante;
import jschool.dao.TurmaDAO;
import jschool.enumeracoes.SituacaoTurma;
import jschool.enumeracoes.TipoMedia;
import jschool.enumeracoes.TipoUsuario;
import jschool.infraestrutura.Ambiente;
import jschool.infraestrutura.Mensagem;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.entidade.Turma;
import jschool.modelo.tabela.TurmaTM;
import jschool.validacao.TurmaValidador;

/**
 *
 * @author andre
 */
public class JIFrameTurmas extends javax.swing.JInternalFrame {
    
    private Turma turma;
    private ArrayList<IEntidade> turmas;
    
    /**
     * Creates new form JIFrameTurmas
     */
    public JIFrameTurmas() {
        initComponents();
        this.turma = new Turma();
        this.turmas = new ArrayList<>();
        ComboBoxAjudante.preencherComboBoxTipoMedia(jcbTipoMedia);
        ajustarCamposHorario();
        aplicarFiltros();
        validarPermissoes();
    }
    
    private void validarPermissoes(){
        if(Ambiente.getTipoUsuarioLogado() != TipoUsuario.ADMINISTRADOR){
            jbtIncluir.setEnabled(false);
            jbtInativar1.setEnabled(false);
            jbtConfirmar.setEnabled(false);
        }
    }
    
    private void ajustarCamposHorario(){
        jsfHoraInicial.setMaximum(23);
        jsfHoraInicial.setMinimum(0);
        jsfMinutoInicial.setMaximum(59);
        jsfMinutoInicial.setMinimum(0);
        jsfHoraFinal.setMaximum(23);
        jsfHoraFinal.setMinimum(0);
        jsfMinutoFinal.setMaximum(59);
        jsfMinutoFinal.setMinimum(0);
    }
    
    private void aplicarFiltros(){
        TurmaTM turmaTabelModel = new TurmaTM();
        this.turmas = new TurmaDAO().pesquisar(jtfCursoPesquisa.getText(), jtfProfessorConsulta.getText(), false);
        turmaTabelModel.addListaTurmas(turmas);
        jtTurmas.setModel(turmaTabelModel);
    }
    
    private void salvarTurma(){
        
        ArrayList<String> inconsistencias = new ArrayList();
        boolean sucesso;
        
        //this.turma.setId(Integer.parseInt(jtfCodigo.getText()));
        this.turma.setDataInicio(jdcDataInicial.getDate());
        this.turma.setDataFim(jdcDataFinal.getDate());
        this.turma.setSala(jtfSalaDeAula.getText());
        this.turma.setDescricao(jtfDescricao.getText());
        
        if(!jtfVagas.getText().trim().isEmpty())
            this.turma.setVagas(Integer.parseInt(jtfVagas.getText()));
        
        if(!jtfAprovacao.getText().trim().isEmpty())
            this.turma.setMediaAprovacao(Double.parseDouble(jtfAprovacao.getText()));
        
        if(!jcbTipoMedia.getModel().getSelectedItem().equals("(selecione)"))
            this.turma.setTipoMedia((TipoMedia) jcbTipoMedia.getSelectedItem());
        
        this.turma.getHorarioInicial().set(Calendar.HOUR_OF_DAY, jsfHoraInicial.getValue());
        this.turma.getHorarioInicial().set(Calendar.MINUTE, jsfMinutoInicial.getValue());
        this.turma.getHorarioFinal().set(Calendar.HOUR_OF_DAY, jsfHoraFinal.getValue());
        this.turma.getHorarioFinal().set(Calendar.MINUTE, jsfMinutoFinal.getValue());
        
        this.turma.getDiasAulasSemana().clear();
        
        if(jchDomingo.isSelected())
            this.turma.getDiasAulasSemana().add(Calendar.SUNDAY);
        
        if(jchSegunda.isSelected())
            this.turma.getDiasAulasSemana().add(Calendar.MONDAY);
        
        if(jchTerca.isSelected())
            this.turma.getDiasAulasSemana().add(Calendar.TUESDAY);
        
        if(jchQuarta.isSelected())
            this.turma.getDiasAulasSemana().add(Calendar.WEDNESDAY);
        
        if(jchQuinta.isSelected())
            this.turma.getDiasAulasSemana().add(Calendar.THURSDAY);
        
        if(jchSexta.isSelected())
            this.turma.getDiasAulasSemana().add(Calendar.FRIDAY);
        
        if(jchSabado.isSelected())
            this.turma.getDiasAulasSemana().add(Calendar.SATURDAY);
        
        TurmaValidador validador = new TurmaValidador();
        
        if(this.turma.getId() <= 0){
            inconsistencias = validador.validarCadastro(turma);
        } else {
            inconsistencias = validador.validarAlteracoes(turma);
        }
        
        if (inconsistencias.size() > 0) {
            Mensagem.frameInconsistencias(inconsistencias);
            return;
        } 
        
        if(jtfCodigo.getText().trim().isEmpty()){
            sucesso = new TurmaDAO().salvarNovo(turma) > 0;
        } else {
            sucesso = new TurmaDAO().atualizar(turma);
        }
        
        if(sucesso){
            Mensagem.sucesso("Dados registrados com sucesso");
            limparDados();
            jtpTurmas.setSelectedComponent(jpConsulta);
        } else {
            Mensagem.erro("Problema ao salvar turma");
            return;
        }
    }
    
    private void limparDados(){
        jtfCodigo.setText("");
        jtfSituacao.setText("");
        jtfCurso.setText("Procurar curso...");
        jtfProfessor.setText("Procurar professor...");
        jtfAprovacao.setText("");
        jtfSalaDeAula.setText("");
        jtfVagas.setText("");
        jchDomingo.setSelected(false);
        jchSegunda.setSelected(false);
        jchTerca.setSelected(false);
        jchQuarta.setSelected(false);
        jchQuinta.setSelected(false);
        jchSexta.setSelected(false);
        jchSabado.setSelected(false);
        jsfHoraInicial.setValue(0);
        jsfMinutoInicial.setValue(0);
        jsfHoraFinal.setValue(0);
        jsfMinutoFinal.setValue(0);
        jdcDataInicial.setDate(null);
        jdcDataFinal.setDate(null);
        jlbVagasRecomendadas.setText("");
        jtfDescricao.setText("");
        jbtConfirmar.setEnabled(true);
        ComboBoxAjudante.preencherComboBoxTipoMedia(jcbTipoMedia);
    }
    
    private void alterarTurma(){
        
        if (jtTurmas.getSelectedRow() == -1) {
            Mensagem.aviso("Selecione uma turma");
            return;
        }

        int idTurma = Integer.parseInt(jtTurmas.getModel().getValueAt(jtTurmas.getSelectedRow(), 0).toString());
        
        this.turma = (Turma) new TurmaDAO().consultarId(idTurma);
        if(this.turma == null){
            Mensagem.erro("Problema ao recuperar turma");
            return;
        }
            
        jtfCodigo.setText(String.valueOf(this.turma.getId()));
        jtfSituacao.setText(this.turma.getSituacao().getDescricaoSituacaoTurma());
        jtfCurso.setText(this.turma.getCurso().getNome());
        jtfProfessor.setText(this.turma.getProfessor().getNome());
        jdcDataInicial.setDate(this.turma.getDataInicio());
        jdcDataFinal.setDate(this.turma.getDataFim());
        jsfHoraInicial.setValue(this.turma.getHorarioInicial().get(Calendar.HOUR_OF_DAY));
        jsfMinutoInicial.setValue(this.turma.getHorarioInicial().get(Calendar.MINUTE));
        jsfHoraFinal.setValue(this.turma.getHorarioFinal().get(Calendar.HOUR_OF_DAY));
        jsfMinutoFinal.setValue(this.turma.getHorarioFinal().get(Calendar.MINUTE));
        jtfVagas.setText(String.valueOf(this.turma.getVagas()));
        jtfAprovacao.setText(String.valueOf(this.turma.getMediaAprovacao()));
        jtfSalaDeAula.setText(this.turma.getSala());
        jcbTipoMedia.setSelectedItem(this.turma.getTipoMedia());
        jtfDescricao.setText(this.turma.getDescricao());
        
        if(this.turma.getSituacao() != SituacaoTurma.ABERTA){
            jbtConfirmar.setEnabled(false);
        }
        
        for(Integer diaSemana : this.turma.getDiasAulasSemana()){
            switch(diaSemana){
                case Calendar.SUNDAY: 
                    jchDomingo.setSelected(true);
                    break;
                case Calendar.MONDAY:
                    jchSegunda.setSelected(true);
                    break;
                case Calendar.TUESDAY:
                    jchTerca.setSelected(true);
                    break;
                case Calendar.WEDNESDAY:
                    jchQuarta.setSelected(true);
                    break;
                case Calendar.THURSDAY:
                    jchQuinta.setSelected(true);
                    break;
                case Calendar.FRIDAY:
                    jchSexta.setSelected(true);
                    break;
                case Calendar.SATURDAY:
                    jchSabado.setSelected(true);
                    break;
                default:
                    break;
            }
        }
        
        jtpTurmas.setSelectedComponent(jpDados);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cursoTM1 = new jschool.modelo.tabela.CursoTM();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jbtInativar = new javax.swing.JButton();
        jtpTurmas = new javax.swing.JTabbedPane();
        jpConsulta = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jtfCursoPesquisa = new javax.swing.JTextField();
        jbtPesquisar = new javax.swing.JButton();
        jtfProfessorConsulta = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtTurmas = new javax.swing.JTable();
        jbtSair = new javax.swing.JButton();
        jbtIncluir = new javax.swing.JButton();
        jbtAlterar = new javax.swing.JButton();
        jbtInativar1 = new javax.swing.JButton();
        jpDados = new javax.swing.JPanel();
        jbtConfirmar = new javax.swing.JButton();
        jtfCancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jcbTipoMedia = new javax.swing.JComboBox<>();
        jtfCodigo = new javax.swing.JTextField();
        jtfCurso = new javax.swing.JTextField();
        jtfProfessor = new javax.swing.JTextField();
        jtfVagas = new javax.swing.JTextField();
        jdcDataInicial = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jdcDataFinal = new com.toedter.calendar.JDateChooser();
        jlPesquisarCurso = new javax.swing.JLabel();
        jlPesquisarProfessor = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jtfAprovacao = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jtfSituacao = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jtfSalaDeAula = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jchDomingo = new javax.swing.JCheckBox();
        jchSegunda = new javax.swing.JCheckBox();
        jchTerca = new javax.swing.JCheckBox();
        jchQuarta = new javax.swing.JCheckBox();
        jchQuinta = new javax.swing.JCheckBox();
        jchSexta = new javax.swing.JCheckBox();
        jchSabado = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        jlbVagasRecomendadas = new javax.swing.JLabel();
        jsfHoraInicial = new com.toedter.components.JSpinField();
        jsfMinutoInicial = new com.toedter.components.JSpinField();
        jLabel13 = new javax.swing.JLabel();
        jsfHoraFinal = new com.toedter.components.JSpinField();
        jLabel14 = new javax.swing.JLabel();
        jsfMinutoFinal = new com.toedter.components.JSpinField();
        jtfDescricao = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();

        jbtInativar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/switch24.png"))); // NOI18N
        jbtInativar.setText("Alterar Situação");
        jbtInativar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtInativarActionPerformed(evt);
            }
        });

        setClosable(true);
        setTitle("Turmas");

        jtpTurmas.setEnabled(false);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros"));

        jLabel6.setText("Curso");

        jtfCursoPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfCursoPesquisaActionPerformed(evt);
            }
        });

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

        jtfProfessorConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfProfessorConsultaActionPerformed(evt);
            }
        });

        jLabel17.setText("Professor");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jtfCursoPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfProfessorConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfCursoPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel17)))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtPesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtfProfessorConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2))
        );

        jtTurmas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtTurmas.setGridColor(new java.awt.Color(175, 158, 158));
        jtTurmas.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jtTurmas);

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

        jbtInativar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/switch24.png"))); // NOI18N
        jbtInativar1.setText("Alterar Situação");
        jbtInativar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtInativar1ActionPerformed(evt);
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
                        .addComponent(jbtIncluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtAlterar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtInativar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtSair)))
                .addContainerGap())
        );

        jpConsultaLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jbtAlterar, jbtIncluir, jbtSair});

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
                    .addComponent(jbtAlterar)
                    .addComponent(jbtIncluir)
                    .addComponent(jbtInativar1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtpTurmas.addTab("Consulta", jpConsulta);

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

        jLabel1.setText("Código");

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 255));
        jLabel2.setText("Curso");

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 255));
        jLabel3.setText("Professor");

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 255));
        jLabel4.setText("Data inicial");

        jLabel5.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 255));
        jLabel5.setText("Horário inicial");

        jLabel7.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 255));
        jLabel7.setText("Vagas");

        jLabel9.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 255));
        jLabel9.setText("Tipo de média");

        jtfCodigo.setEnabled(false);

        jtfCurso.setText("Procurar curso...");

        jtfProfessor.setText("Procurar professor...");

        jLabel10.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 255));
        jLabel10.setText("Data final");

        jLabel11.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 255));
        jLabel11.setText("Horário final");

        jlPesquisarCurso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/search16.png"))); // NOI18N
        jlPesquisarCurso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlPesquisarCursoMouseClicked(evt);
            }
        });

        jlPesquisarProfessor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/search16.png"))); // NOI18N
        jlPesquisarProfessor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlPesquisarProfessorMouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 255));
        jLabel12.setText("Aprovação");

        jLabel15.setText("Situação");

        jtfSituacao.setEnabled(false);

        jLabel16.setText("Sala de aula");

        jtfSalaDeAula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfSalaDeAulaActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dias da semana", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(0, 0, 255))); // NOI18N

        jchDomingo.setText("Domingo");

        jchSegunda.setText("Segunda-feira");

        jchTerca.setText("Terça-feira");

        jchQuarta.setText("Quarta-feira");

        jchQuinta.setText("Quinta-feira");

        jchSexta.setText("Sexta-feira");

        jchSabado.setText("Sábado");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jchDomingo)
                    .addComponent(jchSegunda)
                    .addComponent(jchTerca)
                    .addComponent(jchQuarta)
                    .addComponent(jchQuinta)
                    .addComponent(jchSexta)
                    .addComponent(jchSabado))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jchDomingo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jchSegunda)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jchTerca)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jchQuarta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jchQuinta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jchSexta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jchSabado)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setForeground(java.awt.Color.red);
        jLabel8.setText("Vagas recomendadas:");

        jlbVagasRecomendadas.setForeground(java.awt.Color.red);

        jLabel13.setText(" :");

        jLabel14.setText(" :");

        jLabel19.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 255));
        jLabel19.setText("Descrição");

        javax.swing.GroupLayout jpDadosLayout = new javax.swing.GroupLayout(jpDados);
        jpDados.setLayout(jpDadosLayout);
        jpDadosLayout.setHorizontalGroup(
            jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDadosLayout.createSequentialGroup()
                        .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(37, 37, 37)
                        .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpDadosLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jbtConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtfCancelar))
                            .addGroup(jpDadosLayout.createSequentialGroup()
                                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpDadosLayout.createSequentialGroup()
                                        .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jdcDataInicial, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                            .addGroup(jpDadosLayout.createSequentialGroup()
                                                .addComponent(jsfHoraInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(2, 2, 2)
                                                .addComponent(jLabel13)
                                                .addGap(4, 4, 4)
                                                .addComponent(jsfMinutoInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jtfVagas))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jpDadosLayout.createSequentialGroup()
                                                .addComponent(jLabel8)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jlbVagasRecomendadas, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jpDadosLayout.createSequentialGroup()
                                                        .addComponent(jLabel11)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jsfHoraFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(2, 2, 2)
                                                        .addComponent(jLabel14)
                                                        .addGap(4, 4, 4)
                                                        .addComponent(jsfMinutoFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(jpDadosLayout.createSequentialGroup()
                                                        .addComponent(jLabel10)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jdcDataFinal, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)))
                                                .addGroup(jpDadosLayout.createSequentialGroup()
                                                    .addComponent(jLabel15)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jtfSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addComponent(jtfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jpDadosLayout.createSequentialGroup()
                                        .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jtfProfessor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                                            .addComponent(jtfCurso, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jlPesquisarCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jlPesquisarProfessor, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jpDadosLayout.createSequentialGroup()
                        .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel9)
                            .addComponent(jLabel19))
                        .addGap(35, 35, 35)
                        .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpDadosLayout.createSequentialGroup()
                                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jcbTipoMedia, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtfSalaDeAula))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtfAprovacao, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jtfDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpDadosLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jdcDataFinal, jdcDataInicial});

        jpDadosLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jbtConfirmar, jtfCancelar});

        jpDadosLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jsfHoraInicial, jsfMinutoInicial});

        jpDadosLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jsfHoraFinal, jsfMinutoFinal});

        jpDadosLayout.setVerticalGroup(
            jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDadosLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDadosLayout.createSequentialGroup()
                        .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpDadosLayout.createSequentialGroup()
                                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel15)
                                    .addComponent(jtfSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jlPesquisarCurso))
                                .addGap(42, 42, 42)
                                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpDadosLayout.createSequentialGroup()
                                        .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jdcDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jsfMinutoFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jsfHoraFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jpDadosLayout.createSequentialGroup()
                                        .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jdcDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jsfHoraInicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addComponent(jsfMinutoInicial, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jpDadosLayout.createSequentialGroup()
                                .addComponent(jtfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtfCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jtfProfessor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3))
                                    .addComponent(jlPesquisarProfessor, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtfVagas)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlbVagasRecomendadas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(jcbTipoMedia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jtfAprovacao)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jtfSalaDeAula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(49, 49, 49)
                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtConfirmar)
                    .addComponent(jtfCancelar))
                .addGap(21, 21, 21))
        );

        jtpTurmas.addTab("Dados cadastrais", jpDados);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpTurmas)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpTurmas)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtPesquisarActionPerformed
        try {
            aplicarFiltros();
        } catch (Exception e) {
            Mensagem.erro("Problema ao recuperar turmas");
        }
    }//GEN-LAST:event_jbtPesquisarActionPerformed

    private void jbtSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtSairActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbtSairActionPerformed

    private void jbtIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtIncluirActionPerformed
        try {
            
            this.turma = new Turma();
            jtpTurmas.setSelectedComponent(jpDados);
            
        } catch (Exception e) {
            Mensagem.erro("Problema ao incluir turma");
        }
    }//GEN-LAST:event_jbtIncluirActionPerformed

    private void jbtAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtAlterarActionPerformed
        try {
            alterarTurma();
        } catch (Exception e) {
            System.out.println("err: " + e.getMessage());
            Mensagem.erro("Problema ao alterar turma");
        }
    }//GEN-LAST:event_jbtAlterarActionPerformed

    private void jbtConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtConfirmarActionPerformed
        
        try {
            salvarTurma();
            aplicarFiltros();
        } catch (Exception e) {
            System.out.println("Erro prob: " + e.getMessage());
            Mensagem.erro("Problema ao salvar turma");
        }
        
    }//GEN-LAST:event_jbtConfirmarActionPerformed

    private void jtfCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfCancelarActionPerformed
        
        jtpTurmas.setSelectedComponent(jpConsulta);
        limparDados();
        aplicarFiltros();
        
    }//GEN-LAST:event_jtfCancelarActionPerformed

    private void jlPesquisarCursoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlPesquisarCursoMouseClicked
        
        try {
            
            JDLookUpCursos lookUpCursos = new JDLookUpCursos(null, true, true);
            lookUpCursos.setVisible(true);
            
            if(lookUpCursos.getSelecaoConfirmada()){
                this.turma.setCurso(lookUpCursos.getCursoSelecionado());
                jtfCurso.setText(this.turma.getCurso().getNome());
                jlbVagasRecomendadas.setText(String.valueOf(this.turma.getCurso().getVagasRecomendadas()));
            }
            
        } catch (Exception e) {
            Mensagem.erro("Erro ao selecionar curso.");
        }
        
    }//GEN-LAST:event_jlPesquisarCursoMouseClicked

    private void jlPesquisarProfessorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlPesquisarProfessorMouseClicked
        try {
            JDLookUpProfessores lookUpProfessores = new JDLookUpProfessores(null, true, true);
            lookUpProfessores.setVisible(true);
            
            if(lookUpProfessores.getSelecaoConfirmada()){
                this.turma.setProfessor(lookUpProfessores.getProfessorSelecionado());
                jtfProfessor.setText(turma.getProfessor().getNome());
            }
            
        } catch (Exception e) {
            Mensagem.erro("Erro ao selecionar professor.");
        }
    }//GEN-LAST:event_jlPesquisarProfessorMouseClicked

    private void jtfCursoPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfCursoPesquisaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfCursoPesquisaActionPerformed

    private void jtfProfessorConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfProfessorConsultaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfProfessorConsultaActionPerformed

    private void jbtInativarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtInativarActionPerformed
        
    }//GEN-LAST:event_jbtInativarActionPerformed

    private void jbtInativar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtInativar1ActionPerformed
        try {
            if (jtTurmas.getSelectedRow() == -1) {
                Mensagem.aviso("Selecione uma turma");
                return;
            }

            int idTurma = Integer.parseInt(jtTurmas.getModel().getValueAt(jtTurmas.getSelectedRow(), 0).toString());
            JDSituacaoTurma dialogSituacao = new JDSituacaoTurma(null, true, idTurma);
            dialogSituacao.setVisible(true);
            
            aplicarFiltros();
        } catch (Exception ex) {
            Mensagem.erro("Problema alterar a situação do professor: " + ex.getMessage());
        }
    }//GEN-LAST:event_jbtInativar1ActionPerformed

    private void jtfSalaDeAulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfSalaDeAulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfSalaDeAulaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private jschool.modelo.tabela.CursoTM cursoTM1;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbtAlterar;
    private javax.swing.JButton jbtConfirmar;
    private javax.swing.JButton jbtInativar;
    private javax.swing.JButton jbtInativar1;
    private javax.swing.JButton jbtIncluir;
    private javax.swing.JButton jbtPesquisar;
    private javax.swing.JButton jbtSair;
    private javax.swing.JComboBox<String> jcbTipoMedia;
    private javax.swing.JCheckBox jchDomingo;
    private javax.swing.JCheckBox jchQuarta;
    private javax.swing.JCheckBox jchQuinta;
    private javax.swing.JCheckBox jchSabado;
    private javax.swing.JCheckBox jchSegunda;
    private javax.swing.JCheckBox jchSexta;
    private javax.swing.JCheckBox jchTerca;
    private com.toedter.calendar.JDateChooser jdcDataFinal;
    private com.toedter.calendar.JDateChooser jdcDataInicial;
    private javax.swing.JLabel jlPesquisarCurso;
    private javax.swing.JLabel jlPesquisarProfessor;
    private javax.swing.JLabel jlbVagasRecomendadas;
    private javax.swing.JPanel jpConsulta;
    private javax.swing.JPanel jpDados;
    private com.toedter.components.JSpinField jsfHoraFinal;
    private com.toedter.components.JSpinField jsfHoraInicial;
    private com.toedter.components.JSpinField jsfMinutoFinal;
    private com.toedter.components.JSpinField jsfMinutoInicial;
    private javax.swing.JTable jtTurmas;
    private javax.swing.JTextField jtfAprovacao;
    private javax.swing.JButton jtfCancelar;
    private javax.swing.JTextField jtfCodigo;
    private javax.swing.JTextField jtfCurso;
    private javax.swing.JTextField jtfCursoPesquisa;
    private javax.swing.JTextField jtfDescricao;
    private javax.swing.JTextField jtfProfessor;
    private javax.swing.JTextField jtfProfessorConsulta;
    private javax.swing.JTextField jtfSalaDeAula;
    private javax.swing.JTextField jtfSituacao;
    private javax.swing.JTextField jtfVagas;
    private javax.swing.JTabbedPane jtpTurmas;
    // End of variables declaration//GEN-END:variables
}
