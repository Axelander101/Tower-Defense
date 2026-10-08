package ch.alexb.tower.ui.panles;

import ch.alexb.tower.ui.*;

import javax.swing.JPanel;
import javax.swing.Timer;
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
    ArrayList<Projektiel> projektiele = new ArrayList<>();

    ArrayList<Integer> plaziertx = new ArrayList<>();
    ArrayList<Integer> plazierty = new ArrayList<>();

    UpgradePanel upgradePanel = new UpgradePanel();
    MenuePanel menuePanel= new MenuePanel();

    Tower ausgewaehlterTower;

    boolean upgradeMenueOffen = false;

    int leben = 1;
    int geld = 10000;

    boolean gameOver = true;
    boolean spielMunueOffen = false;

    private static final int WEG_Y = 330;
    private static final int SPIEL_BREITE = 1000;
    private static final int SPIEL_HOEHE = 700;

    int welle = 0;

    int kostenNormal = 2;
    int kostenSchnell = 3;
    int kostenStark = 5;

    double budget = 0;

    int gegnerAufBildschirm = 0;

    double reichweite;

    int zahl;

    int weitesterGegneraufStrecke = 0;





    Timer spawnTimer;
    Timer gameTimer;
    Timer schiessenTimer;

    Random random = new Random();


    public GamePanel() {

        setBackground(Color.GREEN);

        addMouseListener(this);


        // Gegner spawnen
        spawnTimer = new Timer(1000, e -> {

            int delay = random.nextInt(100, 1000);

            spawnTimer.setDelay(delay);

            if (gegnerAufBildschirm == 0 && budget < 2){
                welle += 1;
                budget = welle * 10 * (1 + (double) welle / 10);
            }

            if (welle % 10 != 0 && welle < 5){
                zahl = 1;
            }

            if (welle % 10 != 0 && welle >= 5 && welle < 15){
                //zahl = random.nextInt(1, 3);
                zahl = 2;
            }

            if (welle % 10 != 0 && welle >= 15){
                zahl = random.nextInt(1, 4);
            }


            // Boss jede 10. Welle
            if (welle % 10 == 0 && budget > 0){

                Boss boss = new Boss();

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

                        enemies.add(enemy);
                    }
                }


                case 2 -> {

                    if (budget >= kostenSchnell){

                        budget -= kostenSchnell;
                        gegnerAufBildschirm += 1;

                        FastEnemy fastenemy = new FastEnemy();

                        fastEnemies.add(fastenemy);
                    }
                }


                case 3 -> {

                    if (budget >= kostenStark){

                        budget -= kostenStark;
                        gegnerAufBildschirm += 1;

                        StrongEnemy strongEnemy = new StrongEnemy();

                        strongEnemies.add(strongEnemy);
                    }
                }
            }
        });


        // ch.alexb.tower.ui.Tower schießen
        schiessenTimer = new Timer(500, e -> {

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

                }


                weitesterGegneraufStrecke = 0;
                tower.schiessen(false);
            }
        });


        // Hauptspiel
        gameTimer = new Timer(16, e -> {


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

                            if (enemy.getLeben() <= 0) {

                                enemies.remove(i);
                                geld += 5;
                                gegnerAufBildschirm -= 1;
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

                            if (fastEnemy.getLeben() <= 0) {

                                fastEnemies.remove(i);
                                geld += 7;
                                gegnerAufBildschirm -= 1;
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

                            if (strongEnemy.getLeben() <= 0) {

                                strongEnemies.remove(i);
                                geld += 10;
                                gegnerAufBildschirm -= 1;
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

                            if (boss.getLeben() <= 0) {
                                bosses.remove(i);
                                geld += 100;
                                gegnerAufBildschirm -= 1;
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


            repaint();
        });


        spawnTimer.start();
        gameTimer.start();
        schiessenTimer.start();
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


        // ch.alexb.tower.ui.Tower zeichnen
        for (Tower tower : towers){

            tower.towerzeichnen(g2, tower.getMousex(), tower.getMousey());
        }


        // Projektile zeichnen
        for (Projektiel projektiel : projektiele){

            projektiel.projektielZeichnen(g2);
        }


        // Upgrade Menue
        if (upgradeMenueOffen && ausgewaehlterTower != null){

            upgradePanel.upgrademenue(g2, ausgewaehlterTower);
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
            menuePanel.menue(g2);
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
                welle = 0;
                gegnerAufBildschirm = 0;

                // Game Loop starten
                spawnTimer.start();
                gameTimer.start();
                schiessenTimer.start();


                repaint();
            }
        }


        // Upgrade Menue ist offen
        if (upgradeMenueOffen && ausgewaehlterTower != null){


            // Menue schliessen
            if (mousex >= 950 && mousex <= 1000 && mousey >= 0 && mousey <= 50){

                upgradeMenueOffen = false;

                ausgewaehlterTower = null;

                repaint();

                return;
            }


            // ch.alexb.tower.ui.Tower verkaufen
            if (mousex >= 725 && mousex <= 885 && mousey >= 600 && mousey <= 660){
                upgradeMenueOffen = false;

                towers.removeIf(tower -> ausgewaehlterTower.equals(tower));

                ausgewaehlterTower = null;


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
            // kann kein ch.alexb.tower.ui.Tower dahinter gesetzt werden
            return;
        }


        // Vorhandenen ch.alexb.tower.ui.Tower anklicken
        for (Tower tower : towers){

            if (tower.aufTowerGeklickt(mousex, mousey)){

                ausgewaehlterTower = tower;

                upgradeMenueOffen = true;

                repaint();

                return;
            }
        }


        // Neuen ch.alexb.tower.ui.Tower platzieren
        if (geld >= 50){

            if (mousey + 30 <= WEG_Y || mousey - 30 >= WEG_Y + 100){

                Tower tower = new Tower();


                if (tower.istPlatzFrei(mousex, mousey, plaziertx, plazierty)){

                    tower.newTower(mousex, mousey);

                    geld -= 50;

                    plaziertx.add(mousex);

                    plazierty.add(mousey);

                    towers.add(tower);

                    repaint();
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