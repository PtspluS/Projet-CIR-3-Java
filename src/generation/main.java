

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


    public void start(Stage primaryStage) {
        City lille = new City(0,0,"Lille");
        City tourcoing = new City(500,1000,"Tourcoing");
        City paris = new City(400,1900,"Paris");
        City marseille = new City(1000,50,"Marseille");
        City amien = new City(1551,400,"Tourcoing");
        NetWork map = new NetWork(true , lille,tourcoing,paris);
        int windowweight=1000;
        int windoheight=800;
        double mapweight=2000;
        double mapheight=2000;



        primaryStage.setTitle("Network");
        Group root = new Group();
        Scene scene = new Scene(root, windowweight, windoheight, Color.LIGHTGREEN);
        primaryStage.setScene(scene);
        Canvas canvas = new Canvas(windowweight,  windoheight);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Drawing draw=new Drawing(windowweight,windoheight,mapweight ,mapheight);
        draw.drawroad(map,gc);
        draw.drawcity(map,gc);
        Voiture car=new Voiture(10,60);
        map.getRoads().get(1).debugAjouterAller(car,0,300);
        root.getChildren().add(draw);

        new AnimationTimer()
        {
        public void handle(long currentNanoTime)
        {
            map.getRoads().get(1).avancerFrame(10);
            draw.drawcar(map,gc);

        }
    }.start();


        primaryStage.show();







}



}






