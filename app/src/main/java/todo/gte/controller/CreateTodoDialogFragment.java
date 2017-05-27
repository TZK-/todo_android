package todo.gte.controller;

import android.app.*;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.Toast;
import todo.gte.models.Todo;
import todo.gte.models.User;
import todo.gte.utils.RestClient;

/**
 * Created by muhlinge on 18/05/17.
 */
public class CreateTodoDialogFragment extends DialogFragment {

    private EditText title;
    private EditText description;

    protected void proceedAddRequest(Todo todo, String endpoint) {

        //TODO : ajouter la tâche 'todo' à l'user connecté en passant par l'api

//        String email = mEmailView.getText().toString();
//        String password = mPasswordView.getText().toString();

        RestClient restClient = new RestClient();
    }

    protected void addItemToAdapter(Todo todo) {

        //TODO : Récuperer l'array list et ajouter la tâche todoà celle-ci
        //TODO : Notifier l'adapter qu'on a ajouté un élément

//        listActivity.todoList.add(todo);
//        listActivity.todoRView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_create_todo, null);
        this.title = (EditText) dialogView.findViewById(R.id.title);
        this.description = (EditText) dialogView.findViewById(R.id.description);

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
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Send the positive button event back to the host activity
                String titleString = title.getText().toString();
                String descriptionString = description.getText().toString();

                if( titleString.trim().length() > 0){
                    Todo todo = new Todo();
                    //TODO : Il faudrait que je recupère l'user connecté

                    proceedAddRequest(todo, "add");
                    addItemToAdapter(todo);

                    dialog.dismiss();
                }
                else {
                    title.setError(getResources().getString(R.string.alert_fill_title));
                }
            }
        });

        return dialog;
    }

}
