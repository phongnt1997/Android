package vn.phongandfriends.motorwashing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class BookSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_success);
        final String storeName = getIntent().getStringExtra("STORENAME");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    Intent intent = new Intent(BookSuccessActivity.this, SelectWashCarStoreActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("FEEDBACK", "Has Feedback");
                    intent.putExtra("STORENAME", storeName);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    Log.d("Interrupt: " , e.getMessage());
                }
            }
        });
        thread.start();
    }
}
