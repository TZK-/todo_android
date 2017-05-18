package todo.gte.todo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by gwenael on 14/05/17.
 */
public class TodoAdapter extends RecyclerView.Adapter<TodoRecyclerViewHolder>{

    private ArrayList<Todo> todoList = new ArrayList<>();

    public TodoAdapter(ArrayList<Todo> todos) {
        this.todoList = todos;
    }

    public int getItemCount(){
        return this.todoList.size();
    }

    @Override
    public TodoRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int position) {

        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_view, parent, false);
        TodoRecyclerViewHolder viewHolder = new TodoRecyclerViewHolder(convertView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(TodoRecyclerViewHolder holder, int i) {
        holder.title.setText(this.todoList.get(i).getTitle());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
