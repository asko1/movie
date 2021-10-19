package movies.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Data
public class Actor {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private int id;
    private String name;
    @JsonIgnoreProperties("actors")
    @ManyToMany(mappedBy = "actors") List<Movie> movies = new ArrayList<>();

    public Actor(int id, String name, List<Movie> movies) {
        this.id = id;
        this.name = name;
        this.movies = movies;
    }

    public Actor(int id, String name) {
        this(id, name, null);
    }

    public Actor() {

    }
}
