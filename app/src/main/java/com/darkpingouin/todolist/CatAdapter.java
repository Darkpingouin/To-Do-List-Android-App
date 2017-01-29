package com.darkpingouin.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Le custom adapteur pour la listview catégorie
 */

public class CatAdapter extends ArrayAdapter<Categorie> {
    public CatAdapter(Context context, List<Categorie> Categorie) {
        super(context, 0, Categorie);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_category, parent, false);
        }
        CatAdapter.CatHolder viewHolder = (CatHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new CatAdapter.CatHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.title);
            viewHolder.color = (TextView) convertView.findViewById(R.id.color);
            convertView.setTag(viewHolder);
        }
        Categorie cat = getItem(position);
        if (cat != null) {
            viewHolder.name.setText(cat.getName());
            viewHolder.color.setBackgroundColor(cat.getColor());
        }
        return convertView;
    }

    private class CatHolder {
        public TextView name;
        public TextView color;
    }

}
