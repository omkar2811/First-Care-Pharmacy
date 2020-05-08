package com.example.omkar.navigationdrawer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FourthFragment extends Fragment {

    Button mselectImage;

    private StorageReference mstorage;
    View  myView;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.third_layout, container, false);

        mstorage = FirebaseStorage.getInstance().getReference();
        return myView;
    }
}
