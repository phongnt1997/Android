package vn.phoneandhisfriends.washcar_store;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.Calendar;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {
    ImageView avt;
    ImageView imgSale;
    ImageView imgService;
    TextView addSale;
    TextView addService;
    ExpandableLayout expandSale;
    ExpandableLayout expandService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        avt = findViewById(R.id.imgAvt);
        final Calendar calendar = Calendar.getInstance();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wascarstore02);
        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        drawable.setCircular(true);
        avt.setImageDrawable(drawable);

        imgSale = findViewById(R.id.imgSale);
        imgService = findViewById(R.id.imgService);
        addSale = findViewById(R.id.addSale);
        addService = findViewById(R.id.addService);
        expandSale = findViewById(R.id.saleInfoExpand);
        expandSale.expand();
        expandService = findViewById(R.id.carServiceExpand);

        addController();
        View.OnClickListener onClickAddListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ProfileActivity.this);
                View.OnClickListener clickToDismiss = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                };
                if (v.getId() == addService.getId()) {

                    dialog.setContentView(R.layout.add_combo_dialog);
                    int width = ViewGroup.LayoutParams.MATCH_PARENT;
                    int height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    dialog.getWindow().setLayout(width, height);

                    Button btnOk = dialog.findViewById(R.id.btnOk);
                    Button btnCancel = dialog.findViewById(R.id.btnCancel);

                    btnCancel.setOnClickListener(clickToDismiss);
                    btnOk.setOnClickListener(clickToDismiss);


                } else {
                    dialog.setContentView(R.layout.add_sale_dialog);
                    int width = ViewGroup.LayoutParams.MATCH_PARENT;
                    int height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    dialog.getWindow().setLayout(width, height);

                    Button btnOk = dialog.findViewById(R.id.btnOk);
                    Button btnCancel = dialog.findViewById(R.id.btnCancel);
                    final TextView startDay = dialog.findViewById(R.id.startDay);
                    final TextView endDay = dialog.findViewById(R.id.endDay);
                    final TextView startTime = dialog.findViewById(R.id.startTime);
                    final TextView endTime = dialog.findViewById(R.id.endTime);
                    View.OnClickListener clickToPickDate = new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
                                public void onDateSet(DatePicker view, int selectedYear,
                                                      int selectedMonth, int selectedDay) {
                                    if (v.getId() == startDay.getId()) {
                                        startDay.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                                                + selectedYear);
                                    } else {
                                        endDay.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                                                + selectedYear);
                                    }

                                }
                            };

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileActivity.this, datePickerListener, calendar.YEAR, calendar.MONTH, calendar.DAY_OF_YEAR);
                                datePickerDialog.getDatePicker().setMinDate(calendar.getTime().getTime());
                                datePickerDialog.show();

                            }


                        }
                    };

                    View.OnClickListener clickToPickTime = new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    if (v.getId() == startTime.getId()) {
                                        startTime.setText(hourOfDay + " : " + minute );
                                    } else {
                                        endTime.setText(hourOfDay + " : " + minute );
                                    }
                                }
                            };
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                TimePickerDialog timePickerDialog = new TimePickerDialog(ProfileActivity.this, timePickerListener, calendar.HOUR_OF_DAY, calendar.MINUTE, true);
                                timePickerDialog.show();

                            }
                        }
                    };

                    startDay.setOnClickListener(clickToPickDate);
                    endDay.setOnClickListener(clickToPickDate);
                    startTime.setOnClickListener(clickToPickTime);
                    endTime.setOnClickListener(clickToPickTime);

                    btnCancel.setOnClickListener(clickToDismiss);
                    btnOk.setOnClickListener(clickToDismiss);
                }
                dialog.show();
            }

        };
        addSale.setOnClickListener(onClickAddListener);
        addService.setOnClickListener(onClickAddListener);

    }

    private void addController() {

        imgSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandSale.toggle();
                expandService.toggle();
            }
        });

        imgService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandService.toggle();
                expandSale.toggle();
            }
        });
    }

    public void clickToEditSercive(View v) {
        final Dialog dialogEditService = new Dialog(ProfileActivity.this);
        View.OnClickListener clickToDismiss = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEditService.dismiss();
            }
        };


        dialogEditService.setContentView(R.layout.edit_combo_dialog);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialogEditService.getWindow().setLayout(width, height);

        Button btnOk = dialogEditService.findViewById(R.id.btnOk);
        Button btnCancel = dialogEditService.findViewById(R.id.btnCancel);
        Button btnDelete = dialogEditService.findViewById(R.id.btnDelete);
        btnCancel.setOnClickListener(clickToDismiss);
        btnOk.setOnClickListener(clickToDismiss);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                dialogEditService.dismiss();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setMessage("Are you sure to delete this service?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });


        dialogEditService.show();
    }

    public void clickToEditSale(View v) {
        final Dialog dialogEditSale = new Dialog(ProfileActivity.this);
        View.OnClickListener clickToDismiss = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEditSale.dismiss();
            }
        };


        dialogEditSale.setContentView(R.layout.edit_sale_dialog);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialogEditSale.getWindow().setLayout(width, height);

        Button btnOk = dialogEditSale.findViewById(R.id.btnOk);
        Button btnCancel = dialogEditSale.findViewById(R.id.btnCancel);
        Button btnDelete = dialogEditSale.findViewById(R.id.btnDelete);
        btnCancel.setOnClickListener(clickToDismiss);
        btnOk.setOnClickListener(clickToDismiss);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                dialogEditSale.dismiss();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setMessage("Are you sure to delete this sale?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }



        });

        final TextView startDay = dialogEditSale.findViewById(R.id.startDay);
        final TextView endDay = dialogEditSale.findViewById(R.id.endDay);
        final TextView startTime = dialogEditSale.findViewById(R.id.startTime);
        final TextView endTime = dialogEditSale.findViewById(R.id.endTime);
        final Calendar calendar = Calendar.getInstance();
        View.OnClickListener clickToPickDate = new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int selectedYear,
                                          int selectedMonth, int selectedDay) {
                        if (v.getId() == startDay.getId()) {
                            startDay.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                                    + selectedYear);
                        } else {
                            endDay.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                                    + selectedYear);
                        }

                    }
                };
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileActivity.this, datePickerListener, calendar.YEAR, calendar.MONTH, calendar.DAY_OF_YEAR);
                    datePickerDialog.getDatePicker().setMinDate(calendar.getTime().getTime());
                    datePickerDialog.show();

                }


            }
        };

        View.OnClickListener clickToPickTime = new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (v.getId() == startTime.getId()) {
                            startTime.setText(hourOfDay + " : " + minute );
                        } else {
                            endTime.setText(hourOfDay + " : " + minute );
                        }
                    }
                };
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(ProfileActivity.this, timePickerListener, calendar.HOUR_OF_DAY, calendar.MINUTE, true);
                    timePickerDialog.show();

                }
            }
        };
        startDay.setOnClickListener(clickToPickDate);
        endDay.setOnClickListener(clickToPickDate);
        startTime.setOnClickListener(clickToPickTime);
        endTime.setOnClickListener(clickToPickTime);

        dialogEditSale.show();

    }


}
