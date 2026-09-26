package implementor;

public class ProviderException extends RuntimeException {

    public enum Reason { INVALID_REQUEST, TIMEOUT, UNAVAILABLE, UNKNOWN }

    private final Reason reason;

    public ProviderException(Reason reason, String message) {
        super(message);
        this.reason = reason;
    }

    public Reason reason() {
        return reason;
    }
}
