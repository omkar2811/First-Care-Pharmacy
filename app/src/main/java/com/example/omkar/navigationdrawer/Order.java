package com.example.omkar.navigationdrawer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Order extends Fragment {
    View myView;
    TextView detail;
    Bookingdetail bd;
    List<String> booklist = new ArrayList<>();

    FirebaseAuth firebaseAuth;
    DatabaseReference myRef;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.orders, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        detail = (TextView)myView.findViewById(R.id.textView33);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        final String id = user.getUid();

        bd = new Bookingdetail();

        myRef= FirebaseDatabase.getInstance().getReference("Booking");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    bd= ds.getValue(Bookingdetail.class);
                    for(int i=0;i<bd.getMedilist().size();i++)
                    {
                        booklist.add(bd.getMedilist().get(i));
                        Log.d("Orders",booklist.get(i));
                    }
                }

                int tot = bd.getTotal1();
                String total2 = String.valueOf(tot);
                detail.setText("Medicine1:" + booklist.get(0) + "\n Medicine2:" + booklist.get(1)+ "\n Medicine3:" + booklist.get(2) +"\n Total Money to be paid On Delivery:"+total2);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        return myView;
    }
}

