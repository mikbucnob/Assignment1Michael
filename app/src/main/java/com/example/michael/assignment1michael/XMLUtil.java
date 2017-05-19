package com.example.michael.assignment1michael;

import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Michael on 4/05/2017.
 */

public abstract class XMLUtil {

    // We don't use namespaces
    protected static final String ns = null;
    private String error = "";

    public String getError(){
        return error;
    }

    public List parse(InputStream in){
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readRoot(parser);
        }

        catch (XmlPullParserException e) {
            // unable to parse xml file contents be more specific
            //about nature of the problem by setting error string
            //by error string in other methods as appropriate
            //error=e.getMessage(); --> red problems (lots of errors)
            // or error="Can't parse XML file!";
            error="Cannot parse XML file!";
        }
        catch (IOException e) {
            error = "Problem reading file!";
        }
        return null;
    }

    protected abstract List readRoot(XmlPullParser parser) throws XmlPullParserException, IOException;

    public abstract String createXML(Object object) throws IllegalArgumentException, IllegalStateException, IOException;

    // Reads contents of a tag could do readAttribute
    protected String readTag(XmlPullParser parser, String tagName) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, tagName);
        //ns is the namspace
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, tagName);
        return title;
    }

    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }


    protected void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}
