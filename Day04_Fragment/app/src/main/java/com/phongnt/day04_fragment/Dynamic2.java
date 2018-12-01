package com.phongnt.day04_fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Dynamic2 extends Fragment {


    public Dynamic2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dynamic2, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Button btnSub = getActivity().findViewById(R.id.btnSub);
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtNum1 = getActivity().findViewById(R.id.txtNum1);
                EditText edtNum2 = getActivity().findViewById(R.id.txtNum2);
                TextView txtResult = getActivity().findViewById(R.id.txtResult);
                int result = Integer.parseInt(edtNum1.getText().toString())
                        - Integer.parseInt(edtNum2.getText().toString());
                txtResult.setText("Result = " + result);
            }
        });
    }
}
