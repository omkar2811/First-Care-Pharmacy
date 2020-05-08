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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class MedicineAdd extends Fragment {
    View myView;
    FirebaseAuth firebaseAuth;
    DatabaseReference myRef;
    ListView mlistview;
    List<String> med = new ArrayList<>();
    ArrayList<String> list1;
    ArrayAdapter<String> adapter1;
    TextView username;
    Medicine1 medicine1;
    Button medaadd;
    Button delmed;
    Button sub;
    EditText medi;

    public List<String> mediad = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.storeowner, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("stores");

        username = (TextView) myView.findViewById(R.id.textView15);
        FloatingActionButton fab3;


        medi = (EditText)myView.findViewById(R.id.editTextadd);
        mlistview = (ListView) myView.findViewById(R.id.listview1);
        list1 = new ArrayList<>();
        adapter1 = new ArrayAdapter<String>(MedicineAdd.this.getActivity().getApplicationContext(), R.layout.store_info_1, R.id.storeinfo1, list1);

        medaadd = (Button)myView.findViewById(R.id.button3);

        sub = (Button)myView.findViewById(R.id.button4);

        final FirebaseUser user = firebaseAuth.getCurrentUser();
        username.setText("Welcome:" + user.getEmail());
        medicine1 = new Medicine1();

        fab3 = (FloatingActionButton)myView.findViewById(R.id.floatingActionButton4);

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.content_frame,new ThirdFragment());
                fr.commit();
            }
        });


        medaadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String meditem = medi.getText().toString().trim();
                med.add(meditem);
                medi.setText("");
            }
        });



        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                myRef.child(user.getUid()).child("medicine").setValue(med);
                med.clear();
                list1.clear();
            }
        });

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        Log.d(TAG, "For loops");
                        medicine1 = ds.getValue(Medicine1.class);
                        if (medicine1.getOwnname().contentEquals(user.getEmail())) {
                            for (int i = 0; i < medicine1.getMedicine().size(); i++) {
                                list1.add(medicine1.getMedicine().get(i));
                                med.add(medicine1.getMedicine().get(i));
                            }
                        }
                    }

                    mlistview.setAdapter(adapter1);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                String medicinedetail = list1.get(i);
                SharedPreferences spref = getActivity().getSharedPreferences("myfile2", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = spref.edit();
                Log.d("MedicineAdd.java",medicinedetail);
                editor.putString("medicine",medicinedetail);

                editor.commit();
                editor.apply();


                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.content_frame,new MedicineDetail());
                fr.commit();
            }
        });


        return myView;

    }
}

