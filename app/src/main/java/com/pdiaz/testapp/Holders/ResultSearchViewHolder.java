package com.pdiaz.testapp.Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pdiaz.testapp.R;

/**
 * Created by diiaz94 on 04-04-2017.
 */
public class ResultSearchViewHolder {

    ImageView imThumbnail;
    TextView txtTitle;
    TextView txtPrice;
    TextView txtLocation;
    public ResultSearchViewHolder(View v) {
        this.imThumbnail = (ImageView) v.findViewById(R.id.result_image);
        this.txtTitle = (TextView) v.findViewById(R.id.result_title);
        this.txtPrice = (TextView) v.findViewById(R.id.result_price);
        this.txtLocation = (TextView) v.findViewById(R.id.result_location);
    }

    public ImageView getImThumbnail() {
        return imThumbnail;
    }

    public void setImThumbnail(ImageView imThumbnail) {
        this.imThumbnail = imThumbnail;
    }

    public TextView getTxtTitle() {
        return txtTitle;
    }

    public void setTxtTitle(TextView txtTitle) {
        this.txtTitle = txtTitle;
    }

    public TextView getTxtPrice() {
        return txtPrice;
    }

    public void setTxtPrice(TextView txtPrice) {
        this.txtPrice = txtPrice;
    }

    public TextView getTxtLocation() {
        return txtLocation;
    }

    public void setTxtLocation(TextView txtLocation) {
        this.txtLocation = txtLocation;
    }
}
