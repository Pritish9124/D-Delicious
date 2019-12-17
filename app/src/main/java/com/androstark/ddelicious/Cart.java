package com.androstark.ddelicious;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androstark.ddelicious.Common.Common;
import com.androstark.ddelicious.Database.Database;
import com.androstark.ddelicious.Model.Order;
import com.androstark.ddelicious.Model.Request;
import com.androstark.ddelicious.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity
{
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

   // FirebaseDatabase database;
  //  DatabaseReference request;
    TextView txtTotalPrice;
    Button btnPlace,btnClear;

    ImageView cart_empty_img;

    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;
CardView cardview_cart;
    int test_total = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

       // database = FirebaseDatabase.getInstance();
       // request = database.getReference("Requests");
        //inti
        recyclerView = (RecyclerView) findViewById(R.id.listcart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        btnPlace = (Button) findViewById(R.id.btnplaceOrder);
        txtTotalPrice = (TextView) findViewById(R.id.total);
        btnClear = findViewById(R.id.removecartbtn);

        cart_empty_img = findViewById(R.id.empty_cart);

        cardview_cart = findViewById(R.id.cardview_cart);


        Database database =  new Database(getBaseContext());
        if (database.getCarts().size() >0)
        {
            cardview_cart.setVisibility(View.VISIBLE);
            btnPlace.setVisibility(View.VISIBLE);
            cart_empty_img.setVisibility(View.INVISIBLE);
        }
        else
        {
            cardview_cart.setVisibility(View.INVISIBLE);
            btnPlace.setVisibility(View.INVISIBLE);
            cart_empty_img.setVisibility(View.VISIBLE);
        }


        btnClear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new Database(getBaseContext()).cleanCart();

                Intent intent = getIntent();
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
                Toast.makeText(Cart.this, "Items removed!", Toast.LENGTH_SHORT).show();
            }
        });

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Database databaseaddress =  new Database(getBaseContext());

                if ((databaseaddress.getCarts().size() >0))
                {
                    if (databaseaddress.getAddress().size()>0)
                    {
                        startActivity(new Intent(Cart.this,AddressDetails.class));
                    }
                    else

                        startActivity(new Intent(Cart.this,AddressLayout.class));
                }
                else
                    Toast.makeText(getApplicationContext(), "No items in to checkout..",Toast.LENGTH_SHORT).show();

/*

                if (test_total > 0)
                {
                    Intent intent = new Intent(Cart.this,AddressLayout.class);
                    intent.putExtra("extradata",txtTotalPrice.getText().toString());
                    startActivity(intent);
                }
                else
                    Toast.makeText(Cart.this, "Please add items in Cart to Checkout!", Toast.LENGTH_SHORT).show(); */


              /*  Request request = new Request(
                        Common.currentuser.getPhone(),
                        Common.currentuser.getName(),

                        );*/
            }
        });

        loadListFood();



    }

    private void loadListFood()
    {
        cart= new Database(this).getCarts();
        adapter = new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);

        //Calculating Total Price
        int total = 0;
        for (Order order:cart)
        {
            total += (Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
            Locale locale = new Locale("en","IN");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
            txtTotalPrice.setText(fmt.format(total));
            test_total = total;
        }

        if (total>0)
        {
            btnClear.setVisibility(View.VISIBLE);
        }
        else
        {
            btnClear.setVisibility(View.GONE);
        }


    }
}
