package todo.gte;

import android.app.Application;
import com.github.asifmujteba.easyvolley.EasyVolley;
import todo.gte.models.User;

public class TodoApplication extends Application {

    protected User user;

    @Override
    public void onCreate() {
        EasyVolley.initialize(getApplicationContext());
        user = null;
    }

    @Override
    public void onTerminate() {
        EasyVolley.dispose();
    }

    public User getUser() {
        return user;
    }

    public TodoApplication setUser(User user) {
        this.user = user;

        return this;
    }
}
