package vn.phongandfriends.motorwashing;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import vn.phongandfriends.motorwashing.adapter.DialogComboPickerAdapter;
import vn.phongandfriends.motorwashing.adapter.DialogTimePickerAdapter;
import vn.phongandfriends.motorwashing.model.Combo;

class CarType {
    private String type;
    private String price;

    public CarType() {
    }

    public CarType(String type, String price) {
        this.type = type;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public CarType setType(String type) {
        this.type = type;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public CarType setPrice(String price) {
        this.price = price;
        return this;
    }

    @Override
    public String toString() {
        return type;
    }
}

public class DetailStoreActivity extends AppCompatActivity {

    private static final int MAX_AVAILABLE = 1000 * 60 * 60 * 24 * 15;
    private static final double EXTRA_CAR_TYPE = 20.0;
    private String washPrice;
    TextView tvDate;
    TextView tvTime;
    TextView edtCombo;
    TextView tvName;
    TextView tvAddress;
    TextView tvPrice;
    Spinner spCarType;
    ImageView imgRouteTwoPlace;
    CarouselView carouselView;
    DialogTimePickerAdapter dialogTimePickerAdapter;
    DialogComboPickerAdapter dialogComboPickerAdapter;
    Long beginTime;
    Long endTime;
    int[] sampleImages = {
            R.drawable.wascarstore01, R.drawable.wascarstore02, R.drawable.wascarstore03,
            R.drawable.wascarstore04, R.drawable.wascarstore05, R.drawable.wascarstore06,
            R.drawable.wascarstore07, R.drawable.wascarstore08, R.drawable.wascarstore09
    };

    TextView txtWorkingHours;
    boolean isChangedTime = false;
    private String currentSlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_store);
        Long approximateStartTime = 8L;
        Long approximateEndTime = 17L;
        beginTime = approximateStartTime + (Math.random() > 0.5 ? 1L : 0);
        endTime = approximateEndTime + Math.round(Math.random() * 3);
        txtWorkingHours = findViewById(R.id.txtWorkingHours);
        txtWorkingHours.setText(String.format("Working Hours: %02dh-%02dh", beginTime, endTime));
        spCarType = findViewById(R.id.spCarType);
        edtCombo = findViewById(R.id.edtChoseCombo);
        tvName = findViewById(R.id.tvNameWasher);
        Intent intent = getIntent();
        tvName.setText(intent.getStringExtra("name"));
        tvAddress = findViewById(R.id.tvAddressWasher);
        tvAddress.setText(intent.getStringExtra("address"));
        tvPrice = findViewById(R.id.tvPrice);
//        tvPrice.setText(intent.getStringExtra("price"));
        washPrice = tvPrice.getText().toString().trim();
        imgRouteTwoPlace = findViewById(R.id.imgRouteTwoPlace);
        carouselView = findViewById(R.id.carsouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        });
        edtCombo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(DetailStoreActivity.this);
                dialog.setContentView(R.layout.dialog_combo_selector);
                final ListView lstCombo = dialog.findViewById(R.id.lstComboes);
                try {
                    dialogComboPickerAdapter = dialogComboPickerAdapter != null ?
                            dialogComboPickerAdapter : new DialogComboPickerAdapter(DetailStoreActivity.this);
                    List<Combo> combos = dialogComboPickerAdapter.getComboes();
                    if (combos != null) {
                        for (Combo combo : combos) {
                            if (combo.getPrice() != null && combo.getPrice().contains(" VND")) {
                                String comboPrice = combo.getPrice().replace(" VND", "");
                                if (combo.getBasePrice() == null) {
                                    combo.setBasePrice(Double.parseDouble(comboPrice));
                                }
                                String newPrice = String.format("%.3f", combo.getBasePrice() + EXTRA_CAR_TYPE * spCarType.getSelectedItemPosition());
                                combo.setPrice(newPrice.replace(",", ".") + " VND");
                            }
                        }
                        dialogComboPickerAdapter.setComboes(combos);
                    }
                    lstCombo.setAdapter(dialogComboPickerAdapter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                lstCombo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Combo combo = (Combo) lstCombo.getAdapter().getItem(i);
                        if (!combo.getCombo().equals("Combo is not avaiable")) {
                            edtCombo.setText(combo.getCombo());
                            tvPrice.setText(combo.getPrice());
                        }

                        if (combo.getCombo().equals(DialogComboPickerAdapter.MESSAGE_UNSELECTED_COMBO)) {
                            edtCombo.setText("");
                            tvPrice.setText(washPrice);
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        SimpleDateFormat timeSimpleDateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date(System.currentTimeMillis());
        int nextTime = 15;
        Date nextHour = new Date(System.currentTimeMillis() + 1000 * 60 * nextTime);
        while (nextHour.getMinutes() % 5 != 0) {
            nextHour = new Date(System.currentTimeMillis() + 1000 * 60 * ++nextTime);
        }
        tvTime = findViewById(R.id.editTextTime);
        tvTime.setText(timeSimpleDateFormat.format(nextHour));
        currentSlot = tvTime.getText().toString();
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime();
            }
        });
        tvDate = findViewById(R.id.editTextDate);
        tvDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();

            }
        });

        String price = intent.getStringExtra("price");
        String[] arr = price.split(", ");
        List<CarType> carTypes = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            String[] s = arr[i].split(":");
            CarType carType = new CarType(s[0].trim(), s[1].trim());
            carTypes.add(carType);
        }

        ArrayAdapter<CarType> carTypeArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,
                carTypes);

        spCarType.setAdapter(carTypeArrayAdapter);
        spCarType.setSelection(0);
        washPrice = ((CarType) spCarType.getSelectedItem()).getPrice();
        spCarType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tvPrice.setText(((CarType) spCarType.getAdapter().getItem(i)).getPrice());
                washPrice = tvPrice.getText().toString();
                edtCombo.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
    }

    private void setTime() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_time_selector);
        GridView gvTimeTable = dialog.findViewById(R.id.gvTimeTable);
        dialogTimePickerAdapter =
                dialogTimePickerAdapter == null ? new DialogTimePickerAdapter(gvTimeTable, dialog, this, beginTime, endTime)
                        : dialogTimePickerAdapter.setDialog(dialog);
        gvTimeTable.setAdapter(dialogTimePickerAdapter);
        dialog.show();
    }

    private void setDate() {
        final Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        int mounth = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int date) {
                calendar.set(year, month, date);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                tvDate.setText(simpleDateFormat.format(calendar.getTime()));
                if (!isChangedTime) {
                    isChangedTime = true;
                }
            }
        }, year, mounth, date);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + MAX_AVAILABLE);
        datePickerDialog.show();
    }

    public void clickToDone(View view) {
        String date = tvDate.getText().toString().trim();
        String time = tvTime.getText().toString().trim();
        if (!date.isEmpty() && !time.isEmpty()) {
            String[] arr = time.split("-");
            String bookTime = arr.length > 0 ? arr[0] : null;
            if (bookTime != null) {
                if (StatusFragment.currentUser == null) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                String address = ((TextView) findViewById(R.id.tvAddressWasher)).getText().toString();
                String name = ((TextView) findViewById(R.id.tvNameWasher)).getText().toString();
                String price = tvPrice.getText().toString();
                String combo = edtCombo.getText().toString();
                String carType = spCarType.getSelectedItem().toString();
                Bundle bundle = new Bundle();
                bundle.putString("BOOKTIME", bookTime + " ngày " + date);
                bundle.putString("ADDRESS", address);
                bundle.putString("OPENTIME", txtWorkingHours.getText().toString());
                bundle.putString("NAME", name);
                bundle.putString("COMBOPRICE", price);
                bundle.putString("CARTYPE", carType);
                if (!isChangedTime) {
                    isChangedTime = !tvTime.getText().toString().trim().equals(currentSlot.trim());
                }
                bundle.putBoolean("ISCURRENTSLOT", !isChangedTime);
                if (edtCombo.getText().toString().trim().isEmpty()) {
                    combo = "No combo";
                }
                bundle.putString("COMBO", combo);
                Intent intent = new Intent(this, SuccessActivity.class);
                intent.putExtra("BOOKINFO", bundle);
                startActivity(intent);
                return;
            }
        }
        Toast.makeText(this, "Please fill in enough information to book", Toast.LENGTH_LONG).show();
    }

    public Long getBeginTime() {
        return beginTime;
    }

    public DetailStoreActivity setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
        return this;
    }

    public Long getEndTime() {
        return endTime;
    }

    public DetailStoreActivity setEndTime(Long endTime) {
        this.endTime = endTime;
        return this;
    }

    public void clickToViewMap(View view) {
        if (carouselView.getVisibility() == View.VISIBLE) {
            carouselView.setVisibility(View.GONE);
            imgRouteTwoPlace.setVisibility(View.VISIBLE);
        } else {
            carouselView.setVisibility(View.VISIBLE);
            imgRouteTwoPlace.setVisibility(View.GONE);
        }
    }
}
