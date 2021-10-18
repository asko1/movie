package movies.dto;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Data
public class Movie {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private int id;
    private String name;
    @ManyToMany(cascade = CascadeType.ALL) List<Actor> actors = new ArrayList<>();

    public Movie(String name, List<Actor> actors) {
        this.name = name;
        this.actors = actors;
    }

    public Movie(String name) {
        this(name, null);
    }

    public Movie() {

    }
}
