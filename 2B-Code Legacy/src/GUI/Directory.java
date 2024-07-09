
package GUI;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.JTextComponent;

public class Directory {
    JFileChooser selecFile = new JFileChooser();
    JTabbedPane tabs = new JTabbedPane();
    private File file;
    private FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos .rcb","rcb");
    private final String[] options = new String[]{"Guardar y continuar", "Descartar"};
    private final String title;
    private final String extension;
    private final JFrame jFrame;
    private final JTextComponent jTextComponent;
    
    
    public Directory(JFrame var1, JTextComponent var2, String var3, String var4, JTabbedPane tab) {
        this.title = var3;
        this.extension = var4;
        this.jFrame = var1;
        this.jTextComponent = var2;
        this.tabs = tab;
        //System.out.println(Functions.ANSI_PURPLE_BLACK + "Directory v0.35 (By Yisus Efebei and M45t3r L3g10n)");
    }
    public Directory(JFrame var1, JTextComponent var2, String var3, String var4) {
        this.title = var3;
        this.extension = var4;
        this.jFrame = var1;
        this.jTextComponent = var2;
        //System.out.println(Functions.ANSI_PURPLE_BLACK + "Directory v0.35 (By Yisus Efebei and M45t3r L3g10n)");
    }
    private String getTextFile(File var1) {
        String var2 = "";

        try {
            BufferedReader var3 = new BufferedReader(new InputStreamReader(new FileInputStream(var1), "UTF8"));

            while(true) {
                int var4 = var3.read();
                if (var4 == -1) {
                    return var2;
                }

                var2 = var2 + (char)var4;
            }
        } catch (FileNotFoundException var5) {
            System.err.println("El archivo no pudo ser encontrado... " + var5.getMessage());
            return null;
        } catch (IOException var6) {
            System.err.println("Error al leer el archivo... " + var6.getMessage());
            return null;
        }
    }

    private boolean saveFile(File var1, String var2) {
        try {
            FileOutputStream var3 = new FileOutputStream(var1);
            byte[] var4 = var2.getBytes();
            var3.write(var4);
            return true;
        } catch (FileNotFoundException var5) {
            System.err.println("El archivo no pudo ser encontrado... " + var5.getMessage());
            return false;
        } catch (IOException var6) {
            System.err.println("Error al escribir en el archivo... " + var6.getMessage());
            return false;
        }
    }

    private boolean saveEditNew(File var1, JFileChooser var2) {
        int var3;
        if (this.jFrame.getTitle().equals(this.title + "*")) {
            var3 = 0;
        } else {
            var3 = JOptionPane.showOptionDialog(this.jFrame, "El archivo actual está siendo editado, ¿desea guardar los cambios?", "¿Descartar edición?", -1, 3, (Icon)null, this.options, this.options[0]);
        }

        if (var3 == 0) {
            if (var2.getSelectedFile() != null) {
                boolean var4 = this.saveFile(var1, this.jTextComponent.getText());
                if (var4) {
                    this.jFrame.setTitle(var1.getName());
                }
            } else {
                int var7;
                if (this.jFrame.getTitle().equals(this.title + "*")) {
                    var7 = JOptionPane.showOptionDialog(this.jFrame, "¿Desea guardar el archivo actual?", "¿Descartar edición de archivo nuevo?", -1, 3, (Icon)null, this.options, this.options[0]);
                    if (var7 != 0) {
                        return true;
                    }

                    if (var2.showDialog(this.jFrame, "Guardar") == 0) {
                        var1 = var2.getSelectedFile();
                        String var5 = var1.getName();
                        if (!var5.endsWith(this.extension)) {
                            var5 = var5 + this.extension;
                        }

                        if (!this.fileNameValid(var5)) {
                            JOptionPane.showMessageDialog(this.jFrame, "Escriba un nombre válido para el archivo", "Nombre inválido", 2);
                            return false;
                        }

                        if (!var1.getName().endsWith(this.extension)) {
                            String var10002 = var1.getAbsolutePath();
                            var1 = new File(var10002 + this.extension);
                        }

                        if (!var1.exists()) {
                            this.saveFile(var1);
                        } else {
                            int var6 = JOptionPane.showConfirmDialog(this.jFrame, "Ya hay un archivo con este nombre, ¿desea sobreescribirlo?", "Sobreescribir archivo", 2);
                            if (var6 == 0) {
                                this.saveFile(var1);
                            }
                        }
                    }
                } else {
                    var7 = JOptionPane.showConfirmDialog(this.jFrame, "Ya hay un archivo con este nombre, ¿desea sobreescribirlo?", "Sobreescribir archivo", 2);
                    if (var7 == 0) {
                        this.saveFile(var1);
                    }
                }
            }
        }

        return true;
    }

    private boolean saveEditOpen(File var1, JFileChooser var2) {
        int var3;
        if (this.jFrame.getTitle().equals(this.title + "*")) {
            var3 = 0;
        } else {
            var3 = JOptionPane.showOptionDialog(this.jFrame, "El archivo actual está siendo editado, ¿desea guardar los cambios?", "¿Descartar edición?", -1, 3, (Icon)null, this.options, this.options[0]);
        }

        if (var3 == 0) {
            if (var2.getSelectedFile() != null) {
                boolean var4 = this.saveFile(var1, this.jTextComponent.getText());
                if (var4) {
                    this.jFrame.setTitle(var1.getName());
                }
            } else if (this.jFrame.getTitle().equals(this.title + "*")) {
                int var7 = JOptionPane.showOptionDialog(this.jFrame, "¿Desea guardar el archivo actual?", "¿Descartar edición de archivo nuevo?", -1, 3, (Icon)null, this.options, this.options[0]);
                if (var7 == 0) {
                    if (var2.showDialog(this.jFrame, "Guardar") == 0) {
                        var1 = var2.getSelectedFile();
                        String var5 = var1.getName();
                        if (!var5.endsWith(this.extension)) {
                            var5 = var5 + this.extension;
                        }

                        if (!this.fileNameValid(var5)) {
                            JOptionPane.showMessageDialog(this.jFrame, "Escriba un nombre válido para el archivo", "Nombre inválido", 2);
                            return false;
                        }

                        if (!var1.getName().endsWith(this.extension)) {
                            String var10002 = var1.getAbsolutePath();
                            var1 = new File(var10002 + this.extension);
                        }

                        if (!var1.exists()) {
                            this.saveFile(var1);
                        } else {
                            int var6 = JOptionPane.showConfirmDialog(this.jFrame, "Ya hay un archivo con este nombre, ¿desea sobreescribirlo?", "Sobreescribir archivo", 2);
                            if (var6 == 0) {
                                this.saveFile(var1);
                            }
                        }
                    }
                } else {
                    this.jTextComponent.setText("");
                    this.jFrame.setTitle(this.title);
                }
            }
        } else {
            this.jTextComponent.setText("");
            this.jFrame.setTitle(this.title);
        }

        return true;
    }

    private void saveFile(File var1) {
        boolean var2 = this.saveFile(var1, this.jTextComponent.getText());
        if (var2) {
            this.jFrame.setTitle(var1.getName());
        } else {
            JOptionPane.showMessageDialog(this.jFrame, "No se pudo guardar el archivo", "Error desconocido", 2);
        }

    }

    private boolean fileNameValid(String var1) {
        int var2 = var1.lastIndexOf(".");
        String var3 = var1.substring(0, var2);
        return !var3.replace(" ", "").equals("") && !var1.contains("\\") && !var1.contains("/") && !var1.contains(":") && !var1.contains("*") && !var1.contains("?") && !var1.contains("\"") && !var1.contains("<") && !var1.contains(">") && !var1.contains("|");
    }

    public void New() {
        this.file = this.selecFile.getSelectedFile();
        if (this.jFrame.getTitle().contains("*")) {
            if (this.saveEditNew(this.file, this.selecFile)) {
                this.jFrame.setTitle(this.title);
                this.jTextComponent.setText("");
                this.selecFile = new JFileChooser();
                this.file = null;
            }
        } else {
            this.jFrame.setTitle(this.title);
            this.jTextComponent.setText("");
            this.selecFile = new JFileChooser();
            this.file = null;
        }

    }

    public void Exit() {
        if (this.jFrame.getTitle().contains("*") && this.saveEditOpen(this.file, this.selecFile)) {
            this.selecFile = new JFileChooser();
            this.file = null;
        }

    }

    public boolean Open() {
        if (this.jFrame.getTitle().contains("*")) {
            this.saveEditOpen(this.file, this.selecFile);
        }

        JFileChooser var1 = new JFileChooser();
        var1.setFileFilter(filtro);
        if (var1.showDialog(this.jFrame, "Abrir") == 0) {
            File var2 = var1.getSelectedFile();
            String var3 = var2.getName();
            if (var3.endsWith(this.extension)) {
                if (this.fileNameValid(var3)) {
                    if (!var2.exists()) {
                        JOptionPane.showMessageDialog(this.jFrame, "El archivo que sea desea abrir no existe en el directorio especificado", "Archivo no encontrado", 2);
                    } else {
                        String var4 = this.getTextFile(var2);
                        if (var4 == null) {
                            JOptionPane.showMessageDialog(this.jFrame, "Error al leer el archivo", "Error desconocido", 2);
                            return false;
                        }

                        this.jTextComponent.setText(var4);
                        this.jFrame.setTitle(var2.getName());
                        this.selecFile = var1;
                        this.file = var2;
                        //this.tabs.setTitleAt(1, var2.getName());
                    }

                    this.jTextComponent.setCaretPosition(0);
                    return true;
                } else {
                    JOptionPane.showMessageDialog(this.jFrame, "Escriba un nombre válido para el archivo", "Nombre inválido", 2);
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(this.jFrame, "El archivo debe de tener la extensión '" + this.extension + "'", "Extensión inválida", 2);
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean Save() {
        if (this.file != null) {
            this.saveFile(this.file);
        } else {
            JFileChooser var1 = new JFileChooser();
            if (var1.showDialog(this.jFrame, "Guardar") != 0) {
                return false;
            }

            File var2 = var1.getSelectedFile();
            String var3 = var2.getName();
            if (!var3.endsWith(this.extension)) {
                var3 = var3 + this.extension;
            }

            if (!this.fileNameValid(var3)) {
                JOptionPane.showMessageDialog(this.jFrame, "Escriba un nombre válido para el archivo", "Nombre inválido", 2);
                this.selecFile = new JFileChooser();
                this.file = null;
                return false;
            }

            if (!var2.getName().endsWith(this.extension)) {
                String var10002 = var2.getAbsolutePath();
                var2 = new File(var10002 + this.extension);
            }

            if (!var2.exists()) {
                this.saveFile(var2);
                this.file = var2;
                this.selecFile = var1;
            } else {
                int var4 = JOptionPane.showConfirmDialog(this.jFrame, "Ya hay un archivo con este nombre, ¿desea sobreescribirlo?", "Sobreescribir archivo", 2);
                if (var4 != 0) {
                    this.selecFile = new JFileChooser();
                    this.file = null;
                    return false;
                }

                this.saveFile(var2);
                this.file = var2;
                this.selecFile = var1;
            }
        }

        return true;
    }

    public boolean SaveAs() {
        JFileChooser var1 = new JFileChooser();
        if (var1.showDialog(this.jFrame, "Guardar como") == 0) {
            File var2 = var1.getSelectedFile();
            String var3 = var2.getName();
            if (!var3.endsWith(this.extension)) {
                var3 = var3 + this.extension;
            }

            if (this.fileNameValid(var3)) {
                if (!var2.getName().endsWith(this.extension)) {
                    String var10002 = var2.getAbsolutePath();
                    var2 = new File(var10002 + this.extension);
                }

                this.saveFile(var2);
                this.file = var2;
                this.selecFile = var1;
                return true;
            } else {
                JOptionPane.showMessageDialog(this.jFrame, "Escriba un nombre válido para el archivo", "Nombre inválido", 2);
                return false;
            }
        } else {
            return false;
        }
    }
}
