package ch.alexb.tower.ui;

import java.awt.*;
import java.util.ArrayList;

public class StrongEnemy {
    private int x;
    private int y;
    private int leben = 10;
    private double geschwindigkeit = 1.5;
    private int aktuellerWegPunkt = 0;



    public void strongEnenmySpawn(Graphics g){

        g.setColor(Color.CYAN);
        g.fillOval((int) x, (int) y - 15, 30, 30);
    }


    public StrongEnemy(ArrayList<Point> wegPunkte){

        x = wegPunkte.get(0).x;
        y = wegPunkte.get(0).y;

        aktuellerWegPunkt = 1;
    }

    public void bewegen(ArrayList<Point> wegPunkte){

        if (aktuellerWegPunkt >= wegPunkte.size()){
            return;
        }

        Point ziel = wegPunkte.get(aktuellerWegPunkt);

        double dx = ziel.x - x;
        double dy = ziel.y - y;

        double entfernung = Math.sqrt(dx * dx + dy * dy);


        if (entfernung <= geschwindigkeit){

            x = ziel.x;
            y = ziel.y;

            aktuellerWegPunkt++;

            return;
        }


        x = (int) (x + dx / entfernung * geschwindigkeit);
        y = (int) (y + dy / entfernung * geschwindigkeit);
    }

    public void setLeben(int welle){

        welle -= 10;

        if (welle > 0){
            leben = leben * welle / 10;
        }
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getLeben(){
        return leben;
    }

    public void schadenNehmen(int schaden) {
        leben -= schaden;
    }
}
