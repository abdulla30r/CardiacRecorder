package com.example.cardiacrecorder;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

@RunWith(AndroidJUnit4.class)
public class EditActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);
    public ActivityScenarioRule<AddMeasurement> addMeasurementRule = new ActivityScenarioRule<>(AddMeasurement.class);


    @Test
    public void testEditMeasurement() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(ViewMatchers.withId(R.id.btnEdit))
                .perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(ViewMatchers.withId(R.id.etComment))
                .check(matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.etHeartRate))
                .check(matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.etDiastolic))
                .check(matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.etSystolic))
                .check(matches(ViewMatchers.isDisplayed()));



        Espresso.onView(ViewMatchers.withId(R.id.etSystolic))
                .perform(ViewActions.clearText(), ViewActions.typeText("100"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.etDiastolic))
                .perform(ViewActions.clearText(), ViewActions.typeText("90"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.etHeartRate))
                .perform(ViewActions.clearText(), ViewActions.typeText("72"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.etComment))
                .perform(ViewActions.clearText(), ViewActions.typeText("Good"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.btnSave))
                .perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(ViewMatchers.withId(R.id.txt))
                .check(matches(ViewMatchers.isDisplayed()));

    }
}
