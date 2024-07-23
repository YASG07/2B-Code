import ply.yacc as yacc
import ply.lex as lex
import re
from lexico import tokens, obtenerErrores, agregarError

tablaErrores = obtenerErrores()
tablaSimbolos = {}
regexID = re.compile(r'[a-zA-Z][a-zA-Z0-9_]*')
regexBoolean = re.compile(r'(true|false)')

def obtenerErroresSintactico():
    global tablaErrores
    return tablaErrores

def reiniciarAnalizadorSintactico():
    global tablaErrores
    tablaErrores = []
    global tablaSimbolos
    tablaSimbolos = {}

def obtenerColumna(input, token, n):
    last_cr = input.rfind('\n', 0, token.lexpos(n))
    if last_cr < 0:
        last_cr = 0
    column = (token.lexpos(n) - last_cr)
    if column == 0:
        return 1 
    return column
def obtener_errores_sintactico():
    return tablaErrores
precedence = (
    ('right','ASSIGN'),
    ('left','NE'),
    ('left', 'LT', 'LTE', 'GT', 'GTE'),
    ('left', 'PLUS', 'MINUS'),
    ('left', 'TIMES', 'DIVIDE'),
    ('left', 'LPARENT', 'RPARENT'),
)

#gramatica para definir un programa
def p_programa(prod):
    '''
    programa : programa clase
             | clase
    '''
    #compara la longitud de los tokens que recibe siempre agregar uno de más
    if len(prod) == 3:
        prod[0] = prod[1] + [prod[2]]
    else:
        prod[0] = ['programa', prod[1]]
#Eroro variable mal declaradas

#gramatica para definir una clase
def p_clase(prod):
    '''
    clase : Class ID bloque
    '''
    #Yael
    #si identificador(ID) no esta en la tabla de simbolos (analisis semantico)
    if prod[2] not in tablaSimbolos:
        #construcción del ASA
        prod[0] = ('clase', prod[2], prod[3])
        tablaSimbolos[prod[2]] = 'Class'
    else:
        agregarError(1, 'Semántico', 'Clase ya definida', prod[2], prod.lineno(2)+1, obtenerColumna(prod.lexer.lexdata, prod, 2))
#Gramtica para error de clase doble indetificador o numero
def p_errorclase(prod):
    '''
    clase : Class ID ID bloque
          | Class ID NUMBER bloque
          | Class ID ID
          | Class ID NUMBER
          | Class ID DECIMAL bloque
          | Class ID DECIMAL
          | Class NUMBER bloque
          | Class NUMBER    
          | Class DECIMAL bloque
          | Class DECIMAL
    ''' 
    #Cesar
    agregarError(1, 'Sintactico', 'Clase mal definida ', prod[1], prod.lineno(1)+1, obtenerColumna(prod.lexer.lexdata, prod, 1))
    prod[0] = 'Error'
#Gramtica para error de clase doble indetificador o numero
def p_errorclase2(prod):
    '''
    clase :  declaracion Class ID bloque
          |  Class ID bloque declaracion
    ''' 
    #Cesar
    agregarError(10, 'Sintactico', 'Mal declaracion de variables ', prod[1], prod.lineno(1)+1, obtenerColumna(prod.lexer.lexdata, prod, 1))
    prod[0] = 'Error'                  
#gramatica para bloque de código
def p_bloque(prod):
    '''
    bloque : LKEY instrucciones RKEY
           | LKEY RKEY 
    '''
    prod[0] = ('bloque', prod[2])
#gramatica de errro de bloque de codigo    
def p_errorbloque1(prod):
    '''
    bloque :  instrucciones RKEY
           |  RKEY 
    '''
    #Cesar
    agregarError(2, 'Sintactico', 'Falta llave de apertura { ', prod[1], prod.lineno(1)+1, obtenerColumna(prod.lexer.lexdata, prod, 1))
    prod[0] = 'Error'
def p_errorbloque2(prod):
    '''
    bloque : LKEY instrucciones 
           | LKEY  
    '''
    #Cesar
    agregarError(3, 'Sintactico', 'Falta llave de cierre } ', prod[1], prod.lineno(1)+1, obtenerColumna(prod.lexer.lexdata, prod, 1))
    prod[0] = 'Error'

#lista de instrucciones (1 o más)
def p_instrucciones(prod):
    '''
    instrucciones : instrucciones auxbloque
                  | auxbloque  
    '''
    if len(prod) == 3:
        prod[0] = prod[1] + [prod[2]]
    else:
        prod[0] = [prod[1]]

#auxiliar para bloque de código
def p_auxbloque(prod):
    '''
    auxbloque : declaracion
              | asignacion
              | ciclofor
              | escenario
              | si
              | funcion
              | imprimir
              | escribir
              | leer
    '''
    prod[0] = prod[1]

#declaracion de variables
def p_declaracion(prod):
    '''
    declaracion : tipodato ID ASSIGN expresion FIN_LINEA
                | tipodato ID ASSIGN BOOLEAN FIN_LINEA
                | tipodato ID ASSIGN CADENA FIN_LINEA
                | tipodato ID FIN_LINEA
    '''
    #Yael
    if prod[2] not in tablaSimbolos:
        if len(prod) == 6:
            if regexID.match(str(prod[4])) and not regexBoolean.match(str(prod[4])):
                if prod[4] not in tablaSimbolos:
                    agregarError(3, 'Semántico', 'Variable no declarada', prod[4], prod.lineno(4),obtenerColumna(prod.lexer.lexdata, prod, 4))
                else:
                    if tablaSimbolos[prod[4]][0] != prod[1]:
                        agregarError(4, 'Semántico', f'{prod[4]} no puede ser convertido a "{prod[1]}"', prod[4], prod.lineno(4)+1, obtenerColumna(prod.lexer.lexdata, prod, 4))
                    else:
                        if len(tablaSimbolos[prod[4]]) == 2:
                            prod[0] = ('declaracion', prod[1], prod[2], tablaSimbolos[prod[4]][1])
                            tablaSimbolos[prod[2]] = [prod[1], tablaSimbolos[prod[4]][1]]
                        else:
                            agregarError(9, 'Semántico', 'Valor no definido en la variable', prod[4], prod.lineno(4)+1,obtenerColumna(prod.lexer.lexdata, prod, 4))
            else:
                if prod[1] == type(prod[4]).__name__ or (prod[1] == 'String' and type(prod[4]) == str):
                    prod[0] = ('declaracion', prod[1], prod[2], prod[4])
                    tablaSimbolos[prod[2]] = [prod[1], prod[4]]
                else:
                    agregarError(4, 'Semántico', f'{prod[4]} no puede ser convertido a "{prod[1]}"', prod[4], prod.lineno(4), obtenerColumna(prod.lexer.lexdata, prod, 4))
        else:
            prod[0] = ('declaracion', prod[1], prod[2])
            tablaSimbolos[prod[2]] = [prod[1]]
    else:
        agregarError(2, 'Semántico', 'Variable ya definida', prod[2], prod.lineno(2)+1, obtenerColumna(prod.lexer.lexdata, prod, 2))

#Gramatica para error de declaracion
def p_errordeclaracion(prod):
    '''
    declaracion : tipodato ID ASSIGN expresion 
                | tipodato ID ASSIGN BOOLEAN
                | tipodato ID ASSIGN CADENA 
                | tipodato ID 
    '''
    agregarError(4, 'Sintactico', 'Falta el $ en la declaracion', prod[2], prod.lineno(2)+1, obtenerColumna(prod.lexer.lexdata, prod, 2))
    prod[0] = 'Error'
#asignación de valores
def p_asignacion(prod):
    '''
    asignacion : ID ASSIGN expresion FIN_LINEA
               | ID ASSIGN BOOLEAN FIN_LINEA
               | ID ASSIGN CADENA FIN_LINEA
    '''
    #Yael
    if prod[1] in tablaSimbolos:
        if regexID.match(str(prod[3])) and not regexBoolean.match(str(prod[3])):
            if prod[3] not in tablaSimbolos:
                agregarError(3, 'Semántico', 'Variable no declarada', prod[3], prod.lineno(3),obtenerColumna(prod.lexer.lexdata, prod, 3))
            else:
                if tablaSimbolos[prod[3]][0] != tablaSimbolos[prod[1]][0]:
                    agregarError(4, 'Semántico', f'{prod[3]} no puede ser convertido a "{tablaSimbolos[prod[1]][0]}"', prod[3], prod.lineno(3)+1, obtenerColumna(prod.lexer.lexdata, prod, 3))
                else:
                    if len(tablaSimbolos[prod[3]]) == 2: 
                        prod[0] = ('asignacion', prod[1], tablaSimbolos[prod[3]][1])
                        tablaSimbolos[prod[3]][1] = (tablaSimbolos[prod[3]][1])
                    else:
                        agregarError(9, 'Semántico', 'Valor no definido en la variable', prod[3], prod.lineno(3)+1,obtenerColumna(prod.lexer.lexdata, prod, 3))
        else:
            if tablaSimbolos[prod[1]][0] == type(prod[3]).__name__ or (tablaSimbolos[prod[1]][0] == 'String' and type(prod[3]) == str):
                prod[0] = ('asignacion', prod[1], prod[3])
                if len(tablaSimbolos[prod[1]]) == 2:
                    tablaSimbolos[prod[1]][1] = prod[3]
                else:
                    tablaSimbolos[prod[1]].append(prod[3])
            else:
                agregarError(4, 'Semántico', f'{prod[3]} no puede ser convertido a "{tablaSimbolos[prod[1]][0]}"', prod[3], prod.lineno(3)+1, obtenerColumna(prod.lexer.lexdata, prod, 3))

    else:
        agregarError(3, 'Semántico', 'Variable no declarada', prod[1], prod.lineno(1)+1,obtenerColumna(prod.lexer.lexdata, prod, 1))
#Gramtica para error de asignacion
def p_errorasignacion(prod):
    '''
    asignacion : ID ASSIGN expresion 
               | ID ASSIGN BOOLEAN 
               | ID ASSIGN CADENA 
    '''
    #Cesar
    agregarError(5, 'Sintactico', 'Falta el $ en la asignacion', prod[1], prod.lineno(1)+1,obtenerColumna(prod.lexer.lexdata, prod, 1))
    prod[0] = 'Error'
def p_escribir(prod):
    '''
    escribir : write LPARENT CADENA RPARENT FIN_LINEA
             | write LPARENT ID RPARENT FIN_LINEA
    '''
    prod[0]=('escribir',prod[1],prod[3])
def p_leer(prod):
    '''
    leer : read TWPOINT ASSIGN ID FIN_LINEA
    '''
    prod[0]=('leer',prod[1],prod[4])
 
#auxiliar para declaración (tipos de dato aceptados)
def p_tipodato(prod):
    '''
    tipodato : int
             | String
             | float
             | char
             | bool
    '''
    prod[0] = prod[1]

#expresion (operaciones aritmeticas)    
def p_expresion(prod):
    '''
    expresion : expresion aritmetico valor
              | valor aritmetico valor
              | valor   
    '''
    if len(prod) == 4:
        operador = prod[2]
        valor1 = prod[1]
        valor2 = prod[3]
        
        # Verifica que las variables tengan un valor asociado 
        if valor1 in tablaSimbolos:
            if len(tablaSimbolos[valor1]) == 2:
                valor1 = tablaSimbolos[valor1][1]
            else:
                agregarError(9, 'Semántico', 'Valor no definido en la variable', prod[1], prod.lineno(1)+1,obtenerColumna(prod.lexer.lexdata, prod, 1))
        elif regexID.match(str(valor1)) and not regexBoolean.match(str(valor1)):
            agregarError(3, 'Semántico', 'Variable no declarada', prod[1], prod.lineno(1)+1,obtenerColumna(prod.lexer.lexdata, prod, 1))
        if valor2 in tablaSimbolos:
            if len(tablaSimbolos[valor2]) == 2:
                valor2 = tablaSimbolos[valor2][1]
            else:
                agregarError(9, 'Semántico', 'Valor no definido en la variable', prod[3], prod.lineno(3)+1,obtenerColumna(prod.lexer.lexdata, prod, 3))
        elif regexID.match(str(valor2)) and not regexBoolean.match(str(valor2)):
            agregarError(3, 'Semántico', 'Variable no declarada', prod[3], prod.lineno(3)+1,obtenerColumna(prod.lexer.lexdata, prod, 3))

        # Verifica que los valores sean numéricos
        try:
            valor1 = float(valor1)
        except ValueError:
            agregarError(10, 'Semántico', 'Valor no numérico en la variable', valor1, prod.lineno(1)+1, obtenerColumna(prod.lexer.lexdata, prod, 1))
            return
        try:
            valor2 = float(valor2)
        except ValueError:
            agregarError(10, 'Semántico', 'Valor no numérico en la variable', valor2, prod.lineno(3)+1, obtenerColumna(prod.lexer.lexdata, prod, 3))
            return
        # Solo verifica la división por cero
        if operador == '/':
            if valor2 != 0:
                prod[0] = valor1 / valor2
                if prod[0].is_integer():
                    prod[0] = int(prod[0])
            else:
                agregarError(5, 'Semántico', 'División por cero', valor2, prod.lineno(3)+1, obtenerColumna(prod.lexer.lexdata, prod, 3))
        else:
            # Verifica que el operador sea válido y realiza la operación correspondiente
            if operador in ['+', '-', '*', '/']:
                if operador == '+':
                    prod[0] = valor1 + valor2
                    prod[1] = valor1 
                    prod[2] = '+'
                    prod[3] = valor2
                elif operador == '-':
                    prod[0] = valor1 - valor2
                elif operador == '*':
                    prod[0] = valor1 * valor2
                # La división ya fue manejada
                 # Convertir a entero si el resultado es un entero
                if prod[0].is_integer():
                    prod[0] = int(prod[0])
            else:
                agregarError(10, 'Semántico', 'Operador aritmético no válido', operador, prod.lineno(2)+1, obtenerColumna(prod.lexer.lexdata, prod, 2))
    else:
        prod[0] = prod[1]


def p_valor(prod):
    '''
    valor : ID
          | NUMBER
          | DECIMAL
          | LPARENT expresion RPARENT
    '''
    if len(prod) == 4:
        prod[0] = prod[2]
    else:
        prod[0] = prod[1]
        # Verificación de si el valor es un número
    

def p_aritmetico(prod):
    '''
    aritmetico : PLUS
               | MINUS
               | TIMES
               | DIVIDE 
               | MOD
    '''
    prod[0] = prod[1]


#producción para
def p_operacionlogica(prod):
    '''
    operacionlogica : condicion AND condicion  
                    | condicion OR condicion  
                    | NOT condicion
                    | condicion  
    '''
    if len(prod) == 4:
        prod[0] = (prod[1], prod[2], prod[3])
    elif len(prod) == 3:
        prod[0] = (prod[1], prod[2])
    elif len(prod) == 2:
        prod[0] = (prod[1])

def p_condicion(prod):
    '''
    condicion : expresion comparacion expresion
    '''
    #Cesar
    if prod[1] in tablaSimbolos:
        if prod[3] in tablaSimbolos:
            tipo1 = tablaSimbolos[prod[1]]
            tipo2 = tablaSimbolos[prod[3]]
            var1=tipo1[0]
            var2=tipo2[0]
            #print(var1, var2)
            #Cesar
            # Verificar si los tipos de datos son compatibles
            #PARA DATOS int == String
            if var1 == "String" and var2 == "int":
                agregarError(6, 'Semántico', 'Datos incompatibles',prod[1], prod.lineno(1)+1, obtenerColumna(prod.lexer.lexdata, prod, 1))
            #PARA DATOS STRING == INT
            if var1 == "int" and var2 == "String":
                agregarError(6, 'Semántico', 'Datos incompatibles',prod[3], prod.lineno(3)+1, obtenerColumna(prod.lexer.lexdata, prod, 3))
            #PARA DATOS float == String
            if var1 == "float" and var2 == "String":
                agregarError(6, 'Semántico', 'Datos incompatibles',prod[3], prod.lineno(3)+1, obtenerColumna(prod.lexer.lexdata, prod, 3))
            #PARA DATOS String == float
            if var1 == "String" and var2 == "float":
                agregarError(6, 'Semántico', 'Datos incompatibles',prod[1], prod.lineno(1)+1, obtenerColumna(prod.lexer.lexdata, prod, 1))
            if var1 != 'String' and var2 != 'String':
                if len(tipo1) == 2 and len(tipo2) == 2:
                    prod[1] = tipo1[1]
                    prod[3] = tipo2[1]
                    prod[0] = ('condicion', prod[1], prod[2], prod[3])
                else:
                    agregarError(9, 'Semántico', 'Valor no definido en la variable', prod[3], prod.lineno(3)+1,obtenerColumna(prod.lexer.lexdata, prod, 3))

        else: 
            if regexID.match(str(prod[3])) and not regexBoolean.match(str(prod[3])):
                agregarError(3, 'Semántico', 'Variable no declarada', prod[3], prod.lineno(3)+1, obtenerColumna(prod.lexer.lexdata, prod, 3))
            else:
                if tablaSimbolos[prod[1]][0] != 'String':
                    try:
                       prod[1] = tablaSimbolos[prod[1]][1] 
                    except IndexError:
                        agregarError(9, 'Semántico', 'Valor no definido en la variable', prod[1], prod.lineno(1)+1,obtenerColumna(prod.lexer.lexdata, prod, 1))
                        return
                    prod[0] = ('condicion', prod[1], prod[2], prod[3])
                else:
                   agregarError(6, 'Semántico', 'Datos incompatibles',prod[1], prod.lineno(1)+1, obtenerColumna(prod.lexer.lexdata, prod, 1)) 
    else:
        if regexID.match(str(prod[1])) and not regexBoolean.match(str(prod[1])):
            agregarError(3, 'Semántico', 'Variable no declarada', prod[1], prod.lineno(1)+1, obtenerColumna(prod.lexer.lexdata, prod, 1))
        else:
            if prod[3] in tablaSimbolos and tablaSimbolos[prod[3]][0] == 'String':
                agregarError(6, 'Semántico', 'Datos incompatibles',prod[3], prod.lineno(3)+1, obtenerColumna(prod.lexer.lexdata, prod, 3))
            elif prod[3] not in tablaSimbolos:
                if regexID.match(str(prod[3])) and not regexBoolean.match(str(prod[3])):
                    agregarError(3, 'Semántico', 'Variable no declarada', prod[3], prod.lineno(3)+1, obtenerColumna(prod.lexer.lexdata, prod, 3))
                else:
                    prod[0] = ('condicion', prod[1], prod[2], prod[3])
            elif tablaSimbolos[prod[3]][0] != 'String':
                try:
                    prod[3] = tablaSimbolos[prod[3]][1]
                except IndexError:
                    agregarError(9, 'Semántico', 'Valor no definido en la variable', prod[3], prod.lineno(3)+1,obtenerColumna(prod.lexer.lexdata, prod, 3))
                    return
                prod[0] = ('condicion', prod[1], prod[2], prod[3])

#gramatica para error semantico del if
def p_errorcondicion(prod):
    '''
    condicion : expresion 
    '''
    #Cesar
    if prod[1] not in tablaSimbolos:    
        agregarError(3, 'Semántico', 'Variable no declarada', prod[1], prod.lineno(1)+1, obtenerColumna(prod.lexer.lexdata, prod, 1))
    else:
        agregarError(7, 'Semántico', 'La condición debe ser de comparación', prod[1], prod.lineno(1)+1, obtenerColumna(prod.lexer.lexdata, prod, 1))
        prod[0] = 'Error'
def p_comparacion(prod):
    '''
    comparacion : LT
                | GT
                | LTE
                | GTE
                | NE
                | EQUALS
    '''
    prod[0] = prod[1]
      
#producción para ciclo for
def p_ciclofor(prod):
    '''
    ciclofor : for in range ID bloque
             | for in range NUMBER bloque
    '''
    if regexID.match(str(prod[4])) and not regexBoolean.match(str(prod[4])):
        if prod[4] not in tablaSimbolos:
            agregarError(3, 'Semántico', 'Variable no declarada', prod[4], prod.lineno(4)+1,obtenerColumna(prod.lexer.lexdata, prod, 4))
        else:
            if tablaSimbolos[prod[4]][0] != 'int':
                agregarError(8, 'Semántico', 'La cantidad de repeticiones no es un valor numérico entero.', prod[4], prod.lineno(4)+1, obtenerColumna(prod.lexer.lexdata, prod, 4))
            else:
                if len(tablaSimbolos[prod[4]]) == 2:
                    prod[0] = ('cicloFor', tablaSimbolos[prod[4]][1], prod[5])
                else:
                    agregarError(9, 'Semántico', 'Valor no definido en la variable', prod[4], prod.lineno(4)+1,obtenerColumna(prod.lexer.lexdata, prod, 4))
    else:        
        prod[0] = ('cicloFor', prod[4], prod[5])
        
def p_errorciclofor(prod):
    '''
    ciclofor : for in range error bloque
    '''
    #Cesar
    agregarError(8, 'Semántico', 'La cantidad de repeticiones no es un valor numérico entero.', prod[5], prod.lineno(2)+1, obtenerColumna(prod.lexer.lexdata, prod, 2))
    prod[0] = 'Error'
    
def p_escenario(prod):
    '''
    escenario : scenario LPARENT RPARENT auxscenario
    '''
    prod[0] = ('escenario', prod[4])

def p_completar(prod):
    '''
    completar : clear LPARENT condicion RPARENT FIN_LINEA
    '''
    prod[0] = ('completar', prod[3])

def p_auxscenario(prod):
    '''
    auxscenario : LKEY instrucciones completar RKEY
                | LKEY completar RKEY 
    '''
    if len(prod) == 5:
        prod[0] = ('bloque', prod[2], prod[3])
    else:
        prod[0] = prod[2]

def p_si(prod):
    '''
    si : if LPARENT operacionlogica RPARENT bloque
       | if LPARENT BOOLEAN RPARENT bloque
       | si else bloque
    '''
    if len(prod) == 6:
        prod[0] = ('Si', prod[3], prod[5])
    else:
        prod[0] = ('SiNo', prod[1], prod[3])

def p_funcion(prod):
    '''
    funcion : func ID LPARENT RPARENT bloque
            | func ID LPARENT parametros RPARENT bloque
    '''
    if len(prod) == 6:
        prod[0] = ('funcion', prod[2], prod[5])
    else:
        prod[0] = ('funcionParametrizada', prod[2], prod[4], prod[6])

def p_parametros(prod):
    '''
    parametros : parametros COMMA tipodato ID
               | tipodato ID 
    '''
    if len(prod) == 5:
        prod[0] = prod[1] + [prod[3], prod[4]]
    else:
        prod[0] = [prod[1], prod[2]]

#producción para imprimir una cadena
def p_imprimir(prod):
    '''
    imprimir : print LPARENT CADENA RPARENT FIN_LINEA
             | print LPARENT ID RPARENT FIN_LINEA
    '''
    if regexID.match(str(prod[3])) and not regexBoolean.match(str(prod[3])):
        if prod[3] not in tablaSimbolos:
            agregarError(3, 'Semántico', 'Variable no declarada', prod[3], prod.lineno(3)+1,obtenerColumna(prod.lexer.lexdata, prod, 3))
        else:
            if tablaSimbolos[prod[3]][0] != 'String':
                agregarError(11, 'Semántico', 'El valor a imprimir no es una cadena', prod[3], prod.lineno(3)+1, obtenerColumna(prod.lexer.lexdata, prod, 3))
            else:
                if len(tablaSimbolos[prod[3]]) == 2:
                    prod[0] = ('print', tablaSimbolos[prod[3]][1])
                else:
                    agregarError(9, 'Semántico', 'Valor no definido en la variable', prod[3], prod.lineno(3)+1,obtenerColumna(prod.lexer.lexdata, prod, 3))
    else:
        prod[0] = ('print',prod[3])
def p_errorimprimir(prod):
    '''
    imprimir : print LPARENT error RPARENT FIN_LINEA
    '''
    agregarError(11, 'Semántico', 'El valor a imprimir no es una cadena', prod[3], prod.lineno(3)+1, obtenerColumna(prod.lexer.lexdata, prod, 3))
    prod[0] = 'Error'

#manejo de errores. PD: agreguen todas las gramaticas de error en esta area

#errorres al definir un programa
def p_errorprograma1(prod):
    '''
    programa : error clase
    '''
    agregarError(0, 'Sintactico', 'El código debe ir dentro de una clase', prod[1], prod.lineno(1)+1, obtenerColumna(prod.lexer.lexdata, prod, 1))
def p_errorprograma2(prod):
    '''
    programa : clase error
    '''
    agregarError(0, 'Sintactico', 'El código debe ir dentro de una clase', prod[2], prod.lineno(2)+1, obtenerColumna(prod.lexer.lexdata, prod, 2))

#errores al definir una clase
def p_errorclase3(prod):
    '''
    clase : error ID bloque
    '''
    agregarError(1, 'Sintactico', 'Clase mal definida', prod[1], prod.lineno(1)+1, obtenerColumna(prod.lexer.lexdata, prod, 1))
def p_errorclase4(prod):
    '''
    clase : Class error bloque
    '''
    agregarError(1, 'Sintactico', 'Clase mal definida', prod[2], prod.lineno(2)+1, obtenerColumna(prod.lexer.lexdata, prod, 2))

#errores al construir un bloque
def p_errorbloque3(prod):
    '''
    bloque : error instrucciones RKEY
           | error RKEY 
    '''
    agregarError(2.1, 'Sintactico', 'Llave de apertura "{" no detectada', prod[1], prod.lineno(2)+1, obtenerColumna(prod.lexer.lexdata, prod, 1))
def p_errorbloque4(prod):
    '''
    bloque : LKEY instrucciones error
           | LKEY error 
    '''
    if len(prod) == 4:
        agregarError(3.1, 'Sintactico', 'Llave de cierre "}" no detectada', prod[3].value, prod.lineno(3)+1, obtenerColumna(prod.lexer.lexdata, prod, 3))
    else:
        agregarError(3.1, 'Sintactico', 'Llave de cierre "}" no detectada', prod[2].value, prod.lineno(2)+1, obtenerColumna(prod.lexer.lexdata, prod, 2))

#errores al listar instrucciones
def p_errorinstrucciones1(prod):
    '''
    instrucciones : error auxbloque
    '''
    agregarError(6, 'Sintactico', 'Instrucción inválida', prod[1], prod.lineno(1)+1, obtenerColumna(prod.lexer.lexdata, prod, 1))
def p_errorinstrucciones2(prod):
    '''
    instrucciones : auxbloque error
    '''
    agregarError(6, 'Sintactico', 'Instrucción inválida', prod[2], prod.lineno(2)+1, obtenerColumna(prod.lexer.lexdata, prod, 2))

#errores al declarar variables
def p_errordeclaracion2(prod):
    '''
    declaracion : error ID ASSIGN expresion FIN_LINEA
                | error ID ASSIGN BOOLEAN FIN_LINEA
                | error ID ASSIGN CADENA FIN_LINEA
                | error ID FIN_LINEA
    '''
    agregarError(7, 'Sintactico', 'Tipo de dato no válido', prod[1].value, prod.lineno(1), obtenerColumna(prod.lexer.lexdata, prod, 1))
def p_errordeclaracion3(prod):
    '''
    declaracion : tipodato error ASSIGN expresion FIN_LINEA
                | tipodato error ASSIGN BOOLEAN FIN_LINEA
                | tipodato error ASSIGN CADENA FIN_LINEA
                | tipodato error FIN_LINEA
    '''
    agregarError(8, 'Sintactico', 'Nombre de variable no válido', prod[2].value, prod.lineno(2)+1, obtenerColumna(prod.lexer.lexdata, prod, 2))
def p_errordeclaracion4(prod):
    '''
    declaracion : tipodato ID expresion FIN_LINEA
                | tipodato ID BOOLEAN FIN_LINEA
                | tipodato ID CADENA FIN_LINEA
    '''
    agregarError(9, 'Sintactico', 'Falta el símbolo "=" en la declaración', prod[3], prod.lineno(3)+1, obtenerColumna(prod.lexer.lexdata, prod, 3))
def p_errordeclaracion5(prod):
    '''
    declaracion : tipodato ID error expresion FIN_LINEA
                | tipodato ID error BOOLEAN FIN_LINEA
                | tipodato ID error CADENA FIN_LINEA
    '''
    agregarError(9.1, 'Sintactico', 'Símbolo "=" no detectado', prod[3], prod.lineno(3)+1, obtenerColumna(prod.lexer.lexdata, prod, 3))


#error programa no válido
def p_error(prod):
    if not prod:
        agregarError(0, 'Sintactico', 'Programa inválido','','','')
    

parser = yacc.yacc()