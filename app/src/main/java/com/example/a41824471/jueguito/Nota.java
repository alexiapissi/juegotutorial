package com.example.a41824471.jueguito;

import android.media.MediaPlayer;
import android.provider.MediaStore;

import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.Texture2D;


public class Nota{
    String tipo;
    Sprite sprite;
    MediaPlayer sonido;
    boolean apretada;

    public Nota( String gifFile) {
        sprite = Sprite.sprite(gifFile);
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Sprite getSprite() {
        return sprite;
    }


    public boolean isApretada() {
        return apretada;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setApretada(boolean apretada) {
        this.apretada = apretada;
    }

    public MediaPlayer getSonido() {
        return sonido;
    }

    public void setSonido(MediaPlayer sonido) {
        this.sonido = sonido;
    }
}
