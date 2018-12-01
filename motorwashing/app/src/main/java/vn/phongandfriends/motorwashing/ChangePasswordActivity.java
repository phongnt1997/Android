package vn.phongandfriends.motorwashing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import vn.phongandfriends.motorwashing.persistence.Account;
import vn.phongandfriends.motorwashing.persistence.AccountDBAdapter;

public class ChangePasswordActivity extends AppCompatActivity {

    private AccountDBAdapter accountDBAdapter;
    private EditText edtPassword;
    private EditText edtNewPassword;
    private EditText edtConfirm;
    private TextView lblMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        edtPassword = findViewById(R.id.edtPassword);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirm = findViewById(R.id.edtConfirm);
        lblMessage = findViewById(R.id.lblMessage);
    }

    public void clickToSave(View view) {
        if (!edtNewPassword.getText().toString().equals(edtConfirm.getText().toString())) {
            lblMessage.setText(R.string.confirm_must_match_password);
            return;
        }
        if (accountDBAdapter == null) accountDBAdapter = new AccountDBAdapter(this);
        Account account = StatusFragment.currentUser;
        if (!accountDBAdapter.checkLogin(account.getPhone(), edtPassword.getText().toString())) {
            lblMessage.setText(R.string.password_incorrect);
            return;
        }
        boolean result = accountDBAdapter.updatePassword(account.getPhone(), edtNewPassword.getText().toString());
        if (result) {
            lblMessage.setText(R.string.update_password_succesfully);
            lblMessage.setTextColor(Color.GREEN);
            edtPassword.setText("");
            edtNewPassword.setText("");
            edtConfirm.setText("");
            edtPassword.requestFocus();
        } else {
            lblMessage.setText(R.string.update_occurs_error);
            lblMessage.setTextColor(Color.RED);
        }
    }

    @Override
    protected void onDestroy() {
        if (accountDBAdapter != null) accountDBAdapter.close();
        super.onDestroy();
    }
}
