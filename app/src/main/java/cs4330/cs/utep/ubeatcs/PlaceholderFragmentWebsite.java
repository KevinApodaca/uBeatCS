package cs4330.cs.utep.ubeatcs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragmentWebsite extends Fragment {

    ClassInfo globalClassInfo = new ClassInfo();

    public static PlaceholderFragmentWebsite newInstance() {
        PlaceholderFragmentWebsite fragment = new PlaceholderFragmentWebsite();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            globalClassInfo.setClass_name(bundle.getString("name"));
            globalClassInfo.setClass_number(bundle.getString("number"));
            globalClassInfo.setClass_teacher(bundle.getString("teacher"));
            globalClassInfo.setClass_url(bundle.getString("url"));
            globalClassInfo.setClass_email(bundle.getString("email"));
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_webview, container, false);
        WebView webView = root.findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewController());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(globalClassInfo.getClass_url());
        return root;
    }

    public class WebViewController extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}