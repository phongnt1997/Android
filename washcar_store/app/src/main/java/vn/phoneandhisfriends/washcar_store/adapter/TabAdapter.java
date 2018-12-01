package vn.phoneandhisfriends.washcar_store.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Collections;

import vn.phoneandhisfriends.washcar_store.TabFragment;
import vn.phoneandhisfriends.washcar_store.model.ExampleModel;

public class TabAdapter extends FragmentPagerAdapter {
    private String title[] = {"Request","Accepted","Denied"};
    ArrayList<ExampleModel> listBooking;
    ArrayList<ExampleModel> listAccepted;
    ArrayList<ExampleModel> listDeny;
    Bundle bundle;
    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    public TabAdapter(FragmentManager fm, ArrayList<ExampleModel> listBooking, ArrayList<ExampleModel> listAccepted, ArrayList<ExampleModel> listDeny) {
        super(fm);
        this.listBooking = listBooking;
        this.listAccepted = listAccepted;
        this.listDeny = listDeny;
    }

    @Override
    public Fragment getItem(int position) {
        TabFragment historyFragment = new TabFragment();
        Collections.sort(listBooking);
        Collections.sort(listAccepted);
        Collections.sort(listDeny);
        switch (position){
            case 0:
                Bundle bundle1 = new Bundle();
                bundle1.putParcelableArrayList("list", listBooking);
                bundle1.putInt("status", 1);
                historyFragment.setArguments(bundle1);
                return historyFragment;
            case 1:
                Bundle bundle2 = new Bundle();
                bundle2.putParcelableArrayList("list", listAccepted);
                bundle2.putInt("status", 2);
                historyFragment.setArguments(bundle2);
                return historyFragment;

            case 2:
                Bundle bundle3 = new Bundle();
                bundle3.putParcelableArrayList("list", listDeny);
                bundle3.putInt("status", 3);
                historyFragment.setArguments(bundle3);
                return  historyFragment;

        }
        return null;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
