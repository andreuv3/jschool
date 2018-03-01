/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import jschool.enumeracoes.TipoUsuario;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.entidade.Usuario;
import jschool.infraestrutura.Ambiente;
import jschool.infraestrutura.BancoDados;
import jschool.modelo.entidade.Aluno;
import jschool.modelo.entidade.Professor;
import jschool.seguranca.Criptografia;

/**
 *
 * @author andre
 */
public class UsuarioDAO implements IDAO {

    public static boolean login(String usuario, String senha) throws Exception {

        BancoDados bd = new BancoDados();
        boolean loginSucesso = false;

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }

            String senhaCriptografada = Criptografia.criptografar(senha.toLowerCase());

            StringBuilder query = new StringBuilder();
            query.append("SELECT id, tipo, nome, senha, id_pessoa FROM usuario ");
            query.append("WHERE nome = ? AND senha = ? ");
            query.append("LIMIT 1");

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1, usuario.toLowerCase());
            stmt.setString(2, senhaCriptografada);

            ResultSet resultado = stmt.executeQuery();

            if (resultado.first()) {

                Usuario usuarioLogado = new Usuario();
                usuarioLogado.setId(resultado.getInt("id"));
                usuarioLogado.setNome(resultado.getString("nome"));
                usuarioLogado.setNomeAntigo(resultado.getString("nome"));
                usuarioLogado.setSenha(resultado.getString("senha"));

                int idPessoa = resultado.getInt("id_pessoa");
                int tipoUsuario = resultado.getInt("tipo");

                if (tipoUsuario == TipoUsuario.ADMINISTRADOR.getValorTipoUsuario()) {
                    usuarioLogado.setTipo(TipoUsuario.ADMINISTRADOR);
                } else if (tipoUsuario == TipoUsuario.PROFESSOR.getValorTipoUsuario()) {
                    usuarioLogado.setTipo(TipoUsuario.PROFESSOR);
                    usuarioLogado.setPessoa(new Professor(idPessoa));
                    new PessoaDAO().carregarPessoa(usuarioLogado.getPessoa());
                } else if (tipoUsuario == TipoUsuario.ALUNO.getValorTipoUsuario()) {
                    usuarioLogado.setTipo(TipoUsuario.ALUNO);
                    usuarioLogado.setPessoa(new Aluno(idPessoa));
                    new PessoaDAO().carregarPessoa(usuarioLogado.getPessoa());
                } else {
                    throw new Exception("Tipo de usuário inválido.");
                }

                Ambiente.setUsuarioLogado(usuarioLogado);

                loginSucesso = true;
            }

            return loginSucesso;
        } catch (Exception ex) {
            System.out.println("ex:" + ex.getMessage());
            return loginSucesso;
        } finally {
            bd.fecharConexao();
        }
    }

    @Override
    public boolean salvar(IEntidade e) {

        Usuario u = (Usuario) e;
        BancoDados bd = new BancoDados();

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }

            String senhaCriptografada = Criptografia.criptografar(u.getSenha().toLowerCase());

            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO usuario (nome, senha, tipo, id_pessoa) ");
            query.append("VALUES (?, ?, ?, ?)");

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setString(1, u.getNome().toLowerCase());
            stmt.setString(2, senhaCriptografada);
            stmt.setInt(3, u.getTipo().getValorTipoUsuario());
            if (u.getPessoa() != null) {
                stmt.setInt(4, u.getPessoa().getId());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            return stmt.executeUpdate() > 0;
        } catch (Exception ex) {
            return false;
        } finally {
            bd.fecharConexao();
        }
    }

    @Override
    public boolean atualizar(IEntidade e) {

        Usuario u = (Usuario) e;
        BancoDados bd = new BancoDados();

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }

            String senhaCriptografada = Criptografia.criptografar(u.getSenha().toLowerCase());

            StringBuilder query = new StringBuilder();
            query.append("UPDATE usuario SET nome = ?, senha = ?, tipo = ?, id_pessoa = ? ");
            query.append("WHERE id = ? ");

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setString(1, u.getNome().toLowerCase());
            stmt.setString(2, senhaCriptografada);
            stmt.setInt(3, u.getTipo().getValorTipoUsuario());
            if (u.getPessoa() != null) {
                stmt.setInt(4, u.getPessoa().getId());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }
            stmt.setInt(5, u.getId());

            return stmt.executeUpdate() > 0;
        } catch (Exception ex) {
            return false;
        } finally {
            bd.fecharConexao();
        }
    }

    @Override
    public boolean excluir(int id) {
        throw new UnsupportedOperationException("Operação não suportada");
    }

    @Override
    public ArrayList<IEntidade> consultarTodos() {

        ArrayList<IEntidade> usuarios = new ArrayList<>();
        BancoDados bd = new BancoDados();

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }

            String query = "SELECT id, nome, tipo, id_pessoa FROM usuario";

            PreparedStatement stmt = bd.getConexao().prepareStatement(query);

            ResultSet resultado = stmt.executeQuery();

            Usuario usuario;
            int tipoUsuario;
            while (resultado.next()) {
                usuario = new Usuario();
                usuario.setId(resultado.getInt("id"));
                usuario.setNome(resultado.getString("nome"));
                usuario.setNomeAntigo(resultado.getString("nome"));

                //Define o tipo de usuário
                tipoUsuario = resultado.getInt("tipo");
                int idPessoa = resultado.getInt("id_pessoa");
                if (tipoUsuario == TipoUsuario.ADMINISTRADOR.getValorTipoUsuario()) {
                    usuario.setTipo(TipoUsuario.ADMINISTRADOR);
                } else if (tipoUsuario == TipoUsuario.PROFESSOR.getValorTipoUsuario()) {
                    usuario.setTipo(TipoUsuario.PROFESSOR);
                    Professor p = new Professor(idPessoa);
                    new PessoaDAO().carregarPessoa(p);
                } else if (tipoUsuario == TipoUsuario.ALUNO.getValorTipoUsuario()) {
                    usuario.setTipo(TipoUsuario.ALUNO);
                    Aluno a = new Aluno(idPessoa);
                    new PessoaDAO().carregarPessoa(a);
                }

                usuarios.add(usuario);
            }

            return usuarios;
        } catch (Exception ex) {
            return null;
        } finally {
            bd.fecharConexao();
        }
    }

    @Override
    public ArrayList<IEntidade> consultar(String criterio) {

        ArrayList<IEntidade> usuarios = new ArrayList();
        BancoDados bd = new BancoDados();

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }

            String query = "SELECT id, nome, tipo, id_pessoa FROM usuario WHERE nome ILIKE ?";

            PreparedStatement stmt = bd.getConexao().prepareStatement(query);
            stmt.setString(1, "%" + criterio + "%");

            ResultSet resultado = stmt.executeQuery();

            Usuario usuario;
            int tipoUsuario;
            while (resultado.next()) {
                usuario = new Usuario();
                usuario.setId(resultado.getInt("id"));
                usuario.setNome(resultado.getString("nome"));
                usuario.setNomeAntigo(resultado.getString("nome"));

                //Define o tipo de usuário
                tipoUsuario = resultado.getInt("tipo");
                int idPessoa = resultado.getInt("id_pessoa");
                if (tipoUsuario == TipoUsuario.ADMINISTRADOR.getValorTipoUsuario()) {
                    usuario.setTipo(TipoUsuario.ADMINISTRADOR);
                } else if (tipoUsuario == TipoUsuario.PROFESSOR.getValorTipoUsuario()) {
                    usuario.setTipo(TipoUsuario.PROFESSOR);
                    Professor p = new Professor(idPessoa);
                    new PessoaDAO().carregarPessoa(p);
                } else if (tipoUsuario == TipoUsuario.ALUNO.getValorTipoUsuario()) {
                    usuario.setTipo(TipoUsuario.ALUNO);
                    Aluno a = new Aluno(idPessoa);
                    new PessoaDAO().carregarPessoa(a);
                }

                usuarios.add(usuario);
            }

            return usuarios;
        } catch (Exception ex) {
            return null;
        } finally {
            bd.fecharConexao();
        }
    }

    @Override
    public IEntidade consultarId(int id) {

        BancoDados bd = new BancoDados();

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }

            String query = "SELECT id, nome, tipo, id_pessoa FROM usuario WHERE id = ?";

            PreparedStatement stmt = bd.getConexao().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, id);

            Usuario usuario = null;
            int tipoUsuario = 0;
            ResultSet resultado = stmt.executeQuery();
            if (resultado.first()) {
                usuario = new Usuario();
                usuario.setId(resultado.getInt("id"));
                usuario.setNome(resultado.getString("nome"));
                usuario.setNomeAntigo(resultado.getString("nome"));

                //Define o tipo de usuário
                tipoUsuario = resultado.getInt("tipo");
                int idPessoa = resultado.getInt("id_pessoa");
                if (tipoUsuario == TipoUsuario.ADMINISTRADOR.getValorTipoUsuario()) {
                    usuario.setTipo(TipoUsuario.ADMINISTRADOR);
                } else if (tipoUsuario == TipoUsuario.PROFESSOR.getValorTipoUsuario()) {
                    usuario.setTipo(TipoUsuario.PROFESSOR);
                    Professor p = new Professor(idPessoa);
                    new PessoaDAO().carregarPessoa(p);
                } else if (tipoUsuario == TipoUsuario.ALUNO.getValorTipoUsuario()) {
                    usuario.setTipo(TipoUsuario.ALUNO);
                    Aluno a = new Aluno(idPessoa);
                    new PessoaDAO().carregarPessoa(a);
                }
            }

            return usuario;
        } catch (Exception ex) {
            return null;
        } finally {
            bd.fecharConexao();
        }
    }

    public boolean consultarNome(String nome) {
        BancoDados bd = new BancoDados();

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }

            StringBuilder query = new StringBuilder();
            query.append("SELECT COUNT(*) AS cont FROM usuario WHERE nome = ? ");

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1, nome);
            
            ResultSet resultado = stmt.executeQuery();
            
            int quantidadeUsuarios = 0;
            if (resultado.first()) {
                quantidadeUsuarios = resultado.getInt("cont");
            }

            return quantidadeUsuarios > 0;
        } catch (Exception e) {
            return false;
        } finally {
            bd.fecharConexao();
        }
    }
}
