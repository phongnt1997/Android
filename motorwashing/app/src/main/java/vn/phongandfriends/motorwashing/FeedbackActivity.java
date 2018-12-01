package vn.phongandfriends.motorwashing;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        RatingBar mRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        String storeName = getIntent().getStringExtra("STORENAME");
        TextView txtWashCarStoreName = findViewById(R.id.txtWashCarStoreName);
        txtWashCarStoreName.setText(storeName);
        final TextView mRatingScale = (TextView) findViewById(R.id.tvRatingScale);
        final EditText mFeedback = (EditText) findViewById(R.id.etFeedback);
        Button mSendFeedback = (Button) findViewById(R.id.btnSubmit);
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mRatingScale.setText(String.valueOf(v));
                switch ((int) ratingBar.getRating()) {
                    case 1:
                        mRatingScale.setText("Very bad");
                        break;
                    case 2:
                        mRatingScale.setText("Need some improvement");
                        break;
                    case 3:
                        mRatingScale.setText("Good");
                        break;
                    case 4:
                        mRatingScale.setText("Great");
                        break;
                    case 5:
                        mRatingScale.setText("Awesome. I love it");
                        break;
                    default:
                        mRatingScale.setText("");
                }
            }
        });
        mSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFeedback.getText().toString().isEmpty()) {
                    Toast.makeText(FeedbackActivity.this, "Please fill in feedback text box", Toast.LENGTH_LONG).show();
                } else {
                    finish();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(FeedbackActivity.this,SelectWashCarStoreActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        super.onDestroy();
    }
}
