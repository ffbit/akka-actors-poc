package akka.lp.processor.exception;

public class PersistentException extends RuntimeException {
    private static final long serialVersionUID = -2888615029041074203L;

    public PersistentException(String message) {
        super(message);
    }

    public PersistentException(String message, Throwable cause) {
        super(message, cause);
    }

}
