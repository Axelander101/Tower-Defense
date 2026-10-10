package ch.alexb.tower.ui.panles;

import javax.swing.*;
import java.awt.*;

public class MapSelectPanel extends JPanel {

    private CardLayout cardLayout;

    private JPanel hauptPanel;



    public MapSelectPanel(CardLayout cardLayout, JPanel hauptPanel, GamePanel gamePanel){

        this.cardLayout = cardLayout;

        this.hauptPanel = hauptPanel;


        JButton Map1 = new JButton("Spiel starten");

        JButton zurueckButton = new JButton("Zurück");


        add(Map1);

        add(zurueckButton);


        Map1.addActionListener(e -> {

            cardLayout.show(hauptPanel, "game");

            gamePanel.gameStarten(1);

        });


        zurueckButton.addActionListener(e -> {

            cardLayout.show(hauptPanel, "menu");

        });
    }
}
