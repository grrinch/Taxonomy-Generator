package exceptions;

/**
 * Wyjątek rzucany przy okazji niewłaściwej właściwości
 * @author radmin
 */
public class InvalidPropertyException extends TaxonomyException {
    public InvalidPropertyException() {
        super();
    }

    public InvalidPropertyException(String message) {
        super(message);
    }

    public InvalidPropertyException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPropertyException(Throwable cause) {
        super(cause);
    }
}
