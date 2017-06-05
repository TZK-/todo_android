package todo.gte.controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import todo.gte.controller.R;
import todo.gte.models.TodoFilter;

import java.util.List;

/**
 * Created by gwenael on 05/06/17.
 */
public class TodoFilterAdapter extends ArrayAdapter<TodoFilter>{

    protected Context mContext;

    public TodoFilterAdapter(Context context, TodoFilter[] objects) {
        super(context, 0, objects);

        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item,parent, false);
        }
        TextView textHolder = (TextView) convertView;
        TodoFilter tdf = getItem(position);

        textHolder.setText(tdf.resource(this.mContext));
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item,parent, false);
        }
        TextView textHolder = (TextView) convertView;
        TodoFilter tdf = getItem(position);

        textHolder.setText(tdf.resource(this.mContext));
        return convertView;

    }
}
