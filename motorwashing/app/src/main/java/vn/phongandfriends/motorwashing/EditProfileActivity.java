package vn.phongandfriends.motorwashing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import vn.phongandfriends.motorwashing.persistence.Account;
import vn.phongandfriends.motorwashing.persistence.AccountDBAdapter;
import vn.phongandfriends.motorwashing.persistence.AccountManager;

public class EditProfileActivity extends AppCompatActivity {

    private static final int UPDATE_SUCCESSFULLY = 1;
    private AccountDBAdapter accountDBAdapter;
    private EditText edtFullname;
    private EditText edtBirthDate;
    private EditText edtPhone;
    private EditText edtEmail;
//    private Spinner spCarType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        edtFullname = findViewById(R.id.edtFullname);
        edtBirthDate = findViewById(R.id.edtBirthDate);
        edtPhone = findViewById(R.id.edtPhone);
        edtEmail = findViewById(R.id.edtEmail);
//        spCarType = findViewById(R.id.spCarType);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,
//                new String[] {"4 chỗ", "7 chỗ", "16 chỗ"});
        AccountManager accountManager = new AccountManager(getApplicationContext());
        Account account = accountManager.findByPhone();

        edtFullname.setText(account.getFullname());
        edtPhone.setText(account.getPhone());

        if (account.getBirhtDate() == null || account.getBirhtDate().isEmpty()) {
            edtBirthDate.setHint(R.string.please_fill_up_information);
        } else {
            edtBirthDate.setText(account.getBirhtDate());
        }
        if (account.getEmail() == null || account.getEmail().isEmpty()) {
            edtEmail.setHint(R.string.please_fill_up_information);
        } else {
            edtEmail.setText(account.getEmail());
        }

    }

    @Override
    protected void onDestroy() {
        if (accountDBAdapter != null) {
            accountDBAdapter.close();
        }
        super.onDestroy();
    }

    public void clickToUpdate(View view) {
        if (accountDBAdapter == null) accountDBAdapter = new AccountDBAdapter(this);
        String phone = edtPhone.getText().toString().trim();
        String fullname = edtFullname.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String birthDate = edtBirthDate.getText().toString().trim();
        // Car type
        // spCarType.getSelectedItem().toString()
        boolean result = accountDBAdapter.updateAccount(phone, fullname, email, birthDate, null );
        if (result) {
            showDialog(UPDATE_SUCCESSFULLY);
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case UPDATE_SUCCESSFULLY:
                return new AlertDialog.Builder(this)
                        .setTitle(R.string.title_update_account_successfully)
                        .setMessage(R.string.message_update_account_successfully)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create();
        }
        return null;
    }
}
