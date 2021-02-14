package com.example.duckhuntproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class GameView extends View {

    Bitmap background;
    Rect rect;
    static int dWidth, dHeight;
    Handler handler;
    Runnable runnable;
    final long UPDATE_MILLIS = 30;
    ArrayList<Duck1> duck1;
    ArrayList<Duck2> duck2;
    Bitmap ball, target;
    float ballX, ballY;
    float sX, sY;
    float fX, fY;
    float dX, dY;
    float tempX, tempY;
    Paint borderPaint;
    int score = 0;
    int life = 25;
    Context context;
    MediaPlayer duckHit, duckMiss, ball_throw;
    boolean gameState = true;

    public GameView(Context context) {
        super(context);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background_day);
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        rect = new Rect(0, 0, dWidth, dHeight);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        duck1 = new ArrayList<>();
        duck2 = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Duck1 duck_1 = new Duck1(context);
            duck1.add(duck_1);
            Duck2 duck_2 = new Duck2(context);
            duck2.add(duck_2);
        }
        ball = BitmapFactory.decodeResource(getResources(), R.drawable.bullet);
        target = BitmapFactory.decodeResource(getResources(), R.drawable.crosshair);
        ballX = ballY = 0;
        sX = sY = fX = fY = 0;
        dX = dY = 0;
        tempX = tempY = 0;
        borderPaint = new Paint();
        borderPaint.setColor(Color.RED);
        borderPaint.setStrokeWidth(5);
        this.context = context;
        duckHit = MediaPlayer.create(context,R.raw.shoot_sound);
        duckMiss = MediaPlayer.create(context,R.raw.miss);
        ball_throw = MediaPlayer.create(context,R.raw.bird_hit);

//        final WindowMetrics metrics = ((Activity)getContext()).getWindowManager().getCurrentWindowMetrics();
//        final WindowInsets windowInsets = metrics.getWindowInsets();
//        Insets insets = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars()
//                | WindowInsets.Type.displayCutout());
//        dWidth = insets.right + insets.left;
//        dHeight = insets.top + insets.bottom;
//        final Rect bounds = metrics.getBounds();
//        final Size legacySize = new Size(bounds.width() - dWidth,
//                bounds.height() - dHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (life < 1){
            gameState = false;
            Intent intent = new Intent(context, GameEnd.class);
            intent.putExtra("score", score);
            ((Activity)context).startActivity(intent);
            ((Activity)context).finish();
        }
        canvas.drawBitmap(background, null, rect, null);
        for (int i = 0; i < duck1.size(); i++) {
            canvas.drawBitmap(duck1.get(i).getBitmap(),
                    duck1.get(i).duckX, duck1.get(i).duckY, null);
            duck1.get(i).duckFrame++;

            if (duck1.get(i).duckFrame > 7) {
                duck1.get(i).duckFrame = 0;
            }

            duck1.get(i).duckX -= duck1.get(i).velocity;

            if (duck1.get(i).duckX < -duck1.get(i).getWidth()) {
                duck1.get(i).resetPosition();
                life--;
                if (duckMiss != null)
                    duckMiss.start();
            }

            canvas.drawBitmap(duck2.get(i).getBitmap(),
                    duck2.get(i).duckX, duck2.get(i).duckY, null);

            duck2.get(i).duckFrame++;
            if (duck2.get(i).duckFrame > 3) {
                duck2.get(i).duckFrame = 0;
            }
            duck2.get(i).duckX -= duck2.get(i).velocity;
            if (duck2.get(i).duckX < -duck2.get(i).getWidth()) {
                duck2.get(i).resetPosition();
                life--;
                if (duckMiss != null)
                    duckMiss.start();
            }

            if (ballX <= (duck1.get(i).duckX + duck1.get(i).getWidth()) && ballX + ball.getWidth() >=
            duck1.get(i).duckX && ballY <= (duck1.get(i).duckY + duck1.get(i).getHeight())&&
            ballY >= duck1.get(i).duckY){
                duck1.get(i).resetPosition();
                score++;
                if (duckHit != null)
                    duckHit.start();
            }
            if (ballX <= (duck2.get(i).duckX + duck2.get(i).getWidth()) && ballX + ball.getWidth() >=
                    duck2.get(i).duckX && ballY <= (duck2.get(i).duckY + duck2.get(i).getHeight())&&
                    ballY >= duck2.get(i).duckY){
                duck2.get(i).resetPosition();
                score++;
                if (duckHit != null)
                    duckHit.start();
            }

        }
        if (sX > 0 && sY > dHeight * .75f) {
            canvas.drawBitmap(target,
                    sX - target.getWidth() / 2, sY - target.getHeight() / 2, null);

        }
        if ((Math.abs(fX - sX) > 0 || Math.abs(fY - sY) > 0) && fY > 0 && fY > dHeight * .75f) {
            canvas.drawBitmap(target,
                    fX - target.getWidth() / 2, fY - target.getHeight() / 2, null);

        }
        if ((Math.abs(dX) > 10 || Math.abs(dY) > 10) && sY > dHeight * .75f && fY > dHeight * .75f) {
            ballX = fX - ball.getWidth() / 2 - tempX;
            ballY = fY - ball.getHeight() / 2 - tempY;
            canvas.drawBitmap(ball, ballX, ballY, null);
            tempX += dX;
            tempY += dY;
        }
        canvas.drawLine(0, dHeight * .75f, dWidth, dHeight * .75f, borderPaint);
        if (gameState)
        handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dX = dY = fX = fY = tempX = tempY = 0;
                sX = event.getX();
                sY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                fX = event.getX();
                fY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                fX = event.getX();
                fY = event.getY();
                ballX = event.getX();
                ballY = event.getY();
                dX = fX - sX;
                dY = fY - sY;
                break;
        }
        return true;
    }
}
