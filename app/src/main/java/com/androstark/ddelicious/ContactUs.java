package com.androstark.ddelicious;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;

import io.paperdb.Paper;

public class ContactUs extends AppCompatActivity
{

    ImageView navigate_btn,call_btn,email_btn;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);


        Window window = this.getWindow();

        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));

        Paper.init(this);


        ImageView cat_back_btn = findViewById(R.id.cat_back_btn);

        cat_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });




        navigate_btn = findViewById(R.id.navigate_btn);
        call_btn = findViewById(R.id.call_btn);
        email_btn = findViewById(R.id.email_btn);



        navigate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {


                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", 20.34114, 85.80674, "Ibnus");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);




                try
                {
                    startActivity(intent);
                }
                catch(ActivityNotFoundException ex)
                {
                    try
                    {
                        Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(unrestrictedIntent);
                    }
                    catch(ActivityNotFoundException innerEx)
                    {
                        Toast.makeText(ContactUs.this, "Please install a maps application", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {


                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:9124737364"));

                try
                {
                    startActivity(intent);
                }
                catch(ActivityNotFoundException ex)
                {

                        Toast.makeText(ContactUs.this, "Your phone dialer does not supports direct dial.Please call us manually", Toast.LENGTH_LONG).show();
                }

            }
        });

        email_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{  "pritishsahoo1995@gmail.com"});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Hello There");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Add Message here");
                emailIntent.setPackage("com.google.android.gm");
                emailIntent.setType("message/rfc822");

                try {
                    startActivity(Intent.createChooser(emailIntent,
                            "Send email using..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getBaseContext(),
                            "No email clients installed.",
                            Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
