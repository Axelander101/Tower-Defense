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
    private int shootdelay;
    private boolean flaechenschaden;

    private int reachUpgradesGekauft = 0;
    private int demageUpgradesGekauft = 0;
    private int pearcingUpgradesGekauft = 0;

    private int rueckerstattung = 0;

    private int kosten;

    private int TowerTyp;

    private boolean typSchonBestimmt = false;

    private long letzterSchuss = 0;

    private boolean ultimateGekauft = false;



    public boolean kannSchiessen(int delay) {

        long aktuelleZeit = System.currentTimeMillis();

        return aktuelleZeit - letzterSchuss >= delay;
    }

    public void schussGemacht() {
        letzterSchuss = System.currentTimeMillis();
    }


    public void towerzeichnen(Graphics g2){
        boolean oval = true;

        switch (TowerTyp) {
            //Einfacher Turm
            case 1 -> {
                g2.setColor(Color.GRAY);
            }
            //Sniper
            case 2 -> {
                Color darkGreen= new Color(0, 100, 0);
                g2.setColor(darkGreen);
            }
            //Shotgun
            case 3 -> {
                g2.setColor(Color.RED);
            }
            //Frost
            case 4 -> {
                Color eisblau = new Color(200, 233, 245);
                g2.setColor(eisblau);
            }
            //Detektor
            case 5 -> {
                Color dreckgruen = new Color(100,104,60);
                g2.setColor(dreckgruen);
            }
            //Bomber
            case 6 -> {
                Color dunkelGrau = new Color(74, 70, 70);
                g2.setColor(dunkelGrau);
            }
            //Mörser
            case 7 -> {
                Color pink = new Color(213, 29, 125);
                g2.setColor(pink);
            }
            //Fallenleger
            case 8 -> {
                Color dunkelGelb = new Color(245, 187, 13);
                g2.setColor(dunkelGelb);
            }
            //Dorf
            case 9 -> {
                Color hellGrau = new Color(153, 153, 153);
                g2.setColor(hellGrau);
                g2.fillRect(mousex - 30, mousey - 30, 60, 60);
                oval = false;
            }
        }
        if (oval){
            g2.fillOval(mousex - 30, mousey - 30, 60, 60);
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
            //Einfacher Turm
            case 1 -> {
                reichweite = 155;
                demage = 1;
                pearcing = 1;
                shootdelay = 500;
                flaechenschaden = false;

                kosten = 50;
                rueckerstattung += 25;
            }

            //Sniper
            case 2 -> {
                reichweite = 260;
                demage = 2;
                pearcing = 1;
                shootdelay = 500;
                flaechenschaden = false;

                kosten = 150;
                rueckerstattung += 100;
            }
            //Shotgun
            case 3 -> {
                reichweite = 140;
                demage = 3;
                pearcing = 1;
                shootdelay = 500;
                flaechenschaden = false;

                kosten = 170;
                rueckerstattung += 85;
            }
            //Frost
            case 4 -> {
                reichweite = 165;
                demage = 1;
                pearcing = 1;
                shootdelay = 700;
                flaechenschaden = false;

                kosten = 90;
                rueckerstattung = 45;
            }
            //Detektor
            case 5 -> {
                reichweite = 195;
                demage = 2;
                pearcing = 1;
                shootdelay = 500;
                flaechenschaden = false;

                kosten = 125;
                rueckerstattung = 62;
            }
            //Bomber
            case 6 -> {
                reichweite = 175;
                demage = 4;
                pearcing = 2;
                shootdelay = 900;
                flaechenschaden = true;

                kosten = 255;
                rueckerstattung = 127;
            }
            //Mörser
            case 7 -> {
                reichweite = 360;
                demage = 8;
                pearcing = 1;
                shootdelay = 1800;
                flaechenschaden = true;

                kosten = 390;
                rueckerstattung = 195;
            }
            //Fallenleger
            case 8 -> {
                reichweite = 200;
                demage = 6;
                pearcing = 1;
                shootdelay = 1000;
                flaechenschaden = false;

                kosten = 190;
                rueckerstattung = 95;
            }
            //Dorf
            case 9 -> {
                reichweite = 220;
                demage = 0;
                pearcing = 0;
                shootdelay = 1;
                flaechenschaden = false;

                kosten = 505;
                rueckerstattung = 252;
            }

        }
    }

    public double getEffektiverSchaden(ArrayList<Tower> towers) {

        double schaden = demage;

        for (Tower tower : towers) {

            if (tower.getTowerTyp() != 9) {
                continue;
            }

            double dx = tower.getMousex() - this.mousex;
            double dy = tower.getMousey() - this.mousey;

            double entfernung = Math.sqrt(dx * dx + dy * dy);

            if (entfernung <= tower.getReichweite()) {

                /*
                if (tower.isDorfSchadenUpgrade()) {
                    schaden *= 1.15;
                }
                */
            }
        }

        return schaden;
    }

    public int getEffektiverShootdelay(ArrayList<Tower> towers) {

        double faktor = 1.0;

        for (Tower tower : towers) {

            if (tower.getTowerTyp() != 9) {
                continue;
            }

            double dx = tower.getMousex() - this.mousex;
            double dy = tower.getMousey() - this.mousey;

            double entfernung = Math.sqrt(dx * dx + dy * dy);

            if (entfernung <= tower.getReichweite()) {

                faktor = 0.2; // Grundbuff +8 %

                /*
                if (tower.isDorfFeuerrateUpgrade()) {
                    faktor = 0.80; // insgesamt etwa +20 %
                }
                */
            }
        }

        return (int) (shootdelay * faktor);
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
            reichweite += 25;
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
        if (TowerTyp == 1) {
            return 180 + 80 * getDemageUpgradesGekauft();
        }else if (TowerTyp == 2){
            return 160 + 70 * getDemageUpgradesGekauft();
        } else if (TowerTyp == 3) {
            return 160 + 75 * getDemageUpgradesGekauft();
        }

        return 0;
    }


    public int getPearcingKosten(){
        return 260 + 160 * getPearcingUpgradesGekauft();
    }

    public int getPearcing(){
        return pearcing;
    }

    public int getKosten(int typ){

        switch (typ){

            //Normal
            case 1 -> {
                kosten = 50;
            }
            //Sniper
            case 2 -> {
                kosten = 150;
            }
            //Shotgun
            case 3 -> {
                kosten = 170;
            }
            //Frost
            case 4 -> {
                kosten = 90;
            }
            //Detektor
            case 5 -> {
                kosten = 125;
            }
            //Bomber
            case 6 -> {
                kosten = 255;
            }
            //Mörser
            case 7 -> {
                kosten = 390;
            }
            //Fallenleger
            case 8 -> {
                kosten = 190;
            }
            //Dorf
            case 9 -> {
                kosten = 505;
            }

        }

        return kosten;

    }

    public boolean isUltimateGekauft(){
        return ultimateGekauft;
    }

    public int getDemage(){
        return demage;
    }

    public int getShootdelay(){
        return shootdelay;
    }

    public int getTowerTyp(){
        return TowerTyp;
    }

    public int getReichweite() {
        return reichweite;
    }

}
