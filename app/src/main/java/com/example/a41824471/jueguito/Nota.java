package com.example.a41824471.jueguito;

import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.Texture2D;


public class Nota{
    String tipo;
    Sprite sprite;
    String sonido;
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

    public String getSonido() {
        return sonido;
    }

    public boolean isApretada() {
        return apretada;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setSonido(String sonido) {
        this.sonido = sonido;
    }

    public void setApretada(boolean apretada) {
        this.apretada = apretada;
    }
}
