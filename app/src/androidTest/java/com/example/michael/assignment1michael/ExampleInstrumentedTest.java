package com.example.michael.assignment1michael;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    Context appContext;

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.michael.assignment1michael", appContext.getPackageName());
    }

    @Test
    public void postiveTestLogonValidation() throws Exception {
        useAppContext();
        LoginValidator lv = new LoginValidator(appContext, "Michael", "washthedog");
        assertEquals(lv.isValid(), true);
    }

    @Test
    public void negativePwdTestLogonValidation() throws Exception {
        useAppContext();
        LoginValidator lv = new LoginValidator(appContext, "Michael", "wash");
        assertEquals(lv.isValid(), false);
    }

    @Test
    public void negativeUserNamedTestLogonValidation() throws Exception {
        useAppContext();
        LoginValidator lv = new LoginValidator(appContext, "Mike", "washthedog");
        assertEquals(lv.isValid(), false);
    }
}
