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
public class NewProductDialogActivity extends AppCompatDialogFragment {

    private EditText productName;
    private EditText productURL;
    private NewProductDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.activity_new_product_dialog, null);
        productName = view.findViewById(R.id.editNameString);
        productURL = view.findViewById(R.id.editURLString);
        builder.setView(view).setTitle("Adding Class")
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                .setPositiveButton("OK", (dialog, which) -> {
                    String name = productName.getText().toString();
                    String url = productURL.getText().toString().toLowerCase();
                    if (!url.equals("")) {
                        listener.addClass(name, url);
                    } else {
                        Toast.makeText(getContext(), "Error while adding a new class.", Toast.LENGTH_SHORT).show();
                    }
                });
        if (getArguments() != null) {
            productURL.setText(getArguments().getString("url"));
        }
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (NewProductDialogListener) context;
    }

    public interface NewProductDialogListener {
        void addClass(String name, String url);
    }
}
