package com.weather.report;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.weather.report.helper.Constants;
import com.weather.report.view.WeatherReportActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class WeatherReportActivityTest {

    @Rule
    public ActivityTestRule<WeatherReportActivity> mActivityTestRule = new ActivityTestRule<WeatherReportActivity>(WeatherReportActivity.class);

    private WeatherReportActivity mActivity = null;
    private MockWebServer server;
    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();

        InstrumentationRegistry.getInstrumentation();
        server = new MockWebServer();
        server.start();
        Constants.BASE_URL = server.url("/").toString();
    }

    @Test
    public void testSpinnerViewExistOrNot(){
      View view = mActivity.findViewById(R.id.citySpinner);
      assertNotNull(view);
    }



    @Test
    public void testRandomDataIsShown() throws Exception {
        String fileName = "weather_report_200_response.json";
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), fileName)));

        Intent intent = new Intent();
        mActivityTestRule.launchActivity(intent);

        onView(withId(R.id.citySpinner)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

    }



    @Test
    public void testRandomDataRetrieval() throws Exception {

        MockAdapterTest quoteOfTheDayMockAdapterTest = new MockAdapterTest();
        quoteOfTheDayMockAdapterTest.testRandomDataRetrieval();
    }
    @Test
    public void testRetryButtonShowsWhenError() throws Exception {
        String fileName = "weather_report_401_not_found.json";

        server.enqueue(new MockResponse()
                .setResponseCode(401)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), fileName)));

        Intent intent = new Intent();
        mActivityTestRule.launchActivity(intent);

    }


    @After
    public void tearDown() throws Exception {
    }
}