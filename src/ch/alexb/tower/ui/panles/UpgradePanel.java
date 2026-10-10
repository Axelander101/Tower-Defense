package ch.alexb.tower.ui.panles;

import ch.alexb.tower.ui.Bank;
import ch.alexb.tower.ui.Tower;

import javax.swing.*;
import java.awt.*;

public class UpgradePanel extends JPanel {

    private int rueckerstattung;

    public UpgradePanel(){

        setBackground(Color.WHITE);
    }


    public void towerUpgrademenue(Graphics g2, Tower tower){

        g2.setColor(Color.WHITE);

        g2.fillRect(700, 0, 300, 700);


        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.PLAIN, 25));
        g2.drawString("Upgrades", 725, 25);

        g2.setFont(new Font("Arial", Font.PLAIN, 15));

        // Reach
        g2.drawRect(725, 65, 160, 70);
        g2.drawString(tower.getReachUpgradesGekauft() + "/3  More reach", 730, 80);
        g2.drawString("+25 reach", 730, 100);


        if (tower.getReachUpgradesGekauft() < 3){
            g2.drawString(tower.getReachKosten() + " Coins", 730, 120);

        } else {
            g2.drawString("MAX", 730, 120);
        }


        // Demage
        g2.drawRect(725, 165, 160, 70);
        g2.drawString(tower.getDemageUpgradesGekauft() + "/3  More demage", 730, 180);
        g2.drawString("+1 demage", 730, 200);


        if (tower.getDemageUpgradesGekauft() < 3){
            g2.drawString(tower.getDemageKosten() + " Coins", 730, 220);

        } else {
            g2.drawString("MAX", 730, 220);
        }


        // Pearcing
        g2.drawRect(725, 265, 160, 70);
        g2.drawString(tower.getPearcingUpgradesGekauft() + "/2  More pearcing", 730, 280);
        g2.drawString("+1 pearcing", 730, 300);

        if (tower.getPearcingUpgradesGekauft() < 2){
            g2.drawString(tower.getPearcingKosten() + " Coins", 730, 320);

        } else {
            g2.drawString("MAX", 730, 320);
        }


        // X zum Schliessen
        g2.setColor(Color.RED);

        g2.drawRect(950, 0, 50, 50);

        g2.setFont(new Font("Arial", Font.PLAIN, 40));

        g2.drawString("X", 962, 40);


        // Turm verkaufen
        g2.drawRect(725, 600, 160, 60);
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        rueckerstattung = tower.getRueckerstattung();
        g2.drawString("Sell " + rueckerstattung, 740, 635);


    }

    public void bankUpgradeMenue (Graphics g2, Bank bank) {

        g2.setColor(Color.WHITE);

        g2.fillRect(700, 0, 300, 700);


        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.PLAIN, 25));
        g2.drawString("Bank Upgrades" , 725, 25);
        g2.setFont(new Font("Arial", Font.PLAIN, 15));


        g2.drawRect(725, 65, 160, 70);
        g2.drawString(bank.getMoreMoneyUpgrades() + "/3  More cash", 730, 80);
        g2.drawString("+100 Coins", 730, 100);

        if (bank.getMoreMoneyUpgrades() < 3){
            g2.drawString(bank.getMoreMoneyUpgradeKosten() + " Coins", 730, 120);
        }else {
            g2.drawString("MAX", 730 ,120);
        }

        // X zum Schliessen
        g2.setColor(Color.RED);
        g2.drawRect(950, 0, 50, 50);
        g2.setFont(new Font("Arial", Font.PLAIN, 40));
        g2.drawString("X", 962, 40);


        g2.drawRect(725, 600, 160, 60);
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        rueckerstattung = bank.getRueckerstattung();
        g2.drawString("Sell " + rueckerstattung, 740, 635);

    }





}