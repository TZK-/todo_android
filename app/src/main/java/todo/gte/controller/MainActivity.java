package todo.gte.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userToken = preferences.getString("user_token", null);
        System.out.println(userToken);
        // Load the appropriate activity depending if the user has already been logged in.
        // TODO Check if the token is still valid from the API
        Class activity = AuthActivity.class;
        if(userToken != null) {
            activity = ListActivity.class;
        }

        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
