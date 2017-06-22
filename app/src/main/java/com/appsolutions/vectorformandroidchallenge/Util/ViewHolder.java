package com.appsolutions.vectorformandroidchallenge.Util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.appsolutions.vectorformandroidchallenge.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView title;
    TextView year;
    TextView score;

    public ViewHolder(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.Title);
        year = (TextView) itemView.findViewById(R.id.Year);
        score = (TextView) itemView.findViewById(R.id.score);
    }
}
