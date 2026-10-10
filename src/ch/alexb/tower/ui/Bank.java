package ch.alexb.tower.ui;

import java.awt.*;
import java.util.ArrayList;

public class Bank {



    private int x;
    private int y;

    private int einkommen = 100;
    private int moreMoneyUpgrades = 0;
    private int moreMoneyUpgradeKosten = 300;
    private int rueckerstattung = 100;

    private int kosten = 200;



    public void bankZeichnen(Graphics g){

        g.setColor(new Color(139, 69, 19));

        g.fillRect(x - 30, y - 30, 60, 60);
    }


    public void newBank(int x, int y){

        this.x = x;
        this.y = y;
    }


    public void rueckerstattung(int kosten){
        rueckerstattung += kosten / 2;
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


    public boolean aufBankGeklickt(int mousex, int mousey){

        double dx = mousex - this.x;

        double dy = mousey - this.y;

        double entfernung = Math.sqrt(dx * dx + dy * dy);

        return entfernung <= 30;
    }


    public int getEinkommen(){

        return einkommen;
    }


    public void moreMoneyUpgradeGekauft(){

        if (moreMoneyUpgrades < 3){
            moreMoneyUpgrades += 1;
            einkommen += 100;
        }
    }


    public int getX(){

        return x;
    }


    public int getY(){

        return y;
    }

    public int getKosten(){
        return kosten;
    }

    public int getMoreMoneyUpgrades(){
        return moreMoneyUpgrades;
    }

    public int getMoreMoneyUpgradeKosten(){
        return (int) (300 * (1 + moreMoneyUpgrades * 0.5));

    }

    public int getRueckerstattung(){
        return rueckerstattung;
    }

}
