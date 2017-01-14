package com.darkpingouin.todolist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.util.List;

/**
 * Created by Oreo on 10/01/2017.
 */

public class ItemAdapter extends ArrayAdapter<Item> {

    public ItemAdapter(Context context, List<Item> Items) {
        super(context, 0, Items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_item,parent, false);
        }
        final SwipeLayout s = (SwipeLayout)convertView.findViewById(R.id.swipe);
        View v = (View) parent.getParent().getParent();
        final TextView t = (TextView) v.findViewById(R.id.nb_tasks);
        ItemViewHolder viewHolder = (ItemViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ItemViewHolder();
            viewHolder.swipe =  (SwipeLayout)convertView.findViewById(R.id.swipe);
            viewHolder.doneB = (ImageView) convertView.findViewById(R.id.doneB);
            viewHolder.todoB = (ImageView) convertView.findViewById(R.id.todoB);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.text = (TextView) convertView.findViewById(R.id.text);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            convertView.setTag(viewHolder);
        }

        final Item Item = getItem(position);
        viewHolder.doneB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Item.setStatus(com.darkpingouin.todolist.Item.Status.DONE);
                //s.close(true);
                t.performClick();
            }
        });

        viewHolder.todoB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Item.setStatus(com.darkpingouin.todolist.Item.Status.TODO);
                //s.close(true);
                t.performClick();
            }
        });
        viewHolder.title.setText(Item.getTitle());
        if (Item.getStatus() == com.darkpingouin.todolist.Item.Status.DONE)
            viewHolder.text.getPaint().setStrikeThruText(true);
        else
            viewHolder.text.getPaint().setStrikeThruText(false);
        viewHolder.date.setTextColor(Color.parseColor(Item.getDateColor()));
        viewHolder.text.setText(Item.getText());
        viewHolder.date.setText(Item.getDueDate());

        return convertView;
    }

    private class ItemViewHolder{
        public SwipeLayout swipe;
        public TextView title;
        public TextView text;
        public TextView date;
        public ImageView todoB;
        public ImageView doneB;
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

}

