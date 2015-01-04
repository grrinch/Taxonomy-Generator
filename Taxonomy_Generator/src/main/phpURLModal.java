/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import models.IntStringValuePair;

/**
 *
 * @author radmin
 */
public class phpURLModal extends javax.swing.JDialog {

    private String phpSyntaxTreeURL = null;
    private final Frame _parent;

    /**
     * Creates new form phpURLModal
     */
    public phpURLModal(java.awt.Frame parent, boolean modal, String currentUrl) {
        super(parent, modal);
        _parent = parent;
        initComponents();
        this.setTitle("Please enter new URL");
        ImageIcon image = new ImageIcon(this.getClass().getResource("/icons/three67.png"));
        iconPanel.add(new JLabel(image), BorderLayout.CENTER);
        iconPanel.updateUI();
        urlInput.setText(currentUrl);
        jLabel2.setText("(default " + Generator.defaultPhpSyntaxTreeURL + "):");
        getRootPane().setDefaultButton(okButton);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        connectionProgress = new javax.swing.JProgressBar();
        connectionTestButton = new javax.swing.JButton();
        urlInput = new javax.swing.JTextField();
        iconPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        setDefaultButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();
        smallInfoText = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        connectionTestButton.setText("Connection test");
        connectionTestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectionTestButtonActionPerformed(evt);
            }
        });

        urlInput.setText("jTextField1");

        iconPanel.setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Please enter new phpSyntaxTree (graph generator) URL");

        jLabel2.setText("jLabel2");

        setDefaultButton.setText("Set default");
        setDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setDefaultButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        smallInfoText.setFont(new java.awt.Font("Ubuntu", 2, 12)); // NOI18N
        smallInfoText.setText("For localhost, try: \"http://[::1]:8888/stgraph.png.php\"");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iconPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(connectionProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(connectionTestButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(smallInfoText)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(urlInput, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(setDefaultButton, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(iconPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(urlInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(setDefaultButton))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(smallInfoText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(connectionProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(connectionTestButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void connectionTestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectionTestButtonActionPerformed
        try {
            connectionProgress.setStringPainted(true);
            String toSend = URLEncoder.encode("[ROOT [0]]", "UTF-8");
            connectionProgress.setValue(10);

            // Define the server endpoint to send the HTTP request to
            URL serverUrl = new URL(urlInput.getText());
            connectionProgress.setValue(20);
            HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
            connectionProgress.setValue(30);

            // Indicate that we want to write to the HTTP request body
            urlConnection.setDoOutput(true);
            connectionProgress.setValue(40);
            urlConnection.setRequestMethod("POST");
            connectionProgress.setValue(50);
            urlConnection.setRequestProperty("User-Agent", "Mozilla 5.0");
            connectionProgress.setValue(60);

            try ( // Writing the post data to the HTTP request body
                    BufferedWriter httpRequestBodyWriter = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()))) {
                httpRequestBodyWriter.write("data=" + toSend);
                connectionProgress.setValue(70);
            }

            BufferedImage image = ImageIO.read(urlConnection.getInputStream());
            connectionProgress.setValue(80);
            
            if(image.getHeight() > 0 && image.getWidth() > 0) {
                connectionProgress.setValue(100);
                JOptionPane.showMessageDialog(this, "OK (Rx200)", "Connection status Rx200", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                connectionProgress.setValue(90);
                JOptionPane.showMessageDialog(this, "Response malformed (Rx500)", "Connection status Rx500", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (UnsupportedEncodingException ex) {
            JOptionPane.showMessageDialog(this, "Unable to test (Rx0)", "Connection status Rx0", JOptionPane.ERROR_MESSAGE);
        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(this, "Unreachable (Rx400)", "Connection status Rx400", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Not responding (Rx500)", "Connection status Rx500", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_connectionTestButtonActionPerformed

    private void setDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setDefaultButtonActionPerformed
        urlInput.setText(Generator.defaultPhpSyntaxTreeURL);
    }//GEN-LAST:event_setDefaultButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        exit();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        phpSyntaxTreeURL = urlInput.getText();
        exit();
    }//GEN-LAST:event_okButtonActionPerformed

    /**
     * wyjście z modalu
     */
    private void exit() {
        setVisible(false);
        dispose();
    }

    /**
     * Pokazuje modal i zwraca edytowany tutaj atrybut
     *
     * @return
     */
    public String showDialog() {
        setModal(true);
        setLocationRelativeTo(_parent);
        setVisible(true);
        return phpSyntaxTreeURL;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JProgressBar connectionProgress;
    private javax.swing.JButton connectionTestButton;
    private javax.swing.JPanel iconPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton okButton;
    private javax.swing.JButton setDefaultButton;
    private javax.swing.JLabel smallInfoText;
    private javax.swing.JTextField urlInput;
    // End of variables declaration//GEN-END:variables
}
