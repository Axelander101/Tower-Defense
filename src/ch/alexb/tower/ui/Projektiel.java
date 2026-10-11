package ch.alexb.tower.ui;

import java.awt.*;
import java.util.ArrayList;


public class Projektiel {

    private ArrayList<Object> getroffeneGegner = new ArrayList<>();

    double x;
    double y;

    double bewegungX;
    double bewegungY;
    double treffpunktX;

    int projektielGeschwindigkeit = 8;
    boolean verwendet = false;

    private Tower tower;
    private int getroffen = 0;



    public Projektiel(int towerX, int towerY, Gegner gegner, ArrayList<Point> wegPunkte, double reichweite, Tower tower){

        this.x = towerX;
        this.y = towerY;
        this.tower = tower;

        double projektielGeschwindigkeit = 8;

        double zeit = 0;


        for (int i = 0; i < 10; i++){

            Point.Double position = gegner.getPositionNachZeit(zeit, wegPunkte);

            double dx = position.x - towerX;
            double dy = position.y - towerY;

            double entfernung = Math.sqrt(dx * dx + dy * dy);

            zeit = entfernung / projektielGeschwindigkeit;
        }


        Point.Double ziel = gegner.getPositionNachZeit(zeit, wegPunkte);

        double richtungX = ziel.x - towerX;
        double richtungY = ziel.y - towerY;

        double entfernung = Math.sqrt(richtungX * richtungX + richtungY * richtungY);


        bewegungX = richtungX / entfernung * projektielGeschwindigkeit;
        bewegungY = richtungY / entfernung * projektielGeschwindigkeit;
    }


    public void projektielBewegen() {

        x += bewegungX;
        y += bewegungY;
    }


    public void projektielZeichnen(Graphics g) {

        g.setColor(Color.BLUE);

        g.fillOval((int) x, (int) y, 10, 10);
    }


    public void getroffen(){
        getroffen += 1;
    }

    public void gegnerSpeichern(Object gegner){
        getroffeneGegner.add(gegner);
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;

    }

    public Tower getTower(){
        return tower;
    }

    public int getGetroffen(){
        return getroffen;
    }

    public boolean hatGegnerScchonGetroffen(Object gegner){
        return getroffeneGegner.contains(gegner);
    }

}