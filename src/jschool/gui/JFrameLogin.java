package jschool.gui;

import jschool.dao.UsuarioDAO;
import jschool.enumeracoes.TipoUsuario;
import jschool.infraestrutura.Ambiente;
import jschool.infraestrutura.Mensagem;
import jschool.operacoes.TurmaOP;
import jschool.util.Strings;

/**
 *
 * @author andre
 */
public class JFrameLogin extends javax.swing.JFrame {

    /**
     * Creates new form JFrameLogin
     */
    public JFrameLogin() {
        initComponents();
    }
    
    private void Login(String usuario, String senha) throws Exception{
        
        if(UsuarioDAO.login(usuario, senha)){
        
            TipoUsuario tipoUsuario = Ambiente.getTipoUsuarioLogado();
            TurmaOP.AlterarStatusTurmas();
                        
            switch (tipoUsuario) {
                case ADMINISTRADOR:
                    JFrameJSchool frameJSchool = new JFrameJSchool();
                    frameJSchool.setVisible(true);
                    this.dispose();
                    break;
                case ALUNO:
                    JFrameJSchoolAlunos frameJSchoolAlunos = new JFrameJSchoolAlunos();
                    frameJSchoolAlunos.setVisible(true);
                    this.dispose();
                    break;
                case PROFESSOR:
                    JFrameJSchoolProfessores frameJSchoolProfessores = new JFrameJSchoolProfessores();
                    frameJSchoolProfessores.setVisible(true);
                    this.dispose();
                    break;
                default:
                    throw new Exception("Erro ao recuperar usuário");
            }
            
        } else{
            Mensagem.erro("Usuário ou senha inválidos");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlbUsuario = new javax.swing.JLabel();
        jlbSenha = new javax.swing.JLabel();
        jbtEntrar = new javax.swing.JButton();
        jtfUsuario = new javax.swing.JTextField();
        jpfSenha = new javax.swing.JPasswordField();
        jlbTitulo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JSchool - Login");
        setResizable(false);

        jlbUsuario.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jlbUsuario.setText("Usuário");

        jlbSenha.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jlbSenha.setText("Senha");

        jbtEntrar.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jbtEntrar.setText("Entrar");
        jbtEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtEntrarActionPerformed(evt);
            }
        });

        jlbTitulo.setFont(new java.awt.Font("Ubuntu", 0, 40)); // NOI18N
        jlbTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jschool/icons/learning64.png"))); // NOI18N
        jlbTitulo.setText("JSchool");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpfSenha)
                    .addComponent(jtfUsuario)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbtEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlbUsuario)
                            .addComponent(jlbSenha))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jlbTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jlbTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlbUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlbSenha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbtEntrar)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jpfSenha, jtfUsuario});

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbtEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtEntrarActionPerformed
        
        try{
            
            String usuario, senha;

            usuario = jtfUsuario.getText();
            senha = new String(jpfSenha.getPassword());

            if (Strings.IsNullOrEmpty(usuario) || Strings.IsWhiteSpace(usuario)) {
                Mensagem.aviso("Insira o usuário");
            } else if (Strings.IsNullOrEmpty(senha)) {
                Mensagem.aviso("Insira a senha");
            } else {
                Login(usuario, senha);
            }

        } catch (Exception e){
            Mensagem.erro("Problema ao fazer login, tente novamente.");
        }
        
    }//GEN-LAST:event_jbtEntrarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbtEntrar;
    private javax.swing.JLabel jlbSenha;
    private javax.swing.JLabel jlbTitulo;
    private javax.swing.JLabel jlbUsuario;
    private javax.swing.JPasswordField jpfSenha;
    private javax.swing.JTextField jtfUsuario;
    // End of variables declaration//GEN-END:variables
}
