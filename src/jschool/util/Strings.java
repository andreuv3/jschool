package jschool.util;

/**
 *
 * @author andre
 */
public class Strings {
    
    public static boolean IsNullOrEmpty(String s){
        return s == null || s.equals("");
    }
    
    public static boolean IsWhiteSpace(String s){
        return s.trim().equals("");
    }
}
