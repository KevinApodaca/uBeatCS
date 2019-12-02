package cs4330.cs.utep.ubeatcs;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileView extends AppCompatActivity {

    private static final String FIREBASE_BUCKET = "gs://ubeatcs.appspot.com";
    private static final String FIREBASE_BUCKET_FOLDER = "CS4330/Lecture";

    private List<StorageReference> files;
    private ListView listView;
    private ArrayAdapter adapter;
    private StorageReference storageReference;


    private void downloadFile(String itemName){
        StorageReference ref = storageReference.child("/"+itemName);
        try{
            File rootPath = new File(getApplicationContext().getFilesDir(), "notes");
            if(!rootPath.exists()) {
                rootPath.mkdirs();
            }
            final File localFile = new File(rootPath,itemName);


            ref.getFile(localFile)
                    .addOnSuccessListener(taskSnapshot -> {
                        Toast.makeText(this, "File downloaded, its in the: "+itemName, Toast.LENGTH_LONG).show();
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            Uri path = Uri.fromFile(localFile);
                            String type = getContentResolver().getType(path);
                            intent.setDataAndType(path, "application/vnd.ms-powerpoint");
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            // no Activity to handle this kind of files
                            e.printStackTrace();
                        }
                        //TODO handle success
                    }).addOnFailureListener(exception -> {
                        Toast.makeText(this, "Something wrong", Toast.LENGTH_LONG).show();
                        //TODO handle errors
            });
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_view_layout);

        this.storageReference = FirebaseStorage
                .getInstance(FIREBASE_BUCKET)
                .getReference(FIREBASE_BUCKET_FOLDER);

        this.files = new ArrayList<>();
        this.adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, files.size());

        storageReference.listAll().addOnSuccessListener(listResult ->
                this.files.addAll(listResult.getItems())).addOnCompleteListener(task -> {
                    if(task.getResult() != null){

                        for(StorageReference ref: task.getResult().getItems()){
                            this.adapter.add(ref.getName());
                        }
                        this.adapter.notifyDataSetChanged();
                    }
        });

        this.listView = findViewById(R.id.fileids);
        listView.setOnItemClickListener((parent, view, position, id) ->
                downloadFile((String) adapter.getItem(position)));
        listView.setAdapter(this.adapter);
    }


}


