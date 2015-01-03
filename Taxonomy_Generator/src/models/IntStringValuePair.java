package models;

/**
 * Klasa to wyłącznie model przenoszący dane w postaci (Integer) klucz - (String) wartość
 * @author radmin
 */
public class IntStringValuePair {
    protected Integer _i;
    protected String _s;
    
    /**
     * Konstruktor - tworzy obiekt klasy i ustala klucz oraz wartość
     * @param i
     * @param s 
     */
    public IntStringValuePair(Integer i, String s) {
        _i = i;
        _s = s;
    }

    /**
     * Getter dla klucza
     * @return 
     */
    public Integer getI() {
        return _i;
    }

    /**
     * Setter dla klucza
     * @param _i 
     */
    public void setI(Integer _i) {
        this._i = _i;
    }

    /**
     * Getter dla wartości
     * @return 
     */
    public String getS() {
        return _s;
    }

    /**
     * Setter dla wartości
     * @param _s 
     */
    public void setS(String _s) {
        this._s = _s;
    }
    
    
}
