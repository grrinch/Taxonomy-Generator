/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author radmin
 */
public class WrongNumberException extends TaxonomyException {
    public WrongNumberException() {
        super();
    }

    public WrongNumberException(String message) {
        super(message);
    }

    public WrongNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongNumberException(Throwable cause) {
        super(cause);
    }
}