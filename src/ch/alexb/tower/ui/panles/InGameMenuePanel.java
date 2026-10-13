package ch.alexb.tower.ui.panles;

import java.awt.*;

public class InGameMenuePanel {

    public void menueGameOver(Graphics g2){

        int[] x = {475, 475, 525};
        int[] y = {360, 400, 380};
        g2.setColor(Color.WHITE);
        //Playbutten
        g2.drawRect(465, 350, 70, 60);
        //Settings
        g2.drawRect(545, 350, 70, 60);
        //Back to Map
        g2.drawRect(385, 350, 70, 60);

        g2.setFont(new Font("Arial", Font.PLAIN, 40));
        g2.drawString("Menue", 475, 300);
        g2.setFont(new Font("Arial", Font.PLAIN, 90));
        g2.drawString("⚙️", 555, 405);
        g2.setColor(Color.GREEN);
        g2.fillPolygon(x, y, 3);
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        g2.setColor(Color.BLACK);
        g2.drawString("Menue", 390, 390);



    }

}
