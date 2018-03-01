package jschool.operacoes;

import jschool.modelo.entidade.Aluno;
import jschool.util.Formatador;

/**
 *
 * @author andre
 */
public class AlunoOP {
    
    public String GerarNumeroMatricula(Aluno a){
        
        String numeroMatricula = "";
        
        int numeroAleatorio = (int) (Math.random() * 101);
        
        String dataMatricula = Formatador.dateParaString(a.getDataMatricula()).replace("/", "");
        numeroMatricula = a.getId() + "-" + dataMatricula + "-" + numeroAleatorio;
        
        return numeroMatricula;
    }
    
}
