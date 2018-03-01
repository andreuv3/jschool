package jschool.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jschool.modelo.entidade.Curso;
import jschool.modelo.entidade.IEntidade;
import jschool.enumeracoes.Situacao;
import jschool.infraestrutura.BancoDados;

/**
 *
 * @author andre
 */
public class CursoDAO implements IDAO {

    @Override
    public boolean salvar(IEntidade e) {

        Curso curso = (Curso) e;
        BancoDados bd = new BancoDados();

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados:");
            }

            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO curso (nome, ementa, horas_duracao, vagas_recomendadas, status) ");
            query.append("VALUES (?, ?, ?, ?, ?)");

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getEmenta());
            stmt.setDouble(3, curso.getHoraDuracao());
            stmt.setInt(4, curso.getVagasRecomendadas());
            stmt.setInt(5, curso.getSituacaoValor().getValorSituacao());

            return stmt.executeUpdate() > 0;
        } catch (Exception ex) {
            return false;
        } finally {
            bd.fecharConexao();
        }
    }

    @Override
    public boolean atualizar(IEntidade e) {

        Curso curso = (Curso) e;
        BancoDados bd = new BancoDados();

        try {
            
            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados:");
            }
            
            StringBuilder query = new StringBuilder();
            query.append("UPDATE curso ");
            query.append("SET nome = ?, ementa = ?, ");
            query.append("horas_duracao = ?, vagas_recomendadas = ? ");
            query.append("WHERE id = ?");
            
            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getEmenta());
            stmt.setDouble(3, curso.getHoraDuracao());
            stmt.setInt(4, curso.getVagasRecomendadas());
            stmt.setInt(5, curso.getId());

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
        
        ArrayList<IEntidade> cursos = new ArrayList<>();
        BancoDados bd = new BancoDados();

        try {
            
            if(!bd.abrirConexao()){
                throw new RuntimeException("Problema ao abrir conexão com banco de dados:");
            }
            
            StringBuilder query = new StringBuilder();
            query.append("SELECT id, nome, ementa, horas_duracao, vagas_recomendadas, status ");
            query.append("FROM curso ");
            query.append("ORDER BY nome, id");

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                Curso curso = new Curso();
                curso.setId(resultado.getInt("id"));
                curso.setNome(resultado.getString("nome"));
                curso.setEmenta(resultado.getString("ementa"));
                curso.setHoraDuracao(resultado.getDouble("horas_duracao"));
                curso.setVagasRecomendadas(resultado.getInt("vagas_recomendadas"));
                curso.setSituacao(resultado.getInt("status") == Situacao.ATIVO.getValorSituacao() ? Situacao.ATIVO : Situacao.INATIVO);

                cursos.add(curso);
            }
            
            return cursos;
        } catch (Exception ex) {
            return null;
        } finally {
            bd.fecharConexao();
        }
    }

    public ArrayList<IEntidade> consultarTodosAtivos() {

        ArrayList<IEntidade> cursos = new ArrayList<>();
        BancoDados bd = new BancoDados();

        try {
            
            if(!bd.abrirConexao()){
                throw new RuntimeException("Problema ao abrir conexão com banco de dados:");
            }
            
            StringBuilder query = new StringBuilder();
            query.append("SELECT id, nome, ementa, horas_duracao, vagas_recomendadas, status ");
            query.append("FROM curso ");
            query.append("WHERE status = 1 ");
            query.append("ORDER BY nome, id");
            
            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                Curso curso = new Curso();
                curso.setId(resultado.getInt("id"));
                curso.setNome(resultado.getString("nome"));
                curso.setEmenta(resultado.getString("ementa"));
                curso.setHoraDuracao(resultado.getDouble("horas_duracao"));
                curso.setVagasRecomendadas(resultado.getInt("vagas_recomendadas"));
                curso.setSituacao(resultado.getInt("status") == Situacao.ATIVO.getValorSituacao() ? Situacao.ATIVO : Situacao.INATIVO);

                cursos.add(curso);
            }

            return cursos;
        } catch (Exception ex) {
            return null;
        } finally {
            bd.fecharConexao();
        }
    }

    @Override
    public ArrayList<IEntidade> consultar(String criterio) {

        ArrayList<IEntidade> cursos = new ArrayList();
        BancoDados bd = new BancoDados();

        try {
            
            if(!bd.abrirConexao()){
                throw new RuntimeException("Problema ao abrir conexão com banco de dados:");
            }
            
            StringBuilder query = new StringBuilder();
            query.append("SELECT id, nome, ementa, horas_duracao, vagas_recomendadas, status ");
            query.append("FROM curso ");
            query.append("WHERE nome ILIKE ? ");
            query.append("ORDER BY nome, id");
            
            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setString(1, "%" + criterio + "%");

            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                Curso curso = new Curso();
                curso.setId(resultado.getInt("id"));
                curso.setNome(resultado.getString("nome"));
                curso.setEmenta(resultado.getString("ementa"));
                curso.setHoraDuracao(resultado.getDouble("horas_duracao"));
                curso.setVagasRecomendadas(resultado.getInt("vagas_recomendadas"));
                curso.setSituacao(resultado.getInt("status") == Situacao.ATIVO.getValorSituacao() ? Situacao.ATIVO : Situacao.INATIVO);

                cursos.add(curso);
            }

            return cursos;
        } catch (Exception ex) {
            return null;
        } finally {
            bd.fecharConexao();
        }
    }

    public ArrayList<IEntidade> consultarAtivos(String criterio) {

        ArrayList<IEntidade> cursos = new ArrayList();
        BancoDados bd = new BancoDados();

        try {
            
            if(!bd.abrirConexao()){
                throw new RuntimeException("Problema ao abrir conexão com banco de dados:");
            }
            
            StringBuilder query = new StringBuilder();
            query.append("SELECT id, nome, ementa, horas_duracao, vagas_recomendadas, status ");
            query.append("FROM curso ");
            query.append("WHERE status = 1 AND nome ILIKE ? ");
            query.append("ORDER BY nome, id");
            
            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setString(1, "%" + criterio + "%");

            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                Curso curso = new Curso();
                curso.setId(resultado.getInt("id"));
                curso.setNome(resultado.getString("nome"));
                curso.setEmenta(resultado.getString("ementa"));
                curso.setHoraDuracao(resultado.getDouble("horas_duracao"));
                curso.setVagasRecomendadas(resultado.getInt("vagas_recomendadas"));
                curso.setSituacao(resultado.getInt("status") == Situacao.ATIVO.getValorSituacao() ? Situacao.ATIVO : Situacao.INATIVO);

                cursos.add(curso);
            }

            return cursos;
        } catch (Exception ex) {
            return null;
        } finally {
            bd.fecharConexao();
        }
    }

    @Override
    public IEntidade consultarId(int id) {

        Curso curso = null;
        BancoDados bd = new BancoDados();

        try {

            if(!bd.abrirConexao()){
                throw new RuntimeException("Problema ao abrir conexão com banco de dados:");
            }
            
            StringBuilder query = new StringBuilder();
            query.append("SELECT id, nome, ementa, horas_duracao, vagas_recomendadas, status ");
            query.append("FROM curso ");
            query.append("WHERE id = ?");
            
            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, id);

            ResultSet resultado = stmt.executeQuery();

            if (resultado.first()) {
                curso = new Curso();
                curso.setId(resultado.getInt("id"));
                curso.setNome(resultado.getString("nome"));
                curso.setEmenta(resultado.getString("ementa"));
                curso.setHoraDuracao(resultado.getDouble("horas_duracao"));
                curso.setVagasRecomendadas(resultado.getInt("vagas_recomendadas"));
                curso.setSituacao(resultado.getInt("status") == Situacao.ATIVO.getValorSituacao() ? Situacao.ATIVO : Situacao.INATIVO);
            }

            return curso;

        } catch (SQLException ex) {
            return null;
        } finally {
            bd.fecharConexao();
        }
    }

    public boolean alterarSituacao(Curso curso) {

        BancoDados bd = new BancoDados();

        try {
            
            if(!bd.abrirConexao()){
                throw new RuntimeException("Problema ao abrir conexão com banco de dados:");
            }
            
            StringBuilder query = new StringBuilder();
            query.append("UPDATE curso ");
            query.append("SET status = ? ");
            query.append("WHERE id = ?");
            
            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setInt(1, curso.getSituacaoValor() == Situacao.ATIVO ? Situacao.INATIVO.getValorSituacao() : Situacao.ATIVO.getValorSituacao());
            stmt.setInt(2, curso.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            return false;
        } finally {
            bd.fecharConexao();
        }
    }
}