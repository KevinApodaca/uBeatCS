package cs4330.cs.utep.ubeatcs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author Isaias Leos
 */
public class DetailedTabbedView extends AppCompatActivity {

    private static final int LOADING_IMAGE_RESULT = 123;
    StudyClass sendClass;
    private FloatingActionButton retrievePicture;

    /**
     * TabbedView that will show two tabs, one being the user generated content and the next
     * being a homepage.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);
        Intent i = getIntent();
        sendClass = new StudyClass(i.getStringExtra("name"),
                i.getStringExtra("teacher"),
                i.getStringExtra("number"),
                i.getStringExtra("url"),
                i.getStringExtra("email"),
                i.getStringArrayListExtra("youtubeList"),
                i.getStringExtra("crn"));
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), sendClass);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> sendEmailDialog());

        retrievePicture = findViewById(R.id.addingPictureFab);
        retrievePicture.setOnClickListener(view -> {
            Intent gettingImageIntent = new Intent(Intent.ACTION_PICK);
            gettingImageIntent.setType("image/*");
            startActivityForResult(gettingImageIntent, LOADING_IMAGE_RESULT);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == LOADING_IMAGE_RESULT) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                configureImageDest(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void configureImageDest(Bitmap image) {
        //TODO display the dialog to the user that is selecting the destination of the bitmap, then ->
        //TODO send image to model-layer or activity that is going to save the image to display in the imageView
    }

    /**
     * Display a dialog that will allow the user to send an email using an application.
     */
    private void sendEmailDialog() {
        EmailDialogActivity dialog = new EmailDialogActivity();
        Bundle bundle = new Bundle();
        bundle.putString("email", sendClass.getClass_email());
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "Email");
    }

}