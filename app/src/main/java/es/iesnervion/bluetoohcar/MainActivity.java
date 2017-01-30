package es.iesnervion.bluetoohcar;

import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

    public static String EXTRA_ADDRESS = "device_address";
    ArrayAdapter<Dispositivo> mArrayAdapter;
    BluetoothAdapter mBluetoothAdapter;
    final int REQUEST_ENABLE_BT = 1;
    TextView tvNoData;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        tvNoData = (TextView) findViewById(R.id.tvNoData);

        //listenner
        asignarListnner();

        mArrayAdapter = new ArrayAdapter<>(this,R.layout.text_view_list_view,R.id.tvList);

        mBluetoothAdapter =  BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            iniciarBluetooh();
        }
    }
    public void iniciarBluetooh(){
        // El Bluetooth está apagado, solicitamos permiso al usuario para iniciarlo
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        // REQUEST_ENABLE_BT es un valor entero que vale 1
        startActivityForResult(enableBtIntent,REQUEST_ENABLE_BT);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        tvNoData.setVisibility(View.GONE);
        if (mBluetoothAdapter.isDiscovering()) {
            // El Bluetooth ya está en modo discover, lo cancelamos para iniciarlo de nuevo
            mBluetoothAdapter.cancelDiscovery();
        }
        Dispositivo dispositivo = (Dispositivo) l.getItemAtPosition(position);
        String address = dispositivo.getDireccion();

        // Make an intent to start next activity.
        Intent i = new Intent(this, ControllerActivity.class);

        //Change the activity.
        i.putExtra(EXTRA_ADDRESS, address); //this will be received at Control (class) Activity
        startActivity(i);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_ENABLE_BT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

            }else {
                //Decir al usuario que sin el bluetooh activado no puede usarlo
                Toast.makeText(this,"ES NECESARIO TENER EL BLUETOOHT ACTIVADO",Toast.LENGTH_LONG).show();
            }
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Se ha encontrado un dispositivo Bluetooth
                // Se obtiene la información del dispositivo del intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Dispositivo dispositivo = new Dispositivo(device.getName(),device.getAddress());
                mArrayAdapter.add(dispositivo);
               setListAdapter(mArrayAdapter);
            }
        }
    };



    public void blinkButton(){
        Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink_button);
        fab.startAnimation(startAnimation);

    }

    public void blinkCheked(){

        if(!mBluetoothAdapter.isDiscovering()){
            fab.clearAnimation();
            fab.getAnimation().cancel();
        }

    }

    public void asignarListnner(){

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Se borra el mensaje de nodata
                tvNoData.setVisibility(View.GONE);

                //Iniciamos parpadeo
                blinkButton();


                // Se obtiene una instancia de la clase BluetoothAdapter
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

                if (!mBluetoothAdapter.isEnabled()) {
                    // El Bluetooth está apagado, solicitamos permiso al usuario para iniciarlo
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    // REQUEST_ENABLE_BT es un valor entero que vale 1
                    startActivityForResult(enableBtIntent,1);
                }

                // Se registra el broadcast receiver
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                registerReceiver(mReceiver, filter);

                filter = new IntentFilter(mBluetoothAdapter.ACTION_DISCOVERY_FINISHED);
                registerReceiver(mReceiver,filter);

                if (mBluetoothAdapter.isDiscovering()) {
                    // El Bluetooth ya está en modo discover, lo cancelamos para iniciarlo de nuevo
                    mBluetoothAdapter.cancelDiscovery();
                }
                mBluetoothAdapter.startDiscovery();
            }
        });
    }




}
