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
        TextView emailView = row.findViewById(R.id.classEmail);
        emailView.setText(currentClass.getClass_email());

        TextView websiteView = row.findViewById(R.id.websiteTextView);
        websiteView.setText(currentClass.getClass_url());

        ImageView imageView = row.findViewById(R.id.imageView);

        String[] urlSplit;
        String toCompare = "";
        try {
            urlSplit = currentClass.getClass_teacher().split(" ");
            toCompare = urlSplit[1].toLowerCase();
        } catch (NullPointerException ignored) {
        }
        if (toCompare.contains("aguirre")) {
            imageView.setImageResource(R.drawable.diegoaguirre);
        }

        if (toCompare.contains("roach")) {
            imageView.setImageResource(R.drawable.stevenroach);
        }

        if (toCompare.contains("acosta")) {
            imageView.setImageResource(R.drawable.jaimeacosta);
        }
        if (toCompare.contains("chadderdon")) {
            imageView.setImageResource(R.drawable.logalchadderdon);
        }

        if (toCompare.contains("mejia")) {
            imageView.setImageResource(R.drawable.danielmejia);
        }

        if (toCompare.contains("tai")) {
            imageView.setImageResource(R.drawable.elsetairamirez);
        }

        if (toCompare.contains("c")) {
            imageView.setImageResource(R.drawable.juliourenda);
        }

        if (toCompare.contains("kiekintveld")) {
            imageView.setImageResource(R.drawable.chriskiekintveld);
        }

        if (toCompare.contains("k.")) {
            imageView.setImageResource(R.drawable.maryroy);
        }

        if (toCompare.contains("longpre")) {
            imageView.setImageResource(R.drawable.luclongpre);
        }

        if (toCompare.contains("salamah")) {
            imageView.setImageResource(R.drawable.salamahedit);
        }

        if (toCompare.contains("ceberio")) {
            imageView.setImageResource(R.drawable.martineceberio);
        }

        if (toCompare.contains("cheon")) {
            imageView.setImageResource(R.drawable.yoonsikcheon);
        }

        if (toCompare.contains("akbar")) {
            imageView.setImageResource(R.drawable.monicaakbar);
        }

        if (toCompare.contains("badreddin")) {
            imageView.setImageResource(R.drawable.badreddin);
        }

        if (toCompare.contains("freudenthal")) {
            imageView.setImageResource(R.drawable.ericfreudenthal);
        }

        if (toCompare.contains("deblasio")) {
            imageView.setImageResource(R.drawable.danieldeblasio);
        }

        if (toCompare.contains("fuentes")) {
            imageView.setImageResource(R.drawable.olacfuentes);
        }

        if (toCompare.contains("shahriar")) {
            imageView.setImageResource(R.drawable.mahmudhossain);
        }

        if (toCompare.contains("kreinovich")) {
            imageView.setImageResource(R.drawable.vladlik);
        }

        if (toCompare.contains("villanueva")) {
            imageView.setImageResource(R.drawable.nataliavillanueva);
        }

        if (toCompare.contains("nigel")) {
            imageView.setImageResource(R.drawable.nigelward);
        }
        return row;


    }

    public interface Listener {

        void delete(int index);

        void edit(int index);

    }
}