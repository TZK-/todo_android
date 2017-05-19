package todo.gte.todo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by muhlinge on 18/05/17.
 */
public class CreateTodoDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_create_todo, null);
        final EditText title = (EditText) dialogView.findViewById(R.id.title);
        final EditText description = (EditText) dialogView.findViewById(R.id.description);

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
                        Toast toast = Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_LONG);
                        toast.show();
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
