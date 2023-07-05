package com.example.cardiacrecorder;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;


@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<Login> activityRule = new ActivityScenarioRule<>(Login.class);
    public ActivityScenarioRule<MainActivity> mainActivityRule = new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void testLoginFlow() {

        // Check if the necessary elements are displayed on the screen
        Espresso.onView(ViewMatchers.withId(R.id.inputEmail))
                .check(matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.inputPassword))
                .check(matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.btnLogin))
                .check(matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.gotoRegister))
                .check(matches(ViewMatchers.isDisplayed()));

        // Enter email and password
        Espresso.onView(ViewMatchers.withId(R.id.inputEmail))
                .perform(typeText("nasib@gmail.com"), closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.inputPassword))
                .perform(typeText("aadtjjag"), closeSoftKeyboard());

        // Perform login button click
        Espresso.onView(ViewMatchers.withId(R.id.btnLogin))
                .perform(click());


        try {
            Thread.sleep(4000); // Adjust the delay as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        // Verify that the login is successful and navigates to MainActivity
        Espresso.onView(ViewMatchers.withId(R.id.txt))
                .check(matches(ViewMatchers.isDisplayed()));



        // You can add more assertions or verifications here for MainActivity if needed
    }
}
