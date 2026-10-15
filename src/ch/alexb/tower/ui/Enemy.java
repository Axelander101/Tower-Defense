package ch.alexb.tower.ui;

import java.awt.*;
import java.util.ArrayList;

public class Enemy extends Gegner {

    public Enemy(ArrayList<Point> wegPunkte){

        x = wegPunkte.get(0).x;
        y = wegPunkte.get(0).y;

        geschwindigkeit = 2;
        leben = 1;
    }


    public void normalEnemySpawn(Graphics g){

        g.setColor(Color.BLACK);

        g.fillOval((int) x - 15, (int) y - 15, 30, 30);

        if (verlangsamt) {
            g.setColor(new Color(100, 180, 255, 70));
            g.fillOval((int) x -15, (int) y-15, 30, 30);

            g.setColor(new Color(120, 200, 255));
            g.drawOval((int) x -15, (int) y -15, 30, 30);
        }
    }
}


