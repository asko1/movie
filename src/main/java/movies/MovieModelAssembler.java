package movies;

import movies.controllers.MoviesController;
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
                linkTo(methodOn(MoviesController.class).oneMovie(movie.getId())).withSelfRel(),
                linkTo(methodOn(MoviesController.class).allMovies()).withRel("movies"));
    }

}
