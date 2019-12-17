package com.androstark.ddelicious.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.androstark.ddelicious.Interface.ItemClickLIstener;
import com.androstark.ddelicious.R;



public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView txtOrderId,txtOrderStatus,txtOrderPhone,txtOrderAddress,txtOrderName,txtOrderQty,txtOrderPrice,txtOrderTotal;

    private ItemClickLIstener itemClickLIstener;

    public OrderViewHolder(View itemView)
    {
        super(itemView);
        txtOrderAddress = (TextView)itemView.findViewById(R.id.order_address);
        txtOrderId = (TextView)itemView.findViewById(R.id.order_id);
        txtOrderPhone = (TextView)itemView.findViewById(R.id.order_phone);
        txtOrderStatus = (TextView)itemView.findViewById(R.id.order_status);
        txtOrderName = itemView.findViewById(R.id.order_name);
        txtOrderQty = itemView.findViewById(R.id.order_qty);
        txtOrderPrice = itemView.findViewById(R.id.order_price);
        txtOrderTotal = itemView.findViewById(R.id.order_total_price);
     //   itemView.setOnClickListener(this);

    }

    public void setItemClickLIstener(ItemClickLIstener itemClickLIstener)
    {
        this.itemClickLIstener = itemClickLIstener;
    }

    @Override
    public void onClick(View v)
    {
        itemClickLIstener.onClick(v, getAdapterPosition(),false);
    }
}
