package main;

import exceptions.WrongNumberException;
import java.awt.Frame;
import java.text.NumberFormat;
import javax.swing.JOptionPane;
import models.Attribute;
import models.IntStringValuePair;
import models.Value;

/**
 * Umożliwia ustalenie nazwy oraz kosztu dla atrybutu
 *
 * @author radmin
 */
public class AttributeNameAndCostModal extends javax.swing.JDialog {

    /**
     * minimalna liczba miejsc po przecinku dla kosztu
     */
    private static int MinimumFractionDigits = 0;

    /**
     * maksymalna liczba miejsc po przecinku dla kosztu
     */
    private static int MaximumFractionDigits = 0;

    private final Frame _parent;
    private NumberFormat _doubleFormat;
    private Attribute _a;
    private IntStringValuePair _isvp = new IntStringValuePair(0, null);

    /**
     * Creates new form AttributeNameAndCostModal
     */
    public AttributeNameAndCostModal(java.awt.Frame parent, boolean modal, Attribute attr) {
        super(parent, modal);
        _parent = parent;
        _a = attr;
        formatterInit();
        initComponents();
        getRootPane().setDefaultButton(okButton);
        costInputText.setValue(0);
    }

    /**
     * inicjuje automatycznie formatowane pole
     */
    private void formatterInit() {
        _doubleFormat = NumberFormat.getNumberInstance();
        /*
         Nie wiem dlaczego, ale jak odkomentuję poniższe, to nie działa ;/
         */
        //doubleFormat.setMinimumFractionDigits(PropertyCombineModalInput.MinimumFractionDigits);
        //doubleFormat.setMaximumFractionDigits(PropertyCombineModalInput.MaximumFractionDigits);
        _doubleFormat.setParseIntegerOnly(true);
    }

    /**
     * wyjście z modalu
     */
    private void exit() {
        setVisible(false);
        dispose();
    }

    /**
     * Pokazuje modal i zwraca edytowany tutaj atrybut
     * @return 
     */
    public IntStringValuePair showDialog() {
        setModal(true);
        setLocationRelativeTo(_parent);
        setVisible(true);
        return _isvp;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameLabel = new javax.swing.JLabel();
        nameInputText = new javax.swing.JTextField();
        costLabel = new javax.swing.JLabel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        costInputText = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        nameLabel.setText("Attribute name");

        costLabel.setText("Attribute cost");

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(costInputText)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(okButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameLabel)
                            .addComponent(nameInputText, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(costLabel))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameInputText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(costLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(costInputText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton)
                    .addComponent(cancelButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Akcja dla kliknięcia przycisku OK.
     * @param evt 
     */
    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        _isvp.setS(nameInputText.getText());
        try {
            if (costInputText.getValue() instanceof Long) {
                Long k = (Long) costInputText.getValue();
                _isvp.setI(k.intValue());
            } else if (costInputText.getValue() instanceof Double) {
                Double k = (Double) costInputText.getValue();
                _isvp.setI(k.intValue());
            } else if (costInputText.getValue() instanceof Integer) {
                _isvp.setI((Integer) costInputText.getValue());
            } else {
                throw new WrongNumberException("Integer must be given!");
            }

            exit();
        } catch (WrongNumberException ex) {
            JOptionPane.showMessageDialog(null, "Cost value must be an integer.\n" + ex.toString(), "Wrong number format", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        exit();
    }//GEN-LAST:event_cancelButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JFormattedTextField costInputText;
    private javax.swing.JLabel costLabel;
    private javax.swing.JTextField nameInputText;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JButton okButton;
    // End of variables declaration//GEN-END:variables
}