package vn.phoneandhisfriends.washcar_store.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.phoneandhisfriends.washcar_store.R;
import vn.phoneandhisfriends.washcar_store.model.HistoryItemDetail;


public class HistoryAdapter extends BaseAdapter {

    private List<HistoryItemDetail> historyItemDetails;

    public HistoryAdapter(List<HistoryItemDetail> historyItemDetails) {
        this.historyItemDetails = historyItemDetails;
    }

    private Map<Integer, String> stores = new HashMap<>();

    public HistoryAdapter() {
        stores.put(R.drawable.avt1, "Rữa xe nơi tình yêu bắt đầu");
        stores.put(R.drawable.avt2, "Rữa xe hà nội nhớ");
        stores.put(R.drawable.avt3, "Rữa xe sài gòn xưa");
        stores.put(R.drawable.avt4, "Rữa xe phố mùa đông");
        stores.put(R.drawable.avt5, "Rữa xe cafe phố đông");
        stores.put(R.drawable.avt6, "Rữa xe chiều hoàng hôn");
        stores.put(R.drawable.avt7, "Rữa xe nơi này có anh");
        stores.put(R.drawable.avt8, "Rữa xe mưa trên biển nhớ");
        stores.put(R.drawable.avt9, "Rữa xe làn sương khói phôi pha");
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

        HistoryItemDetail historyItemDetail = (HistoryItemDetail) getItem(i);

        ImageView imgWashCarStore = view.findViewById(R.id.imgWashCarStore);
        ImageView imgStatus = view.findViewById(R.id.imgStatus);
        TextView lblWashCarStoreName = view.findViewById(R.id.lblWashCarStoreName);
        TextView lblWashCarStoreBookedCode = view.findViewById(R.id.lblWashCarStoreBookedCode);
        TextView lblStatus = view.findViewById(R.id.lblStatus);
        TextView lblWashCarStoreDate = view.findViewById(R.id.lblWashCarStoreDate);
        TextView lblReason = view.findViewById(R.id.lblReason);
        imgWashCarStore.setImageDrawable(view.getResources().getDrawable(historyItemDetail.getCarStoreId()));
        lblWashCarStoreBookedCode.setText("Booked Code: " + historyItemDetail.getCode());
        lblWashCarStoreName.setText(stores.get(historyItemDetail.getCarStoreId()));
        lblStatus.setText("Status: " + historyItemDetail.getStatus());
        lblWashCarStoreDate.setText("Date: " + historyItemDetail.getDate());

        lblReason.setVisibility(View.GONE);
        switch (historyItemDetail.getStatus()) {
            case "Done":
                imgStatus.setImageDrawable(view.getResources().getDrawable(R.drawable.ic_checked));
                break;
            case "Cancel":
                imgStatus.setImageDrawable(view.getResources().getDrawable(R.drawable.forbidden));
                break;
            case "Request":
                imgStatus.setImageDrawable(view.getResources().getDrawable(R.drawable.ic_waiting));
                break;
            case "Denied":
                lblReason.setVisibility(View.VISIBLE);
                imgStatus.setImageDrawable(view.getResources().getDrawable(R.drawable.denied));
                break;
            case "Appected":
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
