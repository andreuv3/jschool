package jschool.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import jschool.enumeracoes.Sexo;
import jschool.enumeracoes.Situacao;
import jschool.infraestrutura.BancoDados;
import jschool.modelo.entidade.Aluno;
import jschool.modelo.entidade.Cidade;
import jschool.modelo.entidade.IEntidade;
import jschool.operacoes.AlunoOP;

/**
 *
 * @author andre
 */
public class AlunoDAO implements IDAO {

    @Override
    public boolean salvar(IEntidade e) {

        Aluno aluno = (Aluno) e;
        BancoDados bd = new BancoDados();

        try {

            //Salva os dados na tabela pessoa e recupera o ID gerado
            int idPessoa = new PessoaDAO().salvarPessoa(e);
            if (idPessoa <= 0) {
                return false;
            }

            //Salva os dados na tabela aluno
            aluno.setId(idPessoa);
            aluno.setNumeroMatricula(new AlunoOP().GerarNumeroMatricula(aluno));
            
            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }
            
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO aluno (id_pessoa, data_matricula, numero_matricula) ");
            query.append("VALUES (?, ?, ?)");

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setInt(1, aluno.getId());
            stmt.setDate(2, new java.sql.Date(aluno.getDataMatricula().getTime()));
            stmt.setString(3, aluno.getNumeroMatricula());

            return stmt.executeUpdate() > 0;
        } catch (Exception ex) {
            return false;
        } finally {
            bd.fecharConexao();
        }
    }

    @Override
    public boolean atualizar(IEntidade e) {
        
        Aluno aluno = (Aluno) e;
        BancoDados bd = new BancoDados();

        try {

            if (!new PessoaDAO().atualizar(aluno)) {
                return false;
            }

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados:");
            }

            StringBuilder query = new StringBuilder();
            query.append("UPDATE aluno ");
            query.append("SET data_matricula = ?, numero_matricula = ? ");
            query.append("WHERE id_pessoa = ?");

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setDate(1, new java.sql.Date(aluno.getDataMatricula().getTime()));
            stmt.setString(2, aluno.getNumeroMatricula());
            stmt.setInt(3, aluno.getId());

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

        ArrayList<IEntidade> alunos = new ArrayList();
        BancoDados bd = new BancoDados();

        try {
            
            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados:");
            }

            StringBuilder query = new StringBuilder();
            query.append("SELECT id_pessoa, numero_matricula, data_matricula ");
            query.append("FROM aluno ");
            
            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            ResultSet resultado = stmt.executeQuery();

            Aluno aluno = null;
            while (resultado.next()) {
                aluno = new Aluno();
                aluno.setId(resultado.getInt("id_pessoa"));
                aluno.setNumeroMatricula(resultado.getString("numero_matricula"));
                aluno.setDataMatricula(resultado.getDate("data_matricula"));
                
                //Carrega dados da pessoa
                new PessoaDAO().carregarPessoa(aluno);
                if (aluno == null) {
                    throw new RuntimeException("Problema ao carregar dados da pessoa");
                }

                alunos.add(aluno);
            }

            return alunos;
        } catch (Exception e) {
            return null;
        } finally {
            bd.fecharConexao();
        }
    }

    @Override
    public ArrayList<IEntidade> consultar(String criterio) {

        ArrayList<IEntidade> alunos = new ArrayList();
        BancoDados bd = new BancoDados();

        try {

            StringBuilder query = new StringBuilder();
            query.append("SELECT id, nome, situacao, sexo, data_nascimento, rg, cpf, telefone, celular, email, logradouro, site, ");
            query.append("numero, complemento, bairro, id_cidade, numero_matricula, data_matricula ");
            query.append("FROM pessoa pes ");
            query.append("INNER JOIN aluno alu ");
            query.append("ON pes.id = alu.id_pessoa ");
            query.append("WHERE pes.nome ILIKE ?");

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados:");
            }

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1, "%" + criterio + "%");
            ResultSet resultado = stmt.executeQuery();

            Aluno aluno = null;
            if (resultado.next()) {
                aluno = new Aluno();
                aluno.setId(resultado.getInt("id"));
                aluno.setNome(resultado.getString("nome"));
                aluno.setSituacao(resultado.getInt("situacao") == Situacao.ATIVO.getValorSituacao() ? Situacao.ATIVO : Situacao.INATIVO);
                aluno.setSexo(resultado.getInt("sexo") == Sexo.MASCULINO.getSexo() ? Sexo.MASCULINO : Sexo.FEMININO);
                aluno.setDataNascimento(resultado.getDate("data_nascimento"));
                aluno.setRg(resultado.getString("rg"));
                aluno.setCpf(resultado.getString("cpf"));
                aluno.setTelefone(resultado.getString("telefone"));
                aluno.setCelular(resultado.getString("celular"));
                aluno.setEmail(resultado.getString("email"));
                aluno.setSite(resultado.getString("site"));
                aluno.setLogradouro(resultado.getString("logradouro"));
                aluno.setNumero(resultado.getString("numero"));
                aluno.setComplemento(resultado.getString("complemento"));
                aluno.setBairro(resultado.getString("bairro"));
                aluno.setCidade((Cidade) new CidadeDAO().consultarId(resultado.getInt("id_cidade")));
                aluno.setNumeroMatricula(resultado.getString("numero_matricula"));
                aluno.setDataMatricula(resultado.getDate("data_matricula"));

                alunos.add(aluno);
            }

            return alunos;
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

            StringBuilder query = new StringBuilder();
            query.append("SELECT id, nome, situacao, sexo, data_nascimento, rg, cpf, telefone, celular, email, logradouro, site, ");
            query.append("numero, complemento, bairro, id_cidade, numero_matricula, data_matricula ");
            query.append("FROM pessoa pes ");
            query.append("INNER JOIN aluno alu ");
            query.append("ON pes.id = alu.id_pessoa ");
            query.append("WHERE id = ?");

            if (!bd.abrirConexao()) {
                throw new SQLException("Problema ao conectar com banco de dados");
            }

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();

            Aluno aluno = null;

            if (resultado.first()) {
                aluno = new Aluno();
                aluno.setId(resultado.getInt("id"));
                aluno.setNome(resultado.getString("nome"));
                aluno.setSituacao(resultado.getInt("situacao") == Situacao.ATIVO.getValorSituacao() ? Situacao.ATIVO : Situacao.INATIVO);
                aluno.setSexo(resultado.getInt("sexo") == Sexo.MASCULINO.getSexo() ? Sexo.MASCULINO : Sexo.FEMININO);
                aluno.setDataNascimento(resultado.getDate("data_nascimento"));
                aluno.setRg(resultado.getString("rg"));
                aluno.setCpf(resultado.getString("cpf"));
                aluno.setTelefone(resultado.getString("telefone"));
                aluno.setCelular(resultado.getString("celular"));
                aluno.setEmail(resultado.getString("email"));
                aluno.setSite(resultado.getString("site"));
                aluno.setLogradouro(resultado.getString("logradouro"));
                aluno.setNumero(resultado.getString("numero"));
                aluno.setComplemento(resultado.getString("complemento"));
                aluno.setBairro(resultado.getString("bairro"));
                aluno.setCidade((Cidade) new CidadeDAO().consultarId(resultado.getInt("id_cidade")));
                aluno.setNumeroMatricula(resultado.getString("numero_matricula"));
                aluno.setDataMatricula(resultado.getDate("data_matricula"));
            }

            return aluno;
        } catch (Exception e) {
            return null;
        } finally {
            bd.fecharConexao();
        }
    }

    public boolean alterarSituacao(Aluno aluno) {

        try {
            boolean situacaoAlterada = new PessoaDAO().alterarSituacao(aluno);

            //Se a situação for inativo, é porque está alterando para ativo,
            //logo precisamos alterar a data de matrícula
            if (situacaoAlterada && aluno.getSituacao() == Situacao.INATIVO) {
                alterarDataMatricula(aluno);
            }

            return situacaoAlterada;
        } catch (Exception ex) {
            return false;
        }
    }

    private void alterarDataMatricula(Aluno aluno) {

        BancoDados bd = new BancoDados();

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }

            StringBuilder query = new StringBuilder();
            query.append("UPDATE aluno ");
            query.append("SET data_matricula = ? ");
            query.append("WHERE id_pessoa = ?");

            Date hoje = new Date();
            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setDate(1, new java.sql.Date(hoje.getTime()));
            stmt.setInt(2, aluno.getId());

            stmt.executeUpdate();
        } catch (Exception e) {
        } finally {
            bd.fecharConexao();
        }
    }
}
