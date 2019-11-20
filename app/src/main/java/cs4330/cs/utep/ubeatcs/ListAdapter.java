package cs4330.cs.utep.ubeatcs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class ListAdapter extends ArrayAdapter<ClassInfo> {

    private final List<ClassInfo> classNameList;

    public ListAdapter(Context ctx, List<ClassInfo> className) {
        super(ctx, -1, className);
        this.classNameList = className;
    }


    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View row = convertView != null ? convertView
                : LayoutInflater.from(parent.getContext())
                .inflate(R.layout.class_view_item, parent, false);
        ClassInfo currentClass = classNameList.get(position);
        String[] urlSplit = currentClass.getClass_url().split("/");
        String toCompare = urlSplit[urlSplit.length - 1];
        TextView nameView = row.findViewById(R.id.className);
        TextView teacherNameView = row.findViewById(R.id.classTeacher);
//        TextView emailView = row.findViewById(R.id.classEmail);
//        emailView.setText(currentClass.getClass_email());
        nameView.setText(currentClass.getClass_name());
        teacherNameView.setText(String.format("%s - %s", currentClass.getClass_number(), currentClass.getClass_teacher()));
        ImageView imageView = row.findViewById(R.id.imageView);
        if (toCompare.contains("gates")) {
            imageView.setImageResource(R.drawable.anngates);
        }
        if (toCompare.contains("chris")) {
            imageView.setImageResource(R.drawable.chriskiekintveld);
        }
        if (toCompare.contains("luc")) {
            imageView.setImageResource(R.drawable.luclongpre);
        }
        if (toCompare.contains("sal")) {
            imageView.setImageResource(R.drawable.salamahedit);
        }
        if (toCompare.contains("martine")) {
            imageView.setImageResource(R.drawable.martineceberio);
        }
        if (toCompare.contains("cheon")) {
            imageView.setImageResource(R.drawable.yoonsikcheon);
        }
        if (toCompare.contains("tost")) {
            imageView.setImageResource(R.drawable.deepaktosh);
        }
        if (toCompare.contains("eric")) {
            imageView.setImageResource(R.drawable.ericfreudenthal);
        }
        if (toCompare.contains("olac")) {
            imageView.setImageResource(R.drawable.olacfuentes);
        }
        if (toCompare.contains("hossain")) {
            imageView.setImageResource(R.drawable.mahmudhossain);
        }
        if (toCompare.contains("vladik")) {
            imageView.setImageResource(R.drawable.vladlik);
        }
        if (toCompare.contains("tosh")) {
            imageView.setImageResource(R.drawable.deepaktosh);
        }
        if (toCompare.contains("natalia")) {
            imageView.setImageResource(R.drawable.nataliavillanueva);
        }
        if (toCompare.contains("ward")) {
            imageView.setImageResource(R.drawable.nigelward);
        }
        return row;
    }

    public interface Listener {

        void delete(int index);

        void edit(int index);

    }
}

