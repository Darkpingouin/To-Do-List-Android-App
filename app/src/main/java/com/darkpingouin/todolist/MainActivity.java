package com.darkpingouin.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Item item = (Item) mListView.getAdapter().getItem(position);
                item.setText("lkjlkjlk");
                ItemAdapter a = (ItemAdapter) mListView.getAdapter();
                a.notifyDataSetChanged();
            }
        });
        afficherListeTweets();
    }

    private List<Item> genererTweets(){
        List<Item> items = new ArrayList<Item>();
        items.add(new Item("Liste de courses", "-pain\n-tomates\n-saladewsdkjfksdjfqskjfqksjdfqksjflqkjdlkqjsdflqsdjflkqsjdflkjqskldfjqlskdfjqlskdjfqsldjflqksjdflqkjdflqksjdflkqjsdflkqsjdflkjqfsdklfjqslkdfjqlskdfjqlksdfjqlskdfjqlskdfj\nqksfhqksjdhfqkjsdhfqjkshfkqsjdhfjkqshdfjkqhsdkjfhqsdjkfhqksdjhfqksjdhfkqjshdjkqsfhkqjdfhJESUISLAkjehkjfhsjkefhkjd", getDate(2, 0, 2017)));
        items.add(new Item("Mdp Wifi", "FGFG4545GHFFG544SFFFD", getDate(23, 1, 2019)));
        items.add(new Item("Appeler Gislaine", "009876544567", getDate(15, 2, 2015)));
        items.add(new Item("Mdp Wifi", "FGFG4545GHFFG544SFFFD", getDate(23, 1, 2019)));
        items.add(new Item("Appeler Gislaine", "009876544567", getDate(15, 2, 2015)));
        items.add(new Item("Mdp Wifi", "FGFG4545GHFFG544SFFFD", getDate(23, 1, 2019)));
        items.add(new Item("Appeler Gislaine", "009876544567", getDate(15, 2, 2015)));
        items.add(new Item("Mdp Wifi", "FGFG4545GHFFG544SFFFD", getDate(23, 1, 2019)));
        items.add(new Item("Appeler Gislaine", "009876544567", getDate(15, 2, 2015)));
        items.add(new Item("Mdp Wifi", "FGFG4545GHFFG544SFFFD", getDate(23, 1, 2019)));
        items.add(new Item("Appeler Gislaine", "009876544567", getDate(15, 2, 2015)));
        items.add(new Item("Mdp Wifi", "FGFG4545GHFFG544SFFFD", getDate(23, 1, 2019)));
        items.add(new Item("Appeler Gislaine", "009876544567", getDate(15, 2, 2015)));
        items.add(new Item("Mdp Wifi", "FGFG4545GHFFG544SFFFD", getDate(23, 1, 2019)));
        items.add(new Item("Appeler Gislaine", "009876544567", getDate(15, 2, 2015)));
        items.add(new Item("Mdp Wifi", "FGFG4545GHFFG544SFFFD", getDate(23, 1, 2019)));
        items.add(new Item("Appeler Gislaine", "009876544567", getDate(15, 2, 2015)));
        items.add(new Item("Mdp Wifi", "FGFG4545GHFFG544SFFFD", getDate(23, 1, 2019)));
        items.add(new Item("Appeler Gislaine", "009876544567", getDate(15, 2, 2015)));
        items.add(new Item("Mdp Wifi", "FGFG4545GHFFG544SFFFD", getDate(23, 1, 2019)));
        items.add(new Item("Appeler Gislaine", "009876544567", getDate(15, 2, 2015)));
        items.add(new Item("Mdp Wifi", "FGFG4545GHFFG544SFFFD", getDate(23, 1, 2019)));
        items.add(new Item("Appeler Gislaine", "009876544567", getDate(15, 2, 2015)));
        items.add(new Item("Mdp Wifi", "FGFG4545GHFFG544SFFFD", getDate(23, 1, 2019)));
        items.add(new Item("Appeler Gislaine", "009876544567", getDate(15, 2, 2015)));
        items.add(new Item("Mdp Wifi", "FGFG4545GHFFG544SFFFD", getDate(23, 1, 2019)));
        items.add(new Item("Appeler Gislaine", "009876544567", getDate(15, 2, 2015)));

        return items;
    }

    private void afficherListeTweets(){
        List<Item> items = genererTweets();

        ItemAdapter adapter = new ItemAdapter(MainActivity.this, items);
        mListView.setAdapter(adapter);
    }

    public static Date getDate(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public void add(View v)
    {
        Intent intentMain = new Intent(MainActivity.this ,
                AddItem.class);
        MainActivity.this.startActivity(intentMain);
    }
}
