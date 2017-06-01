package todo.gte.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultActionMoveToSwipedDirection;
import todo.gte.models.Todo;

import java.util.ArrayList;
import java.util.List;

import static com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemConstants.DRAWABLE_SWIPE_LEFT_BACKGROUND;
import static com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemConstants.REACTION_CAN_SWIPE_LEFT;

public class TodoAdapter extends RecyclerView.Adapter<TodoRecyclerViewHolder> implements SwipeableItemAdapter<TodoRecyclerViewHolder> {

    private List<Todo> todoList = new ArrayList<>();
    private final OnTodoClickListener listener;

    public TodoAdapter(List<Todo> todos, OnTodoClickListener listener) {
        setHasStableIds(true);
        this.todoList = todos;
        this.listener = listener;
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
        holder.bind(todoList.get(i), listener);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public long getItemId(int position) {
        return todoList.get(position).id;
    }

    @Override
    public int onGetSwipeReactionType(TodoRecyclerViewHolder holder, int position, int x, int y) {
        return REACTION_CAN_SWIPE_LEFT;
    }

    @Override
    public void onSetSwipeBackground(TodoRecyclerViewHolder holder, int position, int type) {
        //
    }

    @Override
    public SwipeResultAction onSwipeItem(TodoRecyclerViewHolder holder, final int position, int result) {
        return new SwipeResultActionMoveToSwipedDirection() {
            // You can override these three methods
            // - void onPerformAction()
            // - void onSlideAnimationEnd()
            // - void onCleanUp()

            @Override
            protected void onPerformAction() {
                System.out.println("OK SLIDE");
            }
        };
    }

    public void bind() {

    }
}
