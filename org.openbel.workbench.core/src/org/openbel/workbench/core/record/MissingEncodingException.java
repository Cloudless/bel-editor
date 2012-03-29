package org.openbel.workbench.core.record;

/**
 * MissingEncodingException defines a
 * {@link BELUncheckedException unchecked exception} that represents a
 * character encoding not available in the current environment.
 * 
 * @author Anthony Bargnesi &lt;abargnesi@selventa.com&gt;
 */
public class MissingEncodingException extends RuntimeException {
    private static final long serialVersionUID = 6448780981733610067L;

    public MissingEncodingException(String encoding) {
        super(createMessage(encoding));
    }
    
    public MissingEncodingException(String encoding, Throwable cause) {
        super(createMessage(encoding), cause);
    }

    private static String createMessage(String encoding) {
        return "Missing character encoding '" + encoding + "'.";
    }
}