package com.example.michael.assignment1michael;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.michael.assignment1michael.R.id.password;

//need more than one waiter for login
//read from xml file

public class MainActivity extends AppCompatActivity {

    EditText usernameText, passwordText;
    TextView title1, title2;
    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor;
    LinearLayout myLayout;
    int fontSize = 25;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button logonbutton=(Button) findViewById(R.id.logonButton);
        usernameText = (EditText) findViewById(R.id.username);
        passwordText = (EditText) findViewById(password);
        title1 = (TextView) findViewById(R.id.title1);
        title2 = (TextView) findViewById(R.id.title2);

        setPreferenceTheme();

        /*needs up top :- SharedPreferences sharedPref = null;

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        //internally an xml but android reads and writes putString into xml
        editor.putString("username", "Waiter1");
        editor.putString("password", "password123");
        editor.commit();
        //sharedPreferences for a quick inbuilt android data storage mechanism*/


        passwordText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press

                    //from xml file

                    logonValidator(v);
                    return true;
                }
                return false;
            }
        });

        logonbutton.setTextSize(fontSize);
        logonbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logonValidator(view);
            }
        });



    }

    /**
     * Method to set the background and font based on preferences
     */
    private void setPreferenceTheme() {
        sharedPref = getApplicationContext().getSharedPreferences("myprefs", 0);
        String colorString = "";
        colorString = sharedPref.getString("rgb", "");
        Log.d("ColorSet", "Color set is : " + colorString);

        myLayout = (LinearLayout) findViewById(R.id.loginScreenLayout);
        if(colorString != null && colorString != "") {
            myLayout.setBackgroundColor(Integer.valueOf(colorString));
        }

        String fontSizeString = sharedPref.getString("fontsize", "");
        if("" != fontSizeString) {
            fontSize = Integer.valueOf(fontSizeString);
            Log.d("FontSize", "MainActivity : Read font size from preferences : " + fontSize);

            //Set font for all components in this activity
            usernameText.setTextSize(fontSize);
            passwordText.setTextSize(fontSize);
            title1.setTextSize(fontSize);
            title2.setTextSize(fontSize);
        } else if (null == fontSizeString || "".equals(fontSizeString)) {
            //Since the preferences is not set, it could be first time the app is launched
            //So set a default value in preferences and apply font to components in this activity
            editor = sharedPref.edit();
            editor.putString("fontsize", String.valueOf(fontSize));
            editor.commit();

            //Set font for all components in this activity
            usernameText.setTextSize(fontSize);
            passwordText.setTextSize(fontSize);
            title1.setTextSize(fontSize);
        }
    }

    private void logonValidator(View view) {

        /*String username = sharedPref.getString("username", "null");
        String password = sharedPref.getString("password", "null");
        //getString gets the username and puts it into a username variable
        //SharedPref is good for one login not more*/

        String userNameEntered = usernameText.getText().toString();
        String passwordEntered = passwordText.getText().toString();

        //Parse username from the xml file

        LoginValidator lv = new LoginValidator(this, userNameEntered, passwordEntered);

        if (lv.isValid()) {


            Intent intent = new Intent(view.getContext(), MenuScreen.class);
            startActivity(intent);
        } else {

            Toast.makeText(MainActivity.this, "Please enter in the right username and password!",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        setPreferenceTheme();
    }
}
