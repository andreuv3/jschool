/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.validacao;

import java.util.ArrayList;
import java.util.Calendar;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.entidade.Turma;
import jschool.operacoes.TurmaOP;

/**
 *
 * @author andre
 */
public class TurmaValidador implements IValidador{

    @Override
    public ArrayList<String> validarCadastro(IEntidade entidade) {
        
        ArrayList<String> inconsistencias = new ArrayList();
        Turma t = (Turma) entidade;

        if(t.getCurso() == null || t.getCurso().getId() <= 0){
            inconsistencias.add("Informe o curso");
        }
        
        if(t.getProfessor() == null || t.getProfessor().getId() <= 0){
            inconsistencias.add("Informe o professor");
        }
        
        if(t.getDataInicio() == null){
            inconsistencias.add("Informe a data inicial");
        }
        
        if(t.getDataFim() == null){
            inconsistencias.add("Informe a data final");
        }
        
        if(t.getDataInicio() != null && t.getDataFim() != null && t.getDataFim().before(t.getDataInicio())){
            inconsistencias.add("A data inicial deve ser menor que a data final");
        }
        
        if(t.getHorarioInicial() == null){
            inconsistencias.add("Informe o horário inicial");
        }
        
        if(t.getHorarioInicial() == null){
            inconsistencias.add("Informe o horário final");
        }
        
        if(t.getHorarioInicial().get(Calendar.HOUR_OF_DAY) <= 0){
            inconsistencias.add("Informe a hora inicial");
        }
        
        if(t.getHorarioFinal().get(Calendar.HOUR_OF_DAY) <= 0){
            inconsistencias.add("Informe a hora final");
        }
        
        if(t.getHorarioFinal().get(Calendar.HOUR_OF_DAY) < t.getHorarioInicial().get(Calendar.HOUR_OF_DAY)){
            inconsistencias.add("O horário inicial deve ser menor que o horário final");
        }
        
        if(t.getVagas() <= 0){
            inconsistencias.add("Informe o número de vagas");
        }
        
        if(t.getDiasAulasSemana() == null || t.getDiasAulasSemana().size() <= 0){
            inconsistencias.add("Informe os dias de aula");
        }
        
        if(t.getTipoMedia() == null){
            inconsistencias.add("Informe o tipo de média");
        }
        
        if(t.getMediaAprovacao() <= 0){
            inconsistencias.add("Informe a média de aprovação");
        }
        
        if(t.getDescricao() == null || t.getDescricao().trim().isEmpty()){
            inconsistencias.add(("Informe a descrição da turma"));
        }
        
        if(TurmaOP.existemColisoesDiasLetivos(t.getProfessor(), t)){
            inconsistencias.add("O professor está dando aulas em outras turmas que colidem com a data e/ou horário");
        }
        
        return inconsistencias;
    }

    @Override
    public ArrayList<String> validarAlteracoes(IEntidade entidade) {
        
        ArrayList<String> inconsistencias = new ArrayList();
        Turma t = (Turma) entidade;

        if(t.getCurso() == null || t.getCurso().getId() <= 0){
            inconsistencias.add("Informe o curso");
        }
        
        if(t.getProfessor() == null || t.getProfessor().getId() <= 0){
            inconsistencias.add("Informe o professor");
        }
        
        if(t.getDataInicio() == null){
            inconsistencias.add("Informe a data inicial");
        }
        
        if(t.getDataFim() == null){
            inconsistencias.add("Informe a data final");
        }
        
        if(t.getDataInicio() != null && t.getDataFim() != null && t.getDataFim().before(t.getDataInicio())){
            inconsistencias.add("A data inicial deve ser menor que a data final");
        }
        
        if(t.getHorarioInicial() == null){
            inconsistencias.add("Informe o horário inicial");
        }
        
        if(t.getHorarioInicial() == null){
            inconsistencias.add("Informe o horário final");
        }
        
        if(t.getHorarioInicial().get(Calendar.HOUR_OF_DAY) <= 0){
            inconsistencias.add("Informe a hora inicial");
        }
        
        if(t.getHorarioFinal().get(Calendar.HOUR_OF_DAY) <= 0){
            inconsistencias.add("Informe a hora final");
        }
        
        if(t.getHorarioFinal().get(Calendar.HOUR_OF_DAY) < t.getHorarioInicial().get(Calendar.HOUR_OF_DAY)){
            inconsistencias.add("O horário inicial deve ser menor que o horário final");
        }
        
        if(t.getVagas() <= 0){
            inconsistencias.add("Informe o número de vagas");
        }
        
        if(t.getDiasAulasSemana() == null || t.getDiasAulasSemana().size() <= 0){
            inconsistencias.add("Informe os dias de aula");
        }
        
        if(t.getTipoMedia() == null){
            inconsistencias.add("Informe o tipo de média");
        }
        
        if(t.getMediaAprovacao() <= 0){
            inconsistencias.add("Informe a média de aprovação");
        }
        
        if(t.getDescricao() == null || t.getDescricao().trim().isEmpty()){
            inconsistencias.add(("Informe a descrição da turma"));
        }
        
        return inconsistencias;
    }
    
}
