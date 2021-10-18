package movies.dto;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Data
public class Actor {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private int id;
    private String name;
    @ManyToMany(mappedBy = "actors") List<Movie> movies = new ArrayList<>();

    public Actor(String name) {
        this.name = name;
    }

    public Actor() {

    }
}
