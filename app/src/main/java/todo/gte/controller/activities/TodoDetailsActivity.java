package todo.gte.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import todo.gte.controller.R;
import todo.gte.models.Todo;

public class TodoDetailsActivity extends AppCompatActivity {

    protected TextView mTodoTitleTextView;
    protected TextView mTodoDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_details);

        View contentView = findViewById(R.id.content_todo_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTodoTitleTextView = (TextView) contentView.findViewById(R.id.todo_detail_title);
        mTodoDescriptionTextView = (TextView) contentView.findViewById(R.id.todo_detail_description);

        Intent intent = getIntent();
        Todo todo = (Todo) intent.getSerializableExtra("todo");
        mTodoTitleTextView.setText(todo.title);
        mTodoDescriptionTextView.setText(todo.description);
    }

}
