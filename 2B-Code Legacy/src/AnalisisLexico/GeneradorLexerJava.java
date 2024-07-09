
package AnalisisLexico;

import java.io.File;
//Obtiene la ruta del archivo Lexer.flex (contiene las reglas para generar 
//el analizador léxico).
//Llama al método generarLexer() pasando la ruta del archivo Lexer.flex como argumento.
public class GeneradorLexerJava {
    public static void main(String[] args) {
        String lexerFile = System.getProperty("user.dir") + "/src/AnalisisLexico/Lexer.flex";         
        generarLexer(lexerFile);
    }
//Crea un nuevo objeto File utilizando la ruta proporcionada.
//Llama al método generate() de la clase jflex.Main, presumiblemente 
//un método proporcionado por la herramienta JFlex, que genera un analizador léxico a partir del archivo especificado.

    public static void generarLexer(String ruta){
        File archivo = new File(ruta);
        jflex.Main.generate(archivo);
    }
}
