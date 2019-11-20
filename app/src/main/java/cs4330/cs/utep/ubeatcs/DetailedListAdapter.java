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

public class DetailedListAdapter extends ArrayAdapter<String> {

    private final List<String> contentList;

    DetailedListAdapter(Context ctx, List<String> content) {
        super(ctx, -1, content);
        this.contentList = content;
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View row = convertView != null ? convertView
                : LayoutInflater.from(parent.getContext())
                .inflate(R.layout.class_view_detailed_item, parent, false);
        TextView text = row.findViewById(R.id.helpCategory);
        text.setText(contentList.get(position));
        if (contentList.get(position).toLowerCase().contains("exam")) {
            ImageView imageCategory = row.findViewById(R.id.imageCategory1);
            imageCategory.setImageResource(R.drawable.ic_list);
        }
        if (contentList.get(position).toLowerCase().contains("tutorial")) {
            ImageView imageCategory = row.findViewById(R.id.imageCategory1);
            imageCategory.setImageResource(R.drawable.ic_chat_bubble);
        }
        if (contentList.get(position).toLowerCase().contains("image")) {
            ImageView imageCategory = row.findViewById(R.id.imageCategory1);
            imageCategory.setImageResource(R.drawable.ic_burst_mode);
        }
        if (contentList.get(position).toLowerCase().contains("lecture")) {
            ImageView imageCategory = row.findViewById(R.id.imageCategory1);
            imageCategory.setImageResource(R.drawable.ic_note_add);
        }
        if (contentList.get(position).toLowerCase().contains("homework")) {
            ImageView imageCategory = row.findViewById(R.id.imageCategory1);
            imageCategory.setImageResource(R.drawable.ic_assignment);
        }
        return row;
    }

    public interface Listener {
        void doAction(int position);
    }
}

