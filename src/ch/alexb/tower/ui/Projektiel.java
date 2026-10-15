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
    int typ;

    double gesamteStrecke;
    int geflogeneStrecke = 0;
    private double zielX;
    private double zielY;
    private double startX;
    private double startY;
    private double fortschrittMoerser = 0;




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

        gesamteStrecke = entfernung;

        this.startX = x;
        this.startY = y;


        bewegungX = richtungX / entfernung * projektielGeschwindigkeit;
        bewegungY = richtungY / entfernung * projektielGeschwindigkeit;
    }

    public Projektiel(int towerX, int towerY,
                      double zielX, double zielY,
                      Tower tower) {

        this.x = towerX;
        this.y = towerY;

        this.startX = towerX;
        this.startY = towerY;

        this.zielX = zielX;
        this.zielY = zielY;

        this.tower = tower;

        double dx = zielX - towerX;
        double dy = zielY - towerY;

        gesamteStrecke = Math.sqrt(dx * dx + dy * dy);
    }


    public void projektielBewegen() {

        x += bewegungX;
        y += bewegungY;

    }

    public void moerserProjektielBewegen(int zielX, int zielY){
        this.zielX = zielX;
        this.zielY = zielY;


        fortschrittMoerser += 0.02;

        if (fortschrittMoerser > 1) {
            fortschrittMoerser = 1;
        }

        double neuX = startX + (zielX - startX) * fortschrittMoerser;

        double neuY = startY + (zielY - startY) * fortschrittMoerser;

        double maximaleHoehe = 80;

        double hoehe = 4 * maximaleHoehe * fortschrittMoerser * (1 - fortschrittMoerser);

        x = neuX;
        y = neuY - hoehe;
    }




    public void projektielZeichnen(Graphics g) {

        typ = tower.getTowerTyp();
        
        if (typ <= 3 || typ == 5) {
            g.setColor(Color.BLUE);
            g.fillOval((int) x, (int) y, 10, 10);
        } else if (typ == 4) {
            g.setColor(new Color(175, 220, 240));
            g.fillOval((int) x, (int) y, 15, 15);
        } else if(typ == 6){
            g.setColor(Color.BLACK);
            g.fillOval((int) x, (int) y, 15, 15);
        } else if(typ == 7){

            g.setColor(Color.BLACK);
            g.drawOval((int) x-30, (int) y-30, 60, 60);

            g.setColor(Color.DARK_GRAY);
            g.fillOval((int) x - 6, (int) y - 6, 12, 12);

            g.setColor(Color.BLACK);
            g.drawOval((int) x - 6, (int) y - 6, 12, 12);
        }else if (typ == 8){
            g.setColor(Color.BLACK);
            g.fillOval((int) x, (int) y, 15, 15);
        }
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