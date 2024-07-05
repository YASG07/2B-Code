import ply.yacc as yacc
import ply.lex as lex
from lexico import tokens, obtenerErrores, obtenerColumna, agregarError

tablaErrores = obtenerErrores()
tablaSimbolos = {}

def reiniciarAnalizadorSintactico():
    global tablaErrores
    tablaErrores = []

def p_programa(prod):
    '''
    programa : programa clase
             | clase
    '''

def p_clase(prod):
    '''
    clase : Class ID bloque
    '''

def p_bloque(prod):
    '''
    bloque : LKEY instrucciones RKEY
    '''

def p_instrucciones(prod):
    '''
    instrucciones : instrucciones auxBloque
                  | auxBloque  
    '''

def p_auxiliarBloque(prod):
    '''
    auxBloque : declaracion
              | asignacion
              | cicloFor
              | cicloWhile
              | si
              | funcion
    '''

def p_declaracion(prod):
    '''
    declaracion : tipoDato ID ASSIGN expresion FIN_LINEA
                | tipoDato ID ASSIGN BOOLEAN FIN_LINEA
                | tipoDato ID ASSIGN CADENA FIN_LINEA
                | tipoDato ID FIN_LINEA
    '''

def p_asignacion(prod):
    '''
    asignacion : ID ASSIGN expresion FIN_LINEA
               | ID ASSIGN BOOLEAN FIN_LINEA
               | ID ASSIGN CADENA FIN_LINEA
    '''

def p_tipoDato(prod):
    '''
    tipoDato : int
             | String
             | float
             | char
             | bool
    '''
    prod[0] = prod[1]
    
def p_expresion(prod):
    '''
    expresion : expresion aritmetico valor
              | valor   
    '''

def p_valor(prod):
    '''
    valor : ID
          | NUMBER
          | DECIMAL
          | LPARENT expresion RPARENT
    '''
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

def p_operacionLogica(prod):
    '''
    operacionLogica : condicion AND condicion  
                    | condicion OR condicion  
                    | NOT condicion
                    | condicion  
    '''

def p_condicion(prod):
    '''
    condicion : expresion comparacion expresion
    '''

def p_comparacion(prod):
    '''
    comparacion : LT
                | GT
                | LTE
                | GTE
                | NE
                | EQUALS
    '''

def p_cicloFor(prod):
    '''
    cicloFor : for ID in range ID bloque
             | for ID in range NUMBER bloque
    '''

def p_cicloWhile(prod):
    '''
    cicloWhile : while LPARENT operacionLogica RPARENT bloque
               | while LPARENT BOOLEAN RPARENT bloque 
    '''

def p_if(prod):
    '''
    si : if LPARENT operacionLogica RPARENT bloque
       | if LPARENT operacionLogica RPARENT bloque else bloque 
    '''

def p_funcion(prod):
    '''
    funcion : func ID LPARENT RPARENT bloque
            | func ID LPARENT parametros RPARENT bloque
    '''

def p_parametros(prod):
    '''
    parametros : parametros COMMA tipoDato ID
               | tipoDato ID 
    '''

analizador = yacc.yacc()