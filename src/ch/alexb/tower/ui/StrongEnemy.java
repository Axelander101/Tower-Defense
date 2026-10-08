package ch.alexb.tower.ui;

import java.awt.*;

public class StrongEnemy {
    private int x;
    private int y;
    private int leben = 5;
    private double geschwindigkeit = 1.5;



    public void strongEnenmySpawn(Graphics g, int curenty){
        y = curenty;
        g.setColor(Color.CYAN);
        g.fillOval(x, y + 35, 30, 30);
    }


    public void bewegen(){
        x += geschwindigkeit;

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
