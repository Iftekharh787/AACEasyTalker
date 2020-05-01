package com.example.ashik.aactalker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ashik on 06-May-17.
 */
public class SignAdapter  extends RecyclerView.Adapter<SignAdapter.ViewHolder> {
    private List<String> signList;
    LayoutInflater inflater;
    Context context;
    private List<String> sentence;
    OnWordClick onWordClick;


    public SignAdapter(List<String> signList, Context context) {
        this.signList = signList;
        this.context = context;
    }

    @Override

    public SignAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_sign, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }


    @Override
    public void onBindViewHolder(SignAdapter.ViewHolder holder, int position) {
        holder.signView.setText(signList.get(position).toString());


    }

    @Override
    public int getItemCount() {
        return signList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View container_sign;
        TextView signView;
        public ViewHolder(View itemView) {
            super(itemView);

            container_sign = itemView.findViewById(R.id.layout_container_sign);
            signView = (TextView) itemView.findViewById(R.id.tv_sign);


        }
    }
}
