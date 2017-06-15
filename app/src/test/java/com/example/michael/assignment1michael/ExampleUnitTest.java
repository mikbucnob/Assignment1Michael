package com.example.michael.assignment1michael;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void myTestCase() throws Exception{
        String testString = "Hello";
        assertEquals(testString,"Hellow");
    }

    @Test
    public void testUserConst_1() {
        User user = new User();
        assertEquals(user.getUsername(), "");
        assertEquals(user.getPassword(), "");
    }

    @Test
    public void testUserConst_2() {
        User user2 = new User("Username", "pwd");
    }


    @Test
    public void testLogonValidation() {
        LoginValidator lv = new LoginValidator(null, "Username", "pwd");
        assertEquals(lv.isValid(), true);
        assertEquals(lv.isValid(), true);
        assertEquals(lv.isValid(), true);
        assertEquals(lv.isValid(), true);
        assertEquals(lv.isValid(), false);
        assertEquals(lv.isValid(), false);
        assertEquals(lv.isValid(), false);
        assertEquals(lv.isValid(), false);
        assertEquals(lv.isValid(), false);
    }
}