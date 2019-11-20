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

    private EditText classNumber;
    private EditText classUrl;
    private EditText classNm;
    private NewProductDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.activity_new_product_dialog, null);
        classNm = view.findViewById(R.id.editClassNameString);
        classNumber = view.findViewById(R.id.editNumberString);
        classUrl = view.findViewById(R.id.editURLString);
        builder.setView(view).setTitle("Adding Class")
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                .setPositiveButton("OK", (dialog, which) -> {
                    String className = classNm.getText().toString();
                    String number = classNumber.getText().toString();
                    String url = classUrl.getText().toString().toLowerCase();
                    if (!url.equals("") && !number.equals("") && !className.equals("")) {
                        listener.addClass(className, number, url);
                    } else {
                        Toast.makeText(getContext(), "Error trying to add new course.", Toast.LENGTH_SHORT).show();
                    }
                });
        if (getArguments() != null) {
            classUrl.setText(getArguments().getString("url"));
        }//TODO ADDING CLASS
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (NewProductDialogListener) context;
    }

    public interface NewProductDialogListener {
        void addClass(String className, String name, String url);
    }
}
