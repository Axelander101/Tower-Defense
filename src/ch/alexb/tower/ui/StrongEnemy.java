package ch.alexb.tower.ui;

import java.awt.*;
import java.util.ArrayList;

public class StrongEnemy extends Gegner {

    public StrongEnemy(ArrayList<Point> wegPunkte){

        x = wegPunkte.get(0).x;
        y = wegPunkte.get(0).y;

        geschwindigkeit = 1;
        leben = 5;
    }


    public void strongEnenmySpawn(Graphics g){

        g.setColor(Color.BLUE);

        g.fillOval((int) x - 15, (int) y - 15, 30, 30);

        if (verlangsamt) {
            g.setColor(new Color(100, 180, 255, 70));
            g.fillOval((int) x -15, (int) y -15, 30, 30);

            g.setColor(new Color(120, 200, 255));
            g.drawOval((int) x -15, (int) y -15, 30, 30);
        }
    }
}
