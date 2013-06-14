package csunsa.is1.weatherforecast;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import csunsa.is1.weatherforecast.Info_Structures.single_reading;

public class MainActivity extends Activity {
    private static final String URL =
            "http://api.openweathermap.org/data/2.5/weather?lat=-16.3691842&lon=-71.4985723&mode=xml&units=metric&lang=sp";

    public single_reading m_reading = new single_reading();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        show_Reading();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void show_Reading () {
        //*****************TEST**********************//
        String  city = "Arequipa",
                country = "PE",
                desc = "Descrip",
                ico = "02n";

        m_reading.set_city_name(city);
        m_reading.set_country_name(country);
        m_reading.set_descr(desc);
        m_reading.set_ico(ico);
        //*******************************************//

        TextView _textField = (TextView) findViewById(R.id.city_field);                         //Objeto para cambiar todos los campos de texto
        _textField.setText(m_reading.get_city_name() + ", " + m_reading.get_country_name());    //Ciudad, País

        _textField = (TextView) findViewById(R.id.descr_field);                                 //Descripcion
        _textField.setText(m_reading.get_descr());


    }
}

