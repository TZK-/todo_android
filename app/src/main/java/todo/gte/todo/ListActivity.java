package todo.gte.todo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    public RecyclerView todoRView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        View contentView = LayoutInflater.from(ListActivity.this).inflate(R.layout.content_list, null, false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RequestQueue queue = Volley.newRequestQueue(ListActivity.this);
        String getListUrl = "http://www.google.fr";
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, getListUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //TODO parse JSON Response, create todo list, add to recyclerView
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO display a toast ? Or fill list with warning message
                }
            });
            queue.add(stringRequest);
        } catch(RuntimeException e) {}
        // Test code, put it in onResponse when its done
        ArrayList<Todo> todoList = new ArrayList<>();
        todoList.add(new Todo(1, "Test todo 1", "", false, 1));
        todoList.add(new Todo(2, "Test todo 2", "", false, 1));
        todoRView = (RecyclerView) contentView.findViewById(R.id.RTodoList);
        todoRView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        todoRView.setLayoutManager(linearLayoutManager);

        TodoAdapter mAdapter = new TodoAdapter(todoList);
        todoRView.setAdapter(mAdapter);

        // FAB to create new task, opens dialog
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

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
}
