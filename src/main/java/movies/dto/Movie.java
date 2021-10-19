package movies.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Data
public class Movie {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private int id;
    private String name;
    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("movies")
    List<Actor> actors = new ArrayList<>();

    public Movie(int id, String name, List<Actor> actors) {
        this.id = id;
        this.name = name;
        this.actors = actors;
    }

    public Movie(int id, String name) {
        this(id, name, null);
    }

    public Movie() {

    }
}
