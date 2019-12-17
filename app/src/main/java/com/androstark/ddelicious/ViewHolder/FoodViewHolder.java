package com.androstark.ddelicious.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.androstark.ddelicious.CustomView.ElegantNumberButton2;
import com.androstark.ddelicious.Interface.ItemClickLIstener;
import com.androstark.ddelicious.R;

/**
 * Created by SALIM on 2/8/2018.
 */

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView food_name,f_price;
    public ImageView food_image;
    public Button f_add;
    public ElegantNumberButton2 numberButton;
    private ItemClickLIstener itemClickLIstener;

    public void setItemClickLIstener(ItemClickLIstener itemClickLIstener)
    {
        this.itemClickLIstener = itemClickLIstener;
    }



    public FoodViewHolder(View itemView)
    {
        super(itemView);
        food_name = (TextView)itemView.findViewById(R.id.food_name);
        food_image = (ImageView)itemView.findViewById(R.id.food_image);
        f_add = itemView.findViewById(R.id.f_add);
        f_price = itemView.findViewById(R.id.f_price);
        numberButton = itemView.findViewById(R.id.number_button);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        itemClickLIstener.onClick(v,getAdapterPosition(),false);
    }
}
