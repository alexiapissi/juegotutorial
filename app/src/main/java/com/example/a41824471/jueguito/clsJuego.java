package com.example.a41824471.jueguito;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 41824471 on 13/9/2016.
 */
public class clsJuego {
    CCGLSurfaceView _VistaJuego;
    CCSize PantallaDis;
    Sprite ImagenFondo;
    Sprite linea;
    Sprite NotaSOL;
    Sprite NotaRe;
    ArrayList<Sprite> arraynotas;
    public static int tamañotouch=50;

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
            ImagenFondo = Sprite.sprite("fondojuego.gif");
            ImagenFondo.setPosition(PantallaDis.width / 2, PantallaDis.height / 2);
            ImagenFondo.runAction(ScaleBy.action(0.01f, 3.0f, 4.0f));
            super.addChild(ImagenFondo);
        }
    }



    class CapaDeFrente extends Layer {
        public CapaDeFrente() {
            Initlinea();
            arraynotas= new ArrayList<>();
            this.setIsTouchEnabled(true);

            //timer para detectar colisiones

            TimerTask timerenemigos;
            timerenemigos = new TimerTask() {
                @Override
                public void run() {
                    PonerNotaRe();
                    PonerNotaSOL();
                }
            };

            Timer RelojEnemigos;
            RelojEnemigos = new Timer();
            RelojEnemigos.schedule(timerenemigos, 0, 2000);
        }

        void Initlinea (){
            linea = Sprite.sprite("lineaneon.gif");


            float posx, posy;
            float Altolinea = linea.getHeight();
            float Ancholinea=linea.getWidth();
            posy = PantallaDis.height/8;
            posx =0+Ancholinea/2;

            linea.setPosition(posx,posy);
            super.addChild(linea);
        }

           void PonerNotaSOL() {
               NotaSOL = Sprite.sprite("nota-sol.gif");

            CCPoint PosInicial;
            PosInicial = new CCPoint();
            float AlturaSol = NotaSOL.getHeight();
               float AnchoSol=NotaSOL.getWidth();


               //SOL
            PosInicial.y = PantallaDis.height + AlturaSol / 2;
            PosInicial.x = PantallaDis.width/4;
               NotaSOL.setPosition(PosInicial.x, PosInicial.y);
            //Enemigo.runAction(RotateTo.action(0.01f,180f));
            CCPoint PosFinal;
            PosFinal = new CCPoint();
            PosFinal.x = PosInicial.x;
            PosFinal.y = -AlturaSol / 2;
               NotaSOL.runAction(MoveTo.action(5, PosFinal.x, PosFinal.y));
               arraynotas.add(NotaSOL);
            super.addChild(NotaSOL);
        }

        void PonerNotaRe(){
            NotaRe=Sprite.sprite("nota-re.png");

            CCPoint PosInicial;
            PosInicial = new CCPoint();
            float AlturaRe=NotaRe.getHeight();
            float AnchoRe=NotaRe.getWidth();

            CCPoint PosFinal;
            PosFinal = new CCPoint();
            PosInicial.y=PantallaDis.height+AlturaRe/2;
            PosInicial.x=PantallaDis.width/4+PantallaDis.width/4;
            NotaRe.setPosition(PosInicial.x,PosInicial.y);

            PosFinal.x=PosInicial.x;
            PosFinal.y=-AlturaRe/2;
            NotaRe.runAction((MoveTo.action(5,PosFinal.x,PosFinal.y)));
            super.addChild(NotaRe);

        }

        public void Colisiones (){

        }


        public boolean ccTouchesBegan(MotionEvent event){
            int x= (int) event.getX();
            int y= (int) event.getY();
            Rect touchr= new Rect((x-tamañotouch)*13/10, (Math.round(PantallaDis.getHeight()-y-tamañotouch)*13/10), (x+tamañotouch)*13/10, (Math.round(PantallaDis.getHeight()-y+tamañotouch))*13/10);

            int lineax= (int) linea.getWidth()/2;
            int lineay= (int)  linea.getHeight()/2;
            int posxlinea= (int) linea.getPositionX();
            int posylinea= (int) linea.getPositionY();
             Rect linear= new Rect (posxlinea-lineax,Math.round(PantallaDis.getHeight()-posylinea-lineay),posxlinea+lineax,Math.round(PantallaDis.getHeight()-posylinea+lineay));
            Log.d("juego", "touch" + touchr);
            for(Sprite nota : arraynotas) {
                int notax = (int) nota.getWidth() / 2;
                int notay = (int) nota.getHeight() / 2;
                int solX = (int) nota.getPositionX();
                int solY = (int) nota.getPositionY();
                Rect notar = new Rect(solX - notax, Math.round(PantallaDis.getHeight() - solY - notay), solX + notax, Math.round(PantallaDis.getHeight() - solY + notay));
                if(nota.getHeight()>0){
                    arraynotas.remove(nota);
                }
                Log.d("juego", "nota" + notar);

                if (touchr.intersect(notar)) {
                    Log.d("juego", "interA");
                }
                if(notar.intersect(linear)){
                    Log.d("juego","interB");
                }
                if(linear.intersect(touchr)){
                    Log.d("juego","interC");
                }
                if(touchr.intersect(notar) && notar.intersect(linear)){
                    Log.d("juego","interTOTAL");
                }

            }
            return true;
        }
    }






}
