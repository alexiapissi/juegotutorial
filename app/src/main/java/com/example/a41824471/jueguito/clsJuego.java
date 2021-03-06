package com.example.a41824471.jueguito;

import android.content.Context;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;

import org.cocos2d.actions.interval.MoveTo;
import org.cocos2d.actions.interval.ScaleBy;
import org.cocos2d.layers.Layer;
import org.cocos2d.menus.Menu;
import org.cocos2d.menus.MenuItemImage;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Label;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCPoint;
import org.cocos2d.types.CCSize;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class clsJuego {
    CCGLSurfaceView _VistaJuego;
    CCSize PantallaDis;
    Sprite ImagenFondo;
    Sprite linea;
    ArrayList<Nota> arraynotas;
    public static int tamañotouch = 50;
    Rect touchr;
    boolean tocando = false;
    Rect linear;
    MenuItemImage botonjugar;
    MenuItemImage botonvolver;
    boolean juganodo=true;
    int puntaje = 0;
    int extratiempo=0;
    MediaPlayer mpAplauso;
    Label lblPuntaje;
    Context context;
    public int tiempo=0;
    ArrayList<Nota> cancion;
    ArrayList<Nota> cancionEstrellita;
    Nota notax;
    Sprite cuerda;
    MediaPlayer mpSol;
    MediaPlayer mpRe;
    MediaPlayer mpLa;
    MediaPlayer mpMi;

    public clsJuego(CCGLSurfaceView Vistadeljuego, Context context) {
        _VistaJuego = Vistadeljuego;
        this.context = context;
    }


    public void EmpezarApp(){
        Director.sharedDirector().attachInView(_VistaJuego);
        PantallaDis = Director.sharedDirector().displaySize();
        Director.sharedDirector().runWithScene(EscenaMenu());
    }

    private Scene EscenaMenu(){
        Scene EscenaMenuDevolver;
        EscenaMenuDevolver=Scene.node();

        CapaDeFondo capafondo;
        capafondo= new CapaDeFondo();

        CapaMenuFrente capamenufrente;
        capamenufrente= new CapaMenuFrente();

        EscenaMenuDevolver.addChild(capafondo, -10);
        EscenaMenuDevolver.addChild(capamenufrente, 10);

        return EscenaMenuDevolver;
    }
    class CapaMenuFrente extends Layer{
        public CapaMenuFrente(){
            tiempo=0;
            extratiempo=0;
            Label lblTitulo;
            lblTitulo = Label.label("ViolinGame", "Verdana", 100);
            lblTitulo.setString("Violin Game");
            lblTitulo.setPosition(PantallaDis.width / 2, PantallaDis.height/2);

            botonjugar=MenuItemImage.item("play.png", "play.png",this,"Comenzarjuego");
            float posbotonx, posbotony;
            posbotonx=PantallaDis.width / 2;
            posbotony=PantallaDis.height/2 -botonjugar.getHeight();
            botonjugar.setPosition(posbotonx,posbotony);

            Menu botones= Menu.menu(botonjugar);
            //botones.setPosition(PantallaDis.width/2- botones.getWidth()/2, PantallaDis.getHeight()-2- (botonjugar.getHeight()+90));
            botones.setPosition(0, 0);

            addChild(lblTitulo);
            addChild(botones);

        }

        private Nota getNota(int num) {
            Nota nota =new Nota("nota-mi.png");

            switch (num) {
                case 0:
                    nota = new Nota("nota-sol.png");
                    nota.setTipo("sol");
                    mpSol = MediaPlayer.create(context, R.raw.sol);
                    nota.setSonido(mpSol);
                    nota.setApretada(false);
                    break;
                case 1:
                    nota = new Nota("nota-re.png");
                    nota.setTipo("re");
                    mpRe = MediaPlayer.create(context, R.raw.re);
                    nota.setSonido(mpRe);
                    nota.setApretada(false);
                    break;
                case 2:
                    nota = new Nota("nota-la.png");
                    nota.setTipo("la");
                    mpLa = MediaPlayer.create(context, R.raw.la);
                    nota.setSonido(mpLa);
                    nota.setApretada(false);
                    break;
                case 3:
                    nota = new Nota("nota-mi.png");
                    nota.setTipo("mi");
                    mpMi = MediaPlayer.create(context, R.raw.mi);
                    nota.setSonido(mpMi);
                    nota.setApretada(false);
                    break;
            }
            return nota;
        }
        public void Comenzarjuego() {
                       Random random;
            cancion= new ArrayList<>();
            random=new Random();
            int num;
            for(int i=1; i<=20; i++) {
            num=random.nextInt(4);
            cancion.add(getNota(num));
            }
            tiempo=0;
            extratiempo=0;
            juganodo=true;
            puntaje=0;

            cancionEstrellita=new ArrayList<>();

            Director.sharedDirector().runWithScene(EscenaJuego());
        }
    }

    private Scene EscenaFinal(){
        Scene EscenaDevolver;
        EscenaDevolver = Scene.node();

        CapaDeFondo capafondo;
        capafondo = new CapaDeFondo();

        CapaFinalFrente capafinalfrente;
        capafinalfrente = new CapaFinalFrente();

        EscenaDevolver.addChild(capafondo, -10);
        EscenaDevolver.addChild(capafinalfrente, 10);


        return EscenaDevolver;
    }

    class CapaFinalFrente extends Layer{
        public CapaFinalFrente(){
            mpAplauso = MediaPlayer.create(context, R.raw.aplausos);
            mpAplauso.start();
            Label lblmsg;
            lblmsg = Label.label("¡Canción Terminada!", "Verdana", 70);
            lblmsg.setString("¡Canción Terminada!");
            lblmsg.setPosition(PantallaDis.width / 2, PantallaDis.height/2);

            Label lblShowP;
            lblShowP = Label.label("Puntaje:" +puntaje, "Verdana", 70);
            lblShowP.setString("Puntaje:" +puntaje);
            lblShowP.setPosition(PantallaDis.width / 2, PantallaDis.height/2-lblmsg.getHeight());

            botonvolver=MenuItemImage.item("flecha.png", "flecha.png",this,"Iramenu");
            float posbotonx, posbotony;
            posbotonx=PantallaDis.width / 2;
            posbotony=PantallaDis.height/2 -botonvolver.getHeight();
            botonvolver.setPosition(posbotonx,posbotony);

            Menu botones= Menu.menu(botonvolver);
            //botones.setPosition(PantallaDis.width/2- botones.getWidth()/2, PantallaDis.getHeight()-2- (botonjugar.getHeight()+90));
            botones.setPosition(0, 0);

            addChild(lblmsg);
            addChild(lblShowP);
            addChild(botones);
        }
        public void Iramenu() {
            Director.sharedDirector().runWithScene(EscenaMenu());
        }

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

            float pos=PantallaDis.width / 5;
            PonerCuerda(pos);
            pos=PantallaDis.width/5+PantallaDis.width/5;
            PonerCuerda(pos);
            pos=PantallaDis.width/5+PantallaDis.width/5+PantallaDis.width/5;
            PonerCuerda(pos);
            pos=PantallaDis.width/5+PantallaDis.width/5+PantallaDis.width/5+PantallaDis.width/5;
            PonerCuerda(pos);
        }

        void PonerCuerda(float posx) {
            cuerda = Sprite.sprite("cuerda.png");
            cuerda.runAction(ScaleBy.action(0.01f, 1.0f, 1.0f));

            float posy;
            float altocuerda = cuerda.getHeight();
            posy = PantallaDis.height-(altocuerda/2);
            cuerda.setPosition(posx, posy);
            cuerda.runAction(ScaleBy.action(0.01f, 1.0f, 4.0f));
            super.addChild(cuerda);
        }

        private void ponerImagenFondo() {
            ImagenFondo = Sprite.sprite("madera4.png");
            ImagenFondo.setPosition(PantallaDis.width / 2, PantallaDis.height / 2);
            ImagenFondo.runAction(ScaleBy.action(0.01f, 2.0f, 2.0f));
            super.addChild(ImagenFondo);
        }
    }


    class CapaDeFrente extends Layer {


        public CapaDeFrente() {
            Initlinea();
            arraynotas = new ArrayList<>();
            this.setIsTouchEnabled(true);
            int lineax = (int) linea.getWidth() / 2;
            int lineay = (int) linea.getHeight();
            int posxlinea = (int) linea.getPositionX();
            int posylinea = (int) linea.getPositionY();
            linear = new Rect(posxlinea - lineax, Math.round(PantallaDis.getHeight() - posylinea - lineay), posxlinea + lineax, Math.round(PantallaDis.getHeight() - posylinea + lineay));
            Log.d("linear:", "linea+" + linear);
            lblPuntaje = Label.label("" + puntaje, "Verdana", 30);


            TimerTask TColisiones;
            TColisiones = new TimerTask() {
                @Override
                public void run() {
                    DetectarColisiones();
                }
            };
            Timer RelojColisiones;
            RelojColisiones = new Timer();
            RelojColisiones.schedule(TColisiones, 0, 100);


            final TimerTask timerenemigos;
            timerenemigos = new TimerTask() {
                @Override
                public void run() {
                    if (tiempo <= cancion.size() - 1) {
                        notax = cancion.get(tiempo);
                        PonerNota(notax);
                        tiempo++;
                        Log.d("timernotas","puse nota:"+notax.getTipo());
                    } else {
                        if(juganodo){
                            extratiempo++;
                            Log.d("tiempo","extra"+extratiempo);
                            if(extratiempo>=5){
                                juganodo=false;
                                Director.sharedDirector().runWithScene(EscenaFinal());
                            }
                        }
                    }
                }


            };


            Timer RelojEnemigos;
            RelojEnemigos = new Timer();
            RelojEnemigos.schedule(timerenemigos, 0, 1000);

            PonerTitulo();

        }




        void Initlinea() {
            linea = Sprite.sprite("lineaneon.gif");
            linea.runAction(ScaleBy.action(0.01f, 1.5f, 1.5f));


            float posx, posy;
            float Ancholinea = linea.getWidth();
            posy = PantallaDis.height / 8;
            posx = 0 + Ancholinea / 2;

            linea.setPosition(posx, posy);
            super.addChild(linea);
        }

        void PonerNota(Nota nota){

            CCPoint PosInicial;
            PosInicial = new CCPoint();
            float Altura = nota.getSprite().getHeight();

            if (nota.getTipo().equals("sol")){
                PosInicial.x = PantallaDis.width / 5;
            }
            if(nota.getTipo().equals("re")){
                PosInicial.x=PantallaDis.width*2/5;
            }
            if (nota.getTipo().equals("la")){
                PosInicial.x=PantallaDis.width*3/5;
            }
            if(nota.getTipo().equals("mi")){
                PosInicial.x=PantallaDis.width*4/5;
            }
            PosInicial.y=PantallaDis.height+Altura/2;

            nota.getSprite().setPosition(PosInicial.x, PosInicial.y);
            //Enemigo.runAction(RotateTo.action(0.01f,180f));
            CCPoint PosFinal;
            PosFinal = new CCPoint();
            PosFinal.x = PosInicial.x;
            //PosFinal.y = -Altura / 2;
            PosFinal.y = - nota.getSprite().getHeight()/2;
            nota.getSprite().runAction(MoveTo.action(5, PosFinal.x, PosFinal.y));
            arraynotas.add(nota);
            super.addChild(nota.getSprite());

        }

        private void PonerTitulo() {
            lblPuntaje.setString("" + puntaje);
            float AltoTitulo;
            AltoTitulo = lblPuntaje.getHeight();
            lblPuntaje.setPosition(PantallaDis.width / 2, PantallaDis.height - AltoTitulo / 2);
            super.addChild(lblPuntaje);

        }




        public boolean ccTouchesBegan(MotionEvent event) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            touchr = new Rect(x - tamañotouch, y - tamañotouch, x + tamañotouch, y + tamañotouch);
            Log.d("juego", "touch" + touchr);
            tocando = true;
            Log.d("inter touch", "empieza");
            return true;
        }

        public boolean ccTouchesEnded(MotionEvent event) {
            Log.d("inter", "termina");
            tocando = false;
            return true;
        }

        void DetectarColisiones() {
            Nota nota;
            for (int i = 0; i < arraynotas.size(); i++) {
                nota = arraynotas.get(i);
                int notax = (int) nota.getSprite().getWidth() * 2;
                int notay = (int) nota.getSprite().getHeight() * 2;
                int solX = (int) nota.getSprite().getPositionX();
                int solY = (int) nota.getSprite().getPositionY();
                Rect notar = new Rect(solX - notax, Math.round(PantallaDis.getHeight() - solY - notay), solX + notax, Math.round(PantallaDis.getHeight() - solY + notay));
                if (nota.getSprite().getPositionY() < 0) {
                    if(!nota.isApretada()){
                        puntaje--;
                        PonerTitulo();
                    }
                    arraynotas.remove(nota);
                    Log.d("detectar colis tocando", "remuevo nota" + nota.getTipo());
                }
            if (tocando) {
                if (!touchr.intersect(linear)) {
                    Log.d("detectar colis fuera", "linea" + linear);
                    return;
                }
                    if ( notar.intersect(linear)) {
                       if(!nota.isApretada()) {
                           Log.d("juego", "interaTOTAL");
                           puntaje = puntaje + 5;
                          nota.getSonido().start();
                       }
                        nota.setApretada(true);
                        PonerTitulo();
                    }
                }
            }
        }
    }
}








