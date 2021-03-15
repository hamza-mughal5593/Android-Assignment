package com.renesis.tech.androidassignment.quizgame.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class BackgroundMusic {
    MediaPlayer m = null;
    MediaPlayer soundPlayer = null;

    private static BackgroundMusic myInstance = null;
    private Context mContext;
    Vibrator v;

    public static BackgroundMusic getInstance(Context mcontext) {
        if (myInstance == null) {
            myInstance = new BackgroundMusic(mcontext);
        }
        return myInstance;
    }

    public BackgroundMusic(Context mcontext) {
        this.mContext = mcontext;
    }

    public void playmusic_background(Activity activity, String audio) {


        String check_music = Utilities.getString(activity, History.check_music, "yes");
        if (check_music.equalsIgnoreCase("yes")) {
            try {
                if (m != null) {

                    if (m.isPlaying()) {
                        m.stop();
                        m.release();
                        m = new MediaPlayer();
                    }
                } else {
                    m = new MediaPlayer();
                }

                AssetFileDescriptor descriptor = activity.getAssets().openFd(audio);
                m.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
                descriptor.close();

                m.prepare();
                m.setVolume(1f, 1f);
                m.setLooping(true);
                m.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    public void Stopmusic_background() {

        if (m != null) {

            if (m.isPlaying()) {
                m.stop();
                m.release();
                m = new MediaPlayer();
            }
        }
    }

    public void pausemusic(){
        if (m!=null){
            if(m.isPlaying()){
                m.pause();
            }
        }
    }
    public void playmusic(){
        if (m!=null){

            if(!m.isPlaying()){
                m.start();
            }
        }
    }

    public void Playsound(Activity activity, String audio) {


        String check_sound = Utilities.getString(activity, History.check_sound, "yes");
        if (check_sound.equalsIgnoreCase("yes")) {
            try {
                if (soundPlayer != null) {

                    if (soundPlayer.isPlaying()) {
                        soundPlayer.stop();
                        soundPlayer.release();
                        soundPlayer = new MediaPlayer();
                    } else {
                        soundPlayer = new MediaPlayer();
                    }
                } else {
                    soundPlayer = new MediaPlayer();
                }

                AssetFileDescriptor descriptor = activity.getAssets().openFd(audio);
                soundPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
                descriptor.close();

                soundPlayer.prepare();
                soundPlayer.setVolume(1f, 1f);

                soundPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    public void Stopsound() {
        if (soundPlayer != null) {

            if (soundPlayer.isPlaying()) {
                soundPlayer.stop();
                soundPlayer.release();
                soundPlayer = new MediaPlayer();
            }
        }
    }


    public void PlayVibrate500(Activity activity,int time) {
        String check_sound = Utilities.getString(activity, History.IS_VIB, "yes");

        if (check_sound.equalsIgnoreCase("yes")) {

        v = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
        int medium_gap = 500;   // Length of Gap Between Letters
        long[] pattern = {
                0,
                medium_gap,    // S
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            VibrationEffect vibe = VibrationEffect.createWaveform(pattern, 1);
            v.vibrate(vibe);
        } else {
            //deprecated in API 26
            v.vibrate(pattern, 1);
        }

        new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                if (v != null)
                    v.cancel();
            }
        }.start();



        }
    }


}
