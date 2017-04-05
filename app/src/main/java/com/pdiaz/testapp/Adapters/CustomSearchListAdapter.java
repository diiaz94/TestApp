package com.pdiaz.testapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.pdiaz.testapp.DTO.Attribute;
import com.pdiaz.testapp.Holders.ResultSearchViewHolder;
import com.pdiaz.testapp.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by diiaz94 on 04-04-2017.
 */
public class CustomSearchListAdapter extends BaseAdapter {

    private final DisplayImageOptions options;
    private Activity act;
    private ArrayList<Attribute> list;
    private LayoutInflater inflater;

    public CustomSearchListAdapter(Activity act, ArrayList<Attribute> list) {
        this.act = act;
        this.list = list;
        inflater = LayoutInflater.from(act.getApplicationContext());
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
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
        final ViewHolder holder;
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_search_result, parent, false);
            holder = new ViewHolder();
            assert view != null;
            holder.imageView = (ImageView) view.findViewById(R.id.result_image);
            holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
            holder.title = (TextView) view.findViewById(R.id.result_title);
            holder.price = (TextView) view.findViewById(R.id.result_price);
            holder.location = (TextView) view.findViewById(R.id.result_location);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ImageLoader.getInstance()
                .displayImage(list.get(position).getSkuThumbnailImage(), holder.imageView, options, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        holder.progressBar.setProgress(0);
                        holder.progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        holder.progressBar.setVisibility(View.GONE);
                    }
                }, new ImageLoadingProgressListener() {
                    @Override
                    public void onProgressUpdate(String imageUri, View view, int current, int total) {
                        holder.progressBar.setProgress(Math.round(100.0f * current / total));
                    }
                });

        holder.title.setText(list.get(position).getProductDisplayName());
        holder.price.setText(list.get(position).getSkuPromoPrice());
        holder.location.setText(list.get(position).getAncestorCategoriesDisplayName());
        return view;
    }
    static class ViewHolder {
        ImageView imageView;
        ProgressBar progressBar;
        TextView title;
        TextView price;
        TextView location;
    }
}




