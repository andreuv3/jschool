/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.modelo.entidade;

import java.util.Date;
import jschool.enumeracoes.Situacao;

/**
 *
 * @author andre
 */
public class Professor extends Pessoa{
   
    private String numeroCtps;
    private Date dataAdmissao;
    private Date dataDemisao;
    
    public Professor(){
        super();
        this.dataAdmissao = new Date();
        super.setSituacao(Situacao.ATIVO);
    }
    
    public Professor(int id){
        super();
        this.dataAdmissao = new Date();
        super.setSituacao(Situacao.ATIVO);
        super.setId(id);
    }

    public String getNumeroCtps() {
        return numeroCtps;
    }

    public void setNumeroCtps(String numeroCtps) {
        this.numeroCtps = numeroCtps;
    }

    public Date getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(Date dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public Date getDataDemisao() {
        return dataDemisao;
    }

    public void setDataDemisao(Date dataDemisao) {
        this.dataDemisao = dataDemisao;
    }
}
