package com.example.sportssphere;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.apollographql.apollo3.exception.ApolloGraphQLException;
import com.apollographql.apollo3.runtime.java.ApolloClient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class CalendarActivity extends AppCompatActivity {
    RelativeLayout relativeLayout;
    private String userId, password;
    private Button backToHomeButton;
    private CalendarView calendarView;
    private ApolloClient apolloClient;
    private ScrollView scrollView;
    private LinearLayout postContainer;
    private EventOverlayView eventOverlayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        relativeLayout = findViewById(R.id.backgroundLayout);
        relativeLayout.setBackgroundColor(getColor(R.color.background));

        backToHomeButton = findViewById(R.id.backToHomeButton);
        calendarView = findViewById(R.id.calendarView);
        scrollView = findViewById(R.id.scrollView);
        postContainer = findViewById(R.id.postContainer);
        userId = getIntent().getStringExtra("id");
        password = getIntent().getStringExtra("password");

        backToHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(CalendarActivity.this, HomeActivity.class);
            intent.putExtra("id", userId);
            intent.putExtra("password", password);
            startActivity(intent);
        });

        apolloClient = ApolloClientProvider.setupApolloClient();
        fetchUserDetailsAndGamePosts();

        eventOverlayView = findViewById(R.id.eventOverlayView);
    }

    private void fetchUserDetailsAndGamePosts() {
        // Fetch user details first
        apolloClient.query(new UserIDQuery(userId)).enqueue(response -> {
            if (response.data != null) {
                UserIDQuery.GetUserById user = response.data.getUserById;
                if(user != null) {
                    fetchGamePosts(user.first_name, user.last_name);
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

    private void fetchGamePosts(String firstName, String lastName) {
        apolloClient.query(new GamePostQuery()).enqueue(response -> {
            if (response.data != null) {
                List<GamePostQuery.GetAllGamePost> allPosts = response.data.getAllGamePosts;
                List<GamePostQuery.GetAllGamePost> userPosts = new ArrayList<>();
                for(GamePostQuery.GetAllGamePost post : allPosts) {
                    if(post.created_by.first_name.equals(firstName) && post.created_by.last_name.equals(lastName)){
                        userPosts.add(post);
                    }
                }
                runOnUiThread(() -> displayGamePosts(userPosts));
            } else {
                if (response.exception instanceof ApolloGraphQLException) {
                    Log.e("GraphQL", String.valueOf(((ApolloGraphQLException) response.exception).getErrors().get(0)));
                } else {
                    Log.e("Graphql", response.exception.getMessage());
                }
            }
        });
    }

    private void displayGamePosts(List<GamePostQuery.GetAllGamePost> gamePosts) {
        postContainer.removeAllViews();
        Set<String> eventDates = new HashSet<>();

        for (GamePostQuery.GetAllGamePost post : gamePosts) {
            String playingOn = post.playing_on;
            String address = post.location != null ? post.location.address : "N/A";

            TextView dateView = new TextView(CalendarActivity.this);
            dateView.setText("Date: " + playingOn);
            dateView.setTextColor(Color.BLACK);
            dateView.setTextSize(16);

            TextView addressView = new TextView(CalendarActivity.this);
            addressView.setText("Address: " + address);
            addressView.setTextColor(Color.GRAY);
            addressView.setTextSize(14);

            // Add the TextViews to the LinearLayout
            postContainer.addView(dateView);
            postContainer.addView(addressView);

            // Add a separator view
            View separator = new View(CalendarActivity.this);
            separator.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
            separator.setBackgroundColor(Color.LTGRAY);
            postContainer.addView(separator);
            eventDates.add(playingOn);
        }
        
        eventOverlayView.setEventDates(eventDates);
    }
}
