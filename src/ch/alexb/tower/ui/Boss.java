package ch.alexb.tower.ui;

import java.awt.*;
import java.util.ArrayList;

public class Boss extends Gegner {

    public Boss(ArrayList<Point> wegPunkte){

        x = wegPunkte.get(0).x;
        y = wegPunkte.get(0).y;

        geschwindigkeit = 1;
        leben = 5;
    }


    public void bossSpawn(Graphics g){

        g.setColor(Color.GREEN);

        g.fillOval((int) x - 15, (int) y - 15, 30, 30);
    }





}
