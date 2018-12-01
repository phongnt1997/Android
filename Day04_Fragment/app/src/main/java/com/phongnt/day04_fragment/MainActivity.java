package com.phongnt.day04_fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickAdd(View view) {
        EditText edtNum1 = findViewById(R.id.txtNum1);
        EditText edtNum2 = findViewById(R.id.txtNum2);
        TextView txtResult = findViewById(R.id.txtResult);
        int result = Integer.parseInt(edtNum1.getText().toString())
                + Integer.parseInt(edtNum2.getText().toString());
        txtResult.setText("Result = " + result);
    }
}
