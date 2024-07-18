from sintactico import parser

#reinicia la generación de código intermedio
def reiniciarGI():
    global ASA
    global longitudASA
    global fase
    global codigoIntermedio
    global contadorLabel
    ASA = []
    fase = 1
    longitudASA = 0
    codigoIntermedio = ''
    contadorLabel = 0
def obtener_codigo_intermedio():
    return codigoIntermedio


#variables de control de lectura de ASA
contadorLabel = 0    
longitudASA = 0
fase = 1
ASA = []
codigoIntermedio = ''

#recorrido del árbol de sintaxis abstracta
def map(asa):
    #detiene el recorrido al encontrar un error o al terminar el ASA
    if not asa:
        reiniciarGI()
        return
    
    #recupera el nombre del nodo
    nodo = asa[0]

    #evalua si el nombre del nodo es programa
    if nodo == 'programa':
        print(nodo)
        global ASA
        ASA = asa
        global longitudASA
        longitudASA = len(asa)
        global fase
        global codigoIntermedio
        global contadorLabel
        #invoca la recursividad del metodo map
        map(asa[1])

    #evalua si el nombre del nodo es clase
    elif nodo == 'clase':
        print(nodo)
        #invoca la recursividad del metodo map
        map(asa[2])

    elif nodo == 'bloque':
        print(nodo)
        #por cada instruccion en el bloque invoca recursividad
        for instruccion in asa[1]:
            map(instruccion)
        fase += 1
        #cuando la fase alcance la longitud del ASA original termina el recorrido
        if fase < longitudASA:
            map(ASA[fase])

    elif nodo == 'declaracion':
        print(nodo)
        if len(asa) > 3 and asa[3] is not None:
                codigoIntermedio += f'{asa[2]} = {asa[3]}\n'
        else:
                codigoIntermedio += f'{asa[2]} = 0\n' 

    elif nodo == 'asignacion':
        print(nodo)
        codigoIntermedio += f"{asa[1]} = {asa[2]}\n"

    elif nodo == 'condicion':
        print(nodo)
        codigoIntermedio += f'if {asa[1]} {asa[2]} {asa[3]} goto label{contadorLabel}\ngoto label{contadorLabel+1}\n'

    elif nodo == 'cicloFor':
        print(nodo)
        codigoIntermedio += f'T1 = {asa[1]}\nlabel{contadorLabel}:\n'
        contadorLabel += 1
        codigoIntermedio += f'if T1 > 0 goto label{contadorLabel}\ngoto label{contadorLabel+1}\n'
        codigoIntermedio += f'label{contadorLabel}:\n'
        map(asa[2])
        codigoIntermedio += f'T2 = T1 - 1\nT1 = T2\ngoto label{contadorLabel-1}\n'
        contadorLabel += 1
        codigoIntermedio += f'label{contadorLabel}:\n'

    elif nodo == 'cicloWhile':
        print(nodo)
        contadorLabel += 1
        codigoIntermedio += f'label{contadorLabel}:\n'
        map(asa[2])
   
    elif nodo == 'Si':
        print(nodo)
        contadorLabel += 1
        map(asa[1])
        codigoIntermedio += f'label{contadorLabel}:\n'
        map(asa[2])
        contadorLabel += 1
        codigoIntermedio += f'label{contadorLabel}:\n'
    elif nodo == 'SiNo':
        print(nodo)
        condicion = asa[1][1]
        if condicion[2] == '>':
            codigoIntermedio += f'if {condicion[1]} < {condicion[3]} goto label{contadorLabel+2}\n'
        if condicion[2] == '==':
            codigoIntermedio += f'if {condicion[1]} != {condicion[3]} goto label{contadorLabel+2}\n'
        if condicion[2] == '<':
            codigoIntermedio += f'if {condicion[1]} > {condicion[3]} goto label{contadorLabel+2}\n'
        if condicion[2] == '!=':
            codigoIntermedio += f'if {condicion[1]} == {condicion[3]} goto label{contadorLabel+2}\n'
        map(asa[1])
        contadorLabel += 1
        codigoIntermedio += f'label{contadorLabel}:\n'
        map(asa[2])

    elif nodo == 'funcion':
        print(nodo)
        map(asa[2])

    elif nodo == 'funcionParametrizada':
        print(nodo)
        map(asa[3])

    #Detiene la ejecución si encuentra un error
    elif nodo == 'Error':
        reiniciarGI()
        return