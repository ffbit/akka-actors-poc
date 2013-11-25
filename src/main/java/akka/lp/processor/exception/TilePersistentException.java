package akka.lp.processor.exception;

public class TilePersistentException extends RuntimeException {
    private static final long serialVersionUID = -2888615029041074203L;

    public TilePersistentException(String message) {
        super(message);
    }

    public TilePersistentException(String message, Throwable cause) {
        super(message, cause);
    }

}
