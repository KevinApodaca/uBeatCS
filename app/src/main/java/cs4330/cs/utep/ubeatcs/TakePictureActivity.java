package cs4330.cs.utep.ubeatcs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TakePictureActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String currentPhotoPath;
    private ImageView takePictureImageView;
    private Button takePictureButton;

    private View.OnClickListener capture = view -> {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            dispatchTakePictureIntent();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);
        Button captureButton = findViewById(R.id.capture);
        captureButton.setOnClickListener(capture);
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            captureButton.setEnabled(false);
        }
        takePictureImageView = findViewById(R.id.picture);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "cs4330.cs.utep.ubeatcs.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            File imgFile = new File(currentPhotoPath);
            if (imgFile.exists()) {
                takePictureImageView.setImageURI(Uri.fromFile(imgFile));
            }
        }
    }
/**
 * Method will generate the image that the user has taken.
 * TODO implement storage and uploading of the image to the server.
 * @return the image.
 * @throws IOException
 */
    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".png", storageDir);
        
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

}