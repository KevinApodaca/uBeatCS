package cs4330.cs.utep.ubeatcs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.browser.customtabs.CustomTabsIntent;

import java.util.Objects;

/**
 * @author Isaias Leos
 */
public class AboutActivity extends AppCompatDialogFragment {

    /**
     * Creates a custom Dialog Window to show information about the app creator.
     *
     * @param saveInstanceState last instance saved
     * @return instance to be displayed by the activity
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.activity_about, null);
        String website = "https://github.com/KevinApodaca/uBeatCS";
        TextView about = view.findViewById(R.id.about);
        about.setText(Html.fromHtml("<html>"
                + "<center>"
                + "<strong>uBeatCS v1.0</strong>"
                + "<br>Creators:<br><br><i><sup>Kevin Apodaca</sup></i><br><i><sup>Isaias Leos</sup></i>"
                + "<br><strong><a href=\"" + website + "\">Github</a><strong></center></html>"));
        about.setOnClickListener(v -> {
            CustomTabsIntent.Builder customChromeTab = new CustomTabsIntent.Builder();
            customChromeTab.addDefaultShareMenuItem();
            CustomTabsIntent customTabsIntent = customChromeTab.build();
            customTabsIntent.launchUrl(Objects.requireNonNull(getContext()), Uri.parse(website));
        });
        builder.setView(view).setPositiveButton("Close", (dialog, which) -> {
        });
        return builder.create();
    }
}
