package com.example.obstinatebrar.capturetheflag;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.games.Player;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.firebase.database.ChildEventListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ObserverActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FirebaseDatabase db;
    DatabaseReference rootNode;

    LatLng flag1,flag2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observer);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Firebase Reference variables
        db = FirebaseDatabase.getInstance();
        rootNode = db.getReference();


    }


    public void settingGround()
    {
         Polyline line = mMap.addPolyline(new PolylineOptions()
        .add(new LatLng(43.774210, -79.335317), new LatLng(43.774255, -79.335093))
         .width(10)
         .color(Color.RED));


         Polyline line2 = mMap.addPolyline(new PolylineOptions()
          .add(new LatLng(43.774012, -79.335264), new LatLng(43.774067, -79.335018))
         .width(10)
         .color(Color.YELLOW));


           Polyline line3 = mMap.addPolyline(new PolylineOptions()
            .add(new LatLng(43.773794, -79.335166), new LatLng(43.773854, -79.334923))
           .width(10)
          .color(Color.RED));


         Polyline line4 = mMap.addPolyline(new PolylineOptions()
         .add(new LatLng(43.774210, -79.335317), new LatLng(43.773794, -79.335166))
        .width(10)
        .color(Color.RED));



           Polyline line5 = mMap.addPolyline(new PolylineOptions()
             .add(new LatLng(43.774255, -79.335093), new LatLng(43.773854, -79.334923))
             .width(10)
            .color(Color.RED));


          LatLng flag1 = new LatLng(43.774223, -79.335103);
         mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.774223, -79.335103),19f));
          MarkerOptions m1 = new MarkerOptions().position(flag1).title("Flag 1");
          m1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));


        mMap.addMarker(m1);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(flag1));

         LatLng flag2 = new LatLng(43.773837, -79.335121);
         mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.774223, -79.335103),19f));
          MarkerOptions m2 = new MarkerOptions().position(flag2).title("Flag 2");

       // LatLng flag2 = new LatLng(43.647165, -79.739287);

      //  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.7732574, -79.338088), 21f));
      //  MarkerOptions m2 = new MarkerOptions().position(flag2).title("Marker in Toronto");
        m2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mMap.addMarker(m2);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(flag2));



    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        getData();

    }

    public void getData() {


       rootNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                mMap.clear();
                for (DataSnapshot postSnapshot : snapshot.child("Users").child("Team A").getChildren()) {

                    settingGround();
                    Log.d("test longitude", "mmmmmmmmmmmm");
                    User users = postSnapshot.getValue(User.class);

                    Log.d("test longitude", String.valueOf(users.longitude));
                    Log.d("test latitude", String.valueOf(users.latitude));
                    Log.d("test playerName", String.valueOf(users.name));




                    LatLng sydney = new LatLng(users.latitude, users.longitude);
                    MarkerOptions m3 = new MarkerOptions().position(sydney).title(users.name+"Team A");

                    m3.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                    mMap.addMarker(m3);

                }

                for (DataSnapshot postSnapshot : snapshot.child("Users").child("Team B").getChildren()) {

                    settingGround();

                    User users = postSnapshot.getValue(User.class);

                    Log.d("test longitude", String.valueOf(users.longitude));
                    Log.d("test latitude", String.valueOf(users.latitude));
                    Log.d("test playerName", String.valueOf(users.name));


                    LatLng sydney = new LatLng(users.latitude, users.longitude);
                    MarkerOptions m4 = new MarkerOptions().position(sydney).title(users.name +"Team B");

                    m4.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));

                    mMap.addMarker(m4);

                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }
}