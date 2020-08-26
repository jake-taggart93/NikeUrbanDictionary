package com.example.urbandictionary

import android.os.Handler
import android.os.Looper
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.urbandictionary.view.SearchLandingActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchActivityInstrumentedTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(SearchLandingActivity::class.java)
    private lateinit var handler: Handler
    private lateinit var looper: Looper

    @Before
    fun setup() {
        looper = InstrumentationRegistry.getInstrumentation().targetContext.mainLooper
        handler = Handler(looper, null)
    }

    @Test
    fun nonEmptyDefinitions() {
        onView(withId(R.id.tieEntry)).perform(typeText("hey"))
        onView(withId(R.id.btnSearch)).perform(click())
        handler.postDelayed({
            onView(withId(R.id.rvDefinitions)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
            onView(withId(R.id.tvSortDown)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
            onView(withId(R.id.tvSortUp)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        }, 7000)
    }

    @Test
    fun emptyDefinitions() {
        onView(withId(R.id.tieEntry)).perform(typeText("INVALIDxxTERM"))
        onView(withId(R.id.btnSearch)).perform(click())
        handler.postDelayed({
            onView(withId(R.id.tvSortDown)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
            onView(withId(R.id.tvSortUp)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
        }, 7000)
    }
}