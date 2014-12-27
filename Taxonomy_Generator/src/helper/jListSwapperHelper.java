/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import javax.swing.DefaultListModel;
import models.Attribute;
import models.Property;

/**
 *
 * @author radmin
 */
public class jListSwapperHelper {
     /**
     * Zamienia 2 elementy podanego listModelu i atrybutu
     * @param pos1 1 element do zamiany miejscami
     * @param pos2 2 element do zamiany miejscam
     * @param listModel model listy, dla którego ma nastąpić zamiana
     */
    public static void swapElements(int pos1, int pos2, DefaultListModel listModel) {
        try {
        Property tmp = (Property) listModel.get(pos1);
        listModel.set(pos1, listModel.get(pos2));
        listModel.set(pos2, tmp);
        }
        catch(java.lang.ArrayIndexOutOfBoundsException e) {}
    }
    
    public static void swapAttributeValues(DefaultListModel attrModel, int index, DefaultListModel listModel, Attribute attribute) {
//        Sp.s("AttributeListModel: " + attrModel);
//        System.out.print("Indexes attrib: ");
//        int j = 0;
//        Attribute _tmpA = (Attribute) attrModel.get(index);
//        for(Property p: _tmpA.getWartości()) {
//            System.out.print((j == 0 ? "": ", ") + p.getId());
//            j++;
//        }
//        System.out.println();
//        Sp.s("Index: " + index);
//        Sp.s("PropertyListModel: " + listModel);
//        System.out.print("Indexes: ");
//        int k = 0;
//        for(Object o: listModel.toArray()) {
//            System.out.print((k == 0 ? "": ", ") + ((Property) o).getId());
//            k++;
//        }
//        System.out.println();
//        Sp.s("-------------------------------");
        
        Property[] prop = new Property[listModel.toArray().length];
        int i = 0;
        for(Object p: listModel.toArray()) {
            prop[i] = (Property) p;
            prop[i].setId(i);
            i++;
        }
        ((Attribute) attrModel.get(index)).recreateWartosci(prop);
        
//        Sp.s("AttributeListModel: " + attrModel);
//        System.out.print("Indexes attrib: ");
//        Attribute _tmpB = (Attribute) attrModel.get(index);
//        for(Property p: _tmpB.getWartości()) {
//            System.out.print((j == 0 ? "": ", ") + p.getId());
//            j++;
//        }
//        System.out.println();
//        Sp.s("Index: " + index);
//        Sp.s("PropertyListModel: " + listModel);
//        System.out.print("Indexes: ");
//        k = 0;
//        for(Object o: listModel.toArray()) {
//            System.out.print((k == 0 ? "": ", ") + ((Property) o).getId());
//            k++;
//        }
//        System.out.println();
//        Sp.s("===============================");
    }
}
