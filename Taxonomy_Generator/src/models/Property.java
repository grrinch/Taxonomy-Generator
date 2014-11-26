package models;

import exceptions.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa właściwości
 *
 * @author radmin
 */
public class Property implements Comparable<Property>, Serializable {

    /**
     * kolejność z JListy
     */
    private int _id = 1;

    /**
     * nazwa właściwości jeśli podana
     */
    private String _nazwa = "";

    /**
     * koszt danej właściwości
     */
    private double _koszt = 0.0;

    /**
     * tablica właściwości, które mogą należeć do danej właściwości
     */
    private List<Property> _elementy = new ArrayList<Property>();
    
    /**
     * poziom na którym znajduje się dana właściwość na liście
     */
    private int _poziom = 0;

    /**
     * Konstruktor - tylko podany koszt (nie ma nazwy właściwości, ale jej koszt). Kiedy używany:
     *
     * @param _koszt koszt połączenia właściwości
     */
    public Property(double _koszt) {
        this._koszt = _koszt;
    }

    /**
     * Konstruktor - podana nazwa i koszt rozdzielenia. Kiedy używany:
     *
     * @param nazwa nazwa właściwości
     * @param _koszt koszt
     */
    public Property(String nazwa, double _koszt) {
        _nazwa = nazwa;
    }

    /**
     * Konstruktor - podana pozycja na JLiście
     *
     * @param id pozycja na JLiście
     */
    public Property(int id) {
        _id = id;
    }

    /**
     * Konstruktor - podana pozycja na JLiście, nazwa oraz koszt. Ten konstruktor powinien z definicji być najczęściej używanym. Zwłaszcza w kontekście populacji JListy/modelu.
     *
     * @param id pozycja na JLiście
     * @param nazwa nazwa właściwości
     * @param koszt koszt
     */
    public Property(int id, String nazwa, double koszt) {
        _nazwa = nazwa;
        _id = id;
        _koszt = koszt;
    }
    
    /**
     * Konstruktor - podana pozycja na JLiście oraz nazwa.
     * 
     * @param id pozycja na JLiście/w modelu/tablicy
     * @param nazwa nazwa (wartość)
     */
    public Property(int id, String nazwa) {
        _nazwa = nazwa;
        _id = id;
    }

    /**
     * Zwraca nazwę właściwości
     *
     * @return string nazwa
     */
    public String getNazwa() {
        return _nazwa;
    }

    /**
     * Ustala nazwę właściwości
     *
     * @param _nazwa nazwa
     */
    public void setNazwa(String _nazwa) {
        this._nazwa = _nazwa;
    }

    /**
     * Zwraca pozycję elementu na JLiście
     *
     * @return int pozycja (id)
     */
    public int getId() {
        return _id;
    }

    /**
     * Ustala kolejność (id) tego elementu na JLiście.
     *
     * @param id kolejność
     */
    public void setId(int id) {
        this._id = id;
    }

    /**
     * Zwraca koszt, jaki jest ustawiony dla właściwości.
     *
     * @return double koszt
     */
    public double getKoszt() {
        return _koszt;
    }

    /**
     * Zwraca sumę kosztów wszystkich wewnętrznych właściwości.
     *
     * @return double koszt
     */
    public double getInternalPropertiesKoszt() {
        if (_elementy.size() > 0) {
            Double koszt = (double) 0;

            for (Property element : getElementy()) {
                koszt += element.getKoszt();
            }

            return koszt;
        } else {
            return (double) 0;
        }
    }

    /**
     * Ustala koszt danej właściwości
     *
     * @param _koszt koszt
     */
    public void setKoszt(double _koszt) {
        this._koszt = _koszt;
    }

    /**
     * Zwraca tablicę właściwości wewnątrz tej właściwości
     *
     * @return Property[] właściwości wewnątrz właściwości (połączone)
     */
    public Property[] getElementy() {
        return _elementy.toArray(new Property[_elementy.size()]);
    }

    public void add(Property p) throws InvalidPropertyException {
        if (this != p) {
            _elementy.add(p);
        } else {
            throw new InvalidPropertyException("Nie można dodać właściwości do niej samej (nieskończona rekurencja).");
        }
    }

    /**
     * Pozwala na porównanie obiektu właściwości (kryterium jest koszt)
     *
     * @param o podawany obiekt właściwości
     * @return podaje, który obiekt ma większy koszt
     */
    @Override
    public int compareTo(Property o) {
        return Double.compare(this.getKoszt(), o.getKoszt());
    }

    /**
     * Zwraca znakową reprezentację obiektu:
     * <ul>
     * <li>jeśli nie ma właściwości, to jest to nazwa obiektu</li>
     * <li>jeśli ma 1 właściwość, to jest to jej nazwa</li>
     * <li>jeśli ma więcej niż 1 właściwośc, jest to postać z rodzaju: <code>nazwa_wł1, nazwa_wł2
     * ...</code></li>
     * </ul>
     *
     * @return reprezentacja obiektu
     */
    @Override
    public String toString() {
        if (_nazwa.length() > 0) { // ma własną nazwę, więc ją zwracam
            return getNazwa();
        } else if (_elementy.size() == 1) { // ma tylko 1 element wewnętrzny, więc zwracam jego nazwę
            return _elementy.get(_elementy.size() - 1).toString();
        } else if (_elementy.size() > 1) { // ma więcej niż 1 element wewnętrzny, więc zwracam wszystkie ich nazwy po przecinku
            String ret = new String();
            int i = 0;

            for (Property element : getElementy()) {
                if (i == 0) {
                    ret += element.toString();
                } else {
                    ret += "," + element.toString();
                }
                i++;
            }
            return ret;
        } else { // jeśli nie ma ustawionej nazwy ani nie ma elementów wewnętrznych, to zwracam nazwę w postaci "id: koszt"
            return _id + ":" + _koszt;
        }
    }

}
