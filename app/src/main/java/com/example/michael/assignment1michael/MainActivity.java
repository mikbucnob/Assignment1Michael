package com.example.michael.assignment1michael;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;


import static com.example.michael.assignment1michael.R.id.password;
import static com.example.michael.assignment1michael.R.id.shortcut;

//need more than one waiter for login
//read from xml file

public class MainActivity extends AppCompatActivity {

    EditText usernameText, passwordText;
    TextView title1, title2;
    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor;
    LinearLayout myLayout;
    int fontSize = 30;

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


        Log.d("Password", "Original password : " + password);
        String shaPasswordEntered = encryptPassword(passwordEntered);
        Log.d("SHA1Password", "Printing the SHA1 password : " + shaPasswordEntered);


        List users = null;
        XMLUtil xmlUtil = new XMLUsers(this);
        boolean isUserPasswordMatchFound = false;

        try {
            InputStream is = getAssets().open("users.xml");
            users = xmlUtil.parse(is);
            if (users==null){
                Toast.makeText(this,xmlUtil.getError(),Toast.LENGTH_LONG).show();
            } else {
                Log.d("users", "Retrieved users list , size : " + users.size());
                ArrayList<User> usersList = (ArrayList<User>) users;
                for(User user : usersList){
                    if(userNameEntered.equals(user.getUsername())){
                        if(shaPasswordEntered.equals(user.getPassword())){
                            isUserPasswordMatchFound = true;
                            Intent intent = new Intent(view.getContext(), MenuScreen.class);
                            startActivity(intent);
                        }
                    }
                }
            }

            if(!isUserPasswordMatchFound){
                Toast.makeText(MainActivity.this, "Please enter in the right username and password!",
                        Toast.LENGTH_LONG).show();
            }
        }  catch (IOException e) {
            // File not found or unable to open
            //we knwo for sure that the file doesn't exist or we don't have
            //permissions to open it
            Toast.makeText(this, "cannot open users.xml!",Toast.LENGTH_LONG).show();
        }

    }


    private static String encryptPassword(String password)
    {
        String sha1 = "";
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return sha1;
    }

    private static String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        setPreferenceTheme();
    }
}
