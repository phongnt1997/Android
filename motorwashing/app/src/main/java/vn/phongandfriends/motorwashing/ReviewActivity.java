package vn.phongandfriends.motorwashing;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RatingBar;

public class ReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.rating);
        LayerDrawable stars = (LayerDrawable)ratingBar.getProgressDrawable();
        stars.setColorFilter(Color.YELLOW,PorterDuff.Mode.SRC_ATOP);
        RatingBar ratingBar1 = (RatingBar) findViewById(R.id.rating1);
        LayerDrawable stars1 = (LayerDrawable)ratingBar1.getProgressDrawable();
        stars1.setColorFilter(Color.YELLOW,PorterDuff.Mode.SRC_ATOP);
        RatingBar ratingBar2 = (RatingBar) findViewById(R.id.rating2);
        LayerDrawable stars2 = (LayerDrawable)ratingBar2.getProgressDrawable();
        stars2.setColorFilter(Color.YELLOW,PorterDuff.Mode.SRC_ATOP);
        RatingBar ratingBar3 = (RatingBar) findViewById(R.id.rating3);
        LayerDrawable stars3 = (LayerDrawable)ratingBar3.getProgressDrawable();
        stars3.setColorFilter(Color.YELLOW,PorterDuff.Mode.SRC_ATOP);
        RatingBar ratingBar4 = (RatingBar) findViewById(R.id.rating4);
        LayerDrawable stars4 = (LayerDrawable)ratingBar4.getProgressDrawable();
        stars4.setColorFilter(Color.YELLOW,PorterDuff.Mode.SRC_ATOP);
        RatingBar ratingBar5 = (RatingBar) findViewById(R.id.rating5);
        LayerDrawable stars5 = (LayerDrawable)ratingBar5.getProgressDrawable();
        stars5.setColorFilter(Color.YELLOW,PorterDuff.Mode.SRC_ATOP);
        RatingBar ratingBar6 = (RatingBar) findViewById(R.id.rating6);
        LayerDrawable stars6 = (LayerDrawable)ratingBar6.getProgressDrawable();
        stars6.setColorFilter(Color.YELLOW,PorterDuff.Mode.SRC_ATOP);
    }

    public void clickToClose(View view) {
        finish();
    }
//
//    public void clickToFeedBack(View view) {
//        Intent intent = new Intent(this,FeedbackActivity.class);
//        startActivity(intent);
//    }
}
