package com.androstark.ddelicious.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androstark.ddelicious.AddressDetails;
import com.androstark.ddelicious.Database.Database;
import com.androstark.ddelicious.Delivery;
import com.androstark.ddelicious.Home;
import com.androstark.ddelicious.Interface.ItemClickLIstener;
import com.androstark.ddelicious.Model.AddressData;
import com.androstark.ddelicious.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by SALIM on 4/29/2018.
 */

class AddressViewHolder extends RecyclerView.ViewHolder {


    public TextView customername,customerstreetaddrss,customercity;
    public TextView customerstate, customerphone;
    public ImageButton btn_remove_address,btn_select_address;
    public LinearLayout lay;



    public AddressViewHolder(View itemView)
    {
        super(itemView);

        customername = itemView.findViewById(R.id.address_customername);
        customerstreetaddrss = itemView.findViewById(R.id.customer_street_address);

        customerphone = itemView.findViewById(R.id.address_customer_postalcode);
        btn_remove_address = itemView.findViewById(R.id.btn_address_remove);
        lay = itemView.findViewById(R.id.linear_lay);
        btn_select_address = itemView.findViewById(R.id.btn_address_place);


    }
}

public class  AddressAdapter extends RecyclerView.Adapter<AddressViewHolder>
{
    private List<AddressData> listData = new ArrayList<>();
    Context context;
    List<AddressData> data = new ArrayList<>();
    AddressAdapter adapter;

    public AddressAdapter(List<AddressData> listData, Context context) {
        this.listData = listData;
        this.context = context;

    }

    @Override
    public AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemview = inflater.inflate(R.layout.address_layout,parent,false);



        return new AddressViewHolder(itemview);

    }



    @Override
    public void onBindViewHolder(final AddressViewHolder holder, int position)
    {
        final AddressDetails addressDetails = new AddressDetails();

        holder.customername.setText(listData.get(position).getCustomername());


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(listData.get(position).getCustomersreetaddress());
        stringBuilder.append("\n");
        stringBuilder.append(listData.get(position).getShippping_adress2());
        stringBuilder.append("\n");
        stringBuilder.append(listData.get(position).getCustomercity());
        stringBuilder.append(",");
        stringBuilder.append(listData.get(position).getCustomerstate());
        stringBuilder.append(" - ");
        stringBuilder.append(listData.get(position).getCustomerpostalcode());




        holder.customerstreetaddrss.setText(stringBuilder);

        holder.customerphone.setText(listData.get(position).getShippping_phone());

        //TextDrawable drawable = TextDrawable.builder().buildRound(""+listData.get(position).cartproductquantity, Color.RED);

        holder.btn_remove_address.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v)
            {

                String name = getItem(holder.getAdapterPosition()).getCustomername();
                AddressData deleteitem = getItem(holder.getAdapterPosition());
                new Database(context).clean_from_address(deleteitem.getId());
                Intent intent = new Intent(context,AddressDetails.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                addressDetails.finish();
                context.startActivity(intent);

                Toast.makeText(context, " Address removed Successfully", Toast.LENGTH_SHORT).show();


            }
        });

        holder.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Delivery.class);
                intent.putExtra("shippping_name",listData.get(holder.getAdapterPosition()).getCustomername());
                intent.putExtra("shippping_address1",listData.get(holder.getAdapterPosition()).getCustomersreetaddress());
                intent.putExtra("shippping_adress2",listData.get(holder.getAdapterPosition()).getShippping_adress2());
                intent.putExtra("shipping_city",listData.get(holder.getAdapterPosition()).getCustomercity());
                intent.putExtra("shippping_state",listData.get(holder.getAdapterPosition()).getCustomerstate());
                intent.putExtra("shipping_pincode",listData.get(holder.getAdapterPosition()).getCustomerpostalcode());

                // intent.putExtra("shippping_email",shipping_mail.getText().toString());
                intent.putExtra("shippping_phone",listData.get(holder.getAdapterPosition()).getShippping_phone());

                context.startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount()
    {

        return listData.size();
    }

    public AddressData getItem(int position)
    {
        return listData.get(position);
    }

    public void removeItem(int position)
    {
        listData.remove(position);
        notifyItemRemoved(position);
    }



}
