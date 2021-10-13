package movies.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data @Getter @Setter
public class Movie {
    public String name;
    public int id;
    public Movie(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Movie() {

    }
}
