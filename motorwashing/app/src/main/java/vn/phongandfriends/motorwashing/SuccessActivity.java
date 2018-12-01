package vn.phongandfriends.motorwashing;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class SuccessActivity extends AppCompatActivity {

    private static final int MAP_REQUEST_CODE = 1;
    private boolean isCurrentSlot = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        TextView lblTimeAndDate = findViewById(R.id.lblTimeAndDate);
        TextView lblAdress = findViewById(R.id.lblAddress);
        TextView lblOpenHours = findViewById(R.id.lblOpenHours);
        TextView lblNameStore = findViewById(R.id.tvNameWasher);
        TextView lblCombo = findViewById(R.id.lblCombo);
        TextView lblComboPrice = findViewById(R.id.lblComboPrice);
        TextView lblTypeCar = findViewById(R.id.lblTypeCar);
        TextView txtPhone = findViewById(R.id.txtPhone);
        txtPhone.setPaintFlags(txtPhone.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("BOOKINFO");
        lblTimeAndDate.setText(bundle.getString("BOOKTIME"));
        lblAdress.setText(bundle.getString("ADDRESS"));
        lblOpenHours.setText(bundle.getString("OPENTIME"));
        lblNameStore.setText(bundle.getString("NAME"));
        lblCombo.setText(bundle.getString("COMBO"));
        lblComboPrice.setText(bundle.getString("COMBOPRICE"));
        lblTypeCar.setText(bundle.getString("CARTYPE"));
        isCurrentSlot = bundle.getBoolean("ISCURRENTSLOT");
    }

    public void clickToBooking(View view) {
        if (isCurrentSlot) {
            Uri gmmIntentUri = Uri.parse("google.navigation:q=Rữa+Xe+Máy+Thay+Nhớt");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivityForResult(mapIntent, MAP_REQUEST_CODE);
            return;
        }
        goToActivity(BookSuccessActivity.class);
    }

    public void clickToCancel(View view) {
        finish();
    }

    public void clickToCall(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:0794244189"));
        startActivity(intent);
    }

    private void goToActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        TextView lblNameStore = findViewById(R.id.tvNameWasher);
        intent.putExtra("STORENAME", lblNameStore.getText().toString().trim());
        if (clazz.equals(SelectWashCarStoreActivity.class)) {
            intent.putExtra("FEEDBACK", "Has Feedback");
        }
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MAP_REQUEST_CODE) {
            goToActivity(SelectWashCarStoreActivity.class);
            finish();
        }
    }
}
