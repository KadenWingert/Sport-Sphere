package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button toCounterBtn;
    TextView pressCountText;
    static int counter = 0;  // static to keep the value across instances

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toCounterBtn = findViewById(R.id.toCounterBtn);
        pressCountText = findViewById(R.id.pressCountText);

        // Initialize with current counter value
        pressCountText.setText("The button has been pressed " + counter + " times");

        toCounterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CounterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update counter value when coming back from CounterActivity
        pressCountText.setText("The button has been pressed " + counter + " times");
    }
}
