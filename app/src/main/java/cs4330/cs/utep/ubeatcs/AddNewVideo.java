package cs4330.cs.utep.ubeatcs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Objects;

/**
 * This class is used to display a dialog window that allows the user to edit a product.
 *
 * @author Isaias Leos
 */
public class AddNewVideo extends AppCompatDialogFragment {

    private EditText youtubeURL;
    private AddNewVideoListener listener;

    /**
     * Create a dialog to add a youtube URL to the application.
     *
     * @param saveInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.activity_new_video, null);
        youtubeURL = view.findViewById(R.id.youtubeURLString);
        builder.setView(view).setTitle("Add a new Youtube Video")
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                .setPositiveButton("OK", (dialog, which) -> {
                    String url = youtubeURL.getText().toString();
                    if (!url.equals("")) {
                        listener.add(url);
                    } else {
                        Toast.makeText(getContext(), "Error trying to add new course.", Toast.LENGTH_SHORT).show();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (AddNewVideoListener) context;
    }


    public interface AddNewVideoListener {
        void add(String name);
    }
}
