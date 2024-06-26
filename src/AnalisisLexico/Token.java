
package AnalisisLexico;

public class Token {
    public String lexema;
    public String grupoLexico;
    public int linea;
    public int columna;

    public Token(String lexema, String grupoLexico, int linea, int column) {
        this.lexema = lexema;
        this.grupoLexico = grupoLexico;
        this.linea = linea;
        this.columna = column;
    }
    
    public Object[] toArray(){
        return new Object[]{lexema,grupoLexico,linea,columna};
    }
    
    public String getLexicalComp(){
       return grupoLexico;
    }
    public String getLexeme(){
       return lexema;
    }
    public int getLine(){
       return linea;
    }
    public int getColumn(){
       return columna;
    }
    
    
    @Override
    public String toString() {
        return "Token(" + this.lexema + ", " + this.grupoLexico + ", " + this.linea + ", " + this.columna + ")";
    }
}
