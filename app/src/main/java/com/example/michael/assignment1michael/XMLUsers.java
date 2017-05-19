package com.example.michael.assignment1michael;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 12/05/2017.
 */

public class XMLUsers extends XMLUtil {
    Context context;

    public XMLUsers(Context context){
        this.context = context;
    }

    //@ is an annotation - it's like notes
    @Override
    protected List readRoot(XmlPullParser parser) throws XmlPullParserException, IOException {
        List usersList = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "waiters");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the dish tag
            if (name.equals("waiter")) {
                usersList = readUser(parser);
                break;
            } else {
                skip(parser);
            }
        }
        return usersList;
    }


    // Parses the contents of a user.
    private ArrayList<User> readUser(XmlPullParser parser) throws XmlPullParserException, IOException {


        ArrayList<User>  users = null;
        int eventType = parser.getEventType();
        User user = null;

        while (eventType != XmlPullParser.END_DOCUMENT){
            String name;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    if(null == users){
                        users = new ArrayList();
                    }
                    name = parser.getName();
                    if (name.equals("waiter")){
                        Log.d("waiter", "Start Tag waiter");
                        user = new User();
                    } else if (user != null){
                        Log.d("check title", "name : " + name );
                        if (name.equals("username")){
                            user.setUsername(parser.nextText());
                        } else if (name.equals("password")){
                            user.setPassword(parser.nextText());
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    Log.d("end", "end tag");
                    name = parser.getName();
                    if (name.equalsIgnoreCase("waiter") && user != null){
                        Log.d("Adding", "Adding user to users");
                        users.add(user);
                    }
            }
            eventType = parser.next();
        }

        return users;
    }


    public String createXML(Object object) throws IllegalArgumentException, IllegalStateException, IOException {
        return null;
    }

}
