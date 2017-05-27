package todo.gte.models;

import java.util.ArrayList;
import java.util.List;

public class User {

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
