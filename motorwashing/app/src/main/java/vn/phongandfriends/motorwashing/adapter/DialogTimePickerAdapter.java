package vn.phongandfriends.motorwashing.adapter;

import android.app.Dialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import vn.phongandfriends.motorwashing.DetailStoreActivity;
import vn.phongandfriends.motorwashing.R;

public class DialogTimePickerAdapter extends BaseAdapter {

    private static final String STATUS_FULL = "Full";
    private static final String STATUS_CROWD = "Crowd";
    private static final String STATUS_AVAILABLE = "Available";
    private static final String STATUS_FREE = "Free";
    private static final String STATUS_BOOKED = "Selected";
    private int slot = 5 + new Random().nextInt(10);

    private class BookTime {
        private Long startTime;
        private Long endTime;
        private Boolean selected;
        private int currentSlot;

        public BookTime(Long startTime, Long endTime, Boolean selected, int currentSlot) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.selected = selected;
            this.currentSlot = currentSlot;
        }

        public Long getStartTime() {
            return startTime;
        }

        public BookTime setStartTime(Long startTime) {
            this.startTime = startTime;
            return this;
        }

        public Long getEndTime() {
            return endTime;
        }

        public BookTime setEndTime(Long endTime) {
            this.endTime = endTime;
            return this;
        }

        public Boolean getSelected() {
            return selected;
        }

        public BookTime setSelected(Boolean selected) {
            this.selected = selected;
            return this;
        }
    }

    private List<BookTime> bookTimes;
    private GridView gvTimeTable;
    private DetailStoreActivity detailStoreActivity;
    private Dialog dialog;
    private BookTime currentSelected;

    public DialogTimePickerAdapter(GridView gvTimeTable, Dialog dialog, DetailStoreActivity detailStoreActivity, Long beginTime, Long endTime) {
        this.gvTimeTable = gvTimeTable;
        this.detailStoreActivity = detailStoreActivity;
        this.dialog = dialog;
        gvTimeTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });


        detailStoreActivity.setBeginTime(beginTime);
        detailStoreActivity.setEndTime(endTime);
        Random random = new Random();
        int hour = new Date(System.currentTimeMillis()).getHours() + 1;
//        for (int i = beginTime.intValue(); i < endTime; i++) {
        for (int i = hour; i < endTime; i++) {
            int j = 0;
            while (j < 3) {
                int currentSlot = 1 + random.nextInt(slot);
//            int currentSlot = slot - 1;
//                BookTime bookTime = new BookTime((long) i, (long) (i + 1), false, currentSlot);
                BookTime bookTime = new BookTime((long) i, (long) (j), false, currentSlot);
                (this.bookTimes = this.bookTimes == null ? new ArrayList<BookTime>() : this.bookTimes).add(bookTime);
                j++;
            }
        }
        bookTimes = bookTimes == null ? new ArrayList<>() : bookTimes;
        if (!bookTimes.isEmpty()) {
            int n = random.nextInt(bookTimes.size() / 3);
            for (int i = 0; i < n; i++) {
                this.bookTimes.get(random.nextInt(bookTimes.size())).currentSlot = slot;
            }
        }
    }

    @Override
    public int getCount() {
        return bookTimes.size();
    }

    @Override
    public Object getItem(int i) {
        return bookTimes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return bookTimes.indexOf(getItem(i));
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            view = layoutInflater.inflate(R.layout.item_time_picker, viewGroup, false);
        }

        final BookTime bookTime = (BookTime) getItem(i);
        final Button btnSelected = view.findViewById(R.id.btnSelected);
//        TextView txtSlot = view.findViewById(R.id.txtSlot);
//        txtSlot.setText("Slot: " + bookTime.currentSlot + "/" + slot);
        if (bookTime.currentSlot * 1.0 / slot == 1) {
            btnSelected.setText(STATUS_FULL);
            btnSelected.setBackgroundColor(Color.parseColor("#B6B6B6"));
//        } else if (bookTime.currentSlot * 1.0 / slot > 0.8) {
//            btnSelected.setText(STATUS_CROWD);
//            btnSelected.setBackgroundColor(Color.parseColor("#67B459"));
//        } else if (bookTime.currentSlot * 1.0 / slot > 0.4) {
//            btnSelected.setText(STATUS_AVAILABLE);
//            btnSelected.setBackgroundColor(Color.parseColor("#67B459"));
        } else {
            btnSelected.setText(STATUS_FREE);
            btnSelected.setBackgroundColor(Color.parseColor("#67B459"));
        }
        TextView txtTimeRange = view.findViewById(R.id.txtTimeRange);

//        txtTimeRange.setText(createRangeTime(bookTime.startTime, bookTime.endTime, i));
        txtTimeRange.setText(createRangeTime(bookTime.startTime, new Long(i), i));
        if (bookTime == currentSelected) {
            btnSelected.setBackgroundColor(Color.parseColor("#37B0DD"));
            btnSelected.setText(STATUS_BOOKED);
        }
        btnSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!btnSelected.getText().equals(STATUS_FULL)) {
                    TextView txtTime = detailStoreActivity.findViewById(R.id.editTextTime);
//                    txtTime.setText(createRangeTime(bookTime.startTime, bookTime.endTime, i));
                    txtTime.setText(createRangeTime(bookTime.startTime, new Long(i), i));
                    bookTime.selected = true;
                    if (currentSelected != null) {
                        currentSelected.selected = false;
                    }
                    currentSelected = bookTime;
                    dialog.hide();
                } else {
                    Toast.makeText(detailStoreActivity, "This time is full slot. Please choose another time", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private String createRangeTime(Long startTime, Long endTime, int position) {
//        return startTime + "H - " + endTime + "H";
        String[] minutes = {"00", "10", "15", "20", "30", "35", "45", "50", "55"};
//        Random random = new Random();
        int i = endTime.intValue() % minutes.length;
        return startTime + ":" + minutes[i] + "";
    }

    public DialogTimePickerAdapter setDialog(Dialog dialog) {
        this.dialog = dialog;
        return this;
    }
}
