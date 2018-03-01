package jschool.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jschool.enumeracoes.Situacao;
import jschool.enumeracoes.SituacaoAluno;
import jschool.infraestrutura.BancoDados;
import jschool.infraestrutura.ConexaoFactory;
import jschool.modelo.entidade.Aluno;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.entidade.InscricaoTurma;
import jschool.modelo.entidade.Turma;

/**
 *
 * @author andre
 */
public class InscricaoTurmaDAO implements IDAO {

    @Override
    public boolean salvar(IEntidade e) {

        InscricaoTurma it = (InscricaoTurma) e;
        BancoDados bd = null;

        try {

            String query = "INSERT INTO inscricao_turma (data_inscricao, status, "
                    + "situacao_aluno, id_aluno, id_turma) VALUES (?, ?, ?, ? ,?)";

            bd = new BancoDados();
            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao conectar ao banco de dados");
            }

            PreparedStatement stmt = bd.getConexao().prepareStatement(query);
            stmt.setDate(1, new java.sql.Date(it.getDataInscricao().getTime()));
            stmt.setInt(2, it.getStatus().getValorSituacao());
            stmt.setInt(3, it.getSituacaoAluno().getValorSituacao());
            stmt.setInt(4, it.getAluno().getId());
            stmt.setInt(5, it.getTurma().getId());

            new TurmaDAO().diminuirVagasTurmas(it.getTurma());

            return stmt.executeUpdate() > 0;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        } finally {
            bd.fecharConexao();
        }
    }

    @Override
    public boolean atualizar(IEntidade e) {
        InscricaoTurma it = (InscricaoTurma) e;
        BancoDados bd = null;

        try {

            String query = "UPDATE inscricao_turma SET id_aluno = ?, id_turma = ? WHERE id = ?";

            bd = new BancoDados();
            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao conectar ao banco de dados");
            }

            PreparedStatement stmt = bd.getConexao().prepareStatement(query);
            stmt.setInt(1, it.getAluno().getId());
            stmt.setInt(2, it.getTurma().getId());

            return stmt.executeUpdate() > 0;
        } catch (Exception ex) {
            return false;
        } finally {
            bd.fecharConexao();
        }
    }

    @Override
    public boolean excluir(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<IEntidade> consultarTodos() {

        ArrayList<IEntidade> inscricoes = new ArrayList();
        BancoDados bd = new BancoDados();

        try {

            String query = "SELECT id, data_inscricao, status, "
                    + "situacao_aluno, id_aluno, id_turma "
                    + "FROM inscricao_turma";

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao conectar ao banco de dados");
            }

            PreparedStatement stmt = bd.getConexao().prepareStatement(query);
            ResultSet resultado = stmt.executeQuery();

            InscricaoTurma it;
            int situacaoAluno;
            while (resultado.next()) {
                it = new InscricaoTurma();
                it.setId(resultado.getInt("id"));
                it.setDataInscricao(resultado.getDate("data_inscricao"));
                it.setAluno((Aluno) new AlunoDAO().consultarId(resultado.getInt("id_aluno")));
                it.setTurma((Turma) new TurmaDAO().consultarId(resultado.getInt("id_turma")));
                it.setStatus(resultado.getInt("status") == Situacao.ATIVO.getValorSituacao() ? Situacao.ATIVO : Situacao.INATIVO);

                situacaoAluno = resultado.getInt("situacao_aluno");
                if (situacaoAluno == SituacaoAluno.APROVADO.getValorSituacao()) {
                    it.setSituacaoAluno(SituacaoAluno.APROVADO);
                } else if (situacaoAluno == SituacaoAluno.CURSANDO.getValorSituacao()) {
                    it.setSituacaoAluno(SituacaoAluno.CURSANDO);
                } else {
                    it.setSituacaoAluno(SituacaoAluno.REPROVADO);
                }

                inscricoes.add(it);
            }

            return inscricoes;
        } catch (Exception ex) {
            return null;
        } finally {
            bd.fecharConexao();
        }
    }

    @Override
    public ArrayList<IEntidade> consultar(String criterio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IEntidade consultarId(int id) {

        //InscricaoTurma inscricao;
        BancoDados bd = new BancoDados();

        try {

            StringBuilder query = new StringBuilder();
            query.append("SELECT it.id, data_inscricao, it.status, ");
            query.append("situacao_aluno, id_aluno, id_turma, media_aluno ");
            query.append("FROM inscricao_turma it ");
            query.append("WHERE id = ?");

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao conectar ao banco de dados");
            }

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, id);

            ResultSet resultado = stmt.executeQuery();

            InscricaoTurma it = null;
            int situacaoAluno;
            if (resultado.first()) {
                it = new InscricaoTurma();
                it.setId(resultado.getInt("id"));
                it.setDataInscricao(resultado.getDate("data_inscricao"));
                it.setAluno((Aluno) new AlunoDAO().consultarId(resultado.getInt("id_aluno")));
                it.setTurma((Turma) new TurmaDAO().consultarId(resultado.getInt("id_turma")));
                it.setNota(resultado.getDouble("media_aluno"));
                it.setStatus(resultado.getInt("status") == Situacao.ATIVO.getValorSituacao() ? Situacao.ATIVO : Situacao.INATIVO);

                situacaoAluno = resultado.getInt("situacao_aluno");
                if (situacaoAluno == SituacaoAluno.APROVADO.getValorSituacao()) {
                    it.setSituacaoAluno(SituacaoAluno.APROVADO);
                } else if (situacaoAluno == SituacaoAluno.CURSANDO.getValorSituacao()) {
                    it.setSituacaoAluno(SituacaoAluno.CURSANDO);
                } else {
                    it.setSituacaoAluno(SituacaoAluno.REPROVADO);
                }
            }

            return it;
        } catch (Exception ex) {
            return null;
        } finally {
            bd.fecharConexao();
        }
    }

    public ArrayList<IEntidade> pesquisar(String aluno, String curso) {
        ArrayList<IEntidade> inscricoes;
        BancoDados bd = null;

        try {

            StringBuilder query = new StringBuilder();
            query.append("SELECT it.id, data_inscricao, it.status, ");
            query.append("situacao_aluno, id_aluno, id_turma ");
            query.append("FROM inscricao_turma it ");
            query.append("INNER JOIN turma t ON t.id = it.id_turma ");
            query.append("INNER JOIN curso c ON c.id = t.id_curso ");
            query.append("INNER JOIN pessoa a ON it.id_aluno = a.id ");
            query.append("WHERE a.nome ILIKE ? AND c.nome ILIKE ? ");

            inscricoes = new ArrayList();
            bd = new BancoDados();
            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao conectar ao banco de dados");
            }

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setString(1, "%" + aluno + "%");
            stmt.setString(2, "%" + curso + "%");

            ResultSet resultado = stmt.executeQuery();

            InscricaoTurma it;
            int situacaoAluno;
            while (resultado.next()) {
                it = new InscricaoTurma();
                it.setId(resultado.getInt("id"));
                it.setDataInscricao(resultado.getDate("data_inscricao"));
                it.setAluno((Aluno) new AlunoDAO().consultarId(resultado.getInt("id_aluno")));
                it.setTurma((Turma) new TurmaDAO().consultarId(resultado.getInt("id_turma")));
                it.setStatus(resultado.getInt("status") == Situacao.ATIVO.getValorSituacao() ? Situacao.ATIVO : Situacao.INATIVO);

                situacaoAluno = resultado.getInt("situacao_aluno");
                if (situacaoAluno == SituacaoAluno.APROVADO.getValorSituacao()) {
                    it.setSituacaoAluno(SituacaoAluno.APROVADO);
                } else if (situacaoAluno == SituacaoAluno.CURSANDO.getValorSituacao()) {
                    it.setSituacaoAluno(SituacaoAluno.CURSANDO);
                } else {
                    it.setSituacaoAluno(SituacaoAluno.REPROVADO);
                }

                inscricoes.add(it);
            }

            return inscricoes;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        } finally {
            bd.fecharConexao();
        }
    }

    public boolean alterarSituacao(InscricaoTurma inscricao) {

        Connection bd = null;

        try {
            String sql = "UPDATE inscricao_turma SET status = ? WHERE id = ?";

            bd = ConexaoFactory.getConnection();
            PreparedStatement stmt = bd.prepareStatement(sql);
            stmt.setInt(1, inscricao.getStatus() == Situacao.ATIVO ? Situacao.INATIVO.getValorSituacao() : Situacao.ATIVO.getValorSituacao());
            stmt.setInt(2, inscricao.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        } finally {
            ConexaoFactory.closeConnection(bd);
        }
    }

    public boolean alunoJaInscrito(InscricaoTurma inscricao) {

        Connection bd = null;

        try {
            String sql = "SELECT COUNT(*) AS quantidade FROM inscricao_turma "
                    + "WHERE id_aluno = ? and id_turma = ?";

            bd = ConexaoFactory.getConnection();
            PreparedStatement stmt = bd.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, inscricao.getAluno().getId());
            stmt.setInt(2, inscricao.getTurma().getId());

            int regs = 0;
            ResultSet resultado = stmt.executeQuery();
            if (resultado.first()) {
                regs = resultado.getInt("quantidade");
            }

            return regs > 0;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        } finally {
            ConexaoFactory.closeConnection(bd);
        }
    }

    public ArrayList<IEntidade> consultar(Turma t) {

        ArrayList<IEntidade> inscricoes = new ArrayList();
        BancoDados bd = new BancoDados();

        try {

            StringBuilder query = new StringBuilder();
            query.append("SELECT id, data_inscricao, status, ");
            query.append("situacao_aluno, id_aluno, id_turma, media_aluno ");
            query.append("FROM inscricao_turma ");
            query.append("WHERE id_turma = ? ");

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao conectar ao banco de dados");
            }

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setInt(1, t.getId());

            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {

                InscricaoTurma it = new InscricaoTurma();
                it.setId(resultado.getInt("id"));
                it.setDataInscricao(resultado.getDate("data_inscricao"));
                it.setAluno((Aluno) new AlunoDAO().consultarId(resultado.getInt("id_aluno")));
                it.setTurma((Turma) new TurmaDAO().consultarId(resultado.getInt("id_turma")));
                it.setNota(resultado.getDouble("media_aluno"));
                it.setStatus(resultado.getInt("status") == Situacao.ATIVO.getValorSituacao() ? Situacao.ATIVO : Situacao.INATIVO);

                int situacaoAluno = resultado.getInt("situacao_aluno");
                if (situacaoAluno == SituacaoAluno.APROVADO.getValorSituacao()) {
                    it.setSituacaoAluno(SituacaoAluno.APROVADO);
                } else if (situacaoAluno == SituacaoAluno.CURSANDO.getValorSituacao()) {
                    it.setSituacaoAluno(SituacaoAluno.CURSANDO);
                } else {
                    it.setSituacaoAluno(SituacaoAluno.REPROVADO);
                }

                inscricoes.add(it);
            }

            return inscricoes;
        } catch (Exception ex) {
            return null;
        } finally {
            bd.fecharConexao();
        }
    }
    
    public ArrayList<IEntidade> consultar(Aluno a){
        ArrayList<IEntidade> inscricoes = new ArrayList();
        BancoDados bd = new BancoDados();

        try {

            StringBuilder query = new StringBuilder();
            query.append("SELECT id, data_inscricao, status, ");
            query.append("situacao_aluno, id_aluno, id_turma, media_aluno ");
            query.append("FROM inscricao_turma ");
            query.append("WHERE id_aluno = ? ");

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao conectar ao banco de dados");
            }

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setInt(1, a.getId());

            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {

                InscricaoTurma it = new InscricaoTurma();
                it.setId(resultado.getInt("id"));
                it.setDataInscricao(resultado.getDate("data_inscricao"));
                it.setAluno(a);
                it.setTurma((Turma) new TurmaDAO().consultarId(resultado.getInt("id_turma")));
                it.setNota(resultado.getDouble("media_aluno"));
                it.setStatus(resultado.getInt("status") == Situacao.ATIVO.getValorSituacao() ? Situacao.ATIVO : Situacao.INATIVO);

                int situacaoAluno = resultado.getInt("situacao_aluno");
                if (situacaoAluno == SituacaoAluno.APROVADO.getValorSituacao()) {
                    it.setSituacaoAluno(SituacaoAluno.APROVADO);
                } else if (situacaoAluno == SituacaoAluno.CURSANDO.getValorSituacao()) {
                    it.setSituacaoAluno(SituacaoAluno.CURSANDO);
                } else {
                    it.setSituacaoAluno(SituacaoAluno.REPROVADO);
                }

                inscricoes.add(it);
            }

            return inscricoes;
        } catch (Exception ex) {
            return null;
        } finally {
            bd.fecharConexao();
        }
    }

    public boolean lancarNota(InscricaoTurma i) {

        BancoDados bd = new BancoDados();

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }
            
            StringBuilder query = new StringBuilder();
            query.append("UPDATE inscricao_turma ");
            query.append("SET media_aluno = ?, situacao_aluno = ? ");
            query.append("WHERE id = ? ");
            
            if(i.getNota() >= i.getTurma().getMediaAprovacao()){
                i.setSituacaoAluno(SituacaoAluno.APROVADO);
            } else {
                i.setSituacaoAluno(SituacaoAluno.REPROVADO);
            }
            
            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setDouble(1, i.getNota());
            stmt.setInt(2, i.getSituacaoAluno().getValorSituacao());
            stmt.setInt(3, i.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        } finally {
            bd.fecharConexao();
        }
    }
}
