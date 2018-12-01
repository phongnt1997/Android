package com.phongnt.day10_pictureandmenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnContext = findViewById(R.id.btnContext);
        btnContext.setOnCreateContextMenuListener(this);
    }

    public void clickToGallery(View view) {
        Intent intent = new Intent(this, GalleryActivity.class);
        startActivity(intent);
    }

    private void createMenu(Menu menu) {
        MenuItem menuItem1 = menu.add(0,0,0, "Item 1");
        MenuItem menuItem2 = menu.add(0,1,1, "Item 2");
        MenuItem menuItem3 = menu.add(0,2,2, "Item 3");
        MenuItem menuItem4 = menu.add(0,3,3, "Item 4");
    }

    private boolean menuChoice(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Toast.makeText(this, "Item 1 is clicked", Toast.LENGTH_SHORT).show();
                return true;
            case 1:
                Toast.makeText(this, "Item 2 is clicked", Toast.LENGTH_SHORT).show();
                return true;
            case 2:
                Toast.makeText(this, "Item 3 is clicked", Toast.LENGTH_SHORT).show();
                return true;
            case 3:
                Toast.makeText(this, "Item 4 is clicked", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        createMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return menuChoice(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        createMenu(menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return menuChoice(item);
    }
}
