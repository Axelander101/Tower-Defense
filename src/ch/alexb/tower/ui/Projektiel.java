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



    public Projektiel(int towerX, int towerY, int enemyX, int enemyY, double enemyGeschwindigkeit, double reichweite, Tower tower){

        this.x = towerX;
        this.y = towerY;
        this.tower = tower;

        double projektielGeschwindigkeit = 8;

        double dx = enemyX - towerX;
        double dy = enemyY - towerY;

        double a = enemyGeschwindigkeit * enemyGeschwindigkeit - projektielGeschwindigkeit * projektielGeschwindigkeit;
        double b = 2 * dx * enemyGeschwindigkeit;
        double c = dx * dx + dy * dy;

        double diskriminante = b * b - 4 * a * c;

        double zeit = 0;

        if (diskriminante >= 0){

            double zeit1 = (-b + Math.sqrt(diskriminante)) / (2 * a);
            double zeit2 = (-b - Math.sqrt(diskriminante)) / (2 * a);

            if (zeit1 > 0 && zeit2 > 0){
                zeit = Math.min(zeit1, zeit2);

            }else if (zeit1 > 0){
                zeit = zeit1;

            }else if (zeit2 > 0){
                zeit = zeit2;
            }
        }

        double zielX = enemyX + enemyGeschwindigkeit * zeit;
        double zielY = enemyY;

        double richtungX = zielX - towerX;
        double richtungY = zielY - towerY;

        double entfernung = Math.sqrt(richtungX * richtungX + richtungY * richtungY);

        bewegungX = richtungX / entfernung * projektielGeschwindigkeit + 2;
        bewegungY = richtungY / entfernung * projektielGeschwindigkeit + 2;

        System.out.println(projektielGeschwindigkeit);
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