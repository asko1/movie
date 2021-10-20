package movies.exception;

public class ActorNotFoundException extends RuntimeException {
    public ActorNotFoundException(int id) {
        super("Could not find actor" + id);
    }
}
