package com.dohyun.searchimgapp.fragments

import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.dohyun.searchimgapp.R
import com.dohyun.searchimgapp.view.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class SearchFragmentTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
        = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testRecyclerIsVisible() {
        Espresso.onView(withId(R.id.search_edit)).perform(typeText("seoul"), closeSoftKeyboard())
        SystemClock.sleep(3000)
        Espresso.onView(withId(R.id.result_list)).check(matches(isDisplayed()))
    }

    @Test
    fun testScroll() {
        Espresso.onView(withId(R.id.search_edit)).perform(typeText("seoul"), closeSoftKeyboard())
        SystemClock.sleep(3000)
        Espresso.onView(withId(R.id.result_list)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(30))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testEmptyResult() {
        Espresso.onView(withId(R.id.search_edit)).perform(typeText(" "), closeSoftKeyboard())
        SystemClock.sleep(3000)
        Espresso.onView(withId(R.id.result_list)).check(matches(hasChildCount(0)))
    }

    @Test
    fun testWrongQuery(){
        Espresso.onView(withId(R.id.search_edit)).perform(typeText("!!!!!"), closeSoftKeyboard())
        SystemClock.sleep(3000)
        Espresso.onView(withId(R.id.result_list)).check(matches(hasChildCount(0)))
    }

    @Test
    fun testChangeQuery() {
        Espresso.onView(withId(R.id.search_edit)).perform(typeText("seoul"), closeSoftKeyboard())
        SystemClock.sleep(1000)
        Espresso.onView(withId(R.id.search_edit)).perform(clearText())
        Espresso.onView(withId(R.id.search_edit)).perform(typeText("jeju"), closeSoftKeyboard())
        SystemClock.sleep(3000)
        Espresso.onView(withId(R.id.result_list)).check(matches(isDisplayed()))
    }

    @Test
    fun testSlowInput() {
        Espresso.onView(withId(R.id.search_edit)).perform(typeText("j"))
        SystemClock.sleep(1000)
        Espresso.onView(withId(R.id.search_edit)).perform(typeText("e"))
        SystemClock.sleep(1000)
        Espresso.onView(withId(R.id.search_edit)).perform(typeText("j"))
        SystemClock.sleep(1000)
        Espresso.onView(withId(R.id.search_edit)).perform(typeText("u"), closeSoftKeyboard())
        SystemClock.sleep(3000)
        Espresso.onView(withId(R.id.result_list)).check(matches(hasChildCount(15)))
    }
}