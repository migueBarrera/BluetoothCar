package es.iesnervion.bluetoohcar;


import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class GiroscopioFragment extends Fragment implements SensorEventListener {
    boolean andar = false , giroDr = false , giroIzq = false;
    SensorManager mgr;
    Sensor sensor;
    //TextView tvX , tvY ,tvZ ,res;
    ImageView arribaImage , abajoImage, derImage , izImage;
    float ejeZ = 0  , ejeY = 0 , ejeX = 0;
    private OnFragmentInteractionListener mListener;
    Animation startAnimation , startAnimationPlus;

    public GiroscopioFragment() {
        // Required empty public constructor
    }

    public static GiroscopioFragment newInstance(){
        GiroscopioFragment fragment = new GiroscopioFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_giroscopio, container, false);

        arribaImage = (ImageView) v.findViewById(R.id.arribaGiroscopio);
        abajoImage = (ImageView) v.findViewById(R.id.abajoGiroscopio);
        derImage = (ImageView) v.findViewById(R.id.derGiroscopio);
        izImage = (ImageView) v.findViewById(R.id.izqGiroscopio);

         startAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.blink_normal_giroscopio);
        startAnimationPlus = AnimationUtils.loadAnimation(getContext(), R.anim.blink_plus);
        //tvX = (TextView) v.findViewById(R.id.tvx);
        //tvY = (TextView) v.findViewById(R.id.tvy);
        //tvZ = (TextView) v.findViewById(R.id.tvZ);
        //res = (TextView) v.findViewById(R.id.resultado);

        mgr=(SensorManager) v.getContext().getSystemService(Context.SENSOR_SERVICE);
        sensor = mgr.getDefaultSensor(SensorManager.SENSOR_DELAY_GAME);

        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        mgr.unregisterListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mgr.registerListener(this,sensor,SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        ejeX = event.values[0];
        ejeY = event.values[1];
        ejeZ = event.values[2];

        //tvX.setText(String.valueOf(ejeX));
        //tvY.setText(String.valueOf(ejeY));
        //tvZ.setText(String.valueOf(ejeZ));

        //Andar
        if(ejeZ > 7.5 && andar==false){

            DrawableCompat.setTint(arribaImage.getDrawable(), ContextCompat.getColor(getContext(), R.color.colorAccent));

            arribaImage.startAnimation(startAnimation);
            mListener.up();
            //res.setText("ANDAR");
            andar = true;
        }

        //AndarPlus
        if(ejeZ > 9.5){
            DrawableCompat.setTint(arribaImage.getDrawable(), ContextCompat.getColor(getContext(), R.color.colorAccent));

            arribaImage.startAnimation(startAnimationPlus);
            mListener.upplus();
            //res.setText("ANDAR");
            andar = true;
        }

        //Atras
        if(ejeZ < -2 && andar==false){
            DrawableCompat.setTint(abajoImage.getDrawable(), ContextCompat.getColor(getContext(), R.color.colorAccent));

            abajoImage.startAnimation(startAnimation);
            mListener.down();
            //res.setText("ANDAR");
            andar = true;
        }

        //Parar
        if(ejeZ < 4 && ejeZ > 2 && andar==true){
            DrawableCompat.setTint(arribaImage.getDrawable(), ContextCompat.getColor(getContext(), R.color.black));
            DrawableCompat.setTint(abajoImage.getDrawable(), ContextCompat.getColor(getContext(), R.color.black));
            arribaImage.clearAnimation();
            abajoImage.clearAnimation();
            mListener.stopUpDown();
            mListener.up();
            //res.setText("PARAR");
            andar = false;
        }

        //Giro Derecha
        if(ejeY > 5 && giroDr==false){
            DrawableCompat.setTint(derImage.getDrawable(), ContextCompat.getColor(getContext(), R.color.colorAccent));
            derImage.startAnimation(startAnimation);
            mListener.rigth();
            mListener.up();
            //res.setText("GIRO DERECHA");
            giroDr = true;
        }

        //Giro Izquierda
        if(ejeY < -5 && giroIzq==false){
            DrawableCompat.setTint(izImage.getDrawable(), ContextCompat.getColor(getContext(), R.color.colorAccent));
            izImage.startAnimation(startAnimation);
            mListener.left();
            mListener.up();
            //res.setText("GIRO IZQUIERDA");
            giroIzq = true;
        }

        //Desactivar GIRO
        if(giroIzq == true || giroDr == true){

            if(ejeY < 1 && ejeY > -1){
                DrawableCompat.setTint(derImage.getDrawable(), ContextCompat.getColor(getContext(), R.color.black));
                DrawableCompat.setTint(izImage.getDrawable(), ContextCompat.getColor(getContext(), R.color.black));
                izImage.clearAnimation();
                derImage.clearAnimation();
                mListener.stopRigtLeft();
                mListener.up();
                //res.setText("DESACTIVAR GIRO");
                giroDr = false;
                giroIzq = false;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
