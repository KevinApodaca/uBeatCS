package cs4330.cs.utep.ubeatcs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    List<String> subjectList = new ArrayList<>();
    DetailedListAdapter listAdapter;
    ClassInfo globalClassInfo;

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
        listAdapter = new DetailedListAdapter(getContext(), subjectList);
        subjectList.add("Exam Review");
        subjectList.add("Previous Exam");
        subjectList.add("Online Tutorial");
        subjectList.add("Lecture Note");
        subjectList.add("Example Test");
        subjectList.add("Example Quizzes");

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detailed_view, container, false);
        ListView listView = root.findViewById(R.id.listViewDetailed);
        listView.setAdapter(listAdapter);
        return root;
    }
}