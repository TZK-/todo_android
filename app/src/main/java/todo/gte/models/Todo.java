package todo.gte.models;

public class Todo {

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
