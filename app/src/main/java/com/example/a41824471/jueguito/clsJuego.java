package com.example.a41824471.jueguito;

import android.graphics.Paint;
import android.util.Log;

import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCSize;

/**
 * Created by 41824471 on 13/9/2016.
 */
public class clsJuego {
    CCGLSurfaceView _VistaJuego;
    CCSize PantallaDis;
    Sprite Nave;

    public clsJuego(CCGLSurfaceView Vistadeljuego){
        Log.d("log", "Comienza el constrtuctorde la clase");
        _VistaJuego=Vistadeljuego;
    }

    public void Comenzarjuego(){
        Log.d("Comenzar", "Comienza el juego");
        Director.sharedDirector().attachInView(_VistaJuego);

        Log.d("Comenzar", "ejecuta la escena");

        PantallaDis=Director.sharedDirector().displaySize();
        Log.d("Comenzar", "Pantalla del dis - Ancho: "+PantallaDis.width+" - Alto: "+PantallaDis.height);
        Director.sharedDirector().runWithScene(EscenaJuego());

    }

    private Scene EscenaJuego(){
        Log.d("Escena", "Comienza el armado de escena");

        Log.d("Escena", "Declaro e instancio la escena");
        Scene EscenaDevolver;
        EscenaDevolver=Scene.node();

        Log.d("Escena", "capa de fondo");
        CapaDeFondo capafondo;
        capafondo=new CapaDeFondo();

        Log.d("Escena", "capa de frente");
        CapaDeFrente capafrente;
        capafrente=new CapaDeFrente();

        Log.d("Escena", "agrego las capas a la escena");
        EscenaDevolver.addChild(capafondo,-10);
        EscenaDevolver.addChild(capafrente,10);

        Log.d("Escena", "Devuelve");
        return EscenaDevolver;
    }

    class CapaDeFondo extends Layer{
        /*public CapaDeFondo() {
            ponerImagenFondo();
        }
        private void ponerImagenFondo{

        }*/
    }

    class CapaDeFrente extends  Layer{
        public CapaDeFrente(){
            Log.d("CapaFrente", "Constructor");

            Log.d("CapaFrente", "Jugador en pos inicial");
            PonerNavePosicionInicial();
        }

        private void PonerNavePosicionInicial(){
            Log.d("Poner Nave Jugador", "comienza");

            Nave=Sprite.sprite("rocket_mini.png");
            float PosIX, PosIY;
            PosIX=PantallaDis.width/2;
            PosIY=PantallaDis.getHeight()/2;
            Nave.setPosition(PosIX,PosIY);
            super.addChild(Nave);
        }

    }
}
