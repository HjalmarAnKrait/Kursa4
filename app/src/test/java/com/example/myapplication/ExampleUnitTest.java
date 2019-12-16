package com.example.myapplication;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Activities.AuthorizationActivity;
import com.example.myapplication.DatabaseWork.DatabaseHelper;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest
{
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    @Test
    public void addition_isCorrect()
    {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void authFields_isCorrect()
    {
        try
        {
            databaseHelper.updateDataBase();
        }
        catch (IOException e)
        {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            db = databaseHelper.getWritableDatabase();
        }
        catch (SQLException e)
        {
            throw e;
        }

        AuthorizationActivity authorizationActivity = new AuthorizationActivity();
        assertTrue(authorizationActivity.auth("egor", "123", db));
    }
}