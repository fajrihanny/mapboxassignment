package com.example.mapboxassignment;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import android.graphics.BitmapFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import android.content.Intent;
// classes needed to launch navigation UI
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.mapboxassignment.R;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private MapView mapView;
    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String ICON_ID = "ICON_ID";
    private static final String LAYER_ID = "LAYER_ID";
    private static final String TAG = "MyApp";
    // variables needed to initialize navigation
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        // This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        button = findViewById(R.id.startButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open Places Dialogue
                Intent intent = new PlaceAutocomplete.IntentBuilder()
                        .accessToken(getString(R.string.mapbox_access_token))
                        .placeOptions(PlaceOptions.builder().build(PlaceOptions.MODE_CARDS))
                        .build(MainActivity.this);
                startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
                Log.i(TAG,"Button is clicked");
            }
        });
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        List<Feature> symbolLayerIconFeatureList = new ArrayList<>();
        symbolLayerIconFeatureList.add(Feature.fromGeometry(
                Point.fromLngLat(-57.225365, -33.213144)));
        symbolLayerIconFeatureList.add(Feature.fromGeometry(
                Point.fromLngLat(-54.14164, -33.981818)));
        symbolLayerIconFeatureList.add(Feature.fromGeometry(
                Point.fromLngLat(-56.990533, -30.583266)));
        mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/mapbox/cjf4m44iw0uza2spb3q0a7s41")
        // Adding a GeoJson source for the SymbolLayer icons.
                // Add the SymbolLayer icon image to the map style
                .withImage(ICON_ID, BitmapFactory.decodeResource(
                        MainActivity.this.getResources(), R.drawable.mapbox_marker_icon_default))
                .withSource(new GeoJsonSource(SOURCE_ID,
                FeatureCollection.fromFeatures(symbolLayerIconFeatureList)))
        // Adding the actual SymbolLayer to the map style. An offset is added that the bottom of the red
        // marker icon gets fixed to the coordinate, rather than the middle of the icon being fixed to
        // the coordinate point. This is offset is not always needed and is dependent on the image
        // that you use for the SymbolLayer icon.
      .withLayer(new SymbolLayer(LAYER_ID, SOURCE_ID)
                .withProperties(
                        iconImage(ICON_ID),
                        iconAllowOverlap(true),
                        iconIgnorePlacement(true)
                )
        ), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

                // Map is set up and the style has loaded. Now you can add additional data or make other map adjustments.


            }
        });


    }

    @Override
    public void onResume() {
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
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
