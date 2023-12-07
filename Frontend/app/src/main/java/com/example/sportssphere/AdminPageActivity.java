package com.example.sportssphere;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.apollographql.apollo3.exception.ApolloGraphQLException;
import com.apollographql.apollo3.runtime.java.ApolloClient;

import java.util.List;

public class AdminPageActivity extends AppCompatActivity {
    String id, userPassword;

    ApolloClient apolloClient;
    View popupView;
    PopupWindow popupWindow;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Intent i = getIntent();
        id = i.getStringExtra("id");
        userPassword = i.getStringExtra("password");

        Log.d("AdminPage", "Reached admin page");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_page);

        Button backToHome = findViewById(R.id.btnBackToHomeAdmin);

        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPageActivity.this, HomeActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("password", userPassword);
                startActivity(intent);
            }
        });

        apolloClient = ApolloClientProvider.setupApolloClient();

        getAllUsers(this);
    }

    private void getAllUsers(Context context) {
        ScrollView usersScroll = findViewById(R.id.profilesScroll);

        if(usersScroll.getChildCount() > 0) {
            runOnUiThread(() -> { usersScroll.removeAllViews(); });
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth = displayMetrics.widthPixels;

        int quarter = (int) (0.25 * screenWidth);
        int half = (int) (0.5 * screenWidth);

        LinearLayout usersContainer = new LinearLayout(context);
        usersContainer.setOrientation(LinearLayout.VERTICAL);
        usersContainer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        apolloClient.query(new AllUsersQuery()).enqueue(response -> {
            if (response.data != null) {
                List<AllUsersQuery.GetAllUser> users = response.data.getAllUsers;
                for (AllUsersQuery.GetAllUser user : users) {
                    if (user.id.equals(id)) continue;
                    LinearLayout row = new LinearLayout(context);
                    row.setOrientation(LinearLayout.HORIZONTAL);
                    String email = user.email;
                    String password = user.password;
                    String currentID = user.id;

                    TextView textView = new TextView(context);
                    textView.setLayoutParams(new LinearLayout.LayoutParams(half, ViewGroup.LayoutParams.WRAP_CONTENT));
                    textView.setText(email);
                    textView.setPadding(75,0,0,0);

                    Button resetPassword = new Button(context);
                    resetPassword.setText("Reset Password");
                    LinearLayout.LayoutParams resetParams = new LinearLayout.LayoutParams(
                            quarter, ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                    resetPassword.setLayoutParams(resetParams);
                    resetPassword.setPadding(0,40,0,20);
                    resetPassword.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            resetPasswordLogic(email, password);
                        }
                    });

                    Button deleteUser = new Button(context);
                    deleteUser.setText("Delete");
                    deleteUser.setBackgroundColor(Color.RED);
                    LinearLayout.LayoutParams deleteParams = new LinearLayout.LayoutParams(
                            quarter, ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                    deleteUser.setPadding(0,0,0,20);
                    deleteUser.setLayoutParams(deleteParams);
                    deleteUser.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            deleteUserLogic(email, password, currentID, context);
                        }
                    });
                    row.addView(textView);
                    row.addView(resetPassword);
                    row.addView(deleteUser);

                    usersContainer.addView(row);
                }

                runOnUiThread(() -> {
                    usersScroll.addView(usersContainer);
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

    private void resetPasswordLogic(String email, String password) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminPageActivity.this);
        builder.setTitle("Reset Password");
        SpannableString spannableString = new SpannableString("Password for " + email + " has been set to SportsSphere1!");

        // Make "SportsSphere1!" bold
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), spannableString.length() - 14, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Make the email address bold
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 12, 12 + email.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        builder.setMessage(spannableString);

        // Set up the buttons
        builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                apolloClient.mutation(new UpdatePasswordMutation(email, password, "SportsSphere1!")).enqueue(response -> {
                    if (response.data != null) {
                        dialog.cancel();
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

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void deleteUserLogic(String email, String password, String id, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminPageActivity.this);
        builder.setTitle("Delete Account");
        builder.setMessage("Are you sure you want to delete "+ email + "'s account? This cannot be undone.");

        // Set up the buttons
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                apolloClient.mutation(new DeleteUserMutation(id, email, password)).enqueue(response -> {
                    if (response.data != null) {
                        dialog.cancel();
                        getAllUsers(context);
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

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
