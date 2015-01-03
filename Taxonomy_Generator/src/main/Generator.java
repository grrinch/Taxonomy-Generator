package main;

import exceptions.InvalidPropertyException;
import helper.BracketNotationHelper;
import helper.Sp;
import helper.FileChooserHelper;
import helper.jDialogEscapeKeyHelper;
import helper.jListSwapperHelper;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseListener;
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
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private static final long serialVersionUID = -2707712944901661771L;

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
     * Creates new form Generator
     */
    public Generator() {
        _propertiesListModel = new DefaultListModel();
        _attributesListModel = new DefaultListModel();
        initComponents();
        initPaths();
        ImageIcon icon = new ImageIcon(this.getClass().getResource("/icons/ikona.png"));
        this.setIconImage(icon.getImage());
        getRootPane().setDefaultButton(combineAttribs);
    }

    private void initPaths() {
        _paths.put(openPath, null);
        _paths.put(savePath, null);
        _paths.put(projectPath, null);
    }

    private void writeBrackets(Value[] p, Integer root_cost) {
        writeBrackets(p, null, root_cost);
    }

    private void writeBrackets(Value[] p, String rootName, Integer root_cost) {
        BracketNotationHelper bnh = new BracketNotationHelper(p, root_cost);
        if (rootName == null || rootName.equals("")) {
            graphTest(bnh.print());
        } else {
            graphTest(bnh.print(rootName));
        }
    }

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
            URL serverUrl = new URL("http://p43.pl/inz/stgraph.png.php");
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
        chartPanel = new javax.swing.JPanel();
        reDrawButton = new javax.swing.JButton();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Taxonomy Generator");
        setName("MainFrame"); // NOI18N

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rightElementsGridPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rightElementsGridPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                    .addComponent(chartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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
        // nowy obiekt wyboru plików
        JFileChooser plikDanych = new JFileChooser();

        // nadaję oknu tytuł
        plikDanych.setDialogTitle("Select raw data file to open");

        try {
            // ustawiam domyślną lokalizację "piętro wyżej" w katalogu "data"
            plikDanych.setCurrentDirectory(new File(_paths.get(openPath) != null ? (String) _paths.get(openPath) : this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "data/"));
//            Sp.s(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "data/");
        } catch (URISyntaxException ex) {
            JOptionPane.showMessageDialog(this, "Unable to read selected file.\n" + ex.toString(), "Error", JOptionPane.WARNING_MESSAGE);
        }

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
    }//GEN-LAST:event_openButtonActionPerformed

    private void nullPathSetup(String filename) {
        if(_paths.get(savePath) == null) {
            _paths.put(savePath, filename);
        }
        if(_paths.get(projectPath) == null) {
            _paths.put(projectPath, filename);
        }
        if(_paths.get(openPath) == null) {
            _paths.put(openPath, filename);
        }
    }

    /**
     * Czyści modele list atrybutów i wartości (JListy powinny być puste)
     */
    private void clearAttributeAndPropertyLists() {
        // ~...
        
        for(ListSelectionListener l: attributesList.getListSelectionListeners()) {
            attributesList.removeListSelectionListener(l);
        }
        
        for(MouseListener m: attributesList.getMouseListeners()) {
            attributesList.removeMouseListener(m);
        }
        
        for(MouseListener m: propertiesList.getMouseListeners()) {
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
        // okno wyboru pliku
        JFileChooser plikProjektu = new JFileChooser();

        // tytuł okna
        plikProjektu.setDialogTitle("Specify a project file to save");

        try {
            // ustawiam domyślną lokalizację "piętro wyżej" w katalogu "projects"
            plikProjektu.setCurrentDirectory(new File(_paths.get(projectPath) != null ? (String) _paths.get(projectPath) : this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "projects/"));
        } catch (URISyntaxException e) {
            JOptionPane.showMessageDialog(this, "Unable to serialize to project file ...\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // ustawiam filtr dozwolonych plików na *.txt, *.data oraz katalogi
        plikProjektu.setFileFilter(FileChooserHelper.OpenSaveProjectChooserFilter());

        // pokaż okno i zwróć co zostało naciśnięte
        int result = plikProjektu.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            String filename = plikProjektu.getSelectedFile().getAbsolutePath();
            filename += filename.endsWith(".taxp") ? "" : ".taxp";
            _paths.put(projectPath, filename);
            saveProject(filename);
        }
    }//GEN-LAST:event_saveProjectButtonActionPerformed

    /**
     * Akcja po kliknięciu przycisku Otwórz projekt
     *
     * @param evt
     */
    private void openProjectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openProjectButtonActionPerformed
        // okno wyboru pliku
        JFileChooser plikProjektu = new JFileChooser();

        // tytuł okna
        plikProjektu.setDialogTitle("Specify a project file to save");

        try {
            // ustawiam domyślną lokalizację "piętro wyżej" w katalogu "projects"
            plikProjektu.setCurrentDirectory(new File(_paths.get(projectPath) != null ? (String) _paths.get(savePath) : this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "projects/"));
        } catch (URISyntaxException ex) {
            JOptionPane.showMessageDialog(this, "Unable to read the project file ...\n" + ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // ustawiam filtr dozwolonych plików na *.txt, *.data oraz katalogi
        plikProjektu.setFileFilter(FileChooserHelper.OpenSaveProjectChooserFilter());

        // pokaż okno i zwróć co zostało naciśnięte
        int result = plikProjektu.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            String filename = plikProjektu.getSelectedFile().getAbsolutePath();
            nullPathSetup(filename);
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
                        JOptionPane.showMessageDialog(this, "Project opened successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException ex) {
                // komunikat o błędzie
                JOptionPane.showMessageDialog(this, "Unable to read the project file ...\n" + ex.toString(), "Error", JOptionPane.WARNING_MESSAGE);
            } catch (ClassNotFoundException ex) {
                // komunikat o błędzie
                JOptionPane.showMessageDialog(this, "Unable to deserialize the project file ...\n" + ex.toString(), "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_openProjectButtonActionPerformed

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
                for(Value v: _attributes[attributesList.getSelectedIndex()].getWartości()) {
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

    public void graphRedraw() {
        if (graphLinesCrossing.isSelected()) {
            writeBrackets(_attributes[attributesList.getSelectedIndex()].getWartości(), _attributes[attributesList.getSelectedIndex()].getNazwa(), _attributes[attributesList.getSelectedIndex()].getKoszt());
        } else {
            writeBrackets(_attributes[attributesList.getSelectedIndex()].getWartości(), _attributes[attributesList.getSelectedIndex()].getKoszt());
        }
    }

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed

        // okno wyboru pliku
        JFileChooser plikTaksonomii = new JFileChooser();

        // tytuł okna
        plikTaksonomii.setDialogTitle("Specify a taxonomy file to save");

        try {
            // ustawiam domyślną lokalizację "piętro wyżej" w katalogu "taxonomy"
            plikTaksonomii.setCurrentDirectory(new File(_paths.get(savePath) != null ? (String) _paths.get(savePath) : this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "taxonomy/"));
        } catch (URISyntaxException e) {
            JOptionPane.showMessageDialog(this, "Unable to save taxonomy file...\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // ustawiam filtr dozwolonych plików na *.taxonomy
        plikTaksonomii.setFileFilter(FileChooserHelper.SaveFileChooserFilter());

        // pokaż okno i zwróć co zostało naciśnięte
        int result = plikTaksonomii.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            String filename = plikTaksonomii.getSelectedFile().getAbsolutePath();
            filename += filename.endsWith(".taxonomy") ? "" : ".taxonomy";
            _paths.put(savePath, filename);
            
            try ( // Uproszczony zapis, który inspirował StackOverflow
                    // http://stackoverflow.com/questions/1053467/how-do-i-save-a-string-to-a-text-file-using-java   ser.writeObject(_attributes);
                    PrintWriter out = new PrintWriter(filename)) {
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
                return;
            }
            // komunikat powodzenia
            JOptionPane.showMessageDialog(this, "Taxonomy saved successfully.", "Saved!", JOptionPane.INFORMATION_MESSAGE);
        }

    }//GEN-LAST:event_saveButtonActionPerformed

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

    private void upArrowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upArrowButtonActionPerformed
        if (propertiesList.getSelectedIndices().length != 1) {
            JOptionPane.showMessageDialog(this, "You can only move one value at the time!", "Value swap error", JOptionPane.ERROR_MESSAGE);
        } else {
            arrowButtonSwapAction("up");
        }
    }//GEN-LAST:event_upArrowButtonActionPerformed

    private void downArrowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downArrowButtonActionPerformed
        if (propertiesList.getSelectedIndices().length != 1) {
            JOptionPane.showMessageDialog(this, "You can only move one value at the time!", "Value swap error", JOptionPane.ERROR_MESSAGE);
        } else {
            arrowButtonSwapAction("down");
        }
    }//GEN-LAST:event_downArrowButtonActionPerformed

    private void reDrawButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reDrawButtonActionPerformed
        int attr = attributesList.getSelectedIndex();
//        Sp.i(attr);
        if (attr > -1) {
            graphRedraw();
        }
    }//GEN-LAST:event_reDrawButtonActionPerformed

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
                    JOptionPane.showMessageDialog(this, "Project saved successfully.", "Saved!", JOptionPane.INFORMATION_MESSAGE);
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
    private javax.swing.JPanel arrowPanel;
    private javax.swing.JPanel attribPanel;
    private javax.swing.JList attributesList;
    private javax.swing.JScrollPane attributesListScrollPane;
    private javax.swing.JPanel chartPanel;
    private javax.swing.JButton combineAttribs;
    private javax.swing.JButton downArrowButton;
    private javax.swing.JRadioButton graphLinesCrossing;
    private javax.swing.JRadioButton graphLinesNotCrossing;
    private javax.swing.ButtonGroup graphObjectsPlacement;
    private javax.swing.JPanel graphPropertiesPanel;
    private javax.swing.JPanel listAndArrowPanel;
    private javax.swing.JButton openButton;
    private javax.swing.JButton openProjectButton;
    private javax.swing.JPanel openSaveButtonPanel;
    private javax.swing.JPanel openSaveProjectButtonPanel;
    private javax.swing.JPanel projectAndGraphPropertiesPanel;
    private javax.swing.JList propertiesList;
    private javax.swing.JScrollPane propertiesListScrollPane;
    private javax.swing.JPanel propertyPanel;
    private javax.swing.JButton reDrawButton;
    private javax.swing.JPanel rightElementsGridPanel;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton saveProjectButton;
    private javax.swing.JButton upArrowButton;
    // End of variables declaration//GEN-END:variables
}
