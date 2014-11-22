package models;

import exceptions.*;
import java.io.Serializable;

/**
 *
 * @author radmin
 */
public class Attribute implements Serializable {
    /**
     * kolejność na JLiście lub modelu.
     */
    private int _id = 0;
    
    /**
     * Nazwa atrybutu.
     */
    private String _nazwa = "";
    
    /**
     * Lista jego właściwości.
     */
    private Property[] _właściwości;

    /**
     * Konstruktor - pusty atrybut.
     * Kiedy używamy: 
     * 
     */
    public Attribute() {
    }

    /**
     * Konstruktor - podajemy id kolejności w JLiście/modelu.
     * Kiedy używamy: 
     * 
     * @param _id kolejność
     */
    public Attribute(int _id) {
        this._id = _id;
    }

    /**
     * Konstruktor - podana kolejność i nazwa atrybutu.
     * Kiedy używamy: 
     * 
     * @param _id kolejność
     * @param _nazwa nazwa atrybutu
     */
    public Attribute(int _id, String _nazwa) {
        this._id = _id;
        this._nazwa = _nazwa;
    }

    /**
     * Konstruktor - najbardziej rozbudowana forma. Podajemy wszystkie dane.
     * Kiedy używamy: 
     * 
     * @param _id kolejność
     * @param _nazwa nazwa atrybutu
     * @param _właściwości właściwości atrybutu
     */
    public Attribute(int _id, String _nazwa, Property[] _właściwości) {
        this._id = _id;
        this._nazwa = _nazwa;
        this._właściwości = _właściwości;
    }

    /**
     * Zwraca nazwę atrybutu.
     * @return string nazwa
     */
    public String getNazwa() {
        return _nazwa;
    }

    /**
     * Ustawia nazwę dla danego atrybutu.
     * 
     * @param _nazwa nazwa
     */
    public void setNazwa(String _nazwa) {
        this._nazwa = _nazwa;
    }

    /**
     * Zwraca wszystkie właściwości należące do tego atrybutu.
     * @return 
     */
    public Property[] getWłaściwości() {
        return _właściwości;
    }

    /**
     * Zwraca kolejność/id atrybutu.
     * @return int id
     */
    public int getId() {
        return _id;
    }

    /**
     * Ustawia kolejność/id atrybutu.
     * 
     * @param _id kolejność
     */
    public void setId(int _id) {
        this._id = _id;
    }
    
    public void add(Property p) throws InvalidPropertyException {
        for(Property element: _właściwości) {
            if(p == element) {
                throw new InvalidPropertyException("Nie można wielokrotnie dodać tej samej właściwości!");
            }
        }
        
        _właściwości[_właściwości.length] = p;
        
    }
    
}
