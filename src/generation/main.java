package generation;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class main extends Application{




    public static void main(String[] args) {
       Application.launch(main.class, args);
    }


    public void start(Stage primaryStage) {
        City lille = new City(0,0,"Lille");
        City tourcoing = new City(500,1000,"Tourcoing");
        City paris = new City(400,1900,"Paris");
        City marseille = new City(1000,50,"Marseille");
        City amien = new City(1551,400,"Tourcoing");

        Road N1 = new Road(lille,tourcoing);
        Road N2 = new Road(lille,paris);
        Road N3 = new Road(lille,marseille);
        Road N4 = new Road(lille,amien);
        Road N5 = new Road(paris,amien);
        Road N6 = new Road(tourcoing,amien);
        Road N7 = new Road(marseille,amien);
        City listCities []= {lille, tourcoing,paris,marseille,amien};
        NetWork map = new NetWork(listCities,true);
        int windowweight=1000;
        int windoheight=800;
        double mapweight=2000;
        double mapheight=2000;



        primaryStage.setTitle("Network");
        Group root = new Group();
        Scene scene = new Scene(root, windowweight, windoheight, Color.LIGHTGREEN);
        primaryStage.setScene(scene);
        Drawing draw=new Drawing(windowweight,windoheight,mapweight ,mapheight);
        draw.drawroad(map);
        draw.drawcity(map);
        root.getChildren().add(draw);
        primaryStage.show();

    }



}
