package com.example.omkar.navigationdrawer;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class MedicineDetail extends Fragment {
    @Nullable
    View myView;
    TextView med;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.splash, container, false);

        med =(TextView)myView.findViewById(R.id.med);
        SharedPreferences spref = getActivity().getSharedPreferences("myfile2", Context.MODE_PRIVATE);
        String text = "vicks";
        String med1 = spref.getString("medicine", text);
        med.setText(med1);
        return myView;
    }
}
