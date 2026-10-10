package ch.alexb.tower.ui.panles;

import ch.alexb.tower.sounds.Sound;
import ch.alexb.tower.ui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements MouseListener {

    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<FastEnemy> fastEnemies = new ArrayList<>();
    ArrayList<StrongEnemy> strongEnemies = new ArrayList<>();
    ArrayList<Boss> bosses = new ArrayList<>();
    ArrayList<Tower> towers = new ArrayList<>();
    ArrayList<Bank> banks = new ArrayList<>();
    ArrayList<Projektiel> projektiele = new ArrayList<>();

    ArrayList<Integer> plaziertx = new ArrayList<>();
    ArrayList<Integer> plazierty = new ArrayList<>();

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
    int geld = 100;


    boolean gameOver = false;
    boolean spielMunueOffen = false;
    boolean turmMenueOffen = false;

    boolean doppelgeschwindigkeit = false;

    boolean bankSchonGezahlt = false;

    private static final int WEG_Y = 330;
    private static final int SPIEL_BREITE = 1000;
    private static final int SPIEL_HOEHE = 700;

    int welle = 0;
    int gegnerGetoetet = 0;
    int totalGeld = 100;
    int map;

    int kostenNormal = 2;
    int kostenSchnell = 3;
    int kostenStark = 5;

    double budget = 0;

    int gegnerAufBildschirm = 0;

    double reichweite;

    int zahl;

    int typ = 1;

    int weitesterGegneraufStrecke = 0;

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

    public void gameStarten(int ausgewaehlteMap){

        map = ausgewaehlteMap;

        spawnTimer.start();
        schiessenTimer.start();
        gameTimer.start();
    }


    public GamePanel(CardLayout cardLayout, JPanel hauptPanel) {

        this.cardLayout = cardLayout;
        this.hauptPanel = hauptPanel;

        addMouseListener(this);

        setLayout(null);
        shootLautstaerkeSlider.setVisible(false);

        shootLautstaerkeSlider.setBounds(500, 220, 200, 50);

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

        popLautstaerkeSlider.setBounds(500, 290, 200, 50);

        add(popLautstaerkeSlider);

        popLautstaerkeSlider.addChangeListener(e -> {

            int wert = popLautstaerkeSlider.getValue();

            popLautstaerke = -40.0f + wert * 0.4f;

            if (wert == 0){
                popLautstaerke = -50.0f;
            }

        });



        setBackground(Color.GREEN);

        addMouseListener(this);


        // Gegner spawnen
        spawnTimer = new Timer(spawnDelay, e -> {

            // Schnellere spawnrate nach jeder Runde
            spawnDelay -= welle * 3;
            if (welle > 100){
                minSpawnDelay -= (welle -100) * 3;
            }

            int delay = random.nextInt(minSpawnDelay, spawnDelay);

            spawnTimer.setDelay(delay);

            if (gegnerAufBildschirm == 0 && budget < 2){
                welle += 1;
                budget = welle * 10 * (1 + (double) welle / 10) * welle / 2;
            }

            if (welle % 10 != 0  && welle < 5){
                zahl = 1;
            }

            if ( welle % 10 != 0 && welle >= 5 && welle < 15 || welle == 10){

                zahl = random.nextInt(1, 3);
            }

            if (welle % 10 != 0 && welle >= 15){
                zahl = random.nextInt(1, 4);
            }


            // Boss jede 10. Welle
            if (welle % 10 == 0 && budget > 0 && welle != 10){

                Boss boss = new Boss();

                boss.leben(welle * 2);

                budget = 0;
                gegnerAufBildschirm += 1;
                bosses.add(boss);
                return;
            }


            switch (zahl){
                case 1 -> {
                    if (budget >= kostenNormal){

                        budget -= kostenNormal;
                        gegnerAufBildschirm += 1;

                        Enemy enemy = new Enemy();
                        enemy.setLeben(welle);

                        enemies.add(enemy);
                    }
                }


                case 2 -> {
                    if (budget >= kostenSchnell){

                        budget -= kostenSchnell;
                        gegnerAufBildschirm += 1;

                        FastEnemy fastenemy = new FastEnemy();

                        fastenemy.setLeben(welle);
                        fastEnemies.add(fastenemy);
                    }
                }


                case 3 -> {

                    if (budget >= kostenStark){

                        budget -= kostenStark;
                        gegnerAufBildschirm += 1;

                        StrongEnemy strongEnemy = new StrongEnemy();

                        strongEnemy.setLeben(welle);
                        strongEnemies.add(strongEnemy);
                    }
                }
            }

            bankSchonGezahlt = false;
        });


        // Tower schießen
        schiessenTimer = new Timer(shootDelay, e -> {

            schiessenTimer.setDelay(shootDelay);

            for (Tower tower : towers){
                int towerX = tower.getMousex();
                int towerY = tower.getMousey();


                // Normale Gegner
                for (Enemy enemy : enemies) {

                    int enemyX = enemy.getX();
                    int enemyY = WEG_Y + 35;

                    if (enemyX > weitesterGegneraufStrecke && tower.istInReichweite(towerX, towerY, enemyX, enemyY)) {
                        weitesterGegneraufStrecke = enemyX;
                    }

                }


                // Schnelle Gegner
                for (FastEnemy fastEnemy : fastEnemies) {
                    int enemyX = fastEnemy.getX();
                    int enemyY = WEG_Y + 35;

                    if (enemyX > weitesterGegneraufStrecke && tower.istInReichweite(towerX, towerY, enemyX, enemyY)) {
                        weitesterGegneraufStrecke = enemyX;
                    }
                }


                // Starke Gegner
                for (StrongEnemy strongEnemy : strongEnemies) {
                    int enemyX = strongEnemy.getX();
                    int enemyY = WEG_Y + 35;

                    if (enemyX > weitesterGegneraufStrecke && tower.istInReichweite(towerX, towerY, enemyX, enemyY)) {
                        weitesterGegneraufStrecke = enemyX;
                    }
                }


                // Boss
                for (Boss boss : bosses) {
                    int enemyX = boss.getX();
                    int enemyY = WEG_Y + 35;

                    if (enemyX > weitesterGegneraufStrecke && tower.istInReichweite(towerX, towerY, enemyX, enemyY)) {
                        weitesterGegneraufStrecke = enemyX;
                    }
                }

                int enemyY = WEG_Y + 35;

                boolean schiessen = tower.istInReichweite(towerX, towerY,weitesterGegneraufStrecke, enemyY);


                if (schiessen && !tower.hatGeschossen()){

                    reichweite = tower.Reichweite(towerX, towerY, weitesterGegneraufStrecke, enemyY);
                    tower.schiessen(true);
                    Projektiel projektiel = new Projektiel(towerX, towerY, weitesterGegneraufStrecke, enemyY, 1.5, reichweite, tower);
                    projektiele.add(projektiel);

                    Sound.abspielen("sounds/shoot.wav", shootLautstaerke);

                }


                weitesterGegneraufStrecke = 0;
                tower.schiessen(false);
            }
        });


        // Hauptspiel
        gameTimer = new Timer(mainDelay, e -> {

            gameTimer.setDelay(mainDelay);

            // Normale Gegner bewegen
            for (int i = enemies.size() - 1; i >= 0; i--){

                Enemy enemy = enemies.get(i);
                enemy.bewegen();


                if (enemy.getX() > SPIEL_BREITE){

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
                fastEnemy.bewegen();

                if (fastEnemy.getX() > SPIEL_BREITE){

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
                strongEnemy.bewegen();


                if (strongEnemy.getX() > SPIEL_BREITE){

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
                boss.bewegen();

                if (boss.getX() > SPIEL_BREITE){

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
                projektiel.projektielBewegen();
            }


            // Treffer prüfen
            for (int p = projektiele.size() - 1; p >= 0; p--){

                Projektiel projektiel = projektiele.get(p);

                int projektilX = (int) projektiel.getX();
                int projektilY = (int) projektiel.getY();

                int getroffen = 0;


                // Normaler Gegner
                for (int i = enemies.size() - 1; i >= 0; i--){

                    Enemy enemy = enemies.get(i);

                    int enemyX = enemy.getX();
                    int enemyY = WEG_Y + 35;


                    if (projektilX > enemyX - 10 && projektilX < enemyX + 30 && projektilY > enemyY - 10 && projektilY < enemyY + 30){

                        if (!projektiel.hatGegnerScchonGetroffen(enemy)) {

                            enemy.schadenNehmen(1);

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

                if (projektiel.getTower() != null && projektiel.getGetroffen() >= projektiel.getTower().getPearcing()){
                    projektiele.remove(p);
                    continue;
                }


                // Schneller Gegner
                for (int i = fastEnemies.size() - 1; i >= 0; i--){

                    FastEnemy fastEnemy = fastEnemies.get(i);

                    int enemyX = fastEnemy.getX();
                    int enemyY = WEG_Y + 35;


                    if (projektilX > enemyX - 10 && projektilX < enemyX + 30 && projektilY > enemyY - 10 && projektilY < enemyY + 30){

                        if (!projektiel.hatGegnerScchonGetroffen(fastEnemy)) {

                            fastEnemy.schadenNehmen(1);

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

                if (projektiel.getTower() != null && projektiel.getGetroffen() >= projektiel.getTower().getPearcing()){
                    projektiele.remove(p);
                    continue;
                }


                // Starker Gegner
                for (int i = strongEnemies.size() - 1; i >= 0; i--){

                    StrongEnemy strongEnemy = strongEnemies.get(i);

                    int enemyX = strongEnemy.getX();
                    int enemyY = WEG_Y + 35;

                    if (projektilX > enemyX - 10 && projektilX < enemyX + 30 && projektilY > enemyY - 10 && projektilY < enemyY + 30){

                        if (!projektiel.hatGegnerScchonGetroffen(strongEnemy)) {

                            strongEnemy.schadenNehmen(1);

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

                if ( projektiel.getTower() != null && projektiel.getGetroffen() >= projektiel.getTower().getPearcing() ){
                    projektiele.remove(p);
                    continue;
                }


                // Boss
                for (int i = bosses.size() - 1; i >= 0; i--){

                    Boss boss = bosses.get(i);

                    int enemyX = boss.getX();
                    int enemyY = WEG_Y + 35;

                    if (projektilX > enemyX - 15 && projektilX < enemyX + 35 && projektilY > enemyY - 15 && projektilY < enemyY + 35){

                        if (!projektiel.hatGegnerScchonGetroffen(boss)) {

                            boss.schadenNehmen(1);

                            Sound.abspielen("sounds/pop.wav", popLautstaerke);

                            if (boss.getLeben() <= 0) {
                                bosses.remove(i);
                                geld += 100;
                                gegnerAufBildschirm -= 1;

                                gegnerGetoetet += 1;
                                totalGeld += 100;
                            }

                            projektiel.getroffen();
                            projektiel.gegnerSpeichern(boss);
                        }

                        break;
                    }
                }

                if (projektiel.getTower() != null && projektiel.getGetroffen() >= projektiel.getTower().getPearcing()){
                    projektiele.remove(p);
                    continue;
                }


                if (projektilX > SPIEL_BREITE){
                    projektiele.remove(p);
                }
                if (projektilY > SPIEL_HOEHE){
                    projektiele.remove(p);
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

        towers.clear();
        banks.clear();
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);


        double scaleX = getWidth() / (double) SPIEL_BREITE;

        double scaleY = getHeight() / (double) SPIEL_HOEHE;


        Graphics2D g2 = (Graphics2D) g.create();

        g2.scale(scaleX, scaleY);


        int wegY = WEG_Y;

        int wegHoehe = 100;


        // Weg
        g2.setColor(Color.GRAY);
        g2.fillRect(0, wegY, SPIEL_BREITE, wegHoehe);


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
        for (Enemy enemy : enemies){

            enemy.normalEnemySpawn(g2, wegY);
        }


        for (FastEnemy fastEnemy : fastEnemies){

            fastEnemy.fastEnemySpawn(g2, wegY);
        }


        for (StrongEnemy strongEnemy : strongEnemies){

            strongEnemy.strongEnenmySpawn(g2, wegY);
        }


        for (Boss boss : bosses){

            boss.bossSpawn(g2, wegY - 10);
        }


        // Tower zeichnen
        for (Tower tower : towers){

            tower.towerzeichnen(g2);
        }


        // Banken zeichnen
        for (Bank bank: banks){
            bank.bankZeichnen(g2);
        }


        // Projektile zeichnen
        for (Projektiel projektiel : projektiele){

            projektiel.projektielZeichnen(g2);
        }


        // Doppelgeschwindigkeit
        g2.setColor(Color.BLACK);
        g2.drawRect(930, 630, 60, 60);
        int [] x1 = {935, 935, 960};
        int [] y1 = {640, 680, 660};

        int [] x2 = {955, 955, 980};
        int [] y2 = {640, 680, 660};

        g2.fillPolygon(x1, y1, 3);
        if (doppelgeschwindigkeit){
            g2.fillPolygon(x2, y2, 3);
        }


        // Einstellungen im spiel
        g2.drawRect(930, 10, 60, 60);
        g2.setFont(new Font("Arial", Font.PLAIN, 80));
        g2.drawString("⚙️", 935, 62);

        if (einstellungenOffen){
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


            g2.drawString("Shoot volume" , 500, 240);
            g2.drawString("Pop Value", 500, 310);

            g2.setFont(new Font("Arial", Font.PLAIN, 10));
            g2.drawString("0%", 490, 258);
            g2.drawString("100%", 690, 258);
            shootLautstaerkeSlider.setVisible(true);

            g2.drawString("0%", 490, 328);
            g2.drawString("100%", 690, 328);
            popLautstaerkeSlider.setVisible(true);
        }

        if (!einstellungenOffen){
            shootLautstaerkeSlider.setVisible(false);
            popLautstaerkeSlider.setVisible(false);
        }

        // Tower Upgrade Menue
        if (upgradeMenueOffen && ausgewaehlterTower != null){

            upgradePanel.towerUpgrademenue(g2, ausgewaehlterTower);
        }

        // Bank Upgrade Menue
        if (upgradeMenueOffen && ausgewaehlteBank != null){

            upgradePanel.bankUpgradeMenue(g2, ausgewaehlteBank);
        }


        //  Turmmenue anzeigen
        if (!turmMenueOffen) {
            g2.setFont(new Font("Arial", Font.PLAIN, 50));
            g2.setColor(Color.BLACK);
            g2.drawRect(10, 640, 50, 50);
            g2.drawString("➡", 15, 682);
        }else {
            g2.setFont(new Font("Arial", Font.PLAIN, 40));
            g2.setColor(Color.BLACK);
            g2.drawRect(10, 640, 50, 50);
            g2.drawString("⬅", 15, 682);

            g2.setFont(new Font("Arial", Font.PLAIN, 10));
            g2.drawRect(70, 620, 400, 70);

            // Kosten
            g2.drawString("50", 102, 685);
            g2.drawString("100", 160, 685);
            g2.drawString("100", 220, 685);
            g2.drawString("200", 280, 685);

            Color hellGrau= new Color(230, 230, 230);
            // Einfacher Turm
            if (typ == 1){
                g2.setColor(hellGrau);
                g2.fillRect(80, 620, 60, 70);
            }
            g2.setColor(Color.GRAY);
            g2.fillOval(90, 630, 40, 40);

            // Sniper
            if (typ == 2){
                g2.setColor(hellGrau);
                g2.fillRect(140, 620, 60, 70);
            }
            Color darkGreen= new Color(0, 100, 0);
            g2.setColor(darkGreen);
            g2.fillOval(150, 630, 40, 40);

            // Shotgun
            if (typ == 3){
                g2.setColor(hellGrau);
                g2.fillRect(200, 620, 60, 70);
            }
            g2.setColor(Color.RED);
            g2.fillOval(210, 630, 40, 40);

            // Bank
            if (typ == 4){
                g2.setColor(hellGrau);
                g2.fillRect(260, 620, 60, 70);
            }
            Color brown = new Color(139, 69, 19);
            g2.setColor(brown);
            g2.fillRect(270, 630, 40, 40);


            // Kosten
            g2.setColor(Color.BLACK);
            g2.drawString("50", 102, 685);
            g2.drawString("100", 160, 685);
            g2.drawString("100", 220, 685);
            g2.drawString("200", 280, 685);

        }




        // Game Over
        if (gameOver && !spielMunueOffen){

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.PLAIN, 50));
            g2.drawString("Game Over", 375, 350);

            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.drawRect(415, 400, 160, 50);
            g2.drawString("Back to Menue", 425, 433);

        }

        if (spielMunueOffen){
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

            if (spielMunueOffen && mousex > 420 && mousex < 490 && mousey > 350 && mousey < 410){
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

            spawnDelay = 1000 / spielgeschwindigkeit;
            minSpawnDelay = 100 / spielgeschwindigkeit;

            shootDelay = 500 / spielgeschwindigkeit;

            mainDelay = 16 / spielgeschwindigkeit;


        }


        // Turmmenue öffnen
        if (mousex > 10 && mousex < 60 && mousey > 640 && mousey < 690){
            turmMenueOffen = !turmMenueOffen;

        }


        // Turm auswählen
        if (turmMenueOffen){
            if (mousex > 90 && mousex < 130 && mousey > 620 && mousey < 690){
                typ = 1;
            }else if(mousex > 150 && mousex < 190 && mousey > 620 && mousey < 690){
                typ = 2;
            }else if(mousex > 210 && mousex < 250 && mousey > 620 && mousey < 690){
                typ = 3;
            }else if(mousex > 270 && mousex < 310 && mousey > 620 && mousey < 690){
                typ = 4;
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

            if (!einstellungenOffen && !turmMenueOffen){
                if (mousex < 930 || mousex > 1000 || mousey < 630 || mousey > 700){
                    if (mousex < 0 || mousex > 60 || mousey < 640 || mousey > 700) {
                        if (mousey + 30 <= WEG_Y || mousey - 30 >= WEG_Y + 100) {



                            if (tower.istPlatzFrei(mousex, mousey, plaziertx, plazierty) &&
                                    bank.istPlatzFrei(mousex, mousey, plaziertx, plazierty)) {

                                if (typ <= 3) {
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

            if (!einstellungenOffen && !turmMenueOffen) {
                if (mousex < 930 || mousex > 1000 || mousey < 630 || mousey > 700) {
                    if (mousex < 0 || mousex > 60 || mousey < 640 || mousey > 700) {
                        if (mousey + 30 <= WEG_Y || mousey - 30 >= WEG_Y + 100) {


                            if (tower.istPlatzFrei(mousex, mousey, plaziertx, plazierty) &&
                                    bank.istPlatzFrei(mousex, mousey, plaziertx, plazierty)) {

                                if (typ == 4) {
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
}