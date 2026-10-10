package ch.alexb.tower;

import ch.alexb.tower.ui.panles.GamePanel;
import ch.alexb.tower.ui.panles.MapAuswaehlPanel;
import ch.alexb.tower.ui.panles.MapSchwierigkeitPanel;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame();

        CardLayout cardLayout = new CardLayout();

        JPanel hauptPanel = new JPanel(cardLayout);

        GamePanel gamePanel = new GamePanel(cardLayout, hauptPanel);

        MapSchwierigkeitPanel mapSchwierigkeitPanel = new MapSchwierigkeitPanel(cardLayout, hauptPanel, gamePanel);

        MapAuswaehlPanel mapAuswaehlPanel = new MapAuswaehlPanel(cardLayout, hauptPanel, mapSchwierigkeitPanel);



        hauptPanel.add(mapAuswaehlPanel, "menu");
        hauptPanel.add(gamePanel, "game");

        hauptPanel.add(mapSchwierigkeitPanel, "maps");



        frame.add(hauptPanel);

        frame.setSize(1000, 700);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);


        cardLayout.show(hauptPanel, "menu");
    }
}