package com.androstark.ddelicious;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androstark.ddelicious.Common.Common;
import com.androstark.ddelicious.Database.Database;
import com.androstark.ddelicious.Interface.ItemClickLIstener;
import com.androstark.ddelicious.Model.Category;
import com.androstark.ddelicious.Model.Food;
import com.androstark.ddelicious.Model.Order;
import com.androstark.ddelicious.ViewHolder.FoodViewHolder;
import com.androstark.ddelicious.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference category;
    ImageView imageView;
    TextView textView;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;
    AlertDialog.Builder builder;

    Menu customMenu;
    List<Order> cart = new ArrayList<>();

    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_home);
        updateCartCount();
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);


        Paper.init(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        category = firebaseDatabase.getReference("Category");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show(); */
               startActivity(new Intent(Home.this,Cart.class));
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        Menu menu = navigationView.getMenu();
        MenuItem support = menu.findItem(R.id.support);
        //myaccount = menu.findItem(R.id.myaccountitem);
        SpannableString s = new SpannableString(support.getTitle());
       // SpannableString s2 = new SpannableString(myaccount.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s.length(), 0);
       // s2.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s.length(), 0);
        support.setTitle(s);
      //  myaccount.setTitle(s2);
        navigationView.setNavigationItemSelectedListener(this);

        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        String guestname;
       View headerView = navigationView.getHeaderView(0);
        textView = (TextView)headerView.findViewById(R.id.guestname);

        if (Paper.book().read(Common.USER_NAME)!=null && !Paper.book().read(Common.USER_NAME).toString().isEmpty())
        {
            textView.setText("Welcome "+Paper.book().read(Common.USER_NAME).toString());

        }
        else
        {
            textView.setText("Welcome "+Common.currentuser.getName());

        }


        //Load Menu From firebase
        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

      //  if (!Common.isConnectedToInternet(this))
            loadMenu();
       // else
      //  {
       //     Toast.makeText(this, "Check Internet Connection", Toast.LENGTH_LONG).show();
       //     return;
      //  }







    }




    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateCartCount();
    }



    public void loadMenu()
    {




        adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,category)
        {


            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position)
            {
                viewHolder.textMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.imageView);


                viewHolder.setItemClickLIstener(new ItemClickLIstener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick)
                    {
                       // category = firebaseDatabase.getReference("Category");

                        // This will get category and send it to new Activity
                        Intent foodList = new Intent(Home.this,FoodList.class);
                        foodList.putExtra("myextradata",adapter.getRef(position).getKey());
                        startActivity(foodList);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            startActivity(new Intent(Home.this,Cart.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu)
        {
            startActivity(new Intent(this,Home.class));
        }
        else if (id == R.id.nav_cart)
        {
            Intent intent = new Intent(Home.this, Cart.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_orders)

        {
            Intent orders = new Intent(Home.this, OrderStatus.class);
            startActivity(orders);
           // Toast.makeText(this, "To Be Updated! \n Stay Tunned ", Toast.LENGTH_SHORT).show();
        }
        /*else if (id == R.id.nav) {

        }*/
        else if (id == R.id.nav_signout)
        {

            Paper.book().destroy();
           startActivity(new Intent(this,MainActivity.class));
            //Toast.makeText(this, "Well Still Working on It", Toast.LENGTH_SHORT).show();

           new Thread(new Runnable() {
               @Override
               public void run() {
                   builder  = new AlertDialog.Builder(Home.this);
                   builder.setTitle("Sign Out");
                   builder.setMessage("Signing Out");
                   builder.show();
                   try {
                       Thread.sleep(Long.parseLong("1000"));
                   } catch (InterruptedException e)
                   {
                       e.printStackTrace();
                   }
               }
           });


            Intent signin = new Intent(Home.this,SignIn.class);
            signin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(signin);
        }
        else if (id == R.id.nav_customer_care)
        {


            Intent orders = new Intent(Home.this, ContactUs.class);
            startActivity(orders);
        }
        else if (id == R.id.nav_feedback)
        {
            Toast.makeText(this, "This is a Demo App!", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                startActivity(new Intent(Home.this,Cart.class));
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,Cart.class));
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
                    startActivity(new Intent(Home.this,Cart.class));
                }
            });

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Home.this,Cart.class));
                }
            });


        }}

}
