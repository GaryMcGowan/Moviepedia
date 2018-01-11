package com.garymcgowan.moviepedia;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.garymcgowan.moviepedia.view.search.MovieListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MovieListInstrumentedTest {

    @Rule
    public ActivityTestRule<MovieListActivity> mActivityRule =
            new ActivityTestRule<>(MovieListActivity.class);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.garymcgowan.moviepedia", appContext.getPackageName());
    }

    @Test
    public void testMovieNotFound() {
        // Click on the search icon
        onView(withId(R.id.action_search)).perform(click());

        // Type the text in the search field and submit the query
        onView(isAssignableFrom(EditText.class)).perform(typeText("Movie title that is not real"), pressImeActionButton());

        // Checks
        onView(isAssignableFrom(EditText.class)).check(matches(withText("Movie title that is not real")));
        onView(withId(R.id.emptyTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerView)).check(matches(not(isDisplayed())));
    }

    @Test
    public void testMovieFound() {
        // Click on the search icon
        onView(withId(R.id.action_search)).perform(click());

        // Type the text in the search field and submit the query
        onView(isAssignableFrom(EditText.class)).perform(typeText("Taken"), pressImeActionButton());

        // Checks
        onView(isAssignableFrom(EditText.class)).check(matches(withText("Taken")));
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));

        onView(allOf(withId(R.id.titleTextView), withText("Taken (2008)"))).check(matches(withText("Taken (2008)")));
    }
}
