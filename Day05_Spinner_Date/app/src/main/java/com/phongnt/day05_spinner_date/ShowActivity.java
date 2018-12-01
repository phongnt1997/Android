package com.phongnt.day05_spinner_date;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        TextView txtBirthday = findViewById(R.id.txtBirthday);
        TextView txtNationality = findViewById(R.id.txtNationality);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getBundleExtra("INFO");
        txtBirthday.setText(bundle.getString("birthDay"));
        txtNationality.setText(bundle.getString("nationality"));
    }
}
