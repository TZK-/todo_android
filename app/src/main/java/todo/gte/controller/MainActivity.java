package todo.gte.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import com.google.gson.Gson;
import todo.gte.TodoApplication;
import todo.gte.models.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userJson = preferences.getString("user", null);

        // Load the appropriate activity depending if the user has already been logged in.
        Class activity = AuthActivity.class;
        if (userJson != null) {
            // Load user data into user instance
            Gson gson = new Gson();
            TodoApplication application = (TodoApplication) getApplication();
            application.setUser(gson.fromJson(userJson, User.class));

            activity = ListActivity.class;
        }

        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
