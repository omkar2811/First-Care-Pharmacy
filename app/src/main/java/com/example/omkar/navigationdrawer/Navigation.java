package com.example.omkar.navigationdrawer;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

public class Navigation extends Fragment {

    DatabaseReference databasemedicine = FirebaseDatabase.getInstance().getReference("medicine");
    @Nullable
    EditText location;
    EditText medicine1;
    EditText medicine2;
    EditText medicine3;
    Button addloc;
    FloatingActionButton floatingActionButton;
    public String loc;


    View myView;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.nav, container, false);

        location = (EditText) myView.findViewById(R.id.editText);
        medicine1 = (EditText) myView.findViewById(R.id.editText2);
        medicine2 = (EditText) myView.findViewById(R.id.editText3);
        medicine3 = (EditText) myView.findViewById(R.id.editText4);

        floatingActionButton = (FloatingActionButton) myView.findViewById(R.id.floatingActionButton);
        Button submit = (Button) myView.findViewById(R.id.search);

        addloc = (Button) myView.findViewById(R.id.button2);

       addloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission(Navigation.this.getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Navigation.this.getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{
                                        Manifest.permission.ACCESS_COARSE_LOCATION,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                },
                                1000);
                    }
                    return;
                } else {

                    LocationManager locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
                    Location location1 = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    String Street = hereLocation(location1.getLatitude(),location1.getLongitude());
                    location.setText(Street);
                }
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.content_frame,new FirstFragment());
                fr.commit();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View myview) {


                addMedicine();

                String l = medicine1.getText().toString().trim();
                String l1 = location.getText().toString().trim();
                String l2 = medicine2.getText().toString().trim();
                String l3 = medicine3.getText().toString().trim();
                SharedPreferences spref = getActivity().getSharedPreferences("myfile", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = spref.edit();
                editor.putString("medicine",l);
                editor.putString("location",l1);
                editor.putString("medicine1",l2);
                editor.putString("medicine2",l3);
                editor.commit();
                editor.apply();

                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.content_frame,new Stores());
                fr.commit();

            }
        });
        return myView;
    }

    private String hereLocation(double lat,double lon)
    {
        String address = "";
        String fulladdress = "";
        String area = "";
        Geocoder geocoder =  new Geocoder(Navigation.this.getActivity().getApplicationContext(), Locale.getDefault());

        List<Address> addressList;
        try
        {
            addressList=geocoder.getFromLocation(lat,lon,1);


            address = addressList.get(0).getAddressLine(0);
            area = addressList.get(0).getLocality();
            fulladdress =  address +"," + area ;

        }catch (IOException e){
            e.printStackTrace();
        }
        return fulladdress;
    }

    public void addMedicine() {
        //getting the values to save

        String loc = location.getText().toString().trim();
        String med1 = medicine1.getText().toString().trim();
        String med2 = medicine2.getText().toString().trim();
        String med3 = medicine3.getText().toString().trim();




        //checking if the value is provided
        if (!TextUtils.isEmpty(loc)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = databasemedicine.push().getKey();

            //creating an Artist Object
            Medicine med = new Medicine(id, loc, med1,med2,med3);

            //Saving the Artist
            databasemedicine.child(id).setValue(med);

            //setting edittext to blank again


      .      //displaying a success toast
            Toast.makeText(Navigation.this.getActivity().getApplicationContext(), "Medicine added", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(Navigation.this.getActivity().getApplicationContext(), "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }
}


