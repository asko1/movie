package movies;

import movies.controllers.MoviesController;
import movies.dto.Actor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ActorModelAssembler implements RepresentationModelAssembler<Actor, EntityModel<Actor>> {

    @Override
    public EntityModel<Actor> toModel(Actor actor) {

        return EntityModel.of(actor,
                linkTo(methodOn(MoviesController.class).oneActor(actor.getId())).withSelfRel(),
                linkTo(methodOn(MoviesController.class).allActors()).withRel("actors"));
    }
}
