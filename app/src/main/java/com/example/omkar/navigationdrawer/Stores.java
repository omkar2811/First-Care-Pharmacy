package com.example.omkar.navigationdrawer;

import android.app.DownloadManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Stores extends Fragment {

    @Nullable
    View myView;



    private static final String TAG = "Stores.java";
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;
    ListView listview;

    FloatingActionButton fab1;

    ArrayList<String> list;
    ArrayList<String> list1;
    ArrayAdapter<String> adapter;

    Medicine1 medicine;
    String[] data = {"Text1","Text2","Text3"};

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.stores, container, false);

        SharedPreferences spref = getActivity().getSharedPreferences("myfile", Context.MODE_PRIVATE);
        String text = "Dhanakawdi";
        final String med = spref.getString("medicine",text);
        final String loc = spref.getString("location",text);
        final String med1 = spref.getString("medicine1",text);
        final String med2 = spref.getString("medicine2",text);

        medicine = new Medicine1();
        listview = (ListView) myView.findViewById(R.id.listview);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("stores");

        fab1 = (FloatingActionButton)myView.findViewById(R.id.floatingActionButton2);
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        adapter = new ArrayAdapter<String>(Stores.this.getActivity().getApplicationContext(), R.layout.store_info, R.id.storeinfo, list);

        Log.d(TAG,"From Bundle:" + med);
        Log.d(TAG, "From Bundle:" + med1);
        Log.d(TAG, "From Bundle:" + loc);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.content_frame,new Navigation());
                fr.commit();
            }
        });

        final int[] x = {0};
        final int[] y = {0};
        final int[] z = {0};
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {

                            Log.d(TAG, "For loops");
                            medicine = ds.getValue(Medicine1.class);

                            for(int i=0;i<medicine.getMedicine().size();i++) {
                                if (medicine.getMedicine().get(i).contentEquals(med)  && loc.contains(medicine.getStloc()))
                                {
                                    x[0] = 1;
                                }

                                if(medicine.getMedicine().get(i).contentEquals(med1) && loc.contains(medicine.getStloc()))
                                {
                                    y[0] =1;
                                }

                                if(medicine.getMedicine().get(i).contentEquals(med2) && loc.contains(medicine.getStloc()))
                                {
                                    z[0] =1;
                                }
                            }

                            if(x[0] == 1 && y[0] == 1 && z[0] == 1) {
                                list.add("Store Name:" + medicine.getStname().toString() + "\n" + "Store Location:" + medicine.getStadd() +
                                        "\n" + "Contact No:" + medicine.getStno() + "\n" + med + ": Available" + "\n" + med1 + ": Available" + "\n" +  med2 + ":Available");
                                list1.add("Medicine1 : " + med + "\n" + "\n"+ "Medicine2 : " + med1  + "\n" +"\n"+ "Medicine3 : " + med2 +"\n");

                            }
                            else if(x[0] == 1 && y[0] == 1 && z[0] == 0)
                            {
                                list.add("Store Name:" + medicine.getStname().toString() + "\n" + "Store Location:" + medicine.getStadd() +
                                        "\n" + "Contact No:" + medicine.getStno() + "\n" + med + ": Available"+ "\n" + med1 + ": Available" + "\n" +  med2 + ":Unavailable");

                                list1.add("Medicine1 : " + med  + "\n" + "Medicine2 : " + med1);
                            }
                            else if(x[0] == 1 && y[0] == 0 && z[0] == 1)
                            {
                                list.add("Store Name:" + medicine.getStname().toString() + "\n" + "Store Location:" + medicine.getStadd() +
                                        "\n" + "Contact No:" + medicine.getStno() + "\n" + med + ": Available"+ "\n" + med1 + ": Unavailable" + "\n" +  med2 + ":Available");

                                list1.add("Medicine1 : " + med  + "\n" + "Medicine2 : " + med2);
                            }
                            else if(x[0] == 1 && y[0] == 0 && z[0] == 0)
                            {
                                list.add("Store Name:" + medicine.getStname().toString() + "\n" + "Store Location:" + medicine.getStadd() +
                                        "\n" + "Contact No:" + medicine.getStno() + "\n" + med + ": Available"+ "\n" + med1 + ": Unavailable" + "\n" + med2 + ":Unavailable");

                                list1.add("Medicine1 : " + med + "\n");
                            }
                            else if(x[0] == 0 && y[0] == 1 && z[0] == 1)
                            {
                                list.add("Store Name:" + medicine.getStname().toString() + "\n" + "Store Location:" + medicine.getStadd() +
                                        "\n" + "Contact No:" + medicine.getStno() + "\n" + med + ": Unavailable"+ "\n" + med1 + ": Available"+ "\n" + med2 + ":Available");

                                list1.add("Medicine1 : " + med1 + "\n" +  "Medicine2 : " + med2);
                            }
                            else if(x[0] == 0 && y[0] == 1 && z[0] == 0)
                            {
                                list.add("Store Name:" + medicine.getStname().toString() + "\n" + "Store Location:" + medicine.getStadd() +
                                        "\n" + "Contact No:" + medicine.getStno() + "\n" + med + ": Unavailable"+ "\n" + med1 + ": Available"+ "\n" + med2 + ":Unavailable");

                                list1.add("Medicine1 : " + med1 + "\n");
                            }

                            else if(x[0] == 0 && y[0] == 0 && z[0] == 1)
                            {
                                list.add("Store Name:" + medicine.getStname().toString() + "\n" + "Store Location:" + medicine.getStadd() +
                                        "\n" + "Contact No:" + medicine.getStno() + "\n" + med + ": Unavailable"+ "\n" + med1 + ": Unavailable"+ "\n" + med2 + ":Available");

                                list1.add("Medicine1 : " + med2 + "\n");
                            }

                            x[0] = 0;
                            y[0] = 0;
                            z[0] = 0;
                        }

                        listview.setAdapter(adapter);

                    }




                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                        String medlist = list1.get(i);
                        SharedPreferences spref = getActivity().getSharedPreferences("myfile1", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = spref.edit();
                        Log.d("Stores.java",medlist);
                        editor.putString("medicines",medlist);
                        editor.putString("medicine1",med);
                        editor.putString("medicine2",med1);
                        editor.putString("medicine3",med2);
                        editor.commit();
                        editor.apply();


                        FragmentTransaction fr = getFragmentManager().beginTransaction();
                        fr.replace(R.id.content_frame,new SixthFragment());
                        fr.commit();
                    }
                });

        return myView;

    }
};



