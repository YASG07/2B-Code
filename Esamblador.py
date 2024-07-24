from sintactico import parser

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

def redondear(valor):
    """ Redondea un número flotante al entero más cercano """
    if isinstance(valor, float):
        if valor - int(valor) >= 0.5:
            return int(valor) + 1
        else:
            return int(valor)
    return valor

def manejar_variable(variable, valor):
    """ Maneja la asignación de variables y redondea flotantes si es necesario """
    if isinstance(valor, float):
        valor = redondear(valor)
        return f'{variable} DW {valor}\n'
    elif isinstance(valor, int):
        return f'{variable} DW {valor}\n'
    elif isinstance(valor, str):
        # Agregar $ al final de la cadena
        valor_modificado = valor.replace('"', '').replace("'", "\\'")  # Escapa comillas simples
        return f'{variable} DB 7,10,13,\'{valor_modificado} $\'\n'
    return ''

def agregar_condicion(condicion):
    """ Agrega las instrucciones de comparación para las condiciones """
    global codeSection  # Declara codeSection como global
    if condicion[1] in ['AL', 'BL', 'CL', 'DL']:
        codeSection += f'cmp {condicion[1]}, {redondear(condicion[3])}\n'
    else:
        codeSection += f'mov al, {redondear(condicion[1])}\n'
        codeSection += f'cmp al, {redondear(condicion[3])}\n'
    if condicion[2] == '>':
        codeSection += f'jg label{contadorLabel}\n'
    elif condicion[2] == '==':
        codeSection += f'je label{contadorLabel}\n'
    elif condicion[2] == '<':
        codeSection += f'jl label{contadorLabel}\n'
    elif condicion[2] == '!=':
        codeSection += f'jne label{contadorLabel}\n'
    

def map1(asa):
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
        map1(asa[1])
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
        map1(asa[2])

    elif nodo == 'bloque':
        print(nodo)
        for instruccion in asa[1]:
            map1(instruccion)
        fase += 1
        if fase < longitudASA:
            map1(ASA[fase])

    elif nodo == 'declaracion':
        print(nodo)
        variable = asa[2]
        valor = asa[3] if len(asa) > 3 and asa[3] is not None else 0
        dataSection += manejar_variable(variable, valor)

    elif nodo == 'asignacion':
        print(nodo)
        variable = asa[1]
        valor = asa[2]
        if variable in ['AL', 'BL', 'CL', 'DL']:
            codeSection += f"mov {variable}, {valor}\n"
        else:
            codeSection += f"mov ax, {valor}\n"
            codeSection += f"mov {variable}, al\n"

    elif nodo == 'condicion':
        print(nodo)
        agregar_condicion(asa)

    elif nodo == 'cicloFor':
        print(nodo)
        variable = asa[1]
        contadorLabel += 1
        codeSection += f'mov cx, {variable}\nlabel{contadorLabel}:\n'
        contadorLabel += 1
        codeSection += f'cmp cx, 0\njle label{contadorLabel}\n'
        map1(asa[2])
        codeSection += f'dec cx\njmp label{contadorLabel-1}\nlabel{contadorLabel}:\n'

    elif nodo == 'cicloWhile':
        print(nodo)
        variable = asa[1]
        contadorLabel += 1
        codeSection += f'label{contadorLabel}:\n'
        map1(asa[2])  # Verifica la condición mientras el ciclo está en ejecución
        contadorLabel += 1
        codeSection += f'jne label{contadorLabel+1}\nlabel{contadorLabel}:\n'
        map1(asa[3])
        codeSection += f'jmp label{contadorLabel-1}\nlabel{contadorLabel}:\n'
        contadorLabel += 1

    elif nodo == 'Si':
        print(nodo)
        condicion = asa[1]
        contadorLabel += 1
    # Agrega las instrucciones de comparación para la condición
        agregar_condicion(condicion)
    # Etiqueta donde se ejecutará el bloque si la condición es verdadera
        codeSection += f'label{contadorLabel}:\n'
    # Ejecuta el bloque de instrucciones del Si
        map1(asa[2])
    # Agrega una etiqueta final para el bloque Si
        contadorLabel += 1
        codeSection += f'label{contadorLabel}:\n'
    # Salta al final después del bloque Si
        codeSection += f'jmp label{contadorLabel+1}\n'
    # Etiqueta para el final de la sección Si
        contadorLabel += 1
        codeSection += f'label{contadorLabel}:\n'

    elif nodo == 'SiNo':
        print(nodo)
        condicion = asa[1][1]
        contadorLabel += 1
    # Agrega las instrucciones de comparación para la condición
        agregar_condicion(condicion)
    # Etiqueta donde se ejecutará el bloque si la condición es verdadera
        codeSection += f'label{contadorLabel}:\n'
    # Ejecuta el bloque de instrucciones del Si
        map1(asa[1][2])
    # Salta al bloque Sino
        contadorLabel += 1
        codeSection += f'jmp label{contadorLabel+1}\n'
    # Etiqueta para el final del bloque Si
        codeSection += f'label{contadorLabel}:\n'
    # Ejecuta el bloque de instrucciones del Sino
        map1(asa[2])
    # Agrega una etiqueta final para el bloque Sino
        contadorLabel += 1
        codeSection += f'label{contadorLabel}:\n'
        
    elif nodo == 'escribir':
        print(nodo)
        variable = asa[2]
        # Generar el código de impresión para la cadena almacenada en la sección de datos
        codeSection += 'mov ah, 09h\n'
        codeSection += f'lea dx, {variable}\n'
        codeSection += 'int 21h\n'

    elif nodo == 'leer':
        print(nodo)
        variable = asa[2]
        # Generar el código de impresión para la cadena almacenada en la sección de datos
        codeSection += 'mov ah, 0\n'
        codeSection += 'int 16h\n'
        codeSection += f'mov [{variable}],ax\n'
        codeSection += 'mov ah, 0Eh\n'
        codeSection += 'int 10h\n'

    elif nodo == 'funcion':
        print(nodo)
        map1(asa[2])

    elif nodo == 'Error':
        reiniciarGA()
        return
