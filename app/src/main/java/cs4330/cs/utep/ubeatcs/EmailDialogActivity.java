package cs4330.cs.utep.ubeatcs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Objects;

/**
 * This class is used to display a dialog window that allows the user to edit an product.
 *
 * @author Isaias Leos
 */
public class EmailDialogActivity extends AppCompatDialogFragment {

    private EditText sendTo;
    private EditText body;
    private EditText subject;

    /**
     * Creates a custom Dialog Window.
     *
     * @param saveInstanceState last instance saved
     * @return instance to be displayed by the activity
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.email_dialog, null);
        sendTo = view.findViewById(R.id.sendToString);
        subject = view.findViewById(R.id.subjectString);
        body = view.findViewById(R.id.bodyString);
        builder.setView(view).setTitle("Send Email To")
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                .setPositiveButton("Ok", (dialog, which) -> {
                    String email = sendTo.getText().toString();
                    String subject = this.subject.getText().toString();
                    String body = this.body.getText().toString();
                    if (!email.equals("") && !body.equals("") && !subject.equals("")) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/html");
                        intent.putExtra(Intent.EXTRA_EMAIL, email);
                        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                        intent.putExtra(Intent.EXTRA_TEXT, body);
                        startActivity(Intent.createChooser(intent, "Send Email"));
                    } else {
                        Toast.makeText(getContext(), "Error sending email", Toast.LENGTH_SHORT).show();
                    }
                });
        assert getArguments() != null;
        sendTo.setText(getArguments().getString("email"));
        return builder.create();
    }
}
