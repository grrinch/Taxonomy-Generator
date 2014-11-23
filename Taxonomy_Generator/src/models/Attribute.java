package models;

import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil;
import exceptions.*;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.JOptionPane;

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
     * Lista jego właściwości.
     */
    private List<Property> _właściwości = new ArrayList<Property>();

    /* *
     * Konstruktor - pusty atrybut. Kiedy używamy:
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
     * @param właściwości właściwości atrybutu
     */
    public Attribute(int _id, String _nazwa, Property[] właściwości) {
        this._id = _id;
        this._nazwa = _nazwa;
        for (Property wlasciwosc : właściwości) {
            this._właściwości.add(wlasciwosc);
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
     * Zwraca wszystkie właściwości należące do tego atrybutu.
     *
     * @return
     */
    public Property[] getWłaściwości() {
        Collections.sort(_właściwości, new Comparator<Property>() {
            @Override
            public int compare(Property p1, Property p2) {

                return p1.getNazwa().compareTo(p2.getNazwa());
            }
        });
        return _właściwości.toArray(new Property[_właściwości.size()]);
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

    public void add(Property p) throws InvalidPropertyException {
        for (Property element : _właściwości) {
            if (p == element) {
                throw new InvalidPropertyException("Nie można wielokrotnie dodać tej samej właściwości!");
            }
        }

        _właściwości.add(p);

    }

    /**
     * Odszukuje czy we właściwościach tego atrybutu mamy już taki id i nazwę/wartość
     *
     * @param id id/pozycja/kolejność
     * @param nazwa nazwa/wartość
     * @return boolean czy zawiera
     */
    public boolean find(int id, String nazwa) {
        for (Property prop : getWłaściwości()) {
            if (prop.getId() == id && prop.getNazwa().equals(nazwa)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Zwraca postać łańcucha znaków dla atrybutu
     *
     * @return string nazwa/id
     */
    public String toString() {
        if (_nazwa.length() > 0) {
            return getNazwa();
        } else {
            return String.valueOf(getId());
        }
    }
}
