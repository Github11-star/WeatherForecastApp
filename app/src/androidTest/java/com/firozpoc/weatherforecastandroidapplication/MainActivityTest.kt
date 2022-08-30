package com.firozpoc.weatherforecastandroidapplication

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{

    private val activityScenario = ActivityScenario.launch(MainActivity::class.java)

    @Before
    fun setUp() {


    }

    @Test
    fun mainActivityTest(){

        onView(withId(R.id.tvTemperature)).check(matches(isDisplayed()))
        onView(withId(R.id.tvLatitude)).check(matches(isDisplayed()))
        onView(withId(R.id.tvLongitude)).check(matches(isDisplayed()))
        onView(withId(R.id.windDirection)).check(matches(isDisplayed()))
        onView(withId(R.id.windGust)).check(matches(isDisplayed()))
    }

    @After
    fun tearDown(){
        activityScenario.close()
    }
}