package com.example.obstinatebrar.capturetheflag;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback {
    FirebaseDatabase db;
    Context context;
    DatabaseReference rootNode;
    LocationManager locationManager;
    Double latitude;
    Double longitude;
    String name;
    String team;
    TextView textView;
    String id;
    private FirebaseAuth mAuth;
    GoogleMap mgoogleMap;
    boolean flagFound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        context=this;
      Log.d("ssssssssss","aaaaaaaaaaaaaaaaaa");
      textView = (TextView)findViewById(R.id.textView) ;
        db = FirebaseDatabase.getInstance();
        rootNode = db.getReference();
       locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        latitude=loc.getLatitude();
        longitude=loc.getLongitude();
        Log.d("aaaaaaa",latitude+","+longitude);

        Intent intent = getIntent();
         name = intent.getStringExtra("personName");
         team = intent.getStringExtra("team");
         id = intent.getStringExtra("id");

        User user= new User(name,team,latitude,longitude,flagFound);

        rootNode.child("Users").child(team).child(id).setValue(user);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                0,
                0, locListener);
        mAuth = FirebaseAuth.getInstance();


        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInAnonymously:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            readDataFromServer();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInAnonymously:failure", task.getException());


                        }

                    }
                });
    }

    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            latitude=location.getLatitude();
            longitude=location.getLongitude();

            User user = new User(name, team,latitude, longitude,flagFound);

            rootNode.child("Users").child(team).child(id).setValue(user);

            Toast.makeText(context,id,Toast.LENGTH_LONG).show();
            Log.d("Longitude: " , String.valueOf(longitude));
            Log.d("Latitude: " , String.valueOf(latitude));
            Location flag1 = new Location("");

            flag1.setLatitude(43.774223);
            flag1.setLongitude(-79.335103);



            Location flag2 = new Location("");

            flag2.setLatitude(43.773837);
            flag2.setLongitude(-79.335121);

            if(user.team.equals("Team A")) {
                float distance = flag2.distanceTo(location);
               textView.setText("AWAY FROM FLAG B =" + distance +" meters");

               if(distance <= 20.00)
               {

                   user.flagfound = true;

                   rootNode.child("Users").child(team).child(id).setValue(user);
                   AlertDialog.Builder adb = new AlertDialog.Builder(LocationActivity.this);

                   adb.setTitle("You win this game");


                   adb.setIcon(android.R.drawable.ic_dialog_alert);

                   adb.show();


                   }

            }

            if(user.team.equals("Team B")) {
               float distance = flag1.distanceTo(location);
               textView.setText("AWAY FROM FLAG A=" + distance +" meters");

                if(distance <= 20.00)
                {
                    user.flagfound = true;

                    rootNode.child("Users").child(team).child(id).setValue(user);
                    AlertDialog.Builder adb = new AlertDialog.Builder(LocationActivity.this);

                    adb.setTitle("You win this game");

                    adb.setIcon(android.R.drawable.ic_dialog_alert);


                    adb.show();
                }

                }

                }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mgoogleMap = googleMap;

        // Add a marker in your current location and move the camera
        LatLng lambton = new LatLng(latitude, longitude);
        mgoogleMap.addMarker(new MarkerOptions().position(lambton).title("YOU ARE HERE"));

        mgoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lambton,15f));


    }

    private void readDataFromServer() {
        rootNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("SERVER DATA", dataSnapshot.toString());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




    }








