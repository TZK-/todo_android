package todo.gte.models;

public class Todo {

    protected int id;
    protected String title;
    protected String description;
    protected boolean ended;
    protected User user;

    public Todo(int id, String title, String description, boolean ended, User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.ended = ended;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
