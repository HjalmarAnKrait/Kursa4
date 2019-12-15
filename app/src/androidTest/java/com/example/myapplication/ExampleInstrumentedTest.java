package com.example.myapplication;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.myapplication.Activities.AuthorizationActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext()
    {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.myapplication", appContext.getPackageName());
    }

    @Test
    public void testAuth()
    {
        //AuthorizationActivity authorizationActivity = new AuthorizationActivity(InstrumentationRegistry.getTargetContext());
       // assertTrue(authorizationActivity.auth("login", "password"));
    }
}
