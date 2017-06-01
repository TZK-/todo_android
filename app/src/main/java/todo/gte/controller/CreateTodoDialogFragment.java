package todo.gte.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.github.asifmujteba.easyvolley.ASFRequestListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import todo.gte.TodoApplication;
import todo.gte.models.Todo;
import todo.gte.utils.RestClient;

import java.lang.reflect.Type;

/**
 * Created by muhlinge on 18/05/17.
 */
public class CreateTodoDialogFragment extends DialogFragment {

    private EditText mTitle;
    private EditText mDescription;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    protected void proceedAddRequest(String title, String description, String endpoint) {
        TodoApplication application = (TodoApplication) getActivity().getApplication();
        String token = application.getUser().authToken;
        RestClient restClient = new RestClient();
        restClient.setSubscriber(getActivity())
                .addHeader("Authorization", "Bearer " + token)
                .addParam("mTitleTextView", title)
                .addParam("description", description)
                .post(endpoint, addTodoCallback());


    }

    protected ASFRequestListener<JsonObject> addTodoCallback() {
        return new ASFRequestListener<JsonObject>() {
            @Override
            public void onSuccess(JsonObject response) {
                Gson gson = new Gson();
                Type type = new TypeToken<Todo>() {
                }.getType();
                Todo todo = gson.fromJson(response, type);
                addItemToAdapter(todo);
            }

            @Override
            public void onFailure(Exception e) {
                System.err.println(e.toString());
            }
        };
    }

    protected void addItemToAdapter(Todo todo) {
        TodoApplication app = (TodoApplication) getActivity().getApplication();
        app.getUser().todos().add(0, todo);
        RecyclerView todoRView = (RecyclerView) getActivity().findViewById(R.id.RTodoList);
        todoRView.getAdapter().notifyDataSetChanged();
        getDialog().dismiss();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_create_todo, null);
        this.mTitle = (EditText) dialogView.findViewById(R.id.title);
        this.mDescription = (EditText) dialogView.findViewById(R.id.description);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(dialogView)
                .setPositiveButton(R.string.create,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        })
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Do nothing
                            }
                        });

        final AlertDialog dialog = builder.create();

        dialog.show();
        //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send the positive button event back to the host activity
                String titleString = mTitle.getText().toString();
                String descriptionString = mDescription.getText().toString();

                if (titleString.trim().length() > 0) {
                    proceedAddRequest(titleString, descriptionString, "todos");
                } else {
                    mTitle.setError(getResources().getString(R.string.alert_fill_title));
                }
            }
        });

        return dialog;
    }

}
