package jschool.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.entidade.Professor;
import jschool.enumeracoes.Situacao;
import jschool.infraestrutura.BancoDados;

/**
 *
 * @author andre
 */
public class ProfessorDAO implements IDAO {

    @Override
    public boolean salvar(IEntidade e) {

        Professor prof = (Professor) e;
        BancoDados bd = new BancoDados();

        try {

            //Salva os dados na tabela pessoa e recupera o ID gerado
            int idPessoa = new PessoaDAO().salvarPessoa(e);
            if (idPessoa <= 0) {
                return false;
            }

            //Salva os dados na tabela professor
            prof.setId(idPessoa);

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados:");
            }

            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO professor (id_pessoa, data_admissao, numero_ctps) ");
            query.append("VALUES (?, ?, ?)");

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setInt(1, prof.getId());
            stmt.setDate(2, new java.sql.Date(prof.getDataAdmissao().getTime()));
            stmt.setString(3, prof.getNumeroCtps());

            return stmt.executeUpdate() > 0;
        } catch (Exception ex) {
            return false;
        } finally {
            bd.fecharConexao();
        }
    }

    @Override
    public boolean atualizar(IEntidade e) {

        Professor prof = (Professor) e;
        BancoDados bd = new BancoDados();

        try {

            if (!new PessoaDAO().atualizar(prof)) {
                return false;
            }

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados:");
            }

            StringBuilder query = new StringBuilder();
            query.append("UPDATE professor ");
            query.append("SET data_admissao = ?, numero_ctps = ? ");
            query.append("WHERE id_pessoa = ?");

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setDate(1, new java.sql.Date(prof.getDataAdmissao().getTime()));
            stmt.setString(2, prof.getNumeroCtps());
            stmt.setInt(3, prof.getId());

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
        throw new UnsupportedOperationException("Operação não suportada");
    }

    @Override
    public ArrayList<IEntidade> consultar(String criterio) {
        throw new UnsupportedOperationException("Operação não suportada");
    }

    @Override
    public IEntidade consultarId(int id) {

        BancoDados bd = new BancoDados();

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }

            StringBuilder query = new StringBuilder();
            query.append("SELECT id_pessoa, numero_ctps, data_admissao, data_demissao ");
            query.append("FROM professor ");
            query.append("WHERE id_pessoa = ?");

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();

            Professor professor = null;
            if (resultado.first()) {
                //Carregar dados do professor
                professor = new Professor();
                professor.setId(resultado.getInt("id_pessoa"));
                professor.setNumeroCtps(resultado.getString("numero_ctps"));
                professor.setDataAdmissao(resultado.getDate("data_admissao"));
                professor.setDataDemisao(resultado.getDate("data_demissao"));

                //Carrega dados da pessoa
                new PessoaDAO().carregarPessoa(professor);
                if (professor == null) {
                    throw new RuntimeException("Problema ao carregar dados da pessoa");
                }
            }

            return professor;
        } catch (Exception ex) {
            return null;
        } finally {
            bd.fecharConexao();
        }
    }

    public boolean alterarSituacao(Professor professor) {

        try {
            boolean situacaoAlterada = new PessoaDAO().alterarSituacao(professor);
            if (situacaoAlterada) {
                ajustarDatasContratuais(professor);
            }

            return situacaoAlterada;
        } catch (Exception ex) {
            return false;
        }
    }

    private boolean ajustarDatasContratuais(Professor professor) {

        BancoDados bd = new BancoDados();

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }

            StringBuilder query = new StringBuilder();
            query.append("UPDATE professor ");
            query.append("SET data_admissao = ?, data_demissao = ? ");
            query.append("id_pessoa = ?");

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            Date hoje = new Date();
            if (professor.getSituacao() == Situacao.INATIVO) {
                stmt.setDate(1, new java.sql.Date(hoje.getTime()));
                stmt.setDate(2, null);
            } else {
                stmt.setDate(1, new java.sql.Date(professor.getDataAdmissao().getTime()));
                stmt.setDate(2, new java.sql.Date(hoje.getTime()));
            }
            stmt.setInt(3, professor.getId());

            int resultado = stmt.executeUpdate();

            return resultado > 0;
        } catch (Exception ex) {
            return false;
        } finally {
            bd.fecharConexao();
        }
    }

    public ArrayList<IEntidade> consultarTodos(boolean somenteAtivos) {

        ArrayList<IEntidade> professores = new ArrayList();
        BancoDados bd = new BancoDados();

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }

            StringBuilder query = new StringBuilder();
            query.append("SELECT id_pessoa, numero_ctps, data_admissao, data_demissao ");
            query.append("FROM professor ");
            query.append("ORDER BY id_pessoa ");

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            ResultSet resultado = stmt.executeQuery();

            Professor professor;
            while (resultado.next()) {

                //Carrega dados especificos do professor
                professor = new Professor();
                professor.setId(resultado.getInt("id_pessoa"));
                professor.setNumeroCtps(resultado.getString("numero_ctps"));
                professor.setDataAdmissao(resultado.getDate("data_admissao"));
                professor.setDataDemisao(resultado.getDate("data_demissao"));

                //Carrega dados da pessoa
                new PessoaDAO().carregarPessoa(professor);
                if (professor == null) {
                    throw new RuntimeException("Problema ao carregar dados da pessoa");
                }

                if(!somenteAtivos){
                    professores.add((Professor) professor);
                } else if(professor.getSituacao() == Situacao.ATIVO){
                    professores.add((Professor) professor);
                }
            }

            return professores;
        } catch (Exception ex) {
            return null;
        } finally {
            bd.fecharConexao();
        }
    }

    public ArrayList<IEntidade> consultar(String criterio, boolean somenteAtivos) {

        ArrayList<IEntidade> professores = new ArrayList();
        BancoDados bd = new BancoDados();

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }
            
            StringBuilder query = new StringBuilder();
            query.append("SELECT id_pessoa, numero_ctps, data_admissao, data_demissao ");
            query.append("FROM professor ");
            query.append("ORDER BY id_pessoa ");
            query.append("WHERE nome ILIKE ? ");
            if (somenteAtivos) {
                query.append("AND situacao = 1 ");
            }
            query.append("ORDER BY id, nome");

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setString(1, "%" + criterio + "%");
            ResultSet resultado = stmt.executeQuery();

            Professor professor;
            while (resultado.next()) {

                //Carrega dados especificos do professor
                professor = new Professor();
                professor.setId(resultado.getInt("id"));
                professor.setNumeroCtps(resultado.getString("numero_ctps"));
                professor.setDataAdmissao(resultado.getDate("data_admissao"));
                professor.setDataDemisao(resultado.getDate("data_demissao"));

                //Carrega dados da pessoa
                new PessoaDAO().carregarPessoa(professor);
                if (professor == null) {
                    throw new RuntimeException("Problema ao carregar dados da pessoa");
                }

                professores.add((Professor) professor);
            }

            return professores;
        } catch (Exception ex) {
            return null;
        } finally {
            bd.fecharConexao();
        }
    }
}
