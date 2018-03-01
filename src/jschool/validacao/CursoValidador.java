/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.validacao;

import java.util.ArrayList;
import jschool.modelo.entidade.Curso;
import jschool.modelo.entidade.IEntidade;

/**
 *
 * @author andre
 */
public class CursoValidador implements IValidador{

    @Override
    public ArrayList<String> validarCadastro(IEntidade entidade) {
        ArrayList<String> inconsistencias = new ArrayList<>();
        Curso curso = (Curso)entidade;
        
        if(curso.getNome().trim().isEmpty()){
            inconsistencias.add("Informe o nome");
        }
        
        if(curso.getNome().length() > 100){
            inconsistencias.add("O nome deve ter no máximo 100 caracteres. Ele possui: " + curso.getNome().length());
        }
        
        if(curso.getEmenta().trim().isEmpty()){
            inconsistencias.add("Informe a ementa");
        }
        
        if(curso.getEmenta().length() > 200){
            inconsistencias.add("A ementa deve ter no máximo 100 caracteres. Ela possui: " + curso.getEmenta().length());
        }
        
        if(curso.getHoraDuracao() <= 0){
            inconsistencias.add("Informe as horas de duração");
        }
        
        if(curso.getVagasRecomendadas() < 0){
            inconsistencias.add("O número de vagas recomendadas não pode ser menor que 0.");
        }
        
        return inconsistencias;
    }

    @Override
    public ArrayList<String> validarAlteracoes(IEntidade entidade) {
        
        ArrayList<String> inconsistencias = new ArrayList<>();
        Curso curso = (Curso)entidade;
        
        if(curso.getId() <= 0){
            inconsistencias.add("Curso inválido");
        }
        
        if(curso.getNome().trim().isEmpty()){
            inconsistencias.add("Informe o nome");
        }
        
        if(curso.getNome().length() > 100){
            inconsistencias.add("O nome deve ter no máximo 100 caracteres. Ele possui: " + curso.getNome().length());
        }
        
        if(curso.getEmenta().trim().isEmpty()){
            inconsistencias.add("Informe a ementa");
        }
        
        if(curso.getEmenta().length() > 200){
            inconsistencias.add("A ementa deve ter no máximo 100 caracteres. Ela possui: " + curso.getEmenta().length());
        }
        
        if(curso.getHoraDuracao() <= 0){
            inconsistencias.add("Informe as horas de duração");
        }
        
        if(curso.getVagasRecomendadas() < 0){
            inconsistencias.add("O número de vagas recomendadas não pode ser menor que 0.");
        }
        
        return inconsistencias;
        
    }

    
    
}
