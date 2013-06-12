package csunsa.is1.weatherforecast;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by zeroblazer on 6/11/13.
 */
public class OpenWeatherXMLParser {
    private static final String ns = null;

    // We don't use namespaces

    public Reading parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private Reading readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        Reading reading = readReading(parser);
        return reading;
    }

    // This class represents a single entry (post) in the XML feed.
    // It includes the data members "title," "link," and "summary."
    public static class Reading {
        public final String city_nm;
        public final String weather_value;

        private Reading(String city_nm, String weather_value) {
            this.city_nm = city_nm;
            this.weather_value = weather_value;
        }
    }

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them
    // off
    // to their respective &quot;read&quot; methods for processing. Otherwise, skips the tag.
    private Reading readReading(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "current");
        String city_nm = null;
        String weather_value = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("city")) {
                city_nm = readCity(parser);
            } else if (name.equals("weather")) {
                weather_value = readWeather(parser);
            } else {
                skip(parser);
            }
        }
        return new Reading(city_nm, weather_value);
    }

    // Processes title tags in the feed.
    private String readCity(XmlPullParser parser) throws IOException, XmlPullParserException {
        String city_nm="";
        parser.require(XmlPullParser.START_TAG, ns, "city");
        city_nm = parser.getAttributeValue(null, "name");
        parser.nextTag();
        parser.require(XmlPullParser.END_TAG, ns, "city");
        return city_nm;
    }

    // Processes link tags in the feed.
    private String readWeather(XmlPullParser parser) throws IOException, XmlPullParserException {
        String weather_value = "";
        parser.require(XmlPullParser.START_TAG, ns, "weather");
        weather_value = parser.getAttributeValue(null, "value");
        parser.nextTag();
        parser.require(XmlPullParser.END_TAG, ns, "weather");
        return weather_value;
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

    // Skips tags the parser isn't interested in. Uses depth to handle nested tags. i.e.,
    // if the next tag after a START_TAG isn't a matching END_TAG, it keeps going until it
    // finds the matching END_TAG (as indicated by the value of "depth" being 0).
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
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