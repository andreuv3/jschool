/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.gui;

import java.util.ArrayList;
import jschool.dao.CursoDAO;
import jschool.infraestrutura.Mensagem;
import jschool.modelo.entidade.Curso;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.tabela.CursoLookUpTM;
import jschool.modelo.tabela.CursoTM;
import jschool.util.Strings;

/**
 *
 * @author andre
 */
public class JDLookUpCursos extends javax.swing.JDialog {

    private Curso cursoSelecionado;
    private ArrayList<IEntidade> cursos;
    private boolean selecaoConfirmada;
    private boolean cursosAtivos;
    
    /**
     * Creates new form JDLookUpCursos
     */
    public JDLookUpCursos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.cursoSelecionado = new Curso();
        this.cursos = new ArrayList<>();
        this.selecaoConfirmada = false;
        this.cursosAtivos = false;
        
        aplicarFiltros();
    }
    
    public JDLookUpCursos(java.awt.Frame parent, boolean modal, boolean cursosAtivos){
        super(parent, modal);
        initComponents();
        this.cursoSelecionado = new Curso();
        this.cursos = new ArrayList<>();
        this.selecaoConfirmada = false;
        this.cursosAtivos = cursosAtivos;
        
        aplicarFiltros();
    }
    
    public Curso getCursoSelecionado(){
        return this.cursoSelecionado;
    }
    
    public boolean getSelecaoConfirmada(){
        return this.selecaoConfirmada;
    }
    
    private void selecionarCurso(){
        if(jtbCursos.getSelectedRow() == -1){
            Mensagem.aviso("Selecione um curso");
            return;
        }
        
        int idCurso = Integer.parseInt(jtbCursos.getModel().getValueAt(jtbCursos.getSelectedRow(), 0).toString());
        if(idCurso > 0){
            
            this.cursoSelecionado = (Curso) new CursoDAO().consultarId(idCurso);
            
            if(this.cursoSelecionado == null){
                throw new RuntimeException("Não foi possível recuperar o curso");
            }
            
            this.selecaoConfirmada = true;
            this.dispose();
        } else {
            throw new RuntimeException("Não foi possível recuperar o curso");
        }
    }
    
    private void aplicarFiltros(){
        
        CursoDAO cursoDao = new CursoDAO();
        CursoLookUpTM cursoTableModel = new CursoLookUpTM();
        
        if (!Strings.IsNullOrEmpty(jtfCurso.getText()) && this.cursosAtivos){
            this.cursos = cursoDao.consultarAtivos(jtfCurso.getText());
        } else if(!Strings.IsNullOrEmpty(jtfCurso.getText()) && !this.cursosAtivos){
            this.cursos = cursoDao.consultar(jtfCurso.getText());
        } else if(Strings.IsNullOrEmpty(jtfCurso.getText()) && this.cursosAtivos){
            this.cursos = cursoDao.consultarTodosAtivos();
        } else {
            this.cursos = cursoDao.consultarTodos();
        }
        
        cursoTableModel.addListaCursos(this.cursos);
        jtbCursos.setModel(cursoTableModel);
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jtfCurso = new javax.swing.JTextField();
        jbtPesquisar1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtbCursos = new javax.swing.JTable();
        jbtConfirmar1 = new javax.swing.JButton();
        jbtCancelar1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cursos");
        setResizable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros"));

        jLabel3.setText("Curso");

        jbtPesquisar1.setText("Pesquisar");
        jbtPesquisar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtPesquisar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtPesquisar1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtfCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtPesquisar1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtbCursos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtbCursos.setToolTipText("");
        jtbCursos.setFillsViewportHeight(true);
        jScrollPane2.setViewportView(jtbCursos);

        jbtConfirmar1.setText("Confirmar");
        jbtConfirmar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtConfirmar1ActionPerformed(evt);
            }
        });

        jbtCancelar1.setText("Cancelar");
        jbtCancelar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtCancelar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jbtConfirmar1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtCancelar1)
                .addGap(198, 198, 198))
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jbtCancelar1, jbtConfirmar1});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtConfirmar1)
                    .addComponent(jbtCancelar1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbtPesquisar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtPesquisar1ActionPerformed
        try {
            aplicarFiltros();
        } catch (Exception e) {
            Mensagem.erro(e.getMessage());
        }
    }//GEN-LAST:event_jbtPesquisar1ActionPerformed

    private void jbtConfirmar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtConfirmar1ActionPerformed
        try{
            selecionarCurso();
        } catch(Exception ex){
            Mensagem.erro(ex.getMessage());
        }
    }//GEN-LAST:event_jbtConfirmar1ActionPerformed

    private void jbtCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtCancelar1ActionPerformed
        this.selecaoConfirmada = false;
        this.dispose();
    }//GEN-LAST:event_jbtCancelar1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbtCancelar1;
    private javax.swing.JButton jbtConfirmar1;
    private javax.swing.JButton jbtPesquisar1;
    private javax.swing.JTable jtbCursos;
    private javax.swing.JTextField jtfCurso;
    // End of variables declaration//GEN-END:variables
}
