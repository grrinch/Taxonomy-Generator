/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import models.Property;

/**
 *
 * @author radmin
 */
public class BracketNotationHelper {

    private Property[] property;
    private String root = "ROOT";
    public static final String BR_START = "[";
    public static final String BR_END = "]";

    public BracketNotationHelper(Property[] p) {
        property = p;
    }

    public void setRoot(String r) {
        root = r;
    }

    public String print() {
        StringBuilder s = new StringBuilder();
        s.append(getStart());
        s.append(getRoot()
                .replace(" ", "-")
                .replace("_", "-")
                .replace("ż", "z")
                .replace("ó", "o")
                .replace("ą", "a")
                .replace("ź", "z")
                .replace("ę", "e")
                .replace("ł", "l")
                .replace("ś", "s")
                .replace("ń", "n")
                .replace("ć", "c")
                .replace("Ż", "Z")
                .replace("Ó", "O")
                .replace("Ą", "A")
                .replace("Ź", "Z")
                .replace("Ę", "E")
                .replace("Ł", "L")
                .replace("Ś", "S")
                .replace("Ń", "N")
                .replace("Ć", "C")
        );
        s.append(" ");
        for (Property pp : property) {
            s.append(writeProperty(pp));
        }
        s.append(" ");
        s.append(getEnd());
        return s.toString();
    }

    public String print(String rootname) {
        setRoot(rootname);
        return print();
    }

    public String writeProperty(Property p) {
        StringBuilder s = new StringBuilder();
        s.append(getStart());
        s.append(p.toString()
                .replace(" ", "-")
                .replace("_", "-")
                .replace("ż", "z")
                .replace("ó", "o")
                .replace("ą", "a")
                .replace("ź", "z")
                .replace("ę", "e")
                .replace("ł", "l")
                .replace("ś", "s")
                .replace("ń", "n")
                .replace("ć", "c")
                .replace("Ż", "Z")
                .replace("Ó", "O")
                .replace("Ą", "A")
                .replace("Ź", "Z")
                .replace("Ę", "E")
                .replace("Ł", "L")
                .replace("Ś", "S")
                .replace("Ń", "N")
                .replace("Ć", "C")
        );
        if (p.getPoziom() != 0) {
            s.append(" ");
            for (Property pp : p.getElementy()) {
                s.append(writeProperty(pp));
            }
        }
        s.append(getEnd());
        return s.toString();
    }

    public String getStart() {
        return BR_START;
    }

    public String getEnd() {
        return BR_END;
    }

    public String getRoot() {
        return root;
    }
}
