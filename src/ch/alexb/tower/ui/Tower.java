package ch.alexb.tower.ui;

import java.awt.*;
import java.util.ArrayList;

public class Tower {

    private int mousex;
    private int mousey;

    private boolean geschossen = false;

    private int reichweite = 140;
    private int demage = 1;
    private int pearcing = 1;

    private int reachUpgradesGekauft = 0;
    private int demageUpgradesGekauft = 0;
    private int pearcingUpgradesGekauft = 0;

    private int rueckerstattung = 25;


    public void towerzeichnen(Graphics g2, int mousex, int mousey){

        g2.setColor(Color.GRAY);

        g2.fillOval(mousex - 30, mousey - 30, 60, 60);

        g2.drawOval(mousex - reichweite /2, mousey - reichweite / 2, reichweite, reichweite);


    }


    public int getMousex(){
        return mousex;
    }


    public int getMousey(){
        return mousey;
    }


    public void newTower(int mousex, int mousey){

        this.mousex = mousex;
        this.mousey = mousey;
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

}
