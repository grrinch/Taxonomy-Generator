package models;

import java.util.Iterator;

/**
 *
 * @author radmin
 */
public class Property implements Comparable<Property>{
    private int _id;
    private String _nazwa;
    private double _koszt;
    private Property[] _elementy;

    public Property(double _koszt) {
        this._koszt = _koszt;
    }
    
    public Property(String nazwa, double _koszt) {
        _nazwa = nazwa;
    }
    
    public Property(int id) {
        _id = id;
    }
    
    public Property(int id, String nazwa, double _koszt) {
        _nazwa = nazwa;
        _id = id;
    }

    public String getNazwa() {
        return _nazwa;
    }

    public void setNazwa(String _nazwa) {
        this._nazwa = _nazwa;
    }

    public int getId() {
        return _id;
    }

    public double getKoszt() {
        return _koszt;
    }

    public Property[] getElementy() {
        return _elementy;
    }

    @Override
    public int compareTo(Property o) {
        return Double.compare(this.getKoszt(), o.getKoszt());
    }

}
