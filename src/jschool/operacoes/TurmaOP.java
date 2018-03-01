package jschool.operacoes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import jschool.dao.DiaLetivoDAO;
import jschool.dao.InscricaoTurmaDAO;
import jschool.dao.TurmaDAO;
import jschool.modelo.entidade.Aluno;
import jschool.modelo.entidade.DiaLetivo;
import jschool.modelo.entidade.Frequencia;
import jschool.modelo.entidade.IEntidade;
import jschool.modelo.entidade.InscricaoTurma;
import jschool.modelo.entidade.Professor;
import jschool.modelo.entidade.Turma;

/**
 *
 * @author andre
 */
public class TurmaOP {
    
    public static void gerarDiasAula(Turma t){
        
        //Data inicial com Calendar
        Calendar dataAula = Calendar.getInstance();
        dataAula.setTime(t.getDataInicio());
        
        //Thu Jan 01 1970 21:00:00 GMT-0300
        long primeiraDataMilisegundos = 86400000L;
        long diferencaDatasMilisegundos = 0;
        
        int numeroDias = 0;
        
        //Obtem a diferença das datas em milisegundos
        diferencaDatasMilisegundos = (t.getDataFim().getTime() - t.getDataInicio().getTime());
        
        //Obtem a diferença das datas em dias
        numeroDias = (int) (diferencaDatasMilisegundos / primeiraDataMilisegundos) + 1;
        
        //Laço para percorrer o período de dias entre a data inicial e final da turma
        for(int i = 0; i < numeroDias; i++){
            
            /*
                Verifica se a data atual é um dia da semana
                em que a turma deveria ter aula e, se for
                adiciona como dia de aula
            */
            for(Integer dia : t.getDiasAulasSemana()){
                if(dataAula.get(Calendar.DAY_OF_WEEK) == dia){
                    new DiaLetivoDAO().salvar(new DiaLetivo(t, dataAula.getTime()));
                }
            }
            
            //Aumenta em um dia a dataAtual
            dataAula.set(Calendar.DAY_OF_MONTH, dataAula.get(Calendar.DAY_OF_MONTH) + 1);
        }
    }
    
    public static ArrayList<DiaLetivo> gerarDiasLetivos(Turma t){
        
        ArrayList<DiaLetivo> diasLetivos = new ArrayList();
        
        //Data inicial com Calendar
        Calendar dataAula = Calendar.getInstance();
        dataAula.setTime(t.getDataInicio());
        
        //Thu Jan 01 1970 21:00:00 GMT-0300
        long primeiraDataMilisegundos = 86400000L;
        long diferencaDatasMilisegundos = 0;
        
        int numeroDias = 0;
        
        //Obtem a diferença das datas em milisegundos
        diferencaDatasMilisegundos = (t.getDataFim().getTime() - t.getDataInicio().getTime());
        
        //Obtem a diferença das datas em dias
        numeroDias = (int) (diferencaDatasMilisegundos / primeiraDataMilisegundos) + 1;
        
        //Laço para percorrer o período de dias entre a data inicial e final da turma
        for(int i = 0; i < numeroDias; i++){
            
            /*
                Verifica se a data atual é um dia da semana
                em que a turma deveria ter aula e, se for
                adiciona como dia de aula
            */
            for(Integer dia : t.getDiasAulasSemana()){
                if(dataAula.get(Calendar.DAY_OF_WEEK) == dia){
                    diasLetivos.add(new DiaLetivo(t, dataAula.getTime()));
                }
            }
            
            //Aumenta em um dia a dataAtual
            dataAula.set(Calendar.DAY_OF_MONTH, dataAula.get(Calendar.DAY_OF_MONTH) + 1);
        }
        
        return diasLetivos;
    }
    
    public static boolean existemColisoesDiasLetivos(Aluno aluno, Turma turma){
        
        boolean existeColisao = false;
        
        ArrayList<DiaLetivo> diasLetivosNovaTurma = gerarDiasLetivos(turma);
        DiaLetivoDAO diaLetivoDao = new DiaLetivoDAO();
        
        for(DiaLetivo dl : diasLetivosNovaTurma){
            if(diaLetivoDao.existeColisaoAluno(turma, aluno, dl)){
                existeColisao = true;
                break;
            }
        }
        
        return existeColisao;
    }
    
    public static boolean existemColisoesDiasLetivos(Professor professor, Turma turma){
        
        boolean existeColisao = false;
        
        ArrayList<DiaLetivo> diasLetivosNovaTurma = gerarDiasLetivos(turma);
        DiaLetivoDAO diaLetivoDao = new DiaLetivoDAO();
        
        for(DiaLetivo dl : diasLetivosNovaTurma){
            if(diaLetivoDao.existeColisaoAluno(turma, professor, dl)){
                existeColisao = true;
                break;
            }
        }
        
        return existeColisao;
    }
    
    public static void excluirDiasAula(Turma t){
        
    }
    
    public static ArrayList<IEntidade> gerarFrequencia(Turma t, Date data){
        
        ArrayList<IEntidade> frequencias = new ArrayList();
        ArrayList<IEntidade> matriculas = new InscricaoTurmaDAO().consultar(t);
        
        for(IEntidade matricula : matriculas){
            Frequencia f = new Frequencia();
            f.setData(data);
            f.setPresente(true);
            f.setMatricula((InscricaoTurma) matricula);
            
            frequencias.add(f);
        }
        
        return frequencias;
    }
    
    public static void AlterarStatusTurmas(){
        
        Date hoje = new Date();
        
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(hoje);
        calendario.add(Calendar.DAY_OF_YEAR, -1);
        
        Date ontem = calendario.getTime();
        
        TurmaDAO turmaDao = new TurmaDAO();
        turmaDao.IniciarAulas(ontem);
        turmaDao.EncerrarAulas(ontem);
        
    }
}
