package models;

/**
 *
 * @author radmin
 */
public class IntStringValuePair {
    protected Integer _i;
    protected String _s;
    
    public IntStringValuePair(Integer i, String s) {
        _i = i;
        _s = s;
    }

    public Integer getI() {
        return _i;
    }

    public void setI(Integer _i) {
        this._i = _i;
    }

    public String getS() {
        return _s;
    }

    public void setS(String _s) {
        this._s = _s;
    }
    
    
}
