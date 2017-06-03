package todo.gte.controller.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import todo.gte.callbacks.OnTodoClickListener;
import todo.gte.controller.R;
import todo.gte.models.Todo;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoRecyclerViewHolder> {

    private final OnTodoClickListener mTodoClickListener;
    private List<Todo> mTodoList;

    public TodoAdapter(List<Todo> todos, OnTodoClickListener listener) {
        setHasStableIds(true);
        this.mTodoList = todos;
        this.mTodoClickListener = listener;
    }

    public int getItemCount() {
        return this.mTodoList.size();
    }

    @Override
    public TodoRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View todoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_view, parent, false);

        return new TodoRecyclerViewHolder(todoView);
    }

    @Override
    public void onBindViewHolder(TodoRecyclerViewHolder holder, int i) {
        holder.bind(mTodoList.get(i), mTodoClickListener);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public long getItemId(int position) {
        return mTodoList.get(position).id;
    }


}
