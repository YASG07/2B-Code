from tkinter import *
from tkinter import filedialog
from tkinter import scrolledtext
from tkinter.scrolledtext import ScrolledText
from tkinter import messagebox as mb
from lexico import tokens, reservadas,analizador,tablaErrores
from sintactico import parser, yacc,reiniciarAnalizadorSintactico
from intermedio import map,reiniciarGI,obtener_codigo_intermedio
from Esamblador        import map,reiniciarGA, obtener_codigo_ensamblador
import tkinter as tk
from tkinter import filedialog, messagebox
from tkinter import ttk
import re

# Estructura Visual del compilador y funciones basicas


class ScrollTextWithLineNumbers(Frame):
    def __init__(self, master, **kwargs):
        super().__init__(master, **kwargs)  # Llama al constructor de la clase base (Frame)

        # Crear el widget de números de línea
        self.line_numbers = Text(self, width=4, padx=4, wrap='none',bg="#FFA500")
        self.line_numbers.pack(side='left', fill='y')  # Empaqueta el widget en el lado izquierdo y lo hace llenar en la dirección y

        # Crear el widget de texto desplazable
        self.text_widget = ScrolledText(self, width=105, height=10, bg="#001f3f",fg="white")
        self.text_widget.pack(side='left', fill='both', expand=True)  # Empaqueta el widget en el lado izquierdo y lo hace llenar en ambas direcciones y expandir su tamaño

        # Crear la barra de desplazamiento
        self.scrollbar = Scrollbar(self, command=self._scroll_text)
        self.scrollbar.pack(side='right', fill='y')  # Empaqueta la barra de desplazamiento en el lado derecho y lo hace llenar en la dirección y

        # Configurar los comandos de desplazamiento
        self.text_widget.config(yscrollcommand=self.scrollbar.set,insertbackground='white')
        self.text_widget.config(yscrollcommand=self.scrollbar.set)  # Configura el comando de desplazamiento vertical
        self.scrollbar.config(command=self.text_widget.yview)  # Configura el comando de la barra de desplazamiento

        # Asignar eventos a los widgets de texto
        self.text_widget.bind("<KeyRelease>", self.on_key_release)
#        self.text_widget.bind('<Key>', self._on_text_change)  # Asigna el evento Key (tecla) al método _on_text_change
        self.text_widget.bind('<Return>', self._on_text_change)  # Asigna el evento Return (retorno) al método _on_text_change
        self.text_widget.bind('<Button-4>', self._scroll_up)  # Asigna el evento Button-4 (rueda del mouse hacia arriba) al método _scroll_up
        self.text_widget.bind('<Button-5>', self._scroll_down)  # Asigna el evento Button-5 (rueda del mouse hacia abajo) al método _scroll_down
        
        # Actualizar los números de línea
        self._update_line_numbers()

# ---------------------------COLORES------------------------------------- 
        self._create_tags()

    def _create_tags(self):
        # Crear etiquetas para los tokens y las palabras reservadas 
        self.text_widget.tag_configure("TOKENS", foreground="lime green")
        self.text_widget.tag_configure("RESERVADAS", foreground="red")
        self.text_widget.tag_configure("SYMBOL", foreground="orange")
        self.text_widget.tag_configure("COMENTARIOS", foreground="gray52")

    def on_key_release(self, event):
            self.highlight_code()

    def highlight_code(self):
        code = self.text_widget.get("1.0", tk.END)
        self.text_widget.mark_set("range_start", "1.0")

        # Eliminar todas las etiquetas previas
        for tag in self.text_widget.tag_names():
            self.text_widget.tag_remove(tag, "1.0", tk.END)

        # Resaltar palabras reservadas (insensible a mayúsculas/minúsculas)
        for word in reservadas:
            start_index = "1.0"
            while True:
                start_index = self.text_widget.search(r'(?i)\b' + re.escape(word) + r'\b', start_index, tk.END, regexp=True)
                if not start_index:
                    break
                end_index = f"{start_index}+{len(word)}c"
                self.text_widget.tag_add("RESERVADAS", start_index, end_index)
                start_index = end_index

        # Resaltar tokens (insensible a mayúsculas/minúsculas)
        for pattern in tokens:
            regex = re.compile(r'(?i)\b' + pattern + r'\b')
            for match in regex.finditer(code):
                start_index = f"1.0 + {match.start()}c"
                end_index = f"1.0 + {match.end()}c"
                self.text_widget.tag_add("TOKENS", start_index, end_index)

        # Resaltar símbolos
        symbols = [r'\{', r'\}', r'\(', r'\)', r'\[', r'\]']
        for symbol in symbols:
            start_index = "1.0"
            while True:
                start_index = self.text_widget.search(symbol, start_index, tk.END, regexp=True)
                if not start_index:
                    break
                end_index = f"{start_index}+1c"
                self.text_widget.tag_add("SYMBOL", start_index, end_index)
                start_index = end_index

        # Resaltar comentarios de una sola línea
        start_index = "1.0"
        while True:
            start_index = self.text_widget.search(';', start_index, tk.END)
            if not start_index:
                break
            end_index = self.text_widget.search('\n', start_index, tk.END)
            if not end_index:
                end_index = tk.END
            self.text_widget.tag_add("COMENTARIOS", start_index, end_index)
            start_index = end_index

        # Resaltar comentarios de múltiples líneas
        start_index = "1.0"
        while True:
            start_index = self.text_widget.search(r'<-', start_index, tk.END)
            if not start_index:
                break
            end_index = self.text_widget.search(r'->', start_index, tk.END)
            if not end_index:
                end_index = tk.END
            else:
                end_index = f"{end_index}+2c"  # Incluir los caracteres '->' en el resaltado
            self.text_widget.tag_add("COMMENT", start_index, end_index)
            start_index = end_index
#------------------------------------------------------------------------------------------------------

        
    def _scroll_text(self, *args):
        self.text_widget.yview(*args)
        self._update_line_numbers()
        
    def _on_text_change(self, event):
        self._update_line_numbers()
        
    def _scroll_up(self, event):
        self.text_widget.yview_scroll(-1, 'units')
        self._update_line_numbers()
        
    def _scroll_down(self, event):
        self.text_widget.yview_scroll(1, 'units')
        self._update_line_numbers()
        
    def _update_line_numbers(self):
        lines = self.text_widget.get(1.0, 'end').split('\n')
        line_numbers = '\n'.join(str(i) for i in range(1, len(lines)))
        self.line_numbers.config(state='normal')
        self.line_numbers.delete(1.0, 'end')
        self.line_numbers.insert(1.0, line_numbers)
        self.line_numbers.config(state='disabled')

    def get_text(self):
        return self.text_widget.get(1.0, 'end-1c')
    
    def set_text(self, new_text):
        self.text_widget.delete(1.0, 'end')  # Borra el texto existente
        self.text_widget.insert('end', new_text)  # Inserta el nuevo texto al final del widget
        self._update_line_numbers()  # Actualiza la numeración de líneas

    def clear_scroll_text(self):
        self.text_widget.delete(1.0, 'end')  # Borra todo el texto en el widget
        self._update_line_numbers()  # Actualiza la numeración de líneas
def change_bg_color(self):
        colors = {"Blanco": "#FFFFFF", "Negro": "#000000", "Azul": "#001f3f"}
        color_window = Toplevel(self.root)
        color_window.title("Cambiar Color de Fondo")
        color_window.geometry("200x200")

        listbox = Listbox(color_window)
        listbox.pack(fill=tk.BOTH, expand=1)

        for color_name in colors.keys():
            listbox.insert(tk.END, color_name)

        def on_select():
            selected_color_name = listbox.get(listbox.curselection())
            selected_color_code = colors[selected_color_name]
            self.text_widget.config(bg=selected_color_code)
            if selected_color_code == "#FFFFFF":
                self.text_widget.config(fg="black", insertbackground="black")
            else:
                self.text_widget.config(fg="white", insertbackground="white")
            color_window.destroy()

        listbox.bind('<<ListboxSelect>>', lambda event: None)

        accept_button = Button(color_window, text="Aceptar", command=on_select)
        accept_button.pack()

# Variables para almacenar las referencias de las ventanas
lexico_window = None
sintactico_window = None
intermedio_window = None
ensamblador_window = None
tabla_window = None

def cerrar_ventana(window):
    if window is not None:
        window.destroy()
        window = None
    return window
#----------------------------------Analisis Lexico------------------------------------------------------------------
def mostrarAnalisisLexico2(tokens):
    global lexico_window
    lexico_window = cerrar_ventana(lexico_window)  # Cerrar la ventana existente si hay una
    # Crear una nueva ventana
    lexico_window = Toplevel()
    lexico_window.title("Análisis Léxico")

    # Crear un Treeview widget con columnas
    tree = ttk.Treeview(lexico_window, columns=('Tipo', 'Valor', 'Renglon', 'Columna'), show='headings')

    # Configurar encabezados de las columnas
    tree.heading('Tipo', text='Tipo')
    tree.heading('Valor', text='Valor')
    tree.heading('Renglon', text='Renglon')
    tree.heading('Columna', text='Columna')

    # Configurar tamaño de las columnas
    tree.column('Tipo', minwidth=0, width=100)
    tree.column('Valor', minwidth=0, width=150)
    tree.column('Renglon', minwidth=0, width=70)
    tree.column('Columna', minwidth=0, width=70)

    # Insertar datos en el Treeview widget
    for token in tokens:
        token_type, token_value, token_lineno, token_lexpos = token
        tree.insert('', tk.END, values=(token_type, token_value, token_lineno, token_lexpos))

    # Crear un Scrollbar y asociarlo al Treeview
    scrollbar = Scrollbar(lexico_window, orient=tk.VERTICAL, command=tree.yview)
    tree.configure(yscroll=scrollbar.set)

    # Configurar la posición de los widgets
    tree.pack(side='left', fill='both', expand=True)
    scrollbar.pack(side='right', fill='y')


def analisisLexico():
    scrollAnalisis.config(state="normal")  # Cambiar el estado a normal para permitir la edición
    scrollAnalisis.delete(1.0, END)  # Borra el contenido actual del `ScrolledText`
    scrollAnalisis.config(state="disabled")  # Volver a deshabilitar la edición    
    cadena = scroll_text_widget.get_text()
    if len(cadena) > 0:
        analizador.input(cadena)
        a_tok = []
        for tok in analizador:
            a_tok.append((tok.type, tok.value, tok.lineno, tok.lexpos))
       
        imprimir_errores()
        mostrarAnalisisLexico2(a_tok)
        # Imprimir la tabla de errores en scrollAnalisis
    else:
        mb.showwarning("ERROR", "Debes escribir código")
    analizador.lineno=1

#--------------------------------------Boton de compilar----------------------------------
def analisisCompleto(resul=None):
    # Obtener el texto del widget
    cadena = scroll_text_widget.get_text()
    if len(cadena) > 0:
        # Resetear el estado del widget de análisis
        scrollAnalisis.config(state="normal")
        scrollAnalisis.delete(1.0, END)
        
        # Limpiar la tabla de errores
        tablaErrores.clear()
        
        # Análisis léxico
        analizador.input(cadena)
        a_tok = []
        for tok in analizador:
            a_tok.append((tok.type, tok.value, tok.lineno, tok.lexpos))
        
        mostrarAnalisisLexico2(a_tok)

        # Mostrar errores léxicos si existen
        imprimir_errores()
        
        # Análisis sintáctico
        reiniciarAnalizadorSintactico()
        reiniciarGI()
        reiniciarGA()
        try:
            resultado = parser.parse(cadena)
            print(resultado)
            map(resultado)
            mostrarAnalisisSintactico2(resultado)
            mostrarcodigoIntermedio(obtener_codigo_intermedio())
            #mostrarcodigoEnsamblador(obtener_codigo_ensamblador())
        
        except yacc.YaccError as e:
            scrollAnalisis.insert(END, "Errores Sintácticos:\n")
            scrollAnalisis.insert(END, str(e))
            
        # #  # Mostrar errores sintácticos si existen
        imprimir_errores()
        # # # Volver a deshabilitar la edición del widget
        scrollAnalisis.config(state="disabled")
        scrollAnalisis.insert(END, "Compilacion sin error\n")
    else:
        mb.showwarning("ERROR", "Debes escribir código")
    
#------------------------------------Mostrar Errores--------------------------------------------------------------------------------
def imprimir_errores():
     scrollAnalisis.config(state="normal")  # Cambiar el estado a normal para permitir la edición
     scrollAnalisis.delete(1.0, END)  # Borra el contenido actual del `ScrolledText`
     analizador.lineno=0
     for error in tablaErrores:
         texto_error = f"Error: {error['Error']},"
         texto_error += f"Tipo: {error['Tipo']},"
         texto_error += f"Descripción: {error['Descripción']},"
         texto_error += f"Valor: {error['Valor']},"
         texto_error += f"Linea: {error['Linea']},"
         texto_error += f"Columna: {error['Columna']}\n"
         scrollAnalisis.insert(INSERT, texto_error)
     scrollAnalisis.config(state="disabled")  # Volver a deshabilitar la edición
#----------------------------------Analisis Sintactico------------------------------------------------------------------------
def mostrarAnalisisSintactico2(data):
    global sintactico_window
    sintactico_window = cerrar_ventana(sintactico_window)
    sintactico_window = tk.Toplevel()
    sintactico_window.title("Análisis Sintáctico")
    # Crear un Text widget con un Scrollbar
    text_area = Text(sintactico_window, wrap='word')
    scrollbar = Scrollbar(sintactico_window, command=text_area.yview)
    text_area.configure(yscrollcommand=scrollbar.set)

    # Configurar la posición de los widgets
    text_area.pack(side='left', fill='both', expand=True)
    scrollbar.pack(side='right', fill='y')

    # Insertar datos en el Text widget
    text_area.config(state="normal")  # Cambiar el estado a normal para permitir la edición
    text_area.delete(1.0, END)  # Borrar el contenido previo
    if data is None:
        text_area.insert(END, "No se encontraron datos para mostrar.\n")
    elif isinstance(data, (int, float)):
        text_area.insert(END, str(data) + '\n')
    else:
        for item in data:
            text_area.insert(END, str(item) + '\n')
    text_area.config(state="disabled")  # Volver a deshabilitar la edición
    
def analisisSintactico():
    cadena = scroll_text_widget.get_text()
    if len(cadena) > 0:
        try:
            resultado = parser.parse(cadena)
            print(resultado)
            mostrarAnalisisSintactico2(resultado)
            scrollAnalisis.config(state="normal")  # Cambiar el estado a normal para permitir la edición
            scrollAnalisis.delete(1.0, END)  # Borra el contenido actual del `ScrolledText`
            scrollAnalisis.insert(END, "Analisis Correcto\n")
            scrollAnalisis.config(state="disabled")  # Volver a deshabilitar la edición
        except yacc.YaccError as e:
            imprimir_errores(e)
            mb.showerror("Error", str(e))
    else:
        mb.showwarning("ERROR", "Debes escribir código")

#-----------------------------codigo Intemedio--------------------------------------------------------
def mostrarcodigoIntermedio(data):
    global intermedio_window
    intermedio_window = cerrar_ventana(intermedio_window)
    intermedio_window = tk.Toplevel()
    intermedio_window.title("Codigo Intermedio")
    # Crear un Text widget con un Scrollbar
    text_area = Text(intermedio_window, wrap='word')
    scrollbar = Scrollbar(intermedio_window, command=text_area.yview)
    text_area.configure(yscrollcommand=scrollbar.set)

    # Configurar la posición de los widgets
    text_area.pack(side='left', fill='both', expand=True)
    scrollbar.pack(side='right', fill='y')

    # Insertar datos en el Text widget
    text_area.config(state="normal")  # Cambiar el estado a normal para permitir la edición
    text_area.delete(1.0, END)  # Borrar el contenido previo
    if data is None:
        text_area.insert(END, "No se encontraron datos para mostrar.\n")
    else:
            text_area.insert(END,"======================  CODIGO INTERMEDIO ===========================\n")
            text_area.insert(END, data + '\n')
            text_area.insert(END,"=====================================================================\n")
    text_area.config(state="disabled")  # Volver a deshabilitar la edición

def codigointermedio():
    cadena = scroll_text_widget.get_text()
    reiniciarAnalizadorSintactico()
    reiniciarGI()
    if len(cadena) > 0:
        try:
            resultado = parser.parse(cadena)
            map(resultado)
            
            codigo_intermedio = obtener_codigo_intermedio()
            mostrarcodigoIntermedio(codigo_intermedio)
            
            # Convertir a ensamblador y mostrar
            codigo_ensamblador = convertir_a_ensamblador(codigo_intermedio)
            mostrarcodigoEnsamblador(codigo_ensamblador)
        except yacc.YaccError as e:
            imprimir_errores(e)
            mb.showerror("Error", str(e))
    else:
        mb.showwarning("ERROR", "Debes escribir código")


def obtener_tripletas():
    # Asumiendo que `codigo_intermedio` es una lista de tripletas
    return mostrarcodigoIntermedio()  # Modifica según cómo obtienes las tripletas

def mostrar_tripletas():
    tripletas = obtener_tripletas()
    for tripleta in tripletas:
        print(tripleta)






def AbrirArchivos():
    filename = filedialog.askopenfilename(initialdir="/",
                                          title="Select a File",
                                          filetypes=(("Text files", "*.txt"), ("Rcb files", "*.rcb"), ("all files", "*.*")))
    if filename != '':
        if filename.endswith((".txt", ".rcb")):  # Pa' abrir archivos .txt o .ht
            root.title("Compilador Python   code: " + filename)
            with open(filename, "r", encoding="utf-8") as file:
                content = file.read()
                scroll_text_widget.clear_scroll_text()
                scroll_text_widget.set_text(content)
                scroll_text_widget._update_line_numbers()
        else:
            mb.showerror("Error", "El archivo seleccionado no es de tipo .txt o .ht")

def GuardarComo():
    filename = filedialog.asksaveasfilename(initialdir="/", title="Guardar como",
                                            filetypes=(("txt files", "*.txt"), ("rcb files", "*.rcb"), ("todos los archivos", "*.*")))
    if filename != '':
        if filename.endswith((".txt", ".rcb")):  # Pa' guardar como .txt o .ht
            root.title("Compilador Python   code: " + filename)
            with open(filename, "w", encoding="utf-8") as file:
                file.write(scroll_text_widget.get_text())
            messagebox.showinfo("Información", "Los datos fueron guardados en el archivo.")
        else:
            messagebox.showerror("Error", "El archivo debe contener la extension .txt o .rcb")


def Guardar():
    filename = root.title()
    if filename.startswith("Compilador Python   code: "):
        filename = filename.lstrip("Compilador Python   code: ")
        if filename.endswith((".txt", ".rcb")):                  # Pa' guardar como .txt o .ht
            with open(filename, "w", encoding="utf-8") as file:
                file.write(scroll_text_widget.get_text())
            messagebox.showinfo("Información", "Los cambios fueron guardados en el archivo.")
        else:
            GuardarComo()
    else:
        GuardarComo()

def NuevoArchivo():
    root.title("2B'S CODE")
    scroll_text_widget.clear_scroll_text()

def AunSinAgregar():
    mb.showerror("Atención","Aun no se agrega esta funcion al compilador.")

def cambiar_tamaño_letra(size):
    scroll_text_widget.text_widget.config(font=("Console", size))
    if scrollAnalisis:
        scrollAnalisis.config(font=("Console", size))


def Ventana2(data,title):
    vt2 = Tk()
    vt2.title(title)
    vt2.geometry('400x400')

    canvas = Canvas(vt2)
    scroll_y = Scrollbar(vt2, orient="vertical", command=Canvas.yview)

    frame = Frame(Canvas)

    i=0;
    for i in range(len(data)):
        e = Label(frame,text=data[i])
        e.grid(row=i,column=2)

    canvas.create_window(0,0, anchor='nw', Windows=frame)
    canvas.update_idletasks()
    canvas.configure(scrollregion=canvas.bbox('all'),yscrollcommand=scroll_y.set)
    canvas.pack(fill='both', expand=True, side='left')
    vt2.mainloop()

# Funciones adicionales (AbrirArchivos, GuardarComo, Guardar, NuevoArchivo, etc.) se mantienen igual
root = Tk()
root.resizable(FALSE,FALSE) # Con esto denegamos que se ajuste el tamaño de la ventana de largo y ancho
root.geometry("924x596") #definimos las dimesiones de la ventana
root.title("2B'S code - Compilador de videojuegos") #Titulo de la ventana

wtotal = root.winfo_screenwidth()
htotal = root.winfo_screenheight()
wventana = 930
hventana = 599
pwidth = round(wtotal/2-wventana/2)
pheight = round(htotal/2-hventana/2)
root.geometry(str(wventana)+"x"+str(hventana)+"+"+str(pwidth)+"+"+str(pheight))

scroll_text_widget = ScrollTextWithLineNumbers(root)
scroll_text_widget.grid(row=1,column=0,padx=10,pady=10)

scrollAnalisis = ScrolledText(root, width=100,  height=8, font = cambiar_tamaño_letra, state="disable")
scrollAnalisis.grid(row=5,column=0,padx=10,pady=10)

def cambiar_tamaño_letra(size):
    scroll_text_widget.text_widget.config(font=("Console", size))
    

menubar = Menu(root, background='#FFA500', foreground='black', activebackground='white', activeforeground='black')  
file = Menu(menubar, tearoff=1)  
file.add_command(label="Nuevo",command=NuevoArchivo)
file.add_command(label="Abrir",command=AbrirArchivos)  
file.add_command(label="Guardar", command=Guardar)  
file.add_command(label="Guardar como...", command=GuardarComo)    
file.add_separator()  
file.add_command(label="Salir", command=root.quit)
menubar.add_cascade(label="Archivo", menu=file)  

analisis = Menu(menubar, tearoff=0)   
analisis.add_command(label="Lexico",command=analisisLexico)  
analisis.add_command(label="Sintactico", command=analisisSintactico) 
#analisis.add_command(label="Semantico", command=analisisSemantico) 
menubar.add_cascade(label="Analizar", menu=analisis)




font_menu = Menu(menubar, tearoff=0)
font_menu.add_command(label="10", command=lambda: cambiar_tamaño_letra(10))
font_menu.add_command(label="12", command=lambda: cambiar_tamaño_letra(12))
font_menu.add_command(label="14", command=lambda: cambiar_tamaño_letra(14))
font_menu.add_command(label="16", command=lambda: cambiar_tamaño_letra(16))
font_menu.add_command(label="18", command=lambda: cambiar_tamaño_letra(18))
font_menu.add_command(label="20", command=lambda: cambiar_tamaño_letra(20))



menubar.add_cascade(label="Tamaño de la letra", menu=font_menu)
menubar.add_radiobutton(label="Compilar", command=analisisCompleto) #BOTON 
#menubar.add_radiobutton(label="Codigo Intermedio", command=codigointermedio)
#


def mostrarcodigoEnsamblador(data):
    global ensamblador_window
    ensamblador_window = cerrar_ventana(ensamblador_window)
    ensamblador_window = tk.Toplevel()
    ensamblador_window.title("Código Ensamblador")

    # Crear un Text widget con un Scrollbar
    text_area = tk.Text(ensamblador_window, wrap='word')
    scrollbar = tk.Scrollbar(ensamblador_window, command=text_area.yview)
    text_area.configure(yscrollcommand=scrollbar.set)

    # Configurar la posición de los widgets
    text_area.pack(side='left', fill='both', expand=True)
    scrollbar.pack(side='right', fill='y')

    # Insertar datos en el Text widget
    text_area.config(state="normal")  # Cambiar el estado a normal para permitir la edición
    text_area.delete(1.0, tk.END)  # Borrar el contenido previo
    if data is None:
        text_area.insert(tk.END, "No se encontraron datos para mostrar.\n")
    else:
        text_area.insert(tk.END, "======================  CÓDIGO ENSAMBLADOR ===========================\n")
        text_area.insert(tk.END, data + '\n')
        text_area.insert(tk.END, "======================================================================\n")
    text_area.config(state="disabled")  # Volver a deshabilitar la edición

    # Crear un menú en la ventana de ensamblador
    menu_bar = tk.Menu(ensamblador_window)
    ensamblador_window.config(menu=menu_bar)
    file_menu = tk.Menu(menu_bar, tearoff=0)
    menu_bar.add_cascade(label="Archivo", menu=file_menu)
    file_menu.add_command(label="Guardar", command=lambda: guardar_archivo(text_area.get(1.0, tk.END)))
    file_menu.add_command(label="Abrir en EMU8086", command=lambda: abrir_en_emu8086())

def guardar_archivo(contenido):
    archivo_guardar = filedialog.asksaveasfilename(defaultextension=".asm",
                                                 filetypes=[("Archivos de ensamblador", "*.asm"), ("Todos los archivos", "*.*")])
    if archivo_guardar:
        with open(archivo_guardar, 'w') as archivo:
            archivo.write(contenido)

def abrir_en_emu8086():
    # Ruta al ejecutable de EMU8086
    ruta_emu8086 = "C:\emu8086\emu8086.exe"  # Cambia esto a la ruta correcta de tu EMU8086

    # Archivo ASM a abrir
    archivo_abrir = filedialog.askopenfilename(defaultextension=".asm",
                                              filetypes=[("Archivos de ensamblador", "*.asm"), ("Todos los archivos", "*.*")])
    if archivo_abrir:
        # Abre el archivo en EMU8086
        subprocess.Popen([ruta_emu8086, archivo_abrir], shell=True)

def cerrar_ventana(ventana):
    if ventana:
        ventana.destroy()
    return None


def codigoensamblador():
    cadena = scroll_text_widget.get_text()
    reiniciarAnalizadorSintactico()
    reiniciarGA()
    if len(cadena) > 0:
        try:
            resultado = parser.parse(cadena)
            map(resultado)
            
            codigo_ensamblador = obtener_codigo_ensamblador()
            mostrarcodigoEnsamblador(codigo_ensamblador)
        except yacc.YaccError as e:
            imprimir_errores(e)
            mb.showerror("Error", str(e))
    else:
        mb.showwarning("ERROR", "Debes escribir código")

#menubar.add_radiobutton(label="Generar ensamblador", command=lambda: print(mostrarcodigoEnsamblador()))
#

root.config(menu=menubar)
root.mainloop()
