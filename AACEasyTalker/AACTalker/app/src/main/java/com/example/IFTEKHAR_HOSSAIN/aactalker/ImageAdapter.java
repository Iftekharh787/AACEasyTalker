package com.example.ashik.aactalker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ashik on 22-Feb-17.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private List<Sign> eveList;
    LayoutInflater inflater;
    Context context;

    OnWordClick onImageClick;


    public ImageAdapter(List<Sign> eveList, Context context, OnWordClick onImageClick) {
        this.eveList = eveList;
        this.context = context;
        this.onImageClick = onImageClick;
    }

    @Override

    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_image, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }


    @Override
    public void onBindViewHolder(ImageAdapter.ViewHolder holder, int position) {

        final Sign sign = eveList.get(position);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onImageClick.onImageClick(sign);
            }
        });
        holder.wordView.setImageResource(sign.image);

    }

    @Override
    public int getItemCount() {
        return eveList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View container;
        ImageView wordView;
        public ViewHolder(View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.layout_container_img);
            wordView = (ImageView) itemView.findViewById(R.id.iv_word);


        }
    }
}
