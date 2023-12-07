
package com.example.sportssphere;

/** @brief	The android.content. intent */
import android.content.Intent;
/** @brief	The android.os. bundle */
import android.os.Bundle;
/** @brief	The android.util. log */
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
/** @brief	The android.view. view */
import android.view.View;
/** @brief	The android.widget. button */
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
/** @brief	The android.widget. edit text */
import android.widget.EditText;
/** @brief	The android.widget. image view */
import android.widget.ImageView;
/** @brief	The android.widget. toast */
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

/** @brief	The androidx.annotation. non null */
/** @brief	The androidx.appcompat.app. application compatible activity */
import androidx.appcompat.app.AppCompatActivity;


import com.apollographql.apollo3.exception.ApolloGraphQLException;
import com.apollographql.apollo3.runtime.java.ApolloClient;

import java.util.ArrayList;
import java.util.List;

import RetroFitAPI.RetrofitAPICall;
import models.Feature;
import models.FeatureCollection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**********************************************************************************************//**
 * @class	SignUpActivity
 *
 * @brief	A sign up activity.
 *
 * @author	Arie
 * @date	10/20/2023
 **************************************************************************************************/

public class SignUpActivity extends AppCompatActivity {
    ScrollView scrollView;
    RelativeLayout relativeLayout;
    /** @brief	The submit control */
    Button submitButton;
    /** @brief	The first name edit text */
    EditText firstNameEditText;
    /** @brief	The last name edit text */
    EditText lastNameEditText;
    /** @brief	The email edit text */
    EditText emailEditText;
    /** @brief	The password edit text */
    EditText passwordEditText;
    /** @brief	The confirm password edit text */
    EditText confirmPasswordEditText;
    /** @brief	The logo */
    AutoCompleteTextView addressInput;
    ImageView logo;

    /** @brief	The apollo client */
    ApolloClient apolloClient;
    Retrofit retrofit;
    RetrofitAPICall retrofitAPICall;
    Feature feature;
    String longitude, latitude;


    public void createRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://nominatim.openstreetmap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAPICall = retrofit.create(RetrofitAPICall.class);
        addressInput.setThreshold(1);
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
                                addressInput.setDropDownWidth(scrollView.getWidth());
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        scrollView = findViewById(R.id.scrollView);
        scrollView.setBackgroundColor(getColor(R.color.background));
        relativeLayout = findViewById(R.id.backgroundLayout);
        relativeLayout.setBackgroundColor(getColor(R.color.background));
        submitButton = findViewById(R.id.submitButton);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        addressInput = findViewById(R.id.addressInput);
        apolloClient = ApolloClientProvider.setupApolloClient();

        createRetrofit();

        addressAutoPopulate();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Capture the entered information
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                String address = addressInput.getText().toString();

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                longitude = feature.getGeometry().getCoordinates().get(0).toString();
                latitude = feature.getGeometry().getCoordinates().get(1).toString();
                String gps = longitude + ", " + latitude;



                // Send a GraphQL mutation to create the user
                CreateUserMutation createUser = new CreateUserMutation(email, password, firstName, lastName);

                apolloClient.mutation(createUser).enqueue(response -> {
                    if (response.data != null) {
                                                    // Handle successful user registration
                            Log.d("SignUp", "User created successfully!");


                            // Redirect to home after successful registration from the main thread
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    CreateUserAddressMutation createUserAddress = new CreateUserAddressMutation(response.data.createUser.id ,address, gps);
                                    apolloClient.mutation(createUserAddress).enqueue(response2 -> {
                                        if (response2.data != null) {
                                            // Handle successful user registration
                                            Log.d("Address", "Address created successfully!");

                                        } else {
                                            if (response2.exception instanceof ApolloGraphQLException) {
                                                Log.e("GraphQL", String.valueOf(((ApolloGraphQLException) response2.exception).getErrors().get(0)));
                                            } else {
                                                Log.e("Graphql", response2.exception.getMessage());
                                            }
                                        }
                                    });


                                    String id = response.data.createUser.id;
                                    Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                                    intent.putExtra("id", id);
                                    intent.putExtra("password", password);
                                    startActivity(intent);
                                    finish(); // Optionally, to close the SignUpActivity so user cannot navigate back to it
                                }
                            });
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
    }
}

