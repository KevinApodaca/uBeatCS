package cs4330.cs.utep.ubeatcs;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * @author Isaias Leos
 */
public class URLViewAdapterList extends ArrayAdapter<String> {

    private final List<String> urlList;

    URLViewAdapterList(Context ctx, List<String> urls) {
        super(ctx, -1, urls);
        this.urlList = urls;
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
                .inflate(R.layout.youtube_list, parent, false);
        TextView youtubeTextView = row.findViewById(R.id.youtubeTextView);
        Log.e("URLViewAdapterList", urlList.get(position));
        youtubeTextView.setText(urlList.get(position));
        return row;
    }

    public interface Listener {

        void delete(int index);

        void edit(int index);

        void player(int index);

    }
}