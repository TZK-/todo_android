package todo.gte.models;

import java.io.Serializable;

public class Todo implements Serializable {

    public int id;
    public String title;
    public String description;
    public boolean ended;

    public Todo() {
        ended = false;
    }
}
