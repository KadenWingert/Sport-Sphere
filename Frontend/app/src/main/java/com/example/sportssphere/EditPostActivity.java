package com.example.sportssphere;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import com.apollographql.apollo3.exception.ApolloGraphQLException;
import com.apollographql.apollo3.runtime.java.ApolloClient;

import models.Feature;
import models.FeatureCollection;
import RetroFitAPI.RetrofitAPICall;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditPostActivity extends AppCompatActivity {
    ScrollView background;
    private EditText maxPlayersInput, minPlayersInput, dateInput;
    private AutoCompleteTextView addressInput;
    private Button submitButton, cancelButton;
    String gameID, userID, userPassword, sportID, createdOn, userEmail;
    String sportName, address;
    int maxPlayers, minPlayers;
    Feature feature;
    Retrofit retrofit;
    RetrofitAPICall retrofitAPICall;
    String longitude, latitude;
    ApolloClient apolloClient;

    private boolean isValidDate(String inputDate) throws ParseException {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date date = simpleDateFormat.parse(inputDate);
            Date today = new Date();

            return date != null && today.before(date);
        } catch (Exception e) {
            return false;
        }
    }

    public void createRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://nominatim.openstreetmap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAPICall = retrofit.create(RetrofitAPICall.class);
        addressInput.setThreshold(1);
    }

    private void setButtons() {

        maxPlayersInput = findViewById(R.id.maxPlayersInput);
        minPlayersInput = findViewById(R.id.minPlayersInput);
        dateInput = findViewById(R.id.dateInput);
        addressInput = findViewById(R.id.addressInput);
        submitButton = findViewById(R.id.submitButton);
        cancelButton = findViewById(R.id.cancelButton);
    }

    private void addressAutoPopulate() {
        final Handler handler = new Handler();
        addressInput.addTextChangedListener(new TextWatcher() {
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

                // Schedule a new search after a 1-second delay
                delaySearch = new Runnable() {
                    @Override
                    public void run() {
                        String userInput = s.toString();
                        Call<FeatureCollection> call = retrofitAPICall.getFeatureCollection(userInput);
                        call.enqueue(new Callback<FeatureCollection>() {
                            @Override
                            public void onResponse(Call<FeatureCollection> call, retrofit2.Response<FeatureCollection> response) {
                                if (!response.isSuccessful()) {
                                    //addressInput.setText("Code: " + response.code());
                                    return;
                                }

                                FeatureCollection featureCollection = response.body();
                                List<Feature> features = featureCollection.getFeatures();
                                List<String> addressSuggestions = new ArrayList<>();
                                for (Feature tempFeature : features) {
                                    feature = tempFeature;
                                    if (feature.getProperties().getDisplay_name().equals(addressInput.getText().toString())) {
                                        break;
                                    }
                                    addressSuggestions.add(feature.getProperties().getDisplay_name());
                                }
                                ArrayAdapter<String> addressAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, addressSuggestions);
                                addressInput.setAdapter(addressAdapter);
                                addressInput.showDropDown();
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

    private String getSportIdFromName(String sportName) {
        switch (sportName) {
            case "Basketball":
                return "1";
            case "Pickleball":
                return "2";
            case "Football":
                return "3";
            case "Spikeball":
                return "4";
            case "Tennis":
                return "5";
            case "Volleyball":
                return "6";
            case "Softball":
                return "7";
            case "Hockey":
                return "8";
            default:
                return null; // Invalid Sport
        }
    }



    private void getCurrentGamePost() {
        apolloClient.query(new SingleGamePostQuery(gameID)).enqueue(response -> {
            if (response.data != null) {
                sportName = response.data.getGamePostById.sport.sport_name;
                createdOn = response.data.getGamePostById.created_on;
                maxPlayers = response.data.getGamePostById.max_players;
                minPlayers = response.data.getGamePostById.min_players;
                address = response.data.getGamePostById.location.address;
                sportID = getSportIdFromName(sportName);


                maxPlayersInput.setText(response.data.getGamePostById.max_players.toString());
                minPlayersInput.setText(response.data.getGamePostById.min_players.toString());
                addressInput.setText(address);
                dateInput.setText(response.data.getGamePostById.playing_on.toString());

                getUserEmail();
            } else {
                if (response.exception instanceof ApolloGraphQLException) {
                    Log.e("GraphQL", String.valueOf(((ApolloGraphQLException) response.exception).getErrors().get(0)));
                } else {
                    Log.e("Graphql", response.exception.getMessage());
                }
            }
        });
    }
    private void getUserEmail() {
        apolloClient.query(new UserIDQuery(userID)).enqueue(response -> {
            if (response.data != null) {
                userEmail = response.data.getUserById.email;
            } else {
                if (response.exception instanceof ApolloGraphQLException) {
                    Log.e("GraphQL", String.valueOf(((ApolloGraphQLException) response.exception).getErrors().get(0)));
                } else {
                    Log.e("Graphql", response.exception.getMessage());
                }
            }
        });
    }




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        gameID = i.getStringExtra("gameID");
        userID = i.getStringExtra("id");
        userPassword = i.getStringExtra("password");
        setContentView(R.layout.activity_edit_post);
        background = findViewById(R.id.background);
        background.setBackgroundColor(getColor(R.color.background));
        setButtons();
        apolloClient = ApolloClientProvider.setupApolloClient();

        createRetrofit();
        addressAutoPopulate();
        getCurrentGamePost();

        Context context = this;



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int maxPlayers = Integer.parseInt(maxPlayersInput.getText().toString().trim());
                int minPlayers = Integer.parseInt(minPlayersInput.getText().toString().trim());
                String date = dateInput.getText().toString().trim();
                String address = addressInput.getText().toString().trim();
                longitude = feature.getGeometry().getCoordinates().get(0).toString();
                latitude = feature.getGeometry().getCoordinates().get(1).toString();
                String gps = longitude + ", " + latitude;

                // Check for empty fields
                if (maxPlayers <= 0 || minPlayers <= 0 || date.isEmpty() || address.isEmpty()) {
                    Toast.makeText(context, "Please fill in all fields!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (minPlayers > maxPlayers) {
                    Toast.makeText(context, "Minimum players cannot be more tha Maximum", Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    if (!isValidDate(date)) {
                        Toast.makeText(context, "Date cannot be before today", Toast.LENGTH_LONG);
                        return;
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                apolloClient = ApolloClientProvider.setupApolloClient();


                apolloClient.mutation(new EditPostMutation(gameID, sportID, maxPlayers, minPlayers, date, createdOn, false, address, gps, userEmail, userPassword))
                        .enqueue(response -> {
                            if (response.data != null) {
                                Intent intent = new Intent(EditPostActivity.this, ViewPostActivity.class);
                                intent.putExtra("gameID", gameID);
                                intent.putExtra("id", userID);
                                intent.putExtra("password", userPassword);
                                startActivity(intent);
                            } else {
                                if (response.exception instanceof ApolloGraphQLException) {
                                    Log.e("GraphQL", String.valueOf(((ApolloGraphQLException) response.exception).getErrors().get(0)));
                                } else {
                                    Log.e("Graphql", response.exception.getMessage());
                                }
                            }
                        });

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditPostActivity.this, ViewPostActivity.class);
                intent.putExtra("gameID", gameID);
                intent.putExtra("id", userID);
                intent.putExtra("password", userPassword);
                startActivity(intent);
            }
        });
    }
}
