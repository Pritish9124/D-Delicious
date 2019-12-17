package com.androstark.ddelicious;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.androstark.ddelicious.Common.Common;
import com.androstark.ddelicious.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import io.paperdb.Paper;

public class SignIn extends AppCompatActivity {
    Button btnsignin;
    EditText etusername, etpassword;
    com.rey.material.widget.CheckBox ckbRemember;

    FrameLayout myframe;
    FragmentTransaction ft;
    Fragment fr = null;


    Button login_bt, register_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        myframe = findViewById(R.id.my_frame);
        login_bt = findViewById(R.id.login_bt);
        register_bt = findViewById(R.id.register_bt);

        Paper.init(this);

        //Initialization of paper

        Paper.init(this);



                fr = new TabFragment1();

                if (fr != null) {
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.my_frame, fr);
                    ft.commit();

                }

            //Initialization of firebase
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference table_user = firebaseDatabase.getReference("user");


            login_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    login_bt.setBackgroundColor(ContextCompat.getColor(SignIn.this,R.color.white));
                    login_bt.setTextColor(ContextCompat.getColor(SignIn.this,R.color.signupcolor));



                    register_bt.setBackgroundColor(ContextCompat.getColor(SignIn.this,R.color.signupcolor));
                    register_bt.setTextColor(ContextCompat.getColor(SignIn.this,R.color.white));
                    fr = new TabFragment1();

                    if (fr != null) {
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.my_frame, fr);
                        ft.commit();

                    }
                }
            });

            register_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    register_bt.setBackgroundColor(ContextCompat.getColor(SignIn.this,R.color.white));
                    register_bt.setTextColor(ContextCompat.getColor(SignIn.this,R.color.signupcolor));

                    login_bt.setBackgroundColor(ContextCompat.getColor(SignIn.this,R.color.signupcolor));
                    login_bt.setTextColor(ContextCompat.getColor(SignIn.this,R.color.white));
                    fr = new TabFragment2();

                    if (fr != null) {
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.my_frame, fr);
                        ft.commit();

                    }
                }
            });

        }
}

