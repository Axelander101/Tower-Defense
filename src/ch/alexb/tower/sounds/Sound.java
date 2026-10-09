package ch.alexb.tower.sounds;


import javax.sound.sampled.*;
import java.io.File;

public class Sound {

    public static void abspielen(String datei, float lautstaerkewert){

        try {

            File soundDatei = new File(datei);

            AudioInputStream audio = AudioSystem.getAudioInputStream(soundDatei);

            Clip clip = AudioSystem.getClip();

            clip.open(audio);

            FloatControl lautstaerke = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            lautstaerke.setValue(lautstaerkewert);

            clip.start();

        } catch (Exception e){

            System.out.println("Sound konnte nicht abgespielt werden");
        }
    }
}
