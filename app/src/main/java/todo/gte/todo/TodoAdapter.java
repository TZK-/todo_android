package todo.gte.todo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gwenael on 14/05/17.
 */
public class TodoAdapter extends ArrayAdapter<Todo> {

    private RecyclerView adapter;

    public TodoAdapter(Context context, List<Todo> todos) {
        super(context, 0, todos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_view,parent, false);
        }
        Todo todo = getItem(position);

        TextView todoTitle = (TextView) convertView.findViewById(R.id.title);

        todoTitle.setText(todo.getTitle());

        return convertView;
    }

    public void setAdapter(RecyclerView adapter) {
        this.adapter = adapter;
    }
}
