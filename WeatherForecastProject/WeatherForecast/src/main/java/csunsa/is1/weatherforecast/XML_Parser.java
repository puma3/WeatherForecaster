package csunsa.is1.weatherforecast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

public class XML_Parser extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recibir el mensaje del intent
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();
            InputStream in_s = getApplicationContext().getAssets().open("testReading.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);
            parseXML(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.xml__parser, menu);
        return true;
    }
/*
    private void parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
    {

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT){
            String name = null;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    products = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name == "product"){
                        currentProduct = new Product();
                    } else if (currentProduct != null){
                        if (name == "productname"){
                            currentProduct.name = parser.nextText();
                        } else if (name == "productcolor"){
                            currentProduct.color = parser.nextText();
                        } else if (name == "productquantity"){
                            currentProduct.quantity= parser.nextText();
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("product") && currentProduct != null){
                        products.add(currentProduct);
                    }
            }
            eventType = parser.next();
        }
        printProducts(products);
    }
 */
}
