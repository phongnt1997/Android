package vn.phoneandhisfriends.washcar_store;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DetailCustomerActivity extends AppCompatActivity {
    ImageView avt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_customer);

        avt = findViewById(R.id.imgAvt);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avt10);
        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        drawable.setCircular(true);
        avt.setImageDrawable(drawable);


    }

    public void clickToBack(View view) {
        finish();
    }
}
