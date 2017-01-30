package es.iesnervion.bluetoohcar;


import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class GamePadFragment extends Fragment {

    //Buttons
    ImageView btnUP , btnDown , btnRight , btnLeft,btnSpeaker,btnligth, powerButton;
    ImageView btnInterIzq,btnInterDr;
    private OnFragmentInteractionListener mListener;



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
      //  i = (ImageView) v.findViewById(R.id.imageView);
        //i.setOnTouchListener();
        //Enviar Listenner
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
        /*//Button ligth
        btnligth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                    mListener.ligthStar();
                } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                    mListener.ligthStop();
                }

                return true;
            }
        });*/

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

}
