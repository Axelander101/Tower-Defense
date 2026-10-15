package ch.alexb.tower.ui;

import java.awt.*;
import java.util.ArrayList;

public class Gegner {

    protected double x;
    protected double y;

    protected double geschwindigkeit;

    protected int leben;

    protected int aktuellerWegPunkt = 1;

    protected boolean verlangsamt = false;
    protected long slowBis = 0;;


    public void slow(long dauer) {
        verlangsamt = true;
        slowBis = System.currentTimeMillis() + dauer;
    }

    public int getX(){

        return (int) x;
    }


    public int getY(){

        return (int) y;
    }


    public int getLeben(){

        return leben;
    }


    public void setLeben(int leben){

        this.leben = leben;
    }


    public void schadenNehmen(int schaden){

        leben -= schaden;
    }


    public void bewegen(ArrayList<Point> wegPunkte){

        if (verlangsamt && System.currentTimeMillis() > slowBis) {
            verlangsamt = false;
        }

        double aktuelleGeschwindigkeit;

        if (verlangsamt) {
            aktuelleGeschwindigkeit = geschwindigkeit * 0.5;
        } else {
            aktuelleGeschwindigkeit = geschwindigkeit;
        }

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


        x += dx / entfernung * aktuelleGeschwindigkeit;
        y += dy / entfernung * aktuelleGeschwindigkeit;
    }


    public Point.Double getPositionNachZeit(double zeit, ArrayList<Point> wegPunkte){

        double testX = x;
        double testY = y;

        int punkt = aktuellerWegPunkt;

        double restStrecke = geschwindigkeit * zeit;


        while (punkt < wegPunkte.size()){

            Point ziel = wegPunkte.get(punkt);

            double dx = ziel.x - testX;
            double dy = ziel.y - testY;

            double entfernung = Math.sqrt(dx * dx + dy * dy);


            if (entfernung == 0){

                punkt++;

                continue;
            }


            if (restStrecke <= entfernung){

                testX += dx / entfernung * restStrecke;
                testY += dy / entfernung * restStrecke;

                return new Point.Double(testX, testY);
            }


            restStrecke -= entfernung;

            testX = ziel.x;
            testY = ziel.y;

            punkt++;
        }


        return new Point.Double(testX, testY);
    }


    public int getAktuellerWegPunkt(){
        return aktuellerWegPunkt;
    }


    public boolean isVerlangsamt(){
        return verlangsamt;
    }
}