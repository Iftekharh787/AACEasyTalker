package com.example.ashik.aactalker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ashik on 22-Feb-17.
 */
public class LetterAdapter extends RecyclerView.Adapter<LetterAdapter.ViewHolder> {
    private List<Letter> evList;
    LayoutInflater inflater;
    Context context;
    OnWordClick onLetterClick;


    public LetterAdapter(List<Letter> evList, Context context, OnWordClick onLetterClick) {
        this.evList = evList;
        this.context = context;
        this.onLetterClick = onLetterClick;
    }

    @Override

    public LetterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_letter, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }


    @Override
    public void onBindViewHolder(LetterAdapter.ViewHolder holder, int position) {

        final Letter letter = evList.get(position);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLetterClick.onLetterClick(letter);
            }
        });
        holder.wordView.setText(letter.letters);

    }

    @Override
    public int getItemCount() {
        return evList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View container;
        TextView wordView;
        public ViewHolder(View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.layout_container_letter);
            wordView = (TextView) itemView.findViewById(R.id.tv_letter);


        }
    }
}
