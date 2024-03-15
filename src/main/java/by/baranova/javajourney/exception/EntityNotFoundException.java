package by.baranova.javajourney.exception;
/**
 * Exception indicating that an entity was not found.
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code EntityNotFoundException}
     * with the specified detail message.
     *
     * @param message The detail message.
     */
    public EntityNotFoundException(final String message) {
        super(message);
    }
}
