package com.example.a41824471.jueguito;

import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.Texture2D;


public class Nota extends Sprite{
    int Id;

    public Nota(Texture2D tex, int id) {
        super(tex);
        Id = id;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
