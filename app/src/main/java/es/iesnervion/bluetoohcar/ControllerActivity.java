package es.iesnervion.bluetoohcar;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class ControllerActivity extends AppCompatActivity implements OnFragmentInteractionListener{

    private BottomNavigationView mBottomNav;
    private int mSelectedItem;
    private String address;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    Drawable arribaIcon , abajoIcon , derIcon , izqIcon;


    //Strings que se envian por bluetooth
    String start = "1" , startPlus = "13", stop = "2",marchaAtras = "3",giroIzq = "5",
            giroDr = "4",giroStop = "6", bocinaStar = "7",bocinaStop = "8",
            luzStart = "9" , luzStop = "10",intermitenteIzq = "11",intermitenteDr = "12";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        //Intent para recoger la direccion MAC
        Intent newint = getIntent();
        address = newint.getStringExtra(MainActivity.EXTRA_ADDRESS); //receive the address of the bluetooth device

        //Drawables icon
        arribaIcon  = ContextCompat.getDrawable(getApplicationContext(), R.drawable.arriba_icon);
        abajoIcon  = ContextCompat.getDrawable(getApplicationContext(), R.drawable.abajo_icon);
        derIcon  = ContextCompat.getDrawable(getApplicationContext(), R.drawable.der_icon);
        izqIcon = ContextCompat.getDrawable(getApplicationContext(), R.drawable.izq_icon);

        //Botones de navegacion
        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectFragment(item);
                return true;
            }
        });

        MenuItem selectedItem;
        if (savedInstanceState != null) {
            mSelectedItem = savedInstanceState.getInt("y", 0);
            selectedItem = mBottomNav.getMenu().getItem(mSelectedItem);
        } else {
            selectedItem = mBottomNav.getMenu().getItem(0);
        }

        selectFragment(selectedItem);
       //new ConnectBT().execute();
    }

    private void selectFragment(MenuItem item) {
        Fragment frag = null;
        // init corresponding fragment
        switch (item.getItemId()) {
            case R.id.menu_gamepad:
                frag = GamePadFragment.newInstance();
                break;
            case R.id.menu_giroscopio:
                frag = GiroscopioFragment.newInstance();
                break;

        }

        // update selected item
        mSelectedItem = item.getItemId();

        // uncheck the other items.
        for (int i = 0; i< mBottomNav.getMenu().size(); i++) {
            MenuItem menuItem = mBottomNav.getMenu().getItem(i);
            menuItem.setChecked(menuItem.getItemId() == item.getItemId());
        }


        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.containerFrame,frag,"e");
            ft.commit();
        }
    }

    @Override
    public void up() {
        if (btSocket!=null)
        {
            try{

                //Enviar infor por bluetooth
                btSocket.getOutputStream().write(start.toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error enviando datos");
            }
        }
        //Cabiar color
        changeColorIcon(arribaIcon,R.color.colorAccent);
    }

    @Override
    public void upplus() {
        if (btSocket!=null)
        {
            try{
                //Cabiar color
                changeColorIcon(arribaIcon,R.color.colorAccentDark);
                //Enviar infor por bluetooth
                btSocket.getOutputStream().write(startPlus.toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error enviando datos");
            }
        }
    }

    @Override
    public void down() {
        if (btSocket!=null)
        {
            try{
                //Cabiar color
                changeColorIcon(abajoIcon,R.color.colorAccent);
                //Enviar infor por bluetooth
                btSocket.getOutputStream().write(marchaAtras.toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error enviando datos");
            }
        }
    }

    @Override
    public void rigth() {
        if (btSocket!=null)
        {
            try{
                //Cabiar color
                changeColorIcon(derIcon,R.color.colorAccent);
                //Enviar infor por bluetooth
                btSocket.getOutputStream().write(giroDr.toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error enviando datos");
            }
        }
    }

    @Override
    public void left() {
        if (btSocket!=null)
        {
            try{
                //Cabiar color
                changeColorIcon(izqIcon,R.color.colorAccent);
                //Enviar infor por bluetooth
                btSocket.getOutputStream().write(giroIzq.toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error enviando datos");
            }
        }
    }

    @Override
    public void stopUpDown() {
        if (btSocket!=null)
        {
            try{

                //Enviar infor por bluetooth
                btSocket.getOutputStream().write(stop.toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error enviando datos");
            }
        }
        //Cabiar color
        changeColorIcon(arribaIcon,R.color.black);
        changeColorIcon(abajoIcon,R.color.black);
    }

    @Override
    public void stopRigtLeft() {
        if (btSocket!=null)
        {
            try{
                //Cabiar color
                changeColorIcon(izqIcon,R.color.black);
                changeColorIcon(derIcon,R.color.black);
                //Enviar infor por bluetooth
                btSocket.getOutputStream().write(giroStop.toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error enviando datos");
            }
        }
    }

    @Override
    public void speakerStart() {
        if (btSocket!=null)
        {
            try{
                btSocket.getOutputStream().write(bocinaStar.toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error enviando datos");
            }
        }
    }

    @Override
    public void speakerStop() {
        if (btSocket!=null)
        {
            try{
                btSocket.getOutputStream().write(bocinaStop.toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error enviando datos");
            }
        }
    }

    @Override
    public void ligthStar() {
        if (btSocket!=null)
        {
            try{
                btSocket.getOutputStream().write(luzStart.toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error enviando datos");
            }
        }
    }

    @Override
    public void ligthStop() {
        if (btSocket!=null)
        {
            try{
                btSocket.getOutputStream().write(luzStop.toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error enviando datos");
            }
        }
    }

    @Override
    public void intermIzq() {
        if (btSocket!=null)
        {
            try{
                btSocket.getOutputStream().write(intermitenteIzq.toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error enviando datos");
            }
        }
    }

    @Override
    public void intermDr() {
        if (btSocket!=null)
        {
            try{
                btSocket.getOutputStream().write(intermitenteDr.toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error enviando datos");
            }
        }
    }

    @Override
    public void desconectar() {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            {
                msg("Error en la desconexión");
            }
        }
        finish(); //return to the first layout
    }

    public void changeColorIcon(Drawable drawable, int color){
        //DrawableCompat.setTint(drawable, color);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(getApplicationContext(), color));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //drawable.setTint(color);
        }
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(ControllerActivity.this, "Conectando", "Espere por favor");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Conexion fallida, inténtelo de nuevo.");
                finish();
            }
            else
            {
                msg("Conectado.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }

    public void msg(String cadena){
        Toast.makeText(this,cadena,Toast.LENGTH_LONG).show();
    }


}
