# reinicia la generación de código ensamblador
def reiniciarGA():
    global ASA
    global longitudASA
    global fase
    global codigoEnsamblador
    global contadorLabel
    global dataSection
    global codeSection
    ASA = []
    fase = 1
    longitudASA = 0
    codigoEnsamblador = ''
    dataSection = ''
    codeSection = ''
    contadorLabel = 0

def obtener_codigo_ensamblador():
    global codigoEnsamblador
    return codigoEnsamblador

# variables de control de lectura de ASA
contadorLabel = 0    
longitudASA = 0
fase = 1
ASA = []
codigoEnsamblador = ''
dataSection = ''
codeSection = ''

# recorrido del árbol de sintaxis abstracta
def map(asa):
    global dataSection
    global codeSection
    global contadorLabel
    global ASA
    global longitudASA
    global fase
    global codigoEnsamblador
    
    # detiene el recorrido al encontrar un error o al terminar el ASA
    if not asa:
        reiniciarGA()
        return
    
    # recupera el nombre del nodo
    nodo = asa[0]

    # evalúa si el nombre del nodo es programa
    if nodo == 'programa':
        print(nodo)
        ASA = asa
        longitudASA = len(asa)
        fase = 1
        codigoEnsamblador = ''
        contadorLabel = 0
        map(asa[1])
        codigoEnsamblador = (
            ".model small\n"
            ".stack\n"
            f".data\n{dataSection}\n"
            f".code\nmov ax, @data\nmov ds, ax\n"
            f"{codeSection}\n"
            "end\n"
        )

    # evalúa si el nombre del nodo es clase
    elif nodo == 'clase':
        print(nodo)
        map(asa[2])

    elif nodo == 'bloque':
        print(nodo)
        for instruccion in asa[1]:
            map(instruccion)
        fase += 1
        if fase < longitudASA:
            map(ASA[fase])

    elif nodo == 'declaracion':
        print(nodo)
        variable = asa[2]
        valor = asa[3] if len(asa) > 3 and asa[3] is not None else 0
        dataSection += f'{variable} DW {valor}\n'

    elif nodo == 'asignacion':
        print(nodo)
        variable = asa[1]
        valor = asa[2]
        codeSection += f"mov {variable}, {valor}\n"

    elif nodo == 'condicion':
        print(nodo)
        codeSection += f'cmp {asa[1]}, {asa[3]}\n'
        if asa[2] == '==':
            codeSection += f'je label{contadorLabel}\n'
        elif asa[2] == '!=':
            codeSection += f'jne label{contadorLabel}\n'
        elif asa[2] == '>':
            codeSection += f'jg label{contadorLabel}\n'
        elif asa[2] == '<':
            codeSection += f'jl label{contadorLabel}\n'
        elif asa[2] == '>=':
            codeSection += f'jge label{contadorLabel}\n'
        elif asa[2] == '<=':
            codeSection += f'jle label{contadorLabel}\n'
        contadorLabel += 1

    elif nodo == 'cicloFor':
        print(nodo)
        contadorLabel += 1
        codeSection += f'mov cx, {asa[1]}\nlabel{contadorLabel}:\n'
        contadorLabel += 1
        codeSection += f'cmp cx, 0\njle label{contadorLabel}\n'
        map(asa[2])
        codeSection += f'dec cx\njmp label{contadorLabel-1}\nlabel{contadorLabel}:\n'

    elif nodo == 'cicloWhile':
        print(nodo)
        contadorLabel += 1
        codeSection += f'label{contadorLabel}:\n'
        map(asa[1])
        contadorLabel += 1
        codeSection += f'jne label{contadorLabel+1}\nlabel{contadorLabel}:\n'
        map(asa[2])
        codeSection += f'jmp label{contadorLabel-1}\nlabel{contadorLabel}:\n'
        contadorLabel += 1

    elif nodo == 'Si':
        print(nodo)
        contadorLabel += 1
        map(asa[1])
        codeSection += f'jmp label{contadorLabel+1}\nlabel{contadorLabel}:\n'
        map(asa[2])
        contadorLabel += 1
        codeSection += f'label{contadorLabel}:\n'

    elif nodo == 'SiNo':
        print(nodo)
        contadorLabel += 1
        if not asa[1][1]:
            reiniciarGA()
            return
        condicion = asa[1][1]
        codeSection += f'cmp {condicion[1]}, {condicion[3]}\n'
        if condicion[2] == '>':
            codeSection += f'jg label{contadorLabel}\n'
        elif condicion[2] == '==':
            codeSection += f'je label{contadorLabel}\n'
        elif condicion[2] == '<':
            codeSection += f'jl label{contadorLabel}\n'
        elif condicion[2] == '!=':
            codeSection += f'jne label{contadorLabel}\n'
        codeSection += f'jmp label{contadorLabel+1}\nlabel{contadorLabel}:\n'
        map(asa[1][2])
        contadorLabel += 1
        codeSection += f'label{contadorLabel}:\n'
        map(asa[2])
        contadorLabel += 1
        codeSection += f'label{contadorLabel}:\n'

    elif nodo == 'funcion':
        print(nodo)
        map(asa[2])

    elif nodo == 'funcionParametrizada':
        print(nodo)
        codeSection += f'call {asa[1]} ({asa[2][1]},{asa[2][3]})\n'
        map(asa[3])

    elif nodo == 'Error':
        reiniciarGA()
        return
