package com.androstark.ddelicious.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androstark.ddelicious.Interface.ItemClickLIstener;
import com.androstark.ddelicious.R;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView textMenuName;
    public ImageView imageView;

    private ItemClickLIstener itemClickLIstener;

    public MenuViewHolder(View itemView) {
        super(itemView);

        textMenuName = (TextView)itemView.findViewById(R.id.menu_name);
        imageView = (ImageView)itemView.findViewById(R.id.menu_image);

        itemView.setOnClickListener(this);
    }

    public void setItemClickLIstener(ItemClickLIstener itemClickLIstener)
    {
        this.itemClickLIstener = itemClickLIstener;
    }

    @Override
    public void onClick(View v)
    {
        itemClickLIstener.onClick(v,getAdapterPosition(),false);

    }

}
