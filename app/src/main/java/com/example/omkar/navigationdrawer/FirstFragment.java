package com.example.omkar.navigationdrawer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirstFragment extends Fragment{

    @Nullable
    View myView;
    FirebaseAuth firebaseAuth;

    TextView username ;
    FloatingActionButton fab2;

    Button order;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.first_layout,container,false);

        firebaseAuth = FirebaseAuth.getInstance();

        username = (TextView)myView.findViewById(R.id.textView13);
        CardView submit = (CardView) myView.findViewById(R.id.submit);

        order = (Button)myView.findViewById(R.id.button6) ;

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.content_frame,new Order());
                fr.commit();
            }
        });
        fab2 = (FloatingActionButton)myView.findViewById(R.id.floatingActionButton3);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.content_frame,new SecondFragment());
                fr.commit();
            }
        });
        FirebaseUser user = firebaseAuth.getCurrentUser();
        username.setText("Welcome:" + user.getEmail());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View myview) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.content_frame,new Navigation());
                fr.commit();
                }
        });
        return myView;
    }


}

