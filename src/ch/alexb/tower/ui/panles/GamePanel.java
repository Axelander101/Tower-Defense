package ch.alexb.tower.ui.panles;

import ch.alexb.tower.sounds.Sound;
import ch.alexb.tower.ui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.ArrayDeque;
import java.util.Queue;
import java.awt.geom.Path2D;

public class GamePanel extends JPanel
        implements MouseListener, MouseMotionListener, MouseWheelListener {


    ArrayList<Point> wegPunkte = new ArrayList<>();

    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<FastEnemy> fastEnemies = new ArrayList<>();
    ArrayList<StrongEnemy> strongEnemies = new ArrayList<>();
    ArrayList<Boss> bosses = new ArrayList<>();
    ArrayList<Tower> towers = new ArrayList<>();
    ArrayList<Bank> banks = new ArrayList<>();
    ArrayList<Projektiel> projektiele = new ArrayList<>();

    ArrayList<Integer> plaziertx = new ArrayList<>();
    ArrayList<Integer> plazierty = new ArrayList<>();

    private final Queue<Integer> spawnQueue = new ArrayDeque<>();

    private static final int NORMAL = 1;
    private static final int FAST = 2;
    private static final int STRONG = 3;
    private static final int BOSS = 4;


    private int turmScrollOffset = 0;
    private final int TURM_BREITE = 60;
    private final int ANZAHL_TUERME = 10;


    UpgradePanel upgradePanel = new UpgradePanel();
    InGameMenuePanel inGameMenuePanel = new InGameMenuePanel();

    private CardLayout cardLayout;
    private JPanel hauptPanel;

    Tower ausgewaehlterTower;
    Bank ausgewaehlteBank;

    boolean upgradeMenueOffen = false;
    boolean einstellungenOffen = false;

    JSlider shootLautstaerkeSlider = new JSlider(0, 100, 50);
    JSlider popLautstaerkeSlider = new JSlider(0, 100, 50);

    int leben = 20;
    int geld = 100000;


    boolean gameOver = false;
    boolean spielMunueOffen = false;
    boolean turmMenueOffen = false;

    boolean doppelgeschwindigkeit = false;

    boolean bankSchonGezahlt = false;

    private static final int WEG_Y = 300;
    private static final int SPIEL_BREITE = 1000;
    private static final int SPIEL_HOEHE = 700;

    int welle = 0;
    int gegnerGetoetet = 0;
    int totalGeld = 100;
    int map;

    double budget = 0;

    int gegnerAufBildschirm = 0;

    double reichweite;

    int zahl;

    int typ = 1;

    int moerserZielX;
    int moerserZielY;
    int fallenlegerZielX;
    int fallenlegerZielY;


    int spielgeschwindigkeit = 1;

    int spawnDelay = 1000;
    int minSpawnDelay = 100;
    int shootDelay = 500;
    int mainDelay = 16;


    float shootLautstaerke = -15.0f;
    float popLautstaerke = -15.0f;




    Timer spawnTimer;
    Timer gameTimer;
    Timer schiessenTimer;

    Random random = new Random();

    public void positionFreigeben(int x, int y){

        for (int i = 0; i < plaziertx.size(); i++){

            if (plaziertx.get(i) == x && plazierty.get(i) == y){

                plaziertx.remove(i);

                plazierty.remove(i);

                break;
            }
        }
    }

    public double abstandZumNaechstenWegPunkt(Gegner gegner){

        int punkt = gegner.getAktuellerWegPunkt();


        if (punkt >= wegPunkte.size()){
            return 0;
        }


        Point ziel = wegPunkte.get(punkt);

        double dx = ziel.x - gegner.getX();
        double dy = ziel.y - gegner.getY();


        return Math.sqrt(dx * dx + dy * dy);
    }

    public boolean istAufWeg(int x, int y){

        int sicherheitsAbstand = 80;

        for (int i = 0; i < wegPunkte.size() - 1; i++){

            Point p1 = wegPunkte.get(i);
            Point p2 = wegPunkte.get(i + 1);

            double abstand = Line2D.ptSegDist(p1.x, p1.y, p2.x, p2.y, x, y);

            if (abstand < sicherheitsAbstand){
                return true;
            }
        }

        return false;
    }

    public void gameStarten(int ausgewaehlteMap){

        map = ausgewaehlteMap;

        wegPunkte.clear();

        wegPunkte = MapDaten.getWegPunkte(map);


        spawnTimer.start();
        schiessenTimer.start();
        gameTimer.start();
    }

    // Wellen vorbereiten
    private int gegnerTypBestimmen(int aktuelleWelle) {

        double r = random.nextDouble();


        // Welle 1 - 4
        if (aktuelleWelle < 5) {

            return NORMAL;
        }


        // Welle 5 - 14
        if (aktuelleWelle < 15) {

            if (r < 0.70) {
                return NORMAL;
            } else {
                return FAST;
            }
        }


        // Welle 15 - 29
        if (aktuelleWelle < 30) {

            if (r < 0.50) {
                return NORMAL;
            }

            if (r < 0.80) {
                return FAST;
            }

            return STRONG;
        }


        // Welle 30 - 49
        if (aktuelleWelle < 50) {

            if (r < 0.40) {
                return NORMAL;
            }

            if (r < 0.70) {
                return FAST;
            }

            return STRONG;
        }


        // Welle 50 - 69
        if (aktuelleWelle < 70) {

            if (r < 0.30) {
                return NORMAL;
            }

            if (r < 0.60) {
                return FAST;
            }

            return STRONG;
        }


        // Ab Welle 70
        if (r < 0.25) {
            return NORMAL;
        }

        if (r < 0.55) {
            return FAST;
        }

        return STRONG;
    }

    private void welleVorbereiten(int aktuelleWelle) {

        spawnQueue.clear();

        int gegnerAnzahl = Math.max(
                1,
                (int) Math.round(
                        5
                                + 1.25 * aktuelleWelle
                                + 0.0045 * aktuelleWelle * aktuelleWelle
                )
        );


        boolean bossWelle =
                aktuelleWelle >= 20
                        && aktuelleWelle % 10 == 0;


        // Bei Bosswellen nur 45 % der normalen Gegner
        if (bossWelle) {

            int begleitGegner =
                    (int) Math.round(gegnerAnzahl * 0.45);

            // Boss hinzufügen
            spawnQueue.add(BOSS);

            // Begleitgegner
            for (int i = 0; i < begleitGegner; i++) {

                spawnQueue.add(
                        gegnerTypBestimmen(aktuelleWelle)
                );
            }

        } else {

            // Normale Welle
            for (int i = 0; i < gegnerAnzahl; i++) {

                spawnQueue.add(
                        gegnerTypBestimmen(aktuelleWelle)
                );
            }
        }
    }

    private void gegnerSpawnen(int gegnerTyp) {

        double hpFaktor = hpMultiplikator(welle);
        double mapFaktor = mapHpFaktor();


        switch (gegnerTyp) {

            case NORMAL -> {

                Enemy enemy = new Enemy(wegPunkte);

                int leben = Math.max(
                        1,
                        (int) Math.round(
                                4 * hpFaktor * mapFaktor
                        )
                );

                enemy.setLeben(leben);

                enemies.add(enemy);

                gegnerAufBildschirm++;
            }


            case FAST -> {

                FastEnemy enemy =
                        new FastEnemy(wegPunkte);

                int leben = Math.max(
                        1,
                        (int) Math.round(
                                3 * hpFaktor * mapFaktor
                        )
                );

                enemy.setLeben(leben);

                fastEnemies.add(enemy);

                gegnerAufBildschirm++;
            }


            case STRONG -> {

                StrongEnemy enemy =
                        new StrongEnemy(wegPunkte);

                int leben = Math.max(
                        1,
                        (int) Math.round(
                                10 * hpFaktor * mapFaktor
                        )
                );

                enemy.setLeben(leben);

                strongEnemies.add(enemy);

                gegnerAufBildschirm++;
            }


            case BOSS -> {

                Boss boss =
                        new Boss(wegPunkte);

                double bossFaktor;

                if (map == 1) {
                    bossFaktor = 0.65;
                } else {
                    bossFaktor = 0.56;
                }


                int leben = Math.max(
                        1,
                        (int) Math.round(
                                (30 + welle * 2.5)
                                        * hpFaktor
                                        * mapFaktor
                                        * bossFaktor
                        )
                );

                boss.setLeben(leben);

                bosses.add(boss);

                gegnerAufBildschirm++;
            }
        }
    }

    private double hpMultiplikator(int aktuelleWelle) {

        int x = aktuelleWelle - 1;


        double basis =
                1.0
                        + x * 0.035
                        + x * x * 0.0018;


        double lateGame =
                1.0
                        + Math.max(
                        0,
                        aktuelleWelle - 40
                ) * 0.011;


        return basis * lateGame;
    }

    private double mapHpFaktor() {

        if (map == 1) {
            return 0.92;
        }

        return 1.07;
    }


    // Eigentliches Game
    public GamePanel(CardLayout cardLayout, JPanel hauptPanel) {

        this.cardLayout = cardLayout;
        this.hauptPanel = hauptPanel;


        setLayout(null);
        shootLautstaerkeSlider.setVisible(false);

        add(shootLautstaerkeSlider);

        shootLautstaerkeSlider.addChangeListener(e -> {

             int wert = shootLautstaerkeSlider.getValue();

             shootLautstaerke = -40.0f + wert * 0.4f;

             if (wert == 0){
                 shootLautstaerke = -50.0f;
             }

        });

        setLayout(null);
        popLautstaerkeSlider.setVisible(false);

        add(popLautstaerkeSlider);

        popLautstaerkeSlider.addChangeListener(e -> {

            int wert = popLautstaerkeSlider.getValue();

            popLautstaerke = -40.0f + wert * 0.4f;

            if (wert == 0){
                popLautstaerke = -50.0f;
            }

        });



        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);


        // Gegner spawnen
        spawnTimer = new Timer(950, e -> {

            // Neue Welle starten, wenn:
            // 1. keine Gegner mehr leben
            // 2. keine Gegner mehr auf das Spawnen warten
            if (gegnerAufBildschirm == 0 && spawnQueue.isEmpty()) {

                welle++;

                welleVorbereiten(welle);

                // Banken dürfen am Ende dieser neuen Welle wieder auszahlen
                bankSchonGezahlt = false;
                //Alle falen löschen
                projektiele.clear();

            }


            // Wenn noch Gegner gespawnt werden müssen
            if (!spawnQueue.isEmpty()) {

                int gegnerTyp = spawnQueue.poll();

                gegnerSpawnen(gegnerTyp);
            }


            // Spawn-Geschwindigkeit für aktuelle Welle
            int maxSpawnDelay = 950 - (welle - 1) * 6 / spielgeschwindigkeit;
            int minSpawnDelay = 100 / spielgeschwindigkeit;

            // Niemals schneller als max. 350 ms Obergrenze
            maxSpawnDelay = Math.max(350 / spielgeschwindigkeit, maxSpawnDelay);

            // Tatsächlicher Abstand zufällig zwischen 100 ms und maxSpawnDelay
            int delay = random.nextInt(minSpawnDelay, maxSpawnDelay + 1);

            spawnTimer.setDelay(delay);
        });


        // Tower schießen
        schiessenTimer = new Timer(50, e -> {


            ArrayList<Gegner> alleGegner = new ArrayList<>();

            alleGegner.addAll(enemies);
            alleGegner.addAll(fastEnemies);
            alleGegner.addAll(strongEnemies);
            alleGegner.addAll(bosses);


            for (Tower tower : towers){

                if (tower.getShootdelay() <= 0 || !tower.kannSchiessen()){
                    continue;
                }

                int towerX = tower.getMousex();
                int towerY = tower.getMousey();


                Gegner zielEnemy = null;

                int weitesterWegPunkt = -1;

                double kleinsterAbstand = Double.MAX_VALUE;


                for (Gegner gegner : alleGegner){

                    if (tower.getTowerTyp() != 7 && tower.getTowerTyp() != 8){
                        if (tower.istInReichweite(towerX, towerY, gegner.getX(), gegner.getY())) {

                            int wegPunkt = gegner.getAktuellerWegPunkt();


                            if (wegPunkt > weitesterWegPunkt) {

                                zielEnemy = gegner;

                                weitesterWegPunkt = wegPunkt;

                                kleinsterAbstand = abstandZumNaechstenWegPunkt(gegner);
                            } else if (wegPunkt == weitesterWegPunkt) {

                                double abstand = abstandZumNaechstenWegPunkt(gegner);


                                if (abstand < kleinsterAbstand) {

                                    zielEnemy = gegner;

                                    kleinsterAbstand = abstand;
                                }
                            }
                        }
                    }

                }


                if (zielEnemy != null){

                    double reichweite = tower.Reichweite(
                            towerX,
                            towerY,
                            zielEnemy.getX(),
                            zielEnemy.getY()
                    );


                    Projektiel projektiel = new Projektiel(
                            towerX,
                            towerY,
                            zielEnemy,
                            wegPunkte,
                            reichweite,
                            tower
                    );


                    projektiele.add(projektiel);

                    Sound.abspielen("sounds/shoot.wav", shootLautstaerke);

                    tower.schussGemacht();
                }

                if (tower.getTowerTyp() == 7){
                    Projektiel projektiel = new Projektiel(towerX, towerY, moerserZielX, moerserZielY, tower);
                    tower.schussGemacht();
                    projektiele.add(projektiel);

                } else if (tower.getTowerTyp() == 8) {
                    Projektiel projektiel = new Projektiel(towerX, towerY, fallenlegerZielX, fallenlegerZielX, tower);
                    tower.schussGemacht();
                    projektiele.add(projektiel);
                }


            }
        });


        // Hauptspiel
        gameTimer = new Timer(mainDelay, e -> {

            gameTimer.setDelay(mainDelay);

            // Normale Gegner bewegen
            for (int i = enemies.size() - 1; i >= 0; i--){

                Enemy enemy = enemies.get(i);
                enemy.bewegen(wegPunkte);


                if (enemy.getX() >= SPIEL_BREITE || enemy.getX() <= 0 || enemy.getY() >= SPIEL_HOEHE || enemy.getY() <= 0){

                    leben -= 1;
                    gegnerAufBildschirm -= 1;
                    enemies.remove(i);

                    if (leben <= 0){
                        gameOver();
                    }
                }
            }


            // Schnelle Gegner bewegen
            for (int i = fastEnemies.size() - 1; i >= 0; i--){

                FastEnemy fastEnemy = fastEnemies.get(i);
                fastEnemy.bewegen(wegPunkte);

                if (fastEnemy.getX() >= SPIEL_BREITE || fastEnemy.getX() <= 0 ||
                        fastEnemy.getY() >= SPIEL_HOEHE || fastEnemy.getY() <= 0){

                    leben -= 1;
                    gegnerAufBildschirm -= 1;
                    fastEnemies.remove(i);

                    if (leben <= 0){
                        gameOver();
                    }
                }
            }


            // Starke Gegner bewegen
            for (int i = strongEnemies.size() - 1; i >= 0; i--){

                StrongEnemy strongEnemy = strongEnemies.get(i);
                strongEnemy.bewegen(wegPunkte);


                if (strongEnemy.getX() >= SPIEL_BREITE || strongEnemy.getX() <= 0 ||
                        strongEnemy.getY() >= SPIEL_HOEHE || strongEnemy.getY() <= 0){

                    leben -= 2;
                    gegnerAufBildschirm -= 1;
                    strongEnemies.remove(i);

                    if (leben <= 0){
                        gameOver();
                    }
                }
            }


            // Boss bewegen
            for (int i = bosses.size() - 1; i >= 0; i--){

                Boss boss = bosses.get(i);
                boss.bewegen(wegPunkte);

                if (boss.getX() >= SPIEL_BREITE || boss.getX() <= 0 || boss.getY() >= SPIEL_HOEHE || boss.getY() <= 0){

                    leben -= 10;
                    gegnerAufBildschirm -= 1;
                    bosses.remove(i);

                    if (leben <= 0){
                        gameOver();
                    }
                }
            }


            // Projektile bewegen
            for (Projektiel projektiel : projektiele){
                if (projektiel.getTower().getTowerTyp() == 7){
                    projektiel.moerserProjektielBewegen(moerserZielX, moerserZielY);
                }else if(projektiel.getTower().getTowerTyp() == 8){
                    projektiel.moerserProjektielBewegen(fallenlegerZielX, fallenlegerZielY);
                }else{
                    projektiel.projektielBewegen();
                }
            }


            // Treffer prüfen
            for (int p = projektiele.size() - 1; p >= 0; p--){

                Projektiel projektiel = projektiele.get(p);

                int projektilX = (int) projektiel.getX();
                int projektilY = (int) projektiel.getY();



                if (projektiel.getTower().getTowerTyp() != 7 && projektiel.getTower().getTowerTyp() != 8) {
                    // Normaler Gegner
                    for (int i = enemies.size() - 1; i >= 0; i--) {

                        Enemy enemy = enemies.get(i);

                        int enemyX = enemy.getX();
                        int enemyY = enemy.getY();


                        if (projektilX > enemyX - 10 && projektilX < enemyX + 30 && projektilY > enemyY - 10 && projektilY < enemyY + 30) {

                            if (!projektiel.hatGegnerScchonGetroffen(enemy)) {

                                if (projektiel.getTower().getTowerTyp() == 4){
                                    enemy.slow(2000);
                                }

                                enemy.schadenNehmen(projektiel.getTower().getDemage());

                                Sound.abspielen("sounds/pop.wav", popLautstaerke);

                                if (enemy.getLeben() <= 0) {

                                    enemies.remove(i);
                                    geld += 5;
                                    gegnerAufBildschirm -= 1;

                                    gegnerGetoetet += 1;
                                    totalGeld += 5;
                                }


                                projektiel.getroffen();

                                projektiel.gegnerSpeichern(enemy);
                            }
                            break;
                        }
                    }

                    if (projektiel.getTower() != null && projektiel.getGetroffen() >= projektiel.getTower().getPearcing()) {
                        projektiele.remove(p);
                        continue;
                    }


                    // Schneller Gegner
                    for (int i = fastEnemies.size() - 1; i >= 0; i--) {

                        FastEnemy fastEnemy = fastEnemies.get(i);

                        int enemyX = fastEnemy.getX();
                        int enemyY = fastEnemy.getY();


                        if (projektilX > enemyX - 10 && projektilX < enemyX + 30 && projektilY > enemyY - 10 && projektilY < enemyY + 30) {

                            if (!projektiel.hatGegnerScchonGetroffen(fastEnemy)) {

                                if (projektiel.getTower().getTowerTyp() == 4){
                                    fastEnemy.slow(2000);
                                }

                                fastEnemy.schadenNehmen(projektiel.getTower().getDemage());

                                Sound.abspielen("sounds/pop.wav", popLautstaerke);

                                if (fastEnemy.getLeben() <= 0) {

                                    fastEnemies.remove(i);
                                    geld += 7;
                                    gegnerAufBildschirm -= 1;

                                    gegnerGetoetet += 1;
                                    totalGeld += 7;
                                }

                                projektiel.getroffen();
                                projektiel.gegnerSpeichern(fastEnemy);
                            }
                            break;
                        }
                    }

                    if (projektiel.getTower() != null && projektiel.getGetroffen() >= projektiel.getTower().getPearcing()) {
                        projektiele.remove(p);
                        continue;
                    }


                    // Starker Gegner
                    for (int i = strongEnemies.size() - 1; i >= 0; i--) {

                        StrongEnemy strongEnemy = strongEnemies.get(i);

                        int enemyX = strongEnemy.getX();
                        int enemyY = strongEnemy.getY();

                        if (projektilX > enemyX - 10 && projektilX < enemyX + 30 && projektilY > enemyY - 10 && projektilY < enemyY + 30) {

                            if (!projektiel.hatGegnerScchonGetroffen(strongEnemy)) {

                                if (projektiel.getTower().getTowerTyp() == 4){
                                    strongEnemy.slow(2000);
                                }

                                strongEnemy.schadenNehmen(projektiel.getTower().getDemage());

                                Sound.abspielen("sounds/pop.wav", popLautstaerke);

                                if (strongEnemy.getLeben() <= 0) {

                                    strongEnemies.remove(i);
                                    geld += 10;
                                    gegnerAufBildschirm -= 1;

                                    gegnerGetoetet += 1;
                                    totalGeld += 10;
                                }

                                projektiel.getroffen();
                                projektiel.gegnerSpeichern(strongEnemy);

                            }

                            break;
                        }
                    }

                    if (projektiel.getTower() != null && projektiel.getGetroffen() >= projektiel.getTower().getPearcing()) {
                        projektiele.remove(p);
                        continue;
                    }


                    // Boss
                    for (int i = bosses.size() - 1; i >= 0; i--) {

                        Boss boss = bosses.get(i);

                        int enemyX = boss.getX();
                        int enemyY = boss.getY();

                        if (projektilX > enemyX - 15 && projektilX < enemyX + 35 && projektilY > enemyY - 15 && projektilY < enemyY + 35) {

                            if (!projektiel.hatGegnerScchonGetroffen(boss)) {

                                if (projektiel.getTower().getTowerTyp() == 4){
                                    boss.slow(2000);
                                }

                                boss.schadenNehmen(projektiel.getTower().getDemage());

                                Sound.abspielen("sounds/pop.wav", popLautstaerke);

                                if (boss.getLeben() <= 0) {
                                    bosses.remove(i);
                                    geld += 100 + welle * 2;
                                    gegnerAufBildschirm -= 1;

                                    gegnerGetoetet += 1;
                                    totalGeld += 100 + welle * 2;
                                }

                                projektiel.getroffen();
                                projektiel.gegnerSpeichern(boss);
                            }

                            break;
                        }
                    }

                    if (projektiel.getTower() != null && projektiel.getGetroffen() >= projektiel.getTower().getPearcing()) {
                        projektiele.remove(p);
                        continue;
                    }
                }


                if (projektilX > SPIEL_BREITE || projektilX < 0 ||
                        projektilY > SPIEL_HOEHE || projektilY < 0){
                    projektiele.remove(p);
                }

                if (projektiel.getTower().getTowerTyp() == 7){
                    if (projektiel.getX() == moerserZielX && projektiel.getY() == moerserZielY){
                        //Einfache Gegner
                        for (int i = enemies.size() - 1; i >= 0; i--) {
                            Enemy enemy = enemies.get(i);
                            if (Math.abs(enemy.getX() - moerserZielX) < 60 && Math.abs(enemy.getY() - moerserZielY) < 60){
                                enemies.remove(i);
                                gegnerAufBildschirm -= 1;
                            }
                        }
                        //Schnelle Gegner
                        for (int i = fastEnemies.size() - 1; i >= 0; i--) {
                            FastEnemy fastEnemy = fastEnemies.get(i);
                            if (Math.abs(fastEnemy.getX() - moerserZielX) < 60 && Math.abs(fastEnemy.getY() - moerserZielY) < 60){
                                fastEnemies.remove(i);
                                gegnerAufBildschirm -= 1;
                            }
                        }
                        //Starke Gegner
                        for (int i = strongEnemies.size() - 1; i >= 0; i--) {
                            StrongEnemy strongEnemy = strongEnemies.get(i);
                            if (Math.abs(strongEnemy.getX() - moerserZielX) < 60 && Math.abs(strongEnemy.getY() - moerserZielY) < 60){
                                strongEnemies.remove(i);
                                gegnerAufBildschirm -= 1;
                            }
                        }


                        projektiele.remove(p);
                    }
                }

                if (projektiel.getTower().getTowerTyp() == 8){
                    //Einfacher Gegner
                    for (int i = enemies.size() - 1; i >= 0; i--) {
                        Enemy enemy = enemies.get(i);
                        if (Math.abs(enemy.getX() - fallenlegerZielX) < 30 && Math.abs(enemy.getY() - fallenlegerZielY) < 30){
                            enemies.remove(i);
                            gegnerAufBildschirm -= 1;
                            projektiele.remove(p);
                        }
                    }
                    //Schnelle Gegner
                    for (int i = fastEnemies.size() - 1; i >= 0; i--) {
                        FastEnemy fastEnemy = fastEnemies.get(i);
                        if (Math.abs(fastEnemy.getX() - fallenlegerZielX) < 30 && Math.abs(fastEnemy.getY() - fallenlegerZielY) < 30){
                            fastEnemies.remove(i);
                            gegnerAufBildschirm -= 1;
                            projektiele.remove(p);
                        }
                    }
                    //Starke Gegner
                    for (int i = strongEnemies.size() - 1; i >= 0; i--) {
                        StrongEnemy strongEnemy = strongEnemies.get(i);
                        if (Math.abs(strongEnemy.getX() - fallenlegerZielX) < 30 && Math.abs(strongEnemy.getY() - fallenlegerZielY) < 30){
                            strongEnemies.remove(i);
                            gegnerAufBildschirm -= 1;
                            projektiele.remove(p);
                        }
                    }
                }

            }


            if (gegnerAufBildschirm <= 0 && !bankSchonGezahlt){
                for (Bank bank: banks){
                    geld += bank.getEinkommen();
                }
                bankSchonGezahlt = true;
            }



            repaint();
        });


    }


    public void gameOver(){
        leben = 0;
        geld = 100;
        gameOver = true;
        spawnTimer.stop();
        gameTimer.stop();
        schiessenTimer.stop();
        enemies.clear();
        fastEnemies.clear();
        strongEnemies.clear();
        bosses.clear();
        projektiele.clear();
        plaziertx.clear();
        plazierty.clear();
        spawnQueue.clear();

        towers.clear();
        banks.clear();
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        double scaleX = getWidth() / (double) SPIEL_BREITE;

        double scaleY = getHeight() / (double) SPIEL_HOEHE;


        shootLautstaerkeSlider.setBounds(
                (int)(500 * scaleX),
                (int)(220 * scaleY),
                (int)(200 * scaleX),
                (int)(50 * scaleY)
        );

        popLautstaerkeSlider.setBounds(
                (int)(500 * scaleX),
                (int)(290 * scaleY),
                (int)(200 * scaleX),
                (int)(50 * scaleY)
        );


        Graphics2D g2 = (Graphics2D) g.create();

        g2.scale(scaleX, scaleY);


        // Map zeichnen
        Color matschBraun = new Color(205, 133, 63);

        if (map == 1 || map == 5) {
            setBackground(Color.GREEN);
        }else if (map == 2 || map == 7){
            setBackground(Color.YELLOW);
        }else if ( map == 3 || map == 8){
            setBackground(Color.lightGray);
        }else if (map == 6 || map == 4){
            setBackground(matschBraun);
        }

        Path2D.Double weg = new Path2D.Double();
        if (map != 4) {

            Point start = wegPunkte.get(0);

            weg.moveTo(start.x, start.y);

            for (int i = 1; i < wegPunkte.size(); i++) {

                Point punkt = wegPunkte.get(i);

                weg.lineTo(punkt.x, punkt.y);
            }
        }

        if (map == 4){

            Point start = wegPunkte.get(0);
            weg.moveTo(start.x, start.y);

            for (int i = 1; i < wegPunkte.size() - 1; i++) {

                Point kontrollPunkt = wegPunkte.get(i);
                Point naechsterPunkt = wegPunkte.get(i + 1);

                double mittelX =
                        (kontrollPunkt.x + naechsterPunkt.x) / 2.0;

                double mittelY =
                        (kontrollPunkt.y + naechsterPunkt.y) / 2.0;

                weg.quadTo(
                        kontrollPunkt.x,
                        kontrollPunkt.y,
                        mittelX,
                        mittelY
                );
            }

            Point letzterPunkt =
                    wegPunkte.get(wegPunkte.size() - 1);

            weg.lineTo(
                    letzterPunkt.x,
                    letzterPunkt.y
            );

            g2.setStroke(new BasicStroke(
                    100,
                    BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND
            ));

            g2.draw(weg);
        }

        g2.setColor(Color.GRAY);

        g2.setStroke(new BasicStroke(
                100,
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND
        ));

        g2.draw(weg);

        g2.setStroke(new BasicStroke(1));


        // Anzeige
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 100, 70);

        g2.setColor(Color.RED);
        g2.drawString(leben + " ❤️", 0, 20);
        g2.setColor(Color.ORANGE);
        g2.drawString(geld + " 🪙", 0, 40);
        g2.setColor(Color.BLACK);
        g2.drawString(welle + " Welle", 0, 60);


        // Gegner zeichnen
        for (Enemy enemy : enemies) {

            enemy.normalEnemySpawn(g2);

        }


        for (FastEnemy fastEnemy : fastEnemies) {

            fastEnemy.fastEnemySpawn(g2);
        }


        for (StrongEnemy strongEnemy : strongEnemies) {

            strongEnemy.strongEnenmySpawn(g2);
        }


        for (Boss boss : bosses) {

            boss.bossSpawn(g2);
        }


        // Tower zeichnen
        for (Tower tower : towers) {

            tower.towerzeichnen(g2);
        }


        // Banken zeichnen
        for (Bank bank : banks) {
            bank.bankZeichnen(g2);
        }


        // Projektile zeichnen
        for (Projektiel projektiel : projektiele) {

            projektiel.projektielZeichnen(g2);
        }


        // Doppelgeschwindigkeit
        g2.setColor(Color.BLACK);
        g2.drawRect(930, 630, 60, 60);
        int[] x1 = {935, 935, 960};
        int[] y1 = {640, 680, 660};

        int[] x2 = {955, 955, 980};
        int[] y2 = {640, 680, 660};

        g2.fillPolygon(x1, y1, 3);
        if (doppelgeschwindigkeit) {
            g2.fillPolygon(x2, y2, 3);
        }


        // Einstellungen im spiel
        g2.drawRect(930, 10, 60, 60);
        g2.setFont(new Font("Arial", Font.PLAIN, 80));
        g2.setColor(Color.GRAY);
        g2.drawString("⚙️", 935, 62);
        g2.setColor(Color.BLACK);

        if (einstellungenOffen) {
            g2.setColor(Color.ORANGE);
            g2.fillRect(200, 200, 600, 300);
            g2.setColor(Color.RED);
            g2.setFont(new Font("Arial", Font.PLAIN, 45));
            g2.drawRect(740, 210, 50, 50);
            g2.drawString("X", 750, 250);

            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.drawString("Total money: " + totalGeld, 230, 250);
            g2.drawString("Total Pops: " + gegnerGetoetet, 230, 280);


            g2.drawRect(410, 450, 180, 30);
            g2.drawString("Spiel Verlassen", 430, 472);

            g2.drawString("Shoot volume", 500, 240);
            g2.drawString("Pop Value", 500, 310);

            g2.setFont(new Font("Arial", Font.PLAIN, 10));
            g2.drawString("0%", 490, 258);
            g2.drawString("100%", 690, 258);
            shootLautstaerkeSlider.setVisible(true);

            g2.drawString("0%", 490, 328);
            g2.drawString("100%", 690, 328);
            popLautstaerkeSlider.setVisible(true);
        }

        if (!einstellungenOffen) {
            shootLautstaerkeSlider.setVisible(false);
            popLautstaerkeSlider.setVisible(false);
        }

        // Tower Upgrade Menue
        if (upgradeMenueOffen && ausgewaehlterTower != null) {

            upgradePanel.towerUpgrademenue(g2, ausgewaehlterTower);

            if (ausgewaehlterTower.getTowerTyp() == 7){
                g2.drawOval(moerserZielX -15, moerserZielY-15, 30, 30);
            }
            if (ausgewaehlterTower.getTowerTyp() == 8){
                g2.drawOval(fallenlegerZielX -15, fallenlegerZielY -15, 30, 30);
            }
        }

        // Bank Upgrade Menue
        if (upgradeMenueOffen && ausgewaehlteBank != null) {

            upgradePanel.bankUpgradeMenue(g2, ausgewaehlteBank);
        }


        //  Turmmenue anzeigen
        if (!turmMenueOffen) {

            g2.setFont(new Font("Arial", Font.PLAIN, 50));
            g2.setColor(Color.BLACK);

            g2.drawRect(10, 640, 50, 50);
            g2.drawString("➡", 15, 682);

        } else {

            g2.setFont(new Font("Arial", Font.PLAIN, 40));
            g2.setColor(Color.BLACK);

            g2.drawRect(10, 640, 50, 50);
            g2.drawString("⬅", 15, 682);

            // Rahmen des Menüs
            g2.drawRect(70, 620, 400, 70);

            int startX = 80 - turmScrollOffset;

            Color hellGrau = new Color(230, 230, 230);

            g2.setFont(new Font("Arial", Font.PLAIN, 10));



            Shape alterClip = g2.getClip();

            g2.setClip(70, 620, 400, 70);


            for (int i = 0; i < ANZAHL_TUERME; i++) {

                int typNummer = i + 1;
                int x = startX + i * TURM_BREITE;

                // Nur zeichnen, wenn der Turm innerhalb des Menüs liegt
                if (x + 60 < 70 || x > 420) {
                    continue;
                }

                // ausgewählter Turm
                if (typ == typNummer) {
                    g2.setColor(hellGrau);
                    g2.fillRect(x, 620, 60, 70);
                }

                switch (typNummer) {

                    //Normaler Turm
                    case 1 -> {
                        g2.setColor(Color.GRAY);
                        g2.fillOval(x + 10, 630, 40, 40);

                        g2.setColor(Color.BLACK);
                        g2.drawString("50", x + 22, 685);
                    }
                    //Sniper
                    case 2 -> {
                        g2.setColor(new Color(0, 100, 0));
                        g2.fillOval(x + 10, 630, 40, 40);

                        g2.setColor(Color.BLACK);
                        g2.drawString("150", x + 18, 685);
                    }
                    //Shotgun
                    case 3 -> {
                        g2.setColor(Color.RED);
                        g2.fillOval(x + 10, 630, 40, 40);

                        g2.setColor(Color.BLACK);
                        g2.drawString("170", x + 18, 685);
                    }
                    //Frost
                    case 4 -> {
                        g2.setColor(new Color(200, 233, 245));
                        g2.fillOval(x + 10, 630, 40, 40);

                        g2.setColor(Color.BLACK);
                        g2.drawString("90", x + 18, 685);
                    }
                    //Detektor
                    case 5 -> {
                        g2.setColor(new Color(100,104,60));
                        g2.fillOval(x + 10, 630, 40, 40);

                        g2.setColor(Color.BLACK);
                        g2.drawString("125", x + 22, 685);
                    }
                    //Bomber
                    case 6 -> {
                        g2.setColor(new Color(74, 70, 70));
                        g2.fillOval(x + 10, 630, 40, 40);

                        g2.setColor(Color.BLACK);
                        g2.drawString("255", x + 18, 685);
                    }
                    //Mörser
                    case 7 -> {
                        g2.setColor(new Color(213, 29, 125));
                        g2.fillOval(x + 10, 630, 40, 40);

                        g2.setColor(Color.BLACK);
                        g2.drawString("390", x + 18, 685);
                    }
                    //Fallenleger
                    case 8 -> {
                        g2.setColor(new Color(245, 187, 13));
                        g2.fillOval(x + 10, 630, 40, 40);

                        g2.setColor(Color.BLACK);
                        g2.drawString("190", x + 18, 685);
                    }
                    //Dorf
                    case 9 -> {
                        g2.setColor(new Color(153, 153, 153));
                        g2.fillOval(x + 10, 630, 40, 40);

                        g2.setColor(Color.BLACK);
                        g2.drawString("505", x + 18, 685);
                    }
                    case 10 -> {
                        g2.setColor(new Color(139, 69, 19));
                        g2.fillRect(x+10, 630, 40, 40);

                        g2.setColor(Color.BLACK);
                        g2.drawString("190", x + 18, 685);
                    }
                }
                g2.setClip(alterClip);
            }
        }


        // Game Over
        if (gameOver && !spielMunueOffen) {

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.PLAIN, 50));
            g2.drawString("Game Over", 375, 350);

            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.drawRect(415, 400, 160, 50);
            g2.drawString("Back to Menue", 425, 433);

        }

        if (spielMunueOffen) {
            inGameMenuePanel.menueGameOver(g2);
        }


        g2.dispose();
    }



    @Override
    public void mousePressed(MouseEvent e) {

        double scaleX = getWidth() / (double) SPIEL_BREITE;

        double scaleY = getHeight() / (double) SPIEL_HOEHE;


        int mousex = (int) (e.getX() / scaleX);

        int mousey = (int) (e.getY() / scaleY);


        if (gameOver){

            if (mousex > 415 && mousex < 575 && mousey > 400 && mousey < 450){
                spielMunueOffen = true;
                repaint();
            }

            if (spielMunueOffen && mousex > 555 && mousex < 625 && mousey > 350 && mousey < 410){
                gameOver = false;
                spielMunueOffen = false;

                // stats reseten
                leben = 20;
                geld = 100;
                welle = 1;
                gegnerAufBildschirm = 0;

                // Game Loop starten
                towers.clear();
                spawnTimer.start();
                gameTimer.start();
                schiessenTimer.start();


                repaint();
            }

            if (spielMunueOffen && mousex > 390 && mousex < 460 && mousey > 350 && mousey < 410){
                cardLayout.show(hauptPanel ,"menu");
            }
        }

        //Spielgeschwindigkeit erhöhen
        if (mousex > 930 && mousex < 980 && mousey > 630 && mousey < 680){

            if (spielgeschwindigkeit == 1){
                spielgeschwindigkeit = 2;
                doppelgeschwindigkeit = true;


            }else if(spielgeschwindigkeit == 2){
                spielgeschwindigkeit = 1;
                doppelgeschwindigkeit = false;
            }


            shootDelay = 500 / spielgeschwindigkeit;

            mainDelay = 16 / spielgeschwindigkeit;


        }

        // Turmmenue öffnen
        if (mousex > 10 && mousex < 60 && mousey > 640 && mousey < 690){
            turmMenueOffen = !turmMenueOffen;

        }


        // Turm auswählen
        if (turmMenueOffen) {

            int startX = 80 - turmScrollOffset;

            for (int i = 0; i < ANZAHL_TUERME; i++) {

                int x = startX + i * TURM_BREITE;

                if (mousex >= x &&
                        mousex <= x + 60 &&
                        mousey >= 620 &&
                        mousey <= 690) {

                    typ = i + 1;
                    break;
                }
            }
        }


        // Einstellungen im Spiel öffnen
        if (mousex > 930 && mousex < 980 && mousey < 70 && mousey > 10 && !upgradeMenueOffen){
            if (!einstellungenOffen) {
                einstellungenOffen = true;
            }
        }


        // Einstellungsmenue ist offen
        if (einstellungenOffen){

            gameTimer.stop();
            spawnTimer.stop();
            schiessenTimer.stop();

            if (mousex > 740 && mousex < 790 && mousey > 210 && mousey < 260){
                einstellungenOffen = false;

                gameTimer.start();
                spawnTimer.start();
                schiessenTimer.start();

                return;

            }

            if (mousex > 410 && mousex < 590 && mousey > 450 && mousey < 480){

                // Den spielstand reseten
                leben = 20;
                geld = 100;

                gegnerAufBildschirm = 0;

                einstellungenOffen = false;

                enemies.clear();
                fastEnemies.clear();
                strongEnemies.clear();
                bosses.clear();
                projektiele.clear();
                plaziertx.clear();
                plazierty.clear();

                towers.clear();
                banks.clear();

                welle = 0;


                cardLayout.show(hauptPanel ,"menu");

            }
            repaint();
            return;
        }


        // Tower Upgrade Menue ist offen
        if (upgradeMenueOffen && ausgewaehlterTower != null){


            // Menue schliessen
            if (mousex >= 950 && mousex <= 1000 && mousey >= 0 && mousey <= 50){

                upgradeMenueOffen = false;

                ausgewaehlterTower = null;

                repaint();

                return;
            }


            // Tower verkaufen
            if (mousex >= 725 && mousex <= 885 && mousey >= 600 && mousey <= 660){
                geld += ausgewaehlterTower.getRueckerstattung();

                positionFreigeben(ausgewaehlterTower.getMousex(), ausgewaehlterTower.getMousey());

                towers.remove(ausgewaehlterTower);

                upgradeMenueOffen = false;

                ausgewaehlterTower = null;

                repaint();

                return;


            }


            // Reach Upgrade
            if (mousex > 725 && mousex < 885 && mousey > 65 && mousey < 135){

                int kosten = ausgewaehlterTower.getReachKosten();


                if (ausgewaehlterTower.getReachUpgradesGekauft() < 3 && geld >= kosten){

                    geld -= kosten;

                    ausgewaehlterTower.reachUpgradeKaufen();

                    ausgewaehlterTower.rueckerstattung(kosten);

                    repaint();
                }


                return;
            }


            // Demage Upgrade
            if (mousex > 725 && mousex < 885 && mousey > 165 && mousey < 235){

                int kosten = ausgewaehlterTower.getDemageKosten();


                if (ausgewaehlterTower.getDemageUpgradesGekauft() < 3 && geld >= kosten){

                    geld -= kosten;

                    ausgewaehlterTower.demageUpgradeKaufen();
                    ausgewaehlterTower.rueckerstattung(kosten);

                    repaint();
                }


                return;
            }


            // Pearcing Upgrade
            if (mousex > 725 && mousex < 885 && mousey > 265 && mousey < 335){

                int kosten = ausgewaehlterTower.getPearcingKosten();


                if (ausgewaehlterTower.getPearcingUpgradesGekauft() < 2 && geld >= kosten){

                    geld -= kosten;

                    ausgewaehlterTower.pearcingUpgradeKaufen();
                    ausgewaehlterTower.rueckerstattung(kosten);

                    repaint();
                }


                return;
            }

            //Ziel setzten (Mörser)
            if (ausgewaehlterTower.getTowerTyp() == 7){
                if (Math.abs(mousex - ausgewaehlterTower.getMousex()) < ausgewaehlterTower.getReichweite() &&
                        Math.abs(mousey - ausgewaehlterTower.getMousey()) < ausgewaehlterTower.getReichweite()){
                    moerserZielX = mousex;
                    moerserZielY = mousey;
                }
            }

            //Ziel setzten (Fallenlegr)
            if(ausgewaehlterTower.getTowerTyp() == 8){
                if (Math.abs(mousex - ausgewaehlterTower.getMousex()) < ausgewaehlterTower.getReichweite() &&
                        Math.abs(mousey - ausgewaehlterTower.getMousey()) < ausgewaehlterTower.getReichweite()) {
                    fallenlegerZielX = mousex;
                    fallenlegerZielY = mousey;
                }
            }


            // Solange das Menue offen ist,
            // kann kein Tower dahinter gesetzt werden
            return;
        }

        // Bank Upgrade Menue ist offen
        if (upgradeMenueOffen && ausgewaehlteBank != null){

            // Schliessen
            if (mousex >= 950 && mousex <= 1000 && mousey >= 0 && mousey <= 50){

                upgradeMenueOffen = false;

                ausgewaehlteBank = null;

                repaint();

                return;
            }

            // Verkaufen
            if (mousex >= 725 && mousex <= 885 && mousey >= 600 && mousey <= 660){
                geld += ausgewaehlteBank.getRueckerstattung();

                positionFreigeben(ausgewaehlteBank.getX(), ausgewaehlteBank.getY());

                banks.remove(ausgewaehlteBank);

                upgradeMenueOffen = false;

                ausgewaehlteBank = null;

                repaint();

                return;


            }

            // Upgraden
            if (mousex > 725 && mousex < 885 && mousey > 65 && mousey < 135){

                int kosten = ausgewaehlteBank.getMoreMoneyUpgradeKosten();


                if (ausgewaehlteBank.getMoreMoneyUpgrades() < 3 && geld >= kosten){

                    geld -= kosten;

                    ausgewaehlteBank.moreMoneyUpgradeGekauft();

                    ausgewaehlteBank.rueckerstattung(kosten);

                    repaint();
                }


                return;
            }

            return;
        }


        // Vorhandenen Tower anklicken
        for (Tower tower : towers){

            if (!einstellungenOffen && !turmMenueOffen){
                if (tower.aufTowerGeklickt(mousex, mousey)) {

                    ausgewaehlterTower = tower;
                    ausgewaehlteBank = null;

                    upgradeMenueOffen = true;

                    repaint();

                    return;
                }
            }
        }

        // Vorhandene Banken anklicken
        for (Bank bank: banks){
            if (!einstellungenOffen && !turmMenueOffen){
                if (bank.aufBankGeklickt(mousex, mousey)) {

                    ausgewaehlteBank = bank;
                    ausgewaehlterTower = null;

                    upgradeMenueOffen = true;

                    repaint();

                    return;
                }
            }
        }


        // Neuen Tower platzieren
        Bank bank = new Bank();
        Tower tower = new Tower();
        if (geld >= tower.getKosten(typ)){

            if (!einstellungenOffen && !turmMenueOffen && !spielMunueOffen){
                if (mousex < 930 || mousex > 1000 || mousey < 630 || mousey > 700){
                    if (mousex < 0 || mousex > 60 || mousey < 640 || mousey > 700) {
                        if (!istAufWeg(mousex, mousey)){
                            if (tower.istPlatzFrei(mousex, mousey, plaziertx, plazierty) &&
                                    bank.istPlatzFrei(mousex, mousey, plaziertx, plazierty)) {

                                if (typ <= 9 && typ != 7 && typ != 8) {

                                    tower.newTower(mousex, mousey, typ);
                                    geld -= tower.getKosten(typ);

                                    plaziertx.add(mousex);

                                    plazierty.add(mousey);

                                    towers.add(tower);

                                    repaint();
                                }

                                //Mörser
                                if (typ == 7){
                                    moerserZielX = mousex - tower.getReichweite() / 2;
                                    moerserZielY = mousey;
                                    tower.newTower(mousex, mousey, typ);
                                    geld -= tower.getKosten(typ);

                                    plaziertx.add(mousex);

                                    plazierty.add(mousey);

                                    towers.add(tower);

                                    repaint();
                                }
                                //Fallenleger
                                if (typ == 8){
                                    fallenlegerZielX = mousex - tower.getReichweite() / 2;
                                    fallenlegerZielY = mousey;
                                    tower.newTower(mousex, mousey, typ);
                                    geld -= tower.getKosten(typ);

                                    plaziertx.add(mousex);

                                    plazierty.add(mousey);

                                    towers.add(tower);

                                    repaint();
                                }
                            }
                        }
                    }
                }
            }
        }


        // Neu Bank plazieren
        if (geld >= bank.getKosten()) {

            if (!einstellungenOffen && !turmMenueOffen && !spielMunueOffen) {
                if (mousex < 930 || mousex > 1000 || mousey < 630 || mousey > 700) {
                    if (mousex < 0 || mousex > 60 || mousey < 640 || mousey > 700) {
                        if (mousey + 30 <= WEG_Y || mousey - 30 >= WEG_Y + 100) {

                            if (!istAufWeg(mousex, mousey)) {
                                if (tower.istPlatzFrei(mousex, mousey, plaziertx, plazierty) &&
                                        bank.istPlatzFrei(mousex, mousey, plaziertx, plazierty)) {

                                    if (typ == 10) {
                                        bank.newBank(mousex, mousey);

                                        geld -= bank.getKosten();

                                        plaziertx.add(mousex);

                                        plazierty.add(mousey);

                                        banks.add(bank);

                                        repaint();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }


    @Override
    public void mouseClicked(MouseEvent e){

    }


    @Override
    public void mouseReleased(MouseEvent e){

    }


    @Override
    public void mouseEntered(MouseEvent e){

    }


    @Override
    public void mouseExited(MouseEvent e){

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        if (!turmMenueOffen) {
            return;
        }

        int rotation = e.getWheelRotation();

        turmScrollOffset += rotation * 40;

        int menuBreite = 400;

        int maxScroll = Math.max(
                0,
                ANZAHL_TUERME * TURM_BREITE - menuBreite
        );

        if (turmScrollOffset < 0) {
            turmScrollOffset = 0;
        }

        if (turmScrollOffset > maxScroll) {
            turmScrollOffset = maxScroll;
        }

        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}