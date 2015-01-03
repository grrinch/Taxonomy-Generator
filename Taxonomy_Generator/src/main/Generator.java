package main;

import exceptions.InvalidPropertyException;
import helper.BracketNotationHelper;
import helper.FileChooserHelper;
import helper.jDialogEscapeKeyHelper;
import helper.jListSwapperHelper;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionListener;
import models.*;

/**
 *
 * @author radmin
 */
public class Generator extends javax.swing.JFrame {

    /**
     * Wersja programu
     */
    private static final Double _version = 2.22;

    /**
     * atrybuty
     */
    private Attribute[] _attributes = null;

    /**
     * Model dla JListy atrybutów
     */
    private DefaultListModel _attributesListModel;

    /**
     * Model dla JListy wartości
     */
    private DefaultListModel _propertiesListModel;

    private String phpSyntaxTreeURL = "http://p43.pl/inz/stgraph.png.php";

    /**
     * Mapa do ścieżek: otwarcia, zapisu taksonomii oraz zapisu projektu.
     */
    private HashMap _paths = new HashMap<String, String>(3);

    /**
     * Poniżej 3 wartości domyślne, po których indeksowana będzie HashMapa ścieżek
     * <b>otwarcia surowego pliku danych</b>
     */
    public static final String openPath = "openPath";
    /**
     * Poniżej 3 wartości domyślne, po których indeksowana będzie HashMapa ścieżek
     * <b>zapis taksonomii</b>
     */
    public static final String savePath = "savePath";
    /**
     * Poniżej 3 wartości domyślne, po których indeksowana będzie HashMapa ścieżek
     * <b>zapis projektu</b>
     */
    public static final String projectPath = "projectPath";

    /**
     * Zawiera nazwę aktualnie zapisanego lub otwartego pliku
     */
    private String filenameBit = null;

    /**
     * Zawiera ścieżkę, w której aktualnie znajduje się plik JAR
     */
    private final String thisFilePath;

    /**
     * Creates new form Generator
     */
    public Generator() {
        _propertiesListModel = new DefaultListModel();
        _attributesListModel = new DefaultListModel();
        initComponents();
        initPaths();
        String sss;
        try {
            String ss = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            sss = System.getProperty("os.name").toLowerCase().contains("win") ? ss.substring(ss.indexOf("\\"), ss.lastIndexOf("\\")) : ss.substring(ss.indexOf("/"), ss.lastIndexOf("/"));
        } catch (URISyntaxException ex) {
            sss = "";
        }
        thisFilePath = sss;
        ImageIcon icon = new ImageIcon(this.getClass().getResource("/icons/ikona.png"));
        this.setIconImage(icon.getImage());
        getRootPane().setDefaultButton(combineAttribs);
    }

    /**
     * Ustawia początkowe wartości ścieżek na puste
     */
    private void initPaths() {
        _paths.put(openPath, null);
        _paths.put(savePath, null);
        _paths.put(projectPath, null);
    }

    /**
     * Zaślepka dla metody writeBrackets, która nie potrzebuje nazwy atrybutu. Wyłącznie wywołuje pełną wersję writeBrackets
     * @param p tablica wartości
     * @param root_cost koszt korzenia atrybutu
     */
    private void writeBrackets(Value[] p, Integer root_cost) {
        writeBrackets(p, null, root_cost);
    }

    /**
     * Tworzy za pomocą BracketNotationHelpera ciąg znaków zawierający zapis grafu atrybutu w notacji nawiasowej i przegenerowuje graf.
     * @param p tablica wartości
     * @param rootName nazwa korzenia atrybutu
     * @param root_cost koszt korzenia atrybutu
     */
    private void writeBrackets(Value[] p, String rootName, Integer root_cost) {
        BracketNotationHelper bnh = new BracketNotationHelper(p, root_cost);
        if (rootName == null || rootName.equals("")) {
            graphTest(bnh.print());
        } else {
            graphTest(bnh.print(rootName));
        }
    }

    /**
     * Łączy się z naszym webService'em, który przesyła plik graficzny z rozrysowanym grafem
     * @param bnh postać grafu w notacji nawiasowej
     */
    private void graphTest(String bnh) {
        try {
            Component[] comps = chartPanel.getComponents();
            for (Component c : comps) {
                if (c instanceof JLabel) {
                    chartPanel.remove(c);
                }
            }
            String toSend = URLEncoder.encode(bnh, "UTF-8");

            // Define the server endpoint to send the HTTP request to
            URL serverUrl = new URL(phpSyntaxTreeURL);
            HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();

            // Indicate that we want to write to the HTTP request body
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("User-Agent", "Mozilla 5.0");

            try ( // Writing the post data to the HTTP request body
                    BufferedWriter httpRequestBodyWriter = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()))) {
                httpRequestBodyWriter.write("data=" + toSend);
            }

            BufferedImage image = ImageIO.read(urlConnection.getInputStream());
            JLabel lab = new JLabel(new ImageIcon(image, "Attribute Taxonomy"));
            chartPanel.add(lab, BorderLayout.CENTER);
            chartPanel.updateUI();
        } catch (UnsupportedEncodingException | MalformedURLException | ProtocolException e) {
            // komunikat o błędzie
            JOptionPane.showMessageDialog(this, "Unable to re-draw taxonomy. No internet access?\n" + e.toString(), "Communication IO Exception", JOptionPane.WARNING_MESSAGE);
        } catch (IOException e) {
            // komunikat o błędzie
            JOptionPane.showMessageDialog(this, "Unable to re-draw taxonomy. Input/Output exception:\n" + e.toString(), "File IO Exception", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            // komunikat o błędzie
            JOptionPane.showMessageDialog(this, "Unable to re-draw taxonomy. Input/Output exception:\n" + e.toString(), "File IO Exception", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        graphObjectsPlacement = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        rightElementsGridPanel = new javax.swing.JPanel();
        attribPanel = new javax.swing.JPanel();
        attributesListScrollPane = new javax.swing.JScrollPane();
        attributesList = new javax.swing.JList();
        projectAndGraphPropertiesPanel = new javax.swing.JPanel();
        graphPropertiesPanel = new javax.swing.JPanel();
        graphLinesCrossing = new javax.swing.JRadioButton();
        graphLinesNotCrossing = new javax.swing.JRadioButton();
        openSaveProjectButtonPanel = new javax.swing.JPanel();
        openProjectButton = new javax.swing.JButton();
        saveProjectButton = new javax.swing.JButton();
        propertyPanel = new javax.swing.JPanel();
        combineAttribs = new javax.swing.JButton();
        openSaveButtonPanel = new javax.swing.JPanel();
        openButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        listAndArrowPanel = new javax.swing.JPanel();
        propertiesListScrollPane = new javax.swing.JScrollPane();
        propertiesList = new javax.swing.JList();
        arrowPanel = new javax.swing.JPanel();
        upArrowButton = new javax.swing.JButton();
        downArrowButton = new javax.swing.JButton();
        chartPanel = new javax.swing.JPanel();
        reDrawButton = new javax.swing.JButton();
        mainMenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openFileMenu = new javax.swing.JMenuItem();
        saveFileMenu = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        openProjectMenu = new javax.swing.JMenuItem();
        saveProjectMenu = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        exitMenu = new javax.swing.JMenuItem();
        prefMenu = new javax.swing.JMenu();
        phpURLMenu = new javax.swing.JMenuItem();
        questionMarkMenu = new javax.swing.JMenu();
        aboutMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Taxonomy Generator");
        setName("MainFrame"); // NOI18N

        rightElementsGridPanel.setLayout(new java.awt.BorderLayout(0, 5));

        attribPanel.setLayout(new java.awt.BorderLayout());

        attributesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        attributesList.setToolTipText("List presenting loaded attributes");
        attributesList.setName("attributesList"); // NOI18N
        attributesList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                attributesListValueChanged(evt);
            }
        });
        attributesListScrollPane.setViewportView(attributesList);
        attributesList.getAccessibleContext().setAccessibleName("atributesList");

        attribPanel.add(attributesListScrollPane, java.awt.BorderLayout.NORTH);

        projectAndGraphPropertiesPanel.setLayout(new java.awt.BorderLayout());

        graphPropertiesPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        graphPropertiesPanel.setLayout(new java.awt.BorderLayout());

        graphObjectsPlacement.add(graphLinesCrossing);
        graphLinesCrossing.setText("Root ren.");
        graphLinesCrossing.setToolTipText("Graph will rename ROOT with attribute name when possible");
        graphLinesCrossing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graphLinesCrossingActionPerformed(evt);
            }
        });
        graphPropertiesPanel.add(graphLinesCrossing, java.awt.BorderLayout.EAST);
        graphLinesCrossing.getAccessibleContext().setAccessibleName("graphLinesCrossing");

        graphObjectsPlacement.add(graphLinesNotCrossing);
        graphLinesNotCrossing.setSelected(true);
        graphLinesNotCrossing.setText("Don't rename");
        graphLinesNotCrossing.setToolTipText("Graph won't rename ROOT");
        graphPropertiesPanel.add(graphLinesNotCrossing, java.awt.BorderLayout.WEST);

        projectAndGraphPropertiesPanel.add(graphPropertiesPanel, java.awt.BorderLayout.SOUTH);

        openSaveProjectButtonPanel.setLayout(new java.awt.GridLayout(1, 0));

        openProjectButton.setText("Open project");
        openProjectButton.setToolTipText("Opens previously saved project");
        openProjectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openProjectButtonActionPerformed(evt);
            }
        });
        openSaveProjectButtonPanel.add(openProjectButton);

        saveProjectButton.setText("Save project");
        saveProjectButton.setToolTipText("saves current project. Useful if you set attribute names, abstract Value combinations and wish to finish working later.");
        saveProjectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveProjectButtonActionPerformed(evt);
            }
        });
        openSaveProjectButtonPanel.add(saveProjectButton);

        projectAndGraphPropertiesPanel.add(openSaveProjectButtonPanel, java.awt.BorderLayout.PAGE_START);

        attribPanel.add(projectAndGraphPropertiesPanel, java.awt.BorderLayout.SOUTH);

        rightElementsGridPanel.add(attribPanel, java.awt.BorderLayout.PAGE_END);

        propertyPanel.setLayout(new java.awt.BorderLayout());

        combineAttribs.setText("Combine Values");
        combineAttribs.setToolTipText("Combine primitive Values to Abstract Values");
        combineAttribs.setFocusable(false);
        combineAttribs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combineAttribsActionPerformed(evt);
            }
        });
        propertyPanel.add(combineAttribs, java.awt.BorderLayout.SOUTH);

        openSaveButtonPanel.setLayout(new java.awt.GridLayout(1, 0));

        openButton.setText("Open file");
        openButton.setToolTipText("Opens data file");
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });
        openSaveButtonPanel.add(openButton);

        saveButton.setText("Save file");
        saveButton.setToolTipText("Saves currently set data file");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        openSaveButtonPanel.add(saveButton);
        saveButton.getAccessibleContext().setAccessibleDescription("Saves currently set Taxonomy file");

        propertyPanel.add(openSaveButtonPanel, java.awt.BorderLayout.PAGE_START);

        listAndArrowPanel.setLayout(new java.awt.BorderLayout());

        propertiesList.setToolTipText("List representing Values in currently selected attribute");
        propertiesList.setDropMode(javax.swing.DropMode.INSERT);
        propertiesList.setLayoutOrientation(javax.swing.JList.VERTICAL_WRAP);
        propertiesList.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                propertiesListPropertyChange(evt);
            }
        });
        propertiesListScrollPane.setViewportView(propertiesList);

        listAndArrowPanel.add(propertiesListScrollPane, java.awt.BorderLayout.CENTER);

        arrowPanel.setBackground(new java.awt.Color(254, 254, 254));
        arrowPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(207, 207, 207)));
        arrowPanel.setLayout(new java.awt.BorderLayout());

        upArrowButton.setText("↑");
        upArrowButton.setMargin(new Insets(0,0,0,0));
        upArrowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upArrowButtonActionPerformed(evt);
            }
        });
        arrowPanel.add(upArrowButton, java.awt.BorderLayout.NORTH);

        downArrowButton.setText("↓");
        downArrowButton.setMargin(new Insets(0,0,0,0));
        downArrowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downArrowButtonActionPerformed(evt);
            }
        });
        arrowPanel.add(downArrowButton, java.awt.BorderLayout.SOUTH);

        listAndArrowPanel.add(arrowPanel, java.awt.BorderLayout.EAST);

        propertyPanel.add(listAndArrowPanel, java.awt.BorderLayout.CENTER);

        rightElementsGridPanel.add(propertyPanel, java.awt.BorderLayout.CENTER);

        chartPanel.setBackground(new java.awt.Color(254, 254, 254));
        chartPanel.setLayout(new java.awt.BorderLayout());

        reDrawButton.setText("Re Draw");
        reDrawButton.setToolTipText("Click to redraw the graph");
        reDrawButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        reDrawButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        reDrawButton.setOpaque(true);
        reDrawButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reDrawButtonActionPerformed(evt);
            }
        });
        chartPanel.add(reDrawButton, java.awt.BorderLayout.SOUTH);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(chartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rightElementsGridPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(chartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rightElementsGridPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE))
                .addContainerGap())
        );

        fileMenu.setText("File");

        openFileMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openFileMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_16x16/folder232.png"))); // NOI18N
        openFileMenu.setText("Open file");
        openFileMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFileMenuActionPerformed(evt);
            }
        });
        fileMenu.add(openFileMenu);

        saveFileMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveFileMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_16x16/flow2_16.png"))); // NOI18N
        saveFileMenu.setText("Save taxonomy");
        saveFileMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveFileMenuActionPerformed(evt);
            }
        });
        fileMenu.add(saveFileMenu);
        fileMenu.add(jSeparator1);

        openProjectMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        openProjectMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_16x16/open131.png"))); // NOI18N
        openProjectMenu.setText("Open project");
        openProjectMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openProjectMenuActionPerformed(evt);
            }
        });
        fileMenu.add(openProjectMenu);

        saveProjectMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        saveProjectMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_16x16/save25.png"))); // NOI18N
        saveProjectMenu.setText("Save project");
        saveProjectMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveProjectMenuActionPerformed(evt);
            }
        });
        fileMenu.add(saveProjectMenu);
        fileMenu.add(jSeparator2);

        exitMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        exitMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_16x16/door9.png"))); // NOI18N
        exitMenu.setText("Exit");
        exitMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenu);

        mainMenuBar.add(fileMenu);

        prefMenu.setText("Options");

        phpURLMenu.setText("phpSyntaxTree URL");
        phpURLMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phpURLMenuActionPerformed(evt);
            }
        });
        prefMenu.add(phpURLMenu);

        mainMenuBar.add(prefMenu);

        questionMarkMenu.setText("?");

        aboutMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_16x16/help1.png"))); // NOI18N
        aboutMenu.setText("About");
        aboutMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuActionPerformed(evt);
            }
        });
        questionMarkMenu.add(aboutMenu);

        mainMenuBar.add(questionMarkMenu);

        setJMenuBar(mainMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void propertiesListPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_propertiesListPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_propertiesListPropertyChange

    private void graphLinesCrossingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_graphLinesCrossingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_graphLinesCrossingActionPerformed

    /**
     * Akcja kliknięcia przycisku otwórz (plik z danymi)
     *
     * @param evt
     */
    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed
        openRawFileAction();
    }//GEN-LAST:event_openButtonActionPerformed

    /**
     * Akcja właściwa, która otwiera plik danych uczących
     * @throws HeadlessException 
     */
    private void openRawFileAction() throws HeadlessException {
        // nowy obiekt wyboru plików
        JFileChooser plikDanych = new JFileChooser();

        // nadaję oknu tytuł
        plikDanych.setDialogTitle("Select raw data file to open");

        // ustawiam domyślną lokalizację "piętro wyżej" w katalogu "data"
        plikDanych.setCurrentDirectory(new File(_paths.get(openPath) != null ? (String) _paths.get(openPath) : thisFilePath + "/data/"));

        // ustawiam filtr dozwolonych plików na *.txt, *.data oraz katalogi
        plikDanych.setFileFilter(FileChooserHelper.OpenFileChooserFilter());

        // uruchamiam okno z wyborem plików
        int result = plikDanych.showOpenDialog(this);

        // jeśli wybrany został plik
        if (result == JFileChooser.APPROVE_OPTION) {
            _attributes = null;
            clearAttributeAndPropertyLists();
            System.gc();
            try {
                String filename = plikDanych.getSelectedFile().getAbsolutePath();
                nullPathSetup(filename);
                titleSetupOnFilename(filename);
                BufferedReader br = new BufferedReader(new FileReader(filename));
                for (String linia; (linia = br.readLine()) != null;) {
                    // każdą linię dzielimy po przecinkach na przypadki uczące (powinno ich w każdej linii być tyle samo - jest to nasza lista atrybutów
                    String[] learningCaseProperties = linia.split(",");

                    createAttributesArrayIfRequired(learningCaseProperties);

                    for (int j = 0; j < learningCaseProperties.length; j++) {
                        createAttributeInArrayIfRequired(j);
                        fileReadPropertyAddToAttribute(j, learningCaseProperties);
                    }
                }
                setModelsForAttributeAndPropertyLists();
                setEventListenersForAttributeAndPropertyLists();

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Unable to read selected file.\n" + ex.toString(), "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /**
     * Ustawia wszystkie puste ścieżki na tą, pod którą został zapisany lub z której został otwarty plik
     * @param filename ścieżka pliku
     */
    private void nullPathSetup(String filename) {
        if (_paths.get(savePath) == null) {
            _paths.put(savePath, filename);
        }
        if (_paths.get(projectPath) == null) {
            _paths.put(projectPath, filename);
        }
        if (_paths.get(openPath) == null) {
            _paths.put(openPath, filename);
        }
    }
    
    /**
     * Zaślepka właściwej metody, która dopisuje do tytułu okna nazwę pliku
     * @param filename ścieżka pliku
     */
    private void titleSetupOnFilename(String filename) {
        titleSetupOnFilename(filename, false);
    }
    
    /**
     * Dopisuje do tytułu okna nazwę pliku
     * @param filename ścieżka pliku
     * @param onlyBitSave czy ma wyłącznie zapisać do wewnętrznej właściwości, czy również dopisać do okna
     */
    private void titleSetupOnFilename(String filename, Boolean onlyBitSave) {
        int ind = 1 + (System.getProperty("os.name").toLowerCase().contains("win") ? filename.lastIndexOf("\\") : filename.lastIndexOf("/"));
        filenameBit = filename.substring(ind);
        if(!onlyBitSave) {
            this.setTitle("Taxonomy Generator: '" + filenameBit + "'");
        }
    }

    /**
     * Ustawia adres URL dla webService, który będzie generował grafiki grafów.
     * Domyślnie: <a href="http://p43.pl/inz/stgraph.png.php">http://p43.pl/inz/stgraph.png.php</a>
     */
    private void setPhpSyntaxTreeURL() {
        try {
            String newUrl = JOptionPane.showInputDialog(this, "Please enter new phpSyntaxTree (graph generator) URL\n(default " + phpSyntaxTreeURL + "):", "Please enter new URL", JOptionPane.QUESTION_MESSAGE, null, null, phpSyntaxTreeURL).toString();
            phpSyntaxTreeURL = newUrl;
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Unable to set the new URL.\nPrevious value restored.", "Unable to change the URL", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Czyści modele list atrybutów i wartości (JListy powinny być puste)
     */
    private void clearAttributeAndPropertyLists() {
        // ~...

        for (ListSelectionListener l : attributesList.getListSelectionListeners()) {
            attributesList.removeListSelectionListener(l);
        }

        for (MouseListener m : attributesList.getMouseListeners()) {
            attributesList.removeMouseListener(m);
        }

        for (MouseListener m : propertiesList.getMouseListeners()) {
            propertiesList.removeMouseListener(m);
        }

        attributesList.clearSelection();
        propertiesList.clearSelection();

        _attributesListModel.clear();
        _propertiesListModel.clear();

    }

    /**
     * Binduje nasłuchiwanie na listach atrybutów i wartości
     */
    private void setEventListenersForAttributeAndPropertyLists() {
        attributesList.addListSelectionListener(helper.Listeners.AttributesListListener(_attributes, attributesList, _propertiesListModel, this));
        attributesList.addMouseListener(helper.Listeners.AttributesDoubleClickListener(_attributes, this));
        propertiesList.addMouseListener(helper.Listeners.ValuesDoubleClickListener(_attributes, attributesList, this));
    }

    /**
     * Ustawia modele dla list atrybutów i wartości
     */
    private void setModelsForAttributeAndPropertyLists() {
        propertiesList.setModel(_propertiesListModel);
        propertiesList.updateUI();
        attributesList.setModel(_attributesListModel);
        attributesList.updateUI();
    }

    /**
     * Tworzy atrybut w tablicy jeśli jeszcze nie istnieje
     *
     * @param j aktualna iteracja po wartościach w linii
     */
    private void createAttributeInArrayIfRequired(int j) {
        if (null == _attributes[j] && !(_attributes[j] instanceof models.Attribute)) {
            _attributes[j] = new Attribute(j + 1);
            _attributesListModel.addElement(_attributes[j]);
        }
    }

    /**
     * Tworzy dla wszystkich atrybutów model sprzężony z JListą
     */
    private void recreateAttributesListModel() {
        _attributesListModel.clear();

        for (Attribute _attribute : _attributes) {
            _attributesListModel.addElement(_attribute);
        }
    }

    /**
     * Tworzę tablicę x atrybutów, gdzie x = learningCaseProperties.length, jeśli lista atrybutów jeszcze nie istnieje
     *
     * @param learningCaseProperties wartości z aktualnie czytanej linii pliku
     */
    private void createAttributesArrayIfRequired(String[] learningCaseProperties) {
        // tworzę tablicę x atrybutów, gdzie x = learningCaseProperties.length, jeśli lista atrybutów jeszcze nie istnieje
        if (_attributes == null) {
            _attributes = new Attribute[learningCaseProperties.length];
        }
    }

    /**
     * Dodaje wartości do odpowiednich atrybutów
     *
     * @param j aktualna iteracja po wartościach w linii
     * @param learningCaseProperties wartości z aktualnie czytanej linii pliku
     */
    private void fileReadPropertyAddToAttribute(int j, String[] learningCaseProperties) {
        if (_attributes[j].getWartości().length > 0) { // w atrybucie są już wartości
            if (!_attributes[j].find(j + 1, learningCaseProperties[j])) { // sprawdzam, czy atrybut ma już wartość o dokładnie takim ID oraz wartości/nazwie
                // nie ma, więc próbuję dodać
                try {
                    _attributes[j].add(new Value(j + 1, learningCaseProperties[j]));
                } catch (InvalidPropertyException ex) {

                }
            } // byłby else, ale jeśli taka wartość już istnieje, to jej nie dodaję

        } else { // nie ma jeszcze żadnych wartości w tym atrybucie
            // próbuję dodać nową wartość w danym miejscu oraz o odpowiedniej wartości
            try {
                _attributes[j].add(new Value(j + 1, learningCaseProperties[j]));
            } catch (InvalidPropertyException ex) {

            }
        }
    }

    private void attributesListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_attributesListValueChanged
    }//GEN-LAST:event_attributesListValueChanged

    /**
     * Akcja po kliknięciu przycisku Zapisz projekt
     *
     * @param evt
     */
    private void saveProjectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveProjectButtonActionPerformed
        saveProjectAction();
    }//GEN-LAST:event_saveProjectButtonActionPerformed

    /**
     * Właściwa akcja, która zapisuje projekt
     * @throws HeadlessException 
     */
    private void saveProjectAction() throws HeadlessException {
        // okno wyboru pliku
        JFileChooser plikProjektu = new JFileChooser();

        // tytuł okna
        plikProjektu.setDialogTitle("Specify a project file to save");

        // ustawiam domyślną lokalizację "piętro wyżej" w katalogu "projects"
        plikProjektu.setCurrentDirectory(new File(_paths.get(projectPath) != null ? (String) _paths.get(projectPath) : thisFilePath + "/projects/"));

        // ustawiam filtr dozwolonych plików na *.txt, *.data oraz katalogi
        plikProjektu.setFileFilter(FileChooserHelper.OpenSaveProjectChooserFilter());

        // pokaż okno i zwróć co zostało naciśnięte
        int result = plikProjektu.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            String filename = plikProjektu.getSelectedFile().getAbsolutePath();
            filename += filename.endsWith(".taxp") ? "" : ".taxp";
            nullPathSetup(filename);
            titleSetupOnFilename(filename);
            saveProject(filename);
        }
    }

    /**
     * Akcja po kliknięciu przycisku Otwórz projekt
     *
     * @param evt
     */
    private void openProjectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openProjectButtonActionPerformed
        openProjectAction();
    }//GEN-LAST:event_openProjectButtonActionPerformed

    /**
     * Właściwa akcja, która otwiera projekt
     * @throws HeadlessException 
     */
    private void openProjectAction() throws HeadlessException {
        // okno wyboru pliku
        JFileChooser plikProjektu = new JFileChooser();

        // tytuł okna
        plikProjektu.setDialogTitle("Specify a project file to open");

        // ustawiam domyślną lokalizację "piętro wyżej" w katalogu "projects"
        plikProjektu.setCurrentDirectory(new File(_paths.get(projectPath) != null ? (String) _paths.get(savePath) : thisFilePath + "/projects/"));

        // ustawiam filtr dozwolonych plików na *.txt, *.data oraz katalogi
        plikProjektu.setFileFilter(FileChooserHelper.OpenSaveProjectChooserFilter());

        // pokaż okno i zwróć co zostało naciśnięte
        int result = plikProjektu.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            String filename = plikProjektu.getSelectedFile().getAbsolutePath();
            nullPathSetup(filename);
            titleSetupOnFilename(filename);
            try {
                try (ObjectInputStream ser = new ObjectInputStream(
                        new BufferedInputStream(
                                new FileInputStream(filename)))) {
                            clearAttributeAndPropertyLists();

                            _attributes = (Attribute[]) ser.readObject();

                            recreateAttributesListModel();

                            setModelsForAttributeAndPropertyLists();

                            setEventListenersForAttributeAndPropertyLists();
                        }
                        // komunikat o powodzeniu
                        JOptionPane.showMessageDialog(this, "Project '" + filenameBit+ "' opened successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException ex) {
                // komunikat o błędzie
                JOptionPane.showMessageDialog(this, "Unable to read the project file ...\n" + ex.toString(), "Error", JOptionPane.WARNING_MESSAGE);
            } catch (ClassNotFoundException ex) {
                // komunikat o błędzie
                JOptionPane.showMessageDialog(this, "Unable to deserialize the project file ...\n" + ex.toString(), "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /**
     * Akcja po kliknięciu przycisku Połącz wartości
     *
     * @param evt
     */
    private void combineAttribsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combineAttribsActionPerformed
        int[] indexes = propertiesList.getSelectedIndices();
        if (indexes.length > 1) {
            ValueAbstractCombineModal modal = new ValueAbstractCombineModal(this, true);
            jDialogEscapeKeyHelper.addEscapeListener(modal);

            // dostaję tymczasową wartość - w niej będą wszystkie połączone
            Value p = modal.showDialog();

            if (p.getNazwa() == null) {
                return;
            }

            // dodajemy do tymczasowej wartości wszystkie z listy
            for (int e : indexes) {
                try {
                    //wyciągamy z modelu wartości odpowiednią z danego indeksu
                    Value temp = (Value) _propertiesListModel.get(e);
//                    Sp.s("Wyjmuję z modelu wartości: " + temp.getNazwa());

                    // usuwamy ją również z atrybutów
                    if (_attributes[attributesList.getSelectedIndex()].remove(temp)) {
                        // dodajemy do tymczasowej wartości
                        p.add(temp);
//                        Sp.s("Dodaję do wartości: " + temp.getNazwa());
                    } else {
                        throw new InvalidPropertyException("Unable to delete the Value at index(" + e + ") from the attributes list.");
                    }
                } catch (InvalidPropertyException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error deleting abstract Values", JOptionPane.ERROR_MESSAGE);
                }
            }
            for (Value pp : p.getElementy()) {
//                Sp.s("Staram się usunąć z modelu wartości wartość: " + pp.getNazwa());
                _propertiesListModel.removeElement(pp);
            }
            // teraz musimy zaktualizować poziom naszej nowej wartości, dodać ją w odpowiednim atrybucie zamiast poprzednich oraz zaktualizować listę
            p.updatePoziomOnCombine();

            try {
                // dodajemy do listy atrybutów
                _attributes[attributesList.getSelectedIndex()].add(p);
                // dodajemy do modelu wartości
                _propertiesListModel.clear();
                for (Value v : _attributes[attributesList.getSelectedIndex()].getWartości()) {
                    _propertiesListModel.addElement(v);
                }

                graphRedraw();

            } catch (InvalidPropertyException ex) {
                // w przypadku gdy nie można dodać wartości do atrybutów
                JOptionPane.showMessageDialog(this, "Unable to add Abstract Value to Attribute\n" + ex.getMessage(), "Error setting Abstract Values", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(this, "At least two Values must be selected for this action", "Too few arguments", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_combineAttribsActionPerformed

    /**
     * Przerysowuje graf na podstawie wybranego atrybutu
     */
    public void graphRedraw() {
        if (graphLinesCrossing.isSelected()) {
            writeBrackets(_attributes[attributesList.getSelectedIndex()].getWartości(), _attributes[attributesList.getSelectedIndex()].getNazwa(), _attributes[attributesList.getSelectedIndex()].getKoszt());
        } else {
            writeBrackets(_attributes[attributesList.getSelectedIndex()].getWartości(), _attributes[attributesList.getSelectedIndex()].getKoszt());
        }
    }

    /**
     * Akcja po kliknięciu przycisku zapisu taksonomii
     * @param evt 
     */
    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        saveTaxonomyAction();
    }//GEN-LAST:event_saveButtonActionPerformed

    /**
     * Właściwa akcja, która zapisuje taksonomię do pliku
     * @return
     * @throws HeadlessException 
     */
    private boolean saveTaxonomyAction() throws HeadlessException {
        // okno wyboru pliku
        JFileChooser plikTaksonomii = new JFileChooser();
        // tytuł okna
        plikTaksonomii.setDialogTitle("Specify a taxonomy file to save");

        // ustawiam domyślną lokalizację "piętro wyżej" w katalogu "taxonomy"
        plikTaksonomii.setCurrentDirectory(new File(_paths.get(savePath) != null ? (String) _paths.get(savePath) : thisFilePath + "/taxonomy/"));

        // ustawiam filtr dozwolonych plików na *.taxonomy
        plikTaksonomii.setFileFilter(FileChooserHelper.SaveFileChooserFilter());
        // pokaż okno i zwróć co zostało naciśnięte
        int result = plikTaksonomii.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String filename = plikTaksonomii.getSelectedFile().getAbsolutePath();
            filename += filename.endsWith(".taxonomy") ? "" : ".taxonomy";
            nullPathSetup(filename);
            titleSetupOnFilename(filename, true);
            try ( // Uproszczony zapis, który inspirował StackOverflow
                    // http://stackoverflow.com/questions/1053467/how-do-i-save-a-string-to-a-text-file-using-java   ser.writeObject(_attributes);
                    final PrintWriter out = new PrintWriter(filename)) {
                for (Attribute a : _attributes) {
                    Boolean flag = false;
                    for (Value p : a.getWartości()) {
                        if (p.getPoziom() > 0) {
                            if (flag == false) {
                                out.print(a.getId());
                                flag = true;
                            }
                            out.print(p.taxonomy());
                        }
                    }
                    if (flag == true) {
                        out.print("," + a.getKoszt());
                        out.println();
                    }
                }
                out.close();
            } catch (Exception e) {
                // komunikat o błędzie
                JOptionPane.showMessageDialog(this, "Unable to save taxonomy file...\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return true;
            }
            // komunikat powodzenia
            JOptionPane.showMessageDialog(this, "Taxonomy saved successfully as '" + filenameBit+ "'.", "Saved!", JOptionPane.INFORMATION_MESSAGE);
        }
        return false;
    }

    /**
     * Akcja zamieniająca miejscami 2 elementy w modelu JListy (przesuwa element w górę lub w dół)
     * @param direction 
     */
    private void arrowButtonSwapAction(String direction) {
        int pos1 = propertiesList.getSelectedIndex();
        int pos2 = direction.equals("up") ? propertiesList.getSelectedIndex() - 1 : propertiesList.getSelectedIndex() + 1;
        int att = attributesList.getSelectedIndex();
        jListSwapperHelper.swapElements(pos1, pos2, _propertiesListModel);
        propertiesList.setSelectedIndex(pos2);
        propertiesList.updateUI();
        jListSwapperHelper.swapAttributeValues(_attributesListModel, att, _propertiesListModel, (Attribute) _attributesListModel.get(att));
        graphRedraw();
    }

    /**
     * Akcja po kliknięciu przycisku strzałki do góry (przesunięcia wartości wyżej na JLiście)
     * @param evt 
     */
    private void upArrowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upArrowButtonActionPerformed
        if (propertiesList.getSelectedIndices().length != 1) {
            JOptionPane.showMessageDialog(this, "You can only move one value at the time!", "Value swap error", JOptionPane.ERROR_MESSAGE);
        } else {
            arrowButtonSwapAction("up");
        }
    }//GEN-LAST:event_upArrowButtonActionPerformed

    /**
     * Akcja po kliknięciu przycisku strzałki w dół (przesunięcia wartości niżej na JLiście)
     * @param evt 
     */
    private void downArrowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downArrowButtonActionPerformed
        if (propertiesList.getSelectedIndices().length != 1) {
            JOptionPane.showMessageDialog(this, "You can only move one value at the time!", "Value swap error", JOptionPane.ERROR_MESSAGE);
        } else {
            arrowButtonSwapAction("down");
        }
    }//GEN-LAST:event_downArrowButtonActionPerformed

    /**
     * Akcja po kliknięciu przycisku przerysowania grafu
     * @param evt 
     */
    private void reDrawButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reDrawButtonActionPerformed
        int attr = attributesList.getSelectedIndex();
//        Sp.i(attr);
        if (attr > -1) {
            graphRedraw();
        }
    }//GEN-LAST:event_reDrawButtonActionPerformed

    /**
     * Akcja po wybraniu z menu zmiany URL dla webService'u
     * @param evt 
     */
    private void phpURLMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phpURLMenuActionPerformed
        setPhpSyntaxTreeURL();
    }//GEN-LAST:event_phpURLMenuActionPerformed

    /**
     * Akcja po wybraniu z menu opcji zapisu taksonomii
     * @param evt 
     */
    private void saveFileMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveFileMenuActionPerformed
        saveTaxonomyAction();
    }//GEN-LAST:event_saveFileMenuActionPerformed

    /**
     * Akcja po wybraniu z menu opcji otwarcia pliku z danymi uczącymi
     * @param evt 
     */
    private void openFileMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFileMenuActionPerformed
        openRawFileAction();
    }//GEN-LAST:event_openFileMenuActionPerformed

    /**
     * Akcja po wybraniu z menu opcji zapisu pliku projektu
     * @param evt 
     */
    private void saveProjectMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveProjectMenuActionPerformed
        saveProjectAction();
    }//GEN-LAST:event_saveProjectMenuActionPerformed

    /**
     * Akcja po wybraniu z menu opcji otwarcia pliku projektu
     * @param evt 
     */
    private void openProjectMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openProjectMenuActionPerformed
        openProjectAction();
    }//GEN-LAST:event_openProjectMenuActionPerformed

    /**
     * Akcja po wybraniu z menu opcji zamknięcia programu
     * @param evt 
     */
    private void exitMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuActionPerformed
        int cl = JOptionPane.showConfirmDialog(this, "Are you sure you want to close?", "Closing", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (cl == JOptionPane.OK_OPTION) {
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }//GEN-LAST:event_exitMenuActionPerformed

    /**
     * Akcja po wybraniu z menu opcji informacji o programie
     * @param evt 
     */
    private void aboutMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuActionPerformed
        JOptionPane.showMessageDialog(this, "<html><table><tr><td>Program name:</td><td><b>Taxonomy Generator</b></td></tr>"
                + "<tr><td>Author:</td><td>Radosław Paluszak</td></tr>"
                + "<tr><td>E-mail address:</td><td><a href=\"mailto:radoslaw.paluszak@gmail.com\">radoslaw.paluszak@gmail.com</a></td></tr>"
                + "<tr><td>Software version:</td><td>" + _version + "</td></tr>"
                + "<tr><td>License:</td><td>Creative Commons BY-ND</td></tr>"
                + "<tr><td>Icon resources</td><td>http://www.flaticon.com/</td></tr>"
                + "<tr><td>Graph powered by</td><td>code.google.com/p/phpsyntaxtree</td></tr>"
                + "<tr><td> </td><td>Copyright &copy; 2014-2015</td></tr>"
                + "</table>"
                + "</html>", "About Taxonomy Generator", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(this.getClass().getResource("/icons/logo_pp_i_logo_TaxGen_100px_x_166px_1.png")));
    }//GEN-LAST:event_aboutMenuActionPerformed

    /**
     * Zapisuje projekt jako zaserializowany plik Javy
     *
     * @param filename nazwa pliku do zapisu
     * @return boolean czy zapis udany
     */
    public Boolean saveProject(String filename) {
        try {
            try (ObjectOutputStream ser = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(filename)))) {
                        ser.writeObject(_attributes);
                    }
                    // komunikat o powodzeniu
                    JOptionPane.showMessageDialog(this, "Project saved successfully as '" + filenameBit+ "'.", "Saved!", JOptionPane.INFORMATION_MESSAGE);
                    return true;
        } catch (IOException e) {
            // komunikat o błędzie
            JOptionPane.showMessageDialog(this, "Unable to serialize to project file ...\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Generator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Generator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Generator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Generator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Generator().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenu;
    private javax.swing.JPanel arrowPanel;
    private javax.swing.JPanel attribPanel;
    private javax.swing.JList attributesList;
    private javax.swing.JScrollPane attributesListScrollPane;
    private javax.swing.JPanel chartPanel;
    private javax.swing.JButton combineAttribs;
    private javax.swing.JButton downArrowButton;
    private javax.swing.JMenuItem exitMenu;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JRadioButton graphLinesCrossing;
    private javax.swing.JRadioButton graphLinesNotCrossing;
    private javax.swing.ButtonGroup graphObjectsPlacement;
    private javax.swing.JPanel graphPropertiesPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPanel listAndArrowPanel;
    private javax.swing.JMenuBar mainMenuBar;
    private javax.swing.JButton openButton;
    private javax.swing.JMenuItem openFileMenu;
    private javax.swing.JButton openProjectButton;
    private javax.swing.JMenuItem openProjectMenu;
    private javax.swing.JPanel openSaveButtonPanel;
    private javax.swing.JPanel openSaveProjectButtonPanel;
    private javax.swing.JMenuItem phpURLMenu;
    private javax.swing.JMenu prefMenu;
    private javax.swing.JPanel projectAndGraphPropertiesPanel;
    private javax.swing.JList propertiesList;
    private javax.swing.JScrollPane propertiesListScrollPane;
    private javax.swing.JPanel propertyPanel;
    private javax.swing.JMenu questionMarkMenu;
    private javax.swing.JButton reDrawButton;
    private javax.swing.JPanel rightElementsGridPanel;
    private javax.swing.JButton saveButton;
    private javax.swing.JMenuItem saveFileMenu;
    private javax.swing.JButton saveProjectButton;
    private javax.swing.JMenuItem saveProjectMenu;
    private javax.swing.JButton upArrowButton;
    // End of variables declaration//GEN-END:variables
}
