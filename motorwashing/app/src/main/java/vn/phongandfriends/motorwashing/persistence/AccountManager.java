package vn.phongandfriends.motorwashing.persistence;

import android.content.Context;
import android.database.Cursor;

import java.io.Serializable;

import vn.phongandfriends.motorwashing.StatusFragment;

public class AccountManager implements Serializable {

    private AccountDBAdapter accountDBAdapter;
    private Context context;

    public AccountManager(Context context) {
        this.context = context;
    }

    public Account findByPhone() {
        if (accountDBAdapter == null) accountDBAdapter = new AccountDBAdapter(context);
        Cursor cursor = accountDBAdapter.findByPhone(StatusFragment.currentUser.getPhone());
        if (cursor.moveToFirst()) {
            int nPhone = cursor.getColumnIndex("phone");
            int nFullname = cursor.getColumnIndex("fullname");
            int nEmail = cursor.getColumnIndex("email");
            int nBirthDate = cursor.getColumnIndex("birthdate");
            int nCarType = cursor.getColumnIndex("cartype");

            return new Account()
                    .setPhone(cursor.getString(nPhone))
                    .setFullname(cursor.getString(nFullname))
                    .setEmail(cursor.getString(nEmail))
                    .setBirhtDate(cursor.getString(nBirthDate))
                    .setCarType(cursor.getString(nCarType));
        }
        return null;
    }
}
