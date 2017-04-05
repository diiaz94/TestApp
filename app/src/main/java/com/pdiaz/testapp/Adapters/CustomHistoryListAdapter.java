package com.pdiaz.testapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.pdiaz.testapp.DTO.Attribute;
import com.pdiaz.testapp.Holders.HistoryViewHolder;
import com.pdiaz.testapp.Holders.ResultSearchViewHolder;
import com.pdiaz.testapp.R;

import java.util.ArrayList;

/**
 * Created by diiaz94 on 04-04-2017.
 */
public class CustomHistoryListAdapter extends BaseAdapter {

    private Context ctx;
    private ArrayList<String> list;


    public CustomHistoryListAdapter(Context ctx, ArrayList<String> list) {
        this.ctx = ctx;
        this.list = list;
    }
    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        HistoryViewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.item_history, parent, false);
            holder = new HistoryViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (HistoryViewHolder) row.getTag();
        }


        holder.getTxtSearch().setText(list.get(position));


        return row;
    }
}
