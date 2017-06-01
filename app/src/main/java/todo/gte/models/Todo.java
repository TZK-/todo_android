package todo.gte.models;

import java.io.Serializable;

public class Todo implements Serializable {

    public int id;
    public String title;
    public String description;
    public boolean ended;
    public User user;

    public Todo() {
        user = null;
        ended = false;
    }
}
