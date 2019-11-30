package cs4330.cs.utep.ubeatcs;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

/**
 * @author Isaias Leos
 */
public class DetailedTabbedView extends AppCompatActivity {

    StudyClass sendClass;

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
                i.getStringArrayListExtra("youtubeList"));
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), sendClass);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> sendEmailDialog());
    }

    private void sendEmailDialog() {
        EmailDialogActivity dialog = new EmailDialogActivity();
        Bundle bundle = new Bundle();
        bundle.putString("email", sendClass.getClass_email());
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "Email");
    }

}