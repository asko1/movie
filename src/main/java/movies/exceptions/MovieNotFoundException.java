package movies.exceptions;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(int id) {
        super("Could not find movie" + id);
    }
}
