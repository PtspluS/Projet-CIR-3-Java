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

import java.util.ArrayList;

public class Editor {//C est l'objet qui gere l'edition d'une nouvelle map


    public void creat(BorderPane root, int windowwidth, int windowheight){//fonction de creation de la premiere fenetre de l'editeur qui va permetre de selectioner la taille de la fenetre
        VBox vbox = new VBox();//boite verticale
        vbox.setSpacing(8);

        HBox hb = new HBox();//boite horizontale
        Label label1 = new Label("distance maximale de la carte en km");
        TextField inputposx = new TextField ();
        hb.getChildren().addAll(label1, inputposx);//on met dans le boite horizontal le champs de texte et son label
        hb.setSpacing(10);
        hb.setAlignment(Pos.CENTER);

       /* HBox hb2 = new HBox();//boite horizontale
        Label label2 = new Label("size y:");
        TextField inputposy = new TextField ();
        hb2.getChildren().addAll(label2, inputposy);//on met dans le boite horizontal le champs de texte et son label
        hb2.setSpacing(10);
        hb2.setAlignment(Pos.CENTER);
*/
        Button Go = new Button("Go ");// creation d'un bouton de validation
        Go.setOnAction(new EventHandler<ActionEvent>() {//en cas d'aapui sur le bouton go on lance la fonction suivate

            @Override
            public void handle(ActionEvent event) {
                int diagonal=0;

                boolean passed=false;
                try {//on verifie que les valeurs entré son correcte
                    diagonal = Integer.parseInt(inputposx.getText());

                    passed=true;
                }catch (Exception e){
                    inputposx.setText("wrong value");

                    }
                if(passed) {


                    int ratio=0;
                    root.getChildren().clear();//on vide la fenetre
                    ratio=(int)Math.sqrt((diagonal*diagonal)/(16*16+9*9));
                    editor(root,windowwidth,windowheight,16*ratio,9*ratio);//on appele la fonction editeur qui créé la fenetre d'edition
                }}


        });

        vbox.getChildren().addAll(hb,Go);//on met les boite et les bontons dans la boite verticale
        root.setCenter(vbox);
        vbox.setAlignment(Pos.CENTER);

    }



    public void editor(BorderPane root,int windowwidth,int windowheight,int mapwidth,int mapheight ){//Fonction de l'editeur
        NetWork net=new NetWork();//creation du network qui va stocker les ville et les routes créée
        double scaler;
        if(windowwidth/windowheight>mapwidth/mapheight){
            scaler=windowheight/mapheight;
        }else{
            scaler=windowwidth/mapwidth;
        }



        VBox vbox = new VBox();// boite verticale
        vbox.setSpacing(8);
        //creation des groupes de declanchement pour les radiobutton
        final ToggleGroup groupaction = new ToggleGroup();
        final ToggleGroup grouptype = new ToggleGroup();

        //Creation des radiobutton pour l'action
        RadioButton choosecity = new RadioButton("City");
        choosecity.setToggleGroup(groupaction);
        RadioButton chooseroad = new RadioButton("Road");
        chooseroad.setToggleGroup(groupaction);
        RadioButton choosedcity = new RadioButton("delete City");
        choosedcity.setToggleGroup(groupaction);
        RadioButton choosedroad = new RadioButton("delete road");
        choosedroad.setToggleGroup(groupaction);

        //Creation des radiobutton pour le type de route
        RadioButton choosedep = new RadioButton("Departemental");
        choosedep.setToggleGroup(grouptype);
        RadioButton choosenat = new RadioButton("National");
        choosenat.setToggleGroup(grouptype);
        RadioButton chooseaut= new RadioButton("Autoroute");
        chooseaut.setToggleGroup(grouptype);

        HBox hb = new HBox();//boite verticale pour entrer le nom des villes
        Label label1 = new Label("city name:");
        TextField cityname = new TextField ();
        hb.getChildren().addAll(label1, cityname);
        hb.setSpacing(10);

        Label info = new Label ();
        info.setTextFill(Color.RED);
        Label info2 = new Label ();
        info2.setTextFill(Color.RED);

        Button go1 = new Button("Go ");//bouton de validation l'orsque la map est fini
        go1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                ArrayList <Road> tmproad=new ArrayList();
                for(Road r:net.getRoads()) {
                    System.out.println(r.getEquationCarthesienneReduite()[0]);
                    System.out.println(r.getEquationCarthesienneReduite()[1]);
                    Road route = new Road(r.getStart(), r.getEnd(), r.getType());
                    tmproad.add(route);
                }
                for(int i=0;i<net.getRoads().size();i++){
                net.removeRoute(net.getRoads().get(i));
                i=0;
                }
                for(Road rt:tmproad){
                    System.out.println(rt.getEquationCarthesienneReduite()[0]);
                    System.out.println(rt.getEquationCarthesienneReduite()[1]);
                    net.addNewRoad(rt.getStart(),rt.getEnd(),rt.getType());
                }

                root.getChildren().clear();
                vroum(root,windowwidth,windowheight,net,mapwidth,mapheight);//On lance la simulation
            }})
            ;

        vbox.getChildren().addAll(choosecity,chooseroad,choosedcity,choosedroad,choosedep,choosenat,chooseaut,hb,go1,info,info2);//on range tout dans la boite verticale
        root.setLeft(vbox);//on met la boite verticale a gauche
        vbox.setAlignment(Pos.BASELINE_LEFT);

        Drawing draw=new Drawing(windowwidth,windowheight,mapwidth,mapheight);//Creation de l'objet de dessin
        draw.drawwindow();//dessin de la fenetre
        Group drawplace=new Group();//Creation del'endroit ou on va mettre le dessin
        drawplace.getChildren().add(draw);
        root.setCenter(drawplace);




        root.getCenter().setOnMouseClicked(new EventHandler<MouseEvent>(){//detection d'evenement
            City villesave=new City(-1,-1);//ville par default
            public void handle(MouseEvent me){
                Road.TypeRoute route= Road.TypeRoute.DEPARTEMENTALE;//type de route par default


                if(groupaction.getSelectedToggle()==choosecity){// SI le radiobutton city est selectioner
                    if((int)me.getX()>15 && (int)me.getX()<(windowwidth - 15) && (int)me.getY()>15 && (int)me.getY()<windowwidth-30) {//si le lieux du clique est dans la fenetre
                        City ville1 = new City(( (me.getX()) / scaler), ( (me.getY()) / scaler), cityname.getText());//on crée une ville dont la position est mis a l'echelle (le -15 et -30 est pour eviter que la ville depasse du cadre)
                        net.addCity(ville1);//On ajoute la ville au network

                        refresh(drawplace,draw,net);//on affiche
                    }}


                if(groupaction.getSelectedToggle()==choosedcity){// SI on choisit de supprimer une ville
                    City ville=Foundnearcity((int) (me.getX()),(int)(me.getY()),net,scaler,windowwidth,windowheight);//on regarde si le clique a lieu sur une ville
                    if(ville.getX()!=-1){//si il y a eu clique sur une ville
                        for(int r=0;r<net.getRoads().size();r++){//On parcourt les routes pour savoir si la ville etait relié a une route
                            if(net.getRoads().get(r).getStart()==ville || net.getRoads().get(r).getEnd()==ville ){
                                net.removeRoute(net.getRoads().get(r));//on retire la route
                                r=-1;
                            }}
                        net.removeCity(ville);//on retire la route
                        refresh(drawplace,draw,net);//on redessine

                    }
                }

                if(groupaction.getSelectedToggle()==chooseroad){//SI on veut mettre une route
                    if(villesave.getX()!=-1){//On regarde si une ville est selectioné
                        if(grouptype.getSelectedToggle()==choosedep){//on regarde de quel type de route il s'agit
                            route= Road.TypeRoute.DEPARTEMENTALE;
                        }
                        if(grouptype.getSelectedToggle()==choosenat){
                            route= Road.TypeRoute.NATIONALE;
                        }
                        if(grouptype.getSelectedToggle()==chooseaut){
                            route= Road.TypeRoute.AUTOROUTE;
                        }
                        City ville=Foundnearcity((int) (me.getX()),(int)(me.getY()),net,scaler,windowwidth,windowheight);//On selectionne une ville
                        if(villesave==ville){//SI la ville selectioner est la meme que celle-ci on deselectionne la ville
                            villesave=new City(-1,-1);
                            refresh(drawplace,draw,net);
                        }else{
                            if(ville.getX()!=-1){//Si la ville cliqué est valide
                                boolean exist=false;
                                for( Road r :net.getRoads() ){//on verifie que la route n'existe pas
                                    if(r.getStart()==ville && r.getEnd()==villesave || r.getStart()==villesave && r.getEnd()==ville ){
                                        exist=true;
                                    }

                                }
                                if(!exist){//Si la route n'existe pas on crée la route
                                    net.addNewRoad(villesave,ville,route);
                                    refresh(drawplace,draw,net);
                                    villesave=new City(-1,-1);
                                    }}

                        }}else{
                        villesave=Foundnearcity((int) (me.getX()),(int)(me.getY()),net,scaler,windowwidth,windowheight);//Si le clique correspond a une ville
                        if(villesave.getX()!=-1){
                            Circle cercle = new Circle();//On crée un repert visuel sur la ville selectioner
                            cercle.setCenterX(villesave.getX()*scaler);
                            cercle.setCenterY(villesave.getY()*scaler);
                            cercle.setRadius(15);
                            cercle.setFill(Color.TRANSPARENT);
                            cercle.setStroke(Color.BLUE);
                            cercle.setStrokeWidth(10);
                            drawplace.getChildren().add(cercle);

                          }
                    }
                }
                if(groupaction.getSelectedToggle()==choosedroad){//SI on veut supprimer une ville
                    if(villesave.getX()!=-1){//On regarde si une ville est selectioné

                        City ville=Foundnearcity((int) (me.getX()),(int)(me.getY()),net,scaler,windowwidth,windowheight);//On selectionne la ville proche du clique
                        if(villesave==ville){//SI les ville son les memes on deselectionne
                            villesave=new City(-1,-1);
                            refresh(drawplace,draw,net);
                        }else{
                            if(ville.getX()!=-1){
                                boolean exist=false;
                                for( int r=0;r<net.getRoads().size() ;r++ ){//On supprime la route
                                    if(net.getRoads().get(r).getStart()==ville && net.getRoads().get(r).getEnd()==villesave || net.getRoads().get(r).getStart()==villesave && net.getRoads().get(r).getEnd()==ville ){
                                        net.removeRoute(net.getRoads().get(r));
                                        refresh(drawplace,draw,net);
                                        villesave=new City(-1,-1);

                                    }

                                }
                            }

                        }}else{
                        villesave=Foundnearcity((int) (me.getX()),(int)(me.getY()),net,scaler,windowwidth,windowheight);//Si le clique correspond a une ville
                        if(villesave.getX()!=-1){
                            Circle cercle = new Circle();//On crée un repert visuel sur la ville selectioner
                            cercle.setCenterX(villesave.getX()*scaler);
                            cercle.setCenterY(villesave.getY()*scaler);
                            cercle.setRadius(15);
                            cercle.setFill(Color.TRANSPARENT);
                            cercle.setStroke(Color.RED);
                            cercle.setStrokeWidth(10);
                            drawplace.getChildren().add(cercle);
                            }
                    }
                }

            }
        });


    }
    public void refresh(Group drawplace,Drawing draw,NetWork net){//Fonction qui redessine la zone de dessin
        draw.removeall();
        drawplace.getChildren().clear();
        drawplace.getChildren().add(draw);
        draw.drawwindow();
        draw.drawroad(net);
        draw.drawcity(net);

    }

    public City Foundnearcity(int posx,int posy,NetWork net,double scaler,int windowwidth,int windowheight){//fonction qui cherche si on a cliquez sur une ville

        for(City i : net.getCities()){
            if(Math.abs(i.getX()*scaler-posx)<=15 && Math.abs(i.getY()*(scaler)-posy)<=15){

                return i;
            }
        }
        return new City(-1,-1);


    }

    public int scale(int x,int scaler){
        return x*(scaler);
    }




    public void vroum(BorderPane root,int windowwidth,int windowheight,NetWork map,double mapwidth,double mapheight){ //Fonction qui gere la simulation
        map.upDateMap();

        //creaton de la fenetre
        Drawing draw=new Drawing(windowwidth,windowheight,mapwidth ,mapheight);//creation d'un objet dessin
        root.getChildren().add(draw);
        draw.drawroad(map);//dessin des routes
        draw.drawintter(map);
        draw.drawcity(map);//dessin des villes


        new AnimationTimer()//gestion de l'animation
        {
            public int delay = 0;
            public void handle(long currentNanoTime)
            {
                if(delay==1) {
                    delay=0;
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
    }
}
