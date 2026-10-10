package ch.alexb.tower.ui;

import java.awt.*;

public class Enemy {
    private int x;
    private int y;
    private int leben = 3;
    private int geschwindigkeit = 2;

    public void normalEnemySpawn(Graphics g, int curenty){
        y = curenty;
        g.setColor(Color.BLACK);
        g.fillOval(x,y + 35, 30, 30 );

    }

    public void bewegen(){
        x += geschwindigkeit;

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


