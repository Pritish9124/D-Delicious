package com.androstark.ddelicious;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.androstark.ddelicious.Database.Database;
import com.androstark.ddelicious.Model.AddressData;
import com.androstark.ddelicious.ViewHolder.AddressAdapter;

import java.util.ArrayList;
import java.util.List;


public class AddressDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    LinearLayout emty_address_layout;
    CardView address_save_cardview;

    List<AddressData> addressData = new ArrayList<>();
    AddressAdapter adapter;
    Button btn_add_address;
    String something;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_details);

        recyclerView = (RecyclerView) findViewById(R.id.listaddress);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        btn_add_address = findViewById(R.id.btn_add_address);
        emty_address_layout = findViewById(R.id.address_emty_layout);
        address_save_cardview = findViewById(R.id.address_cardlayout);

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



     /*
       Database database =  new Database(getBaseContext());
        if (database.getAddress().size() >0)
        {
            emty_address_layout.setVisibility(View.INVISIBLE);
        }
        else
        {
            address_save_cardview.setCardBackgroundColor(getResources().getColor(R.color.transparent));
            address_save_cardview.setCardElevation(0);
            emty_address_layout.setVisibility(View.VISIBLE);
        }
*/
        btn_add_address.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(AddressDetails.this,AddressLayout.class));
            }
        });


        LoadAddressDetails();
    }

    private void LoadAddressDetails()
    {
        addressData = new Database(this).getAddress();
        adapter = new AddressAdapter(addressData, this);
        recyclerView.setAdapter(adapter);

    }
}
