package ch.alexb.tower;

import ch.alexb.tower.ui.panles.GamePanel;
import ch.alexb.tower.ui.panles.MainMenuPanel;
import ch.alexb.tower.ui.panles.MapSelectPanel;
import ch.alexb.tower.ui.panles.UpgradePanel;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame();

        CardLayout cardLayout = new CardLayout();

        JPanel hauptPanel = new JPanel(cardLayout);

        GamePanel gamePanel = new GamePanel(cardLayout, hauptPanel);

        MainMenuPanel mainMenuPanel = new MainMenuPanel(cardLayout, hauptPanel);

        MapSelectPanel mapSelectPanel = new MapSelectPanel(cardLayout, hauptPanel, gamePanel);



        hauptPanel.add(mainMenuPanel, "menu");
        hauptPanel.add(gamePanel, "game");

        hauptPanel.add(mapSelectPanel, "maps");



        frame.add(hauptPanel);

        frame.setSize(1000, 700);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);


        cardLayout.show(hauptPanel, "menu");
    }
}