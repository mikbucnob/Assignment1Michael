package com.example.michael.assignment1michael;

import android.content.Context;
import android.util.Log;
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

/**
 * Created by Michael on 14/06/2017.
 */

class LoginValidator {

    private boolean valid;

    public LoginValidator(Context activity, String userNameEntered, String passwordEntered) {


        Log.d("Password", "Original password : " + password);
        String shaPasswordEntered = encryptPassword(passwordEntered);
        Log.d("SHA1Password", "Printing the SHA1 password : " + shaPasswordEntered);


        List users = null;
        XMLUtil xmlUtil = new XMLUsers(activity);
        boolean isUserPasswordMatchFound = false;

        try {
            InputStream is = activity.getAssets().open("users.xml");
            users = xmlUtil.parse(is);
            if (users == null) {
                Toast.makeText(activity, xmlUtil.getError(), Toast.LENGTH_LONG).show();
            } else {
                Log.d("users", "Retrieved users list , size : " + users.size());
                ArrayList<User> usersList = (ArrayList<User>) users;
                for (User user : usersList) {
                    Log.d("username", "userNameEntered : " + userNameEntered + " user : " + user.getUsername());
                    if (userNameEntered.equals(user.getUsername())) {

                        Log.d("Pwd ", "shaPasswordEntered : " + shaPasswordEntered + " pwd : " + user.getPassword());
                        if (shaPasswordEntered.equals(user.getPassword())) {
                            isUserPasswordMatchFound = true;
                            Log.d("PwdMatch", "Password valid : true");
                            valid = true;
                        }
                    }
                }
            }

            if (!isUserPasswordMatchFound) {
                valid = false;

            }
        } catch (IOException e) {
            // File not found or unable to open
            //we knwo for sure that the file doesn't exist or we don't have
            //permissions to open it
            valid = false;

        }

    }

    private String encryptPassword(String password) {
        String sha1 = "";
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sha1;
    }


    private String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }


    public boolean isValid() {
        return valid;
    }
}
