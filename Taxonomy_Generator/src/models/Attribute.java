package models;

import exceptions.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author radmin
 */
public class Attribute implements Serializable {

    /**
     * kolejność na JLiście lub modelu.
     */
    private int _id = 1;

    /**
     * Nazwa atrybutu.
     */
    private String _nazwa = "";

    /**
     * Lista jego wartości.
     */
    private List<Property> _wartości = new ArrayList<Property>();

    /* *
     * Konstruktor - pusty atrybut. Kiedy używamy: tylko w przypadku serializacji do XMLa
     *
     * /
    public Attribute() {
    }*/

    /**
     * Konstruktor - podajemy id kolejności w JLiście/modelu. Kiedy używamy: podstawowy konstruktor.
     *
     * @param _id kolejność
     */
    public Attribute(int _id) {
        this._id = _id;
    }

    /**
     * Konstruktor - podana kolejność i nazwa atrybutu. Kiedy używamy:
     *
     * @param _id kolejność
     * @param _nazwa nazwa atrybutu
     */
    public Attribute(int _id, String _nazwa) {
        this._id = _id;
        this._nazwa = _nazwa;
    }

    /**
     * Konstruktor - najbardziej rozbudowana forma. Podajemy wszystkie dane. Kiedy używamy:
     *
     * @param _id kolejność
     * @param _nazwa nazwa atrybutu
     * @param wartości wartości atrybutu
     */
    public Attribute(int _id, String _nazwa, Property[] wartości) {
        this._id = _id;
        this._nazwa = _nazwa;
        for (Property wlasciwosc : wartości) {
            this._wartości.add(wlasciwosc);
        }
    }

    /**
     * Zwraca nazwę atrybutu.
     *
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
     * Zwraca wszystkie wartości należące do tego atrybutu.
     *
     * @return
     */
    public Property[] getWartości() {
        Collections.sort(_wartości, new Comparator<Property>() {
            @Override
            public int compare(Property p1, Property p2) {

                return p1.getNazwa().compareTo(p2.getNazwa());
            }
        });

        Collections.sort(_wartości, new Comparator<Property>() {
            @Override
            public int compare(Property p1, Property p2) {

                return p1.getId() - p2.getId();
            }
        });
        return _wartości.toArray(new Property[_wartości.size()]);
    }

    /**
     * Zwraca kolejność/id atrybutu.
     *
     * @return int id
     */
    public int getId() {
        return _id;
    }

    /**
     * Ustawia kolejność/id atrybutu.
     *
     * @param id kolejność
     */
    public void setId(int id) {
        this._id = id;
    }

    /**
     * Dodaje wartości do tego atrybutu
     *
     * @param p
     * @throws InvalidPropertyException
     */
    public void add(Property p) throws InvalidPropertyException {
        for (Property element : _wartości) {
            if (p == element) {
                throw new InvalidPropertyException("Not allowed to add the same property multiple times!");
            }
        }

        _wartości.add(p);

    }

    /**
     * Odszukuje czy we wartościach tego atrybutu mamy już taki id i nazwę/wartość
     *
     * @param id id/pozycja/kolejność
     * @param nazwa nazwa/wartość
     * @return boolean czy zawiera
     */
    public boolean find(int id, String nazwa) {
        for (Property prop : getWartości()) {
            if (prop.getId() == id && prop.getNazwa().equals(nazwa)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Usuwa z atrybutu zadaną wartość
     *
     * @param p wartość
     * @return boolean
     */
    public boolean remove(Property p) {
        if (_wartości.contains(p)) {
            _wartości.remove(p);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Zwraca postać łańcucha znaków dla atrybutu
     *
     * @return string nazwa/id
     */
    @Override
    public String toString() {
        if (_nazwa.length() > 0) {
            return String.valueOf(getId()) + "] " + getNazwa();
        } else {
            return String.valueOf(getId());
        }
    }

    /**
     * Odtwarza listę wartości na podstawie podanej tablicy
     *
     * @param array wartosci
     */
    public void recreateWartosci(Property[] wartosci) {
        _wartości.clear();

        for (Property p : wartosci) {
            _wartości.add(p);
        }
    }
}
