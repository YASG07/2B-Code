import ply.yacc as yacc
import ply.lex as lex
from lexico import tokens, obtenerErrores, agregarError

tablaErrores = obtenerErrores()
tablaSimbolos = {}

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

#gramatica para definir una clase
def p_clase(prod):
    '''
    clase : Class ID bloque
    '''
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
    agregarError(1, 'Sintactico', 'Clase mal definida ', prod[1], prod.lineno(1)+1, obtenerColumna(prod.lexer.lexdata, prod, 1))
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
    agregarError(2, 'Sintactico', 'Falta llave de apertura { ', prod[1], prod.lineno(1)+1, obtenerColumna(prod.lexer.lexdata, prod, 1))
    prod[0] = 'Error'
def p_errorbloque1(prod):
    '''
    bloque : LKEY instrucciones 
           | LKEY  
    '''
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
              | ciclowhile
              | si
              | funcion
              | imprimir
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
    if prod[2] not in tablaSimbolos:
        if len(prod) == 6:
            prod[0] = ('declaracion', prod[1], prod[2], prod[4])
            tablaSimbolos[prod[2]] = [prod[1], prod[4]]
        else:
            prod[0] = ('declaración', prod[1], prod[2])
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
    if prod[1] in tablaSimbolos:
        prod[0] = ('asignacion', prod[1], prod[3])
        tablaSimbolos[prod[1]].append(prod[3])
    else:
        agregarError(3, 'Semántico', 'Variable no declarada', prod[1], prod.lineno(1)+1,obtenerColumna(prod.lexer.lexdata, prod, 1))
#Gramtica para error de asignacion
def p_errorasignacion(prod):
    '''
    asignacion : ID ASSIGN expresion 
               | ID ASSIGN BOOLEAN 
               | ID ASSIGN CADENA 
    '''
    agregarError(5, 'Sintactico', 'Falta el $ en la asignacion', prod[1], prod.lineno(1)+1,obtenerColumna(prod.lexer.lexdata, prod, 1))
    prod[0] = 'Error'

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
              | valor   
    '''
    if len(prod) == 4:
        prod[0] = ('expresion', prod[1], prod[2], prod[3])
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

def p_aritmetico(prod):
    '''
    aritmetico : PLUS
               | MINUS
               | TIMES
               | DIVIDE 
               | MOD
    '''
    prod[0] = prod[1]

def p_operacionlogica(prod):
    '''
    operacionlogica : condicion AND condicion  
                    | condicion OR condicion  
                    | NOT condicion
                    | condicion  
    '''
    if len(prod) == 4:
        prod[0] = ('opLogica', prod[1], prod[2], prod[3])
    elif len(prod) == 3:
        prod[0] = ('opLogica', prod[1], prod[2])
    elif len(prod) == 2:
        prod[0] = ('opLogica', prod[1])

def p_condicion(prod):
    '''
    condicion : expresion comparacion expresion
    '''
    if prod[1] in tablaSimbolos:
        prod[0] = ('condicion', prod[1], prod[2], prod[3])
    else:
        agregarError(3, 'Semántico', 'Variable no declarada', prod[1], prod.lineno(2)+1, obtenerColumna(prod.lexer.lexdata, prod, 2))
#gramatica para error semantico del if
def p_errorcondicion(prod):
    '''
    condicion : expresion 
    '''
    if prod[1] not in tablaSimbolos:
         agregarError(3, 'Semántico', 'Variable no declarada', prod[1], prod.lineno(1)+1, obtenerColumna(prod.lexer.lexdata, prod, 1))
    else:
        agregarError(8, 'Semántico', 'La condición debe ser de tipo booleana o de comparación', prod[1], prod.lineno(1)+1, obtenerColumna(prod.lexer.lexdata, prod, 1))
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

def p_ciclofor(prod):
    '''
    ciclofor : for ID in range ID bloque
             | for ID in range NUMBER bloque
    '''
    prod[0] = ('cicloFor', prod[2], prod[5])
def p_errorciclofor(prod):
    '''
    ciclofor : for ID in range DECIMAL bloque
    '''
    agregarError(9, 'Semántico', 'La cantidad de repeticiones no es un valor numérico entero.', prod[5], prod.lineno(2), obtenerColumna(prod.lexer.lexdata, prod, 2))
    prod[0] = 'Error'
    
def p_ciclowhile(prod):
    '''
    ciclowhile : while LPARENT operacionlogica RPARENT bloque
               | while LPARENT BOOLEAN RPARENT bloque 
    '''
    prod[0] = ('cicloWhile', prod[3])

def p_si(prod):
    '''
    si : if LPARENT operacionlogica RPARENT bloque
       | if LPARENT operacionlogica RPARENT bloque else bloque 
    '''
    prod[0] = ('Si', prod[3])

def p_funcion(prod):
    '''
    funcion : func ID LPARENT RPARENT bloque
            | func ID LPARENT parametros RPARENT bloque
    '''
    prod[0] = ('funcion', prod[2])

def p_parametros(prod):
    '''
    parametros : parametros COMMA tipodato ID
               | tipodato ID 
    '''
    if len(prod) == 5:
        prod[0] = prod[1] + [prod[3], prod[4]]
    else:
        prod[0] = [prod[1], prod[2]]

def p_imprimir(prod):
    '''
    imprimir : print LPARENT CADENA RPARENT
               
    '''

#manejo de errores
def p_error(prod):
    if not prod:
        agregarError(0, 'Sintactico', 'Programa inválido', prod.value, prod, obtenerColumna(prod.lexer.lexdata, prod, 0))
    

parser = yacc.yacc()
yacc.errorlog = yacc.NullLogger()
src = '''
Class PolloFeliz {
 int a = 5$
 a = 0$

}
Class Pollo {
}
'''
print(parser.parse(src))
print(tablaErrores)