package jschool.validacao;

import java.util.ArrayList;
import java.util.Date;
import jschool.modelo.entidade.Aluno;
import jschool.modelo.entidade.IEntidade;

/**
 *
 * @author andre
 */
public class AlunoValidador implements IValidador {

    @Override
    public ArrayList<String> validarCadastro(IEntidade entidade) {
        
        ArrayList<String> inconsistencias = new ArrayList();
        
        if(entidade != null && entidade instanceof Aluno){
            
            Aluno a = (Aluno) entidade;
            
            inconsistencias = new PessoaValidador().validarCadastro(a);
            
            if(a.getDataMatricula() == null){
                inconsistencias.add("A data de matrícula deve ser informada");
            } else if(a.getDataMatricula().getTime() > new Date().getTime()){
                inconsistencias.add("A data de matrícula deve ser menor que a data atual");
            }
            
        } else {
            inconsistencias.add("Entidade inválida");
        }
        
        return inconsistencias;
    }

    @Override
    public ArrayList<String> validarAlteracoes(IEntidade entidade) {
        ArrayList<String> inconsistencias = new ArrayList();
        
        if(entidade != null && entidade instanceof Aluno){
            
            Aluno a = (Aluno) entidade;
            
            inconsistencias = new PessoaValidador().validarAlteracoes(a);
            
            if(a.getDataMatricula() == null){
                inconsistencias.add("A data de matrícula deve ser informada");
            } else if(a.getDataMatricula().getTime() > new Date().getTime()){
                inconsistencias.add("A data de matrícula deve ser menor que a data atual");
            }
            
        } else {
            inconsistencias.add("Entidade inválida");
        }
        
        return inconsistencias;
    }
}
