package ch.alexb.tower.ui;

import java.awt.*;

public class FastEnemy {
    private int x;
    private int y;
    private int leben = 3;
    private int geschwindigkeit = 3;


    public void fastEnemySpawn(Graphics g, int curenty){
        y = curenty;
        g.setColor(Color.PINK);
        g.fillOval(x,y + 35, 30, 30 );
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
