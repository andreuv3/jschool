/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jschool.modelo.entidade.Cidade;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.entidade.Instituicao;
import jschool.infraestrutura.ConexaoFactory;

/**
 *
 * @author andre
 */
public class InstituicaoDAO implements IDAO{

    @Override
    public boolean salvar(IEntidade e) {
        
        Connection bd = null;
        Instituicao instituicao = (Instituicao) e;
        
        try{
            
            String sql = "UPDATE instituicao SET " +
                         "nome = ?, cnpj = ?, inscricao_estadual = ?, " +
                         "telefone = ?, email = ?, site = ?, " +
                         "logradouro = ?, numero = ?, complemento = ?, " +
                         "bairro = ?, id_cidade = ? " + 
                         "WHERE id = ?";
            
            bd = ConexaoFactory.getConnection();
            
            PreparedStatement stmt = bd.prepareStatement(sql);
            stmt.setString(1, instituicao.getNome());
            stmt.setString(2, instituicao.getCnpj());
            stmt.setString(3, instituicao.getInscricaoEstadual());
            stmt.setString(4, instituicao.getTelefone());
            stmt.setString(5, instituicao.getEmail());
            stmt.setString(6, instituicao.getSite());
            stmt.setString(7, instituicao.getLogradouro());
            stmt.setString(8, instituicao.getNumero());
            stmt.setString(9, instituicao.getComplemento());
            stmt.setString(10, instituicao.getBairro());
            stmt.setInt(11, instituicao.getCidade().getId());
            stmt.setInt(12, instituicao.getId());
            
            int resultado = stmt.executeUpdate();
            
            return resultado > 0;
        }catch(SQLException ex){
            return false;   
        } finally {
            ConexaoFactory.closeConnection(bd);
        }
    }

    @Override
    public boolean atualizar(IEntidade e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean excluir(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<IEntidade> consultarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<IEntidade> consultar(String criterio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IEntidade consultarId(int id) {
        
        Connection bd = null;
        
        try{
            Instituicao instituicao = new Instituicao();
            
            String sql = "SELECT id, nome, cnpj, inscricao_estadual, telefone, email, site, " +
                         "logradouro, numero, complemento, bairro, id_cidade " +
                         "FROM instituicao " +
                         "LIMIT 1";
            
            bd = ConexaoFactory.getConnection();
            PreparedStatement stmt = bd.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultado = stmt.executeQuery();
            
            if(resultado.first()){
                instituicao.setId(resultado.getInt("id"));
                instituicao.setNome(resultado.getString("nome"));
                instituicao.setCnpj(resultado.getString("cnpj"));
                instituicao.setInscricaoEstadual(resultado.getString("inscricao_estadual"));
                instituicao.setTelefone(resultado.getString("telefone"));
                instituicao.setEmail(resultado.getString("email"));
                instituicao.setSite(resultado.getString("site"));
                instituicao.setLogradouro(resultado.getString("logradouro"));
                instituicao.setNumero(resultado.getString("numero"));
                instituicao.setComplemento(resultado.getString("complemento"));
                instituicao.setBairro(resultado.getString("bairro"));
                instituicao.setCidade((Cidade) new CidadeDAO().consultarId(resultado.getInt("id_cidade")));
            } else{
                instituicao = null;
            }
            
            return instituicao;
        }catch(SQLException ex){
            return null;
        } finally {
            ConexaoFactory.closeConnection(bd);
        }
    }
    
}
