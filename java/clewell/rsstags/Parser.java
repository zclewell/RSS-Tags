package clewell.rsstags;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by zach on 4/20/17.
 */

public class Parser {
    private static Feed feed;

    public static Feed parse(URL url) throws XmlPullParserException, IOException {
        InputStream in = url.openStream();
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            readFeed(parser);
        } finally {
            in.close();
        }
        return feed;
    }

    private static void readFeed(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        feedSetup(xmlPullParser);
        String title = null;
        String description = null;
        String link = null;
        String date = null;
        boolean isItem = true;

        //Fills feed object with articles
        while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
            int eventType = xmlPullParser.getEventType();
            String name = xmlPullParser.getName();
            if (name == null) {
                continue;
            }

            if (eventType == XmlPullParser.END_TAG) {
                if (name.equalsIgnoreCase("item")) {
                    isItem = false;
                }
                continue;
            }

            if (eventType == XmlPullParser.START_TAG) {
                if (name.equalsIgnoreCase("item")) {
                    isItem = true;
                    continue;
                }
            }

            String text = "";
            if (xmlPullParser.next() == XmlPullParser.TEXT) {
                text = xmlPullParser.getText();
                xmlPullParser.nextTag();
            }
            if (name.equalsIgnoreCase("title")) {
                title = text;
            }
            if (name.equalsIgnoreCase("description")) {
                description = text;
            }
            if (name.equalsIgnoreCase("link")) {
                link = text;
            }
            if (name.equalsIgnoreCase("pubdate")) {
                date = text;
            }
            if (!(title == null || description == null || link == null || date == null)) {
                if (isItem) {
                    System.out.println("Article Made! " + title);
                    Article temp = new Article(title, description, link, date);
                    feed.addArticle(temp);
                }
                title = null;
                description = null;
                link = null;
                date = null;
            }
        }

    }

    private static void feedSetup(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        //Sets up the fields for a Feed object
        String feedTitle = null;
        String copyright = null;
        String language = null;

        while (true) {
            int eventType = xmlPullParser.getEventType();
            String name = xmlPullParser.getName();
            if (name == null) {
                continue; //skips empty tags
            }
            if (eventType == XmlPullParser.START_TAG) {
                if (name.equalsIgnoreCase("item")) {
                    feed = new Feed(feedTitle, copyright, language);
                    break; //all info for feed takes place before first item
                }
            }
            String text = "";
            if (xmlPullParser.next() == XmlPullParser.TEXT) {
                text = xmlPullParser.getText();
                xmlPullParser.nextTag();
            }

            if (name.equalsIgnoreCase("title")) {
                feedTitle = text;
            }
            if (name.equalsIgnoreCase("language")) {
                language = text;
            }
            if (name.equalsIgnoreCase("copyright")) {
                copyright = text;
            }
        }
    }
}
