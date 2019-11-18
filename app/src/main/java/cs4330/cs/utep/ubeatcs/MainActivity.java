package cs4330.cs.utep.ubeatcs;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListAdapter.Listener {

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
        ClassInfo defaultClass1 = new ClassInfo("Automata / Computability", "Kreinovich", "CS3350");
        ClassInfo defaultClass2 = new ClassInfo("Advanced Object Oriented Programming", "Cheon", "CS3331");
        ClassInfo defaultClass3 = new ClassInfo("Database Management", "Villanueva", "CS4342");
        ClassInfo defaultClass4 = new ClassInfo("Computer Architecture", "Freudenthal", "CS3432");
        ClassInfo defaultClass5 = new ClassInfo("Data Mining", "Hossain", "CS4362");
        classList.add(defaultClass1);
        classList.add(defaultClass2);
        classList.add(defaultClass3);
        classList.add(defaultClass4);
        classList.add(defaultClass5);
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
        FloatingActionButton fab = findViewById(R.id.fab2);
        fab.setOnClickListener(view -> Snackbar.make(view, "Action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        renewList();
    }

    private void itemClicked(int position) {
        Intent i = new Intent(this, DetailedView.class);
        i.putExtra("class", classList.get(position).getClass_name());
        i.putExtra("number", classList.get(position).getClass_number());
        i.putExtra("teacher", classList.get(position).getClass_teacher());
        startActivity(i);
    }

    private void renewList() {
        ListAdapter listAdapter = new ListAdapter(getBaseContext(), classList);
        listView.setAdapter(listAdapter);
        listView.deferNotifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void delete(int index) {

    }

    @Override
    public void edit(int index) {

    }

    @Override
    public void open(int index, boolean isInternal) {

    }

    @Override
    public void refresh(int index) {

    }
}
