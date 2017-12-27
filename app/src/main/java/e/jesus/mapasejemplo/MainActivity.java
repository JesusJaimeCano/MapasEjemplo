package e.jesus.mapasejemplo;

import android.Manifest;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView activadoGPSTV, latiYLongTV, direccionTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 10);

        activadoGPSTV = findViewById(R.id.activadoGPSTV);
        latiYLongTV = findViewById(R.id.latYlongTV);
        direccionTV = findViewById(R.id.direccionTV);

        LocationManager mLocManger = (LocationManager) getSystemService(LOCATION_SERVICE);
        Localizacion localizacion = new Localizacion();

        mLocManger.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0 , 0, localizacion);

    }

    public class Localizacion implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            String text = "Mi ubicación actual es: \n" + "Latitud: " + location.getLatitude() + "\nLongitud: " + location.getLongitude();
            latiYLongTV.setText(text);
            setLocation(location);
        }

        public void setLocation (Location loc){
            if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0){
                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                try {
                    List<Address> list =
                            geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
                    if (!list.isEmpty()){
                        Address calle = list.get(0);
                        direccionTV.setText("Mi direccion actual es: " + calle.getAddressLine(0));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            activadoGPSTV.setText("GPS Activado");
        }

        @Override
        public void onProviderDisabled(String provider) {
            activadoGPSTV.setText("GPS Desactivado");
        }
    }

}
