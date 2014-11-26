package exceptions;

/**
 * Ogólna klasa wyjątków aplikacji
 *
 * @author radmin
 */
public class TaxonomyException extends Exception {

    public TaxonomyException() {
        super();
    }

    public TaxonomyException(String message) {
        super(message);
    }

    public TaxonomyException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaxonomyException(Throwable cause) {
        super(cause);
    }
}
