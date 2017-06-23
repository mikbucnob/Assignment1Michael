package com.example.michael.assignment1michael;

import android.graphics.drawable.Drawable;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by Michael on 23/06/2017.
 */
public class TakingOrdersTest {

    @Rule
    public ActivityTestRule<TakingOrders> rule = new ActivityTestRule<TakingOrders>(TakingOrders.class);
    private TakingOrders takingOrderActivity = null;

    @Before
    public void setUp() throws Exception {
        takingOrderActivity = rule.getActivity();

    }

    @Test
    public void testImage() {

        View view = takingOrderActivity.findViewById(R.id.orderPicture);
        //Check that the view object is not null
        assertThat(view, notNullValue());

        //Check if the view is a ImageView
        assertThat(view, instanceOf(ImageView.class));

        ImageView imageView = (ImageView) view;
        Drawable image = imageView.getDrawable();
        assertThat(image, notNullValue());
    }

    @Test
    public void testOrderButton() {
        View view = takingOrderActivity.findViewById(R.id.okButton);

        assertThat(view, notNullValue());

        assertThat(view, instanceOf(Button.class));

        Button button = (Button) view;
        String buttonText = button.getText().toString();
        assertThat(buttonText, notNullValue());
        assertEquals(buttonText, "Finished Order");
    }

    @After
    public void tearDown() throws Exception {

        takingOrderActivity = null;
    }

}