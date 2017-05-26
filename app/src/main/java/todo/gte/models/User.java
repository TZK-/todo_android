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

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;

        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;

        return this;
    }

    public String getAuthToken() {
        return authToken;
    }

    public User setAuthToken(String authToken) {
        this.authToken = authToken;

        return this;
    }
}
