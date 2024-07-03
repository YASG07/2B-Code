import ply.lex as lex

tablaErrores = []
tablaSimbolos = {}

def reiniciarAnalizadorLex(lexer):
    global tablaErrores
    tablaErrores = []
    lexer.lineno = 1
    lexer.lexpos = 0

def obtenerErroresLexicos():
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

def agregarErrorLex(indice, tipo, descripcion, valor, linea, columna):
    tablaErrores.append({
        'Indice': indice,
        'Tipo': tipo,
        'Descripción': descripcion,
        'Valor': valor,
        'Línea': linea,
        'Columna': columna
    })

tokens = [
    'if',
    'else',
    'while',
    'for',
    'in',
    'range',
    'Class',
    'int',
    'float',
    'String',
    'char',
    'PLUS',
    'MINUS',
    'TIMES',
    'DIVIDE',
    'LT',
    'GT',
    'LPARENT',
    'RPARENT',
    'LKEY',
    'RKEY',
    'SBLKEY',
    'SBRKEY',
    'COMMA',
    'DOT',
    'ASSIGN',
    'PLUSEQUAL',
    'MINUSEQUAL',
    'EQUALS',
    'NE',
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
]

reservadas = {
    'if':'if',
    'else':'else',
    'for':'for',
    'in':'in',
    'range':'range',
    'Class':'Class',
    'int':'int',
    'float':'float',
    'String':'String',
    'char':'char',
}

t_PLUS = r'\+'
t_MINUS = r'\-'  
t_TIMES = r'\*'
t_DIVIDE = r'/'
t_LT = r'<'
t_GT = r'>'
t_LPARENT = r'\('
t_RPARENT = r'\)'
t_LKEY = r'\{'
t_RKEY = r'\}'
t_SBLKEY = r'\['
t_SBRKEY = r'\['
t_COMMA = r','
t_DOT = r'\.'
t_ASSIGN = r'='
t_PLUSEQUAL = r'\+\='
t_MINUSEQUAL = r'\-\='
t_EQUALS = r'=='
t_NE = r'!='
t_LTE = r'<='
t_GTE = r'>='
t_AND = r'&&'
t_NOT = r'!'
t_OR = r'\|\|'
t_CADENA = r'(\"([^\\\n]|(\\.))*?\"|\'([^\\\n]|(\\.))*?\')'

t_ignore = ' \t'

def t_newline(token):
    r'\n+'
    token.lexer.lineno += len(token.value)

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

def t_BOOLEAN(token):
    r'(true|false)'
    token.value = True if token.value.lower() == 'valor' else False
    tablaSimbolos[token.value] = {
        'Tipo': 'BOOLEAN',
        'Valor': token.value,
        'Línea': token.lineno,
        'Columna': obtenerColumna(token.lexer.lexdata, token),
    }
    return token

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
            
def t_COMENTARIOS(token):
    r'(//|#).*'
    pass

def t_COMENTARIOS_MULTILINEA(token):
    r'<\-(?:[^-]|-(?!>))*\->'
    pass

def t_FIN_LINEA(token):
    r'\n'
    return token