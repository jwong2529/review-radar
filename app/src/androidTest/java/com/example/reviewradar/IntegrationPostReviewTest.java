package com.example.reviewradar;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class  IntegrationPostReviewTest {

    @Rule
    public ActivityScenarioRule<PostARestaurantReview> activityScenarioRule = new ActivityScenarioRule<>(PostARestaurantReview.class);

    @Test
    public void postReviewProcess() {
        onView(withId(R.id.postReviewResTitle)).perform(typeText("Sushi Place"));

        onView(withId(R.id.postReviewDescription)).perform(typeText("Loved the sushi!"));

        onView(withId(R.id.postReviewPageButton)).perform(click());
    }
}
