package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HelloActivity extends AppCompatActivity {


    Button backBtn;
    TextView display;

    Button counterView;


    
    /** 
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        display = findViewById(R.id.helloView);
        backBtn = findViewById(R.id.toHome);
        counterView = findViewById(R.id.toCounter);

        Intent i = getIntent();
        String text = i.getStringExtra ( "text");
         if(text.isEmpty()){
            display.setText("Hello World!");
         } else {
            display.setText("Hello " + text + "!");
         }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(HelloActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

         counterView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v)
             {
                 Intent intent = new Intent(HelloActivity.this, CounterActivity.class);
                 startActivity(intent);
             }
         });
    }

}
