/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jschool.enumeracoes;

/**
 *
 * @author andre
 */
public enum Sexo {
    
    MASCULINO(1), FEMININO(2);
    
    private final int sexo;

    Sexo(int valorSexo) {
        sexo = valorSexo;
    }

    public int getSexo() {
        return sexo;
    }
    
    public String getSexoString(){
        if(sexo == 1){
            return "Masculino";
        } else{
            return "Feminino";
        }
    }
    
    @Override
    public String toString(){
        if(sexo == 1){
            return "Masculino";
        } else{
            return "Feminino";
        }
    }
}
