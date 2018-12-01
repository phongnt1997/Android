package vn.phongandfriends.motorwashing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import vn.phongandfriends.motorwashing.persistence.Account;
import vn.phongandfriends.motorwashing.persistence.AccountDBAdapter;

public class LoginActivity extends AppCompatActivity {

    AccountDBAdapter accountDBAdapter;
    EditText edtPhone;
    EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        accountDBAdapter = new AccountDBAdapter(this);
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
    }

    public void clickToRegister(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void clickToLogin(View view) {
        String phone = edtPhone.getText().toString().trim();
        String password = edtPassword.getText().toString();
        if (phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_LONG).show();
            return;
        }
        if (!accountDBAdapter.checkLogin(phone, password)) {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_LONG).show();
            return;
        }
        StatusFragment.currentUser = new Account()
                .setFullname(accountDBAdapter.findFullnameByPhone(phone))
                .setPhone(phone);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accountDBAdapter.close();
    }
}
