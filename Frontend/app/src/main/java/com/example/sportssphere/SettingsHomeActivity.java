
package com.example.sportssphere;

/**
 * @brief The android.content. intent
 */

import static com.example.sportssphere.type.Permission.ADMIN;

import android.content.Intent;
/** @brief The android.os. bundle */
import android.os.Bundle;
/** @brief The android.view. view */
import android.util.Log;
import android.view.View;
/** @brief The android.widget. button */
import android.widget.Button;
/** @brief The android.widget. compound button */
import android.widget.CompoundButton;
/** @brief The android.widget. switch */
import android.widget.Switch;
/** @brief The androidx.appcompat.app. application compatible activity */
import androidx.appcompat.app.AppCompatActivity;
/** @brief The androidx.appcompat.app. application compatible delegate */
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.TextView;

import com.apollographql.apollo3.exception.ApolloGraphQLException;
import com.apollographql.apollo3.runtime.java.ApolloClient;

/**********************************************************************************************/

/**
 * @class SettingsHomeActivity
 *
 * @brief The settings home activity.
 *
 * @author Arie
 * @date 10/20/2023
 **************************************************************************************************/

public class SettingsHomeActivity extends AppCompatActivity {
    ConstraintLayout constraintLayout;
    Button btnBackToHome, btnDeleteAccount, logOut;
    String id, userEmail, userPassword;
    ApolloClient apolloClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i = getIntent();
        id = i.getStringExtra("id");
        userPassword = i.getStringExtra("password");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings_home);
        constraintLayout = findViewById(R.id.linearLayout);
        constraintLayout.setBackgroundColor(getColor(R.color.background));

        backToHomeLogic();
        logOutLogic();
        deleteAccountLogic();

        apolloClient = ApolloClientProvider.setupApolloClient();

        getUserEmail();
    }


    private void deleteAccountLogic() {
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmation();
            }
        });
    }

    private void backToHomeLogic() {
        btnBackToHome = findViewById(R.id.btnBackToHome);
        btnBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsHomeActivity.this, HomeActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("password", userPassword);
                startActivity(intent);
            }
        });
    }

    private void logOutLogic() {
        logOut = findViewById(R.id.logOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsHomeActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showDeleteConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsHomeActivity.this);
        builder.setTitle("Delete Account");
        builder.setMessage("Are you sure you want to delete your account? This cannot be undone.");

        // Set up the buttons
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteUser();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void deleteUser() {
        getUserEmail();

        apolloClient.mutation(new DeleteUserMutation(id, userEmail, userPassword)).enqueue(response -> {
            if (response.data != null) {
                runOnUiThread(() -> {
                    startActivity(new Intent(SettingsHomeActivity.this, StartActivity.class));
                    finish();
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

    private void getUserEmail() {
        apolloClient.query(new UserIDQuery(id)).enqueue(response -> {
            if (response.data != null) {
                userEmail = response.data.getUserById.email;
                adminSettingsLogic();
            } else {
                if (response.exception instanceof ApolloGraphQLException) {
                    Log.e("GraphQL", String.valueOf(((ApolloGraphQLException) response.exception).getErrors().get(0)));
                } else {
                    Log.e("Graphql", response.exception.getMessage());
                }
            }
        });
    }

    private void adminSettingsLogic() {
        Log.d("Email", userEmail);
        Log.d("Password", userPassword);
        apolloClient.query(new UserPermissionsQuery(userEmail, userPassword)).enqueue(response -> {
            if (response.data != null && response.data.getUserRole != null) {
                Log.d("Permissions", response.data.getUserRole.toString());
                if(response.data.getUserRole == ADMIN) {
                    runOnUiThread(() -> {
                        TextView adminSetings = findViewById(R.id.adminSettings);
                        adminSetings.setTextColor(getColor(R.color.blue3));
                        adminSetings.setVisibility(View.VISIBLE);
                        adminSetings.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(SettingsHomeActivity.this, AdminPageActivity.class);
                                intent.putExtra("id", id);
                                intent.putExtra("password", userPassword);
                                intent.putExtra("email", userEmail);
                                startActivity(intent);
                            }
                        });
                    });
                }
            } else if(response.data.getUserRole == null) {
                Log.d("Perms", "No permissions for user: " + userEmail);
            }
            else {
                if (response.exception instanceof ApolloGraphQLException) {
                    Log.e("GraphQL", String.valueOf(((ApolloGraphQLException) response.exception).getErrors().get(0)));
                } else {
                    Log.e("Graphql", response.exception.getMessage());
                }
            }
        });
    }
}
