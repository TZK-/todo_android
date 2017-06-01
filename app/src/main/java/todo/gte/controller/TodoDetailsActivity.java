package todo.gte.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import todo.gte.models.Todo;

public class TodoDetailsActivity extends AppCompatActivity {

    protected TextView todoTitle;
    protected TextView todoDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_details);
        View contentView = findViewById(R.id.content_todo_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        todoTitle = (TextView) contentView.findViewById(R.id.todo_detail_title);
        todoDescription = (TextView) contentView.findViewById(R.id.todo_detail_description);

        Intent intent = getIntent();
        Todo todo = (Todo) intent.getSerializableExtra("todo");
        todoTitle.setText(todo.title);
        todoDescription.setText(todo.description);
    }

}
