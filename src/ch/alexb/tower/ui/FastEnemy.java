package ch.alexb.tower.ui;

import java.awt.*;
import java.util.ArrayList;

public class FastEnemy extends Gegner {

    public FastEnemy(ArrayList<Point> wegPunkte){

        x = wegPunkte.get(0).x;
        y = wegPunkte.get(0).y;

        geschwindigkeit = 3;
        leben = 1;
    }


    public void fastEnemySpawn(Graphics g){

        g.setColor(Color.PINK);

        g.fillOval((int) x - 15, (int) y - 15, 30, 30);

        if (verlangsamt) {
            g.setColor(new Color(100, 180, 255, 70));
            g.fillOval((int) x -15, (int) y -15, 30, 30);

            g.setColor(new Color(120, 200, 255));
            g.drawOval((int) x -15, (int) y -15, 30, 30);
        }
    }
}
