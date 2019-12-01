package cs4330.cs.utep.ubeatcs;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * @author Isaias Leos
 */
public class DetailedListAdapter extends ArrayAdapter<String> {

    private final List<String> contentList;
    private StudyClass globalStudyClass;

    DetailedListAdapter(Context ctx, List<String> content, StudyClass globalStudyClass) {
        super(ctx, -1, content);
        this.contentList = content;
        this.globalStudyClass = globalStudyClass;
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View row = convertView != null ? convertView
                : LayoutInflater.from(parent.getContext())
                .inflate(R.layout.class_view_detailed_item, parent, false);
        TextView text = row.findViewById(R.id.helpCategory);
        text.setText(contentList.get(position));
        if (this.globalStudyClass.getClass_url().equals("https://www.utep.edu/cs/people/index.html")) {
            assert convertView != null;
            Toast toast = Toast.makeText(row.getContext(),
                    "Caution! Your professor doesn't have a website or the application couldn't find it!",
                    Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            View view2 = toast.getView();
            view2.setBackgroundColor(Color.rgb(2, 136, 209));
            TextView toastText = view2.findViewById(android.R.id.message);
            toastText.setTextColor(Color.rgb(33, 33, 33));
            toast.show();
        }
        if (contentList.get(position).toLowerCase().contains("exam")) {
            ImageView imageCategory = row.findViewById(R.id.imageCategory);
            imageCategory.setImageResource(R.drawable.ic_list);
        }
        if (contentList.get(position).toLowerCase().contains("tutorial")) {
            ImageView imageCategory = row.findViewById(R.id.imageCategory);
            imageCategory.setImageResource(R.drawable.ic_turned_in);
        }
        if (contentList.get(position).toLowerCase().contains("image")) {
            ImageView imageCategory = row.findViewById(R.id.imageCategory);
            imageCategory.setImageResource(R.drawable.ic_burst_mode);
        }
        if (contentList.get(position).toLowerCase().contains("lecture")) {
            ImageView imageCategory = row.findViewById(R.id.imageCategory);
            imageCategory.setImageResource(R.drawable.ic_note_add);
        }
        if (contentList.get(position).toLowerCase().contains("homework")) {
            ImageView imageCategory = row.findViewById(R.id.imageCategory);
            imageCategory.setImageResource(R.drawable.ic_assignment);
        }
        return row;
    }

    public interface Listener {
        void doAction(int position);
    }
}

