package com.example.omkar.navigationdrawer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SecondFragment extends Fragment  {


    @Nullable
    View myView;

    EditText lemail;
    EditText lpass;
    CardView login;
    CardView reg;
    FirebaseAuth firebaseAuth;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.log,container,false);

        login = (CardView)myView.findViewById(R.id.login);

        lemail = (EditText)myView.findViewById(R.id.editTextlog);
        lpass = (EditText)myView.findViewById(R.id.editText2log);

        firebaseAuth = FirebaseAuth.getInstance();

        reg = (CardView) myView.findViewById(R.id.register);


        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String email1 = lemail.getText().toString().trim();
                String pass1 = lpass.getText().toString().trim();

                if (TextUtils.isEmpty(email1)) {
                    Toast.makeText(SecondFragment.this.getActivity().getApplicationContext(), "Please Enter email", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(pass1)) {
                    Toast.makeText(SecondFragment.this.getActivity().getApplicationContext(), "Please Enter password", Toast.LENGTH_LONG).show();
                    return;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    firebaseAuth.signInWithEmailAndPassword(email1, pass1)
                            .addOnCompleteListener((Activity) SecondFragment.this.getContext(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(SecondFragment.this.getActivity().getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();
                                        FragmentTransaction fr = getFragmentManager().beginTransaction();
                                        fr.replace(R.id.content_frame, new FirstFragment());
                                        fr.commit();
                                    }
                                    else
                                    {
                                        Toast.makeText(SecondFragment.this.getActivity().getApplicationContext(), "Login Unsuccessful", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View myview) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.content_frame,new Register());
                fr.commit();
            }
        });
        return myView;
    }

}

