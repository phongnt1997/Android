package vn.phongandfriends.motorwashing.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.phongandfriends.motorwashing.HistoryListActivity;
import vn.phongandfriends.motorwashing.R;
import vn.phongandfriends.motorwashing.model.HistoryItemDetail;

public class HistoryAdapter extends BaseAdapter {

    private List<HistoryItemDetail> historyItemDetails;
    private HistoryListActivity historyListActivity;

    public HistoryAdapter(List<HistoryItemDetail> historyItemDetails, HistoryListActivity historyListActivity) {
        this.historyItemDetails = historyItemDetails;
        this.historyListActivity = historyListActivity;
    }

    private Map<Integer, String> stores = new HashMap<>();

    public HistoryAdapter() {
        stores.put(R.drawable.wascarstore01, "Rữa xe nơi tình yêu bắt đầu");
        stores.put(R.drawable.wascarstore02, "Rữa xe hà nội nhớ");
        stores.put(R.drawable.wascarstore03, "Rữa xe sài gòn xưa");
        stores.put(R.drawable.wascarstore04, "Rữa xe phố mùa đông");
        stores.put(R.drawable.wascarstore05, "Rữa xe cafe phố đông");
        stores.put(R.drawable.wascarstore06, "Rữa xe chiều hoàng hôn");
        stores.put(R.drawable.wascarstore07, "Rữa xe nơi này có anh");
        stores.put(R.drawable.wascarstore08, "Rữa xe mưa trên biển nhớ");
        stores.put(R.drawable.wascarstore09, "Rữa xe làn sương khói phôi pha");
    }

    @Override
    public int getCount() {
        return historyItemDetails.size();
    }

    @Override
    public Object getItem(int i) {
        return historyItemDetails.get(i);
    }

    @Override
    public long getItemId(int i) {
        return historyItemDetails.indexOf(getItem(i));
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            view = layoutInflater.inflate(R.layout.history_item, viewGroup, false);
        }

        final HistoryItemDetail historyItemDetail = (HistoryItemDetail) getItem(i);

        ImageView imgWashCarStore = view.findViewById(R.id.imgWashCarStore);
        ImageView imgStatus = view.findViewById(R.id.imgStatus);
        TextView lblWashCarStoreName = view.findViewById(R.id.lblWashCarStoreName);
        TextView lblWashCarStoreBookedCode = view.findViewById(R.id.lblWashCarStoreBookedCode);
        TextView lblStatus = view.findViewById(R.id.lblStatus);
        TextView lblWashCarStoreDate = view.findViewById(R.id.lblWashCarStoreDate);
        TextView lblReason = view.findViewById(R.id.lblReason);
        ImageView btnCancel = view.findViewById(R.id.btnCancel);
        if (historyItemDetail.getStatus().equals("Wait") || historyItemDetail.getStatus().equals("Approved")) {
            btnCancel.setVisibility(View.VISIBLE);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    LayoutInflater li = LayoutInflater.from(historyListActivity);
                    View promptsView = li.inflate(R.layout.dialog_cancel_confirm, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(historyListActivity);
                    alertDialogBuilder.setView(promptsView);
                    EditText edtReason = promptsView.findViewById(R.id.edtReason);
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            historyListActivity.getHistoryItemDetails()
                                    .stream().filter(p -> p.getCode().equals(historyItemDetail.getCode())).findFirst()
                                    .get()
                                    .setStatus("Cancel");
                            historyListActivity.change();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
        } else {
            btnCancel.setVisibility(View.GONE);
        }
        imgWashCarStore.setImageDrawable(view.getResources().getDrawable(historyItemDetail.getCarStoreId()));
        lblWashCarStoreBookedCode.setText("Booked Code: " + historyItemDetail.getCode());
        lblWashCarStoreName.setText(stores.get(historyItemDetail.getCarStoreId()));
        lblStatus.setText("Status: " + historyItemDetail.getStatus());
        lblWashCarStoreDate.setText("Date: " + historyItemDetail.getDate());

        lblReason.setVisibility(View.GONE);
        switch (historyItemDetail.getStatus()) {
            case "Done":
                imgStatus.setImageDrawable(view.getResources().getDrawable(R.drawable.done));
                break;
            case "Cancel":
                imgStatus.setImageDrawable(view.getResources().getDrawable(R.drawable.forbidden));
                break;
            case "Wait":
                imgStatus.setImageDrawable(view.getResources().getDrawable(R.drawable.wait));
                break;
            case "Denied":
                lblReason.setVisibility(View.VISIBLE);
                imgStatus.setImageDrawable(view.getResources().getDrawable(R.drawable.denied));
                break;
            case "Approved":
                imgStatus.setImageDrawable(view.getResources().getDrawable(R.drawable.approved));
                break;
        }
        return view;
    }

    public List<HistoryItemDetail> getHistoryItemDetails() {
        return historyItemDetails;
    }

    public HistoryAdapter setHistoryItemDetails(List<HistoryItemDetail> historyItemDetails) {
        this.historyItemDetails = historyItemDetails;
        return this;
    }
}
