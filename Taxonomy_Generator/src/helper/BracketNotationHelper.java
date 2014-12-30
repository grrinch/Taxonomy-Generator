package helper;

import models.Value;

/**
 *
 * @author radmin
 */
public class BracketNotationHelper {

    private Value[] _valuee;
    private Integer _root_value = 0;
    private String _root = "ROOT";
    public static final String BR_START = "[";
    public static final String BR_END = "]";

    public BracketNotationHelper(Value[] p, Integer root_value) {
        _valuee = p;
        _root_value = root_value;
    }

    public void setRoot(String r) {
        _root = r;
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
        s.append("_" + _root_value);
        s.append(" ");
        for (Value pp : _valuee) {
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

    public String writeProperty(Value p) {
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
        if(p.getKoszt() != 0) {
            s.append("_");
            s.append(p.getKoszt());
        }
        
        if (p.getPoziom() != 0) {
            s.append(" ");
            for (Value pp : p.getElementy()) {
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
        return _root;
    }
}
