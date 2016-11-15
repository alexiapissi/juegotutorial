package com.example.a41824471.jueguito;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import org.cocos2d.opengl.CCGLSurfaceView;

public class MainActivity extends Activity {
    CCGLSurfaceView vistap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        vistap= new CCGLSurfaceView(this);
        setContentView(vistap);
    }

    @Override
    protected void onStart(){
        super.onStart();
        clsJuego juego;
        juego= new clsJuego(vistap, getBaseContext());
        juego.Comenzarjuego();
    }
}
