package com.androstark.ddelicious.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androstark.ddelicious.Cart;
import com.androstark.ddelicious.Interface.ItemClickLIstener;
import com.androstark.ddelicious.Model.Order;
import com.androstark.ddelicious.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by SALIM on 4/18/2018.
 */

class DeliverViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    public TextView deliver_product_name,deliver_product_count;
    public TextView deliver_product_price,deliver_product_quantity,delivery_product_size,delivery_product_sizeAttribute;
    public ImageView img_deliver_product;




    private ItemClickLIstener itemClickLIstener;

    public void deliver_product_name(TextView deliver_product_name) {
        this.deliver_product_name = deliver_product_name;
    }

    public DeliverViewHolder(View itemView)
    {
        super(itemView);
        deliver_product_name = (TextView)itemView.findViewById(R.id.deliver_product_name);
        deliver_product_price = (TextView)itemView.findViewById(R.id.deliver_product_price);
        delivery_product_size = itemView.findViewById(R.id.delivery_product_size);
        delivery_product_sizeAttribute = itemView.findViewById(R.id.delivery_product_sizeAttribute);
        deliver_product_count = itemView.findViewById(R.id.delivery_product_count);
        img_deliver_product = itemView.findViewById(R.id.deliver_product_image);

        deliver_product_quantity = itemView.findViewById(R.id.deliver_product_quantity);
    }

    @Override
    public void onClick(View v)
    {

        Log.e("e","e");
    }
}

public class DeliverAdapter extends RecyclerView.Adapter<DeliverViewHolder>
{
    private List<Order> listData = new ArrayList<>();
    Context context;
    List<Order> cart = new ArrayList<>();
    DeliverAdapter adapter;

    public DeliverAdapter(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;

    }

    @Override
    public DeliverViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemview = inflater.inflate(R.layout.delivery_layout,parent,false);



        return new DeliverViewHolder(itemview);

    }

    @Override
    public void onBindViewHolder(final DeliverViewHolder holder, int position)
    {

        final Cart cart = new Cart();
        int price=(Integer.parseInt(listData.get(position).getPrice()))*(Integer.parseInt(listData.get(position).getQuantity()));

        //TextDrawable drawable = TextDrawable.builder().buildRound(""+listData.get(position).cartproductquantity, Color.RED);
        holder.deliver_product_count.setText(listData.get(position).getQuantity());
        final Locale locale = new Locale("en","IN");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        holder.deliver_product_price.setText(fmt.format(price));
        holder.deliver_product_name.setText(listData.get(position).getProductName());



        Picasso.with(context).load(listData.get(position).getProductImage())
                .into(holder.img_deliver_product);


    }


    @Override
    public int getItemCount()
    {

        return listData.size();
    }

    public Order getItem(int position)
    {
        return listData.get(position);
    }

    public void removeItem(int position)
    {
        listData.remove(position);
        notifyItemRemoved(position);
    }




}

