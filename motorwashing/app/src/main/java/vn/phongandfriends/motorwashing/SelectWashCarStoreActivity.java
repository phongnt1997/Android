package vn.phongandfriends.motorwashing;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Interpolator;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

import vn.phongandfriends.motorwashing.model.WashCarStore;

public class SelectWashCarStoreActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int MORE_SIZE = 75;
    private static final int FEEDBACK_CONFIRM = 1;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationmMarker;
    FusedLocationProviderClient mFusuedLocationClient;
    Geocoder geocoder;
    List<Address> addressList;
    ArrayList<WashCarStore> washCarStores;
    TextView tvCurLoca;
    AtomicBoolean isMarked = new AtomicBoolean(false);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_wash_car_store);
        mFusuedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        geocoder = new Geocoder(this, Locale.getDefault());
        tvCurLoca = findViewById(R.id.txtCurrLocation);

        initData();

        Intent intent = getIntent();
        String hasFeedBack = intent.getStringExtra("FEEDBACK");
        if (hasFeedBack != null) {
            final Handler handler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message message) {
                    return false;
                }
            });
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showDialog(FEEDBACK_CONFIRM);
                }
            },10000);
            handler.sendEmptyMessage(0);
            handler.obtainMessage().sendToTarget();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sendNotify();
                    handler.obtainMessage().sendToTarget();
                }
            }).start();

        }
    }


    private void initData() {
        washCarStores = new ArrayList<>();
        String[] names = new String[]{"Ma Hai Store", "Vo Long Store", "Bach Le Car Wash Store",
                "4Washer", "Da Lat Nho Store"};
        String[] addresses = new String[]{"126-226 Dương Thị Mười, Tân Thới Hiệp, Quận 12",
                "15/111C Tổ 15 Kp2, Tân Hưng Thuận, Quận 12",
                "59/1, tổ 31, Lê Thị Nho, Trung Mỹ Tây, Quận 12", "122 Tô Ký, Đông Hưng Thuận, Quận 12",
                "34/4A Tổ 16 KP4, Trung Mỹ Tây, Quận 12"};
        int[] imageResources = {R.drawable.wascarstore01, R.drawable.wascarstore02, R.drawable.wascarstore03,
                R.drawable.wascarstore04, R.drawable.wascarstore05,};
        double[] longtitude = {106.6237079, 106.6265573, 106.6206369, 106.6302705, 106.6261621};
        double[] latitude = {10.8533359, 10.8578753, 10.8514353, 10.8494596, 10.8472416};
        float[] star = {4.5f, 4.0f, 5.0f, 5.0f, 4.5f};
        float[] distance = {1.2f, 3.4f, 1.1f, 3.2f, 2.4f};
        int[] sale = {10, 20, 10, 30, 20};
        String[] prices = {"4 seats: 100.000VND, 7 seats: 120.000VND",
                "4 seats: 115.000VND, 7 seats: 120.000VND, \n16 seats: 150.000VND",
                "4 seats: 150.000VND, 7 seats: 200.000VND",
                "4 seats: 250.000VND, 7 seats: 300.000VND",
                "4 seats: 350.000VND, 7 seats: 400.000VND, \nlimosine: 500.000VND",
        };
        for (int i = 0; i < addresses.length; i++) {
            washCarStores.add(new WashCarStore(names[i % names.length],
                    addresses[i % addresses.length],
                    imageResources[i % imageResources.length],
                    longtitude[i % longtitude.length],
                    latitude[i % latitude.length],
                    distance[i % distance.length],
                    star[i % star.length],
                    sale[i % sale.length], prices[i]));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFusuedLocationClient.removeLocationUpdates(mLocationCallBack);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(12000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mFusuedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallBack, Looper.myLooper());
                mMap.setMyLocationEnabled(true);

            } else {
                checkLocationPermission();
            }
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                DescriptionStoreDialog dialogFragment = new DescriptionStoreDialog();
                Bundle bundle = new Bundle();
                bundle.putSerializable("store", washCarStores.get(findIndex(marker.getPosition())));
                dialogFragment.setArguments(bundle);
                dialogFragment.setCancelable(true);

                dialogFragment.show(ft, "dialog");
                return true;
            }
        });
        Circle circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(10.852876, 106.629432))
                .radius(1000)
                .strokeColor(Color.parseColor("#8088D1F2"))
                .fillColor(Color.parseColor("#4D88D1F2")));
//        mMap.setMyLocationEnabled(false);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if (isMarked.get()) return;
                LatLng latLng = new LatLng(location.getLatitude(),
                        location.getLongitude());
//                LatLng latLng = new LatLng(10.852876, 106.629432);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);

//                markerOptions.position(new LatLng(location.getLatitude(), location.getLongitude()));
                markerOptions.title("You");
//                BitmapDescriptor bitmapDescriptor = bitmapDescriptorFromVector(SelectWashCarStoreActivity.this, R.drawable.owner_car_icon);
                mMap.addMarker(markerOptions);
                isMarked.set(true);
            }
        });

//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                new LatLng(location.getLatitude(), location.getLongitude()), 16));

    }

    private void makeMarker() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        LatLng curLatLng = new LatLng(addressList.get(addressList.size() - 1).getLatitude(), addressList.get(addressList.size() - 1).getLongitude());
        builder.include(curLatLng);
        for (int i = 0; i < washCarStores.size(); i++) {
            LatLng latLng = new LatLng(washCarStores.get(i).getLattitude(), washCarStores.get(i).getLongtitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(washCarStores.get(i).getName());
            markerOptions.icon(bitmapDescriptorFromVector(SelectWashCarStoreActivity.this, R.drawable.car_wash_icon_fpt_blue));
            builder.include(latLng);
            mCurrLocationmMarker = mMap.addMarker(markerOptions);
        }
        LatLngBounds bounds = builder.build();

        int padding = 10; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mMap.animateCamera(cu);
    }

    LocationCallback mLocationCallBack = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {

                Location location = locationList.get(locationList.size() - 1);
                mLastLocation = location;
                if (mCurrLocationmMarker != null) {
                    mCurrLocationmMarker.remove();
                }

                try {
                    addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    Address address = addressList.get(0);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                        sb.append(address.getAddressLine(i)).append("\n");
                    }
                    if (address.getThoroughfare() == null || address.getThoroughfare().length() == 0) {
                        sb.append(address.getPremises()).append(", ");
                    } else {
                        sb.append(address.getThoroughfare()).append(", ");
                    }
                    sb.append(address.getSubAdminArea()).append(", ");
                    sb.append(address.getLocality());

                    tvCurLoca.setText(sb.toString());

                } catch (Exception e) {
                    Log.d("IO", e.getMessage());
                }
                makeMarker();


            }


        }
    };

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Nedded")
                        .setMessage("This app needs the Location permissio, Please accept")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(SelectWashCarStoreActivity.this,
                                        new String[]{
                                                Manifest.permission.ACCESS_FINE_LOCATION
                                        }, MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        }).create().show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mFusuedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallBack, Looper.myLooper());
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "permisssion denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private int findIndex(LatLng latLng) {
        for (WashCarStore sto : washCarStores) {
            if (latLng.longitude == sto.getLongtitude() && latLng.latitude == sto.getLattitude()) {
                return washCarStores.indexOf(sto);
            }
        }
        return -1;
    }

    public static int getWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public void clickToOpenSearch(View view) {
        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
        intent.putParcelableArrayListExtra("listStore", washCarStores);
        startActivity(intent);
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth() + MORE_SIZE, vectorDrawable.getIntrinsicHeight() + MORE_SIZE);
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth() + MORE_SIZE, vectorDrawable.getIntrinsicHeight() + MORE_SIZE, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private NotificationManager manager;
    private int notiID = 12345;

    private void sendNotify() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Feedback");
        builder.setContentText("Please feedback about store to help us improve services");
//        builder.setTicker("Message Alert");
        builder.setSmallIcon(R.drawable.car_wash_icon_fpt_blue);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        Intent intent = new Intent(this, FeedbackActivity.class);
        taskStackBuilder.addNextIntent(intent);
        long[] pattern = {500,500,500,500,500,500,500,500,500};
        builder.setVibrate(pattern);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(notiID, builder.build());
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case FEEDBACK_CONFIRM:
                final String storeName = getIntent().getStringExtra("STORENAME");
                return new AlertDialog.Builder(this)
                        .setTitle("Feedback")
                        .setMessage("Please feedback about " + storeName + " to improve our service")
                        .setPositiveButton("Later", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(SelectWashCarStoreActivity.this, FeedbackActivity.class);
                                intent.putExtra("STORENAME", storeName);
                                startActivity(intent);
                                finish();
                            }
                        }).create();
        }
        return null;
    }
}