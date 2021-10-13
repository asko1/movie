package movies.controller;

import lombok.extern.slf4j.Slf4j;
import movies.dto.Movie;
import movies.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class MoviesController {

    @Autowired
    MovieService movieService;

    @GetMapping("v1/films")
    List<Movie> all() {
        return movieService.mockMovies();
    }

    @GetMapping("v1/films/{id}")
    Movie one(@PathVariable Integer id) {
        return movieService.mockOneMovie(id);
    }
}
