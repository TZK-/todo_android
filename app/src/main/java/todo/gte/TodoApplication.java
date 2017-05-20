package todo.gte;

import android.app.Application;
import com.github.asifmujteba.easyvolley.EasyVolley;

public class TodoApplication extends Application {

    @Override
    public void onCreate() {
        EasyVolley.initialize(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        EasyVolley.dispose();
    }
}
