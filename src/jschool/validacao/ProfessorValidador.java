package jschool.validacao;

import java.util.ArrayList;
import java.util.Date;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.entidade.Professor;

/**
 *
 * @author andre
 */
public class ProfessorValidador implements IValidador {

    @Override
    public ArrayList<String> validarCadastro(IEntidade entidade) {

        ArrayList<String> inconsistencias = new ArrayList();

        if (entidade != null && entidade instanceof Professor) {
            Professor p = (Professor) entidade;

            inconsistencias = new PessoaValidador().validarCadastro(p);

            if (p.getNumeroCtps() == null || p.getNumeroCtps().trim().isEmpty()) {
                inconsistencias.add("Informe número da CTPS");
            } else if (p.getNumeroCtps().length() > 11) {
                inconsistencias.add("O número da CPTS deve possuir no máximo 11 caracteres");
            }

            if (p.getDataAdmissao() == null) {
                inconsistencias.add("Informe a data de admissão");
            } else if(p.getDataAdmissao().getTime() > new Date().getTime()) {
                inconsistencias.add("A data de admissão deve ser menor que data atual");
            }

        } else {
            inconsistencias.add("Entidade inválida");
        }

        return inconsistencias;
    }

    @Override
    public ArrayList<String> validarAlteracoes(IEntidade entidade) {
        ArrayList<String> inconsistencias = new ArrayList();

        if (entidade != null && entidade instanceof Professor) {
            Professor p = (Professor) entidade;

            inconsistencias = new PessoaValidador().validarAlteracoes(p);

            if (p.getNumeroCtps() == null || p.getNumeroCtps().trim().isEmpty()) {
                inconsistencias.add("Informe número da CTPS");
            } else if (p.getNumeroCtps().length() > 11) {
                inconsistencias.add("O número da CPTS deve possuir no máximo 11 caracteres");
            }

            if (p.getDataAdmissao() == null) {
                inconsistencias.add("Informe a data de admissão");
            } else if(p.getDataAdmissao().getTime() > new Date().getTime()) {
                inconsistencias.add("A data de admissão deve ser menor que data atual");
            }

        } else {
            inconsistencias.add("Entidade inválida");
        }

        return inconsistencias;
    }

}
