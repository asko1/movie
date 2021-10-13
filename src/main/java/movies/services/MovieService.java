package movies.services;

import movies.dto.Movie;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    List<Movie> movies = new ArrayList<>();

    public List<Movie> mockMovies() {

        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Star Wars");

        Movie movie2 = new Movie();
        movie2.setId(2);
        movie2.setName("Star Wars 2");

        movies.add(movie1);
        movies.add(movie2);

        return movies;
    }
    // Have to initialize list with fake data both times
    // because java don't like stuff outside methods
    public Movie mockOneMovie(int id) {

        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Star Wars");

        Movie movie2 = new Movie();
        movie2.setId(2);
        movie2.setName("Star Wars 2");

        movies.add(movie1);
        movies.add(movie2);

        return movies.get(id - 1);
    }
}
