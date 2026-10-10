package ch.alexb.tower.ui;

import ch.alexb.tower.sounds.Sound;

import java.awt.*;
import java.util.ArrayList;

public class Tower {

    private int mousex;
    private int mousey;

    private boolean geschossen = false;

    private int reichweite;
    private int demage;
    private int pearcing;

    private int reachUpgradesGekauft = 0;
    private int demageUpgradesGekauft = 0;
    private int pearcingUpgradesGekauft = 0;

    private int rueckerstattung = 0;

    private int kosten;

    private int TowerTyp;

    private boolean typSchonBestimmt = false;


    public void towerzeichnen(Graphics g2){

        switch (TowerTyp) {
            case 1 -> {

                g2.setColor(Color.GRAY);
                g2.fillOval(mousex - 30, mousey - 30, 60, 60);
            }

            case 2 -> {
                Color darkGreen= new Color(0, 100, 0);
                g2.setColor(darkGreen);
                g2.fillOval(mousex - 30, mousey - 30, 60, 60);
            }

            case 3 -> {
                g2.setColor(Color.RED);
                g2.fillOval(mousex - 30, mousey - 30, 60, 60);
            }
        }
    }


    public int getMousex(){
        return mousex;
    }


    public int getMousey(){
        return mousey;
    }


    public void newTower(int mousex, int mousey, int typ){

        this.mousex = mousex;
        this.mousey = mousey;

        if(!typSchonBestimmt){

            TowerTyp = typ;
            typSchonBestimmt = true;
        }

        switch (TowerTyp) {
            // Einfacher Turm
            case 1 -> {
                reichweite = 140;
                demage = 1;
                pearcing = 1;

                kosten = 50;
                rueckerstattung += 25;
            }

            // Sniper
            case 2 -> {
                reichweite = 300;
                demage = 2;
                pearcing = 1;

                kosten = 150;
                rueckerstattung += 75;
            }

            case 3 -> {
                reichweite = 140;
                demage = 3;
                pearcing = 1;

                kosten = 200;
                rueckerstattung += 100;
            }
        }
    }


    public boolean istInReichweite(int x1, int y1, int x2, int y2) {

        double dx = (x2 + 15) - x1;
        double dy = (y2 + 15) - y1;

        double entfernung = Math.sqrt(dx * dx + dy * dy);

        return entfernung <= reichweite;
    }


    public double Reichweite(int x1, int y1, int x2, int y2){

        double dx = (x2 + 15) - x1;
        double dy = (y2 + 15) - y1;

        double entfernung = Math.sqrt(dx * dx + dy * dy);

        return entfernung;
    }


    public boolean hatGeschossen(){

        return geschossen;
    }


    public void schiessen(boolean reset){

        geschossen = reset;
    }


    public boolean aufTowerGeklickt(int mousex, int mousey){

        double dx = mousex - this.mousex;

        double dy = mousey - this.mousey;

        double entfernung = Math.sqrt(dx * dx + dy * dy);

        return entfernung <= 30;
    }


    public boolean istPlatzFrei(int mousex, int mousey, ArrayList<Integer> plaziertx, ArrayList<Integer> plazierty){
        for (int i = 0; i < plaziertx.size(); i++){
            int andererX = plaziertx.get(i);

            int andererY = plazierty.get(i);


            if (Math.abs(mousex - andererX) < 60 && Math.abs(mousey - andererY) < 60){
                return false;
            }
        }

        return true;
    }




    public void reachUpgradeKaufen(){
        if (reachUpgradesGekauft < 3){
            reachUpgradesGekauft += 1;
            reichweite += 50;
        }
    }


    public void demageUpgradeKaufen(){
        if (demageUpgradesGekauft < 3){
            demageUpgradesGekauft += 1;
            demage += 1;
        }
    }


    public void pearcingUpgradeKaufen(){
        if (pearcingUpgradesGekauft < 2){
            pearcingUpgradesGekauft += 1;
            pearcing += 1;
        }
    }

    public void rueckerstattung(int kosten){
        rueckerstattung += kosten / 2;
    }

    public int getRueckerstattung(){
        return rueckerstattung;
    }


    public int getReachUpgradesGekauft(){
        return reachUpgradesGekauft;
    }


    public int getDemageUpgradesGekauft(){
        return demageUpgradesGekauft;
    }


    public int getPearcingUpgradesGekauft(){
        return pearcingUpgradesGekauft;
    }


    public int getReachKosten(){
        return (int) (50 * (1 + reachUpgradesGekauft * 0.5));
    }


    public int getDemageKosten(){
        return (int) (100 * (1 + demageUpgradesGekauft * 0.5));
    }


    public int getPearcingKosten(){
        return (int) (200 * (1 + pearcingUpgradesGekauft * 0.5));
    }

    public int getPearcing(){
        return pearcing;
    }

    public int getKosten(){
        return kosten;
    }

    public int getTowerTyp(){
        return TowerTyp;
    }

}
