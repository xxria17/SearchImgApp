package com.dohyun.searchimgapp.fragments

import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
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
class DetailFragmentTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testImgIsVisible() {
        Espresso.onView(withId(R.id.search_edit)).perform(typeText("jeju"), closeSoftKeyboard())
        SystemClock.sleep(2000)
        Espresso.onView(withId(R.id.result_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        Espresso.onView(withId(R.id.detail_img)).check(matches(isDisplayed()))
    }

    @Test
    fun testCheckDateTime() {
        Espresso.onView(withId(R.id.search_edit)).perform(typeText("jeju"), closeSoftKeyboard())
        SystemClock.sleep(2000)
        Espresso.onView(withId(R.id.result_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        Espresso.onView(withId(R.id.datetime_tv)).check(matches(isDisplayed()))
    }
}