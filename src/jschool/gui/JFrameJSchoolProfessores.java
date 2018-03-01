/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.gui;



import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import jschool.infraestrutura.ConexaoFactory;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author andre
 */
public class JFrameJSchoolProfessores extends javax.swing.JFrame {

    /**
     * Creates new form JFrameJSchoolProfessores
     */
    public JFrameJSchoolProfessores() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToggleButton1 = new javax.swing.JToggleButton();
        jLabel2 = new javax.swing.JLabel();
        jdpJschool = new javax.swing.JDesktopPane();
        jmbJschool = new javax.swing.JMenuBar();
        jmMeusDados = new javax.swing.JMenu();
        jmiDadosCadastrai = new javax.swing.JMenuItem();
        jmiUsuario = new javax.swing.JMenuItem();
        jmiCursos = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jmiTurmas = new javax.swing.JMenuItem();
        jmiFrequencias = new javax.swing.JMenuItem();
        jmiNotas = new javax.swing.JMenuItem();
        jmiAgenda = new javax.swing.JMenuItem();
        jmRelPessoas = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jmiListagemCursos = new javax.swing.JMenuItem();
        jmFerramentas = new javax.swing.JMenu();
        jmiTemas = new javax.swing.JMenuItem();
        jmSobre = new javax.swing.JMenu();
        jmiSobre = new javax.swing.JMenuItem();

        jToggleButton1.setText("jToggleButton1");

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JSchool");
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        jdpJschool.setBackground(new java.awt.Color(240, 240, 240));

        javax.swing.GroupLayout jdpJschoolLayout = new javax.swing.GroupLayout(jdpJschool);
        jdpJschool.setLayout(jdpJschoolLayout);
        jdpJschoolLayout.setHorizontalGroup(
            jdpJschoolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 757, Short.MAX_VALUE)
        );
        jdpJschoolLayout.setVerticalGroup(
            jdpJschoolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 502, Short.MAX_VALUE)
        );

        jmMeusDados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/folder24.png"))); // NOI18N
        jmMeusDados.setText("Meus dados");

        jmiDadosCadastrai.setText("Dados cadastrais");
        jmiDadosCadastrai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiDadosCadastraiActionPerformed(evt);
            }
        });
        jmMeusDados.add(jmiDadosCadastrai);

        jmiUsuario.setText("Usuário");
        jmiUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiUsuarioActionPerformed(evt);
            }
        });
        jmMeusDados.add(jmiUsuario);

        jmbJschool.add(jmMeusDados);

        jmiCursos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/scholarship.png"))); // NOI18N
        jmiCursos.setText("Educacional");

        jMenuItem5.setText("Cursos");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jmiCursos.add(jMenuItem5);

        jmiTurmas.setText("Turmas");
        jmiTurmas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiTurmasActionPerformed(evt);
            }
        });
        jmiCursos.add(jmiTurmas);

        jmiFrequencias.setText("Frequências");
        jmiFrequencias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiFrequenciasActionPerformed(evt);
            }
        });
        jmiCursos.add(jmiFrequencias);

        jmiNotas.setText("Notas");
        jmiNotas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiNotasActionPerformed(evt);
            }
        });
        jmiCursos.add(jmiNotas);

        jmiAgenda.setText("Agenda");
        jmiAgenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiAgendaActionPerformed(evt);
            }
        });
        jmiCursos.add(jmiAgenda);

        jmbJschool.add(jmiCursos);

        jmRelPessoas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/relatorio24.png"))); // NOI18N
        jmRelPessoas.setText("Relatórios");

        jMenu3.setText("Educacional");

        jmiListagemCursos.setText("Listagem de cursos");
        jmiListagemCursos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiListagemCursosActionPerformed(evt);
            }
        });
        jMenu3.add(jmiListagemCursos);

        jmRelPessoas.add(jMenu3);

        jmbJschool.add(jmRelPessoas);

        jmFerramentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/wrench.png"))); // NOI18N
        jmFerramentas.setText("Ferramentas");

        jmiTemas.setText("Temas");
        jmiTemas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiTemasActionPerformed(evt);
            }
        });
        jmFerramentas.add(jmiTemas);

        jmbJschool.add(jmFerramentas);

        jmSobre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/info.png"))); // NOI18N
        jmSobre.setText("Sobre");

        jmiSobre.setText("Sobre");
        jmiSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiSobreActionPerformed(evt);
            }
        });
        jmSobre.add(jmiSobre);

        jmbJschool.add(jmSobre);

        setJMenuBar(jmbJschool);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jdpJschool, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jdpJschool, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        JIFrameCursos frameCursos = new JIFrameCursos();
        jdpJschool.add(frameCursos);
        frameCursos.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jmiTurmasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiTurmasActionPerformed
        JIFrameTurmas frameTurmas = new JIFrameTurmas();
        jdpJschool.add(frameTurmas);
        frameTurmas.setVisible(true);
    }//GEN-LAST:event_jmiTurmasActionPerformed

    private void jmiTemasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiTemasActionPerformed
        JIFrameTemas frameTemas = new JIFrameTemas();
        jdpJschool.add(frameTemas);
        frameTemas.setVisible(true);
    }//GEN-LAST:event_jmiTemasActionPerformed

    private void jmiSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiSobreActionPerformed
        JIFrameSobre frameSobre = new JIFrameSobre();
        jdpJschool.add(frameSobre);
        frameSobre.setVisible(true);
    }//GEN-LAST:event_jmiSobreActionPerformed

    private void jmiFrequenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiFrequenciasActionPerformed
        JIFrameFrequencias frameFrequencias = new JIFrameFrequencias();
        jdpJschool.add(frameFrequencias);
        frameFrequencias.setVisible(true);
    }//GEN-LAST:event_jmiFrequenciasActionPerformed

    private void jmiAgendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiAgendaActionPerformed
        JIFrameAgenda frameAgenda = new JIFrameAgenda();
        jdpJschool.add(frameAgenda);
        frameAgenda.setVisible(true);
    }//GEN-LAST:event_jmiAgendaActionPerformed

    private void jmiListagemCursosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiListagemCursosActionPerformed
        try {
            // Compila o relatorio
            JasperReport relatorio = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jschool/relatorios/rel_cursos.jrxml"));

            // Mapeia campos de parametros para o relatorio, mesmo que nao existam
            Map parametros = new HashMap();

            // Executa relatoio
            JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, ConexaoFactory.getConnection());

            // Exibe resultado em video
            JasperViewer.viewReport(impressao, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório");
        }
    }//GEN-LAST:event_jmiListagemCursosActionPerformed

    private void jmiNotasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiNotasActionPerformed
        JIFrameNotas frameNotas = new JIFrameNotas();
        jdpJschool.add(frameNotas);
        frameNotas.setVisible(true);
    }//GEN-LAST:event_jmiNotasActionPerformed

    private void jmiUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiUsuarioActionPerformed
        JIFrameDadosUsuario frameDadosUsuario = new JIFrameDadosUsuario();
        jdpJschool.add(frameDadosUsuario);
        frameDadosUsuario.setVisible(true);
    }//GEN-LAST:event_jmiUsuarioActionPerformed

    private void jmiDadosCadastraiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiDadosCadastraiActionPerformed
        JIFrameDadosProfessor frameDadosProfessor = new JIFrameDadosProfessor();
        jdpJschool.add(frameDadosProfessor);
        frameDadosProfessor.setVisible(true);
    }//GEN-LAST:event_jmiDadosCadastraiActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JDesktopPane jdpJschool;
    private javax.swing.JMenu jmFerramentas;
    private javax.swing.JMenu jmMeusDados;
    private javax.swing.JMenu jmRelPessoas;
    private javax.swing.JMenu jmSobre;
    private javax.swing.JMenuBar jmbJschool;
    private javax.swing.JMenuItem jmiAgenda;
    private javax.swing.JMenu jmiCursos;
    private javax.swing.JMenuItem jmiDadosCadastrai;
    private javax.swing.JMenuItem jmiFrequencias;
    private javax.swing.JMenuItem jmiListagemCursos;
    private javax.swing.JMenuItem jmiNotas;
    private javax.swing.JMenuItem jmiSobre;
    private javax.swing.JMenuItem jmiTemas;
    private javax.swing.JMenuItem jmiTurmas;
    private javax.swing.JMenuItem jmiUsuario;
    // End of variables declaration//GEN-END:variables
}
