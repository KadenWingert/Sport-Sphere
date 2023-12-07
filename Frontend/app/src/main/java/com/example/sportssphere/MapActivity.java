package com.example.sportssphere;

import static com.mapbox.maps.plugin.gestures.GesturesUtils.addOnMapClickListener;
import static com.mapbox.maps.plugin.gestures.GesturesUtils.getGestures;
import static com.mapbox.maps.plugin.locationcomponent.LocationComponentUtils.getLocationComponent;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;


import com.apollographql.apollo3.exception.ApolloGraphQLException;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.android.gestures.MoveGestureDetector;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;

import com.apollographql.apollo3.runtime.java.ApolloClient;

import com.mapbox.maps.extension.style.layers.properties.generated.TextAnchor;
import com.mapbox.maps.plugin.LocationPuck2D;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.mapbox.maps.plugin.gestures.OnMapClickListener;
import com.mapbox.maps.plugin.gestures.OnMoveListener;
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin;
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener;
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import RetroFitAPI.RetrofitAPICall;
import models.Feature;
import models.FeatureCollection;
import models.GamePost;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.util.DisplayMetrics;
import android.text.Html;




public class MapActivity extends AppCompatActivity {

    MapView mapView;
    FloatingActionButton floatingActionButton;
    ImageButton searchButton;
    ImageButton clearSearchBar;
    AutoCompleteTextView searchAddress;
    ApolloClient apolloClient;
    FeatureCollection featureCollection;
    Feature feature;
    Retrofit retrofit;
    RetrofitAPICall retrofitAPICall;
    LocationComponentPlugin locationComponentPlugin;
     ArrayList<Point> gamePoints = new ArrayList<>();
    List<GamePostQuery.GetAllGamePost> gamePosts;
    ArrayList<GamePost> gamePostList = new ArrayList<>();
    TextView popupText;
    String id, password;





    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            if (result) {
                Toast.makeText(MapActivity.this, "Permission granted!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MapActivity.this, "Permission not granted!", Toast.LENGTH_LONG).show();
            }
        }
    });
    private final OnIndicatorBearingChangedListener onIndicatorBearingChangedListener = new OnIndicatorBearingChangedListener() {
        @Override
        public void onIndicatorBearingChanged(double v) {
            mapView.getMapboxMap().setCamera(new CameraOptions.Builder().bearing(v).build());
        }
    };
    private final OnIndicatorPositionChangedListener onIndicatorPositionChangedListener = new OnIndicatorPositionChangedListener() {
        @Override
        public void onIndicatorPositionChanged(@NonNull Point point) {
            mapView.getMapboxMap().setCamera((new CameraOptions.Builder().center(point).zoom(20.0).build()));
            getGestures(mapView).setFocalPoint(mapView.getMapboxMap().pixelForCoordinate(point));
        }
    };
    private final OnMoveListener onMoveListener = new OnMoveListener() {
        @Override
        public void onMoveBegin(@NonNull MoveGestureDetector moveGestureDetector) {
            getLocationComponent(mapView).removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
            getLocationComponent(mapView).removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener);
            getGestures(mapView).removeOnMoveListener(onMoveListener);
            floatingActionButton.show();
        }

        @Override
        public boolean onMove(@NonNull MoveGestureDetector moveGestureDetector) {
            return false;
        }

        @Override
        public void onMoveEnd(@NonNull MoveGestureDetector moveGestureDetector) {

        }
    };


    /*
    Sets an onClick listener for the home button
     */
    private void homeButtonLogic() {
        Button homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapActivity.this, HomeActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("password", password);
                startActivity(intent);
            }
        });
    }


    /*
    initial setup of the mapBox map and sets up the userLocation puck at the current location
     */
    private void mapBoxSetup() {
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                locationComponentPlugin = getLocationComponent(mapView);
                locationComponentPlugin.setEnabled(true);
                LocationPuck2D locationPuck2D = new LocationPuck2D(AppCompatResources.getDrawable(
                        MapActivity.this, com.mapbox.maps.plugin.locationcomponent.R.drawable.mapbox_user_icon),
                        AppCompatResources.getDrawable(MapActivity.this, com.mapbox.maps.plugin.locationcomponent.R.drawable.mapbox_user_bearing_icon),
                        AppCompatResources.getDrawable(MapActivity.this, com.mapbox.maps.plugin.locationcomponent.R.drawable.mapbox_user_stroke_icon));

                locationComponentPlugin.setLocationPuck(locationPuck2D);
                locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
                locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener);
                getGestures(mapView).addOnMoveListener(onMoveListener);
                mapView.getMapboxMap().setCamera(new CameraOptions.Builder().zoom(15.0).build());

                floatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener);
                        locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
                        getGestures(mapView).addOnMoveListener(onMoveListener);
                        floatingActionButton.hide();
                        searchAddress.setText("");
                    }
                });
            }
        });
    }

    /*
    onClick listener for the search bar
     */
    private void searchButtonAndImageLogic() {
        searchButton = findViewById(R.id.searchButton);
        searchAddress = findViewById(R.id.searchAddress);
        clearSearchBar = findViewById(R.id.clearSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if search text is filled out
                if (!searchAddress.getText().toString().isEmpty() && searchAddress.getText().toString() != "") {
                    // Perform search if text is filled out
                    performSearch();
                }
            }
        });
        clearSearchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchAddress.setText("");
            }
        });
    }

    /*
    Moves the camera to the searched location
     */
    private void performSearch() {
        String searchText = searchAddress.getText().toString();
        List<Feature> features = featureCollection.getFeatures();
        //Set the feature to the vaule that the user selects
        for (Feature tempFeature : features) {
            if (tempFeature.getProperties().getDisplay_name().equals(searchText)) {
                feature = tempFeature;
            }
        }
        List<Double> coords = feature.getGeometry().getCoordinates();
        Point point = Point.fromLngLat(coords.get(0), coords.get(1));
        locationComponentPlugin.removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
        if (point != null) {
            mapView.getMapboxMap().setCamera(
                    new CameraOptions.Builder()
                            .center(point)
                            .zoom(17.0)
                            .build()
            );
        }
        floatingActionButton.show();
    }

    /*
    Creates a retrofit instance that can be used to call the nominatim api
     */
    public void createRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://nominatim.openstreetmap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAPICall = retrofit.create(RetrofitAPICall.class);
        searchAddress.setThreshold(1);
    }

    /*
    Calls the api after a slight delay when the user begins to type into the search bar
     */
    private void addressAutoPopulate() {
        final Handler handler = new Handler();
        searchAddress.addTextChangedListener(new TextWatcher() {
            private Runnable delaySearch;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Remove any previously scheduled searches
                if (delaySearch != null) {
                    handler.removeCallbacks(delaySearch);
                }
                delaySearch = new Runnable() {
                    @Override
                    public void run() {
                        String userInput = s.toString();
                        Call<FeatureCollection> call = retrofitAPICall.getFeatureCollection(userInput);
                        call.enqueue(new Callback<FeatureCollection>() {
                            @Override
                            public void onResponse(Call<FeatureCollection> call, retrofit2.Response<FeatureCollection> response) {
                                if (!response.isSuccessful()) {
                                    //searchAddress.setText("Code: " + response.code());
                                    return;
                                }

                                featureCollection = response.body();
                                List<Feature> features = featureCollection.getFeatures();
                                List<String> addressSuggestions = new ArrayList<>();
                                for (Feature tempFeature : features) {
                                    feature = tempFeature;
                                    if (feature.getProperties().getDisplay_name().equals(searchAddress.getText().toString())) {
                                        break;
                                    }
                                    addressSuggestions.add(feature.getProperties().getDisplay_name());
                                }
                                ArrayAdapter<String> addressAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, addressSuggestions);
                                searchAddress.setDropDownVerticalOffset(-mapView.getHeight());
                                searchAddress.setDropDownWidth(mapView.getWidth());
                                searchAddress.setAdapter(addressAdapter);
                                searchAddress.showDropDown();
                            }

                            @Override
                            public void onFailure(Call<FeatureCollection> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }
                };
                handler.postDelayed(delaySearch, 250);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /*
    Performs query of gamePosts and creates and places a pin for each gamePost's location
     */
    private void addGamePostPins() {

        apolloClient = ApolloClientProvider.setupApolloClient();

        apolloClient.query(new GamePostQuery()).enqueue(response -> {
            if (response.data != null) {
                GamePostQuery.Data data = response.data;
                gamePosts = data.getAllGamePosts;

                for (GamePostQuery.GetAllGamePost post : gamePosts) {
                    if (post.location != null){
                        String gps = post.location.gps;

                    String gamePostPlayingOn = post.playing_on;
                    // Define the date format pattern to match the input string
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    // Parse the string to a Date object
                    Date gamePostDate, today;
                    try {
                        gamePostDate = dateFormat.parse(gamePostPlayingOn);
                        today = new Date();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    if (gamePostDate.before(today) || gps == null || gps.equals("null") || gps.equals("tes GPS") || gps.equals("test gps")) {
                        continue;
                    }
                    String[] parsedGps = gps.split(",\\s*"); // Split by comma, optionally followed by spaces
                    Double longitude = Double.valueOf(parsedGps[0]);
                    Double latitude = Double.valueOf(parsedGps[1]);
                    if (longitude >= -180 && longitude <= 180 && latitude >= -90 && latitude <= 90) {// ensures you have valid gps coordinates
                        Point point = Point.fromLngLat(longitude, latitude);
                        gamePoints.add(point);
                        GamePost gamePost = new GamePost(post.max_players, post.min_players, post.location.address, point, post.sport.sport_name, post.created_by.first_name, post.playing_on, post.created_by.first_name, post.created_by.last_name);
                        gamePostList.add(gamePost);
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gamepostpin);
                        AnnotationPlugin annotationPlugin = AnnotationPluginImplKt.getAnnotations(mapView);
                        PointAnnotationManager pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(annotationPlugin, mapView);
                        PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions().withTextAnchor(TextAnchor.CENTER).withIconImage(bitmap).withPoint(point);
                        pointAnnotationManager.create(pointAnnotationOptions);
                    }
                }
            }
            } else {
                if (response.exception instanceof ApolloGraphQLException) {
                    Log.e("GraphQL", String.valueOf(((ApolloGraphQLException) response.exception).getErrors().get(0)));
                } else {
                    Log.e("Graphql", response.exception.getMessage());
                }
            }
        });
    }
    private void gamePostClick(){
        addOnMapClickListener(mapView.getMapboxMap(), new OnMapClickListener() {
            @Override
            public boolean onMapClick(@NonNull Point point) {
                boolean popupwindowUsed = false;
                for(Point p : gamePoints){
                    double tolerance = 0.001;
                    if((Math.abs(p.longitude() - point.longitude()) < tolerance
                            && Math.abs(p.latitude() - point.latitude()) < tolerance) && !popupwindowUsed){
                        onClickShowPopupWindow();
                        popupwindowUsed = true;


                        SpannableStringBuilder popupContent = new SpannableStringBuilder();

                        for(int i = 0; i < gamePostList.size(); i++){
                            GamePost gamePost = gamePostList.get(i);
                            if (gamePostList.get(i).getPoint().longitude() == p.longitude() && gamePostList.get(i).getPoint().latitude() == p.latitude()){

                                mapView.getMapboxMap().setCamera((new CameraOptions.Builder().center(point).build()));


                                String currentText = popupText.getText().toString();
                                String sportText = "Sport: " + gamePost.getSport() + "\n";
                                String playingOnText = "Playing on: " + gamePost.getPlayingOn() + "\n";
                                String playersText = "Looking for: " + gamePost.getMinPlayers() + "-" + gamePost.getMaxPlayers() + " players\n";
                                String createdByText = "Created by: " + gamePost.getFirstName() + " " + gamePost.getLastName() + "\n";
                                String addressText = "Address: " + gamePost.getAddress() + "\n\n";
                                SpannableString sportSpannable = new SpannableString(sportText);
                                sportSpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                popupContent.append(sportSpannable);

                                SpannableString playingOnSpannable = new SpannableString(playingOnText);
                                playingOnSpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                popupContent.append(playingOnSpannable);

                                SpannableString playersSpannable = new SpannableString(playersText);
                                playersSpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                popupContent.append(playersSpannable);

                                SpannableString createdBySpannable = new SpannableString(createdByText);
                                createdBySpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                popupContent.append(createdBySpannable);

                                SpannableString addressSpannable = new SpannableString(addressText);
                                addressSpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                popupContent.append(addressSpannable);
                                popupText.setText(popupContent);

                            }
                            popupText.setText(popupContent);
                            }
                        }
                    }
                return false;
            }
        });
    }
    public void onClickShowPopupWindow() {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_map_gamepin_popup, null);
        popupText = popupView.findViewById(R.id.popupText);

        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        Double doubleHeight = screenHeight * .4;
        int height = doubleHeight.intValue();
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(mapView, Gravity.BOTTOM, 0, 0);
        // Add "X" button to the top-right corner
        ImageButton closeButton = popupView.findViewById(R.id.closeButton);
        // Get the device screen width
        int screenWidth = displayMetrics.widthPixels;

        // Calculate the margin for the closeButton to place it on the right
        int closeButtonMargin = screenWidth - 170 /*desired margin from right side*/;

        // Apply the calculated margin to the closeButton
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) closeButton.getLayoutParams();
        params.setMargins(closeButtonMargin, 10, 0, 0);
        closeButton.setLayoutParams(params);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss(); // Close the popup window when the button is clicked
            }
        });
    }

    /*
Initial setup when this view is called
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Context context = this;
        Intent i = getIntent();
       id = i.getStringExtra("id");
       password = i.getStringExtra("password");
        
        homeButtonLogic();
        searchButtonAndImageLogic();
        createRetrofit();
        addressAutoPopulate();

        mapView = findViewById(R.id.mapView);
        floatingActionButton = findViewById(R.id.focusLocation);
        floatingActionButton.hide();
        if (ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            activityResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        mapBoxSetup();
        addGamePostPins();
        gamePostClick();
    }
}
