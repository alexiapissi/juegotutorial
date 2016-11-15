package com.example.a41824471.jueguito;

import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.Texture2D;


public class Nota{
    int Id;
    String tipo;
    Sprite sprite;

    public Nota( String gifFile) {
        sprite = Sprite.sprite(gifFile);
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
