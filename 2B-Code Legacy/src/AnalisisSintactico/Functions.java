
package AnalisisSintactico;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

public class Functions {
    public static String ANSI_BLACK = "\u001b[30m";
    public static String ANSI_RED = "\u001b[31m";
    public static String ANSI_RED_BLACK = "\u001b[31;2m";
    public static String ANSI_GREEN = "\u001b[32m";
    public static String ANSI_GREEN_BLACK = "\u001b[32;2m";
    public static String ANSI_YELLOW = "\u001b[33m";
    public static String ANSI_YELLOW_BLACK = "\u001b[33;2m";
    public static String ANSI_BLUE = "\u001b[34m";
    public static String ANSI_BLUE_BLACK = "\u001b[34;2m";
    public static String ANSI_PURPLE = "\u001b[35m";
    public static String ANSI_PURPLE_BLACK = "\u001b[35;2m";
    public static String ANSI_RESET = "\u001b[0m";

    public Functions() {
    }

    public static void clearDataInTable(JTable var0) {
        DefaultTableModel var1 = (DefaultTableModel)var0.getModel();
        var1.setRowCount(0);
    }

    public static void addRowDataInTable(JTable var0, Object[] var1) {
        DefaultTableModel var2 = (DefaultTableModel)var0.getModel();
        var2.addRow(var1);
    }

    

    public static String centerWord(String var0, String var1, String var2, int var3) {
        int var4 = var0.length();
        if (var3 <= var4) {
            return var0;
        } else {
            var3 -= var4;
            String var5;
            String var6;
            if (var3 % 2 == 0) {
                var3 /= 2;
                var5 = var1.repeat(var3);
                var6 = var2.repeat(var3);
                return var5 + var0 + var6;
            } else {
                var3 = (var3 - 1) / 2;
                var5 = var1.repeat(var3 + 1);
                var6 = var2.repeat(var3);
                return var5 + var0 + var6;
            }
        }
    }

    public static String formatString(String var0, String var1, String var2) {
        String var3 = "";

        while(true) {
            int var4 = var0.indexOf("\\" + var1);
            if (var4 == -1) {
                var3 = var3 + var0.substring(0, var0.length()).replace(var1, var2);
                return var3;
            }

            var3 = var3 + var0.substring(0, var4).replace(var1, var2) + var1;
            var0 = var0.substring(var4 + var1.length() + 1, var0.length());
        }
    }

    public static boolean isLetter(String var0) {
        return var0.matches("[A-Za-zÑñÁÉÍÓÚáéíóúÜü]");
    }

    public static boolean isWord(String var0) {
        return var0.matches("[A-Za-zÑñÁÉÍÓÚáéíóúÜü]+");
    }

    public static boolean isDigit(String var0) {
        return var0.matches("[0-9]");
    }

    public static boolean isNumber(String var0) {
        return var0.matches("0|[1-9][0-9]*");
    }

    public static boolean isSpace(String var0) {
        return var0.matches("[ \t\f]+");
    }

    public static boolean isSpaceOrSaltLine(String var0) {
        return var0.matches("[ \t\f\r\n]+");
    }

    private static String formatCode(String var0, String var1, String var2, String var3) {
        var0 = var0.replaceAll("[\r\t\f]", "");
        var0 = var0.replace(var1, "\n" + var1 + "\n").replace(var2, "\n" + var2 + "\n").replace(var3, "\n" + var3 + "\n").replaceAll("[\n]+", "\n").replaceAll(" +", " ");
        return var0;
    }

    
}
