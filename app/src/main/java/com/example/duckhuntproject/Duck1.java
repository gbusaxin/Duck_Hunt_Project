package com.example.duckhuntproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

import static com.example.duckhuntproject.GameView.dWidth;

public class Duck1 {
    Bitmap[] duck = new Bitmap[8];
    int duckX, duckY, velocity, duckFrame;
    Random random;



    public Duck1(Context context) {
        duck[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.bird1);
        duck[1] = BitmapFactory.decodeResource(context.getResources(),R.drawable.bird2);
        duck[2] = BitmapFactory.decodeResource(context.getResources(),R.drawable.bird3);
        duck[3] = BitmapFactory.decodeResource(context.getResources(),R.drawable.bird4);
        duck[4] = BitmapFactory.decodeResource(context.getResources(),R.drawable.bird5);
        duck[5] = BitmapFactory.decodeResource(context.getResources(),R.drawable.bird6);
        duck[6] = BitmapFactory.decodeResource(context.getResources(),R.drawable.bird7);
        duck[7] = BitmapFactory.decodeResource(context.getResources(),R.drawable.bird8);
        random = new Random();
        duckFrame = 0;
        resetPosition();
    }

    public Bitmap getBitmap(){
        return duck[duckFrame];
    }

    public int getWidth(){
        return duck[0].getWidth();
    }

    public int getHeight(){
        return duck[0].getHeight();
    }

    public void resetPosition(){
        duckX = GameView.dWidth + random.nextInt(1200);
        duckY = random.nextInt(300);
        velocity = 14 + random.nextInt(17);
    }

}
