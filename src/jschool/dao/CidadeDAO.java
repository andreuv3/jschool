/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import jschool.infraestrutura.BancoDados;
import jschool.modelo.entidade.Cidade;
import jschool.modelo.entidade.Estado;
import jschool.modelo.entidade.IEntidade;
import jschool.infraestrutura.ConexaoFactory;

/**
 *
 * @author andre
 */
public class CidadeDAO implements IDAO {

    @Override
    public boolean salvar(IEntidade e) {
        throw new UnsupportedOperationException("Operação não suportada"); //To change body of generated methods, choose Tools | Templates.
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

        ArrayList<IEntidade> cidades = new ArrayList<>();
        BancoDados bd = new BancoDados();

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }

            StringBuilder query = new StringBuilder();
            query.append("SELECT c.id as id_cidade, c.nome as cidade, ");
            query.append("e.id as id_estado, e.nome as estado, e.uf as uf ");
            query.append("FROM cidade c ");
            query.append("INNER JOIN estado e ");
            query.append("ON c.id_estado = e.id ");
            query.append("LIMIT 200");

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                
                Estado estado = new Estado();
                estado.setId(resultado.getInt("id_estado"));
                estado.setNome(resultado.getString("estado"));
                estado.setUf(resultado.getString("uf"));
                
                Cidade cidade = new Cidade();
                cidade.setId(resultado.getInt("id_cidade"));
                cidade.setNome(resultado.getString("cidade"));
                cidade.setEstado(estado);
                //cidade.setEstado((Estado) new EstadoDAO().consultarId(resultado.getInt("id_estado")));
                
                cidades.add(cidade);
            }

            return cidades;
        } catch (Exception e) {
            return null;
        } finally {
            bd.fecharConexao();
        }
    }

    @Override
    public ArrayList<IEntidade> consultar(String criterio) {

        ArrayList<IEntidade> cidades = new ArrayList<>();
        BancoDados bd = new BancoDados();

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }

            StringBuilder query = new StringBuilder();
            query.append("SELECT c.id as id_cidade, c.nome as cidade, ");
            query.append("e.id as id_estado, e.nome as estado, e.uf as uf ");
            query.append("FROM cidade c ");
            query.append("INNER JOIN estado e ");
            query.append("ON c.id_estado = e.id ");
            query.append("WHERE c.nome ILIKE ? ");
            query.append("LIMIT 200");

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setString(1, "%" + criterio + "%");
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                
                Estado estado = new Estado();
                estado.setId(resultado.getInt("id_estado"));
                estado.setNome(resultado.getString("estado"));
                estado.setUf(resultado.getString("uf"));
                
                Cidade cidade = new Cidade();
                cidade.setId(resultado.getInt("id_cidade"));
                cidade.setNome(resultado.getString("cidade"));
                cidade.setEstado(estado);
                //cidade.setEstado((Estado) new EstadoDAO().consultarId(resultado.getInt("id_estado")));
                
                cidades.add(cidade);
            }

            return cidades;
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

            StringBuilder query = new StringBuilder();
            query.append("SELECT id, nome, id_estado ");
            query.append("FROM cidade ");
            query.append("WHERE id = ?");

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, id);
            
            ResultSet resultado = stmt.executeQuery();

            Cidade cidade = null;
            if (resultado.first()) {
                cidade  = new Cidade();
                cidade.setId(resultado.getInt("id"));
                cidade.setNome(resultado.getString("nome"));
                cidade.setEstado((Estado) new EstadoDAO().consultarId(resultado.getInt("id_estado")));
            }

            return cidade;
        } catch (Exception e) {
            return null;
        } finally {
            bd.fecharConexao();
        }
    }

    public ArrayList<IEntidade> consultarCidadesPorEstado(Estado e) {

        Connection bd = null;

        try {
            ArrayList<IEntidade> cidades = new ArrayList<>();

            String sql = "SELECT id, nome FROM cidade WHERE id_estado = ? ORDER BY nome";

            bd = ConexaoFactory.getConnection();
            PreparedStatement stmt = bd.prepareStatement(sql);
            stmt.setInt(1, e.getId());
            ResultSet resultado = stmt.executeQuery();

            Cidade cidadeBranco = new Cidade();
            cidadeBranco.setId(0);
            cidadeBranco.setNome("(selecione)");

            while (resultado.next()) {
                Cidade cidade = new Cidade();
                cidade.setId(resultado.getInt("id"));
                cidade.setNome(resultado.getString("nome"));
                cidade.setEstado(e);

                cidades.add(cidade);
            }

            return cidades;
        } catch (Exception ex) {
            return null;
        } finally {
            ConexaoFactory.closeConnection(bd);
        }
    }

    public ArrayList<IEntidade> consultarCidades(String nome, Estado estado) {

        Connection bd = null;

        try {

            ArrayList<IEntidade> cidades = new ArrayList<>();
            Cidade cidade;

            String sql = "SELECT id, nome FROM cidade "
                       + "WHERE nome ILIKE ? AND id_estado = ? "
                       + "ORDER BY nome";

            bd = ConexaoFactory.getConnection();
            PreparedStatement stmt = bd.prepareStatement(sql);
            stmt.setString(1, "%" + nome + "%");
            stmt.setInt(2, estado.getId());

            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                cidade = new Cidade();
                cidade.setId(resultado.getInt("id"));
                cidade.setNome(resultado.getString("nome"));
                cidade.setEstado(estado);

                cidades.add(cidade);
            }

            return cidades;
        } catch (Exception ex) {
            return null;
        } finally {
            ConexaoFactory.closeConnection(bd);
        }
    }
}