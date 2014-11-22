package helper;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.TreeMap;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import models.Attribute;

/**
 *
 * @author radmin
 */
public class Listeners {

    public static ListSelectionListener AttributesListListener(TreeMap _attributes, final JList attributesList, DefaultListModel _propertiesListModel) {
        final TreeMap tempAttr = _attributes;
        final DefaultListModel tempPropModel = _propertiesListModel;

        return new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                try {
                    if (!arg0.getValueIsAdjusting()) {
                        LinkedHashSet _properties = (LinkedHashSet) tempAttr.get(attributesList.getSelectedValue());

                        if (_properties != null) {
                            Iterator it = _properties.iterator();
                            tempPropModel.clear();

                            while (it.hasNext()) {
                                tempPropModel.addElement(it.next());
                            }
                        }
                    }
                } catch (NullPointerException ex) {
                }
            }
        };
    }

    public static MouseAdapter AttributesDoubleClickListener(TreeMap _attributes) {
        final TreeMap tempAttr = _attributes;
        
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                
                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    Attribute atr = (Attribute) tempAttr.get(index);
                    String nazwa = JOptionPane.showInputDialog("Please input attribute name: ");
                    atr.setNazwa(nazwa);
                } 
                /*else if (evt.getClickCount() == 3) {   // Triple-click
                    int index = list.locationToIndex(evt.getPoint());

                }*/
            }
        };
    }
}
