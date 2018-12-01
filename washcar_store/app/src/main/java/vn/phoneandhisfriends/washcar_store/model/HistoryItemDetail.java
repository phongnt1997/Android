package vn.phoneandhisfriends.washcar_store.model;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryItemDetail implements Serializable, Comparable<HistoryItemDetail> {
    private Integer carStoreId;
    private String code;
    private String date;
    private String status;

    public HistoryItemDetail() {
    }

    public HistoryItemDetail(Integer carStoreId, String code, String date, String status) {
        this.carStoreId = carStoreId;
        this.code = code;
        this.date = date;
        this.status = status;
    }

    public Integer getCarStoreId() {
        return carStoreId;
    }

    public HistoryItemDetail setCarStoreId(Integer carStoreId) {
        this.carStoreId = carStoreId;
        return this;
    }

    public String getCode() {
        return code;
    }

    public HistoryItemDetail setCode(String code) {
        this.code = code;
        return this;
    }

    public String getDate() {
        return date;
    }

    public HistoryItemDetail setDate(String date) {
        this.date = date;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public HistoryItemDetail setStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    public int compareTo(@NonNull HistoryItemDetail historyItemDetail) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date firstDate = null;
        Date secondDate = null;
        try {
            firstDate = simpleDateFormat.parse(this.getDate());
            secondDate = simpleDateFormat.parse(historyItemDetail.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (firstDate != null && secondDate != null) {
            return firstDate.before(secondDate) ? 1 : -1;
        }
        return 0;
    }

    public HistoryItemDetail copy() {
        return new HistoryItemDetail()
                .setCarStoreId(carStoreId)
                .setCode(code)
                .setDate(date)
                .setStatus(status);
    }
}
