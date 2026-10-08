package ch.alexb.tower.ui;

import java.awt.*;

class Enemy {
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

class fastEnemy{
    private int x;
    private int y;
    private int leben = 2;
    private int geschwindigkeit = 4;


    public void fastEnemySpawn(Graphics g,int curenty){
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


class strongEnemy {
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


class Boss {
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

