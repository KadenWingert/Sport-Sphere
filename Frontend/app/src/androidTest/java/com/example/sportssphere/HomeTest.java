package com.example.sportssphere;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class HomeTest {

    @Test
    public void testProfileButtonIsDisplayed() {
        try (ActivityScenario<HomeActivity> activityScenario = ActivityScenario.launch(HomeActivity.class)) {
            Espresso.onView(ViewMatchers.withId(R.id.viewProfileButton))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        }
    }
    @Test
    public void testSettingsButtonIsDisplayed() {
        try (ActivityScenario<HomeActivity> activityScenario = ActivityScenario.launch(HomeActivity.class)) {
            Espresso.onView(ViewMatchers.withId(R.id.settingsButton))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        }
    }
    @Test
    public void testMapButtonIsDisplayed() {
        try (ActivityScenario<HomeActivity> activityScenario = ActivityScenario.launch(HomeActivity.class)) {
            Espresso.onView(ViewMatchers.withId(R.id.mapButton))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        }
    }
}
