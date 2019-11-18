package cs4330.cs.utep.ubeatcs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragmentWebsite extends Fragment {

    public static PlaceholderFragmentWebsite newInstance(int index) {
        PlaceholderFragmentWebsite fragment = new PlaceholderFragmentWebsite();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_webview, container, false);
        WebView webView = root.findViewById(R.id.webView);
        String customHtml = "<html><body><h1>Hello, uBeatCS </h1>" +
                "<h1>Heading 1</h1><h2>Heading 2</h2><h3>Heading 3</h3>" +
                "<p>This is a sample paragraph of static HTML In Web view</p>" +
                "</body></html>";
        webView.loadData(customHtml, "text/html", "UTF-8");
        return root;
    }
}