package helper;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import main.Generator;
import models.*;

/**
 * Helpery do nasłuchiwania (aplikowane na listach itp)
 *
 * @author radmin
 */
public class Listeners {

    public static ListSelectionListener AttributesListListener(Attribute[] _attributes, final JList attributesList, DefaultListModel _valuesListModel, final Generator frame) {
        final Attribute[] tempAttr = _attributes;
        final DefaultListModel tempPropModel = _valuesListModel;

        return new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                try {
                    if (!arg0.getValueIsAdjusting()) {
                        if (attributesList.getSelectedIndex() > -1) {
                            Value[] _values;
                            _values = tempAttr[attributesList.getSelectedIndex()].getWartości();

                            if (null != _values) {
                                tempPropModel.clear();

                                for (Value valuee : _values) {
                                    tempPropModel.addElement(valuee);
                                }
                            }
                        }
                    }
                } catch (NullPointerException ex) {
                }
                frame.graphRedraw();
            }
        };
    }

    public static MouseAdapter AttributesDoubleClickListener(Attribute[] _attributes) {
        final Attribute[] tempAttr = _attributes;

        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();

                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    Attribute atr = (Attribute) tempAttr[index];
                    Object result = JOptionPane.showInputDialog(null, "Please input attribute name: ", "Attribute name", JOptionPane.QUESTION_MESSAGE);
                    if (result instanceof String) {
                        String nazwa = result.toString();
                        atr.setNazwa(nazwa);
                    }
                }
                /*else if (evt.getClickCount() == 3) {   // Triple-click
                 int index = list.locationToIndex(evt.getPoint());

                 }*/
            }
        };
    }
}
