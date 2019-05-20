

package generation;

import java.util.ArrayList;

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
                creat(root, windowwidth,windowheight);

            }
        });
        primaryStage.show();



    }


    public void creat(BorderPane root,int windowwidth,int windowheight){
        VBox vbox = new VBox();
        vbox.setSpacing(8);



        //inputposx.setMaxWidth(120);
        HBox hb = new HBox();
        Label label1 = new Label("size x:");
        TextField inputposx = new TextField ();
        hb.getChildren().addAll(label1, inputposx);
        hb.setSpacing(10);
        hb.setAlignment(Pos.CENTER);



        HBox hb2 = new HBox();
        Label label2 = new Label("size y:");
        TextField inputposy = new TextField ();
        hb2.getChildren().addAll(label2, inputposy);
        hb2.setSpacing(10);
        hb2.setAlignment(Pos.CENTER);

        Button Go = new Button("Go ");
        Go.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                int posx=0;
                int posy=0;
                boolean passed=false;
                try {
                     posx = Integer.parseInt(inputposx.getText());
                     posy = Integer.parseInt(inputposy.getText());
                    passed=true;
                }catch (Exception e){
                    inputposx.setText("wrong value");
                    inputposy.setText("wrong value");}
                if(passed) {

                    root.getChildren().clear();
                    editor(root,windowwidth,windowheight,posx,posy);
                }

            }
        });

        vbox.getChildren().addAll(hb,hb2,Go);
        root.setCenter(vbox);
        vbox.setAlignment(Pos.CENTER);

    }



        public void editor(BorderPane root,int windowwidth,int windowheight,int mapwidth,int mapheight ){
        NetWork net=new NetWork();
            double scaler;if(mapwidth/windowwidth>=mapheight/windowheight){
                scaler=mapwidth;
            }else{scaler=mapheight;}


            VBox vbox = new VBox();
            vbox.setSpacing(8);
            final ToggleGroup groupaction = new ToggleGroup();
            final ToggleGroup grouptype = new ToggleGroup();


            RadioButton choosecity = new RadioButton("City");
            choosecity.setToggleGroup(groupaction);
            RadioButton chooseroad = new RadioButton("Road");
            chooseroad.setToggleGroup(groupaction);
            RadioButton choosedcity = new RadioButton("delete City");
            choosedcity.setToggleGroup(groupaction);
            RadioButton choosedroad = new RadioButton("delete road");
            choosedroad.setToggleGroup(groupaction);


            RadioButton choosedep = new RadioButton("Departemental");
            choosedep.setToggleGroup(grouptype);
            RadioButton choosenat = new RadioButton("National");
            choosenat.setToggleGroup(grouptype);
            RadioButton choosenaut= new RadioButton("Autoroute");
            choosenaut.setToggleGroup(grouptype);



            HBox hb = new HBox();
            Label label1 = new Label("city name:");
            TextField cityname = new TextField ();
            hb.getChildren().addAll(label1, cityname);
            hb.setSpacing(10);


            vbox.getChildren().addAll(choosecity,chooseroad,choosedcity,choosedroad,choosedep,choosenat,choosenaut,hb);
            root.setLeft(vbox);
            vbox.setAlignment(Pos.BASELINE_LEFT);

            Drawing draw=new Drawing(windowwidth-800,windowheight,mapwidth,mapheight);
          draw.drawwindow();
        Group drawplace=new Group();
           drawplace.getChildren().add(draw);
            root.setCenter(drawplace);

            root.getCenter().setOnMouseClicked(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent me){
                    if(groupaction.getSelectedToggle()==choosecity){
                        if((int)me.getX()>815 && (int)me.getX()<mapwidth/(scaler)*(windowwidth-30) && (int)me.getY()>15 && (int)me.getY()<mapwidth/(scaler)*(windowheight-30)) {
                            City ville1 = new City((int) me.getX()/(scaler)*(windowheight-30) , (int) me.getY()/(scaler)*(windowheight-30),cityname.getText());
                            net.addCity(ville1);
                            drawplace.getChildren().add(draw.drawonecity((int) me.getX(), (int) me.getY()));
                        }
                        }
                    if(groupaction.getSelectedToggle()==choosedcity){
                        System.out.println("delete");
                     City ville=Foundnearcity((int)(me.getX()/(scaler)*(windowwidth-30)),(int)(me.getY()/(scaler)*(windowheight-30)),net);
                        if(ville.getX()!=-1){
                            net.removeCity(ville);
                            System.out.println("delete1");
                        }
                    }





                }
            });


        }

        public City Foundnearcity(int posx,int posy,NetWork net){

        for(City i : net.getCities()){

            if(Math.abs(i.getX()-posx)<=15 || Math.abs(i.getY()-posy)<=15){

                return i;
            }
        }
        return new City(-1,-1);


        }







     public void vroum(BorderPane root,int windowwidth,int windowheight,ArrayList<City> citytab){
        //Fonction qui gere l'application

        NetWork map = new NetWork(citytab);
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
            if(delay==100) {
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






