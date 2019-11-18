package cs4330.cs.utep.ubeatcs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
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
public class EditProductDialogActivity extends AppCompatDialogFragment {

    //Text fields displayed in the dialog
    private EditText productName;
    private EditText productURL;
    //Listener used to call methods inside an activity
    private EditProductDialogListener listener;

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
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.activity_new_product_dialog, null);
        productName = view.findViewById(R.id.editNameString);
        productURL = view.findViewById(R.id.editURLString);
        builder.setView(view).setTitle("Edit Class")
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                .setPositiveButton("Ok", (dialog, which) -> {
                    String name = productName.getText().toString();
                    String url = productURL.getText().toString();
                    if (!name.equals("") && !url.equals("")) {
                        assert getArguments() != null;
                        listener.updateProduct(name, url, getArguments().getInt("index"));
                    } else {
                        Toast.makeText(getContext(), "Error editing the class.", Toast.LENGTH_SHORT).show();
                    }
                });
        assert getArguments() != null;
        productName.setText(getArguments().getString("name"));
        productURL.setText(getArguments().getString("url"));
        return builder.create();
    }

    /**
     * Initialize listener within the implemented activity.
     *
     * @param context context of which method its attached to
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (EditProductDialogListener) context;
    }

    /**
     * Interface used to link the activity to the listener.
     */
    public interface EditProductDialogListener {
        void updateProduct(String name, String url, int index);
    }
}
