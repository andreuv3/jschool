package jschool.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import jschool.infraestrutura.BancoDados;
import jschool.modelo.entidade.Aluno;
import jschool.modelo.entidade.DiaLetivo;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.entidade.Professor;
import jschool.modelo.entidade.Turma;

/**
 *
 * @author andre
 */
public class DiaLetivoDAO implements IDAO {

    @Override
    public boolean salvar(IEntidade e) {

        DiaLetivo diaLetivo = (DiaLetivo) e;
        BancoDados bd = new BancoDados();

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Não foi possível abrir a conexão com o banco de dados");
            }

            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO dia_letivo_turma (id_turma, data_aula) ");
            query.append("VALUES (?, ?)");

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setInt(1, diaLetivo.getTurma().getId());
            stmt.setDate(2, new java.sql.Date(diaLetivo.getDataAula().getTime()));

            return stmt.executeUpdate() > 0;
        } catch (Exception ex) {
            return false;
        } finally {
            bd.fecharConexao();
        }
    }

    @Override
    public boolean atualizar(IEntidade e) {
        throw new UnsupportedOperationException("Operação não suportada");
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
        throw new UnsupportedOperationException("Operação não suportada");
    }

    public ArrayList<IEntidade> consultarAulasTurma(Turma t) {

        ArrayList<IEntidade> diasLetivos = new ArrayList<>();
        BancoDados bd = new BancoDados();

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Não foi possível abrir a conexão com o banco de dados");
            }

            StringBuilder query = new StringBuilder();
            query.append("SELECT id, id_turma, data_aula ");
            query.append("FROM dia_letivo_turma ");
            query.append("WHERE id_turma = ?");

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setInt(1, t.getId());

            ResultSet resultado = stmt.executeQuery();

            DiaLetivo diaLetivo;
            while (resultado.next()) {
                diaLetivo = new DiaLetivo();
                diaLetivo.setId(resultado.getInt("id"));
                diaLetivo.setDataAula(resultado.getDate("data_aula"));
                diaLetivo.setTurma(t);

                diasLetivos.add(diaLetivo);
            }

            return diasLetivos;
        } catch (Exception e) {
            return null;
        } finally {
            bd.fecharConexao();
        }
    }

    public boolean excluirTodos(int idTurma) {

        BancoDados bd = new BancoDados();

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Não foi possível abrir a conexão com o banco de dados");
            }

            StringBuilder query = new StringBuilder();
            query.append("DELETE FROM dia_letivo_turma ");
            query.append("WHERE id_turma = ?");

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setInt(1, idTurma);

            return stmt.executeUpdate() > 0;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean existeColisaoAluno(Turma turma, Aluno aluno, DiaLetivo novoDiaLetivo) {

        boolean existeColisao = false;
        BancoDados bd = new BancoDados();

        try {
            
            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }
            
            StringBuilder query = new StringBuilder();
            query.append("SELECT COUNT(*) AS colisoes ");
            query.append("FROM inscricao_turma it ");
            query.append("INNER JOIN turma t ON t.id = it.id_turma ");
            query.append("INNER JOIN dia_letivo_turma dlt ON dlt.id_turma = it.id_turma ");
            query.append("WHERE id_aluno = ? ");
            query.append("AND dlt.data_aula = ? ");
            query.append("AND it.status = 1 ");
            query.append("AND ((? >= t.horario_inicio_aula AND ? <= t.horario_fim_aula) ");
            query.append("OR (? >= t.horario_inicio_aula AND ? <= t.horario_fim_aula)) ");
            
            PreparedStatement stmt = bd.getConexao().prepareCall(query.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, aluno.getId());
            stmt.setDate(2, new java.sql.Date(novoDiaLetivo.getDataAula().getTime()));
            stmt.setTime(3, new java.sql.Time(turma.getHorarioInicial().getTimeInMillis()));
            stmt.setTime(4, new java.sql.Time(turma.getHorarioInicial().getTimeInMillis()));
            stmt.setTime(5, new java.sql.Time(turma.getHorarioFinal().getTimeInMillis()));
            stmt.setTime(6, new java.sql.Time(turma.getHorarioFinal().getTimeInMillis()));
            
            ResultSet resultado = stmt.executeQuery();
            
            int colisoes = 0;
            if(resultado.first()){
                colisoes = resultado.getInt("colisoes");
            }
            
            existeColisao = colisoes > 0; 
            return existeColisao;
        } catch (Exception e) {
            return false;
        } finally {
            bd.fecharConexao();
        }
    }
    
    public boolean existeColisaoAluno(Turma turma, Professor professor, DiaLetivo novoDiaLetivo) {

        boolean existeColisao = false;
        BancoDados bd = new BancoDados();

        try {
            
            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }
            
            StringBuilder query = new StringBuilder();
            query.append("SELECT COUNT(*) AS colisoes ");
            query.append("FROM dia_letivo_turma dlt ");
            query.append("INNER JOIN turma t ON t.id = dlt.id_turma ");
            query.append("WHERE t.id_professor = ? ");
            query.append("AND dlt.data_aula = ? ");
            query.append("AND (t.status = 1 OR t.status = 2 )");
            query.append("AND ((? >= t.horario_inicio_aula AND ? <= t.horario_fim_aula) ");
            query.append("OR (? >= t.horario_inicio_aula AND ? <= t.horario_fim_aula)) ");
            
            PreparedStatement stmt = bd.getConexao().prepareCall(query.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, professor.getId());
            stmt.setDate(2, new java.sql.Date(novoDiaLetivo.getDataAula().getTime()));
            stmt.setTime(3, new java.sql.Time(turma.getHorarioInicial().getTimeInMillis()));
            stmt.setTime(4, new java.sql.Time(turma.getHorarioInicial().getTimeInMillis()));
            stmt.setTime(5, new java.sql.Time(turma.getHorarioFinal().getTimeInMillis()));
            stmt.setTime(6, new java.sql.Time(turma.getHorarioFinal().getTimeInMillis()));
            
            ResultSet resultado = stmt.executeQuery();
            
            int colisoes = 0;
            if(resultado.first()){
                colisoes = resultado.getInt("colisoes");
            }
            
            existeColisao = colisoes > 0; 
            return existeColisao;
        } catch (Exception e) {
            return false;
        } finally {
            bd.fecharConexao();
        }
    }
}
