package vn.phongandfriends.motorwashing;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import vn.phongandfriends.motorwashing.persistence.Account;
import vn.phongandfriends.motorwashing.persistence.AccountManager;

public class ViewProfileActivity extends AppCompatActivity {

    private TextView lblFullname;
    private TextView lblBirthDate;
    private TextView lblPhone;
    private TextView lblEmail;
//    private TextView lblCarType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        AccountManager accountManager = new AccountManager(this);
        Account account = accountManager.findByPhone();

        lblFullname = findViewById(R.id.lblFullname);
        lblBirthDate = findViewById(R.id.lblBirthDate);
        lblPhone = findViewById(R.id.lblPhone);
        lblEmail = findViewById(R.id.lblEmail);
//        lblCarType = findViewById(R.id.lblCarType);

        lblFullname.setText(account.getFullname());
        lblPhone.setText(account.getPhone());
//        lblCarType.setText(account.getCarType());

        if (account.getBirhtDate() == null || account.getBirhtDate().isEmpty()) {
            lblBirthDate.setText(R.string.please_fill_up_information);
            lblBirthDate.setTypeface(lblBirthDate.getTypeface(), Typeface.ITALIC);
        } else {
            lblBirthDate.setText(account.getBirhtDate());
        }
        if (account.getEmail() == null || account.getEmail().isEmpty()) {
            lblEmail.setText(R.string.please_fill_up_information);
            lblEmail.setTypeface(lblEmail.getTypeface(), Typeface.ITALIC);
        } else {
            lblEmail.setText(account.getEmail());
        }
    }

    public void clickToEditProfile(View view) {
        Intent intent = new Intent(this,EditProfileActivity.class);
        startActivity(intent);
    }
}
