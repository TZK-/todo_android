package todo.gte;

import android.app.Application;
import com.github.asifmujteba.easyvolley.EasyVolley;
import todo.gte.models.User;

public class TodoApplication extends Application {

    protected User mUser;

    @Override
    public void onCreate() {
        EasyVolley.initialize(getApplicationContext());
        mUser = null;
    }

    @Override
    public void onTerminate() {
        EasyVolley.dispose();
    }

    public User getUser() {
        return mUser;
    }

    public TodoApplication setUser(User user) {
        mUser = user;

        return this;
    }
}
