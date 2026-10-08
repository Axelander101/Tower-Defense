package ch.alexb.tower.ui;

import java.awt.*;

public class Boss {
    private int x;
    private int y;
    private int leben = 100;
    private double geschwindigkeit = 1.5;



    public void bossSpawn(Graphics g, int curenty){
        y = curenty;
        g.setColor(Color.GREEN);
        g.fillOval(x, y + 35, 50, 50);
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
