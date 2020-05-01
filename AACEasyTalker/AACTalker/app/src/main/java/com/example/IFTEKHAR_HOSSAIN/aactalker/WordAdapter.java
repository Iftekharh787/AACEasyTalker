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
public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {
    private List<Word> eventList;
    LayoutInflater inflater;
    Context context;

    OnWordClick onWordClick;


    public WordAdapter(List<Word> eventList, Context context, OnWordClick onWordClick) {
        this.eventList = eventList;
        this.context = context;
        this.onWordClick = onWordClick;
    }

    @Override

    public WordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_word, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }


    @Override
    public void onBindViewHolder(WordAdapter.ViewHolder holder, int position) {

        final Word word = eventList.get(position);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onWordClick.onClick(word);
            }
        });
        holder.wordView.setText(word.word);

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View container;
        TextView wordView;
        public ViewHolder(View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.layout_container);
            wordView = (TextView) itemView.findViewById(R.id.tv_word);


        }
    }
}
