package movies.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data @Getter @Setter
public class Actor {
    private String name;
    private int id;
    public Actor(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Actor() {

    }
}
