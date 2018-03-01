/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.validacao;

import java.util.ArrayList;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.entidade.Pessoa;
import jschool.util.Documentos;

/**
 *
 * @author andre
 */
public class PessoaValidador implements IValidador{

    @Override
    public ArrayList<String> validarCadastro(IEntidade entidade) {
        ArrayList<String> inconsistencias = new ArrayList();
        
        if (entidade != null && entidade instanceof Pessoa) {
            Pessoa p = (Pessoa) entidade;

            if (p.getNome() == null || p.getNome().trim().isEmpty()) {
                inconsistencias.add("Informe o nome");
            } else if (p.getNome().length() > 100) {
                inconsistencias.add("O nome deve possuir no máximo 100 caracteres");
            }

            if (p.getTelefone() == null || p.getTelefone().trim().isEmpty()) {
                inconsistencias.add("Informe o telefone");
            } else if (p.getTelefone().length() > 12) {
                inconsistencias.add("O telefone deve possuir no máximo 12 caracteres");
            }

            if (p.getCidade() == null || p.getCidade().getId() <= 0) {
                inconsistencias.add("Informe a cidade");
            }
            
            if(p.getRg() != null && p.getRg().length() > 12){
                inconsistencias.add("O RG possuir ter no máximo 12 caracteres");
            }
            
            if(p.getCpf() != null && !p.getCpf().trim().isEmpty() && !Documentos.validarCPF(p.getCpf())){
                inconsistencias.add("O CPF é inválido");
            }
            
            if(p.getCelular()!= null && p.getCelular().length() > 12){
                inconsistencias.add("O celular deve possuir no máximo 12 caracteres");
            }
            
            if(p.getEmail() != null && p.getEmail().length() > 50){
                inconsistencias.add("O e-mail deve possuir no máximo 50 caracteres");
            }
            
            if(p.getSite() != null && p.getSite().length() > 50){
                inconsistencias.add("O site deve possuir no máximo 50 caracteres");
            }
            
            if(p.getLogradouro() != null && p.getLogradouro().length() > 100){
                inconsistencias.add("O logradouro deve possuir no máximo 100 caracteres");
            }
            
            if(p.getNumero() != null && p.getNumero().length() > 10){
                inconsistencias.add("O número deve possuir no máximo 10 caracteres");
            }
            
            if(p.getComplemento() != null && p.getComplemento().length() > 20){
                inconsistencias.add("O complemento deve possuir no máximo 20 caracteres");
            }
            
            if(p.getBairro() != null && p.getBairro().length() > 100){
                inconsistencias.add("O bairro deve possuir no máximo 100 caracteres");
            }
            
            if(p.getSexo() == null){
                inconsistencias.add("Informe o sexo");
            }
            
        } else {
            inconsistencias.add("Entidade inválida");
        }

        return inconsistencias;
    }
    
    @Override
    public ArrayList<String> validarAlteracoes(IEntidade entidade) {
        ArrayList<String> inconsistencias = new ArrayList();
        
        if (entidade != null && entidade instanceof Pessoa) {
            Pessoa p = (Pessoa) entidade;

            if(p.getId() <= 0){
                inconsistencias.add("Código inválido");
            }
            
            if (p.getNome() == null || p.getNome().trim().isEmpty()) {
                inconsistencias.add("Informe o nome");
            } else if (p.getNome().length() > 100) {
                inconsistencias.add("O nome deve possuir no máximo 100 caracteres");
            }

            if (p.getTelefone() == null || p.getTelefone().trim().isEmpty()) {
                inconsistencias.add("Informe o telefone");
            } else if (p.getTelefone().length() > 12) {
                inconsistencias.add("O telefone deve possuir no máximo 12 caracteres");
            }

            if (p.getCidade() == null || p.getCidade().getId() <= 0) {
                inconsistencias.add("Informe a cidade");
            }
            
            if(p.getRg() != null && p.getRg().length() > 12){
                inconsistencias.add("O RG possuir ter no máximo 12 caracteres");
            }
            
            if(p.getCpf() != null && !p.getCpf().trim().isEmpty() && !Documentos.validarCPF(p.getCpf())){
                inconsistencias.add("O CPF é inválido");
            }
            
            if(p.getCelular()!= null && p.getCelular().length() > 12){
                inconsistencias.add("O celular deve possuir no máximo 12 caracteres");
            }
            
            if(p.getEmail() != null && p.getEmail().length() > 50){
                inconsistencias.add("O e-mail deve possuir no máximo 50 caracteres");
            }
            
            if(p.getSite() != null && p.getSite().length() > 50){
                inconsistencias.add("O site deve possuir no máximo 50 caracteres");
            }
            
            if(p.getLogradouro() != null && p.getLogradouro().length() > 100){
                inconsistencias.add("O logradouro deve possuir no máximo 100 caracteres");
            }
            
            if(p.getNumero() != null && p.getNumero().length() > 10){
                inconsistencias.add("O número deve possuir no máximo 10 caracteres");
            }
            
            if(p.getComplemento() != null && p.getComplemento().length() > 20){
                inconsistencias.add("O complemento deve possuir no máximo 20 caracteres");
            }
            
            if(p.getBairro() != null && p.getBairro().length() > 100){
                inconsistencias.add("O bairro deve possuir no máximo 100 caracteres");
            }
            
            if(p.getSexo() == null){
                inconsistencias.add("Informe o sexo");
            }
            
        } else {
            inconsistencias.add("Entidade inválida");
        }

        return inconsistencias;
    }
    
}
