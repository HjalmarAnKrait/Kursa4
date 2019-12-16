package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;


import com.example.myapplication.Activities.AuthorizationActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest extends ActivityInstrumentationTestCase2<AuthorizationActivity> {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    @Before
    public void setUp() throws Exception
    {
        try
        {
            databaseHelper.updateDataBase();
        }
        catch (IOException e)
        {
            throw new Error("UnableToUpdateDatabase");
        }

        try
        {
            db = databaseHelper.getWritableDatabase();
        }
        catch (SQLException e)
        {
            throw e;
        }
    }

    @Test
    public void useAppContext()
    {
        AuthorizationActivity authorizationActivity = new AuthorizationActivity();


        Context appContext = InstrumentationRegistry.getTargetContext();
        //assertEquals("com.example.myapplication", appContext.getPackageName())

        authorizationActivity.auth("egor", "123", db);


    }

    @Test
    public  void checkAuth()
    {
        //Context appContext = InstrumentationRegistry.getTargetContext();
        ActivityTestRule<AuthorizationActivity> activityActivityTestRule = new ActivityTestRule<AuthorizationActivity>(AuthorizationActivity.class);
        Looper.prepare();
        AuthorizationActivity authorizationActivity = new AuthorizationActivity();
        authorizationActivity.auth("egor", "123", db);
    }


}
