package com.androstark.ddelicious;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.androstark.ddelicious.Common.Common;
import com.androstark.ddelicious.Model.Order;
import com.androstark.ddelicious.Model.Request;
import com.androstark.ddelicious.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import io.paperdb.Paper;

public class OrderStatus extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //Starting of firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = (RecyclerView) findViewById(R.id.listorders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Paper.init(this);

        loadOrders(Paper.book().read(Common.USER_KEY).toString());

    }

    private void loadOrders(String phone) {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests.orderByChild("userid").equalTo(phone)
                     //   .equalTo(phone)
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {
                viewHolder.txtOrderId.setText("Order No: "+adapter.getRef(position).getKey());
                viewHolder.txtOrderAddress.setText(model.getAddress());
                viewHolder.txtOrderPhone.setText(model.getPhone());
                viewHolder.txtOrderStatus.setText(convertCodeToStatus(model.getStatus()));
               List<Order> myorder= model.getFoods();
                Order order = myorder.get(0);
                Log.e("ordersize", String.valueOf(myorder.size()));

                viewHolder.txtOrderName.setText("◘ "+order.getProductName());
                viewHolder.txtOrderPrice.setText("₹ "+order.getPrice());
                viewHolder.txtOrderTotal.setText("₹ "+model.getTotal());
                viewHolder.txtOrderQty.setText("Qty: "+order.getQuantity());


            }
        };
        recyclerView.setAdapter(adapter);
    }

    private String convertCodeToStatus(String status)
    {
        if (status.equals("0"))
            return "Preparing Your Order";
        else if (status.equals("1"))
            return "Its on the Way";
        else if (status.equals("2"))
            return "Delivered!";
        else
            return "Order Cancelled!";
    }
}
