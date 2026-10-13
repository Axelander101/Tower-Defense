package ch.alexb.tower.ui;

import java.awt.*;
import java.util.ArrayList;

public class MapDaten {


    public static ArrayList<Point> getWegPunkte(int map){

        ArrayList<Point> wegPunkte = new ArrayList<>();

        if (map == 1){
            wegPunkte.add(new Point(0, 350));
            wegPunkte.add(new Point(1000, 350));
        }else if (map == 2){
            wegPunkte.add(new Point(0, 350));
            wegPunkte.add(new Point(250, 350));
            wegPunkte.add(new Point(250, 500));
            wegPunkte.add(new Point(600, 500));
            wegPunkte.add(new Point(600, 200));
            wegPunkte.add(new Point(1000, 200));

        }else if (map == 3){
            wegPunkte.add(new Point(0, 230));
            wegPunkte.add(new Point(900, 230));
            wegPunkte.add(new Point(900, 470));
            wegPunkte.add(new Point(0, 470));

        }else if(map == 4){   // S-Kurve

            wegPunkte.add(new Point(0, 180));
            wegPunkte.add(new Point(150, 150));
            wegPunkte.add(new Point(300, 170));
            wegPunkte.add(new Point(420, 250));
            wegPunkte.add(new Point(500, 350));
            wegPunkte.add(new Point(580, 450));
            wegPunkte.add(new Point(700, 520));
            wegPunkte.add(new Point(850, 500));
            wegPunkte.add(new Point(1000, 420));

        }else if(map == 5){   // Serpentinen

            wegPunkte.add(new Point(0, 120));
            wegPunkte.add(new Point(850, 120));
            wegPunkte.add(new Point(850, 290));
            wegPunkte.add(new Point(150, 290));
            wegPunkte.add(new Point(150, 480));
            wegPunkte.add(new Point(850, 480));
            wegPunkte.add(new Point(850, 650));
            wegPunkte.add(new Point(0, 650));


        }else if(map == 6){   // Brücke

            wegPunkte.add(new Point(0, 180));
            wegPunkte.add(new Point(800, 180));
            wegPunkte.add(new Point(800, 600));
            wegPunkte.add(new Point(200, 600));
            wegPunkte.add(new Point(200, 350));
            wegPunkte.add(new Point(1000, 350));

        }else if(map == 7){   // Spirale

            wegPunkte.add(new Point(0, 100));
            wegPunkte.add(new Point(900, 100));
            wegPunkte.add(new Point(900, 630));
            wegPunkte.add(new Point(100, 630));
            wegPunkte.add(new Point(100, 270));
            wegPunkte.add(new Point(720, 270));
            wegPunkte.add(new Point(720, 450));
            wegPunkte.add(new Point(0, 450));


        }else if(map == 8){   // Zickzack

            wegPunkte.add(new Point(0, 100));
            wegPunkte.add(new Point(250, 200));
            wegPunkte.add(new Point(100, 300));
            wegPunkte.add(new Point(400, 400));
            wegPunkte.add(new Point(250, 500));
            wegPunkte.add(new Point(600, 600));
            wegPunkte.add(new Point(750, 450));
            wegPunkte.add(new Point(600, 300));
            wegPunkte.add(new Point(850, 180));
            wegPunkte.add(new Point(1000, 250));
        }


        return wegPunkte;
    }




}
