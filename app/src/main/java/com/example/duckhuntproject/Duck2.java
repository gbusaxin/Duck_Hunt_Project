package com.example.duckhuntproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Duck2 extends Duck1 {

    Bitmap[] duck = new Bitmap[4];

    public Duck2(Context context) {
        super(context);
        duck[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.duck1);
        duck[1] = BitmapFactory.decodeResource(context.getResources(),R.drawable.duck2);
        duck[2] = BitmapFactory.decodeResource(context.getResources(),R.drawable.duck3);
        duck[3] = BitmapFactory.decodeResource(context.getResources(),R.drawable.duck4);
        resetPosition();
    }

    @Override
    public Bitmap getBitmap() {
        return duck[duckFrame];
    }

    @Override
    public int getWidth() {
        return duck[0].getWidth();
    }

    @Override
    public int getHeight() {
        return duck[0].getHeight();
    }

    @Override
    public void resetPosition() {
        duckX = GameView.dWidth + random.nextInt(1500);
        duckY = random.nextInt(400);
        velocity = 16 + random.nextInt(19);
    }
}
