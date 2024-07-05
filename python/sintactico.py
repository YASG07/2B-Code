import ply.yacc as yacc
import ply.lex as lex
from lexico import tokens, obtenerErrores, agregarError

tablaErrores = obtenerErrores()
tablaSimbolos = {}

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
        prod[0] = ('clase', prod[2])
        tablaSimbolos[prod[2]] = 'Class'
    else:
        agregarError(1, 'Semántico', 'Clase ya definida', prod[2].value, prod.lineno(2), obtenerColumna(prod.lexer.lexdata, prod, 2))
        
#gramatica para bloque de código
def p_bloque(prod):
    '''
    bloque : LKEY instrucciones RKEY
    '''
    prod[0] = ('bloque', prod[2])

#lista de instrucciones (1 o más)
def p_instrucciones(prod):
    '''
    instrucciones : instrucciones auxBloque
                  | auxBloque  
    '''
    if len(prod) == 3:
        prod[0] = prod[1] + [prod[2]]
    else:
        prod[0] = [prod[1]]

#auxiliar para bloque de código
def p_auxiliarBloque(prod):
    '''
    auxBloque : declaracion
              | asignacion
              | cicloFor
              | cicloWhile
              | si
              | funcion
              | imprimir
    '''
    prod[0] = prod[1]

#declaracion de variables
def p_declaracion(prod):
    '''
    declaracion : tipoDato ID ASSIGN expresion
                | tipoDato ID ASSIGN BOOLEAN 
                | tipoDato ID ASSIGN CADENA 
                | tipoDato ID 
    '''
    if prod[2] not in tablaSimbolos:
        if len(prod) == 6:
            prod[0] = ('declaracion', prod[1], prod[2], prod[4])
            tablaSimbolos[prod[2]] = [prod[1], prod[4]]
        else:
            prod[0] = ('declaración', prod[1], prod[2])
            tablaSimbolos[prod[2]] = [prod[1]]
    else:
        agregarError(2, 'Semántico', 'Variable ya definida', prod[2].value, prod.lineno(2), obtenerColumna(prod.lexer.lexdata, prod, 2))

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
        agregarError(3, 'Semántico', 'Variable no declarada', prod[1].value, prod.lineno(1), obtenerColumna(prod.lexer.lexdata, prod, 1))

#auxiliar para declaración (tipos de dato aceptados)
def p_tipoDato(prod):
    '''
    tipoDato : int
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
def p_imprimir(prod):
    '''
    imprimir : print LPARENT CADENA RPARENT
               
    '''

#manejo de errores
def p_error(prod):
    if not prod:
        agregarError(0, 'Sintactico', 'Programa inválido', prod.value, prod.lineno, obtenerColumna(prod.lexer.lexdata, prod, 0))

yacc.errorlog = yacc.NullLogger()        

parse = yacc.yacc()
print(tablaErrores)

