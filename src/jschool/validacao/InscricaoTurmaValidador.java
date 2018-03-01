/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.validacao;

import java.util.ArrayList;
import jschool.dao.InscricaoTurmaDAO;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.entidade.InscricaoTurma;
import jschool.operacoes.TurmaOP;

/**
 *
 * @author andre
 */
public class InscricaoTurmaValidador implements IValidador{

    @Override
    public ArrayList<String> validarCadastro(IEntidade entidade) {
        
        ArrayList<String> inconsistencias = new ArrayList();
        InscricaoTurma i = (InscricaoTurma) entidade;
                
        if(i.getAluno() == null || i.getAluno().getId() <= 0){
            inconsistencias.add("Informe o aluno");
        }
        
        if(i.getTurma() == null || i.getTurma().getId() <= 0){
            inconsistencias.add("Informe a turma");
        }
        
        if(i.getTurma() != null && i.getTurma().getVagas() <= 0){
            inconsistencias.add("A turma não possui mais vagas");
        }
        
        if(new InscricaoTurmaDAO().alunoJaInscrito(i)){
            inconsistencias.add("O aluno já está inscrito nesta turma");
        }
        
        if(TurmaOP.existemColisoesDiasLetivos(i.getAluno(), i.getTurma())){
            inconsistencias.add("O aluno está matriculado em outras turmas que colidem com a data e/ou horário");
        }
        
        return inconsistencias;
    }

    @Override
    public ArrayList<String> validarAlteracoes(IEntidade entidade) {
        ArrayList<String> inconsistencias = new ArrayList();
        InscricaoTurma i = (InscricaoTurma) entidade;
           
        if(i.getId() <= 0){
            inconsistencias.add("Inscrição de turma inválida");
        }
        
        if(i.getAluno() == null || i.getAluno().getId() <= 0){
            inconsistencias.add("Informe o aluno");
        }
        
        if(i.getTurma() == null || i.getTurma().getId() <= 0){
            inconsistencias.add("Informe a turma");
        }
        
        if(i.getTurma() != null && i.getTurma().getVagas() <= 0){
            inconsistencias.add("A turma não possui mais vagas");
        }
        
        return inconsistencias;
    }   
}
