package todo.gte.models;

import java.util.ArrayList;
import java.util.List;

public class User {

    protected int id;
    protected String email;
    protected String authToken;
    protected List<Todo> todos;

    public User() {
        this.todos = new ArrayList<>();
    }
    public List<Todo> todos() {
        return todos;
    }
}
