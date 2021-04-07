package com.example.wastemanagement.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.MarkerOptions;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.geocoding.v5.GeocodingCriteria;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.core.exceptions.ServicesException;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.geometry.LatLng;

import com.example.wastemanagement.R;
import com.mapbox.mapboxsdk.Mapbox;

import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
 import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;


public class AddBins extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnMapClickListener, PermissionsListener {

        // variables for adding location layer
        private MapView mapView;
        private MapboxMap mapboxMap;
        Button changelocation;
        AlertDialog.Builder builder;
        TextView binloaction;


    private LocationComponent locationComponent;
    private PermissionsManager permissionsManager;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Mapbox.getInstance(this, getString(R.string.mapbox_key));
            setContentView(R.layout.activity_add_bins);
            mapView = findViewById(R.id.mapView);
            binloaction = findViewById(R.id.binloaction);
            changelocation = findViewById(R.id.changelocation);
            mapView.onCreate(savedInstanceState);
            mapView.getMapAsync(this);
            Context context = mapView.getContext();
            changelocation.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    builder = new AlertDialog.Builder(context,R.style.CustomDialog);
                    LinearLayout layout = new LinearLayout(context);
                    layout.setOrientation(LinearLayout.VERTICAL);


                    final EditText titleBox = new EditText(context);
                    titleBox.setHint("Title");
                    titleBox.setTextColor(R.color.white);
                    layout.addView(titleBox); // Notice this is an add method


                    final EditText descriptionBox = new EditText(context);
                    descriptionBox.setHint("Description");
                    descriptionBox.setTextColor(R.color.white);
                    layout.addView(descriptionBox); // Another add method

                    builder.setView(layout);

                    builder.setTitle("Are You Sure To Change ");
                    builder.setMessage("dh");


                    builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();


                }
            });
        }

        @Override
        public void onMapReady(@NonNull final MapboxMap mapboxMap) {
            this.mapboxMap = mapboxMap;
            mapboxMap.setStyle(getString(R.string.navigation_guidance_day), new Style.OnStyleLoaded() {

                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);

                    addDestinationIconSymbolLayer(style);
                    mapboxMap.addOnMapClickListener(AddBins.this);
                }
            });
        }

        @Override
        protected void onStart() {
            super.onStart();
            mapView.onStart();
        }

        @Override
        protected void onResume() {
            super.onResume();
            mapView.onResume();
        }

        @Override
        protected void onPause() {
            super.onPause();
            mapView.onPause();
        }

        @Override
        protected void onStop() {
            super.onStop();
            mapView.onStop();
        }

        @Override
        protected void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            mapView.onSaveInstanceState(outState);
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            mapView.onDestroy();
        }

        @Override
        public void onLowMemory() {
            super.onLowMemory();
            mapView.onLowMemory();
        }
    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
// Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
// Activate the MapboxMap LocationComponent to show user location
// Adding in LocationComponentOptions is also an optional parameter
            locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(this, loadedMapStyle);
            locationComponent.setLocationComponentEnabled(true);
// Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }
    private void addDestinationIconSymbolLayer(@NonNull Style loadedMapStyle) {

        loadedMapStyle.addImage("destination-icon-id",
                BitmapFactory.decodeResource(this.getResources(), R.drawable.wastebasket15));
        GeoJsonSource geoJsonSource = new GeoJsonSource("destination-source-id");
        loadedMapStyle.addSource(geoJsonSource);


            SymbolLayer destinationSymbolLayer = new SymbolLayer("destination-symbol-layer-id", "destination-source-id");
            destinationSymbolLayer.withProperties(
                    iconImage("destination-icon-id"),
                    iconAllowOverlap(true),
                    iconIgnorePlacement(true)
            );
            loadedMapStyle.addLayer(destinationSymbolLayer);







    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        Point destinationPoint = Point.fromLngLat(point.getLongitude(), point.getLatitude());
        Point originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                locationComponent.getLastKnownLocation().getLatitude());
        final LatLng mapTargetLatLng = mapboxMap.getCameraPosition().target;
        GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id");
        if (source != null) {
           source.setGeoJson(Feature.fromGeometry(destinationPoint));
            //source.setGeoJson(Point.fromLngLat(mapTargetLatLng.getLongitude(), mapTargetLatLng.getLatitude()));
        }

        reverseGeocode(destinationPoint);

        return true;
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }
    private void reverseGeocode(final Point point) {
        try {
            MapboxGeocoding client = MapboxGeocoding.builder()
                    .accessToken(getString(R.string.mapbox_key))
                    .query(Point.fromLngLat(point.longitude(), point.latitude()))
                    .geocodingTypes(GeocodingCriteria.TYPE_ADDRESS)
                    .build();

            client.enqueueCall(new Callback<GeocodingResponse>() {
                @Override
                public void onResponse(@NotNull Call<GeocodingResponse> call, @NotNull Response<GeocodingResponse> response) {

                    if (response.body() != null) {
                        List<CarmenFeature> results = response.body().features();
                        if (results.size() > 0) {
                            CarmenFeature feature = results.get(0);

// If the geocoder returns a result, we take the first in the list and show a Toast with the place name.
                            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                                @Override
                                public void onStyleLoaded(@NonNull Style style) {
                                         Toast.makeText(AddBins.this,
                                                String.format(getString(R.string.location_picker_place_name_result),
                                                        feature.placeName()), Toast.LENGTH_SHORT).show();

                                }
                            });

                        } else {
                            Toast.makeText(AddBins.this,
                                    getString(R.string.location_picker_dropped_marker_snippet_no_results), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<GeocodingResponse> call, Throwable throwable) {
                    Timber.e("Geocoding Failure: %s", throwable.getMessage());
                }
            });
        } catch (ServicesException servicesException) {
            Timber.e("Error geocoding: %s", servicesException.toString());
            servicesException.printStackTrace();
        }
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocationComponent(mapboxMap.getStyle());
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }

    }
}