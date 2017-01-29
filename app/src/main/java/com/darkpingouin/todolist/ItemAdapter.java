package com.darkpingouin.todolist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom adaptateur pour les Items
 */

public class ItemAdapter extends ArrayAdapter<Item> {

    public ItemAdapter(Context context, List<Item> Items) {
        super(context, 0, Items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_item, parent, false);
        }
        ItemViewHolder viewHolder = (ItemViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ItemViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.text = (TextView) convertView.findViewById(R.id.text);
            viewHolder.dateHour = (TextView) convertView.findViewById(R.id.dateHour);
            viewHolder.dateMonth = (TextView) convertView.findViewById(R.id.dateMonth);
            viewHolder.dateYear = (TextView) convertView.findViewById(R.id.dateYear);
            viewHolder.categorie = (TextView) convertView.findViewById(R.id.categorie);
            viewHolder.back = (LinearLayout) convertView.findViewById(R.id.back);
            convertView.setTag(viewHolder);
        }
        Item Item = getItem(position);
        final ArrayList<Categorie> cat = MainActivity.getCat();
        viewHolder.back.setBackgroundColor(Color.WHITE);
        boolean found = false;
        int i = 0;
        while (i < cat.size()) {
            if (Item.getCategorie().equals(cat.get(i).getName())) {
                found = true;
                int color = cat.get(i).getColor();
                String lighter = "#15" + Integer.toHexString(color).substring(2);
                viewHolder.categorie.setBackgroundColor(cat.get(i).getColor());
                if (Item.getStatus() == com.darkpingouin.todolist.Item.Status.DONE)
                    viewHolder.back.setBackgroundColor(Color.parseColor(lighter));
            }
            i++;
        }
        if (!found)
        {
            Item.setCategorie("none");
            int color = cat.get(0).getColor();
            String lighter = "#15" + Integer.toHexString(color).substring(2);
            if (Item.getStatus() == com.darkpingouin.todolist.Item.Status.DONE)
                viewHolder.back.setBackgroundColor(Color.parseColor(lighter));
            viewHolder.categorie.setBackgroundColor(cat.get(0).getColor());
        }
        viewHolder.title.setText(Item.getTitle());
        viewHolder.dateHour.setTextColor(Color.parseColor(Item.getDateColor()));
        viewHolder.dateMonth.setTextColor(Color.parseColor(Item.getDateColor()));
        viewHolder.dateYear.setTextColor(Color.parseColor(Item.getDateColor()));
        viewHolder.text.setText(Item.getText());
        viewHolder.dateMonth.setText(Item.getMonth());
        viewHolder.dateYear.setText(Item.getYear());
        viewHolder.dateHour.setText(Item.getTime());

        return convertView;
    }

    private class ItemViewHolder {
        public TextView title;
        public TextView text;
        public TextView dateHour;
        public TextView dateYear;
        public TextView dateMonth;
        public TextView categorie;
        public LinearLayout back;
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

}

