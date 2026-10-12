package ch.alexb.tower.ui.panles;

import javax.swing.*;
import java.awt.*;

public class MapAuswaehlPanel extends JPanel {

    private CardLayout cardLayout;

    private JPanel hauptPanel;

    private MapSchwierigkeitPanel mapSchwierigkeitPanel;

    private int ausgewähltemap;


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



    @Override
    protected void paintComponent(Graphics g){

        super.paintComponent(g);

        // Map 1
        g.setColor(Color.GREEN);
        g.fillRect(10, 10, 150, 100);
        g.setColor(Color.GRAY);
        g.fillRect(10, 55, 150, 15);

        // Map 2
        g.setColor(Color.YELLOW);
        g.fillRect(180, 10, 150, 100);
        g.setColor(Color.GRAY);
        g.fillRect(180, 55, 38, 15);
        g.fillRect(218, 55, 15, 20);
        g.fillRect(218, 75, 65, 15);
        g.fillRect(268, 55, 15, 20);
        g.fillRect(268, 55, 62, 15);


        // Map 3
        g.setColor(Color.lightGray);
        g.fillRect(350, 10, 150, 100);
        g.setColor(Color.GRAY);
        g.fillRect(350, 45, 130, 15);
        g.fillRect(480, 45, 15, 45);
        g.fillRect(350, 75, 140, 15);


    }
}
