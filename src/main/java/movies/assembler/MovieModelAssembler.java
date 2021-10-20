package movies.assembler;

import movies.controller.ActorController;
import movies.controller.MovieController;
import movies.dto.Movie;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MovieModelAssembler implements RepresentationModelAssembler<Movie, EntityModel<Movie>> {

    @Override
    public EntityModel<Movie> toModel(Movie movie) {

        return EntityModel.of(movie,
                linkTo(methodOn(MovieController.class).oneMovie(movie.getId())).withSelfRel(),
                linkTo(methodOn(MovieController.class).allMovies()).withRel("movies"),
                linkTo(methodOn(MovieController.class).allActorMovies(movie.getId())).withRel("movieActors"));
    }
}
