package com.example.sensoraplication2;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView texto, texto2, orientacion;
    SensorManager sensorManager;
    private Sensor gravity,magnetometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        texto = (TextView)findViewById(R.id.texto);
        texto2 = (TextView)findViewById(R.id.texto2);
        orientacion = (TextView)findViewById(R.id.orientacion);


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);   //enlace con el acelerometro
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x,y,z;

        if(event.sensor.getType()== Sensor.TYPE_GRAVITY){
            x= event.values[0];
            y= event.values[1];
            z= event.values[2];
            texto.setText("");
            texto.append("\n\t\t" + "X: " + "\t"+ x + "\n\t\t" + "Y: "+ "\t" + y + "\n\t\t" + "Z: "+ "\t" + z);


            if(x<0 && y>-1 && y<1)
                orientacion.setText("HORIZONTAL 1");
            if(x>-1 && y>0 &&  x<1 && z>0)
                orientacion.setText("VERTICAL 1");
            if(x>-1 && y<0 &&  x<1 && z>0)
                orientacion.setText("VERTICAL 2");
            if(x>0 && y>-1 && y<1)
                orientacion.setText("HORIZONTAL 2");
        }
        else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            x= event.values[0];
            y= event.values[1];
            z= event.values[2];
            texto2.setText("");
            texto2.append("\n\t\t" + "X: " + "\t"+ x + "\n\t\t" + "Y: "+ "\t" + y + "\n\t\t" + "Z: "+ "\t" + z);
        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, gravity, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}