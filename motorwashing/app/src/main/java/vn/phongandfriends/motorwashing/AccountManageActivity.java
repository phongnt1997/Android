package vn.phongandfriends.motorwashing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AccountManageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manage);
    }

    public void clickToViewProFile(View view) {
        Intent intent = new Intent(this, ViewProfileActivity.class);
        startActivity(intent);
    }

    public void clickToEditProfile(View view) {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }

    public void clickToChangePass(View view) {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    public void clickToHistory(View view) {
        Intent intent = new Intent(this, HistoryListActivity.class);
        startActivity(intent);
    }

    public void clickToLogout(View view) {
        StatusFragment.currentUser = null;

        finish();
    }
}
