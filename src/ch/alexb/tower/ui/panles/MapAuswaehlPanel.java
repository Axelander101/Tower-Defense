package ch.alexb.tower.ui.panles;

import ch.alexb.tower.ui.MapDaten;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class MapAuswaehlPanel extends JPanel {

    private CardLayout cardLayout;

    private JPanel hauptPanel;

    private MapSchwierigkeitPanel mapSchwierigkeitPanel;

    private int ausgewaehltemap;

    private int miniX;
    private int miniY;


    public MapAuswaehlPanel(CardLayout cardLayout, JPanel hauptPanel, MapSchwierigkeitPanel mapSchwierigkeitPanel){

        this.cardLayout = cardLayout;

        this.hauptPanel = hauptPanel;

        this.mapSchwierigkeitPanel = mapSchwierigkeitPanel;


        JButton map1Button = new JButton("Gerade auswählen");

        setLayout(null);

        map1Button.setBounds(10, 120, 150, 20);

        add(map1Button);

        JButton map2Button = new JButton("Kurvenweg auswählen");

        map2Button.setBounds(180, 120, 150, 20);

        add(map2Button);

        JButton map3Button = new JButton("U-Turn auswählen");

        map3Button.setBounds(350, 120, 150, 20);

        add(map3Button);

        JButton map4Button = new JButton("S-Kurve auswählen");

        map4Button.setBounds(520, 120, 150, 20);

        add(map4Button);

        JButton map5Button = new JButton("Serpentinen auswählen");

        map5Button.setBounds(690, 120, 150, 20);

        add(map5Button);

        JButton map6Button = new JButton("Brücke auswählen");

        map6Button.setBounds(10, 240, 150, 20);

        add(map6Button);

        JButton map7Button = new JButton("Spirale auswählen");

        map7Button.setBounds(180, 240, 150, 20);

        add(map7Button);

        JButton map8Button = new JButton("Zickzack auswählen");

        map8Button.setBounds(350, 240, 150, 20);

        add(map8Button);




        map1Button.addActionListener(e -> {

            mapSchwierigkeitPanel.mapBestimmen(1);

            cardLayout.show(hauptPanel, "schwirigkeit");


        });

        map2Button.addActionListener(e-> {

            mapSchwierigkeitPanel.mapBestimmen(2);

            cardLayout.show(hauptPanel, "schwirigkeit");

        });


        map3Button.addActionListener(e-> {

            mapSchwierigkeitPanel.mapBestimmen(3);

            cardLayout.show(hauptPanel, "schwirigkeit");

        });

        map4Button.addActionListener(e-> {

            mapSchwierigkeitPanel.mapBestimmen(4);

            cardLayout.show(hauptPanel, "schwirigkeit");

        });

        map5Button.addActionListener(e-> {

            mapSchwierigkeitPanel.mapBestimmen(5);

            cardLayout.show(hauptPanel, "schwirigkeit");

        });

        map6Button.addActionListener(e-> {

            mapSchwierigkeitPanel.mapBestimmen(6);

            cardLayout.show(hauptPanel, "schwirigkeit");

        });

        map7Button.addActionListener(e-> {

            mapSchwierigkeitPanel.mapBestimmen(7);

            cardLayout.show(hauptPanel, "schwirigkeit");

        });

        map8Button.addActionListener(e-> {

            mapSchwierigkeitPanel.mapBestimmen(8);

            cardLayout.show(hauptPanel, "schwirigkeit");

        });




    }


    private void zeichneMiniMap(Graphics2D g2, int map, int x, int y) {

        double scaleX = 150.0 / 1000.0;
        double scaleY = 100.0 / 700.0;

        ArrayList<Point> wegPunkte = MapDaten.getWegPunkte(map);

        Path2D.Double miniMap = new Path2D.Double();

        Point start = wegPunkte.get(0);

        miniMap.moveTo(
                x + start.x * scaleX,
                y + start.y * scaleY
        );

        for (int i = 1; i < wegPunkte.size(); i++) {

            Point p = wegPunkte.get(i);

            miniMap.lineTo(
                    x + p.x * scaleX,
                    y + p.y * scaleY
            );
        }

        Color matschBraun = new Color(205, 133, 63);

        if (map == 1 || map == 5) {
            g2.setColor(Color.GREEN);
        }else if (map == 2 || map == 7){
            g2.setColor(Color.YELLOW);
        }else if ( map == 3 || map == 8){
            g2.setColor(Color.lightGray);
        }else if (map == 6 || map == 4){
            g2.setColor(matschBraun);
        }




        g2.fillRect(x, y, 150, 100);

        g2.setColor(Color.GRAY);

        g2.setStroke(new BasicStroke(
                12,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND
        ));

        g2.draw(miniMap);
    }

    @Override
    protected void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;


        for (int i = 1; i <= 8; i++){
            int x = 10 + 170 * (i-1);
            int y = 25;
            if (i > 5){
                y = 145;
                x = 10 + 170 *(i-6);
            }

            zeichneMiniMap(g2, i, x, y);


        }

    }
}
