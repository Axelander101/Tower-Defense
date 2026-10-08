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



    public Projektiel(int towerX, int towerY, int enemyX, int enemyY, double enemyGeschwindigkeit, double entfernung2, Tower tower) {

        this.tower = tower;

        // Projektil startet ungefähr in ch.alexb.tower.ui.Tower-Mitte
        x = towerX;
        y = towerY;


        // Entfernung ch.alexb.tower.ui.Tower -> Gegner
        double dx = enemyX - x;
        double dy = enemyY - y;

        double entfernung = Math.sqrt(dx * dx + dy * dy);


        // Ungefähre Flugzeit
        double flugzeit = entfernung / projektielGeschwindigkeit;


        // Vorhersage:
        // Gegner läuft nach rechts



        if (towerX - 300 < enemyX) {
            treffpunktX = enemyX + enemyGeschwindigkeit / 1.2 * flugzeit + entfernung / 10;
        } else if (towerX - 300 > enemyX && towerX-200 < enemyY){
            treffpunktX = enemyX + enemyGeschwindigkeit / 1.2 * flugzeit + entfernung / 20;
        } else if (towerX-200 > enemyY && towerX -100 < enemyX) {
            treffpunktX = enemyX + enemyGeschwindigkeit / 1.2 * flugzeit + entfernung / 30;
        } else if (towerX -100 > enemyX && enemyX < towerX) {
            treffpunktX = enemyX + enemyGeschwindigkeit / 1.2  * flugzeit + entfernung / 80;
        } else if (enemyX < towerX && towerX + 100 > enemyX) {
            treffpunktX = enemyX + enemyGeschwindigkeit / 1.2 * flugzeit + entfernung / 80;
        } else if (towerX + 100 < enemyX && towerX + 200 > enemyX) {
            treffpunktX = enemyX + enemyGeschwindigkeit / 1.2 * flugzeit + entfernung / 30;
        } else if (towerX + 200 < enemyX && towerX + 300 > enemyX){
            treffpunktX = enemyX + enemyGeschwindigkeit / 1.2 * flugzeit + entfernung / 20;
        } else if(towerX + 300 > enemyX){
            treffpunktX = enemyX + enemyGeschwindigkeit / 1.2 * flugzeit + entfernung / 10;
        }


        double treffpunktY = enemyY;


        // Richtung zum Treffpunkt
        dx = treffpunktX - x;
        dy = treffpunktY - y;


        double distanz = Math.sqrt(dx * dx + dy * dy);


        if (distanz != 0) {

            double richtungX = dx / distanz;

            double richtungY = dy / distanz;


            bewegungX = richtungX * projektielGeschwindigkeit;

            bewegungY = richtungY * projektielGeschwindigkeit;
        }
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