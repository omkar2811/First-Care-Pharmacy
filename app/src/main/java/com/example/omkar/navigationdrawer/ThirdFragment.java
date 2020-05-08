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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ThirdFragment extends Fragment{
    @Nullable
    View myView;

    EditText semail;
    EditText spass;

    FirebaseAuth firebaseAuth;

    CardView reg;
    CardView log;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.storelog,container,false);

        reg = (CardView) myView.findViewById(R.id.register);

        log = (CardView) myView.findViewById(R.id.login);


        semail = (EditText)myView.findViewById(R.id.editTextslog);
        spass = (EditText)myView.findViewById(R.id.editText2slog);

        firebaseAuth = FirebaseAuth.getInstance();

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email1 = semail.getText().toString().trim();
                String pass1 = spass.getText().toString().trim();

                if (TextUtils.isEmpty(email1)) {
                    Toast.makeText(ThirdFragment.this.getActivity().getApplicationContext(), "Please Enter email", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(pass1)) {
                    Toast.makeText(ThirdFragment.this.getActivity().getApplicationContext(), "Please Enter password", Toast.LENGTH_LONG).show();
                    return;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    firebaseAuth.signInWithEmailAndPassword(email1, pass1)
                            .addOnCompleteListener((Activity) ThirdFragment.this.getContext(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(ThirdFragment.this.getActivity().getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();
                                        FragmentTransaction fr = getFragmentManager().beginTransaction();
                                        fr.replace(R.id.content_frame, new MedicineAdd());
                                        fr.commit();
                                    }
                                    else
                                    {
                                        Toast.makeText(ThirdFragment.this.getActivity().getApplicationContext(), "Login Unsuccessful", Toast.LENGTH_LONG).show();
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
                fr.replace(R.id.content_frame,new StoreReg());
                fr.commit();
            }
        });
        return myView;
    }
}
