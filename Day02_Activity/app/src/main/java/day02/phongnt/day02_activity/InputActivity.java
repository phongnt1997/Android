package day02.phongnt.day02_activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class InputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
    }

    public void clickToDone(View view) {
        Intent intent = this.getIntent();
        String username = intent.getStringExtra("username");
        EditText edtAge =findViewById(R.id.txtAge);
        String result = "Username: " + username + " - Age: " + edtAge.getText().toString();
        intent.putExtra("result", result);
        this.setResult(RESULT_OK, intent);
        finish();
    }
}
