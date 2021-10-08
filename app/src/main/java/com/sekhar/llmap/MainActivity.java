package com.sekhar.llmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    ImageButton locate;
    Button reset;
    MapView mapView;
    GoogleMap gMap;
    private EditText editTextLat, editTextLng;
    private MarkerOptions options = new MarkerOptions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.mapView);
        editTextLat = findViewById(R.id.edittext_lat);
        editTextLng = findViewById(R.id.edittext_lng);
        locate = findViewById(R.id.imagebutton_locate);
        reset = findViewById(R.id.button_reset);

        Bundle mapBundle = null;

        options.draggable(true);


        if (savedInstanceState != null) {
            mapBundle = savedInstanceState.getBundle("MapViewBundleKey");
        }

        mapView.onCreate(mapBundle);
        mapView.getMapAsync(this);

        locate.setOnClickListener(l -> {
            LatLng latlang = getLatLng();
            options.position(latlang);
            options.title("Location Found");
            gMap.addMarker(options);
            gMap.moveCamera(CameraUpdateFactory.newLatLng(latlang));
        });

        reset.setOnClickListener(l -> {
            editTextLat.setText(null);
            editTextLng.setText(null);
            gMap.clear();
            reset();


        });

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        gMap.getUiSettings().setAllGesturesEnabled(true);
        gMap.getUiSettings().setZoomControlsEnabled(true);
        gMap.getUiSettings().setRotateGesturesEnabled(true);

        reset();
    }

    private void reset() {
        LatLng coordinates = new LatLng(0.0, 0.0);
        gMap.addMarker(new MarkerOptions().position(coordinates));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates));
    }

    private LatLng getLatLng() {
        double lat = 0.0, lng = 0.0;
        if (!TextUtils.isEmpty(editTextLat.getText()) && !TextUtils.isEmpty(editTextLat.getText())) {
            lat = Double.parseDouble(editTextLat.getText().toString());
            lng = Double.parseDouble(editTextLng.getText().toString());
        }
        return new LatLng(lat, lng);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}