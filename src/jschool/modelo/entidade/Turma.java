/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.modelo.entidade;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import jschool.enumeracoes.SituacaoTurma;
import jschool.enumeracoes.TipoMedia;

/**
 *
 * @author andre
 */
public class Turma implements IEntidade {

    private int id;
    private Curso curso;
    private Professor professor;
    private int vagas;
    private double mediaAprovacao;
    private String sala;
    private String descricao;
    private Date dataInicio;
    private Date dataFim;
    private Date horarioInicioAula;
    private Date horarioFimAula;
    private Calendar horarioInicial;
    private Calendar horarioFinal;
    private TipoMedia tipoMedia;
    private SituacaoTurma situacao;
    private ArrayList<Integer> diasAulasSemana;
    private ArrayList<DiaLetivo> diasAulas;

    public Turma() {
        this.curso = new Curso();
        this.professor = new Professor();
        this.diasAulasSemana = new ArrayList();
        this.diasAulas = new ArrayList();
        this.horarioInicial = Calendar.getInstance();
        this.horarioFinal = Calendar.getInstance();
        this.situacao = SituacaoTurma.ABERTA;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Date getHorarioInicioAula() {
        return horarioInicioAula;
    }

    public void setHorarioInicioAula(Date horarioInicioAula) {
        this.horarioInicioAula = horarioInicioAula;
    }

    public Date getHorarioFimAula() {
        return horarioFimAula;
    }

    public void setHorarioFimAula(Date horarioFimAula) {
        this.horarioFimAula = horarioFimAula;
    }

    public int getVagas() {
        return vagas;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public double getMediaAprovacao() {
        return mediaAprovacao;
    }

    public void setMediaAprovacao(double mediaAprovacao) {
        this.mediaAprovacao = mediaAprovacao;
    }

    public TipoMedia getTipoMedia() {
        return tipoMedia;
    }

    public void setTipoMedia(TipoMedia tipoMedia) {
        this.tipoMedia = tipoMedia;
    }

    public SituacaoTurma getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoTurma situacao) {
        this.situacao = situacao;
    }

    public ArrayList<Integer> getDiasAulasSemana() {
        return diasAulasSemana;
    }

    public void setDiasAulasSemana(ArrayList<Integer> diasAulasSemana) {
        this.diasAulasSemana = diasAulasSemana;
    }

    public ArrayList<DiaLetivo> getDiasAulas() {
        return diasAulas;
    }

    public void setDiasAulas(ArrayList<DiaLetivo> diasAulas) {
        this.diasAulas = diasAulas;
    }

    public Calendar getHorarioInicial() {
        return horarioInicial;
    }

    public void setHorarioInicial(Calendar horarioInicial) {
        this.horarioInicial = horarioInicial;
    }
    
    public void setHorarioInicial(long horarioInicial){
        this.horarioInicial.setTimeInMillis(horarioInicial);
    }

    public Calendar getHorarioFinal() {
        return horarioFinal;
    }

    public void setHorarioFinal(Calendar horarioFinal) {
        this.horarioFinal = horarioFinal;
    }
    
    public void setHorarioFinal(long horarioFinal){
        this.horarioFinal.setTimeInMillis(horarioFinal);
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
