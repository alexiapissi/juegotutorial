package com.example.a41824471.jueguito;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.provider.MediaStore;
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


public class clsJuego {
    CCGLSurfaceView _VistaJuego;
    CCSize PantallaDis;
    Sprite ImagenFondo;
    Sprite linea;
    Nota NotaSOL;
    Nota NotaRe;
    ArrayList<Nota> arraynotas;
    public static int tamañotouch=50;
    Rect touchr;
    boolean  tocando=false;
    Rect linear;
    int puntaje=0;
    Label lblPuntaje;
    Context context;

    public clsJuego(CCGLSurfaceView Vistadeljuego, Context context) {
        _VistaJuego = Vistadeljuego;
        this.context = context;
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
            int lineax= (int) linea.getWidth()/2;
            int lineay= (int)  linea.getHeight();
            int posxlinea= (int) linea.getPositionX();
            int posylinea= (int) linea.getPositionY();
            linear= new Rect (posxlinea-lineax,Math.round(PantallaDis.getHeight()-posylinea-lineay),posxlinea+lineax,Math.round(PantallaDis.getHeight()-posylinea+lineay));
            Log.d("linear:","linea+"+linear);
            lblPuntaje=Label.label(""+puntaje,"Verdana",30);

            TimerTask TColisiones;
            TColisiones=new TimerTask(){
                @Override
                public void run(){
                    DetectarColisiones();
                }
            };
            Timer RelojColisiones;
            RelojColisiones=new Timer();
            RelojColisiones.schedule(TColisiones,0,100);


            TimerTask timerenemigos;
            timerenemigos = new TimerTask() {
                @Override
                public void run() {
                    PonerNotaSOL();
                    PonerNotaRe();
                }
            };

            Timer RelojEnemigos;
            RelojEnemigos = new Timer();
            RelojEnemigos.schedule(timerenemigos, 0, 2000);

            PonerTitulo();

        }

        MediaPlayer mpSol;
        MediaPlayer mpRe;

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
               //NotaSOL = Sprite.sprite("nota-sol.gif");
               NotaSOL= new Nota("nota-sol.gif");
               NotaSOL.setTipo("Sol");

            CCPoint PosInicial;
            PosInicial = new CCPoint();
            float AlturaSol = NotaSOL.getSprite().getHeight();
               float AnchoSol=NotaSOL.getSprite().getWidth();


               //SOL
            PosInicial.y = PantallaDis.height + AlturaSol / 2;
            PosInicial.x = PantallaDis.width/4;
               NotaSOL.getSprite().setPosition(PosInicial.x, PosInicial.y);
            //Enemigo.runAction(RotateTo.action(0.01f,180f));
            CCPoint PosFinal;
            PosFinal = new CCPoint();
            PosFinal.x = PosInicial.x;
            PosFinal.y = -AlturaSol / 2;
               NotaSOL.getSprite().runAction(MoveTo.action(5, PosFinal.x, PosFinal.y));
               arraynotas.add(NotaSOL);
            super.addChild(NotaSOL.getSprite());
        }

        void PonerNotaRe(){
            //NotaRe=Sprite.sprite("nota-re.png");
            NotaRe= new Nota("nota-re.png");
            NotaRe.setTipo("re");

            CCPoint PosInicial;
            PosInicial = new CCPoint();
            float AlturaRe=NotaRe.getSprite().getHeight();
            float AnchoRe=NotaRe.getSprite().getWidth();

            CCPoint PosFinal;
            PosFinal = new CCPoint();
            PosInicial.y=PantallaDis.height+AlturaRe/2;
            PosInicial.x=PantallaDis.width/4+PantallaDis.width/4;
            NotaRe.getSprite().setPosition(PosInicial.x,PosInicial.y);

            PosFinal.x=PosInicial.x;
            PosFinal.y=-AlturaRe/2;
            NotaRe.getSprite().runAction((MoveTo.action(5,PosFinal.x,PosFinal.y)));
            arraynotas.add(NotaRe);
            super.addChild(NotaRe.getSprite());

        }

        private void PonerTitulo(){
            lblPuntaje.setString(""+puntaje);
            float AltoTitulo;
            AltoTitulo=lblPuntaje.getHeight();
            lblPuntaje.setPosition(PantallaDis.width/2,PantallaDis.height-AltoTitulo/2);
            super.addChild(lblPuntaje);

        }

        public boolean ccTouchesBegan(MotionEvent event){
            int x= (int) event.getX();
            int y= (int) event.getY();
            touchr= new Rect (x-tamañotouch, y-tamañotouch, x+tamañotouch, y+tamañotouch);
            Log.d("juego", "touch" + touchr);
            tocando=true;
            Log.d("inter touch","empeiza");
            return true;
        }

        public boolean ccTouchesEnded(MotionEvent event){
            Log.d("inter","termina");
            tocando=false;
            return  true;
        }

        void DetectarColisiones(){
            boolean hubocolision;
            hubocolision=false;
            Nota nota;
            if(tocando==true) {
                Log.d("inter", "touch" + touchr);
                Log.d("inter","touch"+linear);
                /*if (touchr.intersect(linear)) {
                    hubocolision = true;
                    Log.d("juego", "interaC");
                }*/

                for (int i=0; i<arraynotas.size();i++) {
                     nota=arraynotas.get(i);
                    int notax = (int) nota.getSprite().getWidth()*2;
                    int notay = (int) nota.getSprite().getHeight()*2;
                    int solX = (int) nota.getSprite().getPositionX();
                    int solY = (int) nota.getSprite().getPositionY();
                    Rect notar = new Rect(solX - notax, Math.round(PantallaDis.getHeight() - solY - notay), solX + notax, Math.round(PantallaDis.getHeight() - solY + notay));
                    if (nota.getSprite().getPositionY() <= 0) {
                        arraynotas.remove(nota);
                    }
                    Log.d("inter"+i, "nota" + notar);
                    Log.d("inter"+i, "touch" + touchr);
                    Log.d("inter","touch"+linear);


                    if (touchr.intersect(notar)) {
                        Log.d("juego", "interaA");
                        hubocolision = true;
                    }
                    if (notar.intersect(linear)) {
                        Log.d("juego", "interaB");
                        hubocolision = true;
                    }

                    if (touchr.intersect(notar) && notar.intersect(linear)) {
                        hubocolision = true;
                        Log.d("juego", "interaTOTAL");
                        puntaje=puntaje+5;
                        if(arraynotas.get(i).getTipo().equals("sol")){
                            mpSol=MediaPlayer.create(context, R.raw.sol);
                            mpSol.start();
                        }
                        if(arraynotas.get(i).getTipo().equals("re")){
                            mpRe=MediaPlayer.create(context, R.raw.re);
                            mpRe.start();
                        }

                        PonerTitulo();

                    }else{
                        puntaje--;
                        PonerTitulo();

                    }

                }
            }

        }
    }






}
