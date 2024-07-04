import tkinter as tk
from tkinter import filedialog, simpledialog, colorchooser, Toplevel, Listbox, Button
from lexico import tokens, reservadas
import re

class TextEditor:

    def __init__(self, root):
        self.root = root
        self.root.title("Text Editor")
        self.file_path = None

        # Frame para la barra superior
        self.top_frame = tk.Frame(root, bg="#FFA500", height=60)
        self.top_frame.pack(side=tk.TOP, fill=tk.X)

        # Botón Compilar
        self.compile_button = tk.Button(self.top_frame, text="Compilar", command=self.compile_code, bg="#FFA500")
        self.compile_button.pack(side=tk.RIGHT, padx=5, pady=5)

        # Menú Archivo
        self.file_menu_button = tk.Menubutton(self.top_frame, text="Archivo", bg="#FFA500")
        self.file_menu = tk.Menu(self.file_menu_button, tearoff=0, bg="#FFA500", fg="black")
        self.file_menu.add_command(label="Nuevo", command=self.new_file, accelerator="Ctrl+N")
        self.file_menu.add_command(label="Abrir", command=self.open_file, accelerator="Ctrl+O")
        self.file_menu.add_command(label="Guardar", command=self.save_file, accelerator="Ctrl+S")
        self.file_menu.add_command(label="Guardar como...", command=self.save_as_file, accelerator="Ctrl+Shift+S")
        self.file_menu.add_separator()
        self.file_menu.add_command(label="Cerrar archivo", command=self.close_file, accelerator="Ctrl+W")
        self.file_menu.add_command(label="Salir", command=root.quit, accelerator="Ctrl+Q")
        self.file_menu_button.config(menu=self.file_menu)
        self.file_menu_button.pack(side=tk.LEFT, padx=5, pady=5)

        # Menú Editar
        self.edit_menu_button = tk.Menubutton(self.top_frame, text="Editar", bg="#FFA500")
        self.edit_menu = tk.Menu(self.edit_menu_button, tearoff=0, bg="#FFA500", fg="black")
        self.edit_menu.add_command(label="Cortar", command=self.cut_text, accelerator="Ctrl+X")
        self.edit_menu.add_command(label="Copiar", command=self.copy_text, accelerator="Ctrl+C")
        self.edit_menu.add_command(label="Pegar", command=self.paste_text, accelerator="Ctrl+V")
        self.edit_menu.add_separator()
        self.edit_menu.add_command(label="Deshacer", command=self.edit_undo, accelerator="Ctrl+Z")
        self.edit_menu.add_command(label="Rehacer", command=self.edit_redo, accelerator="Ctrl+Y")
        self.edit_menu_button.config(menu=self.edit_menu)
        self.edit_menu_button.pack(side=tk.LEFT, padx=5, pady=5)

        # Menú Formato
        self.format_menu_button = tk.Menubutton(self.top_frame, text="Formato", bg="#FFA500")
        self.format_menu = tk.Menu(self.format_menu_button, tearoff=0, bg="#FFA500", fg="black")
        self.format_menu.add_command(label="Cambiar Fuente", command=self.change_font)
        self.format_menu.add_command(label="Cambiar Tamaño de Fuente", command=self.change_font_size)
        self.format_menu.add_command(label="Cambiar Color de Fondo", command=self.change_bg_color)
        self.format_menu_button.config(menu=self.format_menu)
        self.format_menu_button.pack(side=tk.LEFT, padx=5, pady=5)

        # Barra lateral izquierda con ancho de 60 píxeles
        self.left_frame = tk.Frame(root, bg="#FFA500", width=60)
        self.left_frame.pack(side=tk.LEFT, fill=tk.Y)
        self.left_frame.pack_propagate(False)

        # Líneas de separación en la barra lateral izquierda
        self.separator1 = tk.Frame(self.left_frame, bg="white", height=2)
        self.separator1.pack(fill=tk.X, pady=root.winfo_screenheight() // 3)

        self.separator2 = tk.Frame(self.left_frame, bg="white", height=2)
        self.separator2.pack(fill=tk.X, pady=root.winfo_screenheight() // 3)

        # Canvas para el contorno negro y el área de texto
        self.canvas = tk.Canvas(root, bg="#001f3f", bd=0, highlightthickness=0)
        self.canvas.pack(fill=tk.BOTH, expand=1)

        # Contorno negro
        self.text_frame = tk.Frame(self.canvas, bg="black", bd=5)
        self.text_frame.pack(fill=tk.BOTH, expand=1, padx=5, pady=5)

        # Área de texto y consola divididas en una proporción 3:1
        self.text_and_console_frame = tk.Frame(self.text_frame, bg="#001f3f")
        self.text_and_console_frame.pack(fill=tk.BOTH, expand=1)

        # Área de texto con scrollbar
        self.text_area_frame = tk.Frame(self.text_and_console_frame, bg="#001f3f")
        self.text_area_frame.pack(fill=tk.BOTH, expand=1, side=tk.TOP)

        self.scrollbar = tk.Scrollbar(self.text_area_frame)
        self.scrollbar.pack(side=tk.RIGHT, fill=tk.Y)

        # Sistema de líneas de código
        self.line_numbers = tk.Text(self.text_area_frame, width=4, bg="white", fg="black", state='disabled')
        self.line_numbers.pack(side=tk.LEFT, fill=tk.Y)

        # Área de texto
        self.text_area = tk.Text(self.text_area_frame, undo=True, bd=0, padx=5, pady=5, bg="#001f3f",fg="white",insertbackground="white", yscrollcommand=self.on_scroll)
        self.text_area.pack(fill=tk.BOTH, expand=1)
        self.scrollbar.config(command=self.on_scroll)
        self.text_area.bind('<KeyRelease>', self.on_key_release)
        self.text_area.bind('<MouseWheel>', self.update_line_numbers)

        # Área de consola
        self.console_area = tk.Text(self.text_and_console_frame, height=5, bg="white", fg="black", bd=0, padx=5, pady=5, state='disabled')
        self.console_area.pack(fill=tk.X, side=tk.BOTTOM)

        # Atajos de teclado
        root.bind('<Control-n>', lambda event: self.new_file())
        root.bind('<Control-o>', lambda event: self.open_file())
        root.bind('<Control-s>', lambda event: self.save_file())
        root.bind('<Control-Shift-s>', lambda event: self.save_as_file())
        root.bind('<Control-w>', lambda event: self.close_file())
        root.bind('<Control-q>', lambda event: root.quit())
        root.bind('<Control-x>', lambda event: self.cut_text())
        root.bind('<Control-c>', lambda event: self.copy_text())
        root.bind('<Control-v>', lambda event: self.paste_text())
        root.bind('<Control-z>', lambda event: self.edit_undo())
        root.bind('<Control-y>', lambda event: self.edit_redo())

        self.update_line_numbers()
        self._create_tags()

    def new_file(self):
        self.file_path = None
        self.text_area.delete(1.0, tk.END)
        self.update_line_numbers()

    def open_file(self):
        file_path = filedialog.askopenfilename(defaultextension=".txt",
                                               filetypes=[("All Files", "*.*"),
                                                          ("Text Documents", "*.txt")])
        if file_path:
            self.file_path = file_path
            with open(file_path, "r") as file:
                self.text_area.delete(1.0, tk.END)
                self.text_area.insert(tk.END, file.read())
            self.update_line_numbers()

    def save_file(self):
        if self.file_path:
            with open(self.file_path, "w") as file:
                file.write(self.text_area.get(1.0, tk.END))
        else:
            self.save_as_file()

    def save_as_file(self):
        file_path = filedialog.asksaveasfilename(defaultextension=".txt",
                                                 filetypes=[("All Files", "*.*"),
                                                            ("Text Documents", "*.txt")])
        if file_path:
            self.file_path = file_path
            with open(file_path, "w") as file:
                file.write(self.text_area.get(1.0, tk.END))

    def close_file(self):
        self.text_area.delete(1.0, tk.END)
        self.file_path = None
        self.update_line_numbers()

    def cut_text(self):
        self.text_area.event_generate("<<Cut>>")
        self.update_line_numbers()

    def copy_text(self):
        self.text_area.event_generate("<<Copy>>")

    def paste_text(self):
        self.text_area.event_generate("<<Paste>>")
        self.update_line_numbers()

    def edit_undo(self):
        self.text_area.edit_undo()
        self.update_line_numbers()

    def edit_redo(self):
        self.text_area.edit_redo()
        self.update_line_numbers()

    def update_line_numbers(self, event=None):
        self.line_numbers.config(state='normal')
        self.line_numbers.delete(1.0, tk.END)

        line_count = int(self.text_area.index('end').split('.')[0])
        line_number_string = "\n".join(str(i) for i in range(1, line_count + 1))
        self.line_numbers.insert(1.0, line_number_string)

        self.line_numbers.config(state='disabled')

    def on_scroll(self, *args):
        self.text_area.yview(*args)
        self.line_numbers.yview(*args)

    def compile_code(self):
        # Lógica para compilar el código
        self.console_area.config(state='normal')
        
        self.console_area.insert(tk.END, self.text_area.get("1.0", tk.END).strip())
        self.console_area.config(state='disabled')

    def change_font(self):
        fonts = ["Arial", "Calibri", "Times New Roman", "Comic Sans MS"]
        font_window = Toplevel(self.root)
        font_window.title("Cambiar Fuente")
        font_window.geometry("200x200")

        listbox = Listbox(font_window)
        listbox.pack(fill=tk.BOTH, expand=1)

        for font in fonts:
            listbox.insert(tk.END, font)

        def on_select():
            selected_font = listbox.get(listbox.curselection())
            current_font = self.text_area.cget("font")
            if current_font:
                current_font_split = current_font.split()
                if len(current_font_split) > 1:
                    self.text_area.config(font=(selected_font, current_font_split[1]))
                else:
                    self.text_area.config(font=(selected_font,))
            else:
                self.text_area.config(font=(selected_font,))
            font_window.destroy()

        listbox.bind('<<ListboxSelect>>', lambda event: None)

        accept_button = Button(font_window, text="Aceptar", command=on_select)
        accept_button.pack()

    def change_font_size(self):
        font_size = simpledialog.askinteger("Cambiar Tamaño de Fuente", "Elige un tamaño de fuente:")
        if font_size:
            self.text_area.config(font=(self.text_area.cget("font").split()[0], font_size))

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
            self.text_area.config(bg=selected_color_code)
            if selected_color_code == "#FFFFFF":
                self.text_area.config(fg="black", insertbackground="black")
            else:
                self.text_area.config(fg="white", insertbackground="white")
            color_window.destroy()

        listbox.bind('<<ListboxSelect>>', lambda event: None)

        accept_button = Button(color_window, text="Aceptar", command=on_select)
        accept_button.pack()

    #----------------------COLORES PALABRAS RESERVADAS------------------------------------------
    def _create_tags(self):
        # Crear etiquetas para los tokens y las palabras reservadas
        self.text_area.tag_configure("TOKENS", foreground="lime green")
        self.text_area.tag_configure("RESERVADAS", foreground="red")
        self.text_area.tag_configure("SYMBOL", foreground="orange")
        self.text_area.tag_configure("COMMENT", foreground="gray52")

    def on_key_release(self, event):
        self.update_line_numbers()
        self.highlight_code()

    def highlight_code(self):
        code = self.text_area.get("1.0", tk.END)
        self.text_area.mark_set("range_start", "1.0")

        # Eliminar todas las etiquetas previas
        for tag in self.text_area.tag_names():
            self.text_area.tag_remove(tag, "1.0", tk.END)

        # Resaltar palabras reservadas (insensible a mayúsculas/minúsculas)
        for word in reservadas:
            start_index = "1.0"
            while True:
                start_index = self.text_area.search(r'(?i)\b' + re.escape(word) + r'\b', start_index, tk.END, regexp=True)
                if not start_index:
                    break
                end_index = f"{start_index}+{len(word)}c"
                self.text_area.tag_add("RESERVADAS", start_index, end_index)
                start_index = end_index

        # Resaltar tokens (insensible a mayúsculas/minúsculas)
        for pattern in tokens:
            regex = re.compile(r'(?i)\b' + pattern + r'\b')
            for match in regex.finditer(code):
                start_index = f"1.0 + {match.start()}c"
                end_index = f"1.0 + {match.end()}c"
                self.text_area.tag_add("TOKENS", start_index, end_index)

        # Resaltar símbolos
        symbols = [r'\{', r'\}', r'\(', r'\)', r'\[', r'\]']
        for symbol in symbols:
            start_index = "1.0"
            while True:
                start_index = self.text_area.search(symbol, start_index, tk.END, regexp=True)
                if not start_index:
                    break
                end_index = f"{start_index}+1c"
                self.text_area.tag_add("SYMBOL", start_index, end_index)
                start_index = end_index

        # Resaltar comentarios de una sola línea
        start_index = "1.0"
        while True:
            start_index = self.text_area.search(';', start_index, tk.END)
            if not start_index:
                break
            end_index = self.text_area.search('\n', start_index, tk.END)
            if not end_index:
                end_index = tk.END
            self.text_area.tag_add("COMMENT", start_index, end_index)
            start_index = end_index

        # Resaltar comentarios de múltiples líneas
        start_index = "1.0"
        while True:
            start_index = self.text_area.search(r'<-', start_index, tk.END)
            if not start_index:
                break
            end_index = self.text_area.search(r'->', start_index, tk.END)
            if not end_index:
                end_index = tk.END
            else:
                end_index = f"{end_index}+2c"  # Incluir los caracteres '->' en el resaltado
            self.text_area.tag_add("COMMENT", start_index, end_index)
            start_index = end_index
     #----------------------COLORES PALABRAS RESERVADAS------------------------------------------
if __name__ == "__main__":
    root = tk.Tk()
    editor = TextEditor(root)
    root.mainloop()
