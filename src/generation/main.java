import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;


public class main extends Application {


    public static void main(String[] args) {
        Application.launch(main.class, args);
        //map.print();

        //Export.exportNetwork(map, "test.txt");
    }

    public void start(Stage primaryStage) {//Fonction qui gere l'application
        City lille = new City(0, 0, "Lille");//creation des villes
        City tourcoing = new City(20, 20, "Tourcoing");
        City paris = new City(0, 20, "Paris");
        City marseille = new City(20, 0, "Marseille");
        City amien = new City(30, 11, "amien");
        City B = new City(0, 45, "B");
        City E = new City(40, 0, "E");
        City R = new City(25, 12, "R");

        //mise a jour du code
        ArrayList<City> cities = new ArrayList<>();
        cities.add( paris);
        cities.add(lille);
        cities.add(tourcoing);
        cities.add(marseille);
        cities.add(amien);

        NetWork map = new NetWork(true, cities);//si on passe un tableau alors on utilise la nouvel methode
        int windowweight = 1900;
        int windoheight = 900;
        double mapweight = 40;
        double mapheight = 40;


        primaryStage.setTitle("Network");//creaton de la fenetre
        Group root = new Group();//creation du groupe racine
        Scene scene = new Scene(root, windowweight, windoheight, Color.LIGHTGREY);//creation de la scene
        primaryStage.setScene(scene);

        Drawing draw = new Drawing(windowweight, windoheight, mapweight, mapheight);//creation d'un objet dessin
        draw.drawroad(map);//dessin des routes
        draw.drawcity(map);//dessin des villes
        root.getChildren().add(draw);

        for(int i = 0; i < map.getCross().size();i++){
            System.out.println(map.getCross().get(i).getRoads().size());
        }

        new AnimationTimer()//gestion de l'animation
        {
            private int delay = 0;

            public void handle(long currentNanoTime) {
                if (delay == 1) {
                    delay = 0;
                    Voiture car = new Voiture(Math.random() * 5 + 10, Math.random() * 30 + 100);//creation des voitures

                    Road rrr = map.getRoads().get((int) (Math.round(Math.random() * (map.getRoads().size() - 1))));
                    if(rrr.getStart() instanceof City) rrr.ajouterVoitureAller(car, 0);//lancement des voitures

                    Voiture car2 = new Voiture(Math.random() * 5 + 10, Math.random() * 30 + 100);//creation des voitures

                    Road rrr2 = map.getRoads().get((int) (Math.round(Math.random() * (map.getRoads().size() - 1))));
                    if(rrr2.getEnd() instanceof City) rrr2.ajouterVoitureRetour(car2, 0);//lancement des voitures

                } else {
                    delay++;
                }
                for (int j = 0; j < map.getRoads().size(); j++) {//boucle de rafraichissement
                    map.getRoads().get(j).avancerFrame(1);
                }
                draw.removecar();
                draw.drawcar(map);

            }
        }.start();


        primaryStage.show();


    }


}






