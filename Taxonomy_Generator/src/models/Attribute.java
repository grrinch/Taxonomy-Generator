package models;

/**
 *
 * @author radmin
 */
public class Attribute {
    private int _id;
    private String _nazwa;
    private Property[] _właściwości;

    public Attribute() {
    }

    public Attribute(int _id) {
        this._id = _id;
    }

    public Attribute(int _id, String _nazwa) {
        this._id = _id;
        this._nazwa = _nazwa;
    }

    public Attribute(int _id, String _nazwa, Property[] _właściwości) {
        this._id = _id;
        this._nazwa = _nazwa;
        this._właściwości = _właściwości;
    }

    public String getNazwa() {
        return _nazwa;
    }

    public void setNazwa(String _nazwa) {
        this._nazwa = _nazwa;
    }
    
    
}
