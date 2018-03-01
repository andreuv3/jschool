package jschool.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import jschool.infraestrutura.BancoDados;
import jschool.modelo.entidade.Estado;
import jschool.modelo.entidade.IEntidade;

/**
 *
 * @author andre
 */
public class EstadoDAO implements IDAO {

    @Override
    public boolean salvar(IEntidade e) {
        throw new UnsupportedOperationException("Operação não suportada");
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

        ArrayList<IEntidade> estados = new ArrayList<>();
        BancoDados bd = new BancoDados();

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }

            StringBuilder query = new StringBuilder();
            query.append("SELECT id, nome, uf ");
            query.append("FROM estado ");
            query.append("ORDER BY uf ");

            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString());
            ResultSet resultado = stmt.executeQuery();

            Estado estadoBranco = new Estado();
            estadoBranco.setId(0);
            estadoBranco.setNome("(selecione)");
            estadoBranco.setUf("(selecione)");

            estados.add(estadoBranco);

            while (resultado.next()) {
                Estado estado = new Estado();
                estado.setId(resultado.getInt("id"));
                estado.setNome(resultado.getString("nome"));
                estado.setUf(resultado.getString("uf"));

                estados.add(estado);
            }

            return estados;
        } catch (Exception ex) {
            return null;
        } finally {
            bd.fecharConexao();
        }
    }

    @Override
    public ArrayList<IEntidade> consultar(String criterio) {
        throw new UnsupportedOperationException("Operação não suportada");
    }

    @Override
    public IEntidade consultarId(int id) {

        BancoDados bd = new BancoDados();

        try {

            if (!bd.abrirConexao()) {
                throw new RuntimeException("Problema ao abrir conexão com banco de dados");
            }
            
            StringBuilder query = new StringBuilder();
            query.append("SELECT id, nome, uf ");
            query.append("FROM estado ");
            query.append("WHERE id = ?");
            
            PreparedStatement stmt = bd.getConexao().prepareStatement(query.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, id);
            
            ResultSet resultado = stmt.executeQuery();
            
            Estado estado = null;
            if(resultado.first()){
                estado = new Estado();
                estado.setId(resultado.getInt("id"));
                estado.setNome(resultado.getString("nome"));
                estado.setUf(resultado.getString("uf"));
            }
            
            return estado;
        } catch (Exception ex) {
            return null;
        } finally {
            bd.fecharConexao();
        }

    }
}
