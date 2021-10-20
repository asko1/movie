package movies.assembler;

import movies.controller.ActorController;
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
            linkTo(methodOn(ActorController.class).oneActor(actor.getId())).withSelfRel(),
            linkTo(methodOn(ActorController.class).allActors()).withRel("actors"),
            linkTo(methodOn(ActorController.class).allMovieActors(actor.getId())).withRel("actorMovies"));
    }
}
