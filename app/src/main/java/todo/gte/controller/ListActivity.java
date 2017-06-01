package todo.gte.controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.github.asifmujteba.easyvolley.ASFRequestListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import todo.gte.TodoApplication;
import todo.gte.models.Todo;
import todo.gte.utils.RestClient;

import java.lang.reflect.Type;
import java.util.List;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    protected RecyclerView mTodoRView;
    protected TodoApplication mApp;
    protected String mSelectedFilter;
    protected String mSearchFieldValue;

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
        mApp = (TodoApplication) getApplication();

        View contentView = findViewById(R.id.content_list_include);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTodoRView = (RecyclerView) contentView.findViewById(R.id.RTodoList);
        mTodoRView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mTodoRView.setLayoutManager(linearLayoutManager);

        TodoAdapter mAdapter = new TodoAdapter(mApp.getUser().todos());
        mTodoRView.setAdapter(mAdapter);

        getTodoList();

        // FAB to create new task, opens dialog
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListActivity.this.showDialogTodo();
            }
        });

        // Button to search through tasks
        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListActivity.this.searchThroughTasks();
            }
        });

        // Spinner to filter tasks
        Spinner filter_spinner = (Spinner) findViewById(R.id.filter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        filter_spinner.setAdapter(adapter);
        filter_spinner.setOnItemSelectedListener(this);
    }

    private void getTodoList() {
        RestClient restClient = new RestClient(mApp.getUser());
        restClient.setSubscriber(this)
                .get("todos", getTodosCallback());
    }

    private void showDialogTodo() {
        CreateTodoDialogFragment dialog = new CreateTodoDialogFragment();
        dialog.show(getSupportFragmentManager(), "todo_fragment");
    }

    private void searchThroughTasks() {

        // TODO : EFFECTUER LA RECHERCHE
        // valeur du spinner = this.mSelectedFilter;

        // Search field
        EditText searchField = (EditText) findViewById(R.id.search_field);
        this.mSearchFieldValue = searchField.getText().toString();

        Toast eToast = Toast.makeText(ListActivity.this, this.mSearchFieldValue, Toast.LENGTH_LONG);
        eToast.show();

//        Toast eToast = Toast.makeText(ListActivity.this, this.mSelectedFilter, Toast.LENGTH_LONG);
//        eToast.show();
    }

    protected ASFRequestListener<JsonObject> getTodosCallback() {
        return new ASFRequestListener<JsonObject>() {
            @Override
            public void onSuccess(JsonObject response) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Todo>>() {
                }.getType();
                List<Todo> todoList = gson.fromJson(response.getAsJsonArray("todos"), type);
                mApp.getUser().todos().addAll(todoList);

                mTodoRView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println(e.toString());
                Toast eToast = Toast.makeText(ListActivity.this, "Error la", Toast.LENGTH_LONG);
                eToast.show();
            }
        };
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        this.mSelectedFilter = parent.getItemAtPosition(pos).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
