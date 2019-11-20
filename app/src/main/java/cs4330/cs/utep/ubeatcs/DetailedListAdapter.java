package cs4330.cs.utep.ubeatcs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class DetailedListAdapter extends ArrayAdapter<String> {

    private final List<String> contentList;

    public DetailedListAdapter(Context ctx, List<String> content) {
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
        return row;
    }

    public interface Listener {
        void doAction(int position);
    }
}

