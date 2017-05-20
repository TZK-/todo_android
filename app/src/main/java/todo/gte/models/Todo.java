package todo.gte.models;

public class Todo {

    private int id;
    private String title;
    private String description;
    private boolean ended;
    private int user_id;

    public Todo(int id, String title, String description, boolean ended, int user_id) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.ended = ended;
        this.user_id = user_id;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
