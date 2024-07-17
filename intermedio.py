from sintactico import parser

#reinicia la generación de código intermedio
def reiniciarGI():
    global ASA
    global longitudASA
    global fase
    global codigoIntermedio
    ASA = []
    fase = 1
    longitudASA = 0
    codigoIntermedio = ''
def obtener_codigo_intermedio():
    return codigoIntermedio


#variables de control de lectura de ASA    
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
                codigoIntermedio += str(asa[2]) +' = '+ str(asa[3]) + '\n'
        else:
                codigoIntermedio += str(asa[2]) +'\n'

    elif nodo == 'asignacion':
        print(nodo)
        codigoIntermedio += str(asa[1]) +'\n'

    elif nodo == 'condicion':
        print(nodo)
        codigoIntermedio += 'if '+str(asa[1])+' '+str(asa[2])+' '+str(asa[3])+' goto'+'\n'
        

    elif nodo == 'cicloFor':
        print(nodo)
        map(asa[2])

    elif nodo == 'cicloWhile':
        print(nodo)
        map(asa[2])
   
    elif nodo == 'Si':
        print(nodo)
        map(asa[1])
        map(asa[2])
        print(nodo)
       
       
          


    elif nodo == 'SiNo':
        print(nodo)
        map(asa[1])
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
            
codigo = '''
Class Mundo1 {
    float enemigos = (5/2)*3$
	int fuerza = 2$
    String Mensaje = "Perdiste"$

    for in range 4{
 	    int af
        $	
    }
    if (enemigos>fuerza){

    }
    else {
    
    }
}
'''
resultado = parser.parse(codigo)
print(resultado)
map(resultado)
print(codigoIntermedio)
