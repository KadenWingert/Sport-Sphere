
package com.example.sportssphere;

import android.content.Context;
import android.content.Intent;
/** @brief The android.os. bundle */
import android.media.MediaPlayer;
import android.os.Bundle;
/** @brief The android.util. log */
import android.util.Log;
/** @brief The android.view. view */
import android.view.View;
/** @brief The android.widget. button */
import android.widget.Button;
/** @brief The android.widget. edit text */
import android.widget.EditText;
/** @brief The android.widget. image view */
import android.widget.ImageView;
/** @brief The android.widget. text view */
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/** @brief The androidx.annotation. non null */
/** @brief The androidx.appcompat.app. application compatible activity */
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import com.apollographql.apollo3.exception.ApolloGraphQLException;
import com.apollographql.apollo3.runtime.java.ApolloClient;


/**********************************************************************************************/

/**
 * @class StartActivity
 * @brief A start activity.
 * @author Arie
 * @date 10/20/2023
 **************************************************************************************************/

public class StartActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    ScrollView scrollBackground;

    /**
     * @brief The button control
     */
    Button button;
    /**
     * @brief The button 2 control
     */
    TextView signUpText;

    /**
     * @brief The edit text
     */
    EditText editText;
    /**
     * @brief The second edit text
     */
    EditText editText2;
    /**
     * @brief The logo
     */
    ImageView logo;
    /**
     * @brief The sign in error
     */
    TextView signInError;

    /**
     * @brief The apollo client
     */
    ApolloClient apolloClient;

    /**********************************************************************************************/
    /**
     * @paramsavedInstanceState .
     * @fn    @Override protected void onCreate(Bundle savedInstanceState)
     * @brief Executes the 'create' action
     * @author Arie
     * @date 10/20/2023
     **************************************************************************************************/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
    private void introTheme(){
        int resId = R.raw.sc_top_ten;
        // Create and start MediaPlayer
        mediaPlayer = MediaPlayer.create(this, resId);
        mediaPlayer.setVolume(1.0f, 1.0f);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }

    private void loginLogic(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apolloClient = ApolloClientProvider.setupApolloClient();
                String email = String.valueOf(editText.getText()).trim();
                String password = String.valueOf(editText2.getText()).trim();
                if (!email.isEmpty()) {
                    apolloClient.query(new UserEmailQuery(email)).enqueue(response -> {
                        if (response.data != null && response.data.getUserByEmail != null) {
                            String responseEmail = response.data.getUserByEmail.email;
                            String responsePassword = response.data.getUserByEmail.password;
                            Log.d("responseemail", responseEmail);
                            if (responseEmail.equals(email) && responsePassword.equals(password)) {
                                String id = response.data.getUserByEmail.id;
                                Intent intent = new Intent(StartActivity.this, HomeActivity.class);
                                intent.putExtra("id", id);
                                intent.putExtra("password", password);
                                startActivity(intent);
                            } else {
                                runOnUiThread(() -> { Toast.makeText(getApplicationContext(), "Incorrect password", Toast.LENGTH_LONG).show(); });
                                editText2.setText("");
                            }
                        } else if(response.data.getUserByEmail == null) {
                            try {
                                runOnUiThread(() -> {
                                    Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_LONG).show();
                                });
                                editText.setText("");
                            }
                            catch (Exception e)
                            {
                                Log.e("Error", e.getMessage(), e);
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
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        scrollBackground = findViewById(R.id.scrollBackground);
        scrollBackground.setBackgroundColor(getColor(R.color.background));
        button = findViewById(R.id.button);
        signUpText = findViewById(R.id.signUpText);
        editText = findViewById(R.id.emailAddress);
        editText2 = findViewById(R.id.password);
        signInError = findViewById(R.id.textView);
        Context context = this;
        introTheme();


        loginLogic();

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
