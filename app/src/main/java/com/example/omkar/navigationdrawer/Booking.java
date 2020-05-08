package com.example.omkar.navigationdrawer;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Booking extends Fragment {

    @Nullable
    View myView;
    TextView id;
    TextView track;
    FirebaseAuth firebaseAuth;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.confirmbooking, container, false);

        id = (TextView)myView.findViewById(R.id.textView26);

        track =(TextView)myView.findViewById(R.id.textView29);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        track.setText(user.getEmail());
        id.setText(user.getUid());
        Log.d("Booking.java",user.getUid());

        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.content_frame,new SecondFragment());
                fr.commit();
            }
        });



        return myView;


    }
}
