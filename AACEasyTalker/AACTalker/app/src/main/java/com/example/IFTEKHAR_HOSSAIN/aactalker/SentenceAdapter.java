package com.example.ashik.aactalker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import java.util.List;

/**
 * Created by Ashik on 22-Feb-17.
 */
public class SentenceAdapter extends RecyclerView.Adapter<SentenceAdapter.ViewHolder> {
    private List<String> sentenceList;
    LayoutInflater inflater;
    Context context;
    private List<String> sentence;
    OnWordClick onWordClick;


    public SentenceAdapter(List<String> sentenceList, Context context, OnWordClick onWordClick) {
        this.sentenceList = sentenceList;
        this.context = context;
        this.onWordClick = onWordClick;
    }

    @Override

    public SentenceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_sentence, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }


    @Override
    public void onBindViewHolder(SentenceAdapter.ViewHolder holder, int position) {
        holder.sentenceView.setText(sentenceList.get(position).toString());


    }

    @Override
    public int getItemCount() {
        return sentenceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View container_sentence;
        TextView sentenceView;
        public ViewHolder(View itemView) {
            super(itemView);

            container_sentence = itemView.findViewById(R.id.layout_container_sentence);
            sentenceView = (TextView) itemView.findViewById(R.id.tv_sentence);


        }
    }
}
