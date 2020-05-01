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
public class TextToImageAdapter extends RecyclerView.Adapter<TextToImageAdapter.ViewHolder> {
    private List<Integer> ttiList;
    LayoutInflater inflater;
    Context context;
    private List<String> sentence;
    OnWordClick onLetterClick;


    public TextToImageAdapter(List<Integer> ttiList, Context context, OnWordClick onLetterClick) {
        this.ttiList = ttiList;
        this.context = context;
        this.onLetterClick = onLetterClick;
    }

    @Override

    public TextToImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_texttoimage, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }


    @Override
    public void onBindViewHolder(TextToImageAdapter.ViewHolder holder, int position) {
        holder.sentenceView.setImageResource(ttiList.get(position));


    }

    @Override
    public int getItemCount() {
        return ttiList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View container_sentence;
        ImageView sentenceView;
        public ViewHolder(View itemView) {
            super(itemView);

            container_sentence = itemView.findViewById(R.id.layout_container_texttoimage);
            sentenceView = (ImageView) itemView.findViewById(R.id.tv_texttoimage);


        }
    }
}
