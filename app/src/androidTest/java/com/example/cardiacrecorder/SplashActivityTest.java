package com.example.cardiacrecorder;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SplashActivityTest {

    @Rule
    public ActivityScenarioRule<Splash> activityRule = new ActivityScenarioRule<>(Splash.class);

    @Test
    public void splashScreenDisplayed() {
        // Check if the ImageView with id "imageView" is displayed
        Espresso.onView(ViewMatchers.withId(R.id.imageView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
