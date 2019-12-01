package cs4330.cs.utep.ubeatcs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class YoutubeViewList extends AppCompatActivity implements URLViewAdapterList.Listener, AddNewVideo.AddNewVideoListener {

    List<String> youtubeList = null;
    ListView youtubeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_view_list);
        youtubeList = getIntent().getStringArrayListExtra("youtubeList");
        if (youtubeList == null) {
            youtubeList = new ArrayList<>();
            youtubeList.add("https://www.youtube.com/watch?v=ScMzIvxBSi4");
        }
        youtubeListView = findViewById(R.id.youtubeListView);
        youtubeListView.setOnItemClickListener((parent, view, position, id) -> player(position));
        youtubeListView.setOnItemLongClickListener((arg0, view, position, id) -> {
            createPopup(view, position);
            return true;
        });
        FloatingActionButton fab = findViewById(R.id.fab3);
        fab.setOnClickListener(view -> {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Add a video!",
                    Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 0);
            View view2 = toast.getView();
            view2.setBackgroundColor(Color.rgb(2, 136, 209));
            TextView text = view2.findViewById(android.R.id.message);
            text.setTextColor(Color.rgb(33, 33, 33));
            toast.show();
            addYoutubeVideoDialog();
        });
        renewList();
    }

    /**
     * This method creates a dialog window to add a new youtube URL.
     */
    private void addYoutubeVideoDialog() {
        AddNewVideo dialog = new AddNewVideo();
        dialog.show(getSupportFragmentManager(), "added");
    }

    private void renewList() {
        URLViewAdapterList listAdapter = new URLViewAdapterList(getBaseContext(), youtubeList);
        youtubeListView.setAdapter(listAdapter);
        youtubeListView.deferNotifyDataSetChanged();
    }

    /**
     * This creates a popup menu when an product is clicked and performs and
     * action depending on what item was clicked.
     *
     * @param view     current view
     * @param position The position of the clicked product in the list.
     */
    @SuppressLint("RestrictedApi")
    private void createPopup(View view, int position) {
        PopupMenu menu = new PopupMenu(this, view);
        menu.inflate(R.menu.menu_popup);
        menu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_delete:
                    delete(position);
                    return true;
                case R.id.menu_edit:
                    return true;
                default:
                    return false;
            }
        });
        MenuPopupHelper menuHelper = new MenuPopupHelper(this, (MenuBuilder) menu.getMenu(), view);
        menuHelper.setForceShowIcon(true);
        menuHelper.setGravity(Gravity.END);
        menuHelper.show();
    }

    @Override
    public void delete(int index) {
        youtubeList.remove(index);
        renewList();
    }

    @Override
    public void player(int index) {
        Intent youtubeIntent = new Intent(this, YoutubeViewer.class);
        youtubeIntent.putExtra("youtubeID", youtubeList.get(index));
        startActivity(youtubeIntent);
    }

    @Override
    public void add(String url) {
        youtubeList.add(url);
        renewList();
    }
}
