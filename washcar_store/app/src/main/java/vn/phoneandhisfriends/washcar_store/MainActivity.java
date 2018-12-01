package vn.phoneandhisfriends.washcar_store;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.ImageView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import vn.phoneandhisfriends.washcar_store.adapter.BookingAdapter;
import vn.phoneandhisfriends.washcar_store.adapter.TabAdapter;
import vn.phoneandhisfriends.washcar_store.model.ExampleModel;

public class MainActivity extends AppCompatActivity implements BookingAdapter.IFragmentConnectData{
    private List<Fragment> fragments = new ArrayList<>();
    private Fragment fragment;
    private ArrayList<ExampleModel> listWaiting;
    private ArrayList<ExampleModel> listFinish;
    private List<ExampleModel> listPending;
    private ArrayList<ExampleModel> listDenied;
    private ViewPager viewPager;
    private ImageView toLeft;
    private ImageView toRight;
    private FloatingActionButton fabtn;
    ExpandableLayout buttonExpand;
    FloatingActionButton btnHistory;
    FloatingActionButton btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listWaiting = new ArrayList<>();
        listFinish = new ArrayList<>();
        listPending = new ArrayList<>();
        listDenied = new ArrayList<>();
        buttonExpand = findViewById(R.id.expand);
        btnHistory = findViewById(R.id.btnHistory);
        btnHome = findViewById(R.id.btnHome);

        viewPager = (ViewPager) findViewById(R.id.detail_pager);
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), listWaiting, listFinish, listDenied);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.detail_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        fabtn = findViewById(R.id.floatingActionButton);
        fabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonExpand.isExpanded()) {
                    fabtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu));
                }else {
                    fabtn.setImageDrawable(getResources().getDrawable(R.drawable.cancel_menu_button));
                }
                buttonExpand.toggle();
            }
        });
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryListActivity.class);
                startActivity(intent);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        init();





    }

    private void init() {
        int[] avt = {R.drawable.avt1, R.drawable.avt2,R.drawable.avt3,R.drawable.avt4,R.drawable.avt5,R.drawable.avt6,R.drawable.avt10,R.drawable.avt8, R.drawable.avt9,R.drawable.avt7};
        String[] name = {"Nguyễn Thành Phong", "Phạm Hưng Thịnh", "Nguyễn Quốc Huy", "Nguyễn Phan Thảo Nhi", "Trần Tiến Đạt", "Nguyễn Duy Đạt", "Ngô Lâm Gia Lộc", "Trần Thế Nhất Anh", "Nguyễn Duy An", "Nguyễn Hữu Tùng"};
        String[] phone = {"0916284653", "0978429573", "0337284521", "0937821748", "091826390", "09388123945", "0981472942", "0128139412", "0982135235", "0916371825", "0912343435", "0934123623"};
        float[] price = {100, 200, 150, 100, 100, 150, 200, 100, 200, 100, 150, 150};
        String[] date = {"29/04/2018", "15/05/2018", "26/11/2017", "14/10/2018","03/04/2018","26/11/2017","21/10/2018","21/10/2018", "21/10/2018","21/10/2018"};
        String[] time = {"8:30", "9:00", "9:30", "10:30", "11:00", "12:30", "13:30", "13:30", "13:30", "13:30"};
        for (int i = 0; i < name.length; i++) {
            listWaiting.add(new ExampleModel(avt[i],name[i], phone[i], price[i], date[i], time[i]));

            if(i > 4) {
                listFinish.add(new ExampleModel(avt[i],name[i], phone[i], price[i], date[i], time[i]));

            }
            if(i > 5) {
                listPending.add(new ExampleModel(avt[i],name[i], phone[i], price[i], date[i], time[i]));
            }
        }
        final RecyclerView rcv = findViewById(R.id.rcvPending);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        BookingAdapter adapter = new BookingAdapter(this, listPending, 0);
        rcv.setNestedScrollingEnabled(false);
        layoutManager.setSmoothScrollbarEnabled(true);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rcv);
        rcv.setLayoutManager(layoutManager);
        rcv.setAdapter(adapter);

        rcv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int pos = layoutManager.findFirstVisibleItemPosition();
                if(pos == 0) {
                    toLeft.setVisibility(View.GONE);
                    toRight.setVisibility(View.VISIBLE);
                }else if(pos == 3){
                    toLeft.setVisibility(View.VISIBLE);
                    toRight.setVisibility(View.GONE);
                } else {
                    toLeft.setVisibility(View.VISIBLE);
                    toRight.setVisibility(View.VISIBLE);
                }
            }
        });



        toLeft =findViewById(R.id.toLeft);
        toRight =findViewById(R.id.toRight);

        toLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = layoutManager.findFirstVisibleItemPosition();
                if(pos > 0){
                    rcv.smoothScrollToPosition(pos - 1);
                    toRight.setVisibility(View.VISIBLE);
                }
                if(pos == 1 || pos == 0) {
                    toLeft.setVisibility(View.GONE);
                    toRight.setVisibility(View.VISIBLE);
                }else {
                    toLeft.setVisibility(View.VISIBLE);
                }

            }
        });
        toRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = layoutManager.findFirstVisibleItemPosition();
                if(pos < 3){
                    toRight.setVisibility(View.VISIBLE);
                    toLeft.setVisibility(View.VISIBLE);
                    rcv.smoothScrollToPosition(pos + 1);
                }
                if(pos == 2 || pos == 3) {
                    toRight.setVisibility(View.GONE);
                    toLeft.setVisibility(View.VISIBLE);
                }

            }
        });


    }


    @Override
    public void PassDataDelete(ExampleModel model, int i) {
        listDenied.add(0,model);
        //listWaiting.remove(i);
    }

    @Override
    public void PassDataAccepted(ExampleModel model, int i) {
        listFinish.add(model);
        //listWaiting.remove(i);
    }
}
