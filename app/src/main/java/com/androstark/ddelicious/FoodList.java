package com.androstark.ddelicious;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androstark.ddelicious.Common.Common;
import com.androstark.ddelicious.Database.Database;
import com.androstark.ddelicious.Interface.ItemClickLIstener;
import com.androstark.ddelicious.Model.Category;
import com.androstark.ddelicious.Model.Food;
import com.androstark.ddelicious.Model.Order;
import com.androstark.ddelicious.Model.User;
import com.androstark.ddelicious.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class FoodList extends AppCompatActivity
{
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;
    Menu customMenu;
    List<Order> cart = new ArrayList<>();

    LinearLayout linearLayout,cart_linear;
    TextView view_cart,item_price,item_qty;


    String categoryId ="";
   // String categoryId2 ="2" ;
    FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        updateCartCount();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        cart_linear = findViewById(R.id.cart_linear);
        view_cart = findViewById(R.id.view_cart);
        item_price = findViewById(R.id.item_price);
        item_qty = findViewById(R.id.item_qty);



        updateBottomCart();

        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Food");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

       if (getIntent() != null)
            categoryId = getIntent().getStringExtra("myextradata");
       if (!categoryId.isEmpty() && categoryId!=null)
       {
           loadListFood(categoryId);
       }

         //  mylist();
         //
        //Toast.makeText(this, getIntent().getStringExtra("loda"), Toast.LENGTH_SHORT).show();



        cart_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Cart.class));
            }
        });




    }

    private void updateBottomCart()
    {

        cart = new Database(this).getCarts();
        int total = 0;
        int total_price =0;

        for (Order cart : cart) {
            total += Integer.parseInt(cart.getQuantity());
            total_price += Integer.parseInt(cart.getPrice());
        }

        if (total>0)
        {
            cart_linear.setVisibility(View.VISIBLE);
            item_qty.setText(String.valueOf(total));
            item_price.setText("₹ "+String.valueOf(total_price)+".00");

        }
    }
    //String categoryId = "";


    private void loadListFood(String categoryId)
    {

        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("MenuId").equalTo(categoryId))
                //Just like : Select * from foods where MenuId =
        {
            @Override
            protected void populateViewHolder(final FoodViewHolder viewHolder, final Food model, final int position)
            {

                viewHolder.food_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.food_image);

                viewHolder.f_price.setText("₹ "+model.getPrice());

                final Food odia = model;

                viewHolder.f_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new Database(getBaseContext()).addToCart(new Order(
                                adapter.getRef(position).getKey(),model.getName(),viewHolder.numberButton.getNumber(),model.getPrice(),model.getImage()

                        ));


                        viewHolder.f_add.setText("Added");
                        viewHolder.f_add.setClickable(false);

                        Toast.makeText(FoodList.this, "Added to Cart", Toast.LENGTH_SHORT).show();

                        updateCartCount();
                        updateBottomCart();
                    }
                });

                viewHolder.setItemClickLIstener(new ItemClickLIstener()
                {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick)
                    {
                       // Toast.makeText(FoodList.this, ""+odia.getName(), Toast.LENGTH_SHORT).show();
                        Intent foodDetail = new Intent(FoodList.this,FoodDetail.class);
                        foodDetail.putExtra("foodId",adapter.getRef(position).getKey());//Sending food id o new Activity
                        startActivity(foodDetail);
                    }
                });
            }
        };


        //Set Adapter
      //  Log.d("TAG",""+adapter.getItemCount());
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateCartCount();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        customMenu=menu;
        updateCartCount();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        if (id == R.id.addTocart)
        {
            startActivity(new Intent(FoodList.this,Cart.class));
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
                startActivity(new Intent(FoodList.this,Cart.class));
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodList.this,Cart.class));
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
                    startActivity(new Intent(FoodList.this,Cart.class));
                }
            });

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(FoodList.this,Cart.class));
                }
            });


        }}
    
}
