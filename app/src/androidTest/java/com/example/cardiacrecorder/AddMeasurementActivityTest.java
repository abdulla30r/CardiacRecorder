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
public class AddMeasurementActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);
    public ActivityScenarioRule<AddMeasurement> addMeasurementRule = new ActivityScenarioRule<>(AddMeasurement.class);


    @Test
    public void testAddMeasurement() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(ViewMatchers.withId(R.id.btnAdd))
                .perform(click());

        // Enter values for systolic, diastolic, heart rate, and comment fields
        Espresso.onView(ViewMatchers.withId(R.id.etSystolic))
                .perform(typeText("120"), closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.etDiastolic))
                .perform(typeText("80"), closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.etHeartRate))
                .perform(typeText("75"), closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.etComment))
                .perform(typeText("Sample comment"), closeSoftKeyboard());

        // Click the save button
        Espresso.onView(ViewMatchers.withId(R.id.btnSave))
                .perform(click());


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify that the activity navigates back to MainActivity
        Espresso.onView(ViewMatchers.withId(R.id.txt))
                .check(matches(ViewMatchers.isDisplayed()));
    }
}