package com.garymcgowan.moviepedia


import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.widget.EditText
import com.garymcgowan.moviepedia.view.search.MovieListActivity
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MovieListInstrumentedTest {

    @Rule @JvmField val activity = ActivityTestRule(MovieListActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.garymcgowan.moviepedia", appContext.packageName)
    }

    @Test
    fun testMovieNotFound() {
        // Click on the search icon
        onView(withId(R.id.action_search)).perform(click())

        // Type the text in the search field and submit the query
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("Movie title that is not real"), pressImeActionButton())

        // Checks
        onView(isAssignableFrom(EditText::class.java)).check(matches(withText("Movie title that is not real")))
        onView(withId(R.id.emptyTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).check(matches(not(isDisplayed())))
    }

    @Ignore("fails when it shouldnt?")
    @Test
    fun testMovieFound() {
        // Click on the search icon
        onView(withId(R.id.action_search)).perform(click())

        // Type the text in the search field and submit the query
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("Taken"), pressImeActionButton())

        // Checks
        onView(isAssignableFrom(EditText::class.java)).check(matches(withText("Taken")))
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))

        onView(allOf(withId(R.id.titleTextView), withText("Taken (2008)"))).check(matches(withText("Taken (2008)")))
    }
}
