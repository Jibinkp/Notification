package com.example.nottification_checking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
Button b;
TextView tv;
LocationManager locationM;
    public static Location curLocation;
    private Boolean locationChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=(TextView) findViewById(R.id.textView2);
        b=(Button)findViewById(R.id.button);
        b.setOnClickListener(this);
//        double la=getBestLocation().getLatitude();

//        tv.setText(String.valueOf(la));


//        try {
//
//            if(Build.VERSION.SDK_INT>9) {
//                StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
//                StrictMode.setThreadPolicy(policy);
//            }
//
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
    }
    /*LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
               if (curLocation == null) {
                curLocation = location;
                locationChanged = true;
            } else if (curLocation.getLatitude() == location.getLatitude() && curLocation.getLongitude() == location.getLongitude()) {
                locationChanged = false;
                return;
            } else
                locationChanged = true;
            curLocation = location;

            if (locationChanged)
                locationM.removeUpdates(locationListener);

            Toast.makeText(getApplicationContext(), "uuuuuuuuuu", Toast.LENGTH_SHORT).show();

        }
    };
    private Location getBestLocation() {
        Location gpslocation = null;
        Location networkLocation = null;
        if (locationM == null) {
            locationM = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
        try {
            if (locationM.isProviderEnabled(locationM.GPS_PROVIDER)) {
                locationM.requestLocationUpdates(locationM.GPS_PROVIDER, 5000, 0, locationListener);// here you can set the 2nd argument time interval also that after how much time it will get the gps location
                gpslocation = locationM.getLastKnownLocation(locationM.GPS_PROVIDER);
                //  System.out.println("starting problem.......7.11....");

            }
            if (locationM.isProviderEnabled(locationM.NETWORK_PROVIDER)) {


                locationM.requestLocationUpdates(locationM.NETWORK_PROVIDER, 5000, 0, locationListener);
                networkLocation = locationM.getLastKnownLocation(locationM.NETWORK_PROVIDER);
            }
        } catch (IllegalArgumentException e) {
            Log.e("error", e.toString());
        }
//        if(gpslocation==null && networkLocation==null)
//            return null;
//
//        if(gpslocation!=null && networkLocation!=null){
//            if(gpslocation.getTime() < networkLocation.getTime()){
//                gpslocation = null;
//                return networkLocation;
//            }else{
//                networkLocation = null;
//                return gpslocation;
//            }
//        }
        if (gpslocation == null) {
            return networkLocation;
        }
        if (networkLocation == null) {
            return gpslocation;
        }
        return null;
    }*/
    private void showSmallNotification(String message){
        String CHANNEL_ID = NotificationHelper.CHANNEL_ONE_ID;
        String CHANNEL_NAME = "Notification";
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        inboxStyle.addLine(message);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // I would suggest that you use IMPORTANCE_DEFAULT instead of IMPORTANCE_HIGH
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            channel.setLightColor(Color.BLUE);
            channel.enableLights(true);
            channel.setShowBadge(true);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setVibrate(new long[]{0, 100})
                .setPriority(Notification.PRIORITY_MAX)
                .setLights(Color.BLUE, 3000, 3000)
                .setAutoCancel(true)
                .setContentTitle("Notification")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(inboxStyle)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.folder))
                .setContentText(message);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationBuilder.setChannelId(NotificationHelper.CHANNEL_ONE_ID);
        }
        notificationManager.notify(CHANNEL_ID, 1, notificationBuilder.build());
    }

    @Override
    public void onClick(View v) {
        showSmallNotification("You have a new vioalation message...!!!");

    }
}