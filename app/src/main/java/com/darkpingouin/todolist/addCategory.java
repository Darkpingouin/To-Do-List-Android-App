package com.darkpingouin.todolist;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;

import petrov.kristiyan.colorpicker.ColorPicker;

public class addCategory extends AppCompatActivity {

    int finalColor;
    ListView mListView;
    public static ArrayList<Categorie> cat2 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        finalColor = Color.parseColor("#DFDFDF");
        mListView = (ListView) findViewById(R.id.listView);
        cat2 = MainActivity.getCat();
        CatAdapter adapter = new CatAdapter(addCategory.this, cat2);
        mListView.setAdapter(adapter);
    }

    public void setColor(View v) {
        ColorPicker colorPicker = new ColorPicker(this);
        colorPicker.show();
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position, int color) {
                System.out.println("Chosen" + color);
                finalColor = color;
                ((View) findViewById(R.id.color)).setBackgroundColor(color);
            }

            @Override
            public void onCancel() {
                System.out.println("CANCEL");
            }
        });
    }


    public void delete(View v)
    {
        final int position = mListView.getPositionForView((View) v.getParent());
        if (!MainActivity.cat.get(position).getName().equals("none")) {
            MainActivity.cat.remove(position);
            cat2.remove(position);
            CatAdapter a = (CatAdapter) mListView.getAdapter();
            a.notifyDataSetChanged();
        }
        else
            Toast.makeText(getApplicationContext(), "Error can't delete \"none\" category", Toast.LENGTH_SHORT).show();
        SwipeLayout s = (SwipeLayout) mListView.getChildAt(position);
        s.close(true);
    }

   /* public void added(View v)
    {
        int i =0;
        boolean ok = true;
        String name;
        name = ((TextView) findViewById(R.id.catName)).getText().toString();
        while (i < MainActivity.getCat().size())
        {
            if (name.equals(MainActivity.getCat().get(i).getName()))
                ok = false;
            i++;
        }
        if (ok) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("name", name);
            returnIntent.putExtra("color", String.valueOf(finalColor));
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
        else {
            Toast.makeText(this.getApplicationContext(), "Error: Category " + name + " already exists", Toast.LENGTH_SHORT).show();
        }

    }*/
}
