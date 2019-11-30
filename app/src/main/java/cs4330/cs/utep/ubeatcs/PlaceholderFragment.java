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
import java.util.Objects;

/**
 * A placeholder fragment containing a simple view.
 *
 * @author Isaias Leos
 */
public class PlaceholderFragment extends Fragment implements DetailedListAdapter.Listener {


    private static final String ARG_SECTION_NUMBER = "section_number";
    private StudyClass globalStudyClass;
    private List<String> subjectList = new ArrayList<>();
    private DetailedListAdapter listAdapter;

    static PlaceholderFragment newInstance(int index) {
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
        globalStudyClass = new StudyClass();
        assert bundle != null;
        globalStudyClass.setClass_name(bundle.getString("name"));
        globalStudyClass.setClass_number(bundle.getString("number"));
        globalStudyClass.setClass_teacher(bundle.getString("teacher"));
        globalStudyClass.setClass_url(bundle.getString("url"));
        globalStudyClass.setClass_email(bundle.getString("email"));
        globalStudyClass.setYoutubePlaylist(bundle.getStringArrayList("youtubeList"));
        globalStudyClass.setClass_crn(bundle.getString("crn"));
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
        ListView listView = root.findViewById(R.id.listViewDetailed);
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
                Intent youtubeIntent = new Intent(getContext(), YoutubeViewList.class);
                youtubeIntent.putStringArrayListExtra("youtubeList", (ArrayList<String>) globalStudyClass.getYoutubePlaylist());
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

    private void toBrowser(String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.addDefaultShareMenuItem();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(Objects.requireNonNull(getContext()), Uri.parse(url));
    }
}