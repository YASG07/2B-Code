import ply.lex as lex

tablaErrores = []
tablaSimbolos = {}

def reiniciarAnalizadorLex(lexer):
    global tablaErrores
    tablaErrores = []
    lexer.lineno = 1
    lexer.lexpos = 0

def obtenerErrores():
    global tablaErrores
    return tablaErrores

def obtenerColumna(input, token):
    last_cr = input.rfind('\n', 0, token.lexpos)
    if last_cr < 0:
        last_cr = 0
    columna = (token.lexpos - last_cr)
    if columna == 0:
        return 1
    return columna

def agregarError(indice, tipo, descripcion, valor, linea, columna):
    tablaErrores.append({
        'Error': indice,
        'Tipo': tipo,
        'Descripción': descripcion,
        'Valor': valor,
        'Linea': linea,
        'Columna': columna
    })




#lista de token validos para el lenguaje
tokens = [
    'if',
    'else',
    'scenario',
    'clear',
    'for',
    'in',
    'range',
    'Class',
    'func',
    'int',
    'float',
    'String',
    'char',
    'bool',
    'PLUS',
    'MINUS',
    'TIMES',
    'DIVIDE',
    'MOD',
    'LPARENT',
    'RPARENT',
    'LKEY',
    'RKEY',
    'SBLKEY',
    'SBRKEY',
    'COMMA',
    'TWPOINT',
    'DOT',
    'ASSIGN',
    'PLUSEQUAL',
    'MINUSEQUAL',
    'EQUALS',
    'NE',
    'LT',
    'GT',
    'LTE',
    'GTE',
    'AND',
    'NOT',
    'OR',
    'CADENA',
    'DECIMAL',
    'NUMBER',
    'BOOLEAN',
    'ID',
    'COMENTARIOS',
    'COMENTARIOS_MULTILINEA',
    'FIN_LINEA',
    'print',
    'read',
    'write',
]

#palabras reservadas
reservadas = {
    'if':'if',
    'else':'else',
    'scenario':'scenario',
    'clear':'clear',
    'for':'for',
    'in':'in',
    'range':'range',
    'Class':'Class',
    'func':'func',
    'print':'print',
    'int':'int',
    'float':'float',
    'String':'String',
    'char':'char',
    'bool':'bool',
    'read':'read',
    'write':'write',

}

#expresiones regulares para validar simbolos
t_PLUS = r'\+'
t_MINUS = r'\-'  
t_TIMES = r'\*'
t_DIVIDE = r'/'
t_MOD = r'%'
t_LT = r'<'
t_GT = r'>'
t_LPARENT = r'\('
t_RPARENT = r'\)'
t_LKEY = r'\{'
t_RKEY = r'\}'
t_SBLKEY = r'\['
t_SBRKEY = r'\['
t_COMMA = r','
t_TWPOINT =r':'
t_DOT = r'\.'
t_ASSIGN = r'='
t_PLUSEQUAL = r'\+\='
t_MINUSEQUAL = r'\-\='
t_EQUALS = r'\=\='
t_NE = r'!='
t_LTE = r'<='
t_GTE = r'>='
t_AND = r'&&'
t_NOT = r'!'
t_OR = r'\|\|'
t_CADENA = r'(\"([^\\\n]|(\\.))*?\"|\'([^\\\n]|(\\.))*?\')'

#ignora espacios en blanco
t_ignore = ' \t'

#aumenta el contador de lineas de código
def t_newline(token):
    r'\n+'
    token.lexer.lineno += len(token.value)

#funcion para validar decimales
def t_DECIMAL(token):
    r'\d+\.\d+'
    token.value = float(token.value)
    tablaSimbolos[token.value] = {
        'Tipo': 'DECIMAL',
        'Valor': token.value,
        'Línea': token.lineno,
        'Columna': obtenerColumna(token.lexer.lexdata, token)
    }
    return token

#funcion para validar enteros
def t_NUMBER(token):
    r'\d+'
    token.value = int(token.value)
    tablaSimbolos[token.value] = {
        'Tipo': 'ENTERO',
        'Valor': token.value,
        'Línea': token.lineno,
        'Columna': obtenerColumna(token.lexer.lexdata, token),
    }
    return token

#funcion para validar booleanos
def t_BOOLEAN(token):
    r'(true|false)'
    token.value = True if token.value.lower() == 'true' else False
    tablaSimbolos[token.value] = {
        'Tipo': 'BOOLEAN',
        'Valor': token.value,
        'Línea': token.lineno,
        'Columna': obtenerColumna(token.lexer.lexdata, token),
    }
    return token

#funcion para validar identificadores
def t_ID(token):
    r'[a-zA-Z][a-zA-Z0-9_]*'
    if token.value in reservadas:
        token.type = reservadas[token.value]
    else:
        if token.value in reservadas:
            token.type = reservadas[token.value]
        else:
            if token.value not in tablaSimbolos:
                tablaSimbolos[token.value] = {
                    'Tipo': 'Identificador',
                    'Valor': token.value,
                    'Línea': token.lineno,
                    'Columna': obtenerColumna(token.lexer.lexdata, token),
                }
    return token

#funcion para reconocer comentarios    
def t_COMENTARIOS(token):
    r'(//|\#).*'
    pass

#funcion para reconocer comentarios multilinea
def t_COMENTARIOS_MULTILINEA(token):
    r'<\-(?:[^-]|-(?!>))*\->'
    pass

def t_FIN_LINEA(token):
    r'\$'
    return token

#Manejo de errores lexicos
def t_error_ID(token):
    r'\d+[a-zA-Z][a-zA-Z0-9_]*'
    agregarError(1, 'Léxico', 'Identificador inválido', token.value, token.lineno, obtenerColumna(token.lexer.lexdata, token))
    token.lexer.skip(len(token.value))
    #identificador invalido

def t_error_NUMBER(token):
    r'[+-]{2,}\d+'
    agregarError(2, 'Léxico', 'Formato de número entero no válido', token.value, token.lineno, obtenerColumna(token.lexer.lexdata, token))
    token.lexer.skip(len(token.value))
    #formato invalido para numero entero

def t_error_DECIMAL(token):
    r'\d+([\.]{2,}\d+[\.|\d]*)+ | \d+\.\d+(\.+\d+)+ | \.+\d+(\.|\d)* | (\d?\.\.\d)+ | \d+\.(?!\d)'
    agregarError(3, 'Léxico', 'Formato de número decimal inválido', token.value, token.lineno, obtenerColumna(token.lexer.lexdata, token))
    token.lexer.skip(len(token.value))
    #formato de número decimal invalido

def t_error(token):
    agregarError(0, 'Léxico', 'Cáracter no reconocido', token.value, token.lineno, obtenerColumna(token.lexer.lexdata, token))
    token.lexer.skip(1)


#construye el analizador lexico posiblemente se tenga que comentar
analizador = lex.lex()
print(tablaErrores)