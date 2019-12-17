package com.androstark.ddelicious;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androstark.ddelicious.Common.Common;
import com.androstark.ddelicious.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

/**
 * Created by papu on 1/19/2018.
 */

public class TabFragment2 extends Fragment {

    EditText Name, phone, password;
    Button signUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       
        View rootView = inflater.inflate(R.layout.tab_fragment2, container, false);


        Name = rootView.findViewById(R.id.signupName);
        phone = rootView.findViewById(R.id.signupuser);
        password = rootView.findViewById(R.id.signUpPwd);
        signUp = rootView.findViewById(R.id.btnusrSignUp);


        Paper.init(getActivity());
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference("user");


        signUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (Common.isConnectedToInternet(getActivity()))
                {
                    final ProgressDialog progressDialog = new ProgressDialog(getActivity());

                    progressDialog.setMessage("Signing Up...");
                    progressDialog.show();

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(phone.getText().toString()).exists())
                            {
                                progressDialog.dismiss();
                               // Toast.makeText(getActivity(), "Phone number exists", Toast.LENGTH_SHORT).show();

                            }
                            else
                                {
                                progressDialog.dismiss();
                                User user = new User(Name.getText().toString(), password.getText().toString());
                                databaseReference.child(phone.getText().toString()).setValue(user);
                                Toast.makeText(getActivity(), "Registered Successfully", Toast.LENGTH_SHORT).show();


                                Paper.book().write(Common.USER_KEY, phone.getText().toString());
                                Paper.book().write(Common.PWD_KEY, password.getText().toString());
                                Paper.book().write(Common.USER_NAME,Name.getText().toString());


                                startActivity(new Intent(getActivity(), Home.class));
                                getActivity().finish();


                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else
                {
                    Toast.makeText(getActivity(), "Check Internet Conneciton", Toast.LENGTH_LONG).show();
                }
            }
        });


        return rootView;
    }
}
