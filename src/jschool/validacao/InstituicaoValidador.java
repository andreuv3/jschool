/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.validacao;

import java.util.ArrayList;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.entidade.Instituicao;
import jschool.util.Documentos;

/**
 *
 * @author andre
 */
public class InstituicaoValidador implements IValidador {

    private ArrayList<String> inconsistencias;
    private Instituicao insituicao;

    public InstituicaoValidador() {
        this.inconsistencias = new ArrayList<>();
    }

    @Override
    public ArrayList<String> validarCadastro(IEntidade entidade) {

        if (entidade != null && entidade instanceof Instituicao) {

            this.insituicao = (Instituicao) entidade;

            if (this.insituicao.getNome().trim().length() <= 0) {
                this.inconsistencias.add("Informe o nome");
            }

            if (this.insituicao.getNome().length() > 100) {
                this.inconsistencias.add("O nome deve ter no máximo 100 caracteres");
            }

            if (this.insituicao.getCnpj() == null) {
                this.inconsistencias.add("O CNPJ é obrigatório");
            } else if (this.insituicao.getCnpj().isEmpty()) {
                this.inconsistencias.add("O CNPJ é obrigatório");
            } else if (!Documentos.validarCNPJ(this.insituicao.getCnpj())) {
                this.inconsistencias.add("CNPJ inválido");
            }

            if (this.insituicao.getInscricaoEstadual().length() != 12) {
                this.inconsistencias.add("A Inscrição Estadual deve ter 12 caracteres");
            }

            if (this.insituicao.getEmail().length() > 50) {
                this.inconsistencias.add("O e-mail deve ter no máximo 50 caracteres");
            }

            if (this.insituicao.getSite().length() > 100) {
                this.inconsistencias.add("O site deve ter no máximo 100 caracteres");
            }

            if (this.insituicao.getLogradouro().length() > 100) {
                this.inconsistencias.add("O logradouro deve ter no máximo 100 caracteres");
            }

            if (this.insituicao.getNumero().length() > 10) {
                this.inconsistencias.add("O número deve ter no máximo 10 caracteres");
            }

            if (this.insituicao.getComplemento().length() > 20) {
                this.inconsistencias.add("O complemento deve ter no máximo 20 caracteres");
            }

            if (this.insituicao.getBairro().length() > 100) {
                this.inconsistencias.add("O bairro deve ter no máximo 100 caracteres");
            }

            if (this.insituicao.getCidade() == null || this.insituicao.getCidade().getId() <= 0) {
                this.inconsistencias.add("Informe a cidade");
            }

        } else {
            this.inconsistencias.add("Entidade inválida");
        }

        return this.inconsistencias;
    }

    @Override
    public ArrayList<String> validarAlteracoes(IEntidade entidade) {
        if (entidade != null && entidade instanceof Instituicao) {

            this.insituicao = (Instituicao) entidade;

            if (this.insituicao.getId() <= 0) {
                this.inconsistencias.add("Código da instituição inválido");
            }

            if (this.insituicao.getNome().trim().length() <= 0) {
                this.inconsistencias.add("Informe o nome");
            }

            if (this.insituicao.getNome().length() > 100) {
                this.inconsistencias.add("O nome deve ter no máximo 100 caracteres");
            }

            if (this.insituicao.getCnpj() == null || this.insituicao.getCnpj().isEmpty()) {
                this.inconsistencias.add("Informe o CNPJ");
            } else {
                if (!Documentos.validarCNPJ(this.insituicao.getCnpj())) {
                    this.inconsistencias.add("CNPJ inválido");
                }
            }

            if (this.insituicao.getInscricaoEstadual().length() != 12) {
                this.inconsistencias.add("A Inscrição Estadual deve ter 12 caracteres");
            }

            if (this.insituicao.getTelefone().length() > 12) {
                this.inconsistencias.add("O telefone deve ter no máximo 12 caracteres");
            }

            if (this.insituicao.getEmail().length() > 50) {
                this.inconsistencias.add("O e-mail deve ter no máximo 50 caracteres");
            }

            if (this.insituicao.getSite().length() > 100) {
                this.inconsistencias.add("O site deve ter no máximo 100 caracteres");
            }

            if (this.insituicao.getLogradouro().length() > 100) {
                this.inconsistencias.add("O logradouro deve ter no máximo 100 caracteres");
            }

            if (this.insituicao.getNumero().length() > 10) {
                this.inconsistencias.add("O número deve ter no máximo 10 caracteres");
            }

            if (this.insituicao.getComplemento().length() > 20) {
                this.inconsistencias.add("O complemento deve ter no máximo 20 caracteres");
            }

            if (this.insituicao.getBairro().length() > 100) {
                this.inconsistencias.add("O bairro deve ter no máximo 100 caracteres");
            }

            if (this.insituicao.getCidade() == null || this.insituicao.getCidade().getId() <= 0) {
                this.inconsistencias.add("Informe a cidade");
            }

        } else {
            this.inconsistencias.add("Entidade inválida");
        }

        return this.inconsistencias;
    }

}
