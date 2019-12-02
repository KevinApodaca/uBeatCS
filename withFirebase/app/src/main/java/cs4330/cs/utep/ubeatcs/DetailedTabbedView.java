package cs4330.cs.utep.ubeatcs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class DetailedTabbedView extends AppCompatActivity {

    ClassInfo sendClass;
    private FloatingActionButton retrievePicture;
    private static final int LOADING_IMAGE_RESULT = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);
        Intent i = getIntent();
        sendClass = new ClassInfo(i.getStringExtra("name"), i.getStringExtra("teacher"), i.getStringExtra("number"), i.getStringExtra("url"), i.getStringExtra("email"));
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
        if (requestCode == LOADING_IMAGE_RESULT) {
            final Uri imageUri = data.getData();
//                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
//                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            configureImageDest(imageUri);

        }
    }

    private void configureImageDest(Uri image) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        FirebaseStorage.getInstance("gs://ubeatcs.appspot.com").getReference("UserUpload/" + image.hashCode()).putFile(image).addOnFailureListener(
                e -> Toast.makeText(getApplicationContext(), "Upload Failed", Toast.LENGTH_LONG).show()).addOnSuccessListener(
                taskSnapshot -> Toast.makeText(getApplicationContext(), "File Uploaded", Toast.LENGTH_LONG).show()).addOnProgressListener(taskSnapshot -> {
            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
            progressDialog.setMessage(((int) progress) + "% Uploaded... ");
        });
    }

    private void sendEmailDialog() {
        EmailDialogActivity dialog = new EmailDialogActivity();
        Bundle bundle = new Bundle();
        bundle.putString("email", sendClass.getClass_email());
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "Email");
    }

}