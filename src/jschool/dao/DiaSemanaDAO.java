/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import jschool.infraestrutura.BancoDados;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.entidade.Turma;

/**
 *
 * @author andre
 */
public class DiaSemanaDAO implements IDAO{

    @Override
    public boolean salvar(IEntidade e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean excluirDiasSemanaTurma(Turma t){
        
        String query = "DELETE FROM dia_semana_turma WHERE id_turma = ?";
        BancoDados bd;
        
        try {
            
            bd = new BancoDados();
            if(!bd.abrirConexao()){
                throw new RuntimeException("Não foi possível estabelecer conexão com o banco de dados");
            }
            
            PreparedStatement stmt = bd.getConexao().prepareStatement(query);
            stmt.setInt(1, t.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
        
    }
    
    public boolean salvarDiasSemanaTurma(int idTurma, int diaSemana){
        
        BancoDados bd = new BancoDados();
        
        try {
            
            if(!bd.abrirConexao()){
                throw new RuntimeException("Não foi possível estabelecer conexão com o banco de dados");
            }
            
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO dia_semana_turma (dia_semana, id_turma) ");
            query.append("VALUES (?, ?)");
            
            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setInt(1, diaSemana);
            stmt.setInt(2, idTurma);
            
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    public ArrayList<Integer> consultarDiasTurma(int idTurma){
        
        BancoDados bd = new BancoDados();
        
        try {
            
            if(!bd.abrirConexao()){
                throw new RuntimeException("Não foi possível conectar ao banco de dados");
            }
            
            StringBuilder query = new StringBuilder();
            query.append("SELECT dia_semana ");
            query.append("FROM dia_semana_turma ");
            query.append("WHERE id_turma = ?");
            
            ArrayList<Integer> diasSemana = new ArrayList();
            
            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            stmt.setInt(1, idTurma);
            
            ResultSet resultado = stmt.executeQuery();
            
            while(resultado.next()){
                diasSemana.add(resultado.getInt("dia_semana"));
            }
            
            return diasSemana;
        } catch (Exception e) {
            return null;
        } finally{
            bd.fecharConexao();
        }
    }
    
}
