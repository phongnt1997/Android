package vn.phongandfriends.motorwashing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import vn.phongandfriends.motorwashing.persistence.Account;


public class StatusFragment extends Fragment {

    public static Account currentUser;

    private static Map<String, Integer> images = new HashMap(){{
        put("0123456789", R.drawable.avatar_01);
    }};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_status, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();

        ImageView imageAvatar = getActivity().findViewById(R.id.imgAvatar);
        TextView txtFullname = getActivity().findViewById(R.id.txtFullname);
        if (currentUser != null) {
            txtFullname.setVisibility(View.VISIBLE);
            txtFullname.setText(currentUser.getFullname());
            int imageResource = images.get(currentUser.getPhone()) != null ? images.get(currentUser.getPhone()) : R.drawable.son_tung;
            imageAvatar.setImageResource(imageResource);
        }else {
            txtFullname.setVisibility(View.GONE);
        }
        imageAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUser == null) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), AccountManageActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (currentUser == null) {
            ImageView imageAvatar = getActivity().findViewById(R.id.imgAvatar);
            imageAvatar.setImageResource(R.drawable.man);
        }
    }
}
