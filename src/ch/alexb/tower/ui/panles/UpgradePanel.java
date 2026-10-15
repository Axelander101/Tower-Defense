package ch.alexb.tower.ui.panles;

import ch.alexb.tower.ui.Bank;
import ch.alexb.tower.ui.Tower;

import javax.swing.*;
import java.awt.*;

public class UpgradePanel extends JPanel {

    private int rueckerstattung;

    public UpgradePanel() {

        setBackground(Color.WHITE);
    }


    public void towerUpgrademenue(Graphics g2, Tower tower) {

        g2.setColor(Color.BLACK);
        int radius = tower.getReichweite();

        g2.drawOval(tower.getMousex() - radius, tower.getMousey() - radius, radius * 2, radius * 2);


        g2.setColor(Color.WHITE);

        g2.fillRect(700, 0, 300, 700);


        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.PLAIN, 25));
        g2.drawString("Upgrades", 725, 25);
        g2.setFont(new Font("Arial", Font.PLAIN, 15));

        //Einfacher Turm Upgrades
        if (tower.getTowerTyp() == 1) {

            // Reach
            if (tower.getReachUpgradesGekauft() == 3 && !tower.isUltimateGekauft()) {
                ultimateUpgradeBox(g2, 725, 65, "Longshot System", "More range and damage", 32_175);
            }else{
                upgradeBox(g2, 725, 65, "Extended Range", "+25 reach", tower.getReachUpgradesGekauft(), 3, tower.getReachKosten());
            }

            // Demage
            if (tower.getDemageUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 165, "Overdrive Core", "More demage and firerate", 28_150);
            }else{
                upgradeBox(g2, 725, 165, "High-Caliber Rounds", "+1 damage", tower.getDemageUpgradesGekauft(), 3, tower.getDemageKosten());
            }

            // Pearcing
            if (tower.getPearcingUpgradesGekauft() == 2 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 265, "Shatter Cannon", "More piercing and damage", 30_160);
            }else {
                upgradeBox(g2, 725, 265, "Piercing Ammo", "+1 pearcing", tower.getPearcingUpgradesGekauft(), 2, tower.getPearcingKosten());
            }
        }
        //Sniper Upgrades
        if (tower.getTowerTyp() == 2){

            // Reach
            if (tower.getReachUpgradesGekauft() == 3 && !tower.isUltimateGekauft()) {
                ultimateUpgradeBox(g2, 725, 65, "Deadeye Mastery", "More range and damage", 26_855);
            }else{
                upgradeBox(g2, 725, 65, "Long Range Scope", "+25 reach", tower.getReachUpgradesGekauft(), 3, tower.getReachKosten());
            }

            // Demage
            if (tower.getDemageUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 165, "Boss Executioner", "More boss damage", 40_215);
            }else{
                upgradeBox(g2, 725, 165, "High-Power Rounds", "+1 damage", tower.getDemageUpgradesGekauft(), 3, tower.getDemageKosten());
            }

            // Pearcing
            if (tower.getPearcingUpgradesGekauft() == 2 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 265, "Hyper Railgun", "More piercing and damage", 30_160);
            }else {
                upgradeBox(g2, 725, 265, "Armor Piercing Rounds", "+1 pearcing", tower.getPearcingUpgradesGekauft(), 2, tower.getPearcingKosten());
            }
        }
        //Shotgun Upgrades
        if (tower.getTowerTyp() == 3){
            // Reach
            if (tower.getReachUpgradesGekauft() == 3 && !tower.isUltimateGekauft()) {
                ultimateUpgradeBox(g2, 725, 65, "Armorstorm", "More armor damage", 32_175);
            }else{
                upgradeBox(g2, 725, 65, "Hardened Pellets", "+25 reach", tower.getReachUpgradesGekauft(), 3, tower.getReachKosten());
            }

            // Demage
            if (tower.getDemageUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 165, "Hellfire Shells", "More close range damage", 30_160);
            }else{
                upgradeBox(g2, 725, 165, "Heavy Shells", "+1 damage", tower.getDemageUpgradesGekauft(), 3, tower.getDemageKosten());
            }

            // Pearcing
            if (tower.getPearcingUpgradesGekauft() == 2 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 265, "Chain Reaction", "More firerate and splash", 29_155);
            }else {
                upgradeBox(g2, 725, 265, "Rapid Action", "+1 pearcing", tower.getPearcingUpgradesGekauft(), 2, tower.getPearcingKosten());
            }
        }
        //Eis Turm Upgrades
        if (tower.getTowerTyp() == 4){
            // Cold
            if (tower.getReachUpgradesGekauft() == 3 && !tower.isUltimateGekauft()) {
                ultimateUpgradeBox(g2, 725, 65, "Absolute Zero", "Stronger and longer slow", 30_160);
            }else{
                upgradeBox(g2, 725, 65, "Deep Freeze", "+25 reach", tower.getReachUpgradesGekauft(), 3, tower.getReachKosten());
            }

            // Brittle
            if (tower.getDemageUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 165, "Crystal Fracture", "More damage to slowed", 30_160);
            }else if (tower.getDemageUpgradesGekauft() == 0){
                upgradeBox(g2, 725, 165, "Frozen Impact", "+1 damage", tower.getDemageUpgradesGekauft(), 3, tower.getDemageKosten());
            }else if (tower.getDemageUpgradesGekauft() == 1){
                upgradeBox(g2, 725, 165, "Brittle Ice", "+1 damage", tower.getDemageUpgradesGekauft(), 3, tower.getDemageKosten());
            }else {
                upgradeBox(g2, 725, 165, "Crystal Weakness", "+1 damage", tower.getDemageUpgradesGekauft(), 3, tower.getDemageKosten());
            }

            // Permafrost / Range
            if (tower.getPearcingUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 265, "Glacial Storm", "More range and slow", 31_170);
            }else if (tower.getPearcingUpgradesGekauft() == 0){
                upgradeBox(g2, 725, 265, "Extended Frost Range", "+1 pearcing", tower.getPearcingUpgradesGekauft(), 3, tower.getPearcingKosten());
            }else if (tower.getPearcingUpgradesGekauft() == 1){
                upgradeBox(g2, 725, 265, "Lingering Frost", "+1 pearcing", tower.getPearcingUpgradesGekauft(), 3, tower.getPearcingKosten());
            }else{
                upgradeBox(g2, 725, 265, "Permafrost", "+1 pearcing", tower.getPearcingUpgradesGekauft(), 3, tower.getPearcingKosten());
            }
        }
        //Detector Upgrades
        if (tower.getTowerTyp() == 5){
            // Network
            if (tower.getReachUpgradesGekauft() == 3 && !tower.isUltimateGekauft()) {
                ultimateUpgradeBox(g2, 725, 65, "Omni Radar", "More detection range", 29_155);
            }else if (tower.getReachUpgradesGekauft() == 0){
                upgradeBox(g2, 725, 65, "Extended Radar", "+25 reach", tower.getReachUpgradesGekauft(), 3, tower.getReachKosten());
            }else if(tower.getReachUpgradesGekauft() == 1){
                upgradeBox(g2, 725, 65, "Detection Relay", "+25 reach", tower.getReachUpgradesGekauft(), 3, tower.getReachKosten());
            }else{
                upgradeBox(g2, 725, 65, "Detection Network", "+25 reach", tower.getReachUpgradesGekauft(), 3, tower.getReachKosten());
            }

            // Hunter
            if (tower.getDemageUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 165, "Phantom Hunter", "More invisible enemy damage", 27_145);
            }else if (tower.getDemageUpgradesGekauft() == 0){
                upgradeBox(g2, 725, 165, "Target Analysis", "+1 damage", tower.getDemageUpgradesGekauft(), 3, tower.getDemageKosten());
            }else if(tower.getDemageUpgradesGekauft() == 1){
                upgradeBox(g2, 725, 165, "Ghost Tracking", "+1 damage", tower.getDemageUpgradesGekauft(), 3, tower.getDemageKosten());
            }else {
                upgradeBox(g2, 725, 165, "Silent Hunter", "+1 damage", tower.getDemageUpgradesGekauft(), 3, tower.getDemageKosten());
            }


            // Boss Radar
            if (tower.getPearcingUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 265, "Target Designator", "More allied tower damage", 30_160);
            }else if (tower.getPearcingUpgradesGekauft() == 0){
                upgradeBox(g2, 725, 265, "Combat Radar", "+1 pearcing", tower.getPearcingUpgradesGekauft(), 3, tower.getPearcingKosten());
            }else if (tower.getPearcingUpgradesGekauft() == 1){
                upgradeBox(g2, 725, 265, "Elite Scanner", "+1 pearcing", tower.getPearcingUpgradesGekauft(), 3, tower.getPearcingKosten());
            }else{
                upgradeBox(g2, 725, 265, "Boss Radar", "+1 pearcing", tower.getPearcingUpgradesGekauft(), 3, tower.getPearcingKosten());
            }


        }
        //Bomber
        if (tower.getTowerTyp() == 6){
            // Charge
            if (tower.getReachUpgradesGekauft() == 3 && !tower.isUltimateGekauft()) {
                ultimateUpgradeBox(g2, 725, 65, "Worldbreaker", "More armor and boss damage", 29_155);
            }else{
                upgradeBox(g2, 725, 65, "Shaped Charge", "+25 reach", tower.getReachUpgradesGekauft(), 3, tower.getReachKosten());
            }

            // Shockwave
            if (tower.getDemageUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 165, "Shock Core", "More splash and firerate", 27_145);
            }else if(tower.getDemageUpgradesGekauft() == 0){
                upgradeBox(g2, 725, 165, "Heavy Warhead", "+1 damage", tower.getDemageUpgradesGekauft(), 3, tower.getDemageKosten());
            }else if(tower.getDemageUpgradesGekauft() == 1){
                upgradeBox(g2, 725, 165, "Shockwave Amplifier", "+1 damage", tower.getDemageUpgradesGekauft(), 3, tower.getDemageKosten());
            }else{
                upgradeBox(g2, 725, 165, "Shockwave", "+1 damage", tower.getDemageUpgradesGekauft(), 3, tower.getDemageKosten());
            }

            // Radius
            if (tower.getPearcingUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 265, "Armor Shredder", "More armor penetration", 30_160);
            }else {
                upgradeBox(g2, 725, 265, "Blast Radius", "+1 pearcing", tower.getPearcingUpgradesGekauft(), 3, tower.getPearcingKosten());
            }
        }
        //Mörser
        if (tower.getTowerTyp() == 7){
            // Precision
            if (tower.getReachUpgradesGekauft() == 3 && !tower.isUltimateGekauft()) {
                ultimateUpgradeBox(g2, 725, 65, "Siege Battery", "More damage and firerate", 29_155);
            }else if (tower.getReachUpgradesGekauft() == 0){
                upgradeBox(g2, 725, 65, "Precision Targeting", "+25 reach", tower.getReachUpgradesGekauft(), 2, tower.getReachKosten());
            }else if (tower.getReachUpgradesGekauft() == 1){
                upgradeBox(g2, 725, 65, "Reinforced Shells", "+25 reach", tower.getReachUpgradesGekauft(), 2, tower.getReachKosten());
            }else{
                upgradeBox(g2, 725, 65, "Bunker Buster", "+25 reach", tower.getReachUpgradesGekauft(), 2, tower.getReachKosten());
            }

            // Fire
            if (tower.getDemageUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 165, "Napalm Barrage", "More fire damage", 27_145);
            }else if (tower.getDemageUpgradesGekauft() == 0){
                upgradeBox(g2, 725, 165, "Heavy Shells", "+1 damage", tower.getDemageUpgradesGekauft(), 3, tower.getDemageKosten());
            }else if (tower.getDemageUpgradesGekauft() == 1){
                upgradeBox(g2, 725, 165, "Incendiary Shells", "+1 damage", tower.getDemageUpgradesGekauft(), 3, tower.getDemageKosten());
            }else{
                upgradeBox(g2, 725, 165, "Burning Payload", "+1 damage", tower.getDemageUpgradesGekauft(), 3, tower.getDemageKosten());
            }

            // Radius
            if (tower.getPearcingUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 265, "Cluster Barrage", "More splash damage", 30_160);
            }else {
                upgradeBox(g2, 725, 265, "Blast Radius", "+1 pearcing", tower.getPearcingUpgradesGekauft(), 3, tower.getPearcingKosten());
            }
        }
        //Fallenleger
        if (tower.getTowerTyp() == 8){
            // Stock
            if (tower.getReachUpgradesGekauft() == 3 && !tower.isUltimateGekauft()) {
                ultimateUpgradeBox(g2, 725, 65, "Minefield", "More persistent mines", 29_155);
            }else{
                upgradeBox(g2, 725, 65, "Trap Stock", "+25 reach", tower.getReachUpgradesGekauft(), 3, tower.getReachKosten());
            }

            // Control
            if (tower.getDemageUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 165, "Containment Grid", "More traps and slow", 27_145);
            }else if (tower.getDemageUpgradesGekauft() == 0){
                upgradeBox(g2, 725, 165, "Reinforced Traps", "+1 damage", tower.getDemageUpgradesGekauft(), 3, tower.getDemageKosten());
            }else if (tower.getDemageUpgradesGekauft() == 1){
                upgradeBox(g2, 725, 165, "Anchored Traps", "+1 damage", tower.getDemageUpgradesGekauft(), 3, tower.getDemageKosten());
            }else{
                upgradeBox(g2, 725, 165, "Persistent Traps", "+1 damage", tower.getDemageUpgradesGekauft(), 3, tower.getDemageKosten());
            }

            // Damage
            if (tower.getPearcingUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 265, "Hunter Mines", "More boss and armor damage", 30_160);
            }else {
                upgradeBox(g2, 725, 265, "Sharpened Traps", "+1 pearcing", tower.getPearcingUpgradesGekauft(), 3, tower.getPearcingKosten());
            }
        }
        //Dorf
        if (tower.getTowerTyp() == 9){
            // Military
            if (tower.getReachUpgradesGekauft() == 3 && !tower.isUltimateGekauft()) {
                ultimateUpgradeBox(g2, 725, 65, "Command Center", "More damage and firerate", 29_155);
            }else if (tower.getReachUpgradesGekauft() == 0){
                upgradeBox(g2, 725, 65, "Attack Speed Boost", "+25 reach", tower.getReachUpgradesGekauft(), 3, tower.getReachKosten());
            }else if (tower.getReachUpgradesGekauft() == 1){
                upgradeBox(g2, 725, 65, "Combat Training", "+25 reach", tower.getReachUpgradesGekauft(), 3, tower.getReachKosten());
            }else {
                upgradeBox(g2, 725, 65, "Damage Boost", "+25 reach", tower.getReachUpgradesGekauft(), 3, tower.getReachKosten());
            }

            // Economy
            if (tower.getDemageUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 165, "Financial District", "More bank income", 27_145);
            }else if (tower.getDemageUpgradesGekauft() > 2){
                upgradeBox(g2, 725, 165, "Bank Support", "+1 damage", tower.getDemageUpgradesGekauft(), 3, tower.getDemageKosten());
            }else{
                upgradeBox(g2, 725, 165, "Investment Network", "+1 damage", tower.getDemageUpgradesGekauft(), 3, tower.getDemageKosten());
            }

            // Technology
            if (tower.getPearcingUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 265, "Research Center", "More detection and piercing", 30_160);
            }else if (tower.getPearcingUpgradesGekauft() == 0){
                upgradeBox(g2, 725, 265, "Detection Support", "+1 pearcing", tower.getPearcingUpgradesGekauft(), 2, tower.getPearcingKosten());
            }else if(tower.getPearcingUpgradesGekauft() == 1){
                upgradeBox(g2, 725, 265, "Armor Piercing Support", "+1 pearcing", tower.getPearcingUpgradesGekauft(), 2, tower.getPearcingKosten());
            }else{
                upgradeBox(g2, 725, 265, "Extended Support Range", "+1 pearcing", tower.getPearcingUpgradesGekauft(), 2, tower.getPearcingKosten());
            }
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

    public void bankUpgradeMenue(Graphics g2, Bank bank) {

        g2.setColor(Color.WHITE);

        g2.fillRect(700, 0, 300, 700);


        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.PLAIN, 25));
        g2.drawString("Bank Upgrades", 725, 25);
        g2.setFont(new Font("Arial", Font.PLAIN, 15));


        g2.drawRect(725, 65, 160, 70);
        g2.drawString(bank.getMoreMoneyUpgrades() + "/3  More cash", 730, 80);
        g2.drawString("+100 Coins", 730, 100);

        if (bank.getMoreMoneyUpgrades() < 3) {
            g2.drawString(bank.getMoreMoneyUpgradeKosten() + " Coins", 730, 120);
        } else {
            g2.drawString("MAX", 730, 120);
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


    private void upgradeBox(Graphics g2, int x, int y, String titel, String effekt, int gekauft, int max, int kosten) {

        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, 200, 70);

        g2.setFont(new Font("Arial", Font.PLAIN, 15));

        g2.drawString(gekauft + "/" + max + "  " + titel, x + 5, y + 15);
        g2.drawString(effekt, x + 5, y + 35);

        if (gekauft < max) {
            g2.drawString(kosten + " Coins", x + 5, y + 55);
        } else {
            g2.drawString("MAX", x + 5, y + 55);
        }
    }

    private void ultimateUpgradeBox(Graphics g2, int x, int y, String titel, String effekt, int kosten) {
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, 180, 80);

        g2.setFont(new Font("Arial", Font.PLAIN, 15));

        g2.drawString(titel, x + 5, y + 18);
        g2.drawString(effekt, x + 5, y + 40);
        g2.drawString(kosten + " Coins", x + 5, y + 65);


    }


}