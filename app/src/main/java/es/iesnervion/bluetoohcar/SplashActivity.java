package es.iesnervion.bluetoohcar;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.jar.Manifest;

public class SplashActivity extends AppCompatActivity {
    final int REQUEST_ENABLE_BT = 1;
    BluetoothAdapter mBluetoothAdapter;
    TextView textView;
    String fraseMotivadora = "Lo importante es llegar";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        textView = (TextView) findViewById(R.id.tvmotivador);

        fraseMotivadora = getFrase();
        textView.setText(fraseMotivadora);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkPermision();
        }

        Thread hilo = new Thread(){
             @Override
            public void run(){
                 try{
                     sleep(3500);

                     // Se obtiene una instancia de la clase BluetoothAdapter
                     mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

                     // Si el adaptador es null significa que el dispositivo no soporta Bluetooth
                     if (mBluetoothAdapter == null) {
                         //Ir a una activity que diga al usuario que  no puede usar esta app
                     }else{
                             //Ir al Main Activity
                             Intent i = new Intent(getApplicationContext(),MainActivity.class);
                             startActivity(i);
                             finish();
                     }
                 }catch (InterruptedException e){
                     e.printStackTrace();
                 }
             }
        };
        hilo.start();
    }


    public String getFrase(){
        String[] frases = leerFrases();
        int tamaño = frases.length;
        int numero = (int) (Math.random() * tamaño);
        return frases[numero];
    }

    public String[] leerFrases(){
        String[] frases;
        ArrayList<String> arrayFrases = new ArrayList<>();
        InputStream is = null;
        BufferedReader br = null;
        try
        {
            is = getAssets().open("frases.txt");
            br = new BufferedReader(new InputStreamReader(is));

            String linea = br.readLine();

            while (linea!=null){

                arrayFrases.add(linea);
                linea = br.readLine();
            }


        }
        catch (Exception ex)
        {
            Log.e("Ficheros", "Error al leer fichero desde recurso");
        }finally {
            if(br!=null || is!=null){
                try {
                    br.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        frases = arrayFrases.toArray(new String[0]);
        return frases;
    }

    @TargetApi(23)
    public void checkPermision(){
        int permiso = this.checkSelfPermission("manifest.permission.ACCESS_FINE_LOCATION");
        permiso += this.checkSelfPermission("manifest.permission.ACCESS_COARSE_LOCATION");
        if(permiso !=0){
            this.requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},666);
        }
    }

}
