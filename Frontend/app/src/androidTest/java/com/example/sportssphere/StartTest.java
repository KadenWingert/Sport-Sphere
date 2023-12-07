package com.example.sportssphere;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.ext.junit.runners.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class StartTest {

    @Test
    public void testEmailAndPasswordInputFieldsDisplayed() {
        try (ActivityScenario<StartActivity> activityScenario = ActivityScenario.launch(StartActivity.class)) {
            Espresso.onView(ViewMatchers.withId(R.id.emailAddress)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
            Espresso.onView(ViewMatchers.withId(R.id.password)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        }
    }
    @Test
    public void testSignUpTextNavigation() {
        try (ActivityScenario<StartActivity> activityScenario = ActivityScenario.launch(StartActivity.class)) {
            Espresso.onView(ViewMatchers.withId(R.id.signUpText)).perform(ViewActions.click());
        }
    }
}
