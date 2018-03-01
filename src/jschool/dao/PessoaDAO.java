package jschool.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import jschool.enumeracoes.Sexo;
import jschool.enumeracoes.Situacao;
import jschool.infraestrutura.BancoDados;
import jschool.infraestrutura.ConexaoFactory;
import jschool.modelo.entidade.Cidade;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.entidade.Pessoa;

/**
 *
 * @author andre
 */
public class PessoaDAO implements IDAO {

    @Override
    public boolean salvar(IEntidade e) {
        throw new UnsupportedOperationException("Operação não suportada");
    }

    @Override
    public boolean atualizar(IEntidade e) {

        Pessoa p = (Pessoa) e;
        BancoDados bd = new BancoDados();

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados:");
            }

            StringBuilder query = new StringBuilder();
            query.append("UPDATE pessoa ");
            query.append("SET nome = ?, data_nascimento = ?, rg = ?, ");
            query.append("cpf = ?, telefone = ?, celular = ?, email = ?, logradouro = ?, ");
            query.append("numero = ?, complemento = ?, bairro = ?, sexo = ?, situacao = ?, id_cidade = ?, site = ? ");
            query.append("WHERE id = ?");
            
            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setString(1, p.getNome());
            stmt.setDate(2, p.getDataNascimento() != null ? new java.sql.Date(p.getDataNascimento().getTime()) : null);
            /*if (p.getDataNascimento() != null) {
                stmt.setDate(2, new java.sql.Date(p.getDataNascimento().getTime()));
            } else {
                stmt.setDate(2, null);
            }*/
            stmt.setString(3, p.getRg());
            stmt.setString(4, p.getCpf());
            stmt.setString(5, p.getTelefone());
            stmt.setString(6, p.getCelular());
            stmt.setString(7, p.getEmail());
            stmt.setString(8, p.getLogradouro());
            stmt.setString(9, p.getNumero());
            stmt.setString(10, p.getComplemento());
            stmt.setString(11, p.getBairro());
            stmt.setInt(12, p.getSexo().getSexo());
            stmt.setInt(13, p.getSituacao().getValorSituacao());
            stmt.setInt(14, p.getCidade() != null ? p.getCidade().getId() : null);
            /*if (p.getCidade() != null) {
                stmt.setInt(14, p.getCidade().getId());
            } else {
                stmt.setInt(14, 0);
            }*/
            stmt.setString(15, p.getSite());
            stmt.setInt(16, p.getId());

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
        throw new UnsupportedOperationException("Operação não suportada");
    }

    public int salvarPessoa(IEntidade e) {

        Pessoa p = (Pessoa) e;
        BancoDados bd = new BancoDados();

        try {
            
            if(!bd.abrirConexao()){
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }
            
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO pessoa (nome, data_nascimento, rg, cpf, telefone, celular, ");
            query.append("email, logradouro, numero, complemento, bairro, sexo, situacao, id_cidade, site) ");
            query.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?)");
            
            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, p.getNome());
            stmt.setDate(2, p.getDataNascimento() != null ? new java.sql.Date(p.getDataNascimento().getTime()) : null);
            /*if (p.getDataNascimento() != null) {
                stmt.setDate(2, new java.sql.Date(p.getDataNascimento().getTime()));
            } else {
                stmt.setDate(2, null);
            }*/
            stmt.setString(3, p.getRg());
            stmt.setString(4, p.getCpf());
            stmt.setString(5, p.getTelefone());
            stmt.setString(6, p.getCelular());
            stmt.setString(7, p.getEmail());
            stmt.setString(8, p.getLogradouro());
            stmt.setString(9, p.getNumero());
            stmt.setString(10, p.getComplemento());
            stmt.setString(11, p.getBairro());
            stmt.setInt(12, p.getSexo().getSexo());
            stmt.setInt(13, p.getSituacao().getValorSituacao());
            stmt.setInt(14, p.getCidade() != null ? p.getCidade().getId() : null);
            /*if (p.getCidade() != null) {
                stmt.setInt(14, p.getCidade().getId());
            }*/
            stmt.setString(15, p.getSite());
            System.out.println(query.toString());
            stmt.executeUpdate();
            
            //Recupera ID da pessoa que foi gerado ao salvar no banco de dados
            ResultSet resultado = stmt.getGeneratedKeys();
            if (resultado.next()) {
                return resultado.getInt(1);
            }

            return 0;
        } catch (Exception ex) {
            System.out.println("erro dao pessoa: " + ex.getMessage());
            return 0;
        } finally {
            bd.fecharConexao();
        }
    }
    
    protected void carregarPessoa(Pessoa p){
        
        BancoDados bd = new BancoDados();
        
        try{
            
            if(!bd.abrirConexao()){
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }
            
            StringBuilder query = new StringBuilder();
            query.append("SELECT id, nome, situacao, sexo, data_nascimento, rg, cpf, telefone, celular, email, logradouro, ");
            query.append("numero, complemento, bairro, id_cidade ");
            query.append("FROM pessoa ");
            query.append("WHERE id = ?");
            
            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, p.getId());
            ResultSet resultado = stmt.executeQuery();
            
            if(resultado.first()){
                p.setNome(resultado.getString("nome"));
                p.setSituacao(resultado.getInt("situacao") == Situacao.ATIVO.getValorSituacao() ? Situacao.ATIVO : Situacao.INATIVO);
                p.setSexo(resultado.getInt("sexo") == Sexo.MASCULINO.getSexo() ? Sexo.MASCULINO : Sexo.FEMININO);
                p.setDataNascimento(resultado.getDate("data_nascimento"));
                p.setRg(resultado.getString("rg"));
                p.setCpf(resultado.getString("cpf"));
                p.setTelefone(resultado.getString("telefone"));
                p.setCelular(resultado.getString("celular"));
                p.setEmail(resultado.getString("email"));
                p.setLogradouro(resultado.getString("logradouro"));
                p.setNumero(resultado.getString("numero"));
                p.setComplemento(resultado.getString("complemento"));
                p.setBairro(resultado.getString("bairro"));
                p.setCidade((Cidade) new CidadeDAO().consultarId(resultado.getInt("id_cidade")));
            }
            
        } catch(Exception ex){
            p = null;
        } finally{
            bd.fecharConexao();
        }
    }
    
    protected boolean alterarSituacao(Pessoa p){
        
        BancoDados bd = new BancoDados();

        try {
            
            if(!bd.abrirConexao()){
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }
            
            StringBuilder query = new StringBuilder();
            query.append("UPDATE pessoa ");
            query.append("SET situacao = ? ");
            query.append("WHERE id = ?");
            
            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setInt(1, p.getSituacao() == Situacao.ATIVO ? Situacao.INATIVO.getValorSituacao() : Situacao.ATIVO.getValorSituacao());
            stmt.setInt(2, p.getId());

            return stmt.executeUpdate() > 0;
        } catch (Exception ex) {
            return false;
        } finally {
            bd.fecharConexao();
        }
    }
}
