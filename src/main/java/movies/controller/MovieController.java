package movies.controller;

import lombok.extern.slf4j.Slf4j;
import movies.assembler.ActorModelAssembler;
import movies.assembler.MovieModelAssembler;
import movies.dto.Actor;
import movies.dto.Movie;
import movies.repo.ActorRepository;
import movies.repo.MovieRepository;
import movies.exception.MovieNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@RestController
@Slf4j
public class MovieController {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final MovieModelAssembler movieAssembler;

    MovieController(MovieRepository movieRepository, ActorRepository actorRepository, MovieModelAssembler movieAssembler) {
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

        return CollectionModel.of(movies, linkTo(methodOn(MovieController.class).allMovies()).withSelfRel());
    }

    @PostMapping("v1/movies")
    public EntityModel<Movie> newMovie(@RequestBody Movie newMovie) {
        movieRepository.save(newMovie);
        return movieAssembler.toModel(newMovie);
    }

    @GetMapping("v1/movies/{id}")
    public EntityModel<Movie> oneMovie(@PathVariable int id) {
        Movie movie = movieRepository.findById(id)
            .orElseThrow(() -> new MovieNotFoundException(id));

        return movieAssembler.toModel(movie);
    }

    @PutMapping(value = "v1/movies/{id}")
    public EntityModel<Movie> editMovie(@RequestBody Movie newMovie, @PathVariable int id) {

        List<Actor> actors = new ArrayList<>();

        if (newMovie.getActors().size() != 0) {
            for (Actor actor: newMovie.getActors()) {
                actors.add(actorRepository.findById(actor.getId()).get());
            }
        }

        Movie updatedMovie = movieRepository.findById(id)
            .map(movie -> {

                if (newMovie.getName() != null) {
                    movie.setName(newMovie.getName());
                }

                if (newMovie.getActors().size() != 0) {
                    movie.setActors(actors);
                }

                return movieRepository.save(movie);
            })
                .orElseGet(() -> {

                    newMovie.setId(id);
                    return movieRepository.save(newMovie);
            });
        return movieAssembler.toModel(updatedMovie);
    }

    @GetMapping("v1/actors/{id}/movies")
    public CollectionModel<EntityModel<Movie>> allActorMovies(@PathVariable int id) {

        List<EntityModel<Movie>> movies = actorRepository.findById(id).get().getMovies().stream()
                .map(movieAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(movies, linkTo(methodOn(MovieController.class).allActorMovies(id)).withSelfRel());
    }

    @DeleteMapping("v1/movies/{id}")
    public void DeleteMovie(@PathVariable int id) {
        movieRepository.deleteById(id);
    }


    ////////////
    // Actors //
    ////////////

    /*@GetMapping("v1/movies/{id}/actors")
    public CollectionModel<EntityModel<Actor>> allMovieActors(@PathVariable int id) {

        List<EntityModel<Actor>> actors = movieRepository.findById(id).get().getActors().stream()
                .map(actorAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(actors, linkTo(methodOn(MoviesController.class).allMovieActors(id)).withSelfRel());
    }

    @GetMapping("v1/actors")
    public CollectionModel<EntityModel<Actor>> allActors() {

        List<EntityModel<Actor>> actors = actorRepository.findAll().stream()
                .map(actorAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(actors, linkTo(methodOn(MoviesController.class).allActors()).withSelfRel());
    }

    @PostMapping("v1/actors")
    EntityModel<Actor> newActor(@RequestBody Actor newActor) {
        actorRepository.save(newActor);
        return actorAssembler.toModel(newActor);
    }

    @GetMapping("v1/actors/{id}")
    public EntityModel<Actor> oneActor(@PathVariable int id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new ActorNotFoundException(id));

        return actorAssembler.toModel(actor);
    }

    @PutMapping(value = "v1/actors/{id}")
    public EntityModel<Actor> editActor(@RequestBody Actor newActor, @PathVariable int id) {

        List<Movie> movies = new ArrayList<>();

        if (newActor.getMovies() != null) {
            movies.add(movieRepository.findById(newActor.getMovies().get(0).getId()).get());
        }

        Actor updatedActor = actorRepository.findById(id)
                .map(actor -> {

                    if (newActor.getName() != null) {
                        actor.setName(newActor.getName());
                    }

                    if (newActor.getMovies() != null) {
                        actor.setMovies(movies);
                    }

                    return actorRepository.save(actor);
                })
                .orElseGet(() -> {
                    newActor.setId(id);
                    return actorRepository.save(newActor);
                });
        return actorAssembler.toModel(updatedActor);
    }

    @GetMapping("v1/actors/{id}/movies")
    public CollectionModel<EntityModel<Movie>> allActorMovies(@PathVariable int id) {

        List<EntityModel<Movie>> movies = actorRepository.findById(id).get().getMovies().stream()
                .map(movieAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(movies, linkTo(methodOn(MoviesController.class).allActorMovies(id)).withSelfRel());
    }

    @DeleteMapping("v1/actors/{id}")
    void DeleteActor(@PathVariable int id) {
        actorRepository.deleteById(id);
    }*/
}
