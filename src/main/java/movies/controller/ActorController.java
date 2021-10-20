package movies.controller;

import movies.assembler.ActorModelAssembler;
import movies.assembler.MovieModelAssembler;
import movies.dto.Actor;
import movies.dto.Movie;
import movies.exception.ActorNotFoundException;
import movies.repo.ActorRepository;
import movies.repo.MovieRepository;
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
public class ActorController {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final ActorModelAssembler actorAssembler;

    ActorController(MovieRepository movieRepository, ActorRepository actorRepository, ActorModelAssembler actorAssembler) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.actorAssembler = actorAssembler;
    }

    @GetMapping("v1/movies/{id}/actors")
    public CollectionModel<EntityModel<Actor>> allMovieActors(@PathVariable int id) {

        List<EntityModel<Actor>> actors = movieRepository.findById(id).get().getActors().stream()
                .map(actorAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(actors, linkTo(methodOn(ActorController.class).allMovieActors(id)).withSelfRel());
    }

    @GetMapping("v1/actors")
    public CollectionModel<EntityModel<Actor>> allActors() {

        List<EntityModel<Actor>> actors = actorRepository.findAll().stream()
                .map(actorAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(actors, linkTo(methodOn(ActorController.class).allActors()).withSelfRel());
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
            for (Movie movie: newActor.getMovies()) {
                movies.add(movieRepository.findById(movie.getId()).get());
            }
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

    @DeleteMapping("v1/actors/{id}")
    void DeleteActor(@PathVariable int id) {
        actorRepository.deleteById(id);
    }
}
