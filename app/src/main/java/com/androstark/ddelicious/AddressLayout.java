package com.androstark.ddelicious;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import com.androstark.ddelicious.Cart;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androstark.ddelicious.Common.Common;
import com.androstark.ddelicious.Database.Database;
import com.androstark.ddelicious.Model.AddressData;
import com.androstark.ddelicious.Model.Order;
import com.androstark.ddelicious.Model.Request;
import com.androstark.ddelicious.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.paperdb.Paper;

public class AddressLayout extends AppCompatActivity
{

    Button ad_button;
    MaterialEditText ad_name,ad_address,ad_phone;
    TextView txtTotalPrice;
    List<Order> cart = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference requests;
    Intent intent;


    EditText shipping_name,shipping_phone,address1,address2,pincode,city;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Cart cart1 =new Cart();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_layout);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        Paper.init(this);


        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        ad_button = (Button)findViewById(R.id.ad_button);
        txtTotalPrice = (TextView) findViewById(R.id.total);
        ad_address = (MaterialEditText) findViewById(R.id.ad_address);
        ad_name = (MaterialEditText) findViewById(R.id.ad_name);
        ad_phone =(MaterialEditText)findViewById(R.id.ad_phone);



        shipping_name = findViewById(R.id.shhipping_name);
        shipping_phone = findViewById(R.id.shipping_phone);
        address1 = findViewById(R.id.address1);
        address2 = findViewById(R.id.address2);
        pincode = findViewById(R.id.pincode);
        city = findViewById(R.id.city);



       // Order order = new Order();
        cart= new Database(this).getCarts();



        Database databaseaddress =  new Database(getBaseContext());
        if (databaseaddress.getAddress().size()>0)
        {
            ad_button.setText("SAVE");
        }


        ad_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String s1 = shipping_name.getText().toString();
                String s2 = address1.getText().toString();
                String s3 = address2.getText().toString();
                String s4 = city.getText().toString();
                String s5 = "tempcity";
                String s6 = pincode.getText().toString();
                String s7 = shipping_phone.getText().toString();
                String s8 ="IN";

                if (s1.isEmpty()||s2.isEmpty()||s3.isEmpty()||s4.isEmpty()||s5.isEmpty()||s6.isEmpty()||s7.isEmpty()||s8.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"All fieds are required",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    new Database(getBaseContext()).addAddress(new AddressData("",
                            s1,
                            s2,
                            s3,
                            s4,
                            s5,
                            s6,
                            s7,
                            s8
                    ));

                    Toast.makeText(getApplicationContext(), "Address Saved", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(AddressLayout.this, Delivery.class);

                intent.putExtra("shippping_name",shipping_name.getText().toString());
                intent.putExtra("shippping_phone",shipping_phone.getText().toString());
                intent.putExtra("shippping_address1",address1.getText().toString());
                intent.putExtra("shippping_adress2",address2.getText().toString());
                intent.putExtra("shipping_pincode",pincode.getText().toString());
                intent.putExtra("shipping_city",city.getText().toString());
                intent.putExtra("shippping_state","OD");
                intent.putExtra("shippping_country","IN");
                startActivity(intent);
                finish();




                
           /*
              intent =getIntent();
                String itemprice = intent.getExtras().getString("extradata");




             if (!ad_name.getText().toString().isEmpty() && !ad_phone.getText().toString().isEmpty() && !ad_address.getText().toString().isEmpty() )
                {

                    Request request = new Request(
                            ad_name.getText().toString(),
                            ad_phone.getText().toString(),
                            ad_address.getText().toString(),
                            itemprice,
                            cart,Paper.book().read(Common.USER_KEY).toString()


                    );

                    requests.child(String.valueOf(System.currentTimeMillis()))
                            .setValue(request);
                    new Database(getBaseContext()).cleanCart();
                    Toast.makeText(AddressLayout.this, "Thank You , Order Placed!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),OrderPlaced.class));

                    finish();

                }
                else
                    Toast.makeText(AddressLayout.this, "Please Enter All the feilds", Toast.LENGTH_SHORT).show(); */

            }
        });
    }
}
