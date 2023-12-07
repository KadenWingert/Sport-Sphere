package com.example.sportssphere;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.ext.junit.runners.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class SignUpTest {
    @Test
    public void testInputFieldsDisplayed() {
        try (ActivityScenario<SignUpActivity> activityScenario = ActivityScenario.launch(SignUpActivity.class)) {
            Espresso.onView(ViewMatchers.withId(R.id.firstNameEditText)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
            Espresso.onView(ViewMatchers.withId(R.id.lastNameEditText)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
            Espresso.onView(ViewMatchers.withId(R.id.emailEditText)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
            Espresso.onView(ViewMatchers.withId(R.id.passwordEditText)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
            Espresso.onView(ViewMatchers.withId(R.id.confirmPasswordEditText)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
            Espresso.onView(ViewMatchers.withId(R.id.addressInput)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        }
    }
}