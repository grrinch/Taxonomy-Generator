/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.TreeMap;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
}
