package com.mdakram28.smarthome.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.mdakram28.smarthome.R;

import butterknife.BindView;

/**
 * Created by mdakram28 on 13/6/17.
 */

public class IconViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.textView_iconTitle)
    TextView iconTitle;

    private AdapterView.OnItemClickListener itemClickListener;
    private AdapterView parent;
    private int position;

    public IconViewHolder(View itemView, AdapterView.OnItemClickListener itemClickListener) {
        super(itemView);
        iconTitle = (TextView) itemView.findViewById(R.id.textView_iconTitle);
        this.itemClickListener = itemClickListener;
        itemView.setOnClickListener(this);
    }

    public void setIconTitle(String title){
        iconTitle.setText(title);
    }

    @Override
    public void onClick(View v) {
        if (itemClickListener != null) itemClickListener.onItemClick(parent, v, position, position);
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
