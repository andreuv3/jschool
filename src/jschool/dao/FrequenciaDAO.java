package jschool.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import jschool.infraestrutura.BancoDados;
import jschool.modelo.entidade.Frequencia;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.entidade.InscricaoTurma;
import jschool.modelo.entidade.Turma;

/**
 *
 * @author andre
 */
public class FrequenciaDAO implements IDAO {

    @Override
    public boolean salvar(IEntidade e) {

        Frequencia f = (Frequencia) e;
        BancoDados bd = new BancoDados();

        try {
            
            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }
            
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO frequencia (data, id_inscricao_turma, presente) ");
            query.append("VALUES (?, ?, ?) ");
            
            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setDate(1, new java.sql.Date(f.getData().getTime()));
            stmt.setInt(2, f.getMatricula().getId());
            stmt.setBoolean(3, f.isPresente());
            
            return stmt.executeUpdate() > 0;
        } catch (Exception ex) {
            return false;
        } finally {
            bd.fecharConexao();
        }
    }

    @Override
    public boolean atualizar(IEntidade e) {
        
        Frequencia f = (Frequencia) e;
        BancoDados bd = new BancoDados();

        try {
            
            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }
            
            StringBuilder query = new StringBuilder();
            query.append("UPDATE frequencia ");
            query.append("SET presente = ? ");
            query.append("WHERE id = ? ");
            
            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setBoolean(1, f.isPresente());
            stmt.setInt(2, f.getId());
            
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
        ArrayList<IEntidade> frequencias = new ArrayList();
        BancoDados bd = new BancoDados();

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }

            StringBuilder query = new StringBuilder();
            query.append("SELECT f.id as id_frequencia, data, presente, id_inscricao_turma ");
            query.append("FROM frequencia f ");

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                Frequencia f = new Frequencia();
                f.setId(resultado.getByte("id_frequencia"));
                f.setPresente(resultado.getBoolean("presente"));
                f.setData(resultado.getDate("data"));
                f.setMatricula((InscricaoTurma) new InscricaoTurmaDAO().consultarId(resultado.getInt("id_inscricao_turma")));

                frequencias.add(f);
            }

            return frequencias;
        } catch (Exception e) {
            return null;
        } finally {

        }
    }

    @Override
    public ArrayList<IEntidade> consultar(String criterio) {
        throw new UnsupportedOperationException("Operação não suportada");
    }

    @Override
    public IEntidade consultarId(int id) {
        throw new UnsupportedOperationException("Operação não suportada");
    }

    public ArrayList<IEntidade> consultar(Turma t, Date data) {

        ArrayList<IEntidade> frequencias = new ArrayList();
        BancoDados bd = new BancoDados();

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }

            StringBuilder query = new StringBuilder();
            query.append("SELECT f.id as id_frequencia, data, presente, id_inscricao_turma ");
            query.append("FROM frequencia f ");
            query.append("INNER JOIN inscricao_turma it ");
            query.append("ON f.id_inscricao_turma = it.id ");
            query.append("INNER JOIN pessoa a ");
            query.append("ON it.id_aluno = a.id ");
            query.append("WHERE data = ? ");
            query.append("AND it.id_turma = ? ");
            query.append("ORDER BY a.nome");

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setDate(1, new java.sql.Date(data.getTime()));
            stmt.setInt(2, t.getId());

            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                Frequencia f = new Frequencia();
                f.setId(resultado.getByte("id_frequencia"));
                f.setPresente(resultado.getBoolean("presente"));
                f.setData(resultado.getDate("data"));
                f.setMatricula((InscricaoTurma) new InscricaoTurmaDAO().consultarId(resultado.getInt("id_inscricao_turma")));

                frequencias.add(f);
            }

            return frequencias;
        } catch (Exception e) {
            return null;
        } finally {
            bd.fecharConexao();
        }
    }
    
    public ArrayList<IEntidade> consultar(InscricaoTurma inscricaoTurma){
        ArrayList<IEntidade> frequencias = new ArrayList();
        BancoDados bd = new BancoDados();

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }

            StringBuilder query = new StringBuilder();
            query.append("SELECT f.id as id_frequencia, data, presente, id_inscricao_turma ");
            query.append("FROM frequencia f ");
            query.append("WHERE id_inscricao_turma = ? ");
            query.append("ORDER BY data");

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setInt(1, inscricaoTurma.getId());

            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                Frequencia f = new Frequencia();
                f.setId(resultado.getByte("id_frequencia"));
                f.setPresente(resultado.getBoolean("presente"));
                f.setData(resultado.getDate("data"));
                f.setMatricula((InscricaoTurma) new InscricaoTurmaDAO().consultarId(resultado.getInt("id_inscricao_turma")));

                frequencias.add(f);
            }

            return frequencias;
        } catch (Exception e) {
            return null;
        } finally {

        }
    }

    public boolean frequenciaRegistrada(Turma t, Date data) {

        BancoDados bd = new BancoDados();
        int numeroFrequencias = 0;

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }

            StringBuilder query = new StringBuilder();
            query.append("SELECT COUNT(*) AS numero_frequencias ");
            query.append("FROM frequencia f ");
            query.append("INNER JOIN inscricao_turma it ");
            query.append("ON f.id_inscricao_turma = it.id ");
            query.append("WHERE it.id_turma = ? ");
            query.append("AND f.data = ?");

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, t.getId());
            stmt.setDate(2, new java.sql.Date(data.getTime()));

            ResultSet resultado = stmt.executeQuery();
            if (resultado.first()) {
                numeroFrequencias = resultado.getInt("numero_frequencias");
            }

            return numeroFrequencias > 0;
        } catch (Exception e) {
            return false;
        } finally {
            bd.fecharConexao();
        }

    }
}
