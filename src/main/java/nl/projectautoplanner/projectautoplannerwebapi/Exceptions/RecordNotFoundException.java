package nl.projectautoplanner.projectautoplannerwebapi.Exceptions;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String message) {
        super(message);
    }
    public RecordNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
