package com.appsolutions.vectorformandroidchallenge.Util;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appsolutions.vectorformandroidchallenge.R;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<ViewHolder> {

    List<MovieModel> items;

    public CardAdapter(List<MovieModel> Movieitems) {
        items = Movieitems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carditem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(items.get(position).getTitle());
        holder.year.setText(items.get(position).getReleaseDate());
        holder.score.setText(items.get(position).getVote() + "/10");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}