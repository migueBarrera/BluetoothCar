package es.iesnervion.bluetoohcar;


import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
/*
    Esta clase es la del GamePad , tiene los metodos a los que llaman los botones del mismo
 */
public class GamePadFragment extends Fragment {

    //Buttons
    ImageView btnUP , btnDown , btnRight , btnLeft,btnSpeaker,btnligth,btnligthPlus, powerButton;
    ImageView btnInterIzq,btnInterDr;
    private OnFragmentInteractionListener mListener;
    Drawable ligth_icon , ligthPlus_icon;


    public GamePadFragment(){

    }

    public static GamePadFragment newInstance(){
        GamePadFragment fragment = new GamePadFragment();

        return fragment;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_pad, container, false);

        //Inicializar Botones
        btnUP = (ImageView) v.findViewById(R.id.arribaButton);
        btnDown = (ImageView) v.findViewById(R.id.abajoButton);
        btnRight = (ImageView) v.findViewById(R.id.derButton);
        btnLeft = (ImageView) v.findViewById(R.id.izqButton);
        btnSpeaker = (ImageView) v.findViewById(R.id.bocinaButton);
        //btnligth = (ImageView) v.findViewById(R.id.luzButton);
        btnInterDr = (ImageView) v.findViewById(R.id.intermitentederButton);
        btnInterIzq = (ImageView) v.findViewById(R.id.intermitenteizqButton);
        powerButton = (ImageView) v.findViewById(R.id.powerButton);
        btnligth = (ImageView) v.findViewById(R.id.luz_cortas);
        btnligthPlus = (ImageView) v.findViewById(R.id.luz_largas);
        ligth_icon = ContextCompat.getDrawable(getContext(), R.drawable.ligth_icon);
        ligthPlus_icon = ContextCompat.getDrawable(getContext(), R.drawable.ligth_icon_plus);

        listenner();

        return v;
    }



    public void listenner(){
        //Button UP
        btnUP.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                        if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                            mListener.upplus();
                        } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                            mListener.stopUpDown();
                        }
                return true;
            }
        });

        //Button Down
        btnDown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                        if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                            mListener.down();
                        } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                            mListener.stopUpDown();
                        }

                return true;
            }
        });

        //Button Right
        btnRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                        if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                            mListener.rigth();
                        } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                            mListener.stopRigtLeft();
                        }

                return true;
            }
        });

        //Button Left
        btnLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                        if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                            mListener.left();
                        } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                            mListener.stopRigtLeft();
                        }

                return true;
            }
        });

        //Button Speaker
        btnSpeaker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                    mListener.speakerStart();
                } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                    mListener.speakerStop();
                }

                return true;
            }
        });
        btnligth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.ligthStar();
                changeColorIcon(ligth_icon,R.color.colorAccentDark);
                changeColorIcon(ligthPlus_icon,R.color.colorAccentLigth);
            }
        });

        btnligthPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.ligthStarPlus();
                changeColorIcon(ligthPlus_icon,R.color.colorAccentDark);
                changeColorIcon(ligth_icon,R.color.colorAccentLigth);
            }
        });

        btnInterDr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.intermDr();
            }
        });

        btnInterIzq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.intermIzq();
            }
        });

        powerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.desconectar();
            }
        });

    }

    public void changeColorIcon(Drawable drawable, int color){
        DrawableCompat.setTint(drawable, color);
        //DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(), color));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable.setTint(color);
        }
    }
}
