package com.androstark.ddelicious;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.androstark.ddelicious.Model.User;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;

import com.androstark.ddelicious.Common.Common;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    Button btnSIgnUp, btnSignIn1,btnskip;
    EditText etUsername;

    //TextView slogan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSIgnUp = (Button) findViewById(R.id.btnSignUp);
        btnSignIn1 = (Button) findViewById(R.id.btnSignIn1);
        btnskip = (Button)findViewById(R.id.btnSkip);
        //This should change fonts style that u put in asserts folder
        /*slogan = (TextView)findViewById(R.id.slogan);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Roboto.ttf");
        slogan.setTypeface(typeface); */


        Paper.init(this);

        btnSignIn1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, SignIn.class).putExtra("fragment_signup","fragment_signin"));
            }
        });

        btnSIgnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, SignIn.class).putExtra("fragment_signup","fragment_signup"));
            }
        });

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = firebaseDatabase.getReference("user");
        btnskip.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               // if (Common.currentuser.getPassword().isEmpty())
                //{
                //   Toast.makeText(MainActivity.this, "You are logged", Toast.LENGTH_SHORT).show();

              //  }
               // if (Common.currentuser.getName()  )
                   // startActivity(new Intent(MainActivity.this, Home.class));
              //  else
              //  {
                  //  Toast.makeText(MainActivity.this, "Sorry You Need to Login First!", Toast.LENGTH_SHORT).show();

              //  }

                //FirebaseDatabase firebaseDatabase;
               // DatabaseReference databaseReference;


            /*   if(Common.currentuser.getName().isEmpty())
                {
                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);

                }
                else
                {

                    Intent intent = new Intent(MainActivity.this,Home.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);

               }*/


                Toast.makeText(MainActivity.this, "Sorry You Need to Login First!", Toast.LENGTH_SHORT).show();





            }
        });

        String user = Paper.book().read(Common.USER_KEY);
        String pwd = Paper.book().read(Common.PWD_KEY);
        if (user!=null && pwd!=null)
        {
            if (!user.isEmpty() && !pwd.isEmpty())
            {
                login(user,pwd);
            }
        }


    }

    private void login(final String phone, final String pwd)
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = firebaseDatabase.getReference("user");
        if (Common.isConnectedToInternet(getBaseContext()))
        {


            final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Hold On...");
            progressDialog.show();
            table_user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.child(phone).exists()) {

                        progressDialog.dismiss();
                        User user = dataSnapshot.child(phone).getValue(User.class);
                        user.setPhone(phone);


                        if (user.getPassword().equals(pwd)) {
                           // Toast.makeText(MainActivity.this, "Signin Successfully", Toast.LENGTH_SHORT).show();
                            Common.currentuser = user;
                            startActivity(new Intent(MainActivity.this, Home.class));
                            finish();
                        } else {

                            Toast.makeText(MainActivity.this, "Sign in failed!!!! ", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "User does not exists ", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else

        {
            Toast.makeText(MainActivity.this, "Please check Internet Connection", Toast.LENGTH_LONG).show();

            return;
        }
    }
    }

