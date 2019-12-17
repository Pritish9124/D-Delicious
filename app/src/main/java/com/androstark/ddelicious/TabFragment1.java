package com.androstark.ddelicious;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class TabFragment1 extends Fragment {


    EditText etusername, etpassword;
    Button signin;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab_fragment1, container, false);


            signin = rootView.findViewById(R.id.sign_btn);
        etusername = rootView.findViewById(R.id.etPhone);
        etpassword =  rootView.findViewById(R.id.etPassword);

        Paper.init(getActivity());

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = firebaseDatabase.getReference("user");



        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Common.isConnectedToInternet(getActivity())) {



                    final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Logging in...");
                    progressDialog.show();
                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.child(etusername.getText().toString()).exists()) {

                                progressDialog.dismiss();
                                User user = dataSnapshot.child(etusername.getText().toString()).getValue(User.class);
                                user.setPhone(etusername.getText().toString());


                                if (user.getPassword().equals(etpassword.getText().toString())) {

                                    Paper.book().write(Common.USER_KEY, etusername.getText().toString());
                                    Paper.book().write(Common.PWD_KEY, etpassword.getText().toString());


                                    Toast.makeText(getActivity(), "Signin Successfully", Toast.LENGTH_SHORT).show();
                                    Common.currentuser = user;
                                    startActivity(new Intent(getActivity(), Home.class));
                                    getActivity().finish();
                                } else {

                                    Toast.makeText(getActivity(), "Sign in failed!!!! ", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "User does not exists ", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else

                {
                    Toast.makeText(getActivity(), "Please check Internet Connection", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });


        return rootView;
    }


}
