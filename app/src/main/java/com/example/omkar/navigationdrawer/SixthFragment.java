package com.example.omkar.navigationdrawer;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class SixthFragment extends Fragment {

    @Nullable
    View myView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;
    DatabaseReference confirmref;
    FirebaseAuth firebaseAuth;
    List<String> medilist = new ArrayList<>();

    int total1;
    EditText qty1,qty2,qty3;
    TextView txt;
    TextView price;
    TextView total;
    FloatingActionButton bck;
    CardView Buy;
    CardView confirm;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.second_layout, container, false);

        SharedPreferences spref = getActivity().getSharedPreferences("myfile1", Context.MODE_PRIVATE);
        String text = "Dhanakawdi";
        String med = spref.getString("medicines",text);
        final String med1 = spref.getString("medicine1",text);
        final String med2 = spref.getString("medicine2",text);
        final String med3 = spref.getString("medicine3",text);

        qty1 = (EditText)myView.findViewById(R.id.qty1);
        qty2 = (EditText)myView.findViewById(R.id.qty2);
        qty3 = (EditText)myView.findViewById(R.id.qty3);

        final String[] m1 = new String[1];
        final String[] m2 = new String[1];
        final String[] m3 = new String[1];
        final int[] p1 = new int[1];
        final int[] p2 = new int[1];
        final int[] p3 = new int[1];
        txt = (TextView)myView.findViewById(R.id.textView17);
        txt.setText(med);
        price = (TextView)myView.findViewById(R.id.textView20);

        total = (TextView)myView.findViewById(R.id.textView22);


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        final String id = user.getUid();
        Buy = (CardView)myView.findViewById(R.id.book);
        confirm = (CardView)myView.findViewById(R.id.book1);
        bck = (FloatingActionButton)myView.findViewById(R.id.floatingActionButton4);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.content_frame,new Stores());
                fr.commit();
            }
        });


        Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String q1 = qty1.getText().toString().trim();
                String q2 = qty2.getText().toString().trim();
                String q3 = qty3.getText().toString().trim();

                final int qt1 = Integer.parseInt(q1);
                final int qt2 = Integer.parseInt(q2);
                final int qt3 = Integer.parseInt(q3);

                p1[0] *= qt1;
                p2[0] *= qt2;
                p3[0] *= qt3;
                 total1 = p1[0] +p2[0] +p3[0];

                String total2 = String.valueOf(total1);
                total.setText(total2);
                medilist.add(med1);
                medilist.add(med2);
                medilist.add(med3);

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                confirmref = FirebaseDatabase.getInstance().getReference("Booking");
                Bookingdetail bookingdetail = new Bookingdetail(medilist,total1);
                confirmref.child(id).setValue(bookingdetail);

                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.content_frame,new Booking());
                fr.commit();



            }
        });
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("medicineprice");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(med1)){
                    m1[0] = String.valueOf(dataSnapshot.child(med1).getValue(Integer.class));
                    p1[0] = dataSnapshot.child(med1).getValue(Integer.class) ;
                }
                if(dataSnapshot.hasChild(med2))
                {
                    m2[0] = String.valueOf(dataSnapshot.child(med2).getValue(Integer.class));
                    p2[0] = dataSnapshot.child(med2).getValue(Integer.class) ;
                }
                if(dataSnapshot.hasChild(med3))
                {
                    m3[0] = String.valueOf(dataSnapshot.child(med3).getValue(Integer.class));
                    p3[0] = dataSnapshot.child(med3).getValue(Integer.class) ;
                }


                price.setText(m1[0] + "\n"+"\n" + m2[0] + "\n"+"\n" + m3[0]);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


        return myView;
    }
}
