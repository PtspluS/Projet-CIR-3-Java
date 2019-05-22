

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
        citytab.add(new City(499,312,"Paris"));
        citytab.add(new City(499,25,"Marseille"));
        citytab.add( new City(321,100,"Tourcoing"));
        citytab.add( new City(0,50,"B"));
        citytab.add( new City(400,0,"B"));
        citytab.add( new City(250,300,"B"));
        NetWork map = new NetWork(true,new City(0,0,"Lille"),new City(100,90,"Tourcoing"),new City(500,100,"Paris"),new City(400,300,"Marseille"), new City(321,500,"Tourcoing"));




        primaryStage.setTitle("Road of Tottiland");
        int windowwidth=1900;
        int windowheight=900;
        int mapwidth=500;
        int mapheight=500;


        BorderPane root = new BorderPane();//Fenetre generale
        Scene scene = new Scene(root, windowwidth, windowheight, Color.LIGHTGREY);//creation de la scene
        primaryStage.setScene(scene);
        VBox vbox = new VBox();//boite horizontale
        vbox.setSpacing(8);
        Button randommap = new Button("Lets Random ! ");//bouton de lancement
        Button create = new Button("Lets Create a map ");//bouton de l'editeur
        vbox.getChildren().addAll(randommap,create);

        root.setCenter(vbox);
        vbox.setAlignment(Pos.CENTER);
        randommap.setOnAction(new EventHandler<ActionEvent>() {//action du bouton de lancement
            @Override
            public void handle(ActionEvent event) {
                System.out.println("yeee");
                root.getChildren().clear();
                new Editor().vroum(root, windowwidth,windowheight,map,mapwidth,mapheight);
            }
        });

        create.setOnAction(new EventHandler<ActionEvent>() {// action du bouton de l'editeur

            @Override
            public void handle(ActionEvent event) {
                System.out.println("yeee");
                root.getChildren().clear();
                new Editor().creat(root, windowwidth,windowheight);

            }
        });
        primaryStage.show();

    }




}






