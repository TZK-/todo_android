package todo.gte.controller.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import todo.gte.callbacks.OnTodoClickListener;
import todo.gte.controller.R;
import todo.gte.models.Todo;

public class TodoRecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView mTitleTextView;
    public FrameLayout mContainer;

    public TodoRecyclerViewHolder(View itemView) {
        super(itemView);

        this.mTitleTextView = (TextView) itemView.findViewById(R.id.todo_title);
        this.mContainer = (FrameLayout) itemView.findViewById(R.id.container);
    }

    public void bind(final Todo todo, final OnTodoClickListener listener) {
        this.mTitleTextView.setText(todo.title);
        this.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(todo);
            }
        });
    }
}