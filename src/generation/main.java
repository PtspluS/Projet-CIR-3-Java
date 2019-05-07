

package generation;

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


public class main extends Application{




    public static void main(String[] args) {
              Application.launch(main.class, args);
    }


    public void start(Stage primaryStage) {//Fonction qui gere l'application
        City lille = new City(0,0,"Lille");//creation des villes
        City tourcoing = new City(50,100,"Tourcoing");
        City paris = new City(40,190,"Paris");
        City marseille = new City(100,5,"Marseille");
        City amien = new City(151,40,"Tourcoing");
        NetWork map = new NetWork(true ,lille,tourcoing,paris,marseille,amien);
        int windowweight=1000;
        int windoheight=800;
        double mapweight=200;
        double mapheight=200;



        primaryStage.setTitle("Network");//creaton de la fenetre
        Group root = new Group();//creation du groupe racine
        Scene scene = new Scene(root, windowweight, windoheight, Color.LIGHTGREEN);//creation de la scene
        primaryStage.setScene(scene);

        Drawing draw=new Drawing(windowweight,windoheight,mapweight ,mapheight);//creation d'un objet dessin
        draw.drawroad(map);//dessin des routes
        draw.drawcity(map);//dessin des villes
        root.getChildren().add(draw);

        new AnimationTimer()//gestion de l'animation
        {
            public int delay = 0;
        public void handle(long currentNanoTime)
        {
            if(delay==10) {
                delay=0;
                Voiture car = new Voiture(Math.random() * 5 + 10, Math.random() * 30 + 50);//creation des voitures
                map.getRoads().get((int) (Math.round(Math.random() * (map.getRoads().size() - 1)))).debugAjouterAller(car, 0, 0);//lancement des voitures
            }else{
                delay++;
            }
            for(int j=0;j<map.getRoads().size();j++) {//boucle de rafraichissement
                map.getRoads().get(j).avancerFrame(5);
                draw.removecar();
                draw.drawcar(map);
            }

        }
    }.start();


        primaryStage.show();







}



}






