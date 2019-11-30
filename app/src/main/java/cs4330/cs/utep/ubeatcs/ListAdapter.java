package cs4330.cs.utep.ubeatcs;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * @author Isaias Leos
 */
public class ListAdapter extends ArrayAdapter<StudyClass> {

    private final List<StudyClass> classNameList;

    ListAdapter(Context ctx, List<StudyClass> className) {
        super(ctx, -1, className);
        this.classNameList = className;
    }

    /**
     * Method will be used to set the email of the instructor depending on which teacher has been selected by the user.
     *
     * @param position    - current position in the list.
     * @param convertView - the converted view.
     * @param parent      - the parent view group.
     * @return the emails of the instructors.
     */
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View row = convertView != null ? convertView
                : LayoutInflater.from(parent.getContext())
                .inflate(R.layout.class_view_item, parent, false);
        StudyClass currentClass = classNameList.get(position);


        TextView nameView = row.findViewById(R.id.className);
        nameView.setText(currentClass.getClass_name());

        TextView teacherNameView = row.findViewById(R.id.classTeacher);
        teacherNameView.setText(String.format("CRN: %s - %s", currentClass.getClass_crn(), currentClass.getClass_teacher()));

        Log.e("Variables", currentClass.getClass_crn() + ":" + currentClass.getClass_teacher() + ":" + currentClass.getClass_email());
        TextView emailView = row.findViewById(R.id.classEmail);
        emailView.setText(currentClass.getClass_email());

        TextView websiteView = row.findViewById(R.id.websiteTextView);
        websiteView.setText(currentClass.getClass_url());

        ImageView imageView = row.findViewById(R.id.imageView);
        return row;


    }

    public interface Listener {

        void delete(int index);

        void edit(int index);

    }
}