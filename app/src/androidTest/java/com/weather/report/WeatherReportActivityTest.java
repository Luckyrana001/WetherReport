package com.weather.report;

import android.content.Intent;
import android.view.View;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.weather.report.helper.Constants;
import com.weather.report.view.WeatherReportActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertNotNull;

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
    public void testSpinnerViewExistOrNot() {
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

    /**
     * Initial values testing.
     */
    @Test
    public void checkCityValueTest() {
        ViewInteraction cityValueTest = onView(withId(R.id.cityValueTv));
        cityValueTest.check(matches(withText("Sydney")));
    }

    @After
    public void tearDown() throws Exception {
    }
}