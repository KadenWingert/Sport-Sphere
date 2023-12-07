package com.example.sportssphere;

import static com.example.sportssphere.type.Permission.ADMIN;
import static com.example.sportssphere.type.Permission.USER;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;

import com.apollographql.apollo3.exception.ApolloGraphQLException;
import com.apollographql.apollo3.runtime.java.ApolloClient;

import java.util.ArrayList;
import java.util.List;

public class ViewPostActivity extends AppCompatActivity {
    CardView cardView, scrollCommentCardView;
    ScrollView relativeLayout;
    Button backToHome, saveButton;
    TextView comment, sportName,hostedBy,lookingfor,dateOfEvent,address;
    LinearLayout postDetails;
    RelativeLayout buttonsLayout;
    ScrollView commentsScroll;
    String userID, gameID, firstName, lastName, userFirstName, userLastName, userPassword, userEmail, creatorEmail, creatorPassword, savedID;
    ApolloClient apolloClient;
    ArrayList<String> commentMessages = new ArrayList<>();
    Button editButton,deleteButton, leaveButton, joinButton;
    boolean admin = false;
    boolean isSaved;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_view_post);
        Intent i = getIntent();
        userID = i.getStringExtra("id");
        gameID = i.getStringExtra("gameID");
        userPassword = i.getStringExtra("password");
        Context context = this;
        setInitialButtons();

        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewPostActivity.this, HomeActivity.class);
                intent.putExtra("id", userID);
                intent.putExtra("password", userPassword);
                startActivity(intent);
            }
        });

        Button postCommentButton = findViewById(R.id.postComment);
        Drawable commentDrawable = getResources().getDrawable(R.drawable.send_comment);
        if (commentDrawable != null) {
            commentDrawable.setBounds(0, 0, (int) (commentDrawable.getIntrinsicWidth() * 1.75), (int) (commentDrawable.getIntrinsicHeight() * 1.75));
            postCommentButton.setCompoundDrawables(commentDrawable, null, null, null);
        }



        EditText commentEditText = findViewById(R.id.commentText);


        postCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentText = commentEditText.getText().toString().trim();
                if (!commentText.isEmpty()) {
                    // Now it calls the method you've already implemented
                    addCommentToPost(gameID, commentText);
                }
                //Clear entry in text field -- follows most UI/UX designs
                commentEditText.setText("");
            }
        });

        apolloClient = ApolloClientProvider.setupApolloClient();

        apolloClient.query(new SingleGamePostQuery(gameID)).enqueue(response -> {
            if (response.data != null) {
                SingleGamePostQuery.GetGamePostById post = response.data.getGamePostById;
                String sportName = post.sport != null ? post.sport.sport_name : null;
                firstName = post.created_by != null ? post.created_by.first_name : null;
                lastName = post.created_by != null ? post.created_by.last_name : null;
                int maxPlayers = post.max_players;
                int minPlayers = post.min_players;
                String playingOn = post.playing_on;
                String address = post.location != null ? post.location.address : null;
                creatorEmail = post.created_by.email;
                creatorPassword = post.created_by.password;


                runOnUiThread(() -> {
                    this.sportName.setText(sportName);
                    switch (sportName.toString()) {
                        case "Basketball":
                            cardView.setBackgroundColor(getColor(R.color.basketball));
                            scrollCommentCardView.setBackgroundColor(getColor(R.color.basketball));
                            break;
                        case "Pickleball":
                            cardView.setBackgroundColor(getColor(R.color.pickleball));
                            scrollCommentCardView.setBackgroundColor(getColor(R.color.pickleball));
                            break;
                        case "Football":
                            cardView.setBackgroundColor(getColor(R.color.football));
                            scrollCommentCardView.setBackgroundColor(getColor(R.color.football));
                            break;
                        case "Spikeball":
                            cardView.setBackgroundColor(getColor(R.color.spikeball));
                            scrollCommentCardView.setBackgroundColor(getColor(R.color.spikeball));
                            break;
                        case "Tennis":
                            cardView.setBackgroundColor(getColor(R.color.tennis));
                            scrollCommentCardView.setBackgroundColor(getColor(R.color.tennis));
                            break;
                        case "Volleyball":
                            cardView.setBackgroundColor(getColor(R.color.volleyball));
                            scrollCommentCardView.setBackgroundColor(getColor(R.color.volleyball));
                            break;
                        case "Softball":
                            cardView.setBackgroundColor(getColor(R.color.softball));
                            scrollCommentCardView.setBackgroundColor(getColor(R.color.softball));
                            break;
                        case "Hockey":
                            cardView.setBackgroundColor(getColor(R.color.hockey));
                            scrollCommentCardView.setBackgroundColor(getColor(R.color.hockey));
                            break;
                    }
                    hostedBy.setText("Hosted by: " + firstName + " " + lastName);
                    lookingfor.setText("Looking for " + minPlayers + "-" + maxPlayers + " players");
                    dateOfEvent.setText("Date: " + playingOn);
                    if(address == null){
                        this.address.setText("");
                    }
                    else {
                        this.address.setText("Address: " + address);
                    }
                });

                fetchUserDetails(context);

            } else {
                if (response.exception instanceof ApolloGraphQLException) {
                    Log.e("GraphQL", String.valueOf(((ApolloGraphQLException) response.exception).getErrors().get(0)));
                } else {
                    Log.e("Graphql", response.exception.getMessage());
                }
            }
        });

        apolloClient.query(new GetAllSavedPostQuery()).enqueue(response1 -> {
            if(response1.data != null && response1.data.getAllSavedGamePosts != null) {
                List<GetAllSavedPostQuery.GetAllSavedGamePost> savedGamePosts = response1.data.getAllSavedGamePosts;
                for(GetAllSavedPostQuery.GetAllSavedGamePost savedGamePost : savedGamePosts) {
                    String thisID = String.valueOf(savedGamePost.gamePost);
                    if(thisID.equals(gameID) && savedGamePost.user.id.equals(userID)) {
                        isSaved = true;
                        savedID = savedGamePost.id;
                        break;
                    } else {
                        isSaved = false;
                    }
                }
                saveButton.setBackgroundResource((isSaved) ? R.drawable.ic_save_filled : R.drawable.ic_save_empty);
                Log.d("SavedPost", String.valueOf(isSaved));
            } else {
                if (response1.exception instanceof ApolloGraphQLException) {
                    Log.e("GraphQL", String.valueOf(((ApolloGraphQLException) response1.exception).getErrors().get(0)));
                } else {
                    Log.e("Graphql", response1.exception.getMessage());
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSaved) {
                    Log.d("SavedPostID", savedID);
                    saveButton.setBackgroundResource(R.drawable.ic_save_empty);
                    apolloClient.mutation(new UnSavePostMutation(savedID)).enqueue(response -> {
                        if(response.data != null) {
                            Log.d("UnSave", "Post " + gameID + " has been unsaved");
                        } else {
                            if (response.exception instanceof ApolloGraphQLException) {
                                Log.e("GraphQL", String.valueOf(((ApolloGraphQLException) response.exception).getErrors().get(0)));
                            } else {
                                Log.e("Graphql", response.exception.getMessage());
                            }
                        }
                    });
                } else {
                    saveButton.setBackgroundResource(R.drawable.ic_save_filled);
                    apolloClient.mutation(new SavePostMutation(userID, gameID)).enqueue(response -> {
                        if(response.data != null) {
                            savedID = response.data.createSavedGamePost.id;
                        } else {
                            if (response.exception instanceof ApolloGraphQLException) {
                                Log.e("GraphQL", String.valueOf(((ApolloGraphQLException) response.exception).getErrors().get(0)));
                            } else {
                                Log.e("Graphql", response.exception.getMessage());
                            }
                        }
                    });
                }
                isSaved = !isSaved;
            }
        });
    }

    private void setInitialButtons(){
        cardView = findViewById(R.id.viewPostCardView);
        cardView.setCardElevation(20f);
        cardView.setRadius(20f);
        scrollCommentCardView = findViewById(R.id.scrollCommentCardView);
        scrollCommentCardView.setCardElevation(20f);
        scrollCommentCardView.setRadius(20f);
        relativeLayout = findViewById(R.id.backgroundLayout);
        relativeLayout.setBackgroundColor(getColor(R.color.background));
        backToHome = findViewById(R.id.btnBackToHome);
        comment = findViewById(R.id.comment);
        postDetails = findViewById(R.id.postDetails);
        buttonsLayout = findViewById(R.id.btnsLayout);
        commentsScroll = findViewById(R.id.commentsScroll);
        sportName = findViewById(R.id.sportName);
        hostedBy =findViewById(R.id.hostedBy);
        lookingfor=findViewById(R.id.lookingfor);
        dateOfEvent = findViewById(R.id.dateOfEvent);
        address= findViewById(R.id.address);

        editButton = findViewById(R.id.editButton);
        editButton.setBackgroundTintList(getResources().getColorStateList(R.color.editButton));
        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setBackgroundTintList(getResources().getColorStateList(R.color.deleteButton));
        leaveButton = findViewById(R.id.leaveButton);
        leaveButton.setBackgroundTintList(getResources().getColorStateList(R.color.leaveButton));
        joinButton = findViewById(R.id.joinButton);
        joinButton.setBackgroundTintList(getResources().getColorStateList(R.color.joinButton));

        saveButton = findViewById(R.id.savePost);
    }

    private boolean isCreator() {
        Log.d("IsCreator", String.valueOf((userFirstName.equals(firstName) && userLastName.equals(lastName))) + userLastName + userFirstName + lastName + firstName);
        return (userFirstName.equals(firstName) && userLastName.equals(lastName));
    }

    private void fetchUserDetails(Context context) {
        apolloClient.query(new UserIDQuery(userID)).enqueue(response -> {
            userFirstName = response.data.getUserById.first_name;
            userLastName = response.data.getUserById.last_name;
            userEmail = response.data.getUserById.email;
            apolloClient.query(new UserPermissionsQuery(userEmail, userPassword)).enqueue(response2 -> {
                if (response2.data != null) {
                    Log.d("Permissions", response2.data.getUserRole.toString());
                    if(response2.data.getUserRole == ADMIN) {
                        Log.d("Permissions", "User is admin");
                        admin = true;
                    }
                } else if(response2.data.getUserRole == null) {
                    Log.d("Perms", "No permissions for user: " + userEmail);
                }
                else {
                    if (response2.exception instanceof ApolloGraphQLException) {
                        Log.e("GraphQL", String.valueOf(((ApolloGraphQLException) response2.exception).getErrors().get(0)));
                    } else {
                        Log.e("Graphql", response2.exception.getMessage());
                    }
                }
            });
            usersPlayingGame(context);
        });
    }

    //post comment
    // Function to call the mutation to add a comment
    private void addCommentToPost(String gamePostId, String commentMessage) {
        apolloClient.mutation(new CreateCommentMutation(gamePostId, commentMessage,userID)).enqueue(response -> {
            if (response.data != null) {
                Log.d("CreateComment", "Comment added: " + response.data.createComment.comment_details.message);
            } else {
                if (response.exception instanceof ApolloGraphQLException) {
                    Log.e("GraphQL", String.valueOf(((ApolloGraphQLException) response.exception).getErrors().get(0)));
                } else {
                    Log.e("Graphql", response.exception.getMessage());
                }
            }
        });
    }

    private void usersPlayingGame(Context context) {

        apolloClient.query(new UsersPlayingQuery(gameID)).enqueue(response -> {
            boolean isInGame = false;
            if (response.data != null) {
                List<UsersPlayingQuery.GetUsersPlaying> usersPlaying = response.data.getUsersPlaying;
                if(isAdmin() && !isCreator()) {
                    runOnUiThread(() -> { deleteButton.setVisibility(View.VISIBLE); });

                    deleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            apolloClient.mutation(new DeletePostMutation(gameID, creatorEmail, creatorPassword)).enqueue(response -> {
                                Intent intent = new Intent(ViewPostActivity.this, HomeActivity.class);
                                intent.putExtra("id", userID);
                                intent.putExtra("password", userPassword);
                                startActivity(intent);
                            });
                        }
                    });
                }
                if (isCreator()) {
                    runOnUiThread(() -> {
                        joinButton.setVisibility(View.INVISIBLE);
                        leaveButton.setVisibility(View.INVISIBLE);
                        editButton.setVisibility(View.VISIBLE);
                        deleteButton.setVisibility(View.VISIBLE);
                    });

                    editButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(ViewPostActivity.this, EditPostActivity.class);
                            intent.putExtra("gameID", gameID);
                            intent.putExtra("id", userID);
                            intent.putExtra("password", userPassword);
                            startActivity(intent);
                        }
                    });

                    deleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            apolloClient.mutation(new DeletePostMutation(gameID, creatorEmail, creatorPassword)).enqueue(response -> {
                                Intent intent = new Intent(ViewPostActivity.this, HomeActivity.class);
                                intent.putExtra("id", userID);
                                intent.putExtra("password", userPassword);
                                startActivity(intent);
                            });
                        }
                    });
                } else {
                    for (UsersPlayingQuery.GetUsersPlaying user : usersPlaying) {
                        if (user.id.equals(userID)) {
                            isInGame = true;
                            break;
                        }
                    }
                    if (isInGame) {
                        runOnUiThread(() -> {
                            joinButton.setVisibility(View.INVISIBLE);
                            leaveButton.setVisibility(View.VISIBLE);
                            editButton.setVisibility(View.INVISIBLE);
                        });

                        leaveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                leaveGame(context);
                                Intent intent = getIntent();
                                intent.putExtra("id", userID);
                                intent.putExtra("password", userPassword);
                                finish();
                                startActivity(intent);
                            }
                        });
                    } else {
                        runOnUiThread(() -> {
                            joinButton.setVisibility(View.VISIBLE);
                            leaveButton.setVisibility(View.INVISIBLE);
                            editButton.setVisibility(View.INVISIBLE);
                        });

                        joinButton.setBackgroundResource(R.drawable.rounded_button_background); // Replace with your drawable
                        joinButton.setTextColor(Color.WHITE);


                        joinButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                joinGame(context);
                                Intent intent = getIntent();
                                intent.putExtra("id", userID);
                                intent.putExtra("password", userPassword);
                                finish();
                                startActivity(intent);
                            }
                        });
                    }
                }
                queryComments(context);
            } else {
                if (response.exception instanceof ApolloGraphQLException) {
                    Log.e("GraphQL", String.valueOf(((ApolloGraphQLException) response.exception).getErrors().get(0)));
                } else {
                    Log.e("Graphql", response.exception.getMessage());
                }
            }
        });
    }

    private void queryComments(Context context) {
        apolloClient.query(new GetAllCommentsQuery()).enqueue(response -> {
            if (response.data != null) {
                List<GetAllCommentsQuery.GetAllComment> comments = response.data.getAllComments;
                for (GetAllCommentsQuery.GetAllComment comment : comments) {
                    if (comment.gamePost.id.equals(gameID)) {
                        String message = comment.comment_details.message.toString();
                        String userName = comment.user.first_name + " " + comment.user.last_name;

                        commentMessages.add(message + " (" + userName + ")");
                    }
                }
                runOnUiThread(() -> {
                    addCommentsToUI(context);
                });

                subscribeToComments(context, gameID);
            } else {
                if (response.exception instanceof ApolloGraphQLException) {
                    Log.e("GraphQL", String.valueOf(((ApolloGraphQLException) response.exception).getErrors().get(0)));
                } else {
                    Log.e("Graphql", response.exception.getMessage());
                }
            }
        });
    }

    private void leaveGame(Context context) {
        apolloClient.mutation(new LeavePostMutation(gameID, userID, userEmail, userPassword)).enqueue(response -> {
            if (response.data != null) {
//                buttonsLayout.removeAllViews();

                usersPlayingGame(context);

                runOnUiThread(() -> {
                    joinButton.setVisibility(View.VISIBLE);
                    leaveButton.setVisibility(View.INVISIBLE);
                    editButton.setVisibility(View.INVISIBLE);
                    deleteButton.setVisibility(View.INVISIBLE);
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

    private void joinGame(Context context) {
        apolloClient.mutation(new JoinGameMutation(gameID, userID, userEmail, userPassword)).enqueue(response -> {
            if (response.data != null) {
//                buttonsLayout.removeAllViews();
                usersPlayingGame(context);
                runOnUiThread(() -> {
                    joinButton.setVisibility(View.INVISIBLE);
                    leaveButton.setVisibility(View.VISIBLE);
                    editButton.setVisibility(View.INVISIBLE);
                    deleteButton.setVisibility(View.INVISIBLE);
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


    private void subscribeToComments(Context context, String gamePostId) {
        apolloClient.subscription(new OnCommentAddedSubscription()).enqueue(response -> {
            if (response.data != null) {

                String userName = response.data.subComments.user.first_name + " " + response.data.subComments.user.last_name;
                commentMessages.add(response.data.subComments.comment_details.message + " (" + userName + ")");
                runOnUiThread(() -> {
                    addCommentsToUI(context);
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

    private void addCommentsToUI(Context context) {
        if (!commentMessages.isEmpty()) {
            commentsScroll.removeAllViews();
            LinearLayout commentContainer = new LinearLayout(context);
            commentContainer.setOrientation(LinearLayout.VERTICAL);

            for (String comment : commentMessages) {
                TextView textView = new TextView(context);
                textView.setTextSize(20);
                textView.setText(comment);
                textView.setPadding(25,0,10,0);
                commentContainer.addView(textView);
            }

            commentsScroll.addView(commentContainer);
        }
    }

    private boolean isAdmin() {
        Log.d("Admin", admin ? "true": "false");
        return admin;
    }
}