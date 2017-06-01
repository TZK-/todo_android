package todo.gte.controller;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractSwipeableItemViewHolder;
import todo.gte.models.Todo;

public class TodoRecyclerViewHolder extends AbstractSwipeableItemViewHolder {

    public TextView title;
    public FrameLayout container;

    public TodoRecyclerViewHolder(View itemView) {
        super(itemView);

        this.title = (TextView) itemView.findViewById(R.id.todo_title);
        this.container = (FrameLayout) itemView.findViewById(R.id.container);
    }

    @Override
    public View getSwipeableContainerView() {
        return container;
    }

    public void bind(final Todo todo, final OnTodoClickListener listener) {
        this.title.setText(todo.title);
        this.container.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(todo);
            }
        });
    }
}