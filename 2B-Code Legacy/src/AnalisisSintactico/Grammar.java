
package AnalisisSintactico;

import AnalisisLexico.ErrorToken;
import AnalisisLexico.Token;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * La clase Grammar representa la gramática utilizada en el análisis sintáctico
 * de un lenguaje. Esta clase contiene las producciones de la gramática, maneja
 * los errores léxicos y controla varios aspectos del proceso de análisis sintáctico.
 */
public class Grammar {
    // Lista de producciones de la gramática.
    private ArrayList<Production> producciones = new ArrayList();
    // Lista de errores léxicos encontrados durante el análisis.
    private final ArrayList<ErrorToken> errors;
    // Indicador de si se deben mostrar líneas y columnas de inicio.
    private boolean lineaColumnaIni;
    // Indicador de si se debe realizar una interpretación manual de componentes.
    private boolean compIntManual;
    // Indicador de si se debe indexar componentes internos.
    private boolean indexComponentInt;
    // Indicador de si se deben mostrar mensajes adicionales.
    private boolean mostrarM;
    // Indicador de si se debe realizar una validación adicional.
    private boolean validacion;
    // Contador de producciones.
    private int contProd = 0;

    public Grammar(ArrayList<Token> var1, ArrayList<ErrorToken> var2) {
        // Inicialización de variables booleanas.
        this.lineaColumnaIni = this.validacion = true;
        this.compIntManual = this.indexComponentInt = false;
        this.mostrarM = true;
        // Creación de producciones a partir de la lista de tokens.
        var1.forEach((var1x) -> {
            this.producciones.add(new Production(var1x));
        });
        // Asignación de la lista de errores léxicos.
        this.errors = var2;
        // Mensajes de información.
        String var3 = "2B'Scripts ";
        String var4 = "Gramática generada con éxito, se crearon " + this.producciones.size() + " producciones";
        String var5 = "Todos los componentes están listos para su ejecución";
        // Longitud del mensaje de información.
        int var6 = var4.length() + 6;
        // Impresión de mensajes formateados en consola.
        String var10001 = Functions.ANSI_GREEN_BLACK;
        System.out.println("\n" + var10001 + "-".repeat(var6));
        var10001 = Functions.ANSI_GREEN_BLACK;
        System.out.println(var10001 + "| " + Functions.centerWord(var4, " ", " ", var6 - 4) + " |");
        var10001 = Functions.ANSI_GREEN_BLACK;
        System.out.println(var10001 + "| " + Functions.centerWord(var5, " ", " ", var6 - 4) + " |");
        var10001 = Functions.ANSI_GREEN_BLACK;
        System.out.println(var10001 + Functions.centerWord(var3, "-", "-", var6) + Functions.ANSI_RESET + "\n");
    }
/**
 * Activa la visualización de líneas y columnas de inicio.
 */
    public void initialLineColumn() {
        this.lineaColumnaIni = true;
    }
/**
 * Desactiva la visualización de líneas y columnas de inicio.
 */
    public void finalLineColumn() {
        this.lineaColumnaIni = false;
    }
/**
 * Elimina elementos numéricos y duplicados de un array de cadenas.
 *
 * @param var1 Array de cadenas que puede contener números y duplicados.
 * @return Un array de cadenas sin números y sin duplicados. Si no se elimina ningún elemento,
 *         se devuelve el array original.
 */
    private String[] eliminarCompRepNum(String[] var1) {
        // Crear una lista a partir del array de entrada
        ArrayList var2 = new ArrayList();
        var2.addAll(Arrays.asList(var1));

        int var3;
        // Eliminar elementos que son números
        for(var3 = 0; var3 < var2.size(); ++var3) {
            if (((String)var2.get(var3)).matches("[0-9]+")) {
                var2.remove(var3);
                --var3;
            }
        }

        int var4;
        // Eliminar elementos duplicados
        for(var3 = 0; var3 < var2.size(); ++var3) {
            for(var4 = 0; var4 < var2.size(); ++var4) {
                if (var3 != var4 && ((String)var2.get(var3)).equals(var2.get(var4))) {
                    var2.remove(var4);
                    --var4;
                }
            }
        }
        // Si no se eliminó ningún elemento, devolver el array original
        if (var2.size() == var1.length) {
            return var1;
        } else {
            // Convertir la lista resultante de nuevo a un array
            String[] var5 = new String[var2.size()];

            for(var4 = 0; var4 < var5.length; ++var4) {
                var5[var4] = (String)var2.get(var4);
            }

            return var5;
        }
    }

    private String[] separarComp(String var1) {
        // Limpiar y separar la cadena de entrada
        // Reemplazar caracteres especiales con espacio
        return var1 == null ? null : this.eliminarCompRepNum(var1.replaceAll("[^0-9A-Za-zÑñÁÉÍÓÚáéíóúÜü_ ]", " ")
        // Eliminar espacios al inicio y al final
                .trim()
        // Reemplazar múltiples espacios con uno solo 
                .replaceAll(" +", " ").split(" "));
    }

    
 /**
 * Agrupa producciones en la gramática basándose en los criterios proporcionados.
 *
 * @param var1 Nombre de la nueva producción resultante de la agrupación.
 * @param var2 Patrón de coincidencia para los nombres de las producciones.
 * @param var3 Lista de componentes que deben ser agrupados.
 * @param var4 Indicador de si se deben considerar producciones parciales en la agrupación.
 * @param var5 Línea del código fuente donde se está realizando la agrupación (para manejo de errores).
 * @param var6 Mensaje de error a registrar si ocurre un problema durante la agrupación.
 * @param var7 Índice del componente interno que se está manejando.
 * @param var8 Lista adicional donde se almacenan las nuevas producciones agrupadas (si no es `null`).
 */
//El método group realiza una agrupación de producciones en una gramática basada 
//en ciertos criterios y condiciones. Esta operación se utiliza en el análisis 
//sintáctico de lenguajes formales para consolidar producciones y manejar errores léxicos y sintácticos.
    public void group(String var1, String var2, String[] var3, boolean var4, int var5, String var6, int var7, ArrayList<Production> var8) {
        // Imprimir mensaje de separación si está habilitado
        if (this.mostrarM) {
            System.out.println(".".repeat(100));
        }
        // Incrementar el contador de producciones
        ++this.contProd;
        // Validar los parámetros de agrupación
        if (this.validarAgr(var1, var2, var3, var7)) {
            // Eliminar espacios del patrón de coincidencia
            var2 = var2.replace(" ", "");
            // Lista para almacenar las nuevas producciones
            ArrayList var9 = new ArrayList();
            int var10 = this.producciones.size();
            int var11 = 0;
            // Ajustar el índice del componente interno si es negativo
            if (this.indexComponentInt && var7 < 0) {
                var7 += var3.length;
            }

            int var12;
            // Iterar sobre las producciones actuales
            for(var12 = 0; var12 < var10; ++var12) {
                Production var13 = (Production)this.producciones.get(var12);
                if (!var13.nameEqualTo(var3)) {
                    var9.add(var13);
                } else {
                    int var14 = -1;
                    String var15 = "";

                    for(int var16 = var12; var16 < var10; ++var16) {
                        var13 = (Production)this.producciones.get(var16);
                        Production var17;
                        Production var18;
                        int var19;
                        Production var20;
                        // Evaluar la coincidencia de nombres y realizar la agrupación
                        if (!var13.nameEqualTo(var3) || var14 != -1 && var4) {
                            if (var14 == -1) {
                                var17 = (Production)this.producciones.get(var12);
                                var9.add(var17);
                            } else {
                                var17 = new Production();
                                var18 = new Production();

                                for(var19 = var12; var19 <= var14; ++var19) {
                                    var20 = (Production)this.producciones.get(var19);
                                    var17.addTokens(var20);
                                    if (this.indexComponentInt && var19 - var12 == var7) {
                                        var18 = var20;
                                    }
                                }

                                var17.setName(var1);
                                var9.add(var17);
                                if (var8 != null) {
                                    var8.add(var17);
                                }

                                if (var6 != null) {
                                    if (this.indexComponentInt) {
                                        this.errors.add(new ErrorToken("Sintáctico",var5, var6, var18, this.lineaColumnaIni));
                                    } else {
                                        this.errors.add(new ErrorToken("Sintáctico",var5, var6, var17, this.lineaColumnaIni));
                                    }
                                }

                                var12 = var14;
                                ++var11;
                            }
                            break;
                        }

                        var15 = var15 + var13.getName();
                        if (var15.matches(var2)) {
                            var14 = var16;
                        }

                        if (var16 == var10 - 1) {
                            if (var14 != -1) {
                                var17 = new Production();
                                var18 = new Production();

                                for(var19 = var12; var19 <= var14; ++var19) {
                                    var20 = (Production)this.producciones.get(var19);
                                    var17.addTokens(var20);
                                    if (this.indexComponentInt && var19 - var12 == var7) {
                                        var18 = var20;
                                    }
                                }

                                var17.setName(var1);
                                var9.add(var17);
                                if (var8 != null) {
                                    var8.add(var17);
                                }

                                if (var6 != null) {
                                    if (this.indexComponentInt) {
                                        this.errors.add(new ErrorToken("Sintáctico",var5, var6, var18, this.lineaColumnaIni));
                                    } else {
                                        this.errors.add(new ErrorToken("Sintáctico",var5, var6, var17, this.lineaColumnaIni));
                                    }
                                }

                                var12 = var14;
                                ++var11;
                            } else {
                                var17 = (Production)this.producciones.get(var12);
                                var9.add(var17);
                            }
                        }
                    }
                }
            }
        // Imprimir mensajes de resultado de la agrupación si está habilitado
            if (this.mostrarM) {
                if (var11 > 0) {
                    var12 = this.producciones.size();
                    int var21 = var9.size();
                    System.out.println("**** Agrupación " + this.contProd + " \"" + var1 + "\" realizada con éxito ****\nCantidad de componentes: " + var3.length);
                    if (var12 == var21) {
                        System.out.println("No hubo reducción en la cantidad de producciones (" + var12 + ")\n");
                    } else {
                        System.out.println("La cantidad de producciones se redujo de " + var12 + " a " + var21 + "\n");
                    }
                } else {
                    System.out.println(Functions.ANSI_BLUE_BLACK + "**** Agrupación " + this.contProd + " \"" + var1 + "\" realizada, pero sin cambios ****\n" + Functions.ANSI_BLUE_BLACK + "Cantidad de componentes: " + var3.length + "\n");
                }
            }
        // Actualizar la lista de producciones con las nuevas agrupaciones
            this.producciones = var9;
        }

    }
// Se encarga de ejecutar repetidamente una tarea proporcionada (var1) hasta que no se detecten cambios 
// en la cantidad de producciones en la gramática.
    public void loopForFunExecUntilChangeNotDetected(Runnable var1) {
        // Obtener el tamaño inicial de producciones
        int var2 = this.producciones.size();

        // Ejecutar en un bucle infinito
        while(true) {
            // Ejecutar la tarea proporcionada
            var1.run();
            // Si no se detectan cambios en el tamaño de las producciones, salir del bucle
            if (var2 == this.producciones.size()) {
                return;
            }
        // Actualizar el tamaño para la próxima iteración
            var2 = this.producciones.size();
        }
    }
/**
 * Valida los parámetros de una agrupación de producciones antes de realizarla.
 *
 * @param var1 El nombre de la producción.
 * @param var2 La expresión regular de la producción.
 * @param var3 Los componentes de la producción.
 * @param var4 El índice del componente interno.
 * @return true si la validación es exitosa y la agrupación puede realizarse, false de lo contrario.
 */
    private boolean validarAgr(String var1, String var2, String[] var3, int var4) {
        // Verificar si la validación está desactivada
        if (!this.validacion) {
            return true;
        } else {
            // Contadores de errores y estado de validación
            int var5 = 0;
            byte var6 = 0;
            String var7 = "";
            // Validación del nombre de la producción
            if (var1 == null) {
                ++var5;
                var7 = var7 + "\n" + Functions.ANSI_RED_BLACK + var5 + ". El nombre de la producción es un valor nulo, proceda a corregirla o eliminarla";
            } else if (var1.contains(" ")) {
                // Verificar si el nombre de la producción contiene espacios
                ++var5;
                var7 = var7 + "\n" + Functions.ANSI_RED_BLACK + var5 + ". El nombre de la producción contiene uno o más espacios, proceda a eliminarlos:\n\"" + var1 + "\"";
            } else if (var1.equals("")) {
                // Verificar si el nombre de la producción es una cadena vacía
                ++var5;
                var7 = var7 + "\n" + Functions.ANSI_RED_BLACK + var5 + ". El nombre de la producción es una cadena vacía, proceda a corregirla o eliminarla";
            } else if (var1.matches("[0-9]+")) {
                // Verificar si el nombre de la producción cumple con la expresión regular dada
                ++var5;
                var7 = var7 + "\n" + Functions.ANSI_RED_BLACK + var5 + ". El nombre de la producción no puede ser un número, proceda a corregirla o eliminarla:\n\"" + var1 + "\"";
            } else if (!var1.matches("[A-Za-zÑñÁÉÍÓÚáéíóúÜü_][0-9A-Za-zÑñÁÉÍÓÚáéíóúÜü_]*")) {
                ++var5;
                var7 = var7 + "\n" + Functions.ANSI_RED_BLACK + var5 + ". El nombre de la producción es inválido, debe de cumplir con la siguiente ER: [A-Za-z_][A-Za-z0-9_]*, proceda a corregirla o eliminarla:\n\"" + var1 + "\"";
            }
        // Validación de la expresión regular de la producción
            if (var2 == null) {
                ++var5;
                var6 = -1;
                var7 = var7 + "\n" + Functions.ANSI_RED_BLACK + var5 + ". La expresión regular de la producción es un valor nulo, proceda a corregirla o eliminarla";
            } else if (var2.matches(" *")) {
                ++var5;
                var6 = -1;
                var7 = var7 + "\n" + Functions.ANSI_RED_BLACK + var5 + ". La expresión regular de la producción no contiene ningún componente ó expresión, proceda a corregirla o eliminarla";
            } else {
                try {
                    Pattern.compile(var2);
                } catch (Exception var12) {
                    ++var5;
                    var6 = -1;
                    var7 = var7 + "\n" + Functions.ANSI_RED_BLACK + var5 + ". Formato de expresión regular inválido: " + var12.getLocalizedMessage();
                }
            }

            String[] var8;
            int var9;
            int var10;
            String var11;
        // Validación de los componentes de la producción si la opción compIntManual está activada
            if (this.compIntManual) {
                var8 = var3;
                var9 = var3.length;

                for(var10 = 0; var10 < var9; ++var10) {
                    var11 = var8[var10];
                    if (var11.contains(" ")) {
                        ++var5;
                        var6 = -1;
                        var7 = var7 + "\n" + Functions.ANSI_RED_BLACK + var5 + ". Uno de los componentes introducidos tiene uno o más espacios, favor de corregirlo o eliminarlo:\n\"" + var11 + "\"";
                        break;
                    }
                }
            }
        // Validación adicional de los componentes de la producción
            if (var6 != -1) {
                var8 = var3;
                var9 = var3.length;

                for(var10 = 0; var10 < var9; ++var10) {
                    var11 = var8[var10];
                    if (!var11.matches("[A-Za-zÑñÁÉÍÓÚáéíóúÜü_][0-9A-Za-zÑñÁÉÍÓÚáéíóúÜü_]*")) {
                        ++var5;
                        var6 = -1;
                        var7 = var7 + "\n" + Functions.ANSI_RED_BLACK + var5 + ". Uno de los componentes no cumple con la siguiente ER: [A-Za-z_][A-Za-z0-9_]*, favor de corregirlo o eliminarlo:\n\"" + var11 + "\"";
                        break;
                    }
                }
            }
        // Validación de índice del componente interno si está habilitada
            if (this.indexComponentInt && var6 != -1) {
                if (var2 != null && !var2.matches("[0-9A-Za-zÑñÁÉÍÓÚáéíóúÜü_ ]+")) {
                    ++var5;
                    var6 = -1;
                    var7 = var7 + "\n" + Functions.ANSI_RED_BLACK + var5 + ". En caso de querer la línea y columna de un componente en específico, no debe de mandar una expresión\n" + Functions.ANSI_RED_BLACK + " ".repeat(String.valueOf(var5).length()) + "  regular como parámetro, si no solo los componentes en un orden determinado, proceda a corregirla:\n\"" + var2 + "\"";
                }

                if (var6 != -1) {
                    int var13 = var3.length;
                    if (var4 >= 0) {
                        if (var4 > var13 - 1) {
                            ++var5;
                            var7 = var7 + "\n" + Functions.ANSI_RED_BLACK + var5 + ". El índice excede al tamaño de la cantidad de componentes: " + var4 + ">" + (var13 - 1);
                        }
                    } else if (var4 < -var13) {
                        ++var5;
                        var7 = var7 + "\n" + Functions.ANSI_RED_BLACK + var5 + ". El índice negativo es inferior al tamaño de la cantidad de componentes: " + var4 + "<-" + var13;
                    }
                }
            }

            if (var5 > 0) {
                System.out.println(Functions.ANSI_RED_BLACK + "**** Agrupación " + this.contProd + " \"" + var1 + "\" no realizada****\n" + Functions.ANSI_RED_BLACK + "Δ Resuelva los errores a continuación descri"
                        + "tos Δ:" + var7 + "\n");
                return false;
            } else {
                return true;
            }
        }
    }
    
/**
 * Agrupa las producciones de acuerdo con los parámetros proporcionados.
 *
 * @param var1       El nombre de la producción.
 * @param var2       La expresión regular de la producción.
 * @param var3       Los componentes de la producción.
 * @param var4       Indica si se debe mostrar el mensaje de agrupación.
 * @param var5       El índice del componente interno.
 * @param var6       El mensaje de error.
 * @param var7       El índice del componente interno.
 * @param var8       Lista de producciones donde se agregará la producción agrupada.
 */
// Agrupación de producciones. En el contexto de un analizador sintáctico, la agrupación 
// de producciones puede ser crucial para definir la estructura gramatical de un lenguaje.
    public void group(String var1, String var2, int var3, String var4) {
        this.group(var1, var2, this.separarComp(var2), false, var3, var4, -1, (ArrayList)null);
    }

    public void group(String var1, String var2, int var3, String var4, int var5) {
        this.indexComponentInt = true;
        this.group(var1, var2, this.separarComp(var2), false, var3, var4, var5, (ArrayList)null);
        this.indexComponentInt = false;
    }

    public void group(String var1, String var2) {
        this.group(var1, var2, this.separarComp(var2), false, -1, (String)null, -1, (ArrayList)null);
    }

    public void group(String var1, String var2, String[] var3) {
        this.compIntManual = true;
        this.group(var1, var2, var3, false, -1, (String)null, -1, (ArrayList)null);
        this.compIntManual = false;
    }

    public void group(String var1, String var2, int var3, String var4, ArrayList<Production> var5) {
        this.group(var1, var2, this.separarComp(var2), false, var3, var4, -1, var5);
    }

    public void group(String var1, String var2, int var3, String var4, int var5, ArrayList<Production> var6) {
        this.indexComponentInt = true;
        this.group(var1, var2, this.separarComp(var2), false, var3, var4, var5, var6);
        this.indexComponentInt = false;
    }

    public void group(String var1, String var2, ArrayList<Production> var3) {
        this.group(var1, var2, this.separarComp(var2), false, -1, (String)null, -1, var3);
    }

    public void group(String var1, String var2, String[] var3, ArrayList<Production> var4) {
        this.compIntManual = true;
        this.group(var1, var2, var3, false, -1, (String)null, -1, var4);
        this.compIntManual = false;
    }

    public void group(String var1, String var2, boolean var3, int var4, String var5) {
        this.group(var1, var2, this.separarComp(var2), var3, var4, var5, -1, (ArrayList)null);
    }

    public void group(String var1, String var2, boolean var3, int var4, String var5, int var6) {
        this.indexComponentInt = true;
        this.group(var1, var2, this.separarComp(var2), var3, var4, var5, var6, (ArrayList)null);
        this.indexComponentInt = false;
    }

    public void group(String var1, String var2, boolean var3) {
        this.group(var1, var2, this.separarComp(var2), var3, -1, (String)null, -1, (ArrayList)null);
    }

    public void group(String var1, String var2, String[] var3, boolean var4) {
        this.compIntManual = true;
        this.group(var1, var2, var3, var4, -1, (String)null, -1, (ArrayList)null);
        this.compIntManual = false;
    }

    public void group(String var1, String var2, boolean var3, int var4, String var5, ArrayList<Production> var6) {
        this.group(var1, var2, this.separarComp(var2), var3, var4, var5, -1, var6);
    }

    public void group(String var1, String var2, boolean var3, int var4, String var5, int var6, ArrayList<Production> var7) {
        // Habilitar el componente interno de índice
        this.indexComponentInt = true;
        // Llamar al método principal "group" con los parámetros proporcionados
        this.group(var1, var2, this.separarComp(var2), var3, var4, var5, var6, var7);
        // Deshabilitar el componente interno de índice después de la llamada al método principal
        this.indexComponentInt = false;
    }

    public void group(String var1, String var2, boolean var3, ArrayList<Production> var4) {
        this.group(var1, var2, this.separarComp(var2), var3, -1, (String)null, -1, var4);
    }

    public void group(String var1, String var2, String[] var3, boolean var4, ArrayList<Production> var5) {
        this.compIntManual = true;
        this.group(var1, var2, var3, var4, -1, (String)null, -1, var5);
        this.compIntManual = false;
    }

    public void delete(String var1, int var2, String var3) {
        if (this.mostrarM) {
            System.out.println(".".repeat(100));
        }

        if (this.validateDeletion(var1)) {
            int var4 = 0;
            int var5 = this.producciones.size();

            for(int var6 = 0; var6 < this.producciones.size(); ++var6) {
                Production var7 = (Production)this.producciones.get(var6);
                if (var7.nameEqualTo(var1)) {
                    if (var3 != null) {
                        this.errors.add(new ErrorToken("Sintáctico",var2, var3, var7, this.lineaColumnaIni));
                    }

                    this.producciones.remove(var6);
                    --var6;
                    ++var4;
                }
            }

            if (this.mostrarM) {
                if (var4 > 0) {
                    System.out.println("**** Se realizaron " + var4 + " eliminaciones de producciones llamadas \"" + var1 + "\" ****\nLa cantidad de producciones se redujo de " + var5 + " a " + this.producciones.size() + "\n");
                } else {
                    System.out.println(Functions.ANSI_BLUE_BLACK + "No se encontró alguna producción llamada \"" + var1 + "\" para su eliminación\n");
                }
            }
        }

    }

// Este método delete elimina producciones con nombres específicos en masa. Toma como entrada un array de 
// cadenas var1, que contiene los nombres de las producciones que se eliminarán, un índice var2 y un mensaje var3.
/**
 * Elimina producciones con nombres específicos en masa.
 *
 * @param var1 Array de cadenas que contiene los nombres de las producciones a eliminar.
 * @param var2 Índice de algún componente.
 * @param var3 Mensaje asociado con la eliminación de las producciones.
 */
    public void delete(String[] var1, int var2, String var3) {
        String[] var4 = var1;
        int var5 = var1.length;
    // Itera sobre cada nombre de producción en el array var1
        for(int var6 = 0; var6 < var5; ++var6) {
            String var7 = var4[var6];
    // Elimina la producción con el nombre especificado, el índice y el mensaje proporcionados
            this.delete(var7, var2, var3);
        }

    }
/**
 * Elimina una producción con un nombre específico.
 *
 * @param var1 El nombre de la producción a eliminar.
 * @param var2 Índice asociado con la eliminación de la producción.
 */
    public void delete(String var1, int var2) {
        if (this.mostrarM) {
            System.out.println(".".repeat(100));
        }
    // Si se debe mostrar un mensaje de eliminación
        if (this.validateDeletion(var1)) {
            int var3 = 0;
            int var4 = this.producciones.size();
        // Iterar sobre las producciones
            for(int var5 = 0; var5 < this.producciones.size(); ++var5) {
                Production var6 = (Production)this.producciones.get(var5);
                // Si el nombre de la producción coincide
                if (var6.nameEqualTo(var1)) {
                // Agregar un mensaje de error a la lista de errores
                    if (var6.getSizeTokens() > 1) {
                        this.errors.add(new ErrorToken("Sintáctico",var2, " × Error sintáctico {}: No se esperaba encontrar los tokens \"[]\", favor de eliminarlos [#, %]", var6, this.lineaColumnaIni));
                    } else {
                        this.errors.add(new ErrorToken("Sintáctico",var2, " × Error sintáctico {}: No se esperaba encontrar el token \"[]\", favor de eliminarlo [#, %]", var6, this.lineaColumnaIni));
                    }
                // Eliminar la producción
                    this.producciones.remove(var5);
                    --var5;
                    ++var3;
                }
            }
        // Si se debe mostrar un mensaje de eliminación
            if (this.mostrarM) {
                if (var3 > 0) {
                // Mostrar la cantidad de producciones eliminadas y el nuevo tamaño de la lista de producciones
                    System.out.println("**** Se realizaron " + var3 + " eliminaciones de producciones llamadas \"" + var1 + "\" ****\nLa cantidad de producciones se redujo de " + var4 + " a " + this.producciones.size() + "\n");
                } else {
                // Mostrar un mensaje si no se encontró ninguna producción para eliminar
                    System.out.println(Functions.ANSI_BLUE_BLACK + "No se encontró alguna producción llamada \"" + var1 + "\" para su eliminación\n");
                }
            }
        }

    }
/**
 * Elimina producciones con nombres específicos en masa.
 *
 * @param var1 Array de cadenas que contiene los nombres de las producciones a eliminar.
 * @param var2 Índice asociado con la eliminación de las producciones.
 */
    public void delete(String[] var1, int var2) {
        String[] var3 = var1;
        int var4 = var1.length;
    // Iterar sobre cada nombre de producción en el array var1
        for(int var5 = 0; var5 < var4; ++var5) {
            String var6 = var3[var5];
        // Eliminar la producción con el nombre especificado y el índice proporcionados
            this.delete(var6, var2);
        }

    }
/**
 * Elimina una producción con un nombre específico.
 *
 * @param var1 El nombre de la producción a eliminar.
 */
    public void delete(String var1) {
    // Eliminar la producción con el nombre especificado y un índice predeterminado y sin mensaje
        this.delete((String)var1, -1, (String)null);
    }
/**
 * Elimina producciones con nombres específicos en masa.
 *
 * @param var1 Array de cadenas que contiene los nombres de las producciones a eliminar.
 */
    public void delete(String[] var1) {
            // Eliminar producciones con los nombres especificados en el array y un índice predeterminado y sin mensaje
        this.delete((String[])var1, -1, (String)null);
    }
/**
 * Valida si la eliminación de una producción es posible.
 *
 * @param var1 El nombre de la producción a eliminar.
 * @return true si la eliminación es posible, false de lo contrario.
 */
    private boolean validateDeletion(String var1) {
    // Si la validación está desactivada, se considera que la eliminación es posible
        if (!this.validacion) {
            return true;
        } else {
            int var2 = 0; // Contador para el número de errores encontrados
            String var3 = ""; // Mensaje que contiene los errores encontrados
              // Validar el nombre de la producción a eliminar
            if (var1 == null) {
                ++var2;
                var3 = var3 + "\n" + Functions.ANSI_RED_BLACK + "1. El nombre de la producción a eliminar es un valor nulo, proceda a corregirla";
            } else if (var1.contains(" ")) {
                ++var2;
                var3 = var3 + "\n" + Functions.ANSI_RED_BLACK + "1. El nombre de la producción a eliminar contiene uno o más espacios, proceda a corregirla:\n\"" + var1 + "\"";
            } else if (var1.equals("")) {
                ++var2;
                var3 = var3 + "\n" + Functions.ANSI_RED_BLACK + "1. El nombre de la producción a eliminar es una cadena vacía, proceda a corregirla";
            } else if (var1.matches("[0-9]+")) {
                ++var2;
                var3 = var3 + "\n" + Functions.ANSI_RED_BLACK + var2 + ". El nombre de la producción a eliminar no puede ser un número, proceda a corregirla:\n\"" + var1 + "\"";
            } else if (!var1.matches("[A-Za-zÑñÁÉÍÓÚáéíóúÜü_][0-9A-Za-zÑñÁÉÍÓÚáéíóúÜü_]*")) {
                ++var2;
                var3 = var3 + "\n" + Functions.ANSI_RED_BLACK + var2 + ". El nombre de la producción a eliminar es inválido, debe de cumplir con la siguiente ER: [A-Za-z_][A-Za-z0-9_]*, proceda a corregirla:\n\"" + var1 + "\"";
            }
        // Si se encontraron errores, mostrar los mensajes de error y retornar false
            if (var2 > 0) {
                System.out.println(Functions.ANSI_RED_BLACK + "**** Eliminación de la producción \"" + var1 + "\" no realizada****\n" + Functions.ANSI_RED_BLACK + "Δ Resuelva los errores a continuación descritos Δ:" + var3 + "\n");
                return false;
            } else {
        // Si no se encontraron errores, la eliminación es posible
                return true;
            }
        }
    }
/**
 * Muestra la representación textual del objeto en la consola.
 */
    public void show() {
        System.out.println(this);
    }
/**
 * Activa la visualización de mensajes durante la ejecución.
 */
    public void activateMessages() {
        this.mostrarM = true;
    }
/**
 * Desactiva la visualización de mensajes durante la ejecución.
 */
    public void disableMessages() {
        this.mostrarM = false;
    }
/**
 * Activa las validaciones durante la ejecución.
 */
    public void activateValidations() {
        this.validacion = true;
    }
/**
 * Desactiva las validaciones durante la ejecución.
 */
    public void disableValidations() {
        this.validacion = false;
    }
/**
 * Devuelve una representación textual de las gramáticas.
 *
 * @return Una cadena que contiene la representación textual de las gramáticas.
 */
    public String toString() {
        // Imprime un mensaje de título en la consola
        System.out.println("\n" + Functions.ANSI_PURPLE_BLACK + "**** Mostrando gramáticas ****\n");
        // Convierte las producciones en una cadena y las concatena

        String var1 = "";
        var1 = (String)this.producciones.stream().map((var0) -> {
                    // Agrega un separador y la representación textual de cada producción
            String var10000 = ".".repeat(100);
            return var10000 + "\n" + var0 + "\n\n";
        }).reduce(var1, String::concat);
            // Retorna la cadena resultante

        return var1;
    }
}
