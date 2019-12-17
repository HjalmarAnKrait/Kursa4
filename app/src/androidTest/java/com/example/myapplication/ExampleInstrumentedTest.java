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
public class ExampleInstrumentedTest
{

    @Test
    public void useAppContext()
    {
        Context appContext = InstrumentationRegistry.getTargetContext();
    }



}
