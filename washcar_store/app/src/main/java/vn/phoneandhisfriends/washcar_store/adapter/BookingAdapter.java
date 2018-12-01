package vn.phoneandhisfriends.washcar_store.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import vn.phoneandhisfriends.washcar_store.DetailCustomerActivity;
import vn.phoneandhisfriends.washcar_store.MainActivity;
import vn.phoneandhisfriends.washcar_store.R;
import vn.phoneandhisfriends.washcar_store.model.ExampleModel;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder>{
    private List<ExampleModel> list;
    int status;
    Context context;
    MainActivity activity;

    public BookingAdapter(Context context, List<ExampleModel> list, int status) {
        this.list = list;
        this.status = status;
        this.context = context;
    }

    public List<ExampleModel> getList() {
        return list;
    }

    public void setList(List<ExampleModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        activity = (MainActivity)context;
        View view = inflater.inflate(R.layout.booking_item_layout, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailCustomerActivity.class);
                context.startActivity(intent);
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.avt.setImageResource(list.get(position).getAvt());
        holder.name.setText(list.get(position).getName());
        holder.lblTimeAndDate.setText(list.get(position).getDate()+ "       " + list.get(position).getTime());
        holder.phone.setText(list.get(position).getPhone());
        String price = Float.toString(list.get(position).getPrice()).replace(".0", ".000");
        holder.price.setText(price + " VND");
        switch (status) {
            case 0:
                if (position > 0) {
                    float pixels = 120 * context.getResources().getDisplayMetrics().density;
                    float tenDp = 18 * context.getResources().getDisplayMetrics().density;
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, (int) pixels, 3.0f);
                    lp.setMargins((int) tenDp, 0, 0, 0);
                    holder.avt.setLayoutParams(lp);
                }
                holder.btnDone.setVisibility(View.VISIBLE);
                switch (position) {
                    case 0:
                        holder.status.setImageResource(R.drawable.ic_one);
                        holder.status.setTag(R.drawable.ic_one);
                        break;
                    case 1:
                        holder.status.setImageResource(R.drawable.ic_two);
                        holder.status.setTag(R.drawable.ic_two);break;
                    case 2:
                        holder.status.setImageResource(R.drawable.ic_three);
                        holder.status.setTag(R.drawable.ic_three);break;
                    case 3:
                        holder.status.setImageResource(R.drawable.ic_four);
                        holder.status.setTag(R.drawable.ic_four);break;

                }
                break;
            case 1:
                holder.status.setImageResource(R.drawable.ic_waiting);
                holder.status.setTag(R.drawable.ic_waiting);
                break;
            case 2:
                holder.status.setImageResource(R.drawable.ic_approve);
                holder.status.setTag(R.drawable.ic_approve);
                break;
            case 3:
                holder.status.setImageResource(R.drawable.ic_denied);
                holder.status.setTag(R.drawable.ic_denied);
                holder.price.setText("Sorry! Our machine has been broken");
                break;

        }


        if((int)holder.status.getTag() == R.drawable.ic_waiting) {
            if(position ==0) {
                holder.buttonExpand.expand();
            }else {
                holder.buttonExpand.collapse();
            }
            holder.status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.buttonExpand.toggle();
                }
            });

            holder.imgAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.PassDataAccepted(list.get(position), position);
                    list.remove(position);
                    notifyItemRemoved(position);
                    notifyDataSetChanged();

                }
            });

            holder.imgDeny.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.reason_deny_dialog);
                        int width = ViewGroup.LayoutParams.MATCH_PARENT;
                        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        dialog.getWindow().setLayout(width, height);

                    Button btnOk = dialog.findViewById(R.id.btnOk);
                    Button btnCancel = dialog.findViewById(R.id.btnCancel);
                    final EditText edtReason = dialog.findViewById(R.id.edtReason);
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(edtReason.getText().toString().length() > 0) {
                                Toast.makeText(context, "Your decision will be sended to customer", Toast.LENGTH_SHORT).show();
                                activity.PassDataDelete(list.get(position), position);
                                list.remove(position);
                                notifyItemRemoved(position);
                                notifyDataSetChanged();
                                dialog.dismiss();
                            }else {
                                Toast.makeText(context, "Please input reason", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            });



        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avt;
        TextView name;
        ImageView status;
        ExpandableLayout buttonExpand;
        ImageView imgAccept;
        ImageView imgDeny;
        TextView lblTimeAndDate;
        TextView phone;
        TextView price;
        Button btnDone;

        public ViewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.name);
            this.avt = itemView.findViewById(R.id.avt);
            this.status = itemView.findViewById(R.id.status);
            this.buttonExpand = itemView.findViewById(R.id.expand);
            this.imgAccept = itemView.findViewById(R.id.statusAccepted);
            this.imgDeny = itemView.findViewById(R.id.statusDenied);
            this.lblTimeAndDate = itemView.findViewById(R.id.lblTimeAndDate);
            this.phone = itemView.findViewById(R.id.phone);
            this.price = itemView.findViewById(R.id.money);
            this.btnDone = itemView.findViewById(R.id.btnDone);
        }

    }

    public interface IFragmentConnectData {
        public void PassDataDelete(ExampleModel model, int i);
        public void PassDataAccepted(ExampleModel model, int i);
    }

}
