package movies.controllers;

import lombok.extern.slf4j.Slf4j;
import movies.MovieModelAssembler;
import movies.dto.Actor;
import movies.dto.Movie;
import movies.exceptions.ActorNotFoundException;
import movies.repos.ActorRepository;
import movies.repos.MovieRepository;
import movies.exceptions.MovieNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Slf4j
public class MoviesController {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;

    private final MovieModelAssembler movieAssembler;

    MoviesController(MovieRepository movieRepository, ActorRepository actorRepository, MovieModelAssembler movieAssembler) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.movieAssembler = movieAssembler;
    }

    // Movies
    @GetMapping("v1/movies")
    public CollectionModel<EntityModel<Movie>> allMovies() {

        List<EntityModel<Movie>> movies = movieRepository.findAll().stream()
            .map(movieAssembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(movies, linkTo(methodOn(MoviesController.class).allMovies()).withSelfRel());
    }

    @PostMapping("v1/movies")
    public Movie newMovie(@RequestBody Movie newMovie) {
        return movieRepository.save(newMovie);
    }

    @GetMapping("v1/movies/{id}")
    public EntityModel<Movie> oneMovie(@PathVariable int id) {
        Movie movie = movieRepository.findById(id)
            .orElseThrow(() -> new MovieNotFoundException(id));

        return movieAssembler.toModel(movie);
    }

    @PutMapping(value = "v1/movies/{id}", consumes = "text/uri-list", produces = "text/uri-list")
    public Movie editMovie(@RequestBody Movie newMovie, @PathVariable int id) {

        log.info(String.valueOf(newMovie));
        return movieRepository.findById(id)
            .map(movie -> {
                log.info(String.valueOf(newMovie));
                movie.setName(newMovie.getName());
                return movieRepository.save(movie);
            })
                .orElseGet(() -> {
                    newMovie.setId(id);
                    return movieRepository.save(newMovie);
            });
    }

    @DeleteMapping("v1/movies/{id}")
    public void DeleteMovie(@PathVariable int id) {
        movieRepository.deleteById(id);
    }

    @GetMapping("v1/actors")
    List<Actor> allActors() {
        return actorRepository.findAll();
    }

    @PostMapping("v1/actors")
    Actor newActor(@RequestBody Actor newActor) {
        return actorRepository.save(newActor);
    }

    @GetMapping("v1/actors/{id}")
    Actor oneActor(@PathVariable int id) {
        return actorRepository.findById(id)
                .orElseThrow(() -> new ActorNotFoundException(id));
    }

    @PutMapping("v1/actors/{id}")
    Actor editActor(@RequestBody Actor newActor, @PathVariable int id) {

        return actorRepository.findById(id)
                .map(actor -> {
                    actor.setName(newActor.getName());
                    return actorRepository.save(actor);
                })
                .orElseGet(() -> {
                    newActor.setId(id);
                    return actorRepository.save(newActor);
                });
    }

    @DeleteMapping("v1/actors/{id}")
    void DeleteActor(@PathVariable int id) {
        actorRepository.deleteById(id);
    }
}
