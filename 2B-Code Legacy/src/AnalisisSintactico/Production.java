
package AnalisisSintactico;

import AnalisisLexico.Token;
import java.util.ArrayList;

public class Production {
    private String nombre;
    private final ArrayList<Token> tokens;
    private int linea;
    private int columna;
    private int lineaF;
    private int columnaF;

    public Production() {
        this.nombre = null;
        this.tokens = new ArrayList();
        this.linea = this.columna = this.lineaF = this.columnaF = 0;
    }

    public Production(Token var1) {
        this.tokens = new ArrayList();
        this.nombre = var1.getLexicalComp();
        this.tokens.add(var1);
        this.linea = this.lineaF = var1.getLine();
        this.columna = this.columnaF = var1.getColumn();
    }

    public String getName() {
        return this.nombre;
    }

    public ArrayList<Token> getTokens() {
        return this.tokens;
    }

    public int getLine() {
        return this.linea;
    }

    public int getColumn() {
        return this.columna;
    }

    public int getFinalLine() {
        return this.lineaF;
    }

    public int getFinalColumn() {
        return this.columnaF;
    }

    public int getSizeTokens() {
        return this.tokens.size();
    }

    public void addTokens(Production var1) {
        ArrayList var2 = var1.getTokens();
        var2.forEach((var1x) -> {
            this.tokens.add((Token) var1x);
        });
        Token var3;
        if (var2.size() > 0) {
            var3 = (Token)var2.get(0);
            if (this.nombre == null) {
                this.nombre = var3.getLexicalComp().toUpperCase();
            }

            var3 = (Token)var2.get(var2.size() - 1);
            this.lineaF = var3.getLine();
            this.columnaF = var3.getColumn();
        }

        if (this.tokens.size() > 0) {
            var3 = (Token)this.tokens.get(0);
            this.linea = var3.getLine();
            this.columna = var3.getColumn();
        }

    }

    public String lexemeRank(int var1) {
        Token var2 = this.tokenRank(var1);
        return var2 != null ? var2.getLexeme() : null;
    }

    public String lexicalCompRank(int var1) {
        Token var2 = this.tokenRank(var1);
        return var2 != null ? var2.getLexicalComp() : null;
    }

    public Token tokenRank(int var1) {
        int var2 = var1;
        int var3 = this.tokens.size();
        if (var1 >= 0) {
            if (var1 > var3 - 1) {
                System.out.println("\n" + Functions.ANSI_RED_BLACK + "El índice excede el tamaño de tokens de la producción: " + var1 + ">" + (var3 - 1));
                return null;
            }
        } else if (var1 < -var3) {
            System.out.println("\n" + Functions.ANSI_RED_BLACK + "El índice es inferior al tamaño de tokens de la producción: " + var1 + "<-" + var3);
            return null;
        }

        if (var1 < 0) {
            var2 = var1 + var3;
        }

        return (Token)this.tokens.get(var2);
    }

    public String lexemeRank(int var1, int var2) {
        ArrayList var3 = this.tokenRank(var1, var2);
        if (var3 != null) {
            String var4 = "";

            for(int var5 = 0; var5 < var3.size(); ++var5) {
                Token var6 = (Token)var3.get(var5);
                if (var5 != var3.size() - 1) {
                    var4 = var4 + var6.getLexeme() + " ";
                } else {
                    var4 = var4 + var6.getLexeme();
                }
            }

            return var4;
        } else {
            return null;
        }
    }

    public String lexicalCompRank(int var1, int var2) {
        ArrayList var3 = this.tokenRank(var1, var2);
        if (var3 != null) {
            String var4 = "";

            for(int var5 = 0; var5 < var3.size(); ++var5) {
                Token var6 = (Token)var3.get(var5);
                if (var5 != var3.size() - 1) {
                    var4 = var4 + var6.getLexicalComp() + " ";
                } else {
                    var4 = var4 + var6.getLexicalComp();
                }
            }

            return var4;
        } else {
            return null;
        }
    }

    public ArrayList<Token> tokenRank(int var1, int var2) {
        int var3 = var1;
        int var4 = var2;
        int var5 = this.tokens.size();
        if (var1 >= 0) {
            if (var1 > var5 - 1) {
                System.out.println("\n" + Functions.ANSI_RED_BLACK + "El índice inicial excede el tamaño de tokens de la producción: " + var1 + ">" + (var5 - 1));
                return null;
            }
        } else if (var1 < -var5) {
            System.out.println("\n" + Functions.ANSI_RED_BLACK + "El índice inicial es inferior al tamaño de tokens de la producción: " + var1 + "<-" + var5);
            return null;
        }

        if (var2 >= 0) {
            if (var2 > var5 - 1) {
                System.out.println("\n" + Functions.ANSI_RED_BLACK + "El índice final excede el tamaño de tokens de la producción: " + var2 + ">" + (var5 - 1));
                return null;
            }
        } else if (var2 < -var5) {
            System.out.println("\n" + Functions.ANSI_RED_BLACK + "El índice final es inferior al tamaño de tokens de la producción: " + var2 + "<-" + var5);
            return null;
        }

        if (var1 < 0 && var2 >= 0) {
            var3 = var1 + var5;
        } else if (var1 >= 0 && var2 < 0) {
            var4 = var2 + var5;
        }

        if (var3 > var4) {
            System.out.println("\n" + Functions.ANSI_RED_BLACK + "El índice inicial debe de ser menor que el índice final: " + var3 + ">" + var4);
            return null;
        } else {
            if (var3 < 0) {
                var3 += var5;
            }

            if (var4 < 0) {
                var4 += var5;
            }

            ArrayList var6 = new ArrayList();

            for(int var7 = var3; var7 <= var4; ++var7) {
                var6.add((Token)this.tokens.get(var7));
            }

            return var6;
        }
    }

    public boolean nameEqualTo(String var1) {
        return this.nombre.equals(var1);
    }

    public boolean nameEqualTo(String[] var1) {
        String[] var2 = var1;
        int var3 = var1.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String var5 = var2[var4];
            if (var5.equals(this.nombre)) {
                return true;
            }
        }

        return false;
    }

    public void setName(String var1) {
        this.nombre = var1;
    }

    public String toString() {
        String var1 = "";
        int var2 = this.tokens.size();

        for(int var3 = 0; var3 < var2; ++var3) {
            if (var3 != var2 - 1) {
                var1 = var1 + this.tokens.get(var3) + ",\n";
            } else {
                var1 = var1 + this.tokens.get(var3);
            }
        }

        return "Produccion(" + this.nombre + ", " + this.linea + ", " + this.columna + ", " + this.lineaF + ", " + this.columnaF + ", [\n" + var1 + "])";
    }
}
