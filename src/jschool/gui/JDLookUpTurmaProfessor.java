package jschool.gui;

import java.util.ArrayList;
import jschool.dao.TurmaDAO;
import jschool.enumeracoes.TipoUsuario;
import jschool.infraestrutura.Ambiente;
import jschool.infraestrutura.Mensagem;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.entidade.Turma;
import jschool.modelo.tabela.TurmaLookUpTM;

/**
 *
 * @author andre
 */
public class JDLookUpTurmaProfessor extends javax.swing.JDialog {

    private Turma turmaSelecionada;
    private boolean selecaoConfirmada;
    private ArrayList<IEntidade> turmas;
    
    public JDLookUpTurmaProfessor(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        aplicarFiltros();
    }
    
    public Turma getTurmaSelecionada(){
        return this.turmaSelecionada;
    }
    
    public boolean getSelecaoConfirmada(){
        return this.selecaoConfirmada;
    }
    
    private void aplicarFiltros(){
        
        TurmaLookUpTM turmaTableModel = new TurmaLookUpTM();
        
        if(Ambiente.getTipoUsuarioLogado() == TipoUsuario.PROFESSOR){
            this.turmas = new TurmaDAO().consultarTurmasProfessor(Ambiente.getUsuarioLogado().getPessoa().getId());
        } else {
            this.turmas = new TurmaDAO().consultarTodos();
        }
        
        
        turmaTableModel.addListaTurmas(this.turmas);
        jtbTurmas.setModel(turmaTableModel);
    }
    
    private void selecionarTurma(){
        if(jtbTurmas.getSelectedRow() == -1){
            Mensagem.aviso("Selecione uma turma");
            return;
        }
        
        int idTurma = Integer.parseInt(jtbTurmas.getModel().getValueAt(jtbTurmas.getSelectedRow(), 0).toString());
        if(idTurma > 0){
            
            this.turmaSelecionada = (Turma) new TurmaDAO().consultarId(idTurma);
            
            if(this.turmaSelecionada == null){
                throw new RuntimeException("Não foi possível recuperar a turma");
            }
            
            this.selecaoConfirmada = true;
            this.dispose();
        } else {
            throw new RuntimeException("Não foi possível recuperar a turma");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jtbTurmas = new javax.swing.JTable();
        jbtConfirmar = new javax.swing.JButton();
        jbtCancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Seleção de turmas");
        setResizable(false);

        jtbTurmas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtbTurmas.setToolTipText("");
        jtbTurmas.setFillsViewportHeight(true);
        jtbTurmas.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jtbTurmas);

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

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Selecione uma turma");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtConfirmar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtCancelar)
                .addGap(122, 122, 122))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(152, 152, 152)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtConfirmar)
                    .addComponent(jbtCancelar))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbtConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtConfirmarActionPerformed
        try{
            selecionarTurma();
        } catch(Exception ex){
            Mensagem.erro(ex.getMessage());
        }
    }//GEN-LAST:event_jbtConfirmarActionPerformed

    private void jbtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtCancelarActionPerformed
        this.selecaoConfirmada = false;
        this.dispose();
    }//GEN-LAST:event_jbtCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbtCancelar;
    private javax.swing.JButton jbtConfirmar;
    private javax.swing.JTable jtbTurmas;
    // End of variables declaration//GEN-END:variables
}
