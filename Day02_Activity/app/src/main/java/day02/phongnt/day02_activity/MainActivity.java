package day02.phongnt.day02_activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
private static final int REQUEST_INPUT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickToRegister(View view) {
        EditText edtUsername = findViewById(R.id.txtUsername);
        String username = edtUsername.getText().toString();
        Intent intent = new Intent(this, InputActivity.class);
        intent.putExtra("username", username);
        startActivityForResult(intent, REQUEST_INPUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_INPUT) {
            if(resultCode == RESULT_OK) {
                String username = data.getStringExtra("username");
                String result = data.getStringExtra("result");
                TextView txtResult = findViewById(R.id.txtResult);
                txtResult.setText(result + "Username = " + username);
            }
        }
    }
}
