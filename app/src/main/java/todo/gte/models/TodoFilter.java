package todo.gte.models;

import android.content.Context;
import todo.gte.TodoApplication;
import todo.gte.controller.R;

/**
 * Created by gwenael on 05/06/17.
 */
public enum TodoFilter {

    ACTIVE(0,  R.string.spinner_active),
    FINISHED(1, R.string.spinner_finished),
    ALL(2, R.string.spinner_all);

    public int key;
    public int filterNameId;
    private TodoFilter(int key, int filterId) {
        this.key = key;
        this.filterNameId = filterId;
    }

    public String resource(Context ctx) {
        return ctx.getString(this.filterNameId);
    }
}
