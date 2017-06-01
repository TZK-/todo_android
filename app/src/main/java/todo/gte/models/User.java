package todo.gte.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    public int id;
    public String email;
    public String authToken;
    protected List<Todo> todos;

    public User() {
        this.todos = new ArrayList<>();
    }

    public List<Todo> todos() {
        return todos;
    }
}
