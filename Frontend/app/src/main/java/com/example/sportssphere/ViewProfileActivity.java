
package com.example.sportssphere;

/** @brief	The android.content. intent */
import static com.example.sportssphere.type.Permission.ADMIN;

import android.content.Intent;
/** @brief	The android.os. bundle */
import android.graphics.Color;
import android.os.Bundle;
/** @brief	The android.util. log */
import android.util.DisplayMetrics;
import android.util.Log;
/** @brief	The android.view. view */
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
/** @brief	The android.widget. button */
import android.widget.Button;
/** @brief	The android.widget. text view */
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/** @brief	The androidx.annotation. non null */
/** @brief	The androidx.appcompat.app. application compatible activity */
import androidx.appcompat.app.AppCompatActivity;

import com.apollographql.apollo3.exception.ApolloGraphQLException;
import com.apollographql.apollo3.runtime.java.ApolloClient;

import java.util.concurrent.atomic.AtomicBoolean;

/**********************************************************************************************//**
 * @class	ViewProfileActivity
 *
 * @brief	A view profile activity.
 *
 * @author	Arie
 * @date	10/20/2023
 **************************************************************************************************/

public class ViewProfileActivity extends AppCompatActivity {
    View popupView;
    PopupWindow popupWindow;
    ScrollView backgroundLayout;
    /** @brief	The identifier */
    Button confirmButton, homeButton, homeButtonPopup, saveButton;
    EditText firstName, lastName, currentPassword, newPassword, confirmNewPassword;
    TextView changePassword, emailLabel;
    String id, password, userEmail; //I have userEmail in here because "email" was showing as null
    String userFirstName, userLastName;

    /** @brief	The apollo client */
    ApolloClient apolloClient;
    /** @brief	The user name text */


    /**********************************************************************************************//**
     * @fn	@Override protected void onCreate(Bundle savedInstanceState)
     *
     * @brief	Executes the 'create' action
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	savedInstanceState	.
     **************************************************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        backgroundLayout = findViewById(R.id.backgroundLayout);
        backgroundLayout.setBackgroundColor(getColor(R.color.background));

        Intent i = getIntent();
        id = i.getStringExtra("id");
        password = i.getStringExtra("password");
        firstName = findViewById(R.id.firstNameEditText);
        lastName = findViewById(R.id.lastNameEditText);
        emailLabel = findViewById(R.id.emailLabel);
        homeButton = findViewById(R.id.homeButton);

        updateFirstAndLastName();
        emailAndPasswordLogic();

        // Fetch the user's data
        fetchUserData();

        homeButtonLogic();
    }
    private void homeButtonLogic(){
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ViewProfileActivity.this, HomeActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("password", password);
                startActivity(intent);
            }
        });
    }
    private void emailAndPasswordLogic(){
        changePassword = findViewById(R.id.changePassword);
        changePassword.setTextColor(getColor(R.color.blue3));
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickShowPopupWindow();
            }
        });
    }

    public void onClickShowPopupWindow() {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.activity_passwordpopup, null);
        popupView.setBackgroundColor(getColor(R.color.offwhite));

        // create the popup window
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        int height = backgroundLayout.getHeight();
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow = new PopupWindow(popupView, width, height, focusable);

        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(getWindow().getDecorView().getRootView(), Gravity.CENTER, 0, 100);
        confirmButton = popupView.findViewById(R.id.confirm_button);
        homeButtonPopup = popupView.findViewById(R.id.homeButtonPopup);
        homeButtonPopup.setHeight(homeButton.getHeight());
        homeButtonPopup.setWidth(homeButton.getWidth());
        homeButtonPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePassword();
            }
        });

        Button cancelButton = popupView.findViewById(R.id.cancelButton);
        cancelButton.setBackgroundColor(getColor(R.color.cancelButton));

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss(); // Close the popup window when the button is clicked
            }
        });
    }
    private void updatePassword(){

                apolloClient  = ApolloClientProvider.setupApolloClient();
                currentPassword = popupView.findViewById(R.id.currentPasswordEditText);
                newPassword = popupView.findViewById(R.id.newPasswordEditText);
                confirmNewPassword = popupView.findViewById(R.id.confirmNewPasswordEditText);

                String currentPasswordString = currentPassword.getText().toString().trim();
                String newPasswordString = newPassword.getText().toString().trim();
                String confirmNewPasswordString = confirmNewPassword.getText().toString().trim();

                if(!currentPasswordString.equals(password)){
                    Toast.makeText(ViewProfileActivity.this, "The current password you entered is incorrect ", Toast.LENGTH_LONG).show();
                } else if (!newPasswordString.equals(confirmNewPasswordString)) {
                    Toast.makeText(ViewProfileActivity.this, "Passwords do not match  ", Toast.LENGTH_LONG).show();
                    newPassword.setText("");
                    confirmNewPassword.setText("");
                }
                else {
                    UpdatePasswordMutation updatePassword = new UpdatePasswordMutation(userEmail, currentPasswordString, newPasswordString);

                    apolloClient.mutation(updatePassword).enqueue(response -> {
                        if (response.data != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ViewProfileActivity.this, "Your password has been successfully changed", Toast.LENGTH_LONG).show();
                                    Log.d("UpdatePassword", "User's password has been updated Successfully!");
                                    popupWindow.dismiss();
                                    Intent intent = new Intent(ViewProfileActivity.this, ViewProfileActivity.class);
                                    intent.putExtra("id", id);
                                    intent.putExtra("password", newPasswordString);
                                    startActivity(intent);

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
    }

    private void updateFirstAndLastName(){
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apolloClient  = ApolloClientProvider.setupApolloClient();
                String newFirstName = firstName.getText().toString().trim();
                String newLastName = lastName.getText().toString().trim();
                UpdateUserMutation updateUser = new UpdateUserMutation(id,userEmail, password, newFirstName, newLastName);

                apolloClient.mutation(updateUser).enqueue(response -> {
                    if (response.data != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ViewProfileActivity.this, "Changes saved successfully", Toast.LENGTH_LONG).show();
                            }
                        });
                        Log.d("UpdateUserInfo", "User updated Successfully!");
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

    /**********************************************************************************************//**
     * @fn	private void fetchUserData()
     *
     * @brief	Fetches user data
     *
     * @author	Arie
     * @date	10/20/2023
     **************************************************************************************************/

    private void fetchUserData() {
        apolloClient = ApolloClientProvider.setupApolloClient();

        apolloClient.query(new UserIDQuery(id)).enqueue(response -> {
            if (response.data != null) {
                userFirstName = response.data.getUserById.first_name;
                userLastName = response.data.getUserById.last_name;
                userEmail = response.data.getUserById.email;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        firstName.setText(userFirstName);
                        lastName.setText(userLastName);
                        emailLabel.setText("Email: " + userEmail);
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
}


