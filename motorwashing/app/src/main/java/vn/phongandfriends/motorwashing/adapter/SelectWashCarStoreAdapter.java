package vn.phongandfriends.motorwashing.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import vn.phongandfriends.motorwashing.DetailStoreActivity;
import vn.phongandfriends.motorwashing.R;
import vn.phongandfriends.motorwashing.model.WashCarStore;

public class SelectWashCarStoreAdapter extends RecyclerView.Adapter<SelectWashCarStoreAdapter.CarStoreViewHolder> {
    private List<WashCarStore> washCarStores;
    private Context context;

    public SelectWashCarStoreAdapter(List<WashCarStore> washCarStores, Context context) {
        this.washCarStores = washCarStores;
        this.context = context;
    }

    public SelectWashCarStoreAdapter() {
    }

    @Override
    public CarStoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.content_search_fragment, parent, false);
        return new CarStoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CarStoreViewHolder holder, final int position) {
        holder.name.setText(washCarStores.get(position).getName());
        holder.address.setText(washCarStores.get(position).getAddress());
        holder.avt.setImageResource(washCarStores.get(position).getImageResource());
        holder.avt.setTag(washCarStores.get(position).getImageResource());
        holder.starNum.setText(washCarStores.get(position).getStar() + "");
        holder.ratingBar.setRating(washCarStores.get(position).getStar());
        holder.km.setText(washCarStores.get(position).getDistance() + " km");
        holder.price.setText(washCarStores.get(position).getPrice());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailStoreActivity.class);
                intent.putExtra("name", washCarStores.get(position).getName());
                intent.putExtra("address", washCarStores.get(position).getAddress());
                intent.putExtra("price", washCarStores.get(position).getPrice());
                context.startActivity(intent);
            }
        });
        if (washCarStores.get(position).getSale() != 0) {
            holder.haveSave.setVisibility(View.VISIBLE);
            holder.haveSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if ((Integer) holder.avt.getTag() == washCarStores.get(position).getImageResource()) {
                        if (washCarStores.get(position).getSale() == 10) {
                            holder.avt.setImageResource(R.drawable.sale10);
                            holder.avt.setTag(R.drawable.sale10);
                            holder.apply.setVisibility(View.VISIBLE);
                        } else if (washCarStores.get(position).getSale() == 20) {
                            holder.avt.setImageResource(R.drawable.sale20);
                            holder.avt.setTag(R.drawable.sale20);
                            holder.apply.setVisibility(View.VISIBLE);
                        } else if (washCarStores.get(position).getSale() == 30) {
                            holder.avt.setImageResource(R.drawable.sale30);
                            holder.avt.setTag(R.drawable.sale30);
                            holder.apply.setVisibility(View.VISIBLE);
                        }

                    } else {
                        holder.avt.setImageResource(washCarStores.get(position).getImageResource());
                        holder.avt.setTag(washCarStores.get(position).getImageResource());
                        holder.apply.setVisibility(View.GONE);
                    }
                }
            });
        } else {
            holder.haveSave.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        if (washCarStores == null) {
            return 0;
        }
        return washCarStores.size();
    }

    public class CarStoreViewHolder extends RecyclerView.ViewHolder {
        ImageView avt;
        TextView name;
        TextView address;
        TextView starNum;
        TextView km;
        RatingBar ratingBar;
        TextView reviewer;
        LinearLayout layout;
        Button haveSave;
        TextView apply;
        TextView price;

        public CarStoreViewHolder(View itemView) {
            super(itemView);
            this.km = itemView.findViewById(R.id.tvKm);
            this.avt = itemView.findViewById(R.id.avt);
            this.name = itemView.findViewById(R.id.tvNameWasher);
            this.address = itemView.findViewById(R.id.tvAddress);
            this.starNum = itemView.findViewById(R.id.tvStarNumber);
            this.ratingBar = itemView.findViewById(R.id.rating);
            this.reviewer = itemView.findViewById(R.id.reviewer);
            Random rd = new Random();
            reviewer.setText((rd.nextInt(3000) + 1) + " reviewers");
            this.layout = itemView.findViewById(R.id.layoutStore);
            this.haveSave = itemView.findViewById(R.id.haveSale);
            this.apply = itemView.findViewById(R.id.apply);
            this.price = itemView.findViewById(R.id.tvPrice);
        }


    }
}
