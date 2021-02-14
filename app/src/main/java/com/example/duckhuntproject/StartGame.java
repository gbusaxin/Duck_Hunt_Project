package com.example.duckhuntproject;

import android.media.MediaActionSound;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartGame extends AppCompatActivity {

    GameView gameView;
    MediaPlayer bg_music;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setContentView(gameView);
        bg_music = MediaPlayer.create(this,R.raw.bird_bg_sound);
        if (bg_music != null){
            bg_music.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (bg_music != null){
            bg_music.stop();
            bg_music.release();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bg_music != null){
            bg_music.stop();
            bg_music.release();
        }
    }
}
