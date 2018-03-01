/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import jschool.enumeracoes.SituacaoTurma;
import jschool.enumeracoes.TipoMedia;
import jschool.enumeracoes.TipoUsuario;
import jschool.infraestrutura.BancoDados;
import jschool.modelo.entidade.Aula;
import jschool.modelo.entidade.Curso;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.entidade.Professor;
import jschool.modelo.entidade.Turma;
import jschool.operacoes.TurmaOP;

/**
 *
 * @author andre
 */
public class TurmaDAO implements IDAO {

    @Override
    public boolean salvar(IEntidade e) {

        String query = "INSERT INTO turma (id_curso, id_professor, data_inicio, data_fim, "
                + "horario_inicio_aula, horario_fim_aula, vagas, sala, "
                + "tipo_media, media_aprovacao, status, descricao) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        BancoDados bd = null;

        try {

            Turma t = (Turma) e;
            bd = new BancoDados();

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com o banco de dados");
            }

            PreparedStatement stmt = bd.getConexao().prepareStatement(query);
            stmt.setInt(1, t.getCurso().getId());
            stmt.setInt(2, t.getProfessor().getId());
            stmt.setDate(3, new java.sql.Date(t.getDataInicio().getTime()));
            stmt.setDate(4, new java.sql.Date(t.getDataFim().getTime()));
            stmt.setTime(5, new Time(t.getHorarioInicial().getTimeInMillis()));
            stmt.setTime(6, new Time(t.getHorarioFinal().getTimeInMillis()));
            stmt.setInt(7, t.getVagas());
            stmt.setString(8, t.getSala());
            stmt.setInt(9, t.getTipoMedia().getValorTipoMedia());
            stmt.setDouble(10, t.getMediaAprovacao());
            stmt.setInt(11, t.getSituacao().getValorSituacaoTurma());
            stmt.setString(12, t.getDescricao());

            int resultado = stmt.executeUpdate();

            return resultado > 0;
        } catch (Exception ex) {
            System.out.println("ex" + ex.getMessage());
            return false;
        } finally {
            bd.fecharConexao();
        }
    }

    @Override
    public boolean atualizar(IEntidade e) {

        BancoDados bd = null;

        try {

            StringBuilder query = new StringBuilder();
            query.append("UPDATE turma SET id_curso = ?, id_professor = ?, data_inicio = ?, data_fim = ?, ");
            query.append("horario_inicio_aula = ?, horario_fim_aula = ?, vagas = ?, sala = ?, ");
            query.append("tipo_media = ?, media_aprovacao = ?, status = ?, descricao = ? ");
            query.append("WHERE id = ?");

            Turma t = (Turma) e;
            bd = new BancoDados();

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com o banco de dados");
            }

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setInt(1, t.getCurso().getId());
            stmt.setInt(2, t.getProfessor().getId());
            stmt.setDate(3, new java.sql.Date(t.getDataInicio().getTime()));
            stmt.setDate(4, new java.sql.Date(t.getDataFim().getTime()));
            stmt.setTime(5, new Time(t.getHorarioInicial().getTimeInMillis()));
            stmt.setTime(6, new Time(t.getHorarioFinal().getTimeInMillis()));
            stmt.setInt(7, t.getVagas());
            stmt.setString(8, t.getSala());
            stmt.setInt(9, t.getTipoMedia().getValorTipoMedia());
            stmt.setDouble(10, t.getMediaAprovacao());
            stmt.setInt(11, t.getSituacao().getValorSituacaoTurma());
            stmt.setString(12, t.getDescricao());
            stmt.setInt(13, t.getId());

            //Remove todos os dias da semana que a turma tem aula
            new DiaSemanaDAO().excluirDiasSemanaTurma(t);
            //Insere os dias da semana que a turma tem aula
            for (Integer diaSemana : t.getDiasAulasSemana()) {
                new DiaSemanaDAO().salvarDiasSemanaTurma(t.getId(), diaSemana);
            }

            //Remove todos os dias letivos
            new DiaLetivoDAO().excluirTodos(t.getId());
            //Gera e insere novamente os dias letivos
            TurmaOP.gerarDiasAula(t);

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

        String query = "SELECT id, id_curso, id_professor, data_inicio, data_fim, horario_inicio_aula, horario_fim_aula, "
                + "vagas, sala, tipo_media, media_aprovacao, status, descricao "
                + "FROM turma";

        ArrayList<IEntidade> turmas;
        BancoDados bd = null;

        try {

            turmas = new ArrayList();
            bd = new BancoDados();

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com o banco de dados");
            }

            PreparedStatement stmt = bd.getConexao().prepareStatement(query);
            ResultSet resultado = stmt.executeQuery();

            Turma t;
            short tipoMedia = 0, situacao = 0;
            while (resultado.next()) {
                t = new Turma();
                t.setId(resultado.getInt("id"));
                t.setDataInicio(resultado.getDate("data_inicio"));
                t.setDataFim(resultado.getDate("data_fim"));
                t.setHorarioInicioAula(resultado.getDate("horario_inicio_aula"));
                t.setHorarioFimAula(resultado.getDate("horario_fim_aula"));
                t.setSala(resultado.getString("sala"));
                t.setDescricao(resultado.getString("descricao"));
                t.setMediaAprovacao(resultado.getDouble("media_aprovacao"));
                t.setVagas(resultado.getInt("vagas"));
                t.setCurso((Curso) new CursoDAO().consultarId(resultado.getInt("id_curso")));
                t.setProfessor((Professor) new ProfessorDAO().consultarId(resultado.getInt("id_professor")));

                tipoMedia = resultado.getShort("tipo_media");
                if (tipoMedia == TipoMedia.ARITIMETICA.getValorTipoMedia()) {
                    t.setTipoMedia(TipoMedia.ARITIMETICA);
                } else if (tipoMedia == TipoMedia.HARMONICA.getValorTipoMedia()) {
                    t.setTipoMedia(TipoMedia.HARMONICA);
                } else {
                    t.setTipoMedia(TipoMedia.PONDERADA);
                }

                situacao = resultado.getShort("status");
                if (situacao == SituacaoTurma.EM_ANDAMENTO.getValorSituacaoTurma()) {
                    t.setSituacao(SituacaoTurma.EM_ANDAMENTO);
                } else if (situacao == SituacaoTurma.ENCERRADA.getValorSituacaoTurma()) {
                    t.setSituacao(SituacaoTurma.ENCERRADA);
                } else {
                    t.setSituacao(SituacaoTurma.ABERTA);
                }

                turmas.add(t);
            }

            return turmas;
        } catch (Exception e) {
            return null;
        } finally {
            bd.fecharConexao();
        }
    }

    @Override
    public ArrayList<IEntidade> consultar(String criterio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<IEntidade> pesquisar(String curso, String professor, boolean somenteAtivos) {

        StringBuilder query = new StringBuilder();
        query.append("SELECT t.id, t.id_curso, t.id_professor, t.data_inicio, t.data_fim, t.horario_inicio_aula, t.horario_fim_aula, ");
        query.append("t.vagas, t.sala, t.tipo_media, t.media_aprovacao, t.status, t.descricao ");
        query.append("FROM turma t ");
        query.append("INNER JOIN curso c ON t.id_curso = c.id ");
        query.append("INNER JOIN pessoa p ON t.id_professor = p.id ");
        query.append("WHERE c.nome ILIKE ? ");
        query.append("AND p.nome ILIKE ? ");
        
        if(somenteAtivos)
            query.append("AND t.status = 1");

        ArrayList<IEntidade> turmas;
        BancoDados bd = null;

        try {

            turmas = new ArrayList();
            bd = new BancoDados();

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com o banco de dados");
            }

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setString(1, "%" + curso + "%");
            stmt.setString(2, "%" + professor + "%");

            System.out.println("%" + curso + "%");
            System.out.println("%" + professor + "%");

            ResultSet resultado = stmt.executeQuery();

            Turma t;
            short tipoMedia = 0, situacao = 0;
            while (resultado.next()) {
                t = new Turma();
                t.setId(resultado.getInt("id"));
                t.setDataInicio(resultado.getDate("data_inicio"));
                t.setDataFim(resultado.getDate("data_fim"));
                t.setHorarioInicioAula(resultado.getDate("horario_inicio_aula"));
                t.setHorarioFimAula(resultado.getDate("horario_fim_aula"));
                t.setSala(resultado.getString("sala"));
                t.setDescricao(resultado.getString("descricao"));
                t.setMediaAprovacao(resultado.getDouble("media_aprovacao"));
                t.setVagas(resultado.getInt("vagas"));
                t.setCurso((Curso) new CursoDAO().consultarId(resultado.getInt("id_curso")));
                t.setProfessor((Professor) new ProfessorDAO().consultarId(resultado.getInt("id_professor")));

                tipoMedia = resultado.getShort("tipo_media");
                if (tipoMedia == TipoMedia.ARITIMETICA.getValorTipoMedia()) {
                    t.setTipoMedia(TipoMedia.ARITIMETICA);
                } else if (tipoMedia == TipoMedia.HARMONICA.getValorTipoMedia()) {
                    t.setTipoMedia(TipoMedia.HARMONICA);
                } else {
                    t.setTipoMedia(TipoMedia.PONDERADA);
                }

                situacao = resultado.getShort("status");
                if (situacao == SituacaoTurma.EM_ANDAMENTO.getValorSituacaoTurma()) {
                    t.setSituacao(SituacaoTurma.EM_ANDAMENTO);
                } else if (situacao == SituacaoTurma.ENCERRADA.getValorSituacaoTurma()) {
                    t.setSituacao(SituacaoTurma.ENCERRADA);
                } else {
                    t.setSituacao(SituacaoTurma.ABERTA);
                }

                turmas.add(t);
            }

            return turmas;
        } catch (Exception e) {

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
                throw new RuntimeException("Problema ao abrir conexão com o banco de dados");
            }

            StringBuilder query = new StringBuilder();
            query.append("SELECT id, id_curso, id_professor, data_inicio, data_fim, horario_inicio_aula, ");
            query.append("horario_fim_aula, vagas, sala, tipo_media, media_aprovacao, status, descricao ");
            query.append("FROM turma ");
            query.append("WHERE id = ?");

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();

            Turma t = null;
            short tipoMedia = 0, situacao = 0;
            if (resultado.first()) {
                t = new Turma();
                t.setId(resultado.getInt("id"));
                t.setDataInicio(resultado.getDate("data_inicio"));
                t.setDataFim(resultado.getDate("data_fim"));
                t.setHorarioFinal(resultado.getTime("horario_fim_aula").getTime());
                t.setHorarioInicial(resultado.getTime("horario_inicio_aula").getTime());
                t.setSala(resultado.getString("sala"));
                t.setDescricao(resultado.getString("descricao"));
                t.setMediaAprovacao(resultado.getDouble("media_aprovacao"));
                t.setVagas(resultado.getInt("vagas"));
                t.setCurso((Curso) new CursoDAO().consultarId(resultado.getInt("id_curso")));
                t.setProfessor((Professor) new ProfessorDAO().consultarId(resultado.getInt("id_professor")));

                tipoMedia = resultado.getShort("tipo_media");
                if (tipoMedia == TipoMedia.ARITIMETICA.getValorTipoMedia()) {
                    t.setTipoMedia(TipoMedia.ARITIMETICA);
                } else if (tipoMedia == TipoMedia.HARMONICA.getValorTipoMedia()) {
                    t.setTipoMedia(TipoMedia.HARMONICA);
                } else {
                    t.setTipoMedia(TipoMedia.PONDERADA);
                }

                situacao = resultado.getShort("status");
                if (situacao == SituacaoTurma.EM_ANDAMENTO.getValorSituacaoTurma()) {
                    t.setSituacao(SituacaoTurma.EM_ANDAMENTO);
                } else if (situacao == SituacaoTurma.ENCERRADA.getValorSituacaoTurma()) {
                    t.setSituacao(SituacaoTurma.ENCERRADA);
                } else {
                    t.setSituacao(SituacaoTurma.ABERTA);
                }

                t.setDiasAulasSemana(new DiaSemanaDAO().consultarDiasTurma(t.getId()));
            }

            return t;
        } catch (Exception e) {
            return null;
        } finally {
            bd.fecharConexao();
        }
    }

    public boolean alterarSituacaoTurma(int idTurma, SituacaoTurma situacao) {

        BancoDados bd = null;

        try {

            String query = "UPDATE turma SET status = ? WHERE id = ?";

            bd = new BancoDados();
            if (!bd.abrirConexao()) {
                throw new RuntimeException("Erro ao abrir conexão com banco de dados");
            }

            PreparedStatement stmt = bd.getConexao().prepareStatement(query);
            stmt.setInt(1, situacao.getValorSituacaoTurma());
            stmt.setInt(2, idTurma);

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        } finally {
            bd.fecharConexao();
        }
    }

    public int salvarNovo(IEntidade e) {

        String query = "INSERT INTO turma (id_curso, id_professor, data_inicio, data_fim, "
                + "horario_inicio_aula, horario_fim_aula, vagas, sala, "
                + "tipo_media, media_aprovacao, status, descricao) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        BancoDados bd = null;

        try {

            Turma t = (Turma) e;
            bd = new BancoDados();

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com o banco de dados");
            }

            PreparedStatement stmt = bd.getConexao().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, t.getCurso().getId());
            stmt.setInt(2, t.getProfessor().getId());
            stmt.setDate(3, new java.sql.Date(t.getDataInicio().getTime()));
            stmt.setDate(4, new java.sql.Date(t.getDataFim().getTime()));
            stmt.setTime(5, new Time(t.getHorarioInicial().getTimeInMillis()));
            stmt.setTime(6, new Time(t.getHorarioFinal().getTimeInMillis()));
            stmt.setInt(7, t.getVagas());
            stmt.setString(8, t.getSala());
            stmt.setInt(9, t.getTipoMedia().getValorTipoMedia());
            stmt.setDouble(10, t.getMediaAprovacao());
            stmt.setInt(11, t.getSituacao().getValorSituacaoTurma());
            stmt.setString(12, t.getDescricao());

            int resultado = stmt.executeUpdate();

            int idSalvo = 0;
            ResultSet ids = stmt.getGeneratedKeys();
            if (ids.next()) {
                idSalvo = ids.getInt(1);
                t.setId(idSalvo);
            }

            if (idSalvo > 0) {

                TurmaOP.gerarDiasAula(t);

                for (Integer diaSemana : t.getDiasAulasSemana()) {
                    new DiaSemanaDAO().salvarDiasSemanaTurma(idSalvo, diaSemana);
                }
            }

            return idSalvo;
        } catch (Exception ex) {
            return 0;
        } finally {
            bd.fecharConexao();
        }
    }

    public boolean diminuirVagasTurmas(Turma t) {

        BancoDados bd = null;

        try {

            String query = "UPDATE turma SET vagas = vagas - 1 WHERE id = ?";

            bd = new BancoDados();
            if (!bd.abrirConexao()) {
                throw new RuntimeException("Erro ao abrir conexão com banco de dados");
            }

            PreparedStatement stmt = bd.getConexao().prepareStatement(query);
            stmt.setInt(1, t.getId());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        } finally {
            bd.fecharConexao();
        }
    }

    public ArrayList<IEntidade> consultarTurmasProfessor(int idProfessor) {

        ArrayList<IEntidade> turmas = new ArrayList();
        BancoDados bd = new BancoDados();

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }

            StringBuilder query = new StringBuilder();
            query.append("SELECT id, id_curso, id_professor, data_inicio, data_fim, horario_inicio_aula, ");
            query.append("horario_fim_aula, vagas, sala, tipo_media, media_aprovacao, status, descricao ");
            query.append("FROM turma ");
            query.append("WHERE id_professor = ? ");
            query.append("ORDER BY data_inicio ");

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setInt(1, idProfessor);
            ResultSet resultado = stmt.executeQuery();

            Turma t = null;
            short tipoMedia = 0, situacao = 0;
            while (resultado.next()) {
                t = new Turma();
                t.setId(resultado.getInt("id"));
                t.setDataInicio(resultado.getDate("data_inicio"));
                t.setDataFim(resultado.getDate("data_fim"));
                t.setHorarioFinal(resultado.getTime("horario_fim_aula").getTime());
                t.setHorarioInicial(resultado.getTime("horario_inicio_aula").getTime());
                t.setSala(resultado.getString("sala"));
                t.setDescricao(resultado.getString("descricao"));
                t.setMediaAprovacao(resultado.getDouble("media_aprovacao"));
                t.setVagas(resultado.getInt("vagas"));
                t.setCurso((Curso) new CursoDAO().consultarId(resultado.getInt("id_curso")));
                t.setProfessor((Professor) new ProfessorDAO().consultarId(resultado.getInt("id_professor")));

                tipoMedia = resultado.getShort("tipo_media");
                if (tipoMedia == TipoMedia.ARITIMETICA.getValorTipoMedia()) {
                    t.setTipoMedia(TipoMedia.ARITIMETICA);
                } else if (tipoMedia == TipoMedia.HARMONICA.getValorTipoMedia()) {
                    t.setTipoMedia(TipoMedia.HARMONICA);
                } else {
                    t.setTipoMedia(TipoMedia.PONDERADA);
                }

                situacao = resultado.getShort("status");
                if (situacao == SituacaoTurma.EM_ANDAMENTO.getValorSituacaoTurma()) {
                    t.setSituacao(SituacaoTurma.EM_ANDAMENTO);
                } else if (situacao == SituacaoTurma.ENCERRADA.getValorSituacaoTurma()) {
                    t.setSituacao(SituacaoTurma.ENCERRADA);
                } else {
                    t.setSituacao(SituacaoTurma.ABERTA);
                }

                t.setDiasAulasSemana(new DiaSemanaDAO().consultarDiasTurma(t.getId()));

                turmas.add(t);
            }

            return turmas;
        } catch (Exception e) {
            return null;
        } finally {
            bd.fecharConexao();
        }
    }

    public ArrayList<IEntidade> consultarProximasAulas(int id, Date dataInicial, Date dataFinal, TipoUsuario tipoUsuario) {

        ArrayList<IEntidade> proximasAulas = new ArrayList();
        BancoDados bd = new BancoDados();

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }

            StringBuilder query = new StringBuilder();
            query.append("SELECT c.nome as curso, dlt.data_aula as data, t.sala, t.descricao, ");
            query.append("t.horario_inicio_aula as hora_inicial, t.horario_fim_aula as hora_final ");
            query.append("FROM turma t ");
            query.append("INNER JOIN professor p ");
            query.append("ON t.id_professor = p.id_pessoa ");
            query.append("INNER JOIN curso c ");
            query.append("ON t.id_curso = c.id ");
            query.append("INNER JOIN dia_letivo_turma dlt ");
            query.append("ON t.id = dlt.id_turma ");
            if(tipoUsuario == TipoUsuario.PROFESSOR){
                query.append("WHERE t.id_professor = ? ");
            } else {
                query.append("INNER JOIN inscricao_turma it ");
                query.append("ON it.id_turma = t.id ");
                query.append("WHERE it.id_aluno = ? ");
            }
            
            query.append("AND dlt.data_aula BETWEEN ? AND ? ");
            query.append("ORDER BY dlt.data_aula, t.horario_inicio_aula ");
            //query.append("LIMIT 25 ");

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setInt(1, id);
            stmt.setDate(2, new java.sql.Date(dataInicial.getTime()));
            stmt.setDate(3, new java.sql.Date(dataFinal.getTime()));

            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {

                Aula a = new Aula();
                a.setCurso(resultado.getString("curso"));
                a.setSala(resultado.getString("sala"));
                a.setDescricao(resultado.getString("descricao"));
                a.setData(resultado.getDate("data"));
                a.setHorarioInicial(resultado.getTime("hora_inicial").getTime());
                a.setHorarioFinal(resultado.getTime("hora_final").getTime());

                proximasAulas.add(a);
            }

            return proximasAulas;
        } catch (Exception e) {
            return null;
        } finally {
            bd.fecharConexao();
        }
    }

    public boolean IniciarAulas(Date data) {

        BancoDados bd = new BancoDados();

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }

            StringBuilder query = new StringBuilder();
            query.append("UPDATE turma ");
            query.append("SET status = ? ");
            query.append("WHERE status = ? AND data_inicio <= ?");

            PreparedStatement stmt = bd.getConexao().prepareCall(query.toString());
            stmt.setInt(1, SituacaoTurma.EM_ANDAMENTO.getValorSituacaoTurma());
            stmt.setInt(2, SituacaoTurma.ABERTA.getValorSituacaoTurma());
            stmt.setDate(3, new java.sql.Date(data.getTime()));
            
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        } finally {
            bd.fecharConexao();
        }
    }

    public boolean EncerrarAulas(Date data) {
        BancoDados bd = new BancoDados();

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }

            StringBuilder query = new StringBuilder();
            query.append("UPDATE turma ");
            query.append("SET status = ? ");
            query.append("WHERE status = ? AND data_fim <= ?");

            PreparedStatement stmt = bd.getConexao().prepareCall(query.toString());
            stmt.setInt(1, SituacaoTurma.ENCERRADA.getValorSituacaoTurma());
            stmt.setInt(2, SituacaoTurma.EM_ANDAMENTO.getValorSituacaoTurma());
            stmt.setDate(3, new java.sql.Date(data.getTime()));
            
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        } finally {
            bd.fecharConexao();
        }
    }
    
    public ArrayList<IEntidade> consultarTodos(boolean somenteAtivos) {


        StringBuilder query = new StringBuilder();
        query.append("SELECT id, id_curso, id_professor, data_inicio, data_fim, horario_inicio_aula, horario_fim_aula, ");
        query.append("vagas, sala, tipo_media, media_aprovacao, status, descricao ");
        query.append("FROM turma ");
        
        if(somenteAtivos)
            query.append("WHERE status = 1 ");
        
        ArrayList<IEntidade> turmas;
        BancoDados bd = null;

        try {

            turmas = new ArrayList();
            bd = new BancoDados();

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com o banco de dados");
            }

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            ResultSet resultado = stmt.executeQuery();

            Turma t;
            short tipoMedia = 0, situacao = 0;
            while (resultado.next()) {
                t = new Turma();
                t.setId(resultado.getInt("id"));
                t.setDataInicio(resultado.getDate("data_inicio"));
                t.setDataFim(resultado.getDate("data_fim"));
                t.setHorarioInicioAula(resultado.getDate("horario_inicio_aula"));
                t.setHorarioFimAula(resultado.getDate("horario_fim_aula"));
                t.setSala(resultado.getString("sala"));
                t.setDescricao(resultado.getString("descricao"));
                t.setMediaAprovacao(resultado.getDouble("media_aprovacao"));
                t.setVagas(resultado.getInt("vagas"));
                t.setCurso((Curso) new CursoDAO().consultarId(resultado.getInt("id_curso")));
                t.setProfessor((Professor) new ProfessorDAO().consultarId(resultado.getInt("id_professor")));

                tipoMedia = resultado.getShort("tipo_media");
                if (tipoMedia == TipoMedia.ARITIMETICA.getValorTipoMedia()) {
                    t.setTipoMedia(TipoMedia.ARITIMETICA);
                } else if (tipoMedia == TipoMedia.HARMONICA.getValorTipoMedia()) {
                    t.setTipoMedia(TipoMedia.HARMONICA);
                } else {
                    t.setTipoMedia(TipoMedia.PONDERADA);
                }

                situacao = resultado.getShort("status");
                if (situacao == SituacaoTurma.EM_ANDAMENTO.getValorSituacaoTurma()) {
                    t.setSituacao(SituacaoTurma.EM_ANDAMENTO);
                } else if (situacao == SituacaoTurma.ENCERRADA.getValorSituacaoTurma()) {
                    t.setSituacao(SituacaoTurma.ENCERRADA);
                } else {
                    t.setSituacao(SituacaoTurma.ABERTA);
                }

                turmas.add(t);
            }

            return turmas;
        } catch (Exception e) {
            return null;
        } finally {
            bd.fecharConexao();
        }
    }
}
