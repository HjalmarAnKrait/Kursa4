package com.example.myapplication;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Activities.AuthorizationActivity;
import com.example.myapplication.Activities.RegistrationActivity;
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

    @Test
    public void addition_isCorrect()
    {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void authFields_isCorrect()
    {
        assertTrue(new AuthorizationActivity().checkValid("aleks", "as"));
    }

    @Test
    public void regFields_isCorrect_Passwords_AreEqual()
    {
        assertTrue(new RegistrationActivity().registration("l", "t", "as", "saf"));
    }

    @Test
    public void regFields_isCorrect_CheckIsEmpty()
    {
        assertTrue(new RegistrationActivity().checkIsEmpty("21", "43", "453", "453", "23"));
    }


}