package todo.gte.controller.activities;

import android.content.Intent;
import android.graphics.*;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import todo.gte.controller.R;
import todo.gte.controller.adapters.DividerItemDecoration;
import todo.gte.controller.adapters.TodoAdapter;
import todo.gte.controller.adapters.TodoFilterAdapter;
import todo.gte.models.Todo;
import todo.gte.models.TodoFilter;
import todo.gte.utils.RestClient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public RecyclerView mTodoRecyclerView;
    public String mSelectedFilter;
    protected Spinner mFilterSpinner;
    protected TodoApplication mApplication;
    private String mSearchFieldValue;
    private TodoAdapter mAdapter;
    private Paint p = new Paint();

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

        mAdapter = new TodoAdapter(mApplication.getUser().todos(), new OnTodoClickListener() {
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
        mFilterSpinner = (Spinner) findViewById(R.id.filter);
        TodoFilterAdapter spinerAdapter = new TodoFilterAdapter(this, TodoFilter.values());

        spinerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFilterSpinner.setAdapter(spinerAdapter);
        mFilterSpinner.setOnItemSelectedListener(this);

        initSwipe();
    }

    private void fetchTodos() {
        RestClient restClient = new RestClient(mApplication.getUser());
        restClient.setSubscriber(this)
                .get("todos", getTodosCallback());
    }

    private void fetchFileteredTodos(String filteredValue, int filterKey) {
        RestClient restClient = new RestClient(mApplication.getUser());
        restClient.setSubscriber(this)
                .addParam("filter_type", "title")
                .addParam("filter_value", filteredValue);
        if(filterKey <2)
            restClient.addParam("status", Integer.toString(filterKey));
        restClient.get("todos", getFilteredTodosCallback());

    }

    private void showDialogTodo() {
        CreateTodoDialogFragment dialog = new CreateTodoDialogFragment();
        dialog.show(getSupportFragmentManager(), "todo_fragment");
    }

    private void searchThroughTodos() {
        EditText searchField = (EditText) findViewById(R.id.search_field);
        this.mSearchFieldValue = searchField.getText().toString();
        TodoFilter filter = (TodoFilter) mFilterSpinner.getSelectedItem();
        int filterKey = filter.key;
        fetchFileteredTodos(this.mSearchFieldValue, filterKey);
    }

    private void initSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                final long todoId = viewHolder.getItemId();
                if (direction == ItemTouchHelper.LEFT){
                    RestClient restClient = new RestClient(mApplication.getUser());
                    restClient.setSubscriber(ListActivity.this)
                            .delete("todos/" + todoId, new ASFRequestListener<JsonObject>() {
                                @Override
                                public void onSuccess(JsonObject response) {
                                    System.out.println("Delete success");
                                    System.out.println(todoId);
                                    mApplication.getUser().todos().remove(todoId);
                                    mTodoRecyclerView.getAdapter().notifyDataSetChanged();
                                }

                                @Override
                                public void onFailure(Exception e) {
                                    System.err.println(e.getMessage());
                                }
                            });
                } else {
                    System.out.println("right");
                    Toast eToast = Toast.makeText(ListActivity.this, "Update to ended", Toast.LENGTH_LONG);
                    eToast.show();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX > 0){
                        p.setColor(Color.parseColor("#b7b7b7"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_close);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mTodoRecyclerView);
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

    protected ASFRequestListener<JsonObject> getFilteredTodosCallback() {
        return new ASFRequestListener<JsonObject>() {
            @Override
            public void onSuccess(JsonObject response) {

                String todos = response.getAsJsonArray("todos").toString();
                Intent filterIntent = new Intent(ListActivity.this, FilteredListActivity.class);
                filterIntent.putExtra("todos", todos);
                startActivity(filterIntent);
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
