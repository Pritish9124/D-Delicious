package com.androstark.ddelicious;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androstark.ddelicious.Common.Common;
import com.androstark.ddelicious.Database.Database;
import com.androstark.ddelicious.Model.Order;
import com.androstark.ddelicious.Model.Request;
import com.androstark.ddelicious.ViewHolder.DeliverAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Delivery extends AppCompatActivity {

    RecyclerView list_delivery;
    RecyclerView.LayoutManager layoutManager;
    CardView temp_product_detailsa;
    FirebaseDatabase database;
    DatabaseReference requests;

    TextView cutomername, complete_Address,
             deliver_product_total, Qty, prduct_name,total_price_1;
    ImageView delivery_image;
    Button btn_change_address, btn_proceed_to_pay;



    public TextView temp_product_name,temp_product_count;
    public TextView temp_product_price,temp_product_quantity,temp_product_size,temp_product_sizeAttribute;
    public ImageView img_temp_product;





    List<Order> cartData = new ArrayList<>();


    List<String> arrayListId = new ArrayList<>();
    List<String> arrayListQty = new ArrayList<>();
    List<String> arrayListSize = new ArrayList<>();
    List<String> arrayListSizeAttr = new ArrayList<>();
    DeliverAdapter deliverAdapter;





    String cid;
    String shipping_name;
    String shipping_email;
    String shipping_phone;
    String shipping_address1;
    String shipping_address2 ;
    String shipping_pincode;
    String shipping_city;
    String shipping_state;
    String shipping_country;
    String shipping_productid;
    String shipping_productQty;
    String shipping_productPrice;
    String shipping_totalPrice;
    String shipping_size;
    String shipping_sizeAttr;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);


        list_delivery = (RecyclerView) findViewById(R.id.listdelivery);
        list_delivery.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        list_delivery.setLayoutManager(layoutManager);

        total_price_1 = findViewById(R.id.total_price_1);
        btn_change_address =findViewById(R.id.btnChangeAddress);
        btn_proceed_to_pay =findViewById(R.id.btn_proceed_to_pay);

        deliver_product_total = findViewById(R.id.delivery_product_total);

        cutomername = findViewById(R.id.customer_name);
        complete_Address = findViewById(R.id.customer_complete_address);



        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");


        Intent intent = getIntent();

         shipping_name = intent.getStringExtra("shippping_name");

         shipping_phone = intent.getStringExtra("shippping_phone");;
         shipping_address1 = intent.getStringExtra("shippping_address1");;
         shipping_address2 = intent.getStringExtra("shippping_adress2");;
         shipping_pincode = intent.getStringExtra("shipping_pincode");;
         shipping_city = intent.getStringExtra("shipping_city");;
         shipping_state = intent.getStringExtra("shippping_state");;
         shipping_country = intent.getStringExtra("shippping_country");



        cutomername.setText(shipping_name);
        complete_Address.setText(String.format("%s,%s\n%s\n%s,%s\n%s", shipping_address1, shipping_address2, shipping_pincode, shipping_city, shipping_state, shipping_phone));


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar) ;
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        btn_change_address.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Delivery.this, AddressLayout.class));
            }
        });

        btn_proceed_to_pay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                postOrder();

            }
            // startActivity(new Intent(Delivery.this, Payment.class));
        });

        Load_Delivery_details();

    }



    private void postOrder()
    {

        Request request = new Request(
                shipping_name,
                shipping_phone,
                shipping_address1 + "," + shipping_address2 + "," + shipping_pincode + "," + shipping_city,
                deliver_product_total.getText().toString(),
                cartData,Paper.book().read(Common.USER_KEY).toString()


        );

        requests.child(String.valueOf(System.currentTimeMillis()))
                .setValue(request);


        new Database(getBaseContext()).cleanCart();
        Toast.makeText(Delivery.this, "Thank You , Order Placed!", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(),OrderPlaced.class));

        finish();

    }


    private void Load_Delivery_details()
    {


            cartData= new Database(this).getCarts();
            deliverAdapter = new DeliverAdapter(cartData,this);
            list_delivery.setAdapter(deliverAdapter);

            //Calculating Total Price
            int total = 0;
            for (Order order:cartData)
            {
                total += (Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
                Locale locale = new Locale("en","IN");
                NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
                deliver_product_total.setText(fmt.format(total));
                total_price_1.setText(fmt.format(total));
            }



    }
}
