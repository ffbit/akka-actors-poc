package akka.supervisor;

public class WorkerException extends RuntimeException {

    public WorkerException() {
    }

    public WorkerException(String message) {
        super(message);
    }

    public WorkerException(String message, Throwable cause) {
        super(message, cause);
    }

}
