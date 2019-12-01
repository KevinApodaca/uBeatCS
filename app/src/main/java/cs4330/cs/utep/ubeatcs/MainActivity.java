package cs4330.cs.utep.ubeatcs;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Isaias Leos
 */
public class MainActivity extends AppCompatActivity implements ListAdapter.Listener,
        NewProductDialogActivity.NewProductDialogListener,
        EditProductDialogActivity.EditProductDialogListener {

    private List<StudyClass> classStudyList = new ArrayList<>();
    private ProgressBar progressBar;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private Uri filePath;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storageReference = FirebaseStorage.getInstance().getReference();

        StudyClass studyClass = new StudyClass();
        studyClass.getYoutubePlaylist().add("https://www.youtube.com/watch?v=dFlPARW5IX8");
        studyClass.getYoutubePlaylist().add("https://www.youtube.com/watch?v=6ow3L39Wxmg");
        studyClass.getYoutubePlaylist().add("https://www.youtube.com/watch?v=rdGpT1pIJlw");
        studyClass.setClass_teacher("Yoonsik Cheon");
        studyClass.setClass_url("http://www.cs.utep.edu/cheon/");
        studyClass.setClass_number("CS4330");
        studyClass.setClass_name("Mobile Application Development");
        studyClass.setClass_crn("17842");
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
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Add a course!",
                    Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 0);
            View view2 = toast.getView();
            view2.setBackgroundColor(Color.rgb(2, 136, 209));
            TextView text = view2.findViewById(android.R.id.message);
            text.setTextColor(Color.rgb(33, 33, 33));
            toast.show();
            openNewProductDialog();
        });
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
                    openNewProductDialog();
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
        i.putExtra("crn", classStudyList.get(position).getClass_crn());
        i.putStringArrayListExtra("youtubeList", (ArrayList<String>) classStudyList.get(position).getYoutubePlaylist());
        startActivity(i);
    }

    private void renewList() {
        ListAdapter listAdapter = new ListAdapter(this, classStudyList);
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
        bundle.putString("crn", classStudyList.get(index).getClass_crn());
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "edited");
    }

    /**
     * This method creates a dialog window to add a new product.
     */
    private void openNewProductDialog() {
        NewProductDialogActivity dialog = new NewProductDialogActivity();
        dialog.show(getSupportFragmentManager(), "added");
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
    }

    @Override
    public void edit(int index) {
        editProductDialog(index);
        renewList();
    }

    /**
     * Method to add class to current list.
     */
    @Override
    public void addClass(String name, String crn) {
        StudyClass studyClass = new StudyClass(name, crn);
        classStudyList.add(studyClass);
        getDetails(classStudyList.indexOf(studyClass), true);
        renewList();
    }

    private void getDetails(int position, boolean isNew) {
        Thread thread = new Thread(() -> {
            String filter = classStudyList.get(position).getClass_crn();
            WebScrape webScrape = new WebScrape();
            try {
                classStudyList.get(position).setClass_teacher(webScrape.getInfo("name", filter));
                classStudyList.get(position).setClass_email(webScrape.getInfo("email", filter));
                classStudyList.get(position).setClass_url(webScrape.getInfo("link", filter));
            } catch (Exception ignored) {
                //TODO implement something
            }
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
    public void update(String name, String crn, int index) {
        classStudyList.get(index).setClass_name(name);
        classStudyList.get(index).setClass_crn(crn);
        renewList();
    }

    /**
     * Method to upload user-selected picture to database.
     */
    private void uploadFile(){
        if (filePath != null) {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading file...");
            progressDialog.show();

            StorageReference riversRef = storageReference.child("images/userDoc1.jpg");

            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            // Get a URL to the uploaded content
//                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"File Uploaded", Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>(){
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot){
                            double progress = (100 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage(((int) progress) + "% Uploaded...");
                        }
                    });
            ;
        }
        else{
            //display error toast
        }
    }
}
