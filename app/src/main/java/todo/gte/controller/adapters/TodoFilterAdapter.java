package todo.gte.controller.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import todo.gte.models.TodoFilter;

import java.util.List;

/**
 * Created by gwenael on 05/06/17.
 */
public class TodoFilterAdapter extends ArrayAdapter<TodoFilter>{

    public TodoFilterAdapter(Context context, int resource, List<TodoFilter> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //TODO: à compléter pour afficher la chaine correspondante
        return convertView;
    }
}
