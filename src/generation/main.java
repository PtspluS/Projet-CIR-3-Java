

package generation;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;


public class main extends Application{




    public static void main(String[] args) {
              Application.launch(main.class, args);
    }


    public void start(Stage primaryStage) {
ArrayList<City> citytab=new ArrayList();
        citytab.add( new City(0,0,"Lille"));//creation des villes
        citytab.add( new City(1,90,"Tourcoing"));
        citytab.add(new City(500,100,"Paris"));
        citytab.add(new City(500,1000,"Marseille"));
        citytab.add( new City(321,100,"Tourcoing"));
        citytab.add( new City(0,50,"B"));
        citytab.add( new City(400,0,"B"));
        citytab.add( new City(250,300,"B"));





        primaryStage.setTitle("Road of Tottiland");
        int windowwidth=1900;
        int windowheight=900;

        //Group root = new Group();//creation du groupe racine
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, windowwidth, windowheight, Color.LIGHTGREY);//creation de la scene
        primaryStage.setScene(scene);

        VBox vbox = new VBox();

        vbox.setSpacing(8);
        Button randommap = new Button("Lets Random ! ");
        Button create = new Button("Lets Create a map ");
        vbox.getChildren().addAll(randommap,create);

        root.setCenter(vbox);
        vbox.setAlignment(Pos.CENTER);


        randommap.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("yeee");
                root.getChildren().clear();
                vroum(root, windowwidth,windowheight,citytab);
            }
        });

        create.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("yeee");
                root.getChildren().clear();
                new Editor().creat(root, windowwidth,windowheight);

            }
        });
        primaryStage.show();



    }










     public void vroum(BorderPane root,int windowwidth,int windowheight,ArrayList<City> citytab){
        //Fonction qui gere l'application

        NetWork map = new NetWork(true,new City(0,0,"Lille"),new City(1,90,"Tourcoing"),new City(500,100,"Paris"),new City(500,1000,"Marseille"), new City(321,100,"Tourcoing"));
         double mapwidth=500;
         double mapheight=1000;



        //creaton de la fenetre


        Drawing draw=new Drawing(windowwidth,windowheight,mapwidth ,mapheight);//creation d'un objet dessin
         root.getChildren().add(draw);
        draw.drawroad(map);//dessin des routes
        draw.drawcity(map);//dessin des villes


        new AnimationTimer()//gestion de l'animation
        {
            public int delay = 0;
        public void handle(long currentNanoTime)
        {
            if(delay==10) {
                delay=0;
                Voiture car = new Voiture(Math.random() * 5 + 10, Math.random() * 30 + 10);//creation des voitures
                map.getRoads().get((int) (Math.round(Math.random() * (map.getRoads().size() - 1)))).debugAjouterAller(car, 0, 0);//lancement des voitures
                Voiture car2 = new Voiture(Math.random() * 5 + 10, Math.random() * 30 + 10);//creation des voitures
                map.getRoads().get((int) (Math.round(Math.random() * (map.getRoads().size() - 1)))).debugAjouterRetour(car2, 0, 0);//lancement des voitures
            }else{
                delay++;
            }
            for(int j=0;j<map.getRoads().size();j++) {//boucle de rafraichissement
                map.getRoads().get(j).avancerFrame(50);
                draw.removecar();
                draw.drawcar(map);
            }

        }
    }.start();










}



}






