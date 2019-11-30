package cs4330.cs.utep.ubeatcs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Isaias Leos
 */
public class MainActivity extends AppCompatActivity implements ListAdapter.Listener, NewProductDialogActivity.NewProductDialogListener, EditProductDialogActivity.EditProductDialogListener {

    List<StudyClass> classStudyList = new ArrayList<>();
    private ProgressBar progressBar;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StudyClass studyClass = new StudyClass();
        studyClass.setClass_teacher("Yoonsik Cheon");
        studyClass.setClass_url("http://www.cs.utep.edu/cheon/");
        studyClass.setClass_number("CS4330");
        studyClass.setClass_name("Mobile Application Development");
        studyClass.setClass_email("ycheon@utep.edu");
        classStudyList.add(studyClass);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            for (int i = 0; i < classStudyList.size(); i++) {
                getDetails(i, false);
            }
            renewList();
            swipeRefreshLayout.setRefreshing(false);
        });

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.INVISIBLE);

        listView = findViewById(R.id.homeView);
        listView.setOnItemClickListener((parent, view, position, id) -> itemClicked(position));
        listView.setOnItemLongClickListener((arg0, view, position, id) -> {
            createPopup(view, position);
            return true;
        });
        FloatingActionButton fab = findViewById(R.id.fab2);
        fab.setOnClickListener(view -> {
            // initiate the Toast with context, message and duration for the Toast
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Select a professor's course homepage",
                    Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 0);
            View view2 = toast.getView();
            view2.setBackgroundColor(Color.rgb(2, 136, 209));
            TextView text = view2.findViewById(android.R.id.message);
            text.setTextColor(Color.rgb(33, 33, 33));
            toast.show();
            toBrowser("https://www.utep.edu/cs/people/index.html");
        });
        handleShare(getIntent());
        renewList();
    }

    /**
     * Handles shared text and opens a new dialog to add the product to the current list.
     *
     * @param intent position of the product
     */
    private void handleShare(Intent intent) {
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                if (sharedText != null) {
                    openNewProductDialog(sharedText);
                }
            }
        }
    }

    /**
     * Open a chrome custom tab given the product.
     *
     * @param url product's url
     */
    public void toBrowser(String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.addDefaultShareMenuItem();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
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

    /**
     * This method is called whenever an icon is clicked from the toolbar.
     * Handles each button's action.
     *
     * @param item the menu item selected
     * @return if an item was selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_about:
                openAboutDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Create a user dialog box to show who created the application.
     */
    private void openAboutDialog() {
        Log.e("About", "Dialog Opened");
        AboutActivity dialog = new AboutActivity();
        dialog.show(getSupportFragmentManager(), "About Menu");
    }

    private void itemClicked(int position) {
        Intent i = new Intent(this, DetailedTabbedView.class);
        i.putExtra("name", classStudyList.get(position).getClass_name());
        i.putExtra("url", classStudyList.get(position).getClass_url());
        i.putExtra("number", classStudyList.get(position).getClass_number());
        i.putExtra("teacher", classStudyList.get(position).getClass_teacher());
        i.putExtra("email", classStudyList.get(position).getClass_email());
        startActivity(i);
    }

    private void renewList() {
        ListAdapter listAdapter = new ListAdapter(getBaseContext(), classStudyList);
        listView.setAdapter(listAdapter);
        listView.deferNotifyDataSetChanged();
    }

    /**
     * This method creates a dialog window to edit a currently existing item from the list.
     *
     * @param index location within the list
     */
    private void editProductDialog(int index) {
        EditProductDialogActivity dialog = new EditProductDialogActivity();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        bundle.putString("name", classStudyList.get(index).getClass_name());
        bundle.putString("number", classStudyList.get(index).getClass_number());
        bundle.putString("url", classStudyList.get(index).getClass_url());
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "Edit Class");
    }

    /**
     * This method creates a dialog window to add a new product.
     *
     * @param sharedText url being shared from other applications
     */
    private void openNewProductDialog(String sharedText) {
        NewProductDialogActivity dialog = new NewProductDialogActivity();
        if (sharedText != null) {
            Bundle bundle = new Bundle();
            bundle.putString("name", sharedText);
            bundle.putString("url", sharedText);
            dialog.setArguments(bundle);
        }
        dialog.show(getSupportFragmentManager(), "New Course Added");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void delete(int index) {
        classStudyList.remove(index);
        renewList();
        Log.e("Delete", "End");
    }

    @Override
    public void edit(int index) {
        editProductDialog(index);
        Log.e("Edit", "End");
    }

    /**
     * Method to add class to current list.
     */
    @Override
    public void addClass(String name, String number, String url) {
        StudyClass studyClass = new StudyClass(name, number, url);
        classStudyList.add(studyClass);
        getDetails(classStudyList.indexOf(studyClass), true);
    }

    private void getDetails(int position, boolean isNew) {
        Thread thread = new Thread(() -> {
            WebScrape webScrape = new WebScrape(classStudyList.get(position).getClass_url());
            classStudyList.get(position).setClass_teacher(webScrape.getName());
        });
        thread.start();
        progressBar.setVisibility(ProgressBar.VISIBLE);
        Thread uiThread = new Thread(() -> {
            while (true) {
                if (!thread.isAlive()) {
                    break;
                }
            }
            runOnUiThread(() -> {
                renewList();
                progressBar.setVisibility(ProgressBar.INVISIBLE);
            });
        });
        uiThread.start();
    }

    /**
     * Method to edit class from the list.
     */
    @Override
    public void update(String name, String number, String url, int index) {
        classStudyList.get(index).setClass_name(name);
        classStudyList.get(index).setClass_url(url);
        classStudyList.get(index).setClass_number(url);
        renewList();
    }
}
