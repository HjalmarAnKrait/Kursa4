package com.example.myapplication.Activities;

import android.content.Intent;
import android.view.View;

import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.R;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


public class AuthorizationActivityTest
{

    @Rule
    public ActivityTestRule<AuthorizationActivity> rule = new ActivityTestRule<>(AuthorizationActivity.class, true, true);

    private AuthorizationActivity authorizationActivity = null;



    @Before
    public void setUp() throws Exception
    {

        authorizationActivity = rule.getActivity();
    }

    @Test
    public void auth()
    {
        View view = authorizationActivity.findViewById(R.id.auth_button);
        Assert.assertNotNull(view);
    }

    @After
    public void tearDown()
    {
        authorizationActivity = null;

    }


}