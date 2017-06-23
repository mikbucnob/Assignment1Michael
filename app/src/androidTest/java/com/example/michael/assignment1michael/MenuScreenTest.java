package com.example.michael.assignment1michael;

import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by Michael on 23/06/2017.
 */
public class MenuScreenTest {

    @Rule
    public ActivityTestRule<MenuScreen> rule = new ActivityTestRule<MenuScreen>(MenuScreen.class);
    private MenuScreen menuActivity = null;

    @Before
    public void setUp() throws Exception {

        menuActivity = rule.getActivity();
    }

    @Test
    public void testMyActivity() {

        View view = menuActivity.findViewById(R.id.myListView);
        //Check that the view object is not null
        assertThat(view, notNullValue());

        //Check if the view is a list view
        assertThat(view, instanceOf(ListView.class));

        ListView menuItemsList = (ListView) view;
        ListAdapter adapter = menuItemsList.getAdapter();
        assertThat(adapter, instanceOf(DishAdapter.class));
        assertThat(adapter.getCount(), greaterThan(5));

    }

    @Test
    public void testMyActivityAgain() {
        View view = menuActivity.findViewById(R.id.myListView);
        assertThat(view, notNullValue());
        assertThat(view, instanceOf(ListView.class));
    }


    @After
    public void tearDown() throws Exception {
        menuActivity = null;
    }

}