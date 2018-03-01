/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.modelo.entidade;

import java.util.Date;
import jschool.enumeracoes.Situacao;
import jschool.enumeracoes.SituacaoAluno;

/**
 *
 * @author andre
 */
public class InscricaoTurma implements IEntidade{
    
    private int id;
    private double nota;
    private Date dataInscricao;
    private Turma turma;
    private Aluno aluno;
    private Situacao status;
    private SituacaoAluno situacaoAluno;

    public InscricaoTurma(){
        this.turma = new Turma();
        this.aluno = new Aluno();
        this.status = Situacao.ATIVO;
        this.situacaoAluno = SituacaoAluno.CURSANDO;
        this.dataInscricao = new Date();
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Date getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(Date dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public Situacao getStatus() {
        return status;
    }

    public void setStatus(Situacao status) {
        this.status = status;
    }

    public SituacaoAluno getSituacaoAluno() {
        return situacaoAluno;
    }

    public void setSituacaoAluno(SituacaoAluno situacaoAluno) {
        this.situacaoAluno = situacaoAluno;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
}
