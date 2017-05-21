package todo.gte.todo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class TodoRecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView title;

    public TodoRecyclerViewHolder(View itemView){
        super(itemView);

        this.title = (TextView) itemView.findViewById(R.id.todo_title);
    }
}