package models;

import exceptions.*;
import helper.Sp;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa wartości
 *
 * @author radmin
 */
public class Value implements Comparable<Value>, Serializable {

    /**
     * kolejność z JListy
     */
    private int _id = 1;

    /**
     * nazwa wartości jeśli podana
     */
    private String _nazwa = "";

    /**
     * koszt danej wartości
     */
    private Integer _koszt = 0;

    /**
     * tablica wartości, które mogą należeć do danej wartości
     */
    private List<Value> _elementy = new ArrayList<Value>();

    /**
     * poziom na którym znajduje się dana wartości na liście
     */
    private int _poziom = 0;

    /**
     * Konstruktor - tylko podany koszt (nie ma nazwy wartości, ale jej koszt). Kiedy używany:
     *
     * @param _koszt koszt połączenia wartości
     */
    public Value(Integer _koszt) {
        this._koszt = _koszt;
    }

    /**
     * Konstruktor - podana nazwa i koszt rozdzielenia. Kiedy używany:
     *
     * @param nazwa nazwa wartości
     * @param _koszt koszt
     */
    public Value(String nazwa, Integer _koszt) {
        _nazwa = nazwa;
        this._koszt = _koszt;
    }

    /**
     * Konstruktor - podana pozycja na JLiście
     *
     * @param id pozycja na JLiście
     */
    public Value(int id) {
        _id = id;
    }

    /**
     * Konstruktor - podana pozycja na JLiście, nazwa oraz koszt. Ten konstruktor powinien z definicji być najczęściej używanym. Zwłaszcza w kontekście populacji JListy/modelu.
     *
     * @param id pozycja na JLiście
     * @param nazwa nazwa wartości
     * @param koszt koszt
     */
    public Value(int id, String nazwa, Integer koszt) {
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
    public Value(int id, String nazwa) {
        _nazwa = nazwa;
        _id = id;
    }

    /**
     * Zwraca nazwę wartości
     *
     * @return string nazwa
     */
    public String getNazwa() {
        return _nazwa;
    }

    /**
     * Ustala nazwę wartości
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
     * Zwraca koszt, jaki jest ustawiony dla wartości.
     *
     * @return double koszt
     */
    public double getKoszt() {
        return _koszt;
    }

    /**
     * Zwraca sumę kosztów wszystkich wewnętrznych wartości.
     *
     * @return double koszt
     */
    public double getInternalPropertiesKoszt() {
        if (_elementy.size() > 0) {
            Double koszt = (double) 0;

            for (Value element : getElementy()) {
                koszt += element.getKoszt();
            }

            return koszt;
        } else {
            return (double) 0;
        }
    }

    /**
     * Ustala koszt danej wartości
     *
     * @param _koszt koszt
     */
    public void setKoszt(Integer _koszt) {
        this._koszt = _koszt;
    }

    /**
     * Zwraca tablicę wartości wewnątrz tej wartości
     *
     * @return Value[] wartości wewnątrz wartości (połączone)
     */
    public Value[] getElementy() {
        return _elementy.toArray(new Value[_elementy.size()]);
    }

    /**
     * Dodaje wartość do listy
     *
     * @param p wartość, którą dodajemy
     * @throws InvalidPropertyException
     */
    public void add(Value p) throws InvalidPropertyException {
        if (this != p) {
            _elementy.add(p);
        } else {
            throw new InvalidPropertyException("Unable to add properties to itself (infinite recursion).");
        }
    }

    /**
     * Pozwala na porównanie obiektu wartości (kryterium jest koszt)
     *
     * @param o podawany obiekt wartości
     * @return podaje, który obiekt ma większy koszt
     */
    @Override
    public int compareTo(Value o) {
        return Double.compare(this.getKoszt(), o.getKoszt());
    }

    /**
     * Zwraca znakową reprezentację obiektu:
     * <ul>
     * <li>jeśli nie ma wartości, to jest to nazwa obiektu</li>
     * <li>jeśli ma 1 wartość, to jest to jej nazwa</li>
     * <li>jeśli ma więcej niż 1 wartość, jest to postać z rodzaju: <code>nazwa_wart1, nazwa_wart2
     * ...</code></li>
     * </ul>
     *
     * @return reprezentacja obiektu
     */
    @Override
    public String toString() {
        return rawNazwa(true);
    }

    public String rawNazwa(boolean forToString) {
        if (forToString && _nazwa.length() > 0) { // ma własną nazwę, więc ją zwracam
            return getNazwa();
        } else if (_elementy.size() == 1) { // ma tylko 1 element wewnętrzny, więc zwracam jego nazwę
            return (forToString ? (_elementy.get(_elementy.size() - 1).toString()) : (_elementy.get(_elementy.size() - 1).rawNazwa(false)));
        } else if (_elementy.size() > 1) { // ma więcej niż 1 element wewnętrzny, więc zwracam wszystkie ich nazwy po przecinku
            String ret = new String();
            int i = 0;

            for (Value element : getElementy()) {
                if (i == 0) {
                    ret = forToString ? element.toString() : element.rawNazwa(false);
                } else {
                    ret += "|" + (forToString ? element.toString() : element.rawNazwa(false));
                }
                i++;
            }
            return ret;
        } else if (!forToString && _nazwa.length() > 0) {
            return getNazwa();
        } else { // jeśli nie ma ustawionej nazwy ani nie ma elementów wewnętrznych, to zwracam nazwę w postaci "id: koszt (hash)"
            return _id + ":" + _koszt + "(" + this.hashCode() + ")";
        }
    }

    /**
     * zwraca aktualny poziom
     *
     * @return
     */
    public int getPoziom() {
        return _poziom;
    }

    /**
     * Funkcja, która aktualizuje poziom wartości
     */
    public void updatePoziomOnCombine() {
        int max = -1;
        for (Value temp : _elementy) {
            if (temp.getPoziom() > max) {
                max = temp.getPoziom();
            }
        }

        _poziom = max + 1;
//        Sp.s("Aktualizuję poziom (" + _poziom + ") wartości '" + getNazwa() + "'");
    }

    public String taxonomy() {
        StringBuilder tax = new StringBuilder();
        int i;
        if (getPoziom() < 2) {
            i = 0;
            StringBuilder tmp = new StringBuilder();
            Boolean flag = false;
            for (Value p : getElementy()) {
                if (i == 0) {
                    tmp.append(p.rawNazwa(false));
                    flag = true;
                } else {
                    tmp.append("|").append(p.rawNazwa(false));
                }
                i++;
            }
            if (flag == true) {
                tax.append(",").append(tmp);
                flag = false;
            }
        } else {
            StringBuilder tmp = new StringBuilder();
            for (Value p : getElementy()) {
                tmp.append(p.taxonomy());

            }
            tax.append(tmp).append(",").append(rawNazwa(false));
        }

        return tax.toString();
    }

}
