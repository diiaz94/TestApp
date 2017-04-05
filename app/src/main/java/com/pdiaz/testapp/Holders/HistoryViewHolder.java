package com.pdiaz.testapp.Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pdiaz.testapp.R;

/**
 * Created by diiaz94 on 04-04-2017.
 */
public class HistoryViewHolder {
    TextView txtSearch;

    public TextView getTxtSearch() {
        return txtSearch;
    }

    public void setTxtSearch(TextView txtSearch) {
        this.txtSearch = txtSearch;
    }

    public HistoryViewHolder(View v) {
        this.txtSearch = (TextView) v.findViewById(R.id.history_search_text);
    }
}
