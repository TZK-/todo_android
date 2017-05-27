package todo.gte.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import todo.gte.models.Todo;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoRecyclerViewHolder> {

    private List<Todo> todoList = new ArrayList<>();

    public TodoAdapter(List<Todo> todos) {
        this.todoList = todos;
    }

    public int getItemCount() {
        return this.todoList.size();
    }

    @Override
    public TodoRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int position) {

        View todoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_view, parent, false);
        return new TodoRecyclerViewHolder(todoView);

    }

    @Override
    public void onBindViewHolder(TodoRecyclerViewHolder holder, int i) {
        holder.title.setText(this.todoList.get(i).title);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
