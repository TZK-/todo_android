package todo.gte.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import todo.gte.TodoApplication;
import todo.gte.callbacks.OnTodoClickListener;
import todo.gte.controller.R;
import todo.gte.controller.adapters.TodoAdapter;
import todo.gte.models.Todo;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class FilteredListActivity extends AppCompatActivity {

    protected TodoAdapter mAdapter;
    protected RecyclerView mRecyclerView;
    protected List<Todo> mTodoList;
    protected TodoApplication mApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_list);
        mApplication = (TodoApplication) getApplication();

        mRecyclerView = (RecyclerView) findViewById(R.id.filtered_todo_list);

        Intent intent = getIntent();
        Gson gson = new Gson();
        Type type = new TypeToken<List<Todo>>(){}.getType();
        String jsonTodoList = intent.getStringExtra("todos");
        mTodoList = gson.fromJson(jsonTodoList, type);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FilteredListActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        Collections.reverse(mTodoList);
        mAdapter = new TodoAdapter(mTodoList, new OnTodoClickListener() {
            @Override
            public void onItemClick(Todo todo) {
                Intent intent = new Intent(FilteredListActivity.this, TodoDetailsActivity.class);
                intent.putExtra("todo", todo);
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        System.out.println(mTodoList.size());
        System.out.println(mRecyclerView.getAdapter().getItemCount());
    }
}
