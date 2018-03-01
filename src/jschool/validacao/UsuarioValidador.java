package jschool.validacao;

import java.util.ArrayList;
import jschool.dao.UsuarioDAO;
import jschool.enumeracoes.TipoUsuario;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.entidade.Usuario;

/**
 *
 * @author andre
 */
public class UsuarioValidador implements IValidador {

    @Override
    public ArrayList<String> validarCadastro(IEntidade entidade) {

        Usuario usuario = (Usuario) entidade;

        ArrayList<String> inconsistencias = new ArrayList();

        if(usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()){
            inconsistencias.add("Informe a senha");
        }
        
        if(usuario.getConfirmacaoSenha()== null || usuario.getConfirmacaoSenha().trim().isEmpty()){
            inconsistencias.add("Confirme a senha");
        }
        
        if (!usuario.getSenha().equalsIgnoreCase(usuario.getConfirmacaoSenha())) {
            inconsistencias.add("As senhas devem ser iguais");
        }

        if (usuario.getNome().trim().isEmpty()) {
            inconsistencias.add("Informe o usuário");
        }
        
        if(new UsuarioDAO().consultarNome(usuario.getNome())){
            inconsistencias.add("Usuário já cadastrado");
        }

        if (usuario.getTipo() == null || usuario.getTipo().getValorTipoUsuario() <= 0) {
            inconsistencias.add("Informe o tipo de usuário");
        }

        if (usuario.getTipo() != TipoUsuario.ADMINISTRADOR && usuario.getPessoa() == null) {
            inconsistencias.add("Vincule uma pessoa ao usuário");
        }

        return inconsistencias;
    }

    @Override
    public ArrayList<String> validarAlteracoes(IEntidade entidade) {
        
        Usuario usuario = (Usuario) entidade;

        ArrayList<String> inconsistencias = new ArrayList();

        if(usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()){
            inconsistencias.add("Informe a senha");
        }
        
        if(usuario.getConfirmacaoSenha()== null || usuario.getConfirmacaoSenha().trim().isEmpty()){
            inconsistencias.add("Confirme a senha");
        }
        
        if (!usuario.getSenha().equalsIgnoreCase(usuario.getConfirmacaoSenha())) {
            inconsistencias.add("As senhas devem ser iguais");
        }

        if (usuario.getNome().trim().isEmpty()) {
            inconsistencias.add("Informe o usuário");
        }
        
        if(new UsuarioDAO().consultarNome(usuario.getNome()) && !usuario.getNome().equals(usuario.getNomeAntigo())){
            inconsistencias.add("Usuário já cadastrado");
        }

        if (usuario.getTipo() == null || usuario.getTipo().getValorTipoUsuario() <= 0) {
            inconsistencias.add("Informe o tipo de usuário");
        }

        if (usuario.getTipo() != TipoUsuario.ADMINISTRADOR && usuario.getPessoa() == null) {
            inconsistencias.add("Vincule uma pessoa ao usuário");
        }

        return inconsistencias;
    }

}
