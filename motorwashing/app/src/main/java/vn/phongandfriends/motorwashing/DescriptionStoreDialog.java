package vn.phongandfriends.motorwashing;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import vn.phongandfriends.motorwashing.model.WashCarStore;

public class DescriptionStoreDialog extends DialogFragment {
    private static final long INTERVAL_TIME = 3000L;
    WashCarStore washCarStore;
    ImageView avt;
    TextView tvName;
    TextView tvKm;
    TextView address;
    TextView starNumber;
    TextView reviewer;
    RatingBar ratingBar;
    Button book;
    Button haveSale;
    TextView apply;
    TextView tvPrice;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        washCarStore = (WashCarStore) getArguments().getSerializable("store");

    }

    @Override
    public void onResume() {
        super.onResume();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.obtainMessage().sendToTarget();
            }
        }, INTERVAL_TIME, INTERVAL_TIME);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            d.getWindow().setLayout(width, height);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_description_store, container, false);
        tvName = v.findViewById(R.id.tvNameWasher);
        tvName.setText(washCarStore.getName());
        address = v.findViewById(R.id.tvAddress);
        address.setText(washCarStore.getAddress());
        tvKm = v.findViewById(R.id.tvKm);
        tvKm.setText(washCarStore.getDistance() + " km");
        starNumber = v.findViewById(R.id.tvStarNumber);
        starNumber.setText(washCarStore.getStar() + "");
        reviewer = v.findViewById(R.id.reviewer);
        Random rd = new Random();
        reviewer.setText((rd.nextInt(3000) + 1) + " reviewer");
        reviewer.setPaintFlags(reviewer.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        ratingBar = v.findViewById(R.id.rating);
        ratingBar.setRating(washCarStore.getStar());

        tvPrice = v.findViewById(R.id.tvPrice);
        tvPrice.setText(washCarStore.getPrice());

        LayerDrawable star = (LayerDrawable) ratingBar.getProgressDrawable();
        star.setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        avt = v.findViewById(R.id.avt);
        avt.setImageResource(washCarStore.getImageResource());
        avt.setTag(washCarStore.getImageResource());
        haveSale = v.findViewById(R.id.haveSale);
        apply = v.findViewById(R.id.apply);
        if (washCarStore.getSale() != 0) {
            haveSale.setVisibility(View.VISIBLE);
            haveSale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeSale();
                }
            });
        } else {
            haveSale.setVisibility(View.GONE);
        }
        reviewer = v.findViewById(R.id.reviewer);
        reviewer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ReviewActivity.class);
                startActivity(intent);
            }
        });
        book = v.findViewById(R.id.btnBook);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailStoreActivity.class);
                intent.putExtra("name", washCarStore.getName());
                intent.putExtra("address", washCarStore.getAddress());
                intent.putExtra("price", washCarStore.getPrice());
                startActivity(intent);
            }
        });
        return v;
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            changeSale();
        }
    };

    private void changeSale() {
        if ((Integer) avt.getTag() == washCarStore.getImageResource()) {
            if (washCarStore.getSale() == 10) {
                avt.setImageResource(R.drawable.sale10);
                avt.setTag(R.drawable.sale10);
                apply.setVisibility(View.VISIBLE);
            } else if (washCarStore.getSale() == 20) {
                avt.setImageResource(R.drawable.sale20);
                avt.setTag(R.drawable.sale20);
                apply.setVisibility(View.VISIBLE);
            } else if (washCarStore.getSale() == 30) {
                avt.setImageResource(R.drawable.sale30);
                avt.setTag(R.drawable.sale30);
                apply.setVisibility(View.VISIBLE);
            }

        } else {
            avt.setImageResource(washCarStore.getImageResource());
            avt.setTag(washCarStore.getImageResource());
            apply.setVisibility(View.GONE);
        }
    }
}
