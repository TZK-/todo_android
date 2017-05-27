package todo.gte.controller;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.github.asifmujteba.easyvolley.ASFRequestListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import todo.gte.models.Todo;
import todo.gte.utils.RestClient;

import java.lang.reflect.Type;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    public RecyclerView todoRView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        View contentView = findViewById(R.id.content_list_include);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        todoRView = (RecyclerView) contentView.findViewById(R.id.RTodoList);
        getTodoList();

        // Test code, put it in onResponse when its done


        //todoRView.getAdapter().notifyDataSetChanged();
        // FAB to create new task, opens dialog
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListActivity.this.showDialogTodo();
            }
        });
    }

    public void getTodoList() {
        RestClient restClient = new RestClient();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = sharedPreferences.getString("user_token", null);
        restClient.setSubscriber(ListActivity.this)
                .addHeader("Authorization", "Bearer " + token)
                .get("todos", getTodosCallback());
    }

    public void showDialogTodo() {
        CreateTodoDialogFragment dialog = new CreateTodoDialogFragment();
        dialog.show(getSupportFragmentManager(), "todo_fragment");

    }

    protected ASFRequestListener<JsonObject> getTodosCallback() {
        return new ASFRequestListener<JsonObject>() {
            @Override
            public void onSuccess(JsonObject response) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Todo>>() {}.getType();
                List<Todo> todoList = gson.fromJson(response.getAsJsonArray("todos"), type);

                todoRView.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                todoRView.setLayoutManager(linearLayoutManager);

                TodoAdapter mAdapter = new TodoAdapter(todoList);
                todoRView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println(e.toString());
                Toast eToast = Toast.makeText(ListActivity.this, "Error la", Toast.LENGTH_LONG);
                eToast.show();
            }
        };
    }
}
