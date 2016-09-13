package com.example.a41824471.jueguito;

import android.graphics.Paint;
import android.util.Log;

import org.cocos2d.actions.interval.MoveTo;
import org.cocos2d.actions.interval.RotateTo;
import org.cocos2d.actions.interval.ScaleBy;
import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Label;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCPoint;
import org.cocos2d.types.CCSize;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 41824471 on 13/9/2016.
 */
public class clsJuego {
    CCGLSurfaceView _VistaJuego;
    CCSize PantallaDis;
    Sprite Nave;
    Sprite ImagenFondo;
    Sprite Enemigo;

    public clsJuego(CCGLSurfaceView Vistadeljuego) {
        _VistaJuego = Vistadeljuego;
    }

    public void Comenzarjuego() {
        Director.sharedDirector().attachInView(_VistaJuego);


        PantallaDis = Director.sharedDirector().displaySize();
        Log.d("Comenzar", "Pantalla del dis - Ancho: " + PantallaDis.width + " - Alto: " + PantallaDis.height);
        Director.sharedDirector().runWithScene(EscenaJuego());

    }

    private Scene EscenaJuego() {

        Scene EscenaDevolver;
        EscenaDevolver = Scene.node();


        CapaDeFondo capafondo;
        capafondo = new CapaDeFondo();

        CapaDeFrente capafrente;
        capafrente = new CapaDeFrente();

        EscenaDevolver.addChild(capafondo, -10);
        EscenaDevolver.addChild(capafrente, 10);

        return EscenaDevolver;
    }

    class CapaDeFondo extends Layer {
        public CapaDeFondo() {
            ponerImagenFondo();
        }

        private void ponerImagenFondo() {
            ImagenFondo = Sprite.sprite("fondo.png");
            ImagenFondo.setPosition(PantallaDis.width / 2, PantallaDis.height / 2);
            ImagenFondo.runAction(ScaleBy.action(0.01f, 3.0f, 4.0f));
            super.addChild(ImagenFondo);
        }
    }

    class CapaDeFrente extends Layer {
        public CapaDeFrente() {
            Log.d("CapaFrente", "Constructor");

            Log.d("CapaFrente", "Jugador en pos inicial");
            PonerNavePosicionInicial();

            TimerTask timerenemigos;
            timerenemigos = new TimerTask() {
                @Override
                public void run() {
                    PonerEnemigo();
                }
            };

            Timer RelojEnemigos;
            RelojEnemigos = new Timer();
            RelojEnemigos.schedule(timerenemigos, 0, 1000);
        }

        private void PonerNavePosicionInicial() {
            Log.d("Poner Nave Jugador", "comienza");

            Nave = Sprite.sprite("rocket_mini.png");
            float PosIX, PosIY;
            PosIX = PantallaDis.width / 2;
            PosIY = PantallaDis.getHeight() / 2;
            Nave.setPosition(PosIX, PosIY);
            super.addChild(Nave);
        }

        void PonerEnemigo() {
            Enemigo = Sprite.sprite("enemigo.gif");
            CCPoint PosInicial;
            PosInicial = new CCPoint();
            float Alturaenemigo = Enemigo.getHeight();
            float AnchoEnemigo = Enemigo.getWidth();

            Random r = new Random();
            PosInicial.y = PantallaDis.height + Alturaenemigo / 2;
            PosInicial.x = r.nextInt((int) PantallaDis.width - (int) AnchoEnemigo) + AnchoEnemigo / 2;
            Enemigo.setPosition(PosInicial.x, PosInicial.y);
            //Enemigo.runAction(RotateTo.action(0.01f,180f));

            CCPoint PosFinal;
            PosFinal = new CCPoint();
            PosFinal.x = PosInicial.x;
            PosFinal.y = -Alturaenemigo / 2;
            Enemigo.runAction(MoveTo.action(3, PosFinal.x, PosFinal.y));
            super.addChild(Enemigo);

        }

        boolean EstaEntre(int NumeroaComparar, int NumeroMenor, int NumeroMayor) {
            boolean Devolver;

            if (NumeroMenor > NumeroMayor) {
                int aux;
                aux = NumeroMayor;
                NumeroMayor = NumeroMenor;
                NumeroMenor = aux;
            }
            if (NumeroaComparar >= NumeroMenor && NumeroaComparar <= NumeroMayor) {
                Devolver = true;
            } else {
                Devolver = false;
            }
            return Devolver;
        }

        boolean InterseccionEntreSprites(Sprite Sprite1, Sprite Sprite2) {
            boolean Devolver;
            Devolver = false;

            int Sprite1Izquierda, Sprite1Derecha, Sprite1Abajo, Sprite1Arriba;
            int Sprite2Izquierda, Sprite2Derecha, Sprite2Abajo, Sprite2Arriba;
            Sprite1Izquierda = (int) (Sprite1.getPositionX() - Sprite1.getWidth() / 2);
            Sprite1Derecha = (int) (Sprite1.getPositionX() + Sprite1.getWidth() / 2);
            Sprite1Abajo = (int) (Sprite1.getPositionY() - Sprite1.getHeight() / 2);
            Sprite1Arriba = (int) (Sprite1.getPositionY() + Sprite1.getHeight() / 2);
            Sprite2Izquierda = (int) (Sprite2.getPositionX() - Sprite2.getWidth() / 2);
            Sprite2Derecha = (int) (Sprite2.getPositionX() + Sprite2.getWidth() / 2);
            Sprite2Abajo = (int) (Sprite2.getPositionY() - Sprite2.getHeight() / 2);
            Sprite2Arriba = (int) (Sprite2.getPositionY() + Sprite2.getHeight() / 2);

            //Borde izq y borde inf de Sprite 1 está dentro de Sprite 2
            if (EstaEntre(Sprite1Izquierda, Sprite2Izquierda, Sprite2Derecha) && EstaEntre(Sprite1Abajo, Sprite2Abajo, Sprite2Arriba)) {
                Devolver = true;
            }

            //Borde izq y borde sup de Sprite 1 está dentro de Sprite 2
            if (EstaEntre(Sprite1Izquierda, Sprite2Izquierda, Sprite2Derecha) && EstaEntre(Sprite1Arriba, Sprite2Abajo, Sprite2Arriba)) {
                Devolver = true;
            }

            //Borde der y borde sup de Sprite 1 está dentro de Sprite 2
            if (EstaEntre(Sprite1Derecha, Sprite2Izquierda, Sprite2Derecha) && EstaEntre(Sprite1Arriba, Sprite2Abajo, Sprite2Arriba)) {
                Devolver = true;
            }

            //Borde der y borde inf de Sprite 1 está dentro de Sprite 2
            if (EstaEntre(Sprite1Derecha, Sprite2Izquierda, Sprite2Derecha) && EstaEntre(Sprite1Abajo, Sprite2Abajo, Sprite2Arriba)) {
                Devolver = true;
            }

            //Borde izq y borde inf de Sprite 2 está dentro de Sprite 1
            if (EstaEntre(Sprite2Izquierda, Sprite1Izquierda, Sprite1Derecha) && EstaEntre(Sprite2Abajo, Sprite1Abajo, Sprite1Arriba)) {
                Devolver = true;
            }

            //Borde izq y borde sup de Sprite 1 está dentro de Sprite 1
            if (EstaEntre(Sprite2Izquierda, Sprite1Izquierda, Sprite1Derecha) && EstaEntre(Sprite2Arriba, Sprite1Abajo, Sprite1Arriba)) {
                Devolver = true;
            }

            //Borde der y borde sup de Sprite 2 está dentro de Sprite 1
            if (EstaEntre(Sprite2Derecha, Sprite1Izquierda, Sprite1Derecha) && EstaEntre(Sprite2Arriba, Sprite1Abajo, Sprite1Arriba)) {
                Devolver = true;
            }

            //Borde der y borde inf de Sprite 2 está dentro de Sprite 1
            if (EstaEntre(Sprite2Derecha, Sprite1Izquierda, Sprite1Derecha) && EstaEntre(Sprite2Abajo, Sprite1Abajo, Sprite1Arriba)) {
                Devolver = true;
            }
            return Devolver;
        }


    }



}
