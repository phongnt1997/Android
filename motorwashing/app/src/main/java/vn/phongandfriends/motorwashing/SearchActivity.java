package vn.phongandfriends.motorwashing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;

import vn.phongandfriends.motorwashing.adapter.SelectWashCarStoreAdapter;
import vn.phongandfriends.motorwashing.model.WashCarStore;

public class SearchActivity extends AppCompatActivity {
    private EditText edtSearch;
    private RecyclerView lstStore;
    ArrayList<WashCarStore> washCarStoreList;
    SelectWashCarStoreAdapter selectWashCarStoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        edtSearch = findViewById(R.id.edtSearch);
        lstStore = findViewById(R.id.lstStore);
        Intent intent = getIntent();
        washCarStoreList = intent.getExtras().getParcelableArrayList("listStore");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        lstStore.setLayoutManager(layoutManager);


        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    selectWashCarStoreAdapter = new SelectWashCarStoreAdapter(findStore(s.toString()), SearchActivity.this);
                    lstStore.setAdapter(selectWashCarStoreAdapter);
                } else {
                    selectWashCarStoreAdapter = new SelectWashCarStoreAdapter();
                    lstStore.setAdapter(selectWashCarStoreAdapter);
                }
            }
        });
    }

    private ArrayList<WashCarStore> findStore(String s) {
        ArrayList<WashCarStore> result = new ArrayList<>();
        for (WashCarStore sto : washCarStoreList) {
            if (sto.getName().toUpperCase().contains(s.toUpperCase()) || sto.getAddress().toUpperCase().contains(s.toUpperCase())) {
                result.add(sto);
            }
        }
        return result;
    }

}
