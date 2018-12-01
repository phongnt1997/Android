package vn.phongandfriends.motorwashing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import vn.phongandfriends.motorwashing.persistence.AccountDBAdapter;

public class SignUpActivity extends AppCompatActivity {
    AccountDBAdapter accountDBAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Button btnRegister = findViewById(R.id.btnRegister);
        accountDBAdapter = new AccountDBAdapter(getApplicationContext());
        final EditText edtPhone = findViewById(R.id.edtPhone);
        final EditText edtPassword = findViewById(R.id.edtPassword);
        final EditText edtConfirm = findViewById(R.id.edtConfirm);
        final EditText edtFullname = findViewById(R.id.edtFullname);
        final MaterialBetterSpinner spCarType = findViewById(R.id.spCarType);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,
                new String[] {"4 seats", "7 seats", "16 seats", "32 seats"});
        spCarType.setAdapter(arrayAdapter);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = edtPhone.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String confirm = edtConfirm.getText().toString().trim();
                String fullname = edtFullname.getText().toString().trim();
                if (!confirm.equals(password)) {
                    Toast.makeText(SignUpActivity.this, "Confirm is not match password", Toast.LENGTH_LONG).show();
                    return;
                }
                if (accountDBAdapter.isExisted(phone)) {
                    Toast.makeText(SignUpActivity.this, phone + " has existed", Toast.LENGTH_LONG).show();
                    return;
                }
                if (spCarType.getText().toString().isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please select car type", Toast.LENGTH_LONG).show();
                    return;
                }
                accountDBAdapter.addAccount(phone, password, fullname, null, null, spCarType.getText().toString());
                Toast.makeText(SignUpActivity.this, "Sign up successfully", Toast.LENGTH_LONG).show();
                finish();
            }
        });
}

    public void clickToLogin(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accountDBAdapter.close();
    }
}
