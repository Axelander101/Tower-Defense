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
            if (tower.getPearcingUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 265, "Shatter Cannon", "More piercing and damage", 30_160);
            }else {
                upgradeBox(g2, 725, 265, "Piercing Ammo", "+1 pearcing", tower.getPearcingUpgradesGekauft(), 3, tower.getPearcingKosten());
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
            if (tower.getPearcingUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 265, "Hyper Railgun", "More piercing and damage", 30_160);
            }else {
                upgradeBox(g2, 725, 265, "Armor Piercing Rounds", "+1 pearcing", tower.getPearcingUpgradesGekauft(), 3, tower.getPearcingKosten());
            }
        }
        //Shotgun Upgrades
        if (tower.getTowerTyp() == 3){
            // Armor
            if (tower.getArmorUpgradesGekauft() == 3 && !tower.isUltimateGekauft()) {
                ultimateUpgradeBox(g2, 725, 65, "Armorstorm", "More armor damage", 32_175);
            }else{
                upgradeBox(g2, 725, 65, "Hardened Pellets", "+25 reach", tower.getArmorUpgradesGekauft(), 3, tower.getShotgunArmorKosten());
            }

            // Demage
            if (tower.getDemageUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 165, "Hellfire Shells", "More close range damage", 30_160);
            }else{
                upgradeBox(g2, 725, 165, "Heavy Shells", "+1 damage", tower.getDemageUpgradesGekauft(), 3, tower.getDemageKosten());
            }

            // Fire Rate
            if (tower.getFireRateUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 265, "Chain Reaction", "More firerate and splash", 29_155);
            }else {
                upgradeBox(g2, 725, 265, "Rapid Action", "More fire rate", tower.getFireRateUpgradesGekauft(), 3, tower.getShotgunFireRateKosten());
            }
        }
        //Eis Turm Upgrades
        if (tower.getTowerTyp() == 4){
            // Cold
            if (tower.getColdUpgradesGekauft() == 3 && !tower.isUltimateGekauft()) {
                ultimateUpgradeBox(g2, 725, 65, "Absolute Zero", "Stronger and longer slow", 30_160);
            }else{
                upgradeBox(g2, 725, 65, "Deep Freeze", "Stronger slow", tower.getColdUpgradesGekauft(), 3, tower.getColdKosten());
            }

            // Brittle
            if (tower.getBrittleUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 165, "Crystal Fracture", "More damage to slowed", 30_160);
            }else if (tower.getBrittleUpgradesGekauft() == 0){
                upgradeBox(g2, 725, 165, "Frozen Impact", "More damage taken", tower.getBrittleUpgradesGekauft(), 3, tower.getBrittleKosten());
            }else if (tower.getBrittleUpgradesGekauft() == 1){
                upgradeBox(g2, 725, 165, "Brittle Ice", "More damage taken", tower.getBrittleUpgradesGekauft(), 3, tower.getBrittleKosten());
            }else {
                upgradeBox(g2, 725, 165, "Crystal Weakness", "More damage to slowed", tower.getBrittleUpgradesGekauft(), 3, tower.getBrittleKosten());
            }

            // Permafrost / Range
            if (tower.getPermafrostUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 265, "Glacial Storm", "More range and slow", 31_170);
            }else if (tower.getPermafrostUpgradesGekauft() == 0){
                upgradeBox(g2, 725, 265, "Extended Frost Range", "More frost range", tower.getPermafrostUpgradesGekauft(), 3, tower.getPermafrostKosten());
            }else if (tower.getPermafrostUpgradesGekauft() == 1){
                upgradeBox(g2, 725, 265, "Lingering Frost", "Longer slow", tower.getPermafrostUpgradesGekauft(), 3, tower.getPermafrostKosten());
            }else{
                upgradeBox(g2, 725, 265, "Permafrost", "Stronger slow", tower.getPermafrostUpgradesGekauft(), 3, tower.getPermafrostKosten());
            }
        }
        //Detector Upgrades
        if (tower.getTowerTyp() == 5){
            // Network
            if (tower.getNetworkUpgradesGekauft() == 3 && !tower.isUltimateGekauft()) {
                ultimateUpgradeBox(g2, 725, 65, "Omni Radar", "More detection range", 29_155);
            }else if (tower.getNetworkUpgradesGekauft() == 0){
                upgradeBox(g2, 725, 65, "Extended Radar", "More range", tower.getNetworkUpgradesGekauft(), 3, tower.getNetworkKosten());
            }else if(tower.getNetworkUpgradesGekauft() == 1){
                upgradeBox(g2, 725, 65, "Detection Relay", "Shares detection", tower.getNetworkUpgradesGekauft(), 3, tower.getNetworkKosten());
            }else{
                upgradeBox(g2, 725, 65, "Detection Network", "More support range", tower.getNetworkUpgradesGekauft(), 3, tower.getNetworkKosten());
            }

            // Hunter
            if (tower.getHunterUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 165, "Phantom Hunter", "More invisible enemy damage", 27_145);
            }else if (tower.getHunterUpgradesGekauft() == 0){
                upgradeBox(g2, 725, 165, "Target Analysis", "More invisible damage", tower.getHunterUpgradesGekauft(), 3, tower.getHunterKosten());
            }else if(tower.getHunterUpgradesGekauft() == 1){
                upgradeBox(g2, 725, 165, "Ghost Tracking", "More invisible damage", tower.getHunterUpgradesGekauft(), 3, tower.getHunterKosten());
            }else {
                upgradeBox(g2, 725, 165, "Silent Hunter", "More invisible damage", tower.getHunterUpgradesGekauft(), 3, tower.getHunterKosten());
            }


            // Boss Radar
            if (tower.getBossUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 265, "Target Designator", "More allied tower damage", 30_160);
            }else if (tower.getBossUpgradesGekauft() == 0){
                upgradeBox(g2, 725, 265, "Combat Radar", "More boss damage", tower.getBossUpgradesGekauft(), 3, tower.getBossRadarKosten());
            }else if (tower.getBossUpgradesGekauft() == 1){
                upgradeBox(g2, 725, 265, "Elite Scanner", "More boss damage", tower.getBossUpgradesGekauft(), 3, tower.getBossRadarKosten());
            }else{
                upgradeBox(g2, 725, 265, "Boss Radar", "More boss damage", tower.getBossUpgradesGekauft(), 3, tower.getBossRadarKosten());
            }


        }
        //Bomber
        if (tower.getTowerTyp() == 6){
            // Charge
            if (tower.getChargeUpgradesGekauft() == 3 && !tower.isUltimateGekauft()) {
                ultimateUpgradeBox(g2, 725, 65, "Worldbreaker", "More armor and boss damage", 29_155);
            }else{
                upgradeBox(g2, 725, 65, "Shaped Charge", "More armor damage", tower.getChargeUpgradesGekauft(), 3, tower.getChargeKosten());
            }

            // Shockwave
            if (tower.getShockwaveUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 165, "Shock Core", "More splash and firerate", 27_145);
            }else if(tower.getShockwaveUpgradesGekauft() == 0){
                upgradeBox(g2, 725, 165, "Heavy Warhead", "More damage", tower.getShockwaveUpgradesGekauft(), 3, tower.getShockwaveKosten());
            }else if(tower.getShockwaveUpgradesGekauft() == 1){
                upgradeBox(g2, 725, 165, "Shockwave Amplifier", "More splash", tower.getShockwaveUpgradesGekauft(), 3, tower.getShockwaveKosten());
            }else{
                upgradeBox(g2, 725, 165, "Shockwave", "Hits immune enemies", tower.getShockwaveUpgradesGekauft(), 3, tower.getShockwaveKosten());
            }

            // Radius
            if (tower.getRadiusUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 265, "Armor Shredder", "More armor penetration", 30_160);
            }else {
                upgradeBox(g2, 725, 265, "Blast Radius", "Larger explosion", tower.getRadiusUpgradesGekauft(), 3, tower.getArmorBreakerRadiusKosten());
            }
        }
        //Mörser
        if (tower.getTowerTyp() == 7){
            // Precision
            if (tower.getPrecisionUpgradesGekauft() == 3 && !tower.isUltimateGekauft()) {
                ultimateUpgradeBox(g2, 725, 65, "Siege Battery", "More damage and firerate", 29_155);
            }else if (tower.getPrecisionUpgradesGekauft() == 0){
                upgradeBox(g2, 725, 65, "Precision Targeting", "More damage", tower.getPrecisionUpgradesGekauft(), 2, tower.getPrecisionKosten());
            }else if (tower.getPrecisionUpgradesGekauft() == 1){
                upgradeBox(g2, 725, 65, "Reinforced Shells", "More damage", tower.getPrecisionUpgradesGekauft(), 2, tower.getPrecisionKosten());
            }else{
                upgradeBox(g2, 725, 65, "Bunker Buster", "Hits armor", tower.getPrecisionUpgradesGekauft(), 2, tower.getPrecisionKosten());
            }

            // Fire
            if (tower.getFireUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 165, "Napalm Barrage", "More fire damage", 27_145);
            }else if (tower.getFireUpgradesGekauft() == 0){
                upgradeBox(g2, 725, 165, "Heavy Shells", "More damage", tower.getFireUpgradesGekauft(), 3, tower.getMortarFireKosten());
            }else if (tower.getFireUpgradesGekauft() == 1){
                upgradeBox(g2, 725, 165, "Incendiary Shells", "Adds burn damage", tower.getFireUpgradesGekauft(), 3, tower.getMortarFireKosten());
            }else{
                upgradeBox(g2, 725, 165, "Burning Payload", "Longer burn", tower.getFireUpgradesGekauft(), 3, tower.getMortarFireKosten());
            }

            // Radius
            if (tower.getMortarRadiusUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 265, "Cluster Barrage", "More splash damage", 30_160);
            }else {
                upgradeBox(g2, 725, 265, "Blast Radius", "Larger explosion", tower.getMortarRadiusUpgradesGekauft(), 3, tower.getMortarRadiusKosten());
            }
        }
        //Fallenleger
        if (tower.getTowerTyp() == 8){
            // Fire Rate
            if (tower.getStockUpgradesGekauft() == 3 && !tower.isUltimateGekauft()) {
                ultimateUpgradeBox(g2, 725, 65, "Minefield", "More persistent mines", 29_155);
            }else{
                upgradeBox(g2, 725, 65, "Trap Stock", "More fire rate", tower.getStockUpgradesGekauft(), 3, tower.getStockKosten());
            }

            // Control
            if (tower.getControlUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 165, "Containment Grid", "More traps and slow", 27_145);
            }else if (tower.getControlUpgradesGekauft() == 0){
                upgradeBox(g2, 725, 165, "Reinforced Traps", "Longer lifetime", tower.getControlUpgradesGekauft(), 3, tower.getControlKosten());
            }else if (tower.getControlUpgradesGekauft() == 1){
                upgradeBox(g2, 725, 165, "Anchored Traps", "Longer lifetime", tower.getControlUpgradesGekauft(), 3, tower.getControlKosten());
            }else{
                upgradeBox(g2, 725, 165, "Persistent Traps", "Traps stay", tower.getControlUpgradesGekauft(), 3, tower.getControlKosten());
            }

            // Damage
            if (tower.getTrapDamageUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 265, "Hunter Mines", "More boss and armor damage", 30_160);
            }else {
                upgradeBox(g2, 725, 265, "Sharpened Traps", "+1 demage", tower.getTrapDamageUpgradesGekauft(), 3, tower.getTrapDamageKosten());
            }
        }
        //Dorf
        if (tower.getTowerTyp() == 9){
            // Military
            if (tower.getMilitaryUpgradesGekauft() == 3 && !tower.isUltimateGekauft()) {
                ultimateUpgradeBox(g2, 725, 65, "Command Center", "More damage and firerate", 29_155);
            }else if (tower.getMilitaryUpgradesGekauft() == 0){
                upgradeBox(g2, 725, 65, "Attack Speed Boost", "More fire rate", tower.getMilitaryUpgradesGekauft(), 3, tower.getMilitaryKosten());
            }else if (tower.getMilitaryUpgradesGekauft() == 1){
                upgradeBox(g2, 725, 65, "Combat Training", "More tower damage", tower.getMilitaryUpgradesGekauft(), 3, tower.getMilitaryKosten());
            }else {
                upgradeBox(g2, 725, 65, "Damage Boost", "More tower damage", tower.getMilitaryUpgradesGekauft(), 3, tower.getMilitaryKosten());
            }

            // Economy
            if (tower.getEconomyUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 165, "Financial District", "More bank income", 27_145);
            }else if (tower.getEconomyUpgradesGekauft() > 2){
                upgradeBox(g2, 725, 165, "Bank Support", "More bank income", tower.getEconomyUpgradesGekauft(), 3, tower.getEconomyKosten());
            }else{
                upgradeBox(g2, 725, 165, "Investment Network", "More bank income", tower.getEconomyUpgradesGekauft(), 3, tower.getEconomyKosten());
            }

            // Technology
            if (tower.getTechnologyUpgradesGekauft() == 3 && !tower.isUltimateGekauft()){
                ultimateUpgradeBox(g2, 725, 265, "Research Center", "More detection and piercing", 30_160);
            }else if (tower.getTechnologyUpgradesGekauft() == 0){
                upgradeBox(g2, 725, 265, "Detection Support", "More support range", tower.getTechnologyUpgradesGekauft(), 3, tower.getTechnologyKosten());
            }else if(tower.getTechnologyUpgradesGekauft() == 1){
                upgradeBox(g2, 725, 265, "Armor Piercing Support", "Shares detection", tower.getTechnologyUpgradesGekauft(), 3, tower.getTechnologyKosten());
            }else{
                upgradeBox(g2, 725, 265, "Extended Support Range", "Shares armor piercing", tower.getTechnologyUpgradesGekauft(), 3, tower.getTechnologyKosten());
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

        if (gekauft == 4){
            g2.drawString(gekauft + "/" + 4 + "  " + titel, x + 5, y + 15);
        }else{
            g2.drawString(gekauft + "/" + max + "  " + titel, x + 5, y + 15);
        }

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