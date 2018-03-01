/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.gui;

import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import jschool.dao.CidadeDAO;
import jschool.dao.EstadoDAO;
import jschool.modelo.entidade.Estado;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.tabela.CidadeTM;
import jschool.infraestrutura.Mensagem;
import jschool.util.Strings;

/**
 *
 * @author andre
 */
public class JFrameLookUpCidade extends javax.swing.JFrame {

    private int idCidadeSelecionada;
    private ArrayList<IEntidade> cidades;
    
    /**
     * Creates new form JFrameLookUpCidade
     */
    public JFrameLookUpCidade() {
        initComponents();
        carregarEstados();
        this.cidades = new ArrayList<>();
        this.idCidadeSelecionada = 0;
    }
    
    public int getIdCidadeSelecionada(){
        return this.idCidadeSelecionada;
    }
    
    private void carregarEstados() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();

        ArrayList<IEntidade> estados = new EstadoDAO().consultarTodos();
        for (int i = 0; i < estados.size(); i++) {
            Estado e = (Estado) estados.get(i);
            modelo.addElement(e);
        }

        jcbEstados.setModel(modelo);
    }
    
    private void aplicarFiltros(){
        
        CidadeDAO cidadeDAO = new CidadeDAO();
        CidadeTM tabelaCidades = new CidadeTM();
        
        String nomeCidade = jtfCidade.getText();
        Estado estadoSelecionado = (Estado)jcbEstados.getModel().getSelectedItem();
        
        if(!Strings.IsNullOrEmpty(nomeCidade) && estadoSelecionado != null && estadoSelecionado.getId() > 0){
            System.out.println("nome e estado");
            this.cidades = cidadeDAO.consultarCidades(nomeCidade, estadoSelecionado);
        } else if(Strings.IsNullOrEmpty(nomeCidade) && estadoSelecionado != null && estadoSelecionado.getId() > 0){
            System.out.println("só estado");
            this.cidades = cidadeDAO.consultarCidadesPorEstado(estadoSelecionado);
        } else if(!Strings.IsNullOrEmpty(nomeCidade) && (estadoSelecionado == null || estadoSelecionado.getId() <= 0)){
            System.out.println("só estado");
            this.cidades = cidadeDAO.consultar(nomeCidade);
        } else {
            System.out.println("qualquer");
            this.cidades = cidadeDAO.consultarTodos();
        }
        
        if(this.cidades != null){
            tabelaCidades.addListaCidades(this.cidades);
        }
        
        jtbCidades.setModel(tabelaCidades);
    }
    
    private void selecionarCidade(){
        if(jtbCidades.getSelectedRow() == -1){
            Mensagem.aviso("Selecione uma cidade");
        }
        
        int idCurso = Integer.parseInt(jtbCidades.getModel().getValueAt(jtbCidades.getSelectedRow(), 0).toString());
        if(idCurso > 0){
            this.idCidadeSelecionada = idCurso;
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jtfCidade = new javax.swing.JTextField();
        jbtPesquisar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jcbEstados = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbCidades = new javax.swing.JTable();
        jbtConfirmar = new javax.swing.JButton();
        jbtCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cidades");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("FIltros"));

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

        jLabel1.setText("UF");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtfCidade)
                    .addComponent(jcbEstados, 0, 321, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jbtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jcbEstados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jtbCidades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jtbCidades);

        jbtConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/add24.png"))); // NOI18N
        jbtConfirmar.setText("Confirmar");
        jbtConfirmar.setIconTextGap(6);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jbtConfirmar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtCancelar)
                .addGap(146, 146, 146))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jbtCancelar, jbtConfirmar});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtConfirmar)
                    .addComponent(jbtCancelar))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbtPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtPesquisarActionPerformed
        try{
            aplicarFiltros();
        } catch (Exception ex){
            Mensagem.erro(ex.getMessage());
        }
    }//GEN-LAST:event_jbtPesquisarActionPerformed

    private void jbtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbtCancelarActionPerformed

    private void jbtConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtConfirmarActionPerformed
        try{
            selecionarCidade();
        } catch(Exception ex){
            Mensagem.erro(ex.getMessage());
        }
    }//GEN-LAST:event_jbtConfirmarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtCancelar;
    private javax.swing.JButton jbtConfirmar;
    private javax.swing.JButton jbtPesquisar;
    private javax.swing.JComboBox<String> jcbEstados;
    private javax.swing.JTable jtbCidades;
    private javax.swing.JTextField jtfCidade;
    // End of variables declaration//GEN-END:variables
}
