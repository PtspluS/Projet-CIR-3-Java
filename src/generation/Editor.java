package generation;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Editor {


    public void creat(BorderPane root, int windowwidth, int windowheight){
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
        RadioButton chooseaut= new RadioButton("Autoroute");
        chooseaut.setToggleGroup(grouptype);



        HBox hb = new HBox();
        Label label1 = new Label("city name:");
        TextField cityname = new TextField ();
        hb.getChildren().addAll(label1, cityname);
        hb.setSpacing(10);

        Button go1 = new Button("Go ");
        go1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                for(City c : net.getCities()){
                    double tmp=c.getX()/(scaler)*(windowwidth-830)+15;
                    tmp+=-800;
                    tmp=(tmp-15) * (scaler) / (windowwidth - 830);
                    c.setX(tmp);
                }
                root.getChildren().clear();
                vroum(root,windowwidth,windowheight,net,mapwidth,mapheight);
            }})
            ;

        vbox.getChildren().addAll(choosecity,chooseroad,choosedcity,choosedroad,choosedep,choosenat,chooseaut,hb,go1);
        root.setLeft(vbox);
        vbox.setAlignment(Pos.BASELINE_LEFT);

        Drawing draw=new Drawing(windowwidth-800,windowheight,mapwidth,mapheight);
        draw.drawwindow();
        Group drawplace=new Group();
        drawplace.getChildren().add(draw);
        root.setCenter(drawplace);




        root.getCenter().setOnMouseClicked(new EventHandler<MouseEvent>(){
            City villesave=new City(-1,-1);
            public void handle(MouseEvent me){
                Road.TypeRoute route= Road.TypeRoute.DEPARTEMENTALE;


                if(groupaction.getSelectedToggle()==choosecity){
                    if((int)me.getX()>815 && (int)me.getX()<mapwidth/(scaler)*(windowwidth-30) && (int)me.getY()>15 && (int)me.getY()<mapwidth/(scaler)*(windowheight-30)) {
                        City ville1 = new City((int) ((me.getX()-15) * (scaler) / (windowwidth - 830)), (int)( (me.getY()-15) * (scaler) / (windowheight-30)), cityname.getText());
                        System.out.println((int) me.getX());
                        System.out.println((int)me.getY());
                        net.addCity(ville1);
                        refresh(drawplace,draw,net);
                    }}


                if(groupaction.getSelectedToggle()==choosedcity){
                    System.out.println("delete");
                    City ville=Foundnearcity((int) (me.getX()),(int)(me.getY()),net,scaler,windowwidth,windowheight);
                    if(ville.getX()!=-1){

                        for(int r=0;r<net.getRoads().size();r++){
                            if(net.getRoads().get(r).getStart()==ville || net.getRoads().get(r).getEnd()==ville ){
                                net.removeRoad(net.getRoads().get(r));
                                r=-1;
                            }}
                        net.removeCity(ville);
                        refresh(drawplace,draw,net);
                        System.out.println("delete1");
                    }
                }

                if(groupaction.getSelectedToggle()==chooseroad){
                    if(villesave.getX()!=-1){
                        if(grouptype.getSelectedToggle()==choosedep){
                            route= Road.TypeRoute.DEPARTEMENTALE;
                        }
                        if(grouptype.getSelectedToggle()==choosenat){
                            route= Road.TypeRoute.NATIONALE;
                        }
                        if(grouptype.getSelectedToggle()==chooseaut){
                            route= Road.TypeRoute.AUTOROUTE;
                        }

                        City ville=Foundnearcity((int) (me.getX()),(int)(me.getY()),net,scaler,windowwidth,windowheight);
                        if(villesave==ville){
                            villesave=new City(-1,-1);
                            System.out.println("ville deselectioner");
                            refresh(drawplace,draw,net);
                        }else{
                            if(ville.getX()!=-1){
                                boolean exist=false;
                                for( Road r :net.getRoads() ){
                                    if(r.getStart()==ville && r.getEnd()==villesave || r.getStart()==villesave && r.getEnd()==ville ){
                                        exist=true;
                                    }

                                }
                                if(!exist){
                                    net.addRoad(villesave,ville,route);
                                    refresh(drawplace,draw,net);
                                    villesave=new City(-1,-1);
                                    System.out.println("route cree");}}

                        }}else{
                        villesave=Foundnearcity((int) (me.getX()),(int)(me.getY()),net,scaler,windowwidth,windowheight);
                        if(villesave.getX()!=-1){
                            Circle cercle = new Circle();
                            cercle.setCenterX(villesave.getX()/(scaler)*(windowwidth-830)+15);
                            cercle.setCenterY(villesave.getY()/(scaler)*(windowheight-30)+15);
                            cercle.setRadius(15);
                            cercle.setFill(Color.TRANSPARENT);
                            cercle.setStroke(Color.WHITE);
                            cercle.setStrokeWidth(10);
                            drawplace.getChildren().add(cercle);

                            System.out.println("ville selected");}
                    }
                }
                if(groupaction.getSelectedToggle()==choosedroad){
                    if(villesave.getX()!=-1){

                        City ville=Foundnearcity((int) (me.getX()),(int)(me.getY()),net,scaler,windowwidth,windowheight);
                        if(villesave==ville){
                            villesave=new City(-1,-1);
                            System.out.println("ville deselectioner");
                            refresh(drawplace,draw,net);
                        }else{
                            if(ville.getX()!=-1){
                                boolean exist=false;
                                for( int r=0;r<net.getRoads().size() ;r++ ){
                                    if(net.getRoads().get(r).getStart()==ville && net.getRoads().get(r).getEnd()==villesave || net.getRoads().get(r).getStart()==villesave && net.getRoads().get(r).getEnd()==ville ){
                                        net.removeRoad(net.getRoads().get(r));
                                        refresh(drawplace,draw,net);
                                        villesave=new City(-1,-1);
                                        System.out.println("route cree");
                                    }

                                }
                            }

                        }}else{
                        villesave=Foundnearcity((int) (me.getX()),(int)(me.getY()),net,scaler,windowwidth,windowheight);
                        if(villesave.getX()!=-1){
                            Circle cercle = new Circle();
                            cercle.setCenterX(villesave.getX()/(scaler)*(windowwidth-830)+15);
                            cercle.setCenterY(villesave.getY()/(scaler)*(windowheight-30)+15);
                            cercle.setRadius(15);
                            cercle.setFill(Color.TRANSPARENT);
                            cercle.setStroke(Color.RED);
                            cercle.setStrokeWidth(10);
                            drawplace.getChildren().add(cercle);

                            System.out.println("ville selected");}
                    }
                }





            }
        });


    }
    public void refresh(Group drawplace,Drawing draw,NetWork net){
        draw.removeall();
        drawplace.getChildren().clear();
        drawplace.getChildren().add(draw);
        draw.drawwindow();
        draw.drawroad(net);
        draw.drawcity(net);

    }

    public City Foundnearcity(int posx,int posy,NetWork net,double scaler,int windowwidth,int windowheight){

        for(City i : net.getCities()){
            if(Math.abs(i.getX()/(scaler)*(windowwidth-830)+15-posx)<=15 && Math.abs(i.getY()/(scaler)*(windowheight-30)+15-posy)<=15){

                return i;
            }
        }
        return new City(-1,-1);


    }




    public void vroum(BorderPane root,int windowwidth,int windowheight,NetWork map,double mapwidth,double mapheight){
        //Fonction qui gere l'application

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
