package cs4330.cs.utep.ubeatcs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment implements DetailedListAdapter.Listener {


    private static final String ARG_SECTION_NUMBER = "section_number";
    private ListView listView;
    private List<String> subjectList = new ArrayList<>();
    private DetailedListAdapter listAdapter;
    private ClassInfo globalClassInfo;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        globalClassInfo = new ClassInfo();
        globalClassInfo.setClass_name(bundle.getString("name"));
        globalClassInfo.setClass_number(bundle.getString("number"));
        globalClassInfo.setClass_teacher(bundle.getString("teacher"));
        globalClassInfo.setClass_url(bundle.getString("url"));
        globalClassInfo.setClass_email(bundle.getString("email"));
        listAdapter = new DetailedListAdapter(getContext(), subjectList);
        subjectList.add("Exam Review");
        subjectList.add("Tutorials");
        subjectList.add("Lecture Notes");
        subjectList.add("Image Notes");
        subjectList.add("Example Test/Quizzes");
        subjectList.add("Homework");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detailed_view, container, false);
        listView = root.findViewById(R.id.listViewDetailed);
        listView.setOnItemClickListener((parent, view, position, id) -> doAction(position));
        listView.setAdapter(listAdapter);
        return root;
    }

    @Override
    public void doAction(int position) {
        Log.e("Position", String.valueOf(position));
        switch (position) {
            case 0:
                toBrowser("http://www.cs.utep.edu/cheon/cs4330/index.php?page=exams");
                break;
            case 1:
                Intent youtubeIntent = new Intent(getContext(), YoutubeViewer.class);
                startActivity(youtubeIntent);
                break;
            case 2:
                toBrowser("http://www.cs.utep.edu/cheon/cs4330/index.php?page=notes");
                break;
            case 3:
                Intent i = new Intent(getContext(), TakePictureActivity.class);
                startActivity(i);
                break;
            case 4:
                Intent intent = new Intent(getContext(), PDFViewer.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                break;
            case 5:
                toBrowser("http://www.cs.utep.edu/cheon/cs4330/index.php?page=homework");
                break;
        }
    }

    public void toBrowser(String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.addDefaultShareMenuItem();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(getContext(), Uri.parse(url));
    }
}