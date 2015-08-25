package com.ciandt.worldwonders.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ciandt.worldwonders.R;
import com.ciandt.worldwonders.fragments.WondersFragment;
import com.ciandt.worldwonders.helpers.Helper;
import com.ciandt.worldwonders.models.Wonder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WonderItemAdapter extends RecyclerView.Adapter<WonderItemAdapter.WonderItemHolder> {

    List<Wonder> wonders;
    WonderOnClickListener onClickListener;

    public WonderItemAdapter(List<Wonder> wonders, WonderOnClickListener onClickListener) {
        this.wonders = wonders;
        this.onClickListener = onClickListener;
    }

    @Override
    public WonderItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wonder, parent, false);
        WonderItemHolder wonderItemHolder = new WonderItemHolder(itemView, onClickListener);
        wonderItemHolder.setContext(parent.getContext());

        return wonderItemHolder;
    }

    @Override
    public void onBindViewHolder(WonderItemHolder holder, int position) {
        holder.renderWonder(wonders.get(position));
    }

    @Override
    public int getItemCount() {
        return wonders.size();
    }


    public static class WonderItemHolder extends RecyclerView.ViewHolder {

        WonderOnClickListener listener;
        Context context;

        Wonder wonder;
        ImageView image;
        TextView name;

        public WonderItemHolder(View v, WonderOnClickListener l) {
            super(v);

            listener = l;
            image = (ImageView)v.findViewById(R.id.image);
            name = (TextView)v.findViewById(R.id.name);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(wonder);
                }
            });
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public void renderWonder(Wonder wonder) {

            this.wonder = wonder;

            name.setText(wonder.name.toUpperCase());

            String pictureFilename = wonder.photo.split("\\.")[0];
            int pictureResource = Helper.getRawResourceID(context, pictureFilename);

            Picasso.with(context)
                    .load(pictureResource)
                    .config(Bitmap.Config.RGB_565)
                    .resize(100, 100)
                    .centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(image);

        }
    }

    public interface WonderOnClickListener {
        void onClick(Wonder wonder);
    }
}
