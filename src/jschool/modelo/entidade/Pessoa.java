/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.modelo.entidade;

import java.util.Date;
import jschool.enumeracoes.Sexo;
import jschool.enumeracoes.Situacao;

/**
 *
 * @author andre
 */
public abstract class Pessoa implements IEntidade{
    
    private int id;
    private String nome;
    private Situacao situacao;
    private Sexo sexo;
    private Date dataNascimento;
    private String rg;
    private String cpf;
    private String telefone;
    private String celular;
    private String email;
    private String site;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private Cidade cidade;
    
    public Pessoa(){
        this.cidade = new Cidade();
        this.setSituacao(Situacao.ATIVO);
    }
    
    public Pessoa(int id){
        this.cidade = new Cidade();
        this.setSituacao(Situacao.ATIVO);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }
    
    public String getSituacaoString(){
        return this.situacao.getDescricaoSituacao();
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }
    
    public String getSexoString(){
        return sexo.getSexoString();
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }   

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
