package bananatechnologies.recyclerviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<WordProperties> word = new ArrayList<>();

    private RecyclerView recyclerView;
    private WordDefinitionsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        mAdapter = new WordDefinitionsAdapter(word);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        prepareSampleData();

    }

    private void prepareSampleData() {

        WordProperties def1 = new WordProperties("Abscond", "Verb", "leave hurriedly and secretly, typically to escape from custody or avoid arrest");
        word.add(def1);

        def1 = new WordProperties("Abscond", "Verb", "(of a person on bail) fail to surrender oneself for custody at the appointed time");
        word.add(def1);

        def1 = new WordProperties("Abscond", "Verb", "(of a person on bail) fail to surrender oneself for custody at the appointed time");
        word.add(def1);

        WordProperties def2=new WordProperties("Abscond","Verb","(of a colony of honeybees, especially Africanized ones) entirely abandon a hive or nest.");
        word.add(def2);
    }
}
