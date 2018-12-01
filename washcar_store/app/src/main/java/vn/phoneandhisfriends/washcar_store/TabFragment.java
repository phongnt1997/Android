package vn.phoneandhisfriends.washcar_store;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import vn.phoneandhisfriends.washcar_store.adapter.BookingAdapter;
import vn.phoneandhisfriends.washcar_store.model.ExampleModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {
RecyclerView rcv;
List<ExampleModel> list;
EditText edtSearch;
BookingAdapter adapter;

    public TabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        edtSearch = view.findViewById(R.id.edtSearch);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter = new BookingAdapter(getContext(), findCus(s.toString()), getArguments().getInt("status"));
                rcv.setAdapter(adapter);
            }
        };
        edtSearch.addTextChangedListener(textWatcher);
        rcv = view.findViewById(R.id.rcvBooking);
        list = getArguments().getParcelableArrayList("list");
        adapter = new BookingAdapter(getContext(), list, getArguments().getInt("status"));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setAdapter(adapter);
        return view;
    }

    private List<ExampleModel> findCus(String str) {
        List<ExampleModel> result = new ArrayList<>();
        for (ExampleModel exampleModel: list) {
            if (exampleModel.getName().toLowerCase().contains(str.toLowerCase()) || exampleModel.getPhone().contains(str)){
                result.add(exampleModel);
            }
        }
        return result;
    }

}
