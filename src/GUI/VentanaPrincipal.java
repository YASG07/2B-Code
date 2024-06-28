package GUI;

import AnalisisLexico.ErrorToken;
import AnalisisLexico.Lexer;
import AnalisisLexico.Token;
import AnalisisSintactico.Grammar;
import AnalisisSintactico.Production;
import com.formdev.flatlaf.icons.FlatTabbedPaneCloseIcon;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDeepOceanIJTheme;
import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.IntConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.fife.ui.rsyntaxtextarea.*;
import org.fife.ui.rtextarea.*;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatGradiantoDeepOceanIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatGrayIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatGruvboxDarkHardIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatHiberbeeDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatMaterialDesignDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatSolarizedDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatXcodeDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialLighterIJTheme;
import java.awt.Component;
import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import org.fife.ui.rsyntaxtextarea.folding.FoldParserManager;


public class VentanaPrincipal extends javax.swing.JFrame {

   public ArrayList<Token> ComponentesLexicos = null;
   public ArrayList<ErrorToken> errores = null;
   private boolean lineaColumnaIni;
   public ArrayList<Production> indpro;
   public ArrayList<Production> ciclofor;
   public ArrayList<Production> fuera;
   private HashMap<String, String> identificadores;
   private HashMap<String, String> NumEntero;
   private HashMap<String, String> Fuera;
   private ArrayList<Production> asigProdConID;
   private Directory directorio;
   int[] edad = {45,};

   public VentanaPrincipal() {
      initComponents();
       indpro = new ArrayList<>();
       ciclofor =new ArrayList<>();
       fuera =new ArrayList<>();
       
       identificadores = new HashMap<>();
       NumEntero = new HashMap<>();
       Fuera = new HashMap<>();
      setupTabTraversalKeys(panelContenedorPestañas);
      AbstractTokenMakerFactory atmf = (AbstractTokenMakerFactory) TokenMakerFactory.getDefaultInstance();
      atmf.putMapping("text/myLanguage", "Colores.Color");

      FoldParserManager.get().addFoldParserMapping("text/myLanguage", new Plegado());
      //Inicializacion de una ventana para el jTabbedPane con un RSyntaxTextArea
      RSyntax();
      //Para centrar la ventana
      setLocationRelativeTo(null);
      setTitle("RCB Compiler");

      //Para navegar por las pestañas con Ctrl+tab, pero no funciona correctamente aún
      setupTabTraversalKeys(panelContenedorPestañas);
      //Establecer icono para la ventana
      setIconImage(new FlatSVGIcon("assets/rcbcontrol.svg").getImage());

      pantalla.setVisible(false);
   }
   
   public void initialLineColumn() {
        this.lineaColumnaIni = true;
    }
   /**
 * Activa la visualización de líneas y columnas de inicio.
 */
/**
 * Desactiva la visualización de líneas y columnas de inicio.
 */
    public void finalLineColumn() {
        this.lineaColumnaIni = false;
    }

   @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ventanaLexico = new javax.swing.JFrame();
        contenedorBaseLexico = new javax.swing.JPanel();
        scrollLexico = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        ventanaTS = new javax.swing.JFrame();
        contenedorBaseTS = new javax.swing.JPanel();
        scrollTS = new javax.swing.JScrollPane();
        jTableTS = new javax.swing.JTable();
        ventanaTSFija = new javax.swing.JFrame();
        contenedorBaseTS1 = new javax.swing.JPanel();
        scrollTS1 = new javax.swing.JScrollPane();
        tablaIdentificadores = new javax.swing.JTable();
        ventanaTSIdFunciones = new javax.swing.JFrame();
        contenedorBaseTS2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaFunciones = new javax.swing.JTable();
        ventanaTSIdArreglos = new javax.swing.JFrame();
        contenedorBaseTS3 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaArreglos = new javax.swing.JTable();
        panelContenedorPrincipal = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        panelBaseIzquierdo = new javax.swing.JPanel();
        jSplitPane3 = new javax.swing.JSplitPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTree2 = new javax.swing.JTree();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        panelBaseDerecho = new javax.swing.JPanel();
        jSplitPane4 = new javax.swing.JSplitPane();
        panelBasePestañas = new javax.swing.JPanel();
        panelContenedorPestañas = new javax.swing.JTabbedPane();
        pantalla = new javax.swing.JInternalFrame();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtResultado = new javax.swing.JTextPane();
        jToolBar1 = new javax.swing.JToolBar();
        compilationButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuNuevo = new javax.swing.JMenuItem();
        menuAbrir = new javax.swing.JMenuItem();
        menuGuardar = new javax.swing.JMenuItem();
        menuGuardarComo = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuCerrar = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        menuSalir = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu9 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenu7 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();

        ventanaLexico.setTitle("Análisis Léxico");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Lexema", "Componente Léxico"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollLexico.setViewportView(jTable1);

        javax.swing.GroupLayout contenedorBaseLexicoLayout = new javax.swing.GroupLayout(contenedorBaseLexico);
        contenedorBaseLexico.setLayout(contenedorBaseLexicoLayout);
        contenedorBaseLexicoLayout.setHorizontalGroup(
            contenedorBaseLexicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollLexico, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
        );
        contenedorBaseLexicoLayout.setVerticalGroup(
            contenedorBaseLexicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollLexico, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
        );

        ventanaLexico.setSize(new java.awt.Dimension(400, 450));
        ventanaLexico.setLocationRelativeTo(null);
        ventanaLexico.setType(java.awt.Window.Type.POPUP);

        javax.swing.GroupLayout ventanaLexicoLayout = new javax.swing.GroupLayout(ventanaLexico.getContentPane());
        ventanaLexico.getContentPane().setLayout(ventanaLexicoLayout);
        ventanaLexicoLayout.setHorizontalGroup(
            ventanaLexicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ventanaLexicoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contenedorBaseLexico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        ventanaLexicoLayout.setVerticalGroup(
            ventanaLexicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ventanaLexicoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contenedorBaseLexico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        ventanaLexico.setIconImage(new FlatSVGIcon("assets/AnLEX.svg").getImage());

        ventanaTS.setTitle("Tabla de símbolos");

        jTableTS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Lexema", "Dirección", "Linea", "Columna"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollTS.setViewportView(jTableTS);
        if (jTableTS.getColumnModel().getColumnCount() > 0) {
            jTableTS.getColumnModel().getColumn(2).setHeaderValue("Linea");
            jTableTS.getColumnModel().getColumn(3).setHeaderValue("Columna");
        }

        javax.swing.GroupLayout contenedorBaseTSLayout = new javax.swing.GroupLayout(contenedorBaseTS);
        contenedorBaseTS.setLayout(contenedorBaseTSLayout);
        contenedorBaseTSLayout.setHorizontalGroup(
            contenedorBaseTSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollTS, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
        );
        contenedorBaseTSLayout.setVerticalGroup(
            contenedorBaseTSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollTS, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
        );

        ventanaTS.setSize(new java.awt.Dimension(436, 448));
        ventanaTS.setLocationRelativeTo(null);
        ventanaTS.setType(java.awt.Window.Type.POPUP);

        javax.swing.GroupLayout ventanaTSLayout = new javax.swing.GroupLayout(ventanaTS.getContentPane());
        ventanaTS.getContentPane().setLayout(ventanaTSLayout);
        ventanaTSLayout.setHorizontalGroup(
            ventanaTSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ventanaTSLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contenedorBaseTS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        ventanaTSLayout.setVerticalGroup(
            ventanaTSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ventanaTSLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contenedorBaseTS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        ventanaTS.setIconImage(new FlatSVGIcon("assets/TS.svg").getImage());

        ventanaTSFija.setTitle("Tabla Fija");

        tablaIdentificadores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "int"},
                {"2", "float"},
                {"3", "const"},
                {"4", "char"},
                {"5", "String"},
                {"6", "bool"},
                {"7", "true"},
                {"8", "false"},
                {"9", "Array"},
                {"10", "Color"},
                {"11", "Rect2"},
                {"12", "importAll"},
                {"13", "extends"},
                {"14", "Vector2"},
                {"15", "File"},
                {"16", "return"},
                {"17", "new"},
                {"18", "AABB"},
                {"19", "TimeSpan"},
                {"20", "Resource"},
                {"21", "Object"},
                {"22", "Start"},
                {"23", "PhysicsShape"},
                {"24", "in"},
                {"25", "class"},
                {"26", "range"},
                {"27", "void"},
                {"28", "print"},
                {"29", "PhysicsBody"},
                {"30", "func"},
                {"31", "Error"},
                {"32", "for"},
                {"33", "while"},
                {"34", "if"},
                {"35", "elif"},
                {"36", "else"},
                {"37", "break"}
            },
            new String [] {
                "ID", "Lexema"
            }
        ));
        tablaIdentificadores.setShowGrid(true);
        tablaIdentificadores.setSurrendersFocusOnKeystroke(true);
        scrollTS1.setViewportView(tablaIdentificadores);

        javax.swing.GroupLayout contenedorBaseTS1Layout = new javax.swing.GroupLayout(contenedorBaseTS1);
        contenedorBaseTS1.setLayout(contenedorBaseTS1Layout);
        contenedorBaseTS1Layout.setHorizontalGroup(
            contenedorBaseTS1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollTS1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
        );
        contenedorBaseTS1Layout.setVerticalGroup(
            contenedorBaseTS1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollTS1, javax.swing.GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)
        );

        ventanaTSFija.setSize(new java.awt.Dimension(250, 480));
        ventanaTSFija.setLocationRelativeTo(null);
        ventanaTSFija.setType(java.awt.Window.Type.POPUP);
        // Layout setup code - not shown here
        ventanaTSFija.setIconImage(new FlatSVGIcon("assets/TS.svg").getImage());

        javax.swing.GroupLayout ventanaTSFijaLayout = new javax.swing.GroupLayout(ventanaTSFija.getContentPane());
        ventanaTSFija.getContentPane().setLayout(ventanaTSFijaLayout);
        ventanaTSFijaLayout.setHorizontalGroup(
            ventanaTSFijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ventanaTSFijaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contenedorBaseTS1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        ventanaTSFijaLayout.setVerticalGroup(
            ventanaTSFijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ventanaTSFijaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contenedorBaseTS1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        ventanaTSIdFunciones.setTitle("Tabla de funciones");

        tablaFunciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Identificador", "Cantidad de parámetros", "Tipos de parámetros", "Dirección"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tablaFunciones);

        javax.swing.GroupLayout contenedorBaseTS2Layout = new javax.swing.GroupLayout(contenedorBaseTS2);
        contenedorBaseTS2.setLayout(contenedorBaseTS2Layout);
        contenedorBaseTS2Layout.setHorizontalGroup(
            contenedorBaseTS2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedorBaseTS2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                .addContainerGap())
        );
        contenedorBaseTS2Layout.setVerticalGroup(
            contenedorBaseTS2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedorBaseTS2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
                .addContainerGap())
        );

        ventanaTSIdFunciones.setSize(new java.awt.Dimension(500, 480));
        ventanaTSIdFunciones.setLocationRelativeTo(null);
        ventanaTSIdFunciones.setType(java.awt.Window.Type.POPUP);
        // Layout setup code - not shown here
        ventanaTSIdFunciones.setIconImage(new FlatSVGIcon("assets/TS.svg").getImage());

        javax.swing.GroupLayout ventanaTSIdFuncionesLayout = new javax.swing.GroupLayout(ventanaTSIdFunciones.getContentPane());
        ventanaTSIdFunciones.getContentPane().setLayout(ventanaTSIdFuncionesLayout);
        ventanaTSIdFuncionesLayout.setHorizontalGroup(
            ventanaTSIdFuncionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contenedorBaseTS2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ventanaTSIdFuncionesLayout.setVerticalGroup(
            ventanaTSIdFuncionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contenedorBaseTS2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        ventanaTSIdArreglos.setTitle("Tabla de arreglos");

        tablaArreglos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Dimension", "Tipo", "Elementos", "Direccion"
            }
        ));
        jScrollPane5.setViewportView(tablaArreglos);

        javax.swing.GroupLayout contenedorBaseTS3Layout = new javax.swing.GroupLayout(contenedorBaseTS3);
        contenedorBaseTS3.setLayout(contenedorBaseTS3Layout);
        contenedorBaseTS3Layout.setHorizontalGroup(
            contenedorBaseTS3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
        );
        contenedorBaseTS3Layout.setVerticalGroup(
            contenedorBaseTS3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE)
        );

        ventanaTSIdArreglos.setSize(new java.awt.Dimension(500, 480));
        ventanaTSIdArreglos.setLocationRelativeTo(null);
        ventanaTSIdArreglos.setType(java.awt.Window.Type.POPUP);
        // Layout setup code - not shown here
        ventanaTSIdArreglos.setIconImage(new FlatSVGIcon("assets/TS.svg").getImage());

        javax.swing.GroupLayout ventanaTSIdArreglosLayout = new javax.swing.GroupLayout(ventanaTSIdArreglos.getContentPane());
        ventanaTSIdArreglos.getContentPane().setLayout(ventanaTSIdArreglosLayout);
        ventanaTSIdArreglosLayout.setHorizontalGroup(
            ventanaTSIdArreglosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ventanaTSIdArreglosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contenedorBaseTS3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        ventanaTSIdArreglosLayout.setVerticalGroup(
            ventanaTSIdArreglosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ventanaTSIdArreglosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contenedorBaseTS3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Caja");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Sprite2D");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("blue");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("violet");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("red");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("yellow");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("CollisionShape2D");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("basketball");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("soccer");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("football");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("hockey");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        jTree2.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane2.setViewportView(jTree2);

        jTabbedPane2.addTab("Escenas", jScrollPane2);

        jSplitPane3.setLeftComponent(jTabbedPane2);

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(128, 200));

        treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("res://");
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("gráficos");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("blue");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("violet");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("red");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("yellow");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("scripts");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("basketball");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("soccer");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("football");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("hockey");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("iconos");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("hot dogs");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("pizza");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("ravioli");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("bananas");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane1.setViewportView(jTree1);

        jTabbedPane1.addTab("Sistema de archivos", jScrollPane1);

        jSplitPane3.setRightComponent(jTabbedPane1);

        javax.swing.GroupLayout panelBaseIzquierdoLayout = new javax.swing.GroupLayout(panelBaseIzquierdo);
        panelBaseIzquierdo.setLayout(panelBaseIzquierdoLayout);
        panelBaseIzquierdoLayout.setHorizontalGroup(
            panelBaseIzquierdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBaseIzquierdoLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jSplitPane3)
                .addGap(0, 0, 0))
        );
        panelBaseIzquierdoLayout.setVerticalGroup(
            panelBaseIzquierdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBaseIzquierdoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE))
        );

        jSplitPane2.setLeftComponent(panelBaseIzquierdo);

        jSplitPane4.setDividerSize(3);
        jSplitPane4.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane4.setResizeWeight(1.0);
        jSplitPane4.setFocusTraversalPolicyProvider(true);

        panelBasePestañas.setLayout(new java.awt.CardLayout());
        panelBasePestañas.add(panelContenedorPestañas, "card2");

        jSplitPane4.setLeftComponent(panelBasePestañas);

        pantalla.setBorder(null);
        pantalla.setClosable(true);
        pantalla.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        pantalla.setTitle("Output");
        pantalla.setFrameIcon(null);
        pantalla.setVisible(true);

        txtResultado.setEditable(false);
        txtResultado.setBorder(null);
        txtResultado.setMargin(new java.awt.Insets(0, 0, 0, 0));
        txtResultado.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                txtResultadoMouseWheelMoved(evt);
            }
        });
        jScrollPane3.setViewportView(txtResultado);

        javax.swing.GroupLayout pantallaLayout = new javax.swing.GroupLayout(pantalla.getContentPane());
        pantalla.getContentPane().setLayout(pantallaLayout);
        pantallaLayout.setHorizontalGroup(
            pantallaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pantallaLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane3)
                .addGap(0, 0, 0))
        );
        pantallaLayout.setVerticalGroup(
            pantallaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pantallaLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        jSplitPane4.setRightComponent(pantalla);
        try {
            pantalla.setIcon(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }

        javax.swing.GroupLayout panelBaseDerechoLayout = new javax.swing.GroupLayout(panelBaseDerecho);
        panelBaseDerecho.setLayout(panelBaseDerechoLayout);
        panelBaseDerechoLayout.setHorizontalGroup(
            panelBaseDerechoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBaseDerechoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 916, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelBaseDerechoLayout.setVerticalGroup(
            panelBaseDerechoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBaseDerechoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        jSplitPane2.setRightComponent(panelBaseDerecho);

        jToolBar1.setRollover(true);

        compilationButton.setText("Compilar");
        compilationButton.setFocusable(false);
        compilationButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        compilationButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        compilationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compilationButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(compilationButton);

        javax.swing.GroupLayout panelContenedorPrincipalLayout = new javax.swing.GroupLayout(panelContenedorPrincipal);
        panelContenedorPrincipal.setLayout(panelContenedorPrincipalLayout);
        panelContenedorPrincipalLayout.setHorizontalGroup(
            panelContenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContenedorPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane2))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelContenedorPrincipalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelContenedorPrincipalLayout.setVerticalGroup(
            panelContenedorPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelContenedorPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSplitPane2)
                .addContainerGap())
        );

        jMenu1.setText("Archivo");

        menuNuevo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuNuevo.setText("Nuevo");
        menuNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNuevoActionPerformed(evt);
            }
        });
        jMenu1.add(menuNuevo);

        menuAbrir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuAbrir.setText("Abrir...");
        menuAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAbrirActionPerformed(evt);
            }
        });
        jMenu1.add(menuAbrir);

        menuGuardar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuGuardar.setText("Guardar");
        menuGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGuardarActionPerformed(evt);
            }
        });
        jMenu1.add(menuGuardar);

        menuGuardarComo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuGuardarComo.setText("Guardar como...");
        menuGuardarComo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGuardarComoActionPerformed(evt);
            }
        });
        jMenu1.add(menuGuardarComo);
        jMenu1.add(jSeparator1);

        menuCerrar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuCerrar.setText("Cerrar");
        menuCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCerrarActionPerformed(evt);
            }
        });
        jMenu1.add(menuCerrar);
        jMenu1.add(jSeparator2);

        menuSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuSalir.setText("Salir");
        menuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSalirActionPerformed(evt);
            }
        });
        jMenu1.add(menuSalir);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Editar");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem2.setText("Deshacer");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem10.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem10.setText("Rehacer");
        jMenu2.add(jMenuItem10);
        jMenu2.add(jSeparator3);

        jMenuItem11.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem11.setText("Cortar");
        jMenu2.add(jMenuItem11);

        jMenuItem12.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem12.setText("Copiar");
        jMenu2.add(jMenuItem12);

        jMenuItem13.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem13.setText("Pegar");
        jMenu2.add(jMenuItem13);
        jMenu2.add(jSeparator4);

        jMenuItem14.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        jMenuItem14.setText("Eliminar");
        jMenu2.add(jMenuItem14);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Ver");

        jMenuItem9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem9.setText("jMenuItem9");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem9);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Fuente");

        jMenu8.setText("Ventana");

        jMenuItem5.setText("jMenuItem5");
        jMenu8.add(jMenuItem5);

        jMenuItem6.setText("jMenuItem6");
        jMenu8.add(jMenuItem6);

        jMenuItem7.setText("jMenuItem7");
        jMenu8.add(jMenuItem7);

        jMenu4.add(jMenu8);

        jMenu9.setText("Código");

        jMenuItem8.setText("jMenuItem8");
        jMenu9.add(jMenuItem8);

        jMenuItem16.setText("jMenuItem16");
        jMenu9.add(jMenuItem16);

        jMenuItem15.setText("jMenuItem15");
        jMenu9.add(jMenuItem15);

        jMenu4.add(jMenu9);

        jMenuBar1.add(jMenu4);

        jMenu5.setText("Opciones");
        jMenuBar1.add(jMenu5);

        jMenu7.setText("Ayuda");

        jMenu6.setText("Documentación");

        jMenuItem3.setText("Diseño de RCBScript");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem3);

        jMenu7.add(jMenu6);

        jMenuItem1.setText("Acerca de");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem1);

        jMenuBar1.add(jMenu7);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelContenedorPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelContenedorPrincipal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuGuardarComoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGuardarComoActionPerformed
       directorio.SaveAs();
    }//GEN-LAST:event_menuGuardarComoActionPerformed

   private static void setupTabTraversalKeys(JTabbedPane tabbedPane) {
      KeyStroke ctrlTab = KeyStroke.getKeyStroke("ctrl TAB");
      KeyStroke ctrlShiftTab = KeyStroke.getKeyStroke("ctrl shift TAB");

      // Remove ctrl-tab from normal focus traversal
      Set<AWTKeyStroke> forwardKeys = new HashSet<AWTKeyStroke>(tabbedPane.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
      forwardKeys.remove(ctrlTab);
      tabbedPane.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardKeys);

      // Remove ctrl-shift-tab from normal focus traversal
      Set<AWTKeyStroke> backwardKeys = new HashSet<AWTKeyStroke>(tabbedPane.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
      backwardKeys.remove(ctrlShiftTab);
      tabbedPane.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backwardKeys);

      // Add keys to the tab's input map
      InputMap inputMap = tabbedPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
      inputMap.put(ctrlTab, "navigateNext");
      inputMap.put(ctrlShiftTab, "navigatePrevious");
   }
    private void menuNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNuevoActionPerformed
       RSyntaxTextArea textArea = new RSyntaxTextArea(); // Crea una nueva instancia de RSyntaxTextArea
       RTextScrollPane scrollPane = new RTextScrollPane(textArea);
       scrollPane.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
          public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
             if (evt.isControlDown()) {
                int fontSize = textArea.getFont().getSize();
                fontSize += evt.getPreciseWheelRotation() > 0 ? -1 : 1;
                textArea.setFont(new Font(textArea.getFont().getName(), textArea.getFont().getStyle(), fontSize));
             }
          }
       });
       panelContenedorPestañas.add("Script", scrollPane);
       directorio = new Directory(this, textArea, "RCBBBB", ".rcb", panelContenedorPestañas);
       //textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_GO);

       textArea.setSyntaxEditingStyle("text/myLanguage");

       textArea.setCodeFoldingEnabled(true);
       textArea.setAntiAliasingEnabled(true);
       textArea.setAnimateBracketMatching(true);
       changeStyleViaThemeXml(textArea);
       textArea.setFont(new Font("Segoe UI", Font.BOLD, 18));
       /*CompletionProvider provider = createCompletionProvider();
        AutoCompletion ac = new AutoCompletion(provider);
        ac.install(textArea);*/
    }//GEN-LAST:event_menuNuevoActionPerformed


    private void menuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSalirActionPerformed
       dispose();
    }//GEN-LAST:event_menuSalirActionPerformed

    private void menuAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAbrirActionPerformed
       directorio.Open();
    }//GEN-LAST:event_menuAbrirActionPerformed

    private void menuCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCerrarActionPerformed
       if (panelContenedorPestañas.getTabCount() >= 0) {
          Object[] options = {"Sí", "No"};
          int option = JOptionPane.showOptionDialog(this, "¿Guardar antes de cerrar la pestaña?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
          if (option == 0) { // 0 representa "Sí" en el array de opciones
             directorio.Save();
             panelContenedorPestañas.removeTabAt(panelContenedorPestañas.getSelectedIndex());
          } else {
             panelContenedorPestañas.removeTabAt(panelContenedorPestañas.getSelectedIndex());
          }
       }
    }//GEN-LAST:event_menuCerrarActionPerformed

    private void menuGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGuardarActionPerformed
       directorio.Save();
    }//GEN-LAST:event_menuGuardarActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed

    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
       // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
       try {
          Desktop.getDesktop().browse(new URI("https://docs.google.com/document/d/1qeY6uYMQqamKzeFeyg6Z0P87JS65njK4om06n904f1Q/edit?usp=sharing"));
       } catch (IOException | URISyntaxException ex) {
       }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed

    }//GEN-LAST:event_jMenuItem2ActionPerformed

   private void txtResultadoMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_txtResultadoMouseWheelMoved
      if (evt.isControlDown()) { // Verifica si la tecla Ctrl está presionada
         int fontSize = txtResultado.getFont().getSize();
         fontSize += evt.getPreciseWheelRotation() > 0 ? -1 : 1; // Aumenta o disminuye según la dirección de la rueda del mouse
         txtResultado.setFont(new Font(txtResultado.getFont().getName(), txtResultado.getFont().getStyle(), fontSize));
      }
   }//GEN-LAST:event_txtResultadoMouseWheelMoved

   private void compilationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compilationButtonActionPerformed
            if (panelContenedorPestañas.getTabCount() > 0) {
         analisisLexico();
         analisisSintactico();
         analisisSemantico();
         mostrarErrores();
         clearFields();
      } else {
         pantalla.hide();
         return;
      }
      return;
   }//GEN-LAST:event_compilationButtonActionPerformed
   private void clearFields() {
        if(indpro !=null)
            indpro.clear();
        identificadores.clear();
       
    }

   private void changeStyleViaThemeXml(RSyntaxTextArea textArea) {
      try {
         Theme theme = Theme.load(new FileInputStream("byMe.xml"));
         theme.apply(textArea);
      } catch (IOException ioe) {
      }
   }

   private void RSyntax() {
      RSyntaxTextArea textArea = new RSyntaxTextArea();
      RTextScrollPane scrollPane = new RTextScrollPane(textArea);

      //METODO PARA HACER ZOOM AL CODIGO
      scrollPane.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
         public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
            if (evt.isControlDown()) {
               int fontSize = textArea.getFont().getSize();
               fontSize += evt.getPreciseWheelRotation() > 0 ? -1 : 1;
               textArea.setFont(new Font(textArea.getFont().getName(), textArea.getFont().getStyle(), fontSize));
            }
         }
      });
      panelContenedorPestañas.add("Script", scrollPane);
      directorio = new Directory(this, textArea, "RCBBBB", ".rcb");
      //textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_GO);

      textArea.setSyntaxEditingStyle("text/myLanguage");
      textArea.setCodeFoldingEnabled(true); //Para contraer partes del codigo

      textArea.setAntiAliasingEnabled(true);
      textArea.setAnimateBracketMatching(true);
      changeStyleViaThemeXml(textArea);
      textArea.setFont(new Font("Segoe UI", Font.BOLD, 18));

      panelContenedorPestañas.putClientProperty("JTabbedPane.tabClosable", true);
      panelContenedorPestañas.putClientProperty("JTabbedPane.tabCloseCallback", (IntConsumer) tabIndex -> {
         if (tabIndex >= 0) {
            Object[] options = {"Sí", "No"};
            int option = JOptionPane.showOptionDialog(this, "¿Desea guardar antes de cerrar la pestaña?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (option == 0) { // 0 representa "Sí" en el array de opciones
               directorio.Save();
               panelContenedorPestañas.removeTabAt(tabIndex);
            } else {
               panelContenedorPestañas.removeTabAt(tabIndex);
            }
         }
      });

   }

   public static void main(String args[]) {
      try {
         FlatGradiantoDeepOceanIJTheme.setup();
         //Boton circular Cerrar pestaña 
         UIManager.put("TabbedPane.closeArc", Integer.valueOf(999));
         UIManager.put("TabbedPane.closeCrossFilledSize", Float.valueOf(5.5F));
         UIManager.put("TabbedPane.closeIcon", new FlatTabbedPaneCloseIcon());

         //Estilo underlined para los menuItems
         UIManager.put("MenuItem.selectionType", "underline");

         //Botones redondeados
         UIManager.put("Button.arc", 999);

         //Grosor de los bordes de focus de los componentes
         UIManager.put("Component.focusWidth", 0);
         UIManager.put("Component.innerFocusWidth", 0);

         //Barra de scroll redondeada
         UIManager.put("ScrollBar.thumbArc", 999);
         UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));

      } catch (Exception e) {
      }
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
            new VentanaPrincipal().setVisible(true);
         }
      });

   }

   private void analisisLexico() {
// Se inicializa una cadena de texto vacía para almacenar el contenido del JTextArea
      String texto = "";
// Se obtiene el índice del panel activo actualmente seleccionado en un contenedor de pestañas
      int indicePanelActivo = panelContenedorPestañas.getSelectedIndex(); 
// Se verifica si hay algún panel activo seleccionado     
      if (indicePanelActivo != -1) { 
// Se obtiene el componente en el panel activo seleccionado
         Component componente = panelContenedorPestañas.getComponentAt(indicePanelActivo); 
// Se comprueba si el componente es un JScrollPane       
         if (componente instanceof JScrollPane) { 
// Se convierte el componente en un JScrollPane
            JScrollPane scrollPane = (JScrollPane) componente; 
// Se obtiene el componente interno del JScrollPane
            Component componenteInterno = scrollPane.getViewport().getView(); 
// Se verifica si el componente interno es un JTextArea
            if (componenteInterno instanceof JTextArea) {
// Se convierte el componente interno en un JTextArea
                JTextArea textArea = (JTextArea) componenteInterno; 
// Se obtiene el texto del JTextArea y se concatena al contenido de la cadena 'texto'
               texto += textArea.getText(); 

            }
         }
      }
// Limpieza de las tablas 'tablaFunciones' y 'tablaArreglos' mediante el método 'vaciarTabla()'
      DefaultTableModel TablaFunciones = (DefaultTableModel) tablaFunciones.getModel();
      vaciarTabla(TablaFunciones, tablaFunciones);
      DefaultTableModel TablaArreglos = (DefaultTableModel) tablaArreglos.getModel();
      vaciarTabla(TablaArreglos, tablaArreglos);
      
// Se crea un archivo llamado "codigo.txt" y se escribe en él el contenido del JTextArea
      File archivo = new File("codigo.txt");
      PrintWriter escribir;
      DefaultTableModel Tabla = (DefaultTableModel) jTable1.getModel();
      vaciarTabla(Tabla, jTable1);
      try {
         escribir = new PrintWriter(archivo);
         escribir.print(texto);
         escribir.close();
      } catch (FileNotFoundException ex) {
         Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
      }

      try {
// Se crea un lector de archivos para leer desde "codigo.txt"
         Reader lector = new BufferedReader(new FileReader("codigo.txt"));
// Se pasa el lector al analizador léxico (Lexer)
         Lexer lexer = new Lexer(lector);
// Se inicializa el color del texto en el resultado a verde

         txtResultado.setForeground(Color.green);

         Object[] row = new Object[2];

         jSplitPane4.setDividerLocation(0.75);

         pantalla.setVisible(true);
         ventanaLexico.setVisible(true);
// Se preparan las estructuras para mostrar la ventana de componentes léxicos y la tabla de símbolos
         while (true) {
            // Se llama al método 'lexer.yylex()' para obtener el siguiente token
            if (lexer.yylex() == null) {
                // Cuando 'lexer.yylex()' devuelve 'null', se han analizado todos los tokens del archivo
               
                // Se obtienen los tokens y errores del analizador léxico
               ComponentesLexicos = lexer.tablaToken.getTokens();
               errores = lexer.tablaError.getErrores();
// Se llama a los métodos 'llenarTSIdentificadores()' y 'llenarTabla()' para llenar la tabla de símbolos y la tabla de componentes léxicos

               llenarTSIdentificadores();
               llenarTabla();
               
               return;
            }
         }
      } catch (FileNotFoundException ex) {
         Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException ex) {
         Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
      }
   }


   private void analisisSintactico() {

      Grammar gramatica = new Grammar(ComponentesLexicos, errores);
  
      gramatica.group("ASIG_VARIABLE", "Identificador (signoMenosIgual | signoMasIgual | signoModIgual | signoPorIgual | signoEntreIgual ) (((NumEntero | NumFlotante | CadChar | CadenaCaracteres | TRUE | FALSE ) | Identificador) | (((NumEntero | NumFlotante | CadChar | CadenaCaracteres | TRUE | FALSE ) | Identificador) ((signoSUMA|signoMUL|signoDIV|signoMOD|signoRESTA) (VALOR | Identificador))+) | ( PAREN_A ((NumEntero | NumFlotante | CadChar | CadenaCaracteres | TRUE | FALSE ) | Identificador) ((signoSUMA|signoMUL|signoDIV|signoMOD|signoRESTA) ((NumEntero | NumFlotante | CadChar | CadenaCaracteres | TRUE | FALSE ) | Identificador))+ PAREN_C) )+");
//GRAMATICA DE LA ESTRUCTURA PARA IMPRIMIR EN RCBScript                                                                                                                           
      gramatica.group("DECLARACION_PRINT", "PRINT PAREN_A (((NumEntero | NumFlotante | CadChar | CadenaCaracteres | TRUE | FALSE )|Identificador) (COMA ((NumEntero | NumFlotante | CadChar | CadenaCaracteres | TRUE | FALSE )|Identificador)+)?)? PAREN_C");
      gramatica.group("ERROR_PRINT", "PRINT  (((NumEntero | NumFlotante | CadChar | CadenaCaracteres | TRUE | FALSE )|Identificador) (COMA ((NumEntero | NumFlotante | CadChar | CadenaCaracteres | TRUE | FALSE )|Identificador)+)?)? PAREN_C",16,"Falta parentesis de apertura");
      gramatica.group("ERROR_PRINT", "PRINT  PAREN_A (((NumEntero | NumFlotante | CadChar | CadenaCaracteres | TRUE | FALSE )|Identificador) (COMA ((NumEntero | NumFlotante | CadChar | CadenaCaracteres | TRUE | FALSE )|Identificador)+)?)? ",17,"Falta parentesis de cierre");
      gramatica.group("ERROR_PRINT", "PRINT  (((NumEntero | NumFlotante | CadChar | CadenaCaracteres | TRUE | FALSE )|Identificador) (COMA ((NumEntero | NumFlotante | CadChar | CadenaCaracteres | TRUE | FALSE )|Identificador)+)?)? ",17,"Falta parentesis de apertura y cierre");

      
//GRAMATICA PARA DECLARAR VARIABLE EN RCBScript                                                                                                                           
      gramatica.group("DECLARACION_VARIABLE", "(CONSTANTE)? TIPO_DATO Identificador OpAsignacion (NumEntero | NumFlotante | CadChar | CadenaCaracteres | TRUE | FALSE ) | (CONSTANTE)? TIPO_DATO Identificador | (CONSTANTE)? TIPO_DATO Identificador OpAsignacion Identificador | DECLARACION_ARREGLO | DECLARACION_RECT2 | DECLARACION_VECTOR2 | DECLARACION_RESOURCE  ",indpro);
            gramatica.group("ERROR_DECVAR", "(CONSTANTE Identificador | (CONSTANTE)? Identificador OpAsignacion (NumEntero | NumFlotante | CadChar | CadenaCaracteres | TRUE | FALSE ))", 3, "Se esperaba un tipo de dato en la declaración");
            gramatica.group("ERROR_DECVAR", "CONSTANTE OpAsignacion", 3, "Falta tipo de dato, identificador y el valor");
            gramatica.group("ERROR_DECVAR", "CONSTANTE TIPO_DATO OpAsignacion", 3, "Falta identificador y el valor");
            gramatica.group("ERROR_DECVAR", "(CONSTANTE)? TIPO_DATO (NumEntero | NumFlotante | CadChar | CadenaCaracteres | TRUE | FALSE )", 3, "Se esperaba un identificador y un operador de asignacion en la declaración");
            gramatica.group("ERROR_DECVAR", "(CONSTANTE)? TIPO_DATO Identificador (NumEntero | NumFlotante | CadChar | CadenaCaracteres | TRUE | FALSE )", 3, "Se esperaba un operador de asignacion en la declaración");
            gramatica.group("ERROR_DECVAR", "(CONSTANTE)? TIPO_DATO | (CONSTANTE)? TIPO_DATO OpAsignacion (NumEntero | NumFlotante | CadChar | CadenaCaracteres | TRUE | FALSE )", 3, "Se esperaba un identificador en la declaración");
            gramatica.group("ERROR_DECVAR", "(CONSTANTE)? TIPO_DATO Identificador OpAsignacion", 3, "Se esperaba un valor en la declaración");
            gramatica.group("ERROR_DECVAR", "OpAsignacion", 4, "Se esperaba una declaración de variable");

//gramatica.group("ERROR_DECVAR", "(NumEntero | NumFlotante | CadChar | CadenaCaracteres | TRUE | FALSE )", 3, "Se esperaba una declaración de variable");
        //Caso while(true){}, while(false){}, while(identificador){} 
    gramatica.group("CICLO_WHILE",       "(WHILE PAREN_A (TRUE|FALSE|Identificador) PAREN_C LLAVE_A (START | DECLARACION_VARIABLE | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)? LLAVE_C)" );
            gramatica.group("ERROR_WHILE", "(PAREN_A (TRUE|FALSE|Identificador) PAREN_C LLAVE_A (START | DECLARACION_VARIABLE | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)? LLAVE_C)", 4, "Falta palabra reservada WHILE");
            gramatica.group("ERROR_WHILE", "(WHILE (TRUE|FALSE|Identificador) PAREN_C LLAVE_A (START | DECLARACION_VARIABLE | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)? LLAVE_C)", 4, "Falta parentesis de apertura");
            gramatica.group("ERROR_WHILE", "(WHILE PAREN_A PAREN_C LLAVE_A (START | DECLARACION_VARIABLE | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)? LLAVE_C)", 4, "Falta definir condicional, true, false o identificador");
            gramatica.group("ERROR_WHILE", "(WHILE PAREN_A(TRUE|FALSE|Identificador) LLAVE_A (START | DECLARACION_VARIABLE | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)? LLAVE_C)", 4, "Falta parentesis de cerrado");
            gramatica.group("ERROR_WHILE", "(WHILE PAREN_A(TRUE|FALSE|Identificador) PAREN_C (START | DECLARACION_VARIABLE | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)? LLAVE_C)", 4, "Falta llave de apertura");
            gramatica.group("ERROR_WHILE", "(WHILE PAREN_A(TRUE|FALSE|Identificador) PAREN_C LLAVE_A (START | DECLARACION_VARIABLE | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)? )", 4, "Falta llave de cerrado");
            gramatica.group("ERROR_WHILE", "(WHILE PAREN_A(TRUE|FALSE|Identificador) PAREN_C (START | DECLARACION_VARIABLE | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)? )", 4, "Falta llaves de apertura y cerrado");

              

        //Caso while(identificador){}
        //while(identificador ==identificador) d<=e
   /*      + "| (WHILE PAREN_A (Identificador (signoIgualIgual|signoMenorIgual|signoMayorIgual|signoNotIgual|signoMenor|signoMayor)(Identificador))PAREN_C LLAVE_A (START | DECLARACION_VARIABLE | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)? LLAVE_C)"
     //XXXXX ERROR while(identificador ==identificador) d<=e || DZ>=E && d<=yd &&                                                                          
        + "| (WHILE PAREN_A ((Identificador (signoIgualIgual|signoMenorIgual|signoMayorIgual|signoNotIgual|signoMenor|signoMayor)(Identificador)(OpLogicoAND|OpLogicoOR|OpLogicoNOT)?)+)PAREN_C LLAVE_A (START | DECLARACION_VARIABLE | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)? LLAVE_C) "
        //while(identificador ==true)
        //while(identificador ==false)        
         + "| (WHILE PAREN_A (Identificador signoIgualIgual(TRUE|FALSE))PAREN_C LLAVE_A (START | DECLARACION_VARIABLE | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)? LLAVE_C) "
        //while(identificador ==false && identificador ==true)        
         + "| (WHILE PAREN_A ((Identificador signoIgualIgual(TRUE|FALSE)(OpLogicoAND|OpLogicoOR|OpLogicoNOT)?)+) PAREN_C LLAVE_A (START | DECLARACION_VARIABLE | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)? LLAVE_C) "
        //while(identificador ==5 && identificador ==13)        
        + "| (WHILE PAREN_A ((Identificador (signoIgualIgual|signoMenorIgual|signoMayorIgual|signoNotIgual|signoMenor|signoMayor)(NumEntero|NumFlotante|CadChar|CadenaCaracteres)(OpLogicoAND|OpLogicoOR|OpLogicoNOT)?)+)PAREN_C LLAVE_A (START | DECLARACION_VARIABLE | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)? LLAVE_C) "
         
//XXXX while(identificar())
         + "| (WHILE PAREN_A (Identificador PAREN_A (((NumEntero | NumFlotante | CadChar | CadenaCaracteres |"
                + " TRUE | FALSE ) |Identificador) (COMA ((NumEntero | NumFlotante | CadChar | CadenaCaracteres | TRUE | FALSE )|Identificador)+)?)? PAREN_C PAREN_C LLAVE_A (START | DECLARACION_VARIABLE | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)? LLAVE_C) "
*/
    
     // gramatica.group("FUNCION", "(VOID)? FUNC Identificador PAREN_A ((DECLARACION_VARIABLE) ((COMA DECLARACION_VARIABLE)+)?)? PAREN_C");      

/*
      gramatica.group("CICLO_WHILE", "(WHILE PAREN_A (((Identificador |TRUE|FALSE) "
              + "(((OpLogicoOR | OpLogicoAND)(Identificador ) )+)?) | PAREN_A (Identificador  "
              + "|TRUE|FALSE) PAREN_C) "
              + "PAREN_C LLAVE_A (START|(DECLARACION_VARIABLE | CICLO_FOR "
              + "| DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)+)? LLAVE_C) "
              + "| "
              + "(WHILE (((Identificador |TRUE|FALSE) (((OpLogicoOR | OpLogicoAND)(Identificador ) )+)?) | "
              + "PAREN_A (Identificador  |TRUE|FALSE) PAREN_C) LLAVE_A (START|(DECLARACION_VARIABLE | CICLO_FOR |"
              + " DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)+)? LLAVE_C)");
  */    
//GRAMATICA DE LA ESTRUCTURA DE CONTROL IF EN RCBScript
     
             gramatica.group("DECLARACION_IF", "(( IF PAREN_A ((Identificador | NumEntero | NumFlotante)(OpComparacion|OpLogico) (Identificador | NumEntero | NumFlotante)) PAREN_C LLAVE_A (START|((DECLARACION_VARIABLE | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)+))? LLAVE_C) |"
                     + "( IF PAREN_A CONDICION PAREN_C LLAVE_A (START|((DECLARACION_VARIABLE | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)+))? LLAVE_C ELSE LLAVE_A (START|((DECLARACION_VARIABLE | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)+))? LLAVE_C))");
             gramatica.group("ERROR_IF", "( IF PAREN_A ((Identificador | NumEntero | NumFlotante)(OpComparacion|OpLogico)) PAREN_C LLAVE_A (START|((DECLARACION_VARIABLE | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)+))? LLAVE_C) ",11,"Se esperaba valor de comparacion");
             gramatica.group("ERROR_IF", "( IF PAREN_A ((Identificador | NumEntero | NumFlotante) (Identificador | NumEntero | NumFlotante)) PAREN_C LLAVE_A (START|((DECLARACION_VARIABLE | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)+))? LLAVE_C) ",11,"No se encontro operador de comparacion/logico");

             gramatica.group("ERROR_IF", "(  PAREN_A ((Identificador | NumEntero | NumFlotante)(OpComparacion|OpLogico) (Identificador | NumEntero | NumFlotante)) PAREN_C LLAVE_A (START|((DECLARACION_VARIABLE | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)+))? LLAVE_C) ",11,"No se encontro palabra reservada IF");
             gramatica.group("ERROR_IF", "( IF ((Identificador | NumEntero | NumFlotante)(OpComparacion|OpLogico) (Identificador | NumEntero | NumFlotante)) PAREN_C LLAVE_A (START|((DECLARACION_VARIABLE | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)+))? LLAVE_C) ",11,"Falta parentesis de apertura");
             gramatica.group("ERROR_IF", "( IF PAREN_A((Identificador | NumEntero | NumFlotante)(OpComparacion|OpLogico) (Identificador | NumEntero | NumFlotante)) LLAVE_A (START|((DECLARACION_VARIABLE | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)+))? LLAVE_C) ",11,"Falta parentesis de cerrado");
             gramatica.group("ERROR_IF", "( IF PAREN_A ((Identificador | NumEntero | NumFlotante)(OpComparacion|OpLogico) (Identificador | NumEntero | NumFlotante)) PAREN_C (START|((DECLARACION_VARIABLE | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)+))? LLAVE_C) ",11,"Falta llave de apertura");
             gramatica.group("ERROR_IF", "( IF PAREN_A ((Identificador | NumEntero | NumFlotante)(OpComparacion|OpLogico) (Identificador | NumEntero | NumFlotante)) PAREN_C LLAVE_A (START|((DECLARACION_VARIABLE | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)+))?) ",11,"Falta llave de cerrado");

             gramatica.group("ERROR_IF", "( IF PAREN_A ((OpComparacion|OpLogico) (Identificador | NumEntero | NumFlotante)) PAREN_C LLAVE_A (START|((DECLARACION_VARIABLE | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)+))? LLAVE_C) ",11,"Se esperaba valor de comparacion");

             gramatica.group("ERROR_IF", "( IF PAREN_A ((Identificador | NumEntero | NumFlotante)(OpAsignacion) (Identificador | NumEntero | NumFlotante)) PAREN_C LLAVE_A (START|((DECLARACION_VARIABLE | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)+))? LLAVE_C) ",11,"Operador Ilegal");

             
             gramatica.group("ERROR_IF", "( IF PAREN_A PAREN_C )",11,"Se esperaba una condicion dentro del ( )");

             gramatica.group("ERROR_IF", "( IF  PAREN_A CONDICION )",13,"Falta el parentesis de cierre ");
//GRAMATICA DEL CICLO FOR EN RCBScript

      gramatica.group("CICLO_FOR", "FOR Identificador IN RANGE (NumEntero ) LLAVE_A ((DECLARACION_VARIABLE | ERROR_DECVAR | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)+)? LLAVE_C",ciclofor);
      gramatica.group("ERROR_CICLO_FOR", "Identificador IN RANGE (NumEntero ) LLAVE_A ((DECLARACION_VARIABLE | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)+)? LLAVE_C",70,"Falta palabra reservada FOR");
      gramatica.group("ERROR_CICLO_FOR", "FOR IN RANGE (NumEntero ) LLAVE_A ((DECLARACION_VARIABLE | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)+)? LLAVE_C",70,"Falta el identificador del ciclo FOR");
      gramatica.group("ERROR_CICLO_FOR", "FOR Identificador RANGE (NumEntero ) LLAVE_A ((DECLARACION_VARIABLE | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)+)? LLAVE_C",71,"Falta la palabra reservada IN");
      gramatica.group("ERROR_CICLO_FOR", "FOR Identificador IN (NumEntero) LLAVE_A ((DECLARACION_VARIABLE | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)+)? LLAVE_C",72,"Falta la palabra reservada RANGE");
      gramatica.group("ERROR_CICLO_FOR", "FOR Identificador IN RANGE LLAVE_A ((DECLARACION_VARIABLE | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)+)? LLAVE_C",73,"Falta definir el rango del ciclo for");
      gramatica.group("ERROR_CICLO_FOR", "FOR Identificador IN RANGE (NumEntero ) ((DECLARACION_VARIABLE | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)+)? LLAVE_C",74,"Falta la llave de apertura del ciclo FOR");
      gramatica.group("ERROR_CICLO_FOR", "FOR Identificador IN RANGE (NumEntero ) LLAVE_A ((DECLARACION_VARIABLE | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)+)?",75,"Falta la llave de cierre del ciclo For");

//GRAMATICA DE UNA FUNCION EN RCBScript

      gramatica.group("DECLARACION_FUNCION", "(VOID)? FUNC Identificador PAREN_A ((DECLARACION_VARIABLE | ERROR_DECVAR) ((COMA (DECLARACION_VARIABLE | ERROR_DECVAR))+)?)? PAREN_C LLAVE_A (START|((DECLARACION_VARIABLE | ERROR_DECVAR | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ERROR_PRINT | ASIG_VARIABLE)+))? LLAVE_C");      
      gramatica.group("ERROR_DECLARACION_FUNCION", "(VOID)? FUNC Identificador PAREN_A((DECLARACION_VARIABLE | ERROR_DECVAR) ((COMA (DECLARACION_VARIABLE | ERROR_DECVAR))+)?)? PAREN_C (START|((DECLARACION_VARIABLE | ERROR_DECVAR | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ERROR_PRINT | ASIG_VARIABLE)+))? LLAVE_C", 69, "Falto llave de apertura");
/*-*/ gramatica.group("ERROR_DECLARACION_FUNCION", "(VOID)? FUNC Identificador PAREN_A((DECLARACION_VARIABLE | ERROR_DECVAR) ((COMA (DECLARACION_VARIABLE | | ERROR_DECVAR))+)?)? (START|((DECLARACION_VARIABLE | ERROR_DECVAR | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ERROR_PRINT | ASIG_VARIABLE)+))? ", 69, "Falto parentesis de cerrado y llave de apertura");
      gramatica.group("ERROR_DECLARACION_FUNCION", "(VOID)? Identificador PAREN_A((DECLARACION_VARIABLE | ERROR_DECVAR) ((COMA (DECLARACION_VARIABLE| ERROR_DECVAR))+)?)? PAREN_C (START|((DECLARACION_VARIABLE | ERROR_DECVAR | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ERROR_PRINT | ASIG_VARIABLE)+))? LLAVE_C", 69, "Falto palabra reservada FUNC y llave de apertura");
      gramatica.group("ERROR_DECLARACION_FUNCION", "(VOID)? FUNC Identificador PAREN_A((DECLARACION_VARIABLE | ERROR_DECVAR) ((COMA (DECLARACION_VARIABLE | ERROR_DECVAR))+)?)? PAREN_C LLAVE_A (START|((DECLARACION_VARIABLE | ERROR_DECVAR | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ERROR_PRINT | ASIG_VARIABLE)+))?", 69, "Falto llave de cerrado");
      gramatica.group("ERROR_DECLARACION_FUNCION", "(VOID)? FUNC Identificador PAREN_A((DECLARACION_VARIABLE | ERROR_DECVAR) ((COMA (DECLARACION_VARIABLE | ERROR_DECVAR))+)?)? PAREN_C (START|((DECLARACION_VARIABLE | ERROR_DECVAR | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ERROR_PRINT | ASIG_VARIABLE)+))?", 69, "Falto llave de apertura y cerrado");
      gramatica.group("ERROR_DECLARACION_FUNCION", "(VOID)? Identificador PAREN_A ((DECLARACION_VARIABLE | ERROR_DECVAR) ((COMA (DECLARACION_VARIABLE | ERROR_DECVAR))+)?)? PAREN_C LLAVE_A (START|((DECLARACION_VARIABLE | ERROR_DECVAR | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ERROR_PRINT | ASIG_VARIABLE)+))? LLAVE_C", 69, "Falto palabra reservada FUNC");
      gramatica.group("ERROR_DECLARACION_FUNCION", "(VOID)? Identificador PAREN_A ((DECLARACION_VARIABLE | ERROR_DECVAR) ((COMA (DECLARACION_VARIABLE | ERROR_DECVAR))+)?)? PAREN_C LLAVE_A (START|((DECLARACION_VARIABLE | ERROR_DECVAR | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ERROR_PRINT | ASIG_VARIABLE)+))?", 69, "Falto palabra reservada FUNC y llave de cerrado");
      gramatica.group("ERROR_DECLARACION_FUNCION", "(VOID)? FUNC Identificador ((DECLARACION_VARIABLE | ERROR_DECVAR) ((COMA (DECLARACION_VARIABLE | ERROR_DECVAR))+)?)? PAREN_C LLAVE_A (START|((DECLARACION_VARIABLE | ERROR_DECVAR | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ERROR_PRINT | ASIG_VARIABLE)+))? LLAVE_C", 69, "Falto parentesis de apertura");
      gramatica.group("ERROR_DECLARACION_FUNCION", "(VOID)? FUNC Identificador PAREN_A((DECLARACION_VARIABLE | ERROR_DECVAR) ((COMA (DECLARACION_VARIABLE| ERROR_DECVAR))+)?)? LLAVE_A (START|((DECLARACION_VARIABLE | ERROR_DECVAR | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ERROR_PRINT | ASIG_VARIABLE)+))? LLAVE_C", 69, "Falto parentesis de cerrado");
      gramatica.group("ERROR_DECLARACION_FUNCION", "(VOID)? FUNC Identificador ((DECLARACION_VARIABLE | ERROR_DECVAR) ((COMA (DECLARACION_VARIABLE| ERROR_DECVAR))+)?)? LLAVE_A (START|((DECLARACION_VARIABLE | ERROR_DECVAR | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ERROR_PRINT | ASIG_VARIABLE)+))? LLAVE_C", 69, "Falto parentesis de apertura y cerrado");
      gramatica.group("ERROR_DECLARACION_FUNCION", "(VOID)? Identificador PAREN_A((DECLARACION_VARIABLE | ERROR_DECVAR) ((COMA (DECLARACION_VARIABLE| ERROR_DECVAR))+)?)?PAREN_C LLAVE_A (START|((DECLARACION_VARIABLE | ERROR_DECVAR | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ERROR_PRINT | ASIG_VARIABLE)+))? LLAVE_C", 69, "Falto palabra reservada FUNC, llave de apertura y cerrado");
      gramatica.group("ERROR_DECLARACION_FUNCION", "(VOID)? FUNC Identificador PAREN_A ((DECLARACION_VARIABLE | ERROR_DECVAR) ((COMA )+)?)? PAREN_C LLAVE_A (START|((DECLARACION_VARIABLE | CICLO_FOR | DECLARACION_FUNCION | ERROR_DECVAR | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ERROR_PRINT | ASIG_VARIABLE)+))? LLAVE_C", 69, "Se detectecto una coma falta declaracion de variable");
      gramatica.group("ERROR_DECLARACION_FUNCION", "(VOID)? FUNC PAREN_A ((DECLARACION_VARIABLE| ERROR_DECVAR) ((COMA (DECLARACION_VARIABLE| ERROR_DECVAR))+)?)? PAREN_C LLAVE_A (START|((DECLARACION_VARIABLE | ERROR_DECVAR | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ERROR_PRINT | ASIG_VARIABLE)+))? LLAVE_C", 69, "No se encontro el identificador de la función");
      gramatica.group("ERROR_DECLARACION_FUNCION", "(VOID)? FUNC Identificador PAREN_A ((DECLARACION_VARIABLE) ((COMA DECLARACION_VARIABLE | ERROR_DECVAR)+)?)? PAREN_C LLAVE_A (START|((DECLARACION_VARIABLE | ERROR_DECVAR | CICLO_FOR | DECLARACION_FUNCION | CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ERROR_PRINT | ASIG_VARIABLE)+))? LLAVE_C", 69, "No se encontro el identificador de la función");

            
//GRAMATICA DE SENTENCIAS EN RCBScript
      gramatica.group("SENTENCIAS", "(DECLARACION_VARIABLE |  CICLO_FOR | DECLARACION_IF | ERROR_IF |ERROR_CICLO_FOR |ERROR_DECVAR | ERROR_WHILE | ERROR_DECLARACION_FUNCION |DECLARACION_FUNCION| CICLO_WHILE | DECLARACION_IF | DECLARACION_PRINT | ASIG_VARIABLE)+");            
//GRAMATICA DE INICIO EN RCBScript
      gramatica.group("INICIO", "(IMPORT)? CLASS Identificador (EXTENDS Identificador)? LLAVE_A (SENTENCIAS)? LLAVE_C",fuera);        
      gramatica.group("ERROR_INICIO", "(IMPORT)? CLASS Identificador (EXTENDS Identificador)? LLAVE_A (SENTENCIAS)?", 1, "Llave de cerrado de clase principal no detectada");
            gramatica.group("ERROR_INICIO", "(IMPORT)? CLASS (EXTENDS Identificador)? LLAVE_A (SENTENCIAS)? LLAVE_C", 1, "inicio ilegal no se declaró el identificador de la clase");
            gramatica.group("ERROR_INICIO", "(IMPORT)? CLASS Identificador (EXTENDS)? LLAVE_A (SENTENCIAS)? LLAVE_C", 1, "No se indico la clase que se hereda");
            gramatica.group("ERROR_INICIO", "(IMPORT)? Identificador (EXTENDS Identificador)? LLAVE_A (SENTENCIAS)? LLAVE_C", 1, "Inicio ilegal no se declaro la palabra reservada CLASS");
            gramatica.group("ERROR_INICIO", "(IMPORT)? CLASS Identificador (EXTENDS Identificador)? (SENTENCIAS)? LLAVE_C", 1, "Llave de apertura de clase principal no detectada");
            gramatica.group("ERROR_INICIO", "(IMPORT)? CLASS Identificador (EXTENDS Identificador)? LLAVE_A (SENTENCIAS)?", 1, "Llave de cerrado de clase principal no detectada");
            gramatica.group("ERROR_INICIO", "(IMPORT)? CLASS Identificador (EXTENDS Identificador)?", 1, "Bloque de codigo no detectado ");
            gramatica.group("ERROR_INICIO", "(IMPORT)? CLASS (SENTENCIAS)? LLAVE_C ", 1, "inicio ilegal no se declaró el identificador ni la llave de apertura");
            gramatica.group("ERROR_INICIO", "(IMPORT)? LLAVE_A (SENTENCIAS)? LLAVE_C ", 1, "inicio ilegal no se declaró la clase ni su identificador");
            gramatica.group("ERROR_INICIO", "(IMPORT)? CLASS Identificador EXTENDS", 1, "inicio ilegal falta bloque de codigo y el identificador de la clase a heredar");
            gramatica.group("ERROR_INICIO", "(IMPORT)? CLASS Identificador EXTENDS (SENTENCIAS)? LLAVE_C", 1, "Identificador de clase a heredar y llave de apertura no detectada");
            gramatica.group("ERROR_INICIO", "(IMPORT)? CLASS Identificador EXTENDS LLAVE_A (SENTENCIAS)?", 1, "Identificador de clase a heredar y llave de cerrado no detectada");
            gramatica.group("ERROR_INICIO", "(IMPORT)? CLASS Identificador (EXTENDS Identificador)?", 1, "Bloque de codigo no detectado ");
            gramatica.group("ERROR_INICIO", "(IMPORT)? CLASS Identificador (EXTENDS)?", 1, "inicio ilegal falta bloque de codigo");
            gramatica.group("ERROR_INICIO", "(IMPORT)? CLASS EXTENDS", 1, "inicio ilegal falta identificador de clase principal, que hereda y bloque de codigo");
            gramatica.group("ERROR_INICIO", "(IMPORT)? CLASS ", 1, "inicio ilegal falta identificador de clase y bloque de codigo");

   
      gramatica.show();
   }
   
    private void analisisSemantico() {
       HashMap<String, String> identDataType = new HashMap<>();
        identDataType.put("CadenaCaracteres", "String");
        identDataType.put("NumEntero", "int");
        identDataType.put("TRUE", "BOOLEAN");
       identDataType.put("FALSE", "BOOLEAN");
        int i = 0;
        //errore de declaracion semantica
        //ERROR Semantico 6 ------------------------------------------
        for(Production id: indpro){
         // System.out.println(id.lexemeRank(0,-1));
        // System.out.println(id.lexicalCompRank(0,-1));
            //System.out.println("Prueba "+id.lexemeRank(3));
            
            if (!identificadores.containsKey(id.lexemeRank(1))){
                if(id.lexemeRank(3) !=  null){
                    identificadores.put(id.lexemeRank(1), id.lexemeRank(0)+ ":"+ id.lexemeRank(3));
                }//añade el identificador, tipo de dato y valor asignado
                else{
                    identificadores.put(id.lexemeRank(1), id.lexemeRank(0));
                }//añade el identificador y tipo de dato
                i++;
            }
            else {
                errores.add(new ErrorToken(6,"Semántico","Error semántico: El identificador o nombre de variable ya ha sido declarado. ",id.lexemeRank(1),5,6));
                //System.out.println("Prueba "+id.lexemeRank(1));
            } 
            if(i > 0){
                String[] value = identificadores.get(id.lexemeRank(1)).split(":");
                if(value.length > 1){
                    if(!identificadores.containsKey(value[1]) && value[1].matches("[a-zA-Z][a-zA-Z0-9_]*") && !(value[1].equals("true") || value[1].equals("false"))){
                        errores.add(new ErrorToken(3, "Semántico", "Error semántico: El identificador o nombre de variable debe ser declarado antes de usarse", value[1],id.getLine(),id.getColumn()));
                    }
                }
                
            }//recorrido de los identificadores guardados
            }//for each llenado identificadores y comprobar error 6
        System.out.println(Arrays.asList(identificadores));
        //-----------------------------------------------------------------
    }
   
    
   private void llenarTSIdentificadores() {

      DefaultTableModel dm = (DefaultTableModel) jTableTS.getModel();
      dm.setRowCount(0);
      for (Token entry : ComponentesLexicos) {
         if (entry.grupoLexico.equals("Identificador")) {
            Object[] campos = {entry.lexema, entry.hashCode(), entry.linea, entry.columna};
            dm.addRow(campos);
         }
      }
      jTableTS.setModel(dm);
   }
   private void llenarTabla() {

      DefaultTableModel dm = (DefaultTableModel) jTable1.getModel();
      dm.setRowCount(0);

      for (Token entry : ComponentesLexicos) {
         Object[] campos = {entry.lexema, entry.grupoLexico};
         dm.addRow(campos);
      }
      jTable1.setModel(dm);
   }
   
   private void mostrarErrores() {
      int errorSize = errores.size();
      if (errorSize > 0) {
         txtResultado.setForeground(new Color(219, 88, 88));
         txtResultado.setText("");
         for (ErrorToken error : errores) {
            txtResultado.setText(txtResultado.getText() + "\n" + String.valueOf(error));
         }
         txtResultado.setText(txtResultado.getText() + "\n\n" + "-> Compilación finalizada con errores");
      } else {
         txtResultado.setForeground(new Color(120, 212, 110));
         txtResultado.setText("-> Compilación finalizada sin errores");
      }

   }
   

   public static void vaciarTabla(DefaultTableModel Modelo, JTable JT) {
      //Inicializamos la tabla con un modelo
      Modelo = (DefaultTableModel) JT.getModel();
      int i = 0;
      while (i < Modelo.getRowCount()) {
         Modelo.removeRow(i);
      }//metodo while para remover los renglones de la tabla
   }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton compilationButton;
    private javax.swing.JPanel contenedorBaseLexico;
    private javax.swing.JPanel contenedorBaseTS;
    private javax.swing.JPanel contenedorBaseTS1;
    private javax.swing.JPanel contenedorBaseTS2;
    private javax.swing.JPanel contenedorBaseTS3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JSplitPane jSplitPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTableTS;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTree jTree1;
    private javax.swing.JTree jTree2;
    private javax.swing.JMenuItem menuAbrir;
    private javax.swing.JMenuItem menuCerrar;
    private javax.swing.JMenuItem menuGuardar;
    private javax.swing.JMenuItem menuGuardarComo;
    private javax.swing.JMenuItem menuNuevo;
    private javax.swing.JMenuItem menuSalir;
    private javax.swing.JPanel panelBaseDerecho;
    private javax.swing.JPanel panelBaseIzquierdo;
    private javax.swing.JPanel panelBasePestañas;
    private javax.swing.JTabbedPane panelContenedorPestañas;
    private javax.swing.JPanel panelContenedorPrincipal;
    private javax.swing.JInternalFrame pantalla;
    private javax.swing.JScrollPane scrollLexico;
    private javax.swing.JScrollPane scrollTS;
    private javax.swing.JScrollPane scrollTS1;
    private javax.swing.JTable tablaArreglos;
    private javax.swing.JTable tablaFunciones;
    private javax.swing.JTable tablaIdentificadores;
    private javax.swing.JTextPane txtResultado;
    private javax.swing.JFrame ventanaLexico;
    private javax.swing.JFrame ventanaTS;
    private javax.swing.JFrame ventanaTSFija;
    private javax.swing.JFrame ventanaTSIdArreglos;
    private javax.swing.JFrame ventanaTSIdFunciones;
    // End of variables declaration//GEN-END:variables
}
