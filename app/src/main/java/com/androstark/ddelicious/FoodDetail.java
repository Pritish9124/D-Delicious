package com.androstark.ddelicious;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androstark.ddelicious.Common.Common;
import com.androstark.ddelicious.CustomView.ElegantNumberButton;
import com.androstark.ddelicious.Database.Database;
import com.androstark.ddelicious.Model.Food;
import com.androstark.ddelicious.Model.Order;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodDetail extends AppCompatActivity
{
    TextView food_name, food_price, food_description;
    ImageView food_image;

    FloatingActionButton btnCart;

    Button add_cart;
    ElegantNumberButton numberButton;
    Menu customMenu;
    List<Order> cart = new ArrayList<>();
    String foodId="";

    FirebaseDatabase database;
    DatabaseReference foods;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Food currentfood;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        updateCartCount();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //Starting firebase;
        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Food");

        //Initia of view

        numberButton = (ElegantNumberButton)findViewById(R.id.number_button);
        btnCart = (FloatingActionButton)findViewById(R.id.btncart);
        add_cart = findViewById(R.id.btn_addCart);
        food_description = (TextView)findViewById(R.id.food_description);

        food_name = (TextView)findViewById(R.id.food_name);
        food_price = (TextView)findViewById(R.id.food_price);
        food_image = (ImageView)findViewById(R.id.img_food);


        add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new Database(getBaseContext()).addToCart(new Order(
                        foodId,currentfood.getName(),numberButton.getNumber(),currentfood.getPrice(),currentfood.getImage()

                ));

                Toast.makeText(FoodDetail.this, "Added to Cart", Toast.LENGTH_SHORT).show();

                updateCartCount();
            }
        });



        collapsingToolbarLayout  = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsedAppbar);


        //Get food Id from intent
        if (getIntent() !=null)
            foodId = getIntent().getStringExtra("foodId");
        if (!foodId.isEmpty())
        {
            getDetailFood(foodId);

        }



    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateCartCount();
    }

    private void getDetailFood(String foodId)
    {
        foods.child(foodId).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                currentfood = dataSnapshot.getValue(Food.class);

                //Now setting the image
                Picasso.with(getBaseContext()).load(currentfood.getImage())
                        .into(food_image);
                collapsingToolbarLayout.setTitle(currentfood.getName());

                food_price.setText("₹ "+currentfood.getPrice());

                food_name.setText(currentfood.getName());
                food_description.setText(R.string.my_desc);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);


        customMenu=menu;
        updateCartCount();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        if (id == R.id.addTocart)
        {
            startActivity(new Intent(FoodDetail.this,Cart.class));
        }

        return super.onOptionsItemSelected(item);
    }


    public Menu updateCart(Menu customMenu, String count) {

        cart= new Database(this).getCarts();
        int total =0;

        for (Order cart:cart)
        {
            total += Integer.parseInt(cart.getQuantity());

        }




        MenuItem item = customMenu.findItem(R.id.addTocart);
        MenuItemCompat.setActionView(item, R.layout.actionbar_badge_layout);
        FrameLayout notifCount = (FrameLayout) MenuItemCompat.getActionView(item);



        ImageView imageView = (ImageView)notifCount.findViewById(R.id.custom_cart);
        TextView tv = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);
        tv.setText(count);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodDetail.this,Cart.class));
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodDetail.this,Cart.class));
            }
        });

        return customMenu;
    }


    public void updateCartCount() {
        if (customMenu!=null){

            cart= new Database(this).getCarts();
            int total =0;

            for (Order cart:cart)
            {
                total += Integer.parseInt(cart.getQuantity());

            }
            MenuItem item = customMenu.findItem(R.id.addTocart);
            MenuItemCompat.setActionView(item, R.layout.actionbar_badge_layout);
            FrameLayout notifCount = (FrameLayout) MenuItemCompat.getActionView(item);



            ImageView imageView = (ImageView)notifCount.findViewById(R.id.custom_cart);
            TextView tv = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);
            tv.setText(String.valueOf(total));


            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(FoodDetail.this,Cart.class));
                }
            });

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(FoodDetail.this,Cart.class));
                }
            });


        }}

}
