
package com.example.sportssphere;

/**
 * @brief Context for the android.content.
 */

import android.content.Context;
/** @brief The android.content. intent */
import android.content.Intent;
/** @brief The android.graphics. color */
/** @brief The android.os. bundle */
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
/** @brief The android.util. log */
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
/** @brief The android.view. view */
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
/** @brief The android.widget. button */
import android.view.ViewGroup;
import android.widget.Button;
/** @brief The android.widget. image button */
/** @brief The android.widget. linear layout */
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
/** @brief The android.widget. scroll view */
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
/** @brief The android.widget. text view */
import android.widget.TextView;

/** @brief The androidx.annotation. non null */
/** @brief The androidx.appcompat.app. application compatible activity */
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.apollographql.apollo3.exception.ApolloGraphQLException;
import com.apollographql.apollo3.runtime.java.ApolloClient;
import com.google.api.Distribution;


/** @brief List of java.util.s */

import org.checkerframework.checker.units.qual.A;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**********************************************************************************************/

/**
 * @class HomeActivity
 *
 * @brief A home activity.
 *
 * @author Arie
 * @date 10/20/2023
 **************************************************************************************************/

public class HomeActivity extends AppCompatActivity {
    ConstraintLayout constraintLayout;
    ScrollView scrollView;
    CardView cardViewTop, cardViewBottom;
    Button profileButton, createPostButton, settingsButton, mapButton, calendarButton, filterButton;
    View borderView;
    ApolloClient apolloClient;
    /** @brief The identifier */
    String id;
    String userPassword;
    TextView textView;
    ArrayList<Post> allPosts = new ArrayList<>();
    AllGamePostsSubscription.SubGamePost subGamePost;
    ArrayList<String> checkedSports = new ArrayList<>();
    boolean checkedSave = false;
    Date from = null, to = null;
    CheckBox basketballCheck, softballCheck, pickelballCheck, footballCheck, tennisCheck, hockeyCheck, volleyballCheck, spikeballCheck;
    List<GetAllSavedPostQuery.GetAllSavedGamePost> savedGamePosts;
    /**********************************************************************************************/
    /**
     * @fn    @Override protected void onCreate(Bundle savedInstanceState)
     *
     * @brief Executes the 'create' action
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param    savedInstanceState    .
     **************************************************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        constraintLayout = findViewById(R.id.scrollBackground);
        constraintLayout.setBackgroundColor(getColor(R.color.background));
        cardViewTop = findViewById(R.id.topCardView);
        cardViewTop.setBackgroundColor(getColor(R.color.background));
        cardViewBottom = findViewById(R.id.bottomCardView);
        cardViewBottom.setBackgroundColor(getColor(R.color.background));
        cardViewBottom.setCardElevation(100);
        profileButton = findViewById(R.id.viewProfileButton);
        createPostButton = findViewById(R.id.createPostButton);
        scrollView = findViewById(R.id.scrollViewHome);
        scrollView.setBackgroundColor(getColor(R.color.lightGrey));
        settingsButton = findViewById(R.id.settingsButton);  // Initializing the settings button
        filterButton = findViewById(R.id.filterButton);
        mapButton = findViewById(R.id.mapButton);
        calendarButton = findViewById(R.id.calendarButton);

        Context context = this;
        Intent i = getIntent();
        id = i.getStringExtra("id");
        userPassword = i.getStringExtra("password");

        apolloClient = ApolloClientProvider.setupApolloClient();

        queryPosts(context);

        apolloClient.subscription(new GreetingsSubscription()).enqueue(response -> {
            if (response.data != null) {
                Log.d("Greetings", response.data.greetings);
            } else {
                if (response.exception instanceof ApolloGraphQLException) {
                    Log.e("GraphQL", String.valueOf(((ApolloGraphQLException) response.exception).getErrors().get(0)));
                } else {
                    Log.e("Graphql", response.exception.getMessage());
                }
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ViewProfileActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("password", userPassword);
                //intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        createPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this, CreatePostActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("password", userPassword);
                startActivity(intent);
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {  // Listener for settings button
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SettingsHomeActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("password", userPassword);
                startActivity(intent);
            }
        });
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MapActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("password", userPassword);
                startActivity(intent);
            }
        });
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CalendarActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("password", userPassword);
                startActivity(intent);
            }
        });
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    onClickShowPopupWindow();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private LinearLayout displayPostInfo(Context context, String sportName, String firstName, String lastName, int minPlayers, int maxPlayers, String playingOn, String address, String gameID) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        CardView cardView = new CardView(context);
        cardView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        cardView.setCardElevation(15);
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                100,
                100
        ));
        imageView.setForegroundGravity(Gravity.CENTER);
        LinearLayout linearLayout1 = new LinearLayout(context);
        linearLayout1.setGravity(LinearLayout.HORIZONTAL);
        textView = new TextView(context);
        textView.setWidth(scrollView.getWidth());
        textView.setHeight(375);

        switch (sportName) {
            case "Basketball":
                linearLayout1.setBackgroundColor(getColor(R.color.basketball));
                imageView.setBackgroundResource(R.drawable.basketball);
                break;
            case "Pickleball":
                linearLayout1.setBackgroundColor(getColor(R.color.pickleball));
                imageView.setBackgroundResource(R.drawable.pickleball_paddle);
                break;
            case "Football":
                linearLayout1.setBackgroundColor(getColor(R.color.football));
                imageView.setBackgroundResource(R.drawable.football);
                break;
            case "Spikeball":
                linearLayout1.setBackgroundColor(getColor(R.color.spikeball));
                imageView.setBackgroundResource(R.drawable.spikeball);
                break;
            case "Tennis":
                linearLayout1.setBackgroundColor(getColor(R.color.tennis));
                imageView.setBackgroundResource(R.drawable.tennis);
                break;
            case "Volleyball":
                linearLayout1.setBackgroundColor(getColor(R.color.volleyball));
                imageView.setBackgroundResource(R.drawable.volleyball);
                break;
            case "Softball":
                linearLayout1.setBackgroundColor(getColor(R.color.softball));
                imageView.setBackgroundResource(R.drawable.softball);
                break;
            case "Hockey":
                linearLayout1.setBackgroundColor(getColor(R.color.hockey));
                imageView.setBackgroundResource(R.drawable.hoceky);
                break;
            default:
                imageView.setBackgroundResource(R.drawable.softball);
        }
        Spanned formattedText;
        if(address == null){
            formattedText = Html.fromHtml(
                    "<b>" + sportName + "</b>" + "<br>Created by: " + firstName
                            + " " + lastName + "<br>Looking for " + minPlayers + " to "
                            + maxPlayers + " players<br>Playing on " + playingOn
            );
        }
        else{        formattedText = Html.fromHtml(
                "<b>" + sportName + "</b>" + "<br>Created by: " + firstName
                        + " " + lastName + "<br>Looking for " + minPlayers + " to "
                        + maxPlayers + " players<br>Playing on " + playingOn + " at "
                        + address);
        }


        textView.setText(formattedText);
        textView.setGravity(Gravity.CENTER);
        linearLayout1.addView(imageView);
        linearLayout1.setGravity(Gravity.CENTER);
        linearLayout1.addView(textView);
        cardView.addView(linearLayout1);

        linearLayout.setPadding(0, -55, 0, -55); // Adjusted padding
        textViewOnClickListener(gameID);
        cardView.setBackgroundResource(R.drawable.my_nine_patch);
        linearLayout.addView(cardView);

        return linearLayout;
    }


private void textViewOnClickListener(String gameID){
    textView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(HomeActivity.this, ViewPostActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("gameID", gameID);
            intent.putExtra("password", userPassword);
            startActivity(intent);
        }
    });
}


    private void displayPosts(Context context) {
        scrollView.removeAllViews();
        LinearLayout postContainer = new LinearLayout(context);
        postContainer.setOrientation(LinearLayout.VERTICAL);

        ArrayList<Post> postsDisplayed = new ArrayList<>();

        for (Post post : allPosts) {
            String sportName = post.sportName;
            String firstName = post.firstName;
            String lastName = post.lastName;
            int maxPlayers = post.max;
            int minPlayers = post.min;
            String playingOn = post.playingOn;
            String address = post.address;

            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

            if(!checkedSports.isEmpty() && !checkedSports.contains(sportName)) {
                continue;
            }
            if(checkedSave) {
                boolean foundPost = false;
                for(GetAllSavedPostQuery.GetAllSavedGamePost savedPost : savedGamePosts) {
                    String thisID = String.valueOf(savedPost.gamePost);
                    if(savedPost.user.id.equals(id) && thisID.equals(post.id)) {
                        foundPost = true;
                        break;
                    }
                }
                if(!foundPost || (!postsDisplayed.isEmpty() && postsDisplayed.contains(post))) {
                    continue;
                }
            }

            RelativeLayout.LayoutParams postParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
            );
            postContainer.addView(displayPostInfo(context, sportName, firstName, lastName, minPlayers, maxPlayers, playingOn, address, post.id), postParams);
            postContainer.setPadding(0,0,0,0);

            postsDisplayed.add(post);
        }

        scrollView.addView(postContainer);
        scrollView.setPadding(0,0,0,0);
        scrollView.setBackgroundColor(getColor(R.color.background));
    }

    private void subscribeToPosts(Context context) {
        apolloClient.subscription(new AllGamePostsSubscription()).enqueue(response -> {
            if (response.data != null) {
                Log.d("Ressponse", "Receiving a response");
                subGamePost = response.data.subGamePost;
                String sportName = subGamePost.sport != null ? subGamePost.sport.sport_name : null;
                String firstName = subGamePost.created_by != null ? subGamePost.created_by.first_name : null;
                String lastName = subGamePost.created_by != null ? subGamePost.created_by.last_name : null;
                int maxPlayers = subGamePost.max_players;
                int minPlayers = subGamePost.min_players;
                String playingOn = subGamePost.playing_on;
                String address = subGamePost.location != null ? subGamePost.location.address : null;
                String id = subGamePost.id;

                allPosts.add(new Post(sportName, firstName, lastName, maxPlayers, minPlayers, playingOn, address, id));
                runOnUiThread(() -> {
                    displayPosts(context);
                });
            } else {
                Log.d("Response", "Did not receive response");
                if (response.exception instanceof ApolloGraphQLException) {
                    Log.e("GraphQL", String.valueOf(((ApolloGraphQLException) response.exception).getErrors().get(0)));
                } else {
                    Log.e("Graphql", response.exception.getMessage());
                }
            }
        });
    }

    private void querySaved() {
        apolloClient.query(new GetAllSavedPostQuery()).enqueue(response -> {
           if(response.data != null && response.data.getAllSavedGamePosts != null) {
               savedGamePosts = response.data.getAllSavedGamePosts;
           } else if(response.data.getAllSavedGamePosts == null) {
               Log.d("SavedPosts", "No saved posts for anyone");
           } else {
               if (response.exception instanceof ApolloGraphQLException) {
                   Log.e("GraphQL", String.valueOf(((ApolloGraphQLException) response.exception).getErrors().get(0)));
               } else {
                   Log.e("Graphql", response.exception.getMessage());
               }
           }
        });
    }

    private void queryPosts(Context context) {
        allPosts.clear();
        apolloClient.query(new GamePostQuery()).enqueue(response -> {
            if (response.data != null) {
                GamePostQuery.Data data = response.data;
                List<GamePostQuery.GetAllGamePost> gamePosts = data.getAllGamePosts;

                for(GamePostQuery.GetAllGamePost post : gamePosts) {
                    String sportName = post.sport != null ? post.sport.sport_name : null;
                    String firstName = post.created_by != null ? post.created_by.first_name : null;
                    String lastName = post.created_by != null ? post.created_by.last_name : null;
                    int maxPlayers = post.max_players;
                    int minPlayers = post.min_players;
                    String playingOn = post.playing_on;
                    String address = post.location != null ? post.location.address : null;
                    String id = post.id;

                    Post postObject = new Post(sportName, firstName, lastName, maxPlayers, minPlayers, playingOn, address, id);

                    allPosts.add(postObject);
                }

                querySaved();

                runOnUiThread(() -> {
                    displayPosts(context); // Call the method to display posts in two columns
                });

                subscribeToPosts(context);
            } else {
                if (response.exception instanceof ApolloGraphQLException) {
                    Log.e("GraphQL", String.valueOf(((ApolloGraphQLException) response.exception).getErrors().get(0)));
                } else {
                    Log.e("Graphql", response.exception.getMessage());
                }
            }
        });
    }

    private void onClickShowPopupWindow() throws ParseException {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_filter_popup, null);
        popupView.setBackgroundColor(getColor(R.color.offwhite));

        // create the popup window
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        int height = constraintLayout.getHeight();
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(getWindow().getDecorView().getRootView(), Gravity.CENTER, 0, 60);

        basketballCheck = popupView.findViewById(R.id.basketballCheck);
        basketballCheck.setChecked(checkedSports.contains("Basketball"));
        basketballCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    checkedSports.add("Basketball");
                } else if(!b && checkedSports.contains("Basketball")) {
                    checkedSports.remove("Basketball");
                }
            }
        });

        pickelballCheck = popupView.findViewById(R.id.pickelballCheck);
        pickelballCheck.setChecked(checkedSports.contains("Pickelball"));
        pickelballCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    checkedSports.add("Pickelball");
                } else if(!b && checkedSports.contains("Pickelball")) {
                    checkedSports.remove("Pickelball");
                }
            }
        });

        footballCheck = popupView.findViewById(R.id.footballCheck);
        footballCheck.setChecked(checkedSports.contains("Football"));
        footballCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    checkedSports.add("Football");
                } else if(!b && checkedSports.contains("Football")) {
                    checkedSports.remove("Football");
                }
            }
        });

        spikeballCheck = popupView.findViewById(R.id.spikeballCheck);
        spikeballCheck.setChecked(checkedSports.contains("Spikeball"));
        spikeballCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    checkedSports.add("Spikeball");
                } else if (!b && checkedSports.contains("Spikeball")) {
                    checkedSports.remove("Spikeball");
                }
            }
        });

        tennisCheck = popupView.findViewById(R.id.tennisCheck);
        tennisCheck.setChecked(checkedSports.contains("Tennis"));
        tennisCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    checkedSports.add("Tennis");
                } else if(!b && checkedSports.contains("Tennis")) {
                    checkedSports.remove("Tennis");
                }
            }
        });

        volleyballCheck = popupView.findViewById(R.id.volleyballCheck);
        volleyballCheck.setChecked(checkedSports.contains("Volleyball"));
        volleyballCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    checkedSports.add("Volleyball");
                } else if(!b && checkedSports.contains("Volleyball")) {
                    checkedSports.remove("Volleyball");
                }
            }
        });

        softballCheck = popupView.findViewById(R.id.softballCheck);
        softballCheck.setChecked(checkedSports.contains("Softball"));
        softballCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    checkedSports.add("Softball");
                } else if(!b && checkedSports.contains("Softball")) {
                    checkedSports.remove("Softball");
                }
            }
        });

        hockeyCheck = popupView.findViewById(R.id.hockeyCheck);
        hockeyCheck.setChecked(checkedSports.contains("Hockey"));
        hockeyCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    checkedSports.add("Hockey");
                } else if(!b && checkedSports.contains("Hockey")) {
                    checkedSports.remove("Hockey");
                }
            }
        });

        CheckBox saved = popupView.findViewById(R.id.savedBtn);
        saved.setChecked(checkedSave);
        saved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkedSave = b;
            }
        });

        Button view = popupView.findViewById(R.id.viewFilter);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                queryPosts(HomeActivity.this);
            }
        });

        Button clear = popupView.findViewById(R.id.clearFilter);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkedSports.clear();
                checkedSave = false;

                basketballCheck.setChecked(false);
                pickelballCheck.setChecked(false);
                footballCheck.setChecked(false);
                spikeballCheck.setChecked(false);
                tennisCheck.setChecked(false);
                volleyballCheck.setChecked(false);
                softballCheck.setChecked(false);
                hockeyCheck.setChecked(false);
                saved.setChecked(false);
            }
        });
    }
    private class Post {
        String sportName, firstName, lastName, playingOn, address, id;
        int max, min;
        private Post(String sportName, String firstName, String lastName, int max, int min, String playingOn, String address, String id) {
            this.sportName = sportName;
            this.firstName = firstName;
            this.lastName = lastName;
            this.max = max;
            this.min = min;
            this.playingOn = playingOn;
            this.address = address;
            this.id = id;
        }
    }
}