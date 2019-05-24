

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

        //NetWork map = new NetWork(true,citytab);




        primaryStage.setTitle("Road of Tottiland");
        int windowwidth=1900;
        int windowheight=900;



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
                int mapwidth=100;
                int mapheight=100;
                new Editor().vroum(root, windowwidth,windowheight,maprandom(mapwidth,mapheight),mapwidth,mapheight);
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

    public NetWork maprandom(int mapsizex,int mapsizey){
        NetWork map = new NetWork();
        System.out.println(mapsizex);
        System.out.println(mapsizey);
        int nbville=(int)(Math.random()*9)+3;
        for(int i=0;i<nbville;i++){
            map.addCity(new City(Math.random()*(mapsizex-15),Math.random()*(mapsizey-15),Integer.toString(i)));
                  }
        for(City c:map.getCities()){
            for(int i=0;i<2;i++){
            int rand=(int)(Math.random()*nbville);
            City ville=map.getCities().get(rand);
            if(c!=ville) {
                if (c.getRoads().size()==0){
                    map.addRoad(c,ville,choostype());
                }else{if(roadexist(c,ville)){
                    i--;

                }else{
                    map.addRoad(c,ville,choostype());
                }

                }
            }else{i--;}
            }
        }


return map;
        }


        public Road.TypeRoute choostype(){
        int rand=(int)(Math.random()*3);
            Road.TypeRoute type= Road.TypeRoute.DEPARTEMENTALE;
            switch (rand){

                case 0:
                    type= Road.TypeRoute.DEPARTEMENTALE;
                    break;

                case 1:
                    type= Road.TypeRoute.NATIONALE;
                    break;

                    case 2:
                        type= Road.TypeRoute.AUTOROUTE;
                        break;
            }
        return type;
        }

    public boolean roadexist(City a,City b){
        boolean exist=false;
        for(Road r:a.getRoads()){
            if(r.getStart()==b || r.getEnd()==b){
                exist=true;
            }
        }

        return exist;

    }






}






