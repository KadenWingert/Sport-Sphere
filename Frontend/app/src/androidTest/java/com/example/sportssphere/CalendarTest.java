package com.example.sportssphere;

import static org.junit.Assert.assertNotNull;

import android.content.Intent;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CalendarTest {

    private String userId;
    private String password;

    @Before
    public void setUp() {
        userId = "k";
        password = "k";
    }

    @Test
    public void testActivityLaunch() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), CalendarActivity.class);
        intent.putExtra("id", userId);
        intent.putExtra("password", password);

        try (ActivityScenario<CalendarActivity> scenario = ActivityScenario.launch(intent)) {
            scenario.onActivity(activity -> {
                assertNotNull(activity);
            });
        }
    }

    @Test
    public void testBackButtonVisibility() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), CalendarActivity.class);
        intent.putExtra("id", userId);
        intent.putExtra("password", password);

        try (ActivityScenario<CalendarActivity> scenario = ActivityScenario.launch(intent)) {
            Espresso.onView(ViewMatchers.withId(R.id.backToHomeButton))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        }
    }

}
