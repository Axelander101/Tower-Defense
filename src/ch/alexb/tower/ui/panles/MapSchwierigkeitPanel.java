package ch.alexb.tower.ui.panles;

import javax.swing.*;
import java.awt.*;

public class MapSchwierigkeitPanel extends JPanel {

    private CardLayout cardLayout;

    private JPanel hauptPanel;

    private int map;


    public void mapBestimmen (int ausgewaehltemap){

        map = ausgewaehltemap;
    }

    public MapSchwierigkeitPanel(CardLayout cardLayout, JPanel hauptPanel, GamePanel gamePanel){

        this.cardLayout = cardLayout;

        this.hauptPanel = hauptPanel;


        JButton Map1 = new JButton("Spiel starten");

        JButton zurueckButton = new JButton("Zurück");


        add(Map1);

        add(zurueckButton);


        Map1.addActionListener(e -> {

            cardLayout.show(hauptPanel, "game");

            gamePanel.gameStarten(map);

        });


        zurueckButton.addActionListener(e -> {

            cardLayout.show(hauptPanel, "menu");

        });
    }
}
