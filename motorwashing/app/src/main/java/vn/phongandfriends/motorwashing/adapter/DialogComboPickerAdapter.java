package vn.phongandfriends.motorwashing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import vn.phongandfriends.motorwashing.R;
import vn.phongandfriends.motorwashing.model.Combo;


public class DialogComboPickerAdapter extends BaseAdapter {
    private static final int REMOVE_SIZE = 3;
    public static final String MESSAGE_UNSELECTED_COMBO = "I do not want to use combo";
    private Context context;
    private List<Combo> comboes;

    public DialogComboPickerAdapter(Context context) throws IOException {
        this.context = context;
        InputStream inputStream = context.getResources().openRawResource(R.raw.combo);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String s = null;
        Combo combo = null;
        while ((s = bufferedReader.readLine()) != null) {
            String[] arr = s.split(" - ");
            combo = null;
            if (arr.length > 1) {
                combo = new Combo(arr[0].trim(), arr[1].trim());
            } else {
                combo = new Combo(arr[0].trim(), null);
            }
            (comboes = comboes == null ? new ArrayList<Combo>() : comboes).add(combo);
        }
        if (comboes == null) comboes = new ArrayList<>();
        if (inputStream != null) inputStream.close();
        if (inputStreamReader != null) inputStreamReader.close();
        if (bufferedReader != null) bufferedReader.close();
        int removeNumber = (int) Math.round(Math.random() * REMOVE_SIZE);
        for (int i = 0; i < removeNumber; i++) {
            comboes.remove(comboes.size() - 1);
        }
        if (comboes.size() != 1) {
            comboes.remove(0);
        }
        comboes.add(new Combo(MESSAGE_UNSELECTED_COMBO, null));
    }

    @Override
    public int getCount() {
        return comboes != null ? comboes.size() : (comboes = new ArrayList<>()).size();
    }

    @Override
    public Object getItem(int i) {
        return comboes != null ? comboes.get(i) : null;
    }

    @Override
    public long getItemId(int i) {
        return comboes.indexOf(getItem(i));
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            view = layoutInflater.inflate(R.layout.dialog_combo_item, viewGroup, false);
        }

        Combo combo = (Combo) getItem(i);
        TextView txtCombo = view.findViewById(R.id.txtCombo);
        TextView txtPrice = view.findViewById(R.id.txtPrice);
        txtCombo.setText(combo.getCombo());
        txtPrice.setText(combo.getPrice());
        return view;
    }

    public List<Combo> getComboes() {
        return comboes;
    }

    public DialogComboPickerAdapter setComboes(List<Combo> comboes) {
        this.comboes = comboes;
        notifyDataSetChanged();
        return this;
    }
}
