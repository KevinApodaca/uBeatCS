package cs4330.cs.utep.ubeatcs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

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

public class MainActivity extends AppCompatActivity implements ListAdapter.Listener, NewProductDialogActivity.NewProductDialogListener, EditProductDialogActivity.EditProductDialogListener {

    List<ClassInfo> classList = new ArrayList<>();
    private ProgressBar progressBar;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(() -> {
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
        fab.setOnClickListener(view -> toBrowser("https://www.utep.edu/cs/people/index.html"));
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
    private boolean createPopup(View view, int position) {
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
        return false;
    }

    private void itemClicked(int position) {
        Intent i = new Intent(this, DetailedView.class);
        i.putExtra("name", classList.get(position).getClass_name());
        i.putExtra("url", classList.get(position).getClass_url());
        i.putExtra("number", classList.get(position).getClass_number());
        i.putExtra("teacher", classList.get(position).getClass_teacher());
        startActivity(i);
    }

    private void renewList() {
        ListAdapter listAdapter = new ListAdapter(getBaseContext(), classList);
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
        bundle.putString("name", classList.get(index).getClass_name());
        bundle.putString("number", classList.get(index).getClass_number());
        bundle.putString("url", classList.get(index).getClass_url());
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
        dialog.show(getSupportFragmentManager(), "New Class Added");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void delete(int index) {
        classList.remove(index);
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
     *
     * @param name
     * @param url
     */
    @Override
    public void addClass(String name, String number, String url) {
        ClassInfo classInfo = new ClassInfo(name, number, url);
        classList.add(classInfo);
        getInfo(classList.indexOf(classInfo), true);
    }

    private void getInfo(int position, boolean isNew) {
        Thread priceThread = new Thread(() -> {
            WebScrape webScrape = new WebScrape(classList.get(position).getClass_url());
            classList.get(position).setClass_teacher(webScrape.getTitle());
        });
        priceThread.start();
        progressBar.setVisibility(ProgressBar.VISIBLE);
        Thread uiThread = new Thread(() -> {
            while (true) {
                if (!priceThread.isAlive()) {
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
     *
     * @param name
     * @param url
     * @param index
     */
    @Override
    public void update(String name, String number, String url, int index) {

    }
}
