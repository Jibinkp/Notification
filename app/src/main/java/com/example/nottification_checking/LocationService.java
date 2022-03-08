//package com.example.nottification_checking;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//
//
//import android.Manifest;
//import android.app.AlertDialog;
//import android.app.Service;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.IBinder;
//import android.preference.PreferenceManager;
//import android.provider.Settings;
//import android.telephony.TelephonyManager;
//import android.util.Log;
//import android.view.WindowManager;
//import android.widget.ArrayAdapter;
//import android.widget.Toast;
//
//import androidx.core.app.ActivityCompat;
//
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//public class LocationService extends Service {
//
//    private LocationManager locationManager;
//    private Boolean locationChanged;
//
//    private Handler handler = new Handler();
//    public static Location curLocation;
//    public static boolean isService = true;
//    String ip = "";
//    String[] zone;
//    String pc = "";
//    String oldplace = "";
//    public static String place = "", address = "", lati = "", logi = "", speed = "";
//    String lat1, lat2, longi1, longi2;
//
//    //	String oldspace="";
//    int counter = 0;
//
//    String[] tag_id, tag_name;
//
//    String uid, url, url2, hu = "";
//    SharedPreferences sh;
//
//    LocationListener locationListener = new LocationListener() {
//
//        public void onLocationChanged(Location location) {
//            if (curLocation == null) {
//                curLocation = location;
//                locationChanged = true;
//            } else if (curLocation.getLatitude() == location.getLatitude() && curLocation.getLongitude() == location.getLongitude()) {
//                locationChanged = false;
//                return;
//            } else
//                locationChanged = true;
//            curLocation = location;
//
//            if (locationChanged)
//                locationManager.removeUpdates(locationListener);
//        }
//
//        public void onProviderDisabled(String provider) {
//        }
//
//        public void onProviderEnabled(String provider) {
//        }
//
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//            if (status == 0)// UnAvailable
//            {
//            } else if (status == 1)// Trying to Connect
//            {
//            } else if (status == 2) {// Available
//            }
//        }
//    };
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        curLocation = getBestLocation();
//
//        if (curLocation == null) {
//            System.out.println("starting problem.........3...");
//            Toast.makeText(this, "GPS problem..........", Toast.LENGTH_SHORT).show();
//        } else {
//            Log.d("ssssssssssss", String.valueOf("latitude2.........." + curLocation.getLatitude()));
//        }
//        isService = true;
//    }
//
//    final String TAG = "LocationService";
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    @Override
//
//    public void onLowMemory() {
//        super.onLowMemory();
//    }
//
//    @Override
//    public void onStart(Intent intent, int startId) {
//        Toast.makeText(this, "Start services", Toast.LENGTH_SHORT).show();
//
//        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
//
//        if (!provider.contains("gps")) { //if gps is disabled
//            final Intent poke = new Intent();
//            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
//            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
//            poke.setData(Uri.parse("3"));
//            sendBroadcast(poke);
//        }
//
//
//        handler.postDelayed(GpsFinder, 100);
//
//    }
//
//
//    @Override
//    public void onDestroy() {
//
//        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
//
//        if (provider.contains("gps")) { //if gps is enabled
//            final Intent poke = new Intent();
//            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
//            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
//            poke.setData(Uri.parse("3"));
//            sendBroadcast(poke);
//        }
//
//        handler.removeCallbacks(GpsFinder);
//        handler = null;
//        Toast.makeText(this, "Service Stopped..!!", Toast.LENGTH_SHORT).show();
//        isService = false;
//    }
//
//
//    public Runnable GpsFinder = new Runnable() {
//
//
//        public void run() {
//
//            String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
//
//            if (!provider.contains("gps")) { //if gps is disabled
//                final Intent poke = new Intent();
//                poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
//                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
//                poke.setData(Uri.parse("3"));
//                sendBroadcast(poke);
//            }
//
//
//            Location tempLoc = getBestLocation();
//
//            if (tempLoc != null) {
//
//                //Toast.makeText(getApplicationContext(), phoneid, Toast.LENGTH_LONG).show();
//
//                curLocation = tempLoc;
//                // Log.d("MyService", String.valueOf("latitude"+curLocation.getLatitude()));
//
//                lati = String.valueOf(curLocation.getLatitude());
//                logi = String.valueOf(curLocation.getLongitude());
//                speed = String.valueOf(curLocation.getSpeed());
//
//                // Toast.makeText(getApplicationContext(),URL+" received", Toast.LENGTH_SHORT).show();
//
//                Toast.makeText(getApplicationContext(), "\nlat.. and longi.." + lati + "..." + logi, Toast.LENGTH_SHORT).show();
//
//
//                String loc = "";
//
//
//                Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
//                try {
//                    List<Address> addresses = geoCoder.getFromLocation(curLocation.getLatitude(), curLocation.getLongitude(), 1);
//                    if (addresses.size() > 0) {
//                        for (int index = 0; index < addresses.get(0).getMaxAddressLineIndex(); index++)
//                            address += addresses.get(0).getAddressLine(index) + " ";
//
//                        place = addresses.get(0).getLocality().toString();
//
//
//                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//
//                        String hu = sh.getString("ip", "");
//                        String url = "http://" + hu + ":5000/and_update_location";
//
//
//                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                                new Response.Listener<String>() {
//                                    @Override
//                                    public void onResponse(String response) {
//                                        //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
//
//                                        // response
//                                        try {
//                                            JSONObject jsonObj = new JSONObject(response);
//                                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
//
////												Toast.makeText(LocationService.this, "AAAAAAAAAAAAAAAAA", Toast.LENGTH_SHORT).show();
//
//
//                                            }
//
//                                            // }
//                                            else {
//                                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
//                                            }
//
//                                        } catch (Exception e) {
//                                            Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                },
//                                new Response.ErrorListener() {
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//                                        // error
//                                        Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                        ) {
//                            @Override
//                            protected Map<String, String> getParams() {
//                                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                                Map<String, String> params = new HashMap<String, String>();
//
//                                params.put("lat", lati);
//                                params.put("lon", logi);
//                                params.put("place", place);
//                                params.put("lid", sh.getString("lid", ""));
//                                params.put("type", sh.getString("type", ""));
//                                params.put("speed", speed);
//
//                                return params;
//                            }
//                        };
//
//                        int MY_SOCKET_TIMEOUT_MS = 100000;
//
//                        postRequest.setRetryPolicy(new DefaultRetryPolicy(
//                                MY_SOCKET_TIMEOUT_MS,
//                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                        requestQueue.add(postRequest);
//
//
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                Toast.makeText(getBaseContext(), "locality-" + place, Toast.LENGTH_SHORT).show();
//
//
//            }
//
//            handler.postDelayed(GpsFinder, 20000);// register again to start after 20 seconds...
//        }
//
//    };
//
//    private Location getBestLocation() {
//        Location gpslocation = null;
//        Location networkLocation = null;
//        if (locationManager == null) {
//            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
//        }
//        try {
//            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);// here you can set the 2nd argument time interval also that after how much time it will get the gps location
//                gpslocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                //  System.out.println("starting problem.......7.11....");
//
//            }
//            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
//                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, locationListener);
//				networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//			}
//		} catch (IllegalArgumentException e) {
//			Log.e("error", e.toString());
//		}
//		if(gpslocation==null && networkLocation==null)
//			return null;
//
//		if(gpslocation!=null && networkLocation!=null){
//			if(gpslocation.getTime() < networkLocation.getTime()){
//				gpslocation = null;
//				return networkLocation;
//			}else{
//				networkLocation = null;
//				return gpslocation;
//			}
//		}
//		if (gpslocation == null) {
//			return networkLocation;
//		}
//		if (networkLocation == null) {
//			return gpslocation;
//		}
//		return null;
//	}
//	@Override
//	public IBinder onBind(Intent arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
//
//
//
