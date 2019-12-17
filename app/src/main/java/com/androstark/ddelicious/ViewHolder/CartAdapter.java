package com.androstark.ddelicious.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.androstark.ddelicious.Cart;
import com.androstark.ddelicious.Database.Database;
import com.androstark.ddelicious.Interface.ItemClickLIstener;
import com.androstark.ddelicious.Model.Order;
import com.androstark.ddelicious.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION;

/**
 * Created by SALIM on 2/9/2018.
 */
class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    public TextView txt_cart_name,txt_price;
    public TextView img_cart_count;
    public ImageView cart_img,delete_btn;

    private ItemClickLIstener itemClickLIstener;

    public void setTxt_cart_name(TextView txt_cart_name) {
        this.txt_cart_name = txt_cart_name;
    }

    public CartViewHolder(View itemView)
    {
        super(itemView);
        txt_cart_name = (TextView)itemView.findViewById(R.id.cart_item_name);
        txt_price = (TextView)itemView.findViewById(R.id.cart_item_price);
        img_cart_count = itemView.findViewById(R.id.cart_item_count);
        cart_img = itemView.findViewById(R.id.cart_item_image);
        delete_btn = itemView.findViewById(R.id.delete_item);
    }

    @Override
    public void onClick(View v)
    {

    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder>
{
    private List<Order> listData = new ArrayList<>();
    private Context context;

    public CartAdapter(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemview = inflater.inflate(R.layout.cart_layout,parent,false);
        return new CartViewHolder(itemview);

    }

    @Override
    public void onBindViewHolder(final CartViewHolder holder, int position)
    {

        final Cart cart = new Cart();

        int price=(Integer.parseInt(listData.get(position).getPrice()))*(Integer.parseInt(listData.get(position).getQuantity()));

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(""+listData.get(position).getQuantity(), Color.RED);
        holder.img_cart_count.setText(String.format("Qty: %s", listData.get(position).getQuantity()));
        Locale locale = new Locale("en","IN");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        holder.txt_price.setText(fmt.format(price));
        holder.txt_cart_name.setText(listData.get(position).getProductName());
        Picasso.with(context).load(listData.get(position).getProductImage())
                .into(holder.cart_img);


        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order deleteitem = getItem(holder.getAdapterPosition());
                new Database(context).clean_from_cart(deleteitem.getProductId());
                //  String string = new Database(context).clean_from_cart(deleteitem.getId());
                Intent intent = new Intent(context,Cart.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(FLAG_ACTIVITY_NO_ANIMATION);

                cart.finish();
                intent.addFlags(FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(intent);





                int total = 0;
                List<Order> cart = new Database(context).getCarts();
                for (Order item:cart)
                {
                    total += (Integer.parseInt(item.getPrice()))*(Integer.parseInt(item.getQuantity()));
                    Locale locale = new Locale("en","IN");
                    NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
                    holder.txt_price.setText(fmt.format(total));
                }


                Toast.makeText(context, " Item removed Successfully", Toast.LENGTH_SHORT).show();


            }
        });

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
