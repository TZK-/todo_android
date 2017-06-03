package todo.gte.controller.activities;

import android.content.Intent;
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
import todo.gte.callbacks.OnTodoClickListener;
import todo.gte.controller.CreateTodoDialogFragment;
import todo.gte.controller.adapters.DividerItemDecoration;
import todo.gte.controller.R;
import todo.gte.controller.adapters.TodoAdapter;
import todo.gte.models.Todo;
import todo.gte.utils.RestClient;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public RecyclerView mTodoRecyclerView;
    public String mSelectedFilter;
    protected TodoApplication mApplication;
    private String mSearchFieldValue;

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
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        this.mSelectedFilter = parent.getItemAtPosition(pos).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mApplication = (TodoApplication) getApplication();

        View contentView = findViewById(R.id.content_list_include);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTodoRecyclerView = (RecyclerView) contentView.findViewById(R.id.RTodoList);
        mTodoRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mTodoRecyclerView.setLayoutManager(linearLayoutManager);

        TodoAdapter mAdapter = new TodoAdapter(mApplication.getUser().todos(), new OnTodoClickListener() {
            @Override
            public void onItemClick(Todo todo) {
                Intent intent = new Intent(ListActivity.this, TodoDetailsActivity.class);
                intent.putExtra("todo", todo);
                startActivity(intent);
            }
        });

        mTodoRecyclerView.setAdapter(mAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        mTodoRecyclerView.addItemDecoration(itemDecoration);

        fetchTodos();

        // FAB to create new task, opens dialog
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
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
                ListActivity.this.searchThroughTodos();
            }
        });

        // Spinner to filter tasks
        Spinner filterSpinner = (Spinner) findViewById(R.id.filter);
        ArrayAdapter<CharSequence> spinerAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner,
                android.R.layout.simple_spinner_item
        );

        spinerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(spinerAdapter);
        filterSpinner.setOnItemSelectedListener(this);
    }

    private void fetchTodos() {
        RestClient restClient = new RestClient(mApplication.getUser());
        restClient.setSubscriber(this)
                .get("todos", getTodosCallback());
    }

    private void showDialogTodo() {
        CreateTodoDialogFragment dialog = new CreateTodoDialogFragment();
        dialog.show(getSupportFragmentManager(), "todo_fragment");
    }

    private void searchThroughTodos() {
        // TODO : EFFECTUER LA RECHERCHE

        EditText searchField = (EditText) findViewById(R.id.search_field);
        this.mSearchFieldValue = searchField.getText().toString();

        Toast eToast = Toast.makeText(ListActivity.this, this.mSearchFieldValue, Toast.LENGTH_LONG);
        eToast.show();
    }

    protected ASFRequestListener<JsonObject> getTodosCallback() {
        return new ASFRequestListener<JsonObject>() {
            @Override
            public void onSuccess(JsonObject response) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Todo>>() {
                }.getType();
                List<Todo> todoList = gson.fromJson(response.getAsJsonArray("todos"), type);
                mApplication.getUser().todos().addAll(todoList);
                Collections.reverse(mApplication.getUser().todos());
                mTodoRecyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println(e.getMessage());
                Toast eToast = Toast.makeText(ListActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG);
                eToast.show();
            }
        };
    }
}
