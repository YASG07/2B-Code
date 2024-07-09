package AnalisisLexico;
import java.util.ArrayList;

public class TablaErrores {
    ArrayList<ErrorToken> tablaError;
    
    public TablaErrores(){
       this.tablaError =  new ArrayList<>(); 
    }
    public void insertar (ErrorToken error){
        this.tablaError.add(error);
    }
    public ArrayList<ErrorToken> getErrores(){
        return tablaError;
    }
}
