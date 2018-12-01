package vn.phongandfriends.motorwashing;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import vn.phongandfriends.motorwashing.adapter.HistoryAdapter;
import vn.phongandfriends.motorwashing.model.HistoryItemDetail;

public class HistoryListActivity extends AppCompatActivity {

    private static final int CONFIRM_CANCEL_DIALOG = 1;
    private TextView lblDate;
    private Button btnSelectDate;
    private Button btnRemoveSelectDate;
    private Spinner spType;
    private ListView lstHistory;
    private TextView lblNoBook;

    private List<HistoryItemDetail> historyItemDetails = new ArrayList<>(Arrays.asList(
            new HistoryItemDetail(R.drawable.wascarstore01, "121235213", "29/04/2018", "Done"),
            new HistoryItemDetail(R.drawable.wascarstore03, "121223123", "15/05/2018", "Cancel"),
            new HistoryItemDetail(R.drawable.wascarstore02, "798789489", "26/11/2017", "Done"),
            new HistoryItemDetail(R.drawable.wascarstore04, "213121531", "14/10/2018", "Cancel"),
            new HistoryItemDetail(R.drawable.wascarstore05, "421321321", "03/04/2018", "Done"),
            new HistoryItemDetail(R.drawable.wascarstore07, "113421312", "26/11/2017", "Cancel"),
            new HistoryItemDetail(R.drawable.wascarstore09, "125134123", "21/10/2018", "Wait"),
            new HistoryItemDetail(R.drawable.wascarstore01, "325234239", "21/10/2018", "Approved"),
            new HistoryItemDetail(R.drawable.wascarstore02, "463789043", "04/01/2018", "Done"),
            new HistoryItemDetail(R.drawable.wascarstore05, "345367897", "14/10/2018", "Done"),
            new HistoryItemDetail(R.drawable.wascarstore06, "435687565", "12/05/2018", "Cancel"),
            new HistoryItemDetail(R.drawable.wascarstore07, "324333457", "27/02/2018", "Done"),
            new HistoryItemDetail(R.drawable.wascarstore08, "2342343223", "16/09/2018", "Cancel"),
            new HistoryItemDetail(R.drawable.wascarstore01, "9798564099", "14/10/2018", "Done"),
            new HistoryItemDetail(R.drawable.wascarstore03, "2313213213", "24/03/2018", "Done"),
            new HistoryItemDetail(R.drawable.wascarstore05, "2131231312", "14/03/2018", "Done"),
            new HistoryItemDetail(R.drawable.wascarstore09, "1213131231", "02/07/2018", "Done"),
            new HistoryItemDetail(R.drawable.wascarstore05, "7854546456", "25/10/2018", "Denied")
    ));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);

        lblDate = findViewById(R.id.lblDate);
        lblNoBook = findViewById(R.id.lblNoBook);
        btnSelectDate = findViewById(R.id.btnSelectDate);
        btnRemoveSelectDate = findViewById(R.id.btnRemoveSelectDate);
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate();
                btnSelectDate.setVisibility(View.GONE);
            }
        });
        lstHistory = findViewById(R.id.lstHistory);
        lstHistory.setAdapter(new HistoryAdapter(historyItemDetails, HistoryListActivity.this));

        Collections.sort(historyItemDetails);

        spType = findViewById(R.id.spType);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Arrays.asList(
                "All", "Done", "Wait", "Approved", "Denied", "Cancel"
        ));
        spType.setAdapter(typeAdapter);
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                change();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        lblDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate();
            }
        });
    }

    private void setDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = String.format("%02d/%02d/%04d", day, month + 1, year);
                lblDate.setText(date);
                btnRemoveSelectDate.setVisibility(View.VISIBLE);
                lblDate.setVisibility(View.VISIBLE);
                change();
            }
        }, year, month, year);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public void change() {
        List<HistoryItemDetail> historyItemDetailList = new Vector<>();
        String selectedDate = lblDate.getText().toString().trim();
        if (!selectedDate.isEmpty()) {
            selectedDate = selectedDate.replace("Date: ", "");
            for (HistoryItemDetail historyItemDetail : historyItemDetails) {
                if (selectedDate.equals(historyItemDetail.getDate())) {
                    historyItemDetailList.add(historyItemDetail.copy());
                }
            }
        } else {
            for (HistoryItemDetail historyItemDetail : historyItemDetails) {
                historyItemDetailList.add(historyItemDetail.copy());
            }
        }
        List<HistoryItemDetail> result = new ArrayList<>();
        if (!spType.getSelectedItem().toString().equals("All")) {
            for (HistoryItemDetail historyItemDetail : historyItemDetailList) {
                if (historyItemDetail.getStatus().equals(spType.getSelectedItem().toString())) {
                    result.add(historyItemDetail.copy());
                }
            }
        } else {
            for (HistoryItemDetail historyItemDetail : historyItemDetailList) {
                result.add(historyItemDetail.copy());
            }
        }
        ((HistoryAdapter) lstHistory.getAdapter()).setHistoryItemDetails(result);
        ((HistoryAdapter) lstHistory.getAdapter()).notifyDataSetChanged();

        if (result.size() > 0) {
            lstHistory.setVisibility(View.VISIBLE);
            lblNoBook.setVisibility(View.GONE);
        } else {
            lstHistory.setVisibility(View.GONE);
            lblNoBook.setVisibility(View.VISIBLE);
        }

    }

    public void clickToRemoveSelectDate(View view) {
        btnSelectDate.setVisibility(View.VISIBLE);
        lblDate.setText("");
        lblDate.setVisibility(View.GONE);
        btnRemoveSelectDate.setVisibility(View.GONE);
        change();
    }

    public List<HistoryItemDetail> getHistoryItemDetails() {
        return historyItemDetails;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case CONFIRM_CANCEL_DIALOG:

                break;
        }
        return null;
    }
}
