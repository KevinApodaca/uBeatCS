package cs4330.cs.utep.ubeatcs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;

import java.util.ArrayList;
import java.util.List;

public class YoutubeViewList extends AppCompatActivity implements URLViewAdapterList.Listener {

    List<String> youtubeList = new ArrayList<>();
    ListView youtubeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_view_list);
        youtubeList = getIntent().getStringArrayListExtra("youtubeList");
        youtubeListView = findViewById(R.id.youtubeListView);
        youtubeListView.setOnItemClickListener((parent, view, position, id) -> player(position));
        youtubeListView.setOnItemLongClickListener((arg0, view, position, id) -> {
            createPopup(view, position);
            return true;
        });
        renewList();
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
                    edit(position);
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

    }

    @Override
    public void edit(int index) {

    }

    @Override
    public void player(int index) {
        Intent youtubeIntent = new Intent(this, YoutubeViewer.class);
        youtubeIntent.putExtra("youtubeID", youtubeList.get(index));
        startActivity(youtubeIntent);
    }
}
