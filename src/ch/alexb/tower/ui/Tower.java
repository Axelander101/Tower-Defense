package ch.alexb.tower.ui;

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

    // Ein Tower darf nur EIN Ultimate besitzen
    private boolean ultimateGekauft = false;
    private int ultimatePfad = 0;

    // Upgrade-Zähler
    private int reachUpgradesGekauft = 0;
    private int demageUpgradesGekauft = 0;
    private int pearcingUpgradesGekauft = 0;

    // Shotgun
    private int fireRateUpgradesGekauft = 0;
    private int armorUpgradesGekauft = 0;

    // Frost
    private int coldUpgradesGekauft = 0;
    private int brittleUpgradesGekauft = 0;
    private int permafrostUpgradesGekauft = 0;

    // Detector
    private int hunterUpgradesGekauft = 0;
    private int networkUpgradesGekauft = 0;
    private int bossUpgradesGekauft = 0;

    // Panzerbrecher
    private int chargeUpgradesGekauft = 0;
    private int shockwaveUpgradesGekauft = 0;
    private int radiusUpgradesGekauft = 0;

    // Mörser
    private int precisionUpgradesGekauft = 0;
    private int fireUpgradesGekauft = 0;
    private int mortarRadiusUpgradesGekauft = 0;

    // Fallenleger
    private int stockUpgradesGekauft = 0;
    private int controlUpgradesGekauft = 0;
    private int trapDamageUpgradesGekauft = 0;

    // Dorf
    private int militaryUpgradesGekauft = 0;
    private int economyUpgradesGekauft = 0;
    private int technologyUpgradesGekauft = 0;


    // Spezialwerte
    private double slowBonus = 0;
    private double slowFaktor = 1.0;
    private double maxSlow = 0.52;
    private double slowDauerBonus = 0;

    private double brittleBonus = 0;

    private double invisibleDamageFaktor = 1.0;
    private double bossDamageFaktor = 1.0;
    private double armorDamageFaktor = 1.0;

    private double splashFaktor = 1.0;
    private int splashRadiusBonus = 0;

    private boolean kannPanzerTreffen = false;
    private boolean kannExplosionImmuneTreffen = false;

    private double explosionImmuneEffizienz = 0;

    private boolean detectionTeilen = false;
    private boolean mapWideDetection = false;
    private boolean armorPiercingTeilen = false;

    private double alliedDamageBonus = 0;
    private double alliedArmorDamageBonus = 0;

    private boolean burn = false;
    private double burnDamageBonus = 0;
    private double burnDauerBonus = 0;

    private int trapAnzahlBonus = 0;
    private int trapLebensdauerWellen = 0;
    private boolean trapsPermanent = false;
    private int maxGespeicherteFallen = 32;
    private double trapSlow = 0;

    private double dorfFeuerrateBonus = 0.08;
    private double dorfSchadenBonus = 0;
    private double bankIncomeBonus = 0;
    private int supportReichweiteBonus = 0;

    private int projektielgeschwindigkeit = 8;


    private int rueckerstattung = 0;

    private int kosten;

    private int TowerTyp;

    private boolean typSchonBestimmt = false;

    private long letzterSchuss = 0;



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



    public void rueckerstattung(int kosten){
        rueckerstattung += kosten / 2;
    }

    public int getRueckerstattung(){
        return rueckerstattung;
    }


    // Erhöht die Feuerrate
    private void feuerrateErhoehen(double prozent) {
        shootdelay = (int) Math.round(shootdelay / (1.0 + prozent));
    }


    // Gibt die Kosten des nächsten Upgrades zurück
    private int upgradeKosten(int gekauft, int kosten1, int kosten2, int kosten3, int ultimateKosten) {

        if (gekauft == 0) {
            return kosten1;
        }

        if (gekauft == 1) {
            return kosten2;
        }

        if (gekauft == 2) {
            return kosten3;
        }

        if (gekauft == 3) {
            if (ultimateGekauft) {
                return 0;
            }

            return ultimateKosten;
        }

        return 0;
    }


    // Reichweiten-Upgrades
    public void reachUpgradeKaufen() {

        // Normal Tower
        if (TowerTyp == 1) {

            if (reachUpgradesGekauft < 3) {
                reachUpgradesGekauft += 1;
                reichweite += 25;

            } else if (reachUpgradesGekauft == 3 && !ultimateGekauft) {

                // Longshot System
                reachUpgradesGekauft += 1;
                ultimateGekauft = true;
                ultimatePfad = 3;

                demage *= 1.70;
                reichweite += 100;
                projektielgeschwindigkeit =
                        (int) Math.round(projektielgeschwindigkeit * 1.5);
            }
        }

        // Sniper
        else if (TowerTyp == 2) {

            if (reachUpgradesGekauft < 3) {
                reachUpgradesGekauft += 1;
                reichweite += 25;

            } else if (reachUpgradesGekauft == 3 && !ultimateGekauft) {

                // Deadeye Mastery
                reachUpgradesGekauft += 1;
                ultimateGekauft = true;
                ultimatePfad = 3;

                demage *= 2.25;
                reichweite += 100;
                feuerrateErhoehen(0.15);
            }
        }
    }


    // Schadens-Upgrades
    public void demageUpgradeKaufen() {

        if (TowerTyp >= 1 && TowerTyp <= 3) {

            if (demageUpgradesGekauft < 3) {

                demageUpgradesGekauft += 1;
                demage *= 1.22;

            } else if (demageUpgradesGekauft == 3 && !ultimateGekauft) {

                demageUpgradesGekauft += 1;
                ultimateGekauft = true;
                ultimatePfad = 1;

                if (TowerTyp == 1) {

                    // Overdrive Core
                    demage *= 2.20;
                    feuerrateErhoehen(0.35);
                    pearcing += 2;
                    reichweite += 40;

                } else if (TowerTyp == 2) {

                    // Boss Executioner
                    demage *= 1.55;
                    bossDamageFaktor *= 5.0;
                    kannPanzerTreffen = true;

                } else if (TowerTyp == 3) {

                    // Hellfire Shells
                    demage *= 2.20;
                    pearcing += 4;
                    splashFaktor *= 1.65;
                }
            }
        }
    }


    // Piercing-Upgrades
    public void pearcingUpgradeKaufen() {

        if (TowerTyp == 1 || TowerTyp == 2) {

            if (pearcingUpgradesGekauft < 3) {

                pearcingUpgradesGekauft += 1;
                pearcing += 1;

            } else if (pearcingUpgradesGekauft == 3 && !ultimateGekauft) {

                pearcingUpgradesGekauft += 1;
                ultimateGekauft = true;
                ultimatePfad = 2;

                if (TowerTyp == 1) {

                    // Shatter Cannon
                    demage *= 1.55;
                    pearcing += 5;
                    splashFaktor *= 1.5;

                } else if (TowerTyp == 2) {

                    // Hyper Railgun
                    demage *= 1.55;
                    pearcing += 6;
                    kannPanzerTreffen = true;
                    armorDamageFaktor *= 1.18;
                }
            }
        }
    }


    // Shotgun Feuerrate
    public void shotgunFireRateUpgradeKaufen() {

        if (TowerTyp != 3) {
            return;
        }

        if (fireRateUpgradesGekauft < 3) {

            fireRateUpgradesGekauft += 1;

            if (fireRateUpgradesGekauft == 1) {
                feuerrateErhoehen(0.10);

            } else if (fireRateUpgradesGekauft == 2) {
                feuerrateErhoehen(0.10);

            } else if (fireRateUpgradesGekauft == 3) {
                feuerrateErhoehen(0.15);
            }

        } else if (fireRateUpgradesGekauft == 3 && !ultimateGekauft) {

            // Chain Reaction
            fireRateUpgradesGekauft += 1;
            ultimateGekauft = true;
            ultimatePfad = 2;

            demage *= 1.65;
            feuerrateErhoehen(0.20);
            splashFaktor *= 1.95;
        }
    }


    // Shotgun Panzer-Upgrades
    public void shotgunArmorUpgradeKaufen() {

        if (TowerTyp != 3) {
            return;
        }

        if (armorUpgradesGekauft < 3) {

            armorUpgradesGekauft += 1;

            if (armorUpgradesGekauft == 1) {
                pearcing += 1;

            } else if (armorUpgradesGekauft == 2) {
                pearcing += 1;

            } else if (armorUpgradesGekauft == 3) {
                kannPanzerTreffen = true;
            }

        } else if (armorUpgradesGekauft == 3 && !ultimateGekauft) {

            // Armorstorm
            armorUpgradesGekauft += 1;
            ultimateGekauft = true;
            ultimatePfad = 3;

            demage *= 1.80;
            kannPanzerTreffen = true;
            armorDamageFaktor *= 1.85;
        }
    }


    // Frost Kälte
    public void coldUpgradeKaufen() {

        if (TowerTyp != 4) {
            return;
        }

        if (coldUpgradesGekauft < 3) {

            coldUpgradesGekauft += 1;
            slowBonus += 0.035;

        } else if (coldUpgradesGekauft == 3 && !ultimateGekauft) {

            // Absolute Zero
            coldUpgradesGekauft += 1;
            ultimateGekauft = true;
            ultimatePfad = 1;

            slowFaktor *= 2.10;
            maxSlow = 0.70;
        }
    }


    // Frost Brittle
    public void brittleUpgradeKaufen() {

        if (TowerTyp != 4) {
            return;
        }

        if (brittleUpgradesGekauft < 3) {

            brittleUpgradesGekauft += 1;

            if (brittleUpgradesGekauft == 1) {
                brittleBonus += 0.04;

            } else if (brittleUpgradesGekauft == 2) {
                brittleBonus += 0.07;

            } else if (brittleUpgradesGekauft == 3) {
                brittleBonus += 0.11;
            }

        } else if (brittleUpgradesGekauft == 3 && !ultimateGekauft) {

            // Crystal Fracture
            brittleUpgradesGekauft += 1;
            ultimateGekauft = true;
            ultimatePfad = 2;

            brittleBonus = Math.max(brittleBonus, 0.34);
        }
    }


    // Frost Permafrost
    public void permafrostUpgradeKaufen() {

        if (TowerTyp != 4) {
            return;
        }

        if (permafrostUpgradesGekauft < 3) {

            permafrostUpgradesGekauft += 1;

            if (permafrostUpgradesGekauft == 1) {
                reichweite += 25;

            } else if (permafrostUpgradesGekauft == 2) {
                slowDauerBonus += 1;

            } else if (permafrostUpgradesGekauft == 3) {
                slowFaktor *= 1.22;
            }

        } else if (permafrostUpgradesGekauft == 3 && !ultimateGekauft) {

            // Glacial Storm
            permafrostUpgradesGekauft += 1;
            ultimateGekauft = true;
            ultimatePfad = 3;

            reichweite += 90;
            demage *= 1.85;
            slowFaktor *= 1.20;
            splashFaktor *= 1.5;
        }
    }


    // Detector Hunter
    public void hunterUpgradeKaufen() {

        if (TowerTyp != 5) {
            return;
        }

        if (hunterUpgradesGekauft < 3) {

            hunterUpgradesGekauft += 1;
            invisibleDamageFaktor += 0.30;

        } else if (hunterUpgradesGekauft == 3 && !ultimateGekauft) {

            // Phantom Hunter
            hunterUpgradesGekauft += 1;
            ultimateGekauft = true;
            ultimatePfad = 1;

            demage *= 2.05;
            invisibleDamageFaktor *= 2.25;
        }
    }


    // Detector Netzwerk
    public void networkUpgradeKaufen() {

        if (TowerTyp != 5) {
            return;
        }

        if (networkUpgradesGekauft < 3) {

            networkUpgradesGekauft += 1;

            if (networkUpgradesGekauft == 1) {
                reichweite += 30;

            } else if (networkUpgradesGekauft == 2) {
                detectionTeilen = true;

            } else if (networkUpgradesGekauft == 3) {
                supportReichweiteBonus += 50;
            }

        } else if (networkUpgradesGekauft == 3 && !ultimateGekauft) {

            // Omni Radar
            networkUpgradesGekauft += 1;
            ultimateGekauft = true;
            ultimatePfad = 2;

            reichweite += 120;
            detectionTeilen = true;
            mapWideDetection = true;
        }
    }


    // Detector Boss
    public void bossRadarUpgradeKaufen() {

        if (TowerTyp != 5) {
            return;
        }

        if (bossUpgradesGekauft < 3) {

            bossUpgradesGekauft += 1;

            if (bossUpgradesGekauft == 1) {
                bossDamageFaktor += 0.10;

            } else if (bossUpgradesGekauft == 2) {
                bossDamageFaktor += 0.10;

            } else if (bossUpgradesGekauft == 3) {
                bossDamageFaktor += 0.22;
            }

        } else if (bossUpgradesGekauft == 3 && !ultimateGekauft) {

            // Target Designator
            bossUpgradesGekauft += 1;
            ultimateGekauft = true;
            ultimatePfad = 3;

            alliedDamageBonus =
                    Math.max(alliedDamageBonus, 0.18);

            detectionTeilen = true;
        }
    }


    // Panzerbrecher Hohlladung
    public void chargeUpgradeKaufen() {

        if (TowerTyp != 6) {
            return;
        }

        if (chargeUpgradesGekauft < 3) {

            chargeUpgradesGekauft += 1;
            armorDamageFaktor += 0.14;

        } else if (chargeUpgradesGekauft == 3 && !ultimateGekauft) {

            // Worldbreaker
            chargeUpgradesGekauft += 1;
            ultimateGekauft = true;
            ultimatePfad = 1;

            demage *= 2.10;
            bossDamageFaktor *= 1.65;
            kannExplosionImmuneTreffen = true;
            explosionImmuneEffizienz = 0.75;
        }
    }


    // Panzerbrecher Shockwave
    public void shockwaveUpgradeKaufen() {

        if (TowerTyp != 6) {
            return;
        }

        if (shockwaveUpgradesGekauft < 3) {

            shockwaveUpgradesGekauft += 1;

            if (shockwaveUpgradesGekauft == 1) {
                demage *= 1.22;

            } else if (shockwaveUpgradesGekauft == 2) {
                splashRadiusBonus += 25;

            } else if (shockwaveUpgradesGekauft == 3) {
                kannExplosionImmuneTreffen = true;
                explosionImmuneEffizienz = 0.38;
            }

        } else if (shockwaveUpgradesGekauft == 3 && !ultimateGekauft) {

            // Shock Core
            shockwaveUpgradesGekauft += 1;
            ultimateGekauft = true;
            ultimatePfad = 2;

            demage *= 1.48;
            pearcing += 3;
            feuerrateErhoehen(0.20);
            splashFaktor *= 1.75;
        }
    }


    // Panzerbrecher Radius
    public void armorBreakerRadiusUpgradeKaufen() {

        if (TowerTyp != 6) {
            return;
        }

        if (radiusUpgradesGekauft < 3) {

            radiusUpgradesGekauft += 1;

            if (radiusUpgradesGekauft == 1) {
                splashRadiusBonus += 20;

            } else if (radiusUpgradesGekauft == 2) {
                splashRadiusBonus += 25;

            } else if (radiusUpgradesGekauft == 3) {
                splashRadiusBonus += 35;
            }

        } else if (radiusUpgradesGekauft == 3 && !ultimateGekauft) {

            // Armor Shredder
            radiusUpgradesGekauft += 1;
            ultimateGekauft = true;
            ultimatePfad = 3;

            demage *= 1.42;
            alliedArmorDamageBonus = 0.30;
        }
    }


    // Mörser Präzision
    public void precisionUpgradeKaufen() {

        if (TowerTyp != 7) {
            return;
        }

        if (precisionUpgradesGekauft < 3) {

            precisionUpgradesGekauft += 1;

            if (precisionUpgradesGekauft == 1) {
                demage *= 1.22;

            } else if (precisionUpgradesGekauft == 2) {
                demage *= 1.15;

            } else if (precisionUpgradesGekauft == 3) {
                kannPanzerTreffen = true;
                armorDamageFaktor *= 0.72;
            }

        } else if (precisionUpgradesGekauft == 3 && !ultimateGekauft) {

            // Siege Battery
            precisionUpgradesGekauft += 1;
            ultimateGekauft = true;
            ultimatePfad = 1;

            demage *= 1.48;
            feuerrateErhoehen(0.70);
        }
    }


    // Mörser Feuer
    public void mortarFireUpgradeKaufen() {

        if (TowerTyp != 7) {
            return;
        }

        if (fireUpgradesGekauft < 3) {

            fireUpgradesGekauft += 1;

            if (fireUpgradesGekauft == 1) {
                demage += 2;

            } else if (fireUpgradesGekauft == 2) {
                burn = true;
                burnDamageBonus = 0.18;

            } else if (fireUpgradesGekauft == 3) {
                burnDauerBonus += 2;
            }

        } else if (fireUpgradesGekauft == 3 && !ultimateGekauft) {

            // Napalm Barrage
            fireUpgradesGekauft += 1;
            ultimateGekauft = true;
            ultimatePfad = 2;

            demage *= 1.92;
            burn = true;
            kannExplosionImmuneTreffen = true;
            explosionImmuneEffizienz = 0.42;
        }
    }


    // Mörser Radius
    public void mortarRadiusUpgradeKaufen() {

        if (TowerTyp != 7) {
            return;
        }

        if (mortarRadiusUpgradesGekauft < 3) {

            mortarRadiusUpgradesGekauft += 1;

            if (mortarRadiusUpgradesGekauft == 1) {
                splashRadiusBonus += 20;

            } else if (mortarRadiusUpgradesGekauft == 2) {
                splashRadiusBonus += 30;

            } else if (mortarRadiusUpgradesGekauft == 3) {
                splashRadiusBonus += 40;
            }

        } else if (mortarRadiusUpgradesGekauft == 3 && !ultimateGekauft) {

            // Cluster Barrage
            mortarRadiusUpgradesGekauft += 1;
            ultimateGekauft = true;
            ultimatePfad = 3;

            demage *= 1.42;
            splashFaktor *= 1.90;
        }
    }


    // Fallenleger Vorrat
    public void stockUpgradeKaufen() {

        if (TowerTyp != 8) {
            return;
        }

        if (stockUpgradesGekauft < 3) {

            stockUpgradesGekauft += 1;
            feuerrateErhoehen(0.10);

        } else if (stockUpgradesGekauft == 3 && !ultimateGekauft) {

            // Minefield
            stockUpgradesGekauft += 1;
            ultimateGekauft = true;
            ultimatePfad = 1;

            demage *= 1.85;
            trapsPermanent = true;
            maxGespeicherteFallen = 80;
            feuerrateErhoehen(0.50);
        }
    }


    // Fallenleger Kontrolle
    public void controlUpgradeKaufen() {

        if (TowerTyp != 8) {
            return;
        }

        if (controlUpgradesGekauft < 3) {

            controlUpgradesGekauft += 1;

            if (controlUpgradesGekauft == 1) {
                trapLebensdauerWellen += 1;

            } else if (controlUpgradesGekauft == 2) {
                trapLebensdauerWellen += 2;

            } else if (controlUpgradesGekauft == 3) {
                trapsPermanent = true;
            }

        } else if (controlUpgradesGekauft == 3 && !ultimateGekauft) {

            // Containment Grid
            controlUpgradesGekauft += 1;
            ultimateGekauft = true;
            ultimatePfad = 2;

            trapAnzahlBonus += 8;
            demage *= 1.30;
            trapSlow += 0.045;
        }
    }


    // Fallenleger Schaden
    public void trapDamageUpgradeKaufen() {

        if (TowerTyp != 8) {
            return;
        }

        if (trapDamageUpgradesGekauft < 3) {

            trapDamageUpgradesGekauft += 1;
            demage *= 1.25;

            if (trapDamageUpgradesGekauft == 3) {
                kannPanzerTreffen = true;
            }

        } else if (trapDamageUpgradesGekauft == 3 && !ultimateGekauft) {

            // Hunter Mines
            trapDamageUpgradesGekauft += 1;
            ultimateGekauft = true;
            ultimatePfad = 3;

            demage *= 1.65;
            bossDamageFaktor *= 2.0;
            kannPanzerTreffen = true;
            armorDamageFaktor *= 1.35;
        }
    }


    // Dorf Militär
    public void militaryUpgradeKaufen() {

        if (TowerTyp != 9) {
            return;
        }

        if (militaryUpgradesGekauft < 3) {

            militaryUpgradesGekauft += 1;

            if (militaryUpgradesGekauft == 1) {
                dorfFeuerrateBonus =
                        Math.max(dorfFeuerrateBonus, 0.20);

            } else if (militaryUpgradesGekauft == 2) {
                dorfSchadenBonus =
                        Math.max(dorfSchadenBonus, 0.15);

            } else if (militaryUpgradesGekauft == 3) {
                dorfSchadenBonus =
                        Math.max(dorfSchadenBonus, 0.25);
            }

        } else if (militaryUpgradesGekauft == 3 && !ultimateGekauft) {

            // Command Center
            militaryUpgradesGekauft += 1;
            ultimateGekauft = true;
            ultimatePfad = 1;

            dorfFeuerrateBonus =
                    Math.max(dorfFeuerrateBonus, 0.30);

            dorfSchadenBonus =
                    Math.max(dorfSchadenBonus, 0.25);

            supportReichweiteBonus += 100;
        }
    }


    // Dorf Wirtschaft
    public void economyUpgradeKaufen() {

        if (TowerTyp != 9) {
            return;
        }

        if (economyUpgradesGekauft < 3) {

            economyUpgradesGekauft += 1;

            if (economyUpgradesGekauft == 1) {
                bankIncomeBonus = 0.05;

            } else if (economyUpgradesGekauft == 2) {
                bankIncomeBonus = 0.10;

            } else if (economyUpgradesGekauft == 3) {
                bankIncomeBonus = 0.20;
            }

        } else if (economyUpgradesGekauft == 3 && !ultimateGekauft) {

            // Financial District
            economyUpgradesGekauft += 1;
            ultimateGekauft = true;
            ultimatePfad = 2;

            bankIncomeBonus = 0.65;
        }
    }


    // Dorf Technologie
    public void technologyUpgradeKaufen() {

        if (TowerTyp != 9) {
            return;
        }

        if (technologyUpgradesGekauft < 3) {

            technologyUpgradesGekauft += 1;

            if (technologyUpgradesGekauft == 1) {
                supportReichweiteBonus += 50;

            } else if (technologyUpgradesGekauft == 2) {
                detectionTeilen = true;

            } else if (technologyUpgradesGekauft == 3) {
                armorPiercingTeilen = true;
            }

        } else if (technologyUpgradesGekauft == 3 && !ultimateGekauft) {

            // Research Center
            technologyUpgradesGekauft += 1;
            ultimateGekauft = true;
            ultimatePfad = 3;

            dorfSchadenBonus =
                    Math.max(dorfSchadenBonus, 0.18);

            detectionTeilen = true;
            armorPiercingTeilen = true;
            supportReichweiteBonus += 70;
        }
    }


    // Reichweiten-Kosten
    public int getReachKosten() {

        if (TowerTyp == 1) {
            return upgradeKosten(
                    reachUpgradesGekauft,
                    25, 35, 50,
                    32175
            );
        }

        if (TowerTyp == 2) {
            return upgradeKosten(
                    reachUpgradesGekauft,
                    70, 105, 145,
                    26855
            );
        }

        return 0;
    }


    // Schadens-Kosten
    public int getDemageKosten() {

        if (TowerTyp == 1) {
            return upgradeKosten(
                    demageUpgradesGekauft,
                    35, 55, 70,
                    28150
            );
        }

        if (TowerTyp == 2) {
            return upgradeKosten(
                    demageUpgradesGekauft,
                    110, 160, 215,
                    40215
            );
        }

        if (TowerTyp == 3) {
            return upgradeKosten(
                    demageUpgradesGekauft,
                    120, 185, 245,
                    30160
            );
        }

        return 0;
    }


    // Piercing-Kosten
    public int getPearcingKosten() {

        if (TowerTyp == 1) {
            return upgradeKosten(
                    pearcingUpgradesGekauft,
                    55, 80, 110,
                    30160
            );
        }

        if (TowerTyp == 2) {
            return upgradeKosten(
                    pearcingUpgradesGekauft,
                    160, 240, 325,
                    30160
            );
        }

        return 0;
    }


    // Shotgun-Kosten
    public int getShotgunFireRateKosten() {
        return upgradeKosten(fireRateUpgradesGekauft, 110, 180, 275, 29155);
    }


    public int getShotgunArmorKosten() {
        return upgradeKosten(armorUpgradesGekauft, 180, 270, 365, 32175);
    }


    // Frost-Kosten
    public int getColdKosten() {
        return upgradeKosten(coldUpgradesGekauft, 120, 190, 280, 30160);
    }


    public int getBrittleKosten() {
        return upgradeKosten(brittleUpgradesGekauft, 180, 280, 420, 30160);
    }


    public int getPermafrostKosten() {
        return upgradeKosten(permafrostUpgradesGekauft, 140, 230, 360, 31170);
    }


    // Detector-Kosten
    public int getHunterKosten() {
        return upgradeKosten(hunterUpgradesGekauft, 130, 210, 330, 27145);
    }


    public int getNetworkKosten() {
        return upgradeKosten(networkUpgradesGekauft, 140, 220, 305, 29155);
    }


    public int getBossRadarKosten() {
        return upgradeKosten(bossUpgradesGekauft, 150, 240, 385, 30160);
    }


    // Panzerbrecher-Kosten
    public int getChargeKosten() {
        return upgradeKosten(chargeUpgradesGekauft, 170, 260, 360, 34185);
    }


    public int getShockwaveKosten() {
        return upgradeKosten(shockwaveUpgradesGekauft, 185, 300, 420, 29155);
    }


    public int getArmorBreakerRadiusKosten() {
        return upgradeKosten(radiusUpgradesGekauft, 150, 240, 360, 31170);
    }


    // Mörser-Kosten
    public int getPrecisionKosten() {
        return upgradeKosten(precisionUpgradesGekauft, 280, 400, 460, 36195);
    }


    public int getMortarFireKosten() {
        return upgradeKosten(fireUpgradesGekauft, 250, 360, 520, 34185);
    }


    public int getMortarRadiusKosten() {
        return upgradeKosten(mortarRadiusUpgradesGekauft, 180, 280, 420, 35190);
    }


    // Fallenleger-Kosten
    public int getStockKosten() {
        return upgradeKosten(stockUpgradesGekauft, 120, 190, 285, 25135);
    }


    public int getControlKosten() {
        return upgradeKosten(controlUpgradesGekauft, 140, 220, 265, 26140);
    }


    public int getTrapDamageKosten() {
        return upgradeKosten(trapDamageUpgradesGekauft, 140, 220, 365, 28150);
    }


    // Dorf-Kosten
    public int getMilitaryKosten() {
        return upgradeKosten(militaryUpgradesGekauft, 220, 260, 360, 36195);
    }


    public int getEconomyKosten() {
        return upgradeKosten(economyUpgradesGekauft, 180, 240, 300, 40215);
    }


    public int getTechnologyKosten() {
        return upgradeKosten(technologyUpgradesGekauft, 250, 340, 420, 30160);
    }


    // Upgrade-Getter
    public int getReachUpgradesGekauft() {
        return reachUpgradesGekauft;
    }

    public int getDemageUpgradesGekauft() {
        return demageUpgradesGekauft;
    }

    public int getPearcingUpgradesGekauft() {
        return pearcingUpgradesGekauft;
    }

    public int getFireRateUpgradesGekauft() {
        return fireRateUpgradesGekauft;
    }

    public int getArmorUpgradesGekauft() {
        return armorUpgradesGekauft;
    }

    public int getColdUpgradesGekauft() {
        return coldUpgradesGekauft;
    }

    public int getBrittleUpgradesGekauft() {
        return brittleUpgradesGekauft;
    }

    public int getPermafrostUpgradesGekauft() {
        return permafrostUpgradesGekauft;
    }

    public int getHunterUpgradesGekauft() {
        return hunterUpgradesGekauft;
    }

    public int getNetworkUpgradesGekauft() {
        return networkUpgradesGekauft;
    }

    public int getBossUpgradesGekauft() {
        return bossUpgradesGekauft;
    }

    public int getChargeUpgradesGekauft() {
        return chargeUpgradesGekauft;
    }

    public int getShockwaveUpgradesGekauft() {
        return shockwaveUpgradesGekauft;
    }

    public int getRadiusUpgradesGekauft() {
        return radiusUpgradesGekauft;
    }

    public int getPrecisionUpgradesGekauft() {
        return precisionUpgradesGekauft;
    }

    public int getFireUpgradesGekauft() {
        return fireUpgradesGekauft;
    }

    public int getMortarRadiusUpgradesGekauft() {
        return mortarRadiusUpgradesGekauft;
    }

    public int getStockUpgradesGekauft() {
        return stockUpgradesGekauft;
    }

    public int getControlUpgradesGekauft() {
        return controlUpgradesGekauft;
    }

    public int getTrapDamageUpgradesGekauft() {
        return trapDamageUpgradesGekauft;
    }

    public int getMilitaryUpgradesGekauft() {
        return militaryUpgradesGekauft;
    }

    public int getEconomyUpgradesGekauft() {
        return economyUpgradesGekauft;
    }

    public int getTechnologyUpgradesGekauft() {
        return technologyUpgradesGekauft;
    }


    // Ultimate-Getter
    public boolean isUltimateGekauft() {
        return ultimateGekauft;
    }

    public int getUltimatePfad() {
        return ultimatePfad;
    }


    // Effekt-Getter
    public int getPearcing() {
        return pearcing;
    }

    public double getInvisibleDamageFaktor() {
        return invisibleDamageFaktor;
    }

    public double getBossDamageFaktor() {
        return bossDamageFaktor;
    }

    public double getArmorDamageFaktor() {
        return armorDamageFaktor;
    }

    public double getSplashFaktor() {
        return splashFaktor;
    }

    public int getSplashRadiusBonus() {
        return splashRadiusBonus;
    }

    public boolean kannPanzerTreffen() {
        return kannPanzerTreffen;
    }

    public boolean kannExplosionImmuneTreffen() {
        return kannExplosionImmuneTreffen;
    }

    public double getExplosionImmuneEffizienz() {
        return explosionImmuneEffizienz;
    }

    public double getSlowBonus() {
        return slowBonus;
    }

    public double getSlowFaktor() {
        return slowFaktor;
    }

    public double getMaxSlow() {
        return maxSlow;
    }

    public double getSlowDauerBonus() {
        return slowDauerBonus;
    }

    public double getBrittleBonus() {
        return brittleBonus;
    }

    public boolean isDetectionTeilen() {
        return detectionTeilen;
    }

    public boolean isMapWideDetection() {
        return mapWideDetection;
    }

    public boolean isArmorPiercingTeilen() {
        return armorPiercingTeilen;
    }

    public double getAlliedDamageBonus() {
        return alliedDamageBonus;
    }

    public double getAlliedArmorDamageBonus() {
        return alliedArmorDamageBonus;
    }

    public boolean isBurn() {
        return burn;
    }

    public double getBurnDamageBonus() {
        return burnDamageBonus;
    }

    public double getBurnDauerBonus() {
        return burnDauerBonus;
    }

    public int getTrapAnzahlBonus() {
        return trapAnzahlBonus;
    }

    public int getTrapLebensdauerWellen() {
        return trapLebensdauerWellen;
    }

    public boolean isTrapsPermanent() {
        return trapsPermanent;
    }

    public int getMaxGespeicherteFallen() {
        return maxGespeicherteFallen;
    }

    public double getTrapSlow() {
        return trapSlow;
    }

    public double getDorfFeuerrateBonus() {
        return dorfFeuerrateBonus;
    }

    public double getDorfSchadenBonus() {
        return dorfSchadenBonus;
    }

    public double getBankIncomeBonus() {
        return bankIncomeBonus;
    }

    public int getSupportReichweiteBonus() {
        return supportReichweiteBonus;
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

    public int getTowerTyp(){
        return TowerTyp;
    }

    public int getReichweite() {
        return reichweite;
    }

}
