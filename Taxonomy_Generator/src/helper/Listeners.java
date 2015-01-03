package helper;

import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import main.AttributeNameAndCostModal;
import main.Generator;
import main.ValueAbstractCombineModal;
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
                            frame.graphRedraw();
                        }
                    }
                } catch (NullPointerException ex) {
                }
            }
        };
    }

    public static MouseAdapter AttributesDoubleClickListener(Attribute[] _attributes, final Generator parent) {
        final Attribute[] tempAttr = _attributes;
        final Frame _parent = parent;

        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();

                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    Attribute atr = (Attribute) tempAttr[index];
//                    Object result = JOptionPane.showInputDialog(null, "Please input attribute name: ", "Attribute name", JOptionPane.QUESTION_MESSAGE);
//                    if (result instanceof String) {
//                        String nazwa = result.toString();
                    AttributeNameAndCostModal modal = new AttributeNameAndCostModal(_parent, true, atr);
                    jDialogEscapeKeyHelper.addEscapeListener(modal);

                    // dostaję tymczasową wartość - w niej będą wszystkie połączone
                    IntStringValuePair isvp = modal.showDialog();

                    if (isvp.getS() == null && isvp.getI() == 0) {
                        return;
                    } else {
                        atr.setNazwa(isvp.getS());
                        atr.setKoszt(isvp.getI());
                        parent.graphRedraw();
                    }
//                    }
                }
            }
        };
    }

    public static MouseAdapter ValuesDoubleClickListener(final Attribute[] _attributes, final JList attributesList, final Generator parent) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (attributesList.getSelectedIndex() > -1) {
                    final Value[] tempVals = _attributes[attributesList.getSelectedIndex()].getWartości();

                    final Frame _parent = parent;
                    JList list = (JList) evt.getSource();

                    if (evt.getClickCount() == 2) {
                        int index = list.locationToIndex(evt.getPoint());
                        Value val = (Value) tempVals[index];
//                        Sp.i(index);
//                        Sp.s(val.getNazwa());
//                        Sp.i(val.getPoziom());
                        if (val.getPoziom() > 0) {
                            ValueAbstractCombineModal modal = new ValueAbstractCombineModal(_parent, true);
                            jDialogEscapeKeyHelper.addEscapeListener(modal);

                            IntStringValuePair isvp = new IntStringValuePair(val.getKoszt(), val.toString());
                            // dostaję tymczasową wartość - w niej będą wszystkie połączone
                            IntStringValuePair new_isvp = modal.showDialog(isvp);

                            if (isvp.getS() == null && isvp.getI() == 0) {
                                return;
                            } else {
                                val.setNazwa(new_isvp.getS());
                                val.setKoszt(new_isvp.getI());
                                parent.graphRedraw();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Primitive values must not be changed.\nOnly Abstract Values are allowed have optional name and cost set.", "Incorrect Value selected", JOptionPane.WARNING_MESSAGE);
                        }
//                    }
                    }
                }
            }
        };
    }
}
