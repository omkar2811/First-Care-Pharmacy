package com.example.omkar.navigationdrawer;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import java.util.concurrent.Executor;

public class Register extends Fragment {
    @Nullable

    EditText etname ;
    EditText etemail ;
    EditText etmobile;
    EditText etpass ;

    private static final String TAG = "Register.java";
    CardView reg1 ;
    ProgressDialog progressDialog ;

    FirebaseAuth firebaseAuth ;

    View myView;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.register,container,false);

        reg1 = (CardView)myView.findViewById(R.id.register1);
        etemail = (EditText)myView.findViewById(R.id.editText1);
        etpass =  (EditText)myView.findViewById(R.id.editText3);
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(Register.this.getActivity().getApplicationContext());

        reg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Iam in for on data change");
                registerUser();
            }

        });
        return myView;
    }

    void registerUser()
    {
        String email = etemail.getText().toString().trim();
        String pass = etpass.getText().toString().trim();
         if(TextUtils.isEmpty(email))
         {
             Toast.makeText(Register.this.getActivity().getApplicationContext(),"Please Enter email", Toast.LENGTH_LONG).show();
             return;
         }

         if(TextUtils.isEmpty(pass))
         {
             Toast.makeText(Register.this.getActivity().getApplicationContext(),"Please Enter password", Toast.LENGTH_LONG).show();
             return;
         }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            firebaseAuth.createUserWithEmailAndPassword(email,pass)
                    .addOnCompleteListener((Activity)Register.this.getContext(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {


                                Toast.makeText(Register.this.getActivity().getApplicationContext(), "Registered Successfully", Toast.LENGTH_LONG).show();
                                FragmentTransaction fr = getFragmentManager().beginTransaction();
                                fr.replace(R.id.content_frame, new SecondFragment());
                                fr.commit();
                            } else {
                                FirebaseAuthException e = (FirebaseAuthException )task.getException();
                                Toast.makeText(Register.this.getActivity().getApplicationContext(), "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(Register.this.getActivity().getApplicationContext(), "Not registered Succesfully", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }


    }
}
