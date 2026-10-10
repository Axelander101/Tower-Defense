package ch.alexb.tower.ui.panles;

import javax.swing.*;
import java.awt.*;

public class MapAuswaehlPanel extends JPanel {

    private CardLayout cardLayout;

    private JPanel hauptPanel;

    private MapSchwierigkeitPanel mapSchwierigkeitPanel;


    public MapAuswaehlPanel(CardLayout cardLayout, JPanel hauptPanel, MapSchwierigkeitPanel mapSchwierigkeitPanel){

        this.cardLayout = cardLayout;

        this.hauptPanel = hauptPanel;

        this.mapSchwierigkeitPanel = mapSchwierigkeitPanel;


        JButton map1Button = new JButton("Map 1 auswählen");

        setLayout(null);

        map1Button.setBounds(10, 120, 150, 20);

        add(map1Button);

        JButton map2Button = new JButton("Map 2 auswählen");

        setLayout(null);

        map2Button.setBounds(180, 120, 150, 20);

        add(map2Button);


        map1Button.addActionListener(e -> {

            mapSchwierigkeitPanel.mapBestimmen(1);

            cardLayout.show(hauptPanel, "maps");


        });

        map2Button.addActionListener(e-> {

            mapSchwierigkeitPanel.mapBestimmen(2);

            cardLayout.show(hauptPanel, "maps");

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




    }
}
