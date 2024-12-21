package ru.itschool.newyear;

import static ru.itschool.newyear.Main.*;

import com.badlogic.gdx.math.MathUtils;

public class SnowFlake {
    public float x;
    public float y;
    public float width;
    public float height;
    float stepX;
    float stepY;
    float rotation;
    float speedRotation;

    public SnowFlake(){
        respawn();
        this.y = MathUtils.random(0, SCR_HEIGHT);
        speedRotation = MathUtils.random(-0.1f, 0.1f);
    }

    public void fly(){
        x += stepX;
        y += stepY;
        if (y < - height) respawn();
        rotation += speedRotation;
    }

    void respawn(){
        width = height = MathUtils.random(10, 30);
        this.x = MathUtils.random(0, SCR_WIDTH);
        this.y = MathUtils.random(SCR_HEIGHT+height, SCR_HEIGHT*2);
        stepX = MathUtils.random(-0.2f, 0.2f);
        stepY = MathUtils.random(-1f, -0.5f);
    }

    boolean hit(float tx, float ty){
        return x<tx && tx<x+width && y<ty && ty<y+height;
    }
}
