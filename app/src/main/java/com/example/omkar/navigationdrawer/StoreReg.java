package com.example.omkar.navigationdrawer;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class StoreReg extends Fragment {

    DatabaseReference databaseReference;
    View myView;
    FirebaseAuth firebaseAuth ;

    EditText etname;
    EditText etstore;
    EditText etloc;
    EditText etmob;
    EditText etpass;
    EditText etadd;
    CardView reg2;

StoreInformation storeInformation;
    public List<String> medicine = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.medicalinfo, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("stores");
        etname = (EditText)myView.findViewById(R.id.editText);
        etstore = (EditText)myView.findViewById(R.id.editText4);
        etloc = (EditText)myView.findViewById(R.id.editText1);
        etmob = (EditText)myView.findViewById(R.id.editText2);
        etpass = (EditText)myView.findViewById(R.id.editText3);
        etadd = (EditText) myView.findViewById(R.id.editText5);
        firebaseAuth = FirebaseAuth.getInstance();



        reg2 = (CardView)myView.findViewById(R.id.register2);

        reg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regstore();
                saveInfo();
            }
        });


        return myView;
    }

    public void saveInfo()

    {
        String ownname = etname.getText().toString().trim();
        String stname = etstore.getText().toString().trim();
        String stloc= etloc.getText().toString().trim();
        String stmob = etmob.getText().toString().trim();
        String stpass = etpass.getText().toString().trim();
        String stadd = etadd.getText().toString().trim();
        medicine.add("vicks");
        storeInformation = new StoreInformation(medicine,stadd,ownname,stname,stloc,stmob,stpass);
    }

    public void regstore()
    {
        String ownname = etname.getText().toString().trim();
        String spass = etpass.getText().toString().trim();
        if(TextUtils.isEmpty(ownname))
        {
            Toast.makeText(StoreReg.this.getActivity().getApplicationContext(),"Please Enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(spass))
        {
            Toast.makeText(StoreReg.this.getActivity().getApplicationContext(),"Please Enter password", Toast.LENGTH_LONG).show();
            return;
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            firebaseAuth.createUserWithEmailAndPassword(ownname,spass)
                    .addOnCompleteListener((Activity)StoreReg.this.getContext(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(StoreReg.this.getActivity().getApplicationContext(), "Registered Successfully", Toast.LENGTH_LONG).show();

                                final FirebaseUser user = firebaseAuth.getCurrentUser();
                                String id = user.getUid();
                                databaseReference.child(id).setValue(storeInformation);
                                Toast.makeText(StoreReg.this.getActivity().getApplicationContext(),"Store Information Saved", Toast.LENGTH_LONG).show();

                                FragmentTransaction fr = getFragmentManager().beginTransaction();
                                fr.replace(R.id.content_frame,new ThirdFragment());
                                fr.commit();
                            } else {
                                FirebaseAuthException e = (FirebaseAuthException )task.getException();
                                Toast.makeText(StoreReg.this.getActivity().getApplicationContext(), "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(StoreReg.this.getActivity().getApplicationContext(), "Not registered Succesfully", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }


    }
}
