package generation;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import generation.Road;
import generation.Voiture;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Drawing extends Parent {// Classe qui rassemble tout es objets a dessiner
    private int windowwidth;
    private int windowheight;
    private double mapwidth;
    private double mapheight;
    private double scaler;
    private Group car;

    Drawing(int windowwidth,int windowheight,double mapwidth,double mapheight){//Constructeur, initialisation des vaviable de taille de fenetre
        this.windowheight=windowheight;
        this.windowwidth=windowwidth;
        this.mapheight=mapheight;
        this.mapwidth=mapwidth;
        if(mapwidth/windowwidth>mapheight/windowheight){
            this.scaler=mapwidth;

        }else{this.scaler=mapheight;
        System.out.println("aa"+this.scaler);}

    }

    public double scaleX(double tmp){//Une fonction qui met a l'echelle
        return tmp/(this.scaler)*(this.windowwidth-30)+15;
    }

    public double scaleY(double tmp){//Une fonction qui met a l'echelle
        return tmp/(this.scaler)*(this.windowheight-30)+15;
    }

    public void drawcity(NetWork map){// Fonction qui dessine les ville
        Group city = new Group();//Cration du groupe de dessin de ville
        this.getChildren().add(city);
        for(City c:map.getCities()){

            Circle cercle = new Circle();//les villes sont des cercles

            cercle.setCenterX(scaleX(c.getX()));
            cercle.setCenterY(scaleY(c.getY()));
            cercle.setRadius(10);
            cercle.setFill(Color.YELLOW);
            cercle.setStroke(Color.ORANGE);
            cercle.setStrokeWidth(5);
            city.getChildren().add(cercle);


        }

    }







    public void drawroad(NetWork map){// Dessin des routes
        Group road= new Group();//groupe de dessins des routes
        this.getChildren().add(road);
        for(Road r:map.getRoads()){

            Line line = new Line();//les routes sont des ligne
            line.setStartX(scaleX(r.getStart().getX()));
            line.setStartY(scaleY(r.getStart().getY()));
            line.setEndX(scaleX(r.getEnd().getX()));
            line.setEndY(scaleY(r.getEnd().getY()));
            line.setSmooth(true);
            Line milieu= new Line(line.getStartX(),line.getStartY(),line.getEndX(),line.getEndY());
            milieu.setSmooth(true);
            milieu.setStroke(Color.BLACK);
            milieu.setStrokeWidth(1);
            Line bordure= new Line(line.getStartX(),line.getStartY(),line.getEndX(),line.getEndY());
            bordure.setSmooth(true);

            switch (r.getType()){//ON dessine des routes differentes pour chaque route differentes
                case DEPARTEMENTALE:
                    bordure.setStroke(Color.BLACK);
                    bordure.setStrokeWidth(5);

                    line.setStroke(Color.LIGHTGREEN);
                    line.setStrokeWidth(4);
                    break;

                case NATIONALE:
                    bordure.setStroke(Color.BLACK);
                    bordure.setStrokeWidth(9);
                    line.setStroke(Color.YELLOW);
                    line.setStrokeWidth(8);
                    break;

                case AUTOROUTE:
                    bordure.setStroke(Color.BLACK);
                    bordure.setStrokeWidth(12);
                    line.setStroke(Color.RED);
                    line.setStrokeWidth(11);
                    break;


            }


            road.getChildren().add(bordure);
            road.getChildren().add(line);
            road.getChildren().add(milieu);

        }

    }
    public void drawcar(NetWork map){// Fonction de dessin des voitures
        this.car= new Group();//groupe des voitures
        this.getChildren().add(car);

        for(Road R:map.getRoads()){//On parcourt les route
            double roadangle=-2*Math.atan((R.getEnd().getY()-R.getStart().getY())/(R.getEnd().getX()-R.getStart().getX()+Math.sqrt(Math.pow(R.getEnd().getX()-R.getStart().getX(),2)+Math.pow(R.getEnd().getY()-R.getStart().getY(),2))));//calcule angle de la route avec le decalage en cas de depassement

            int counter = R.getVoiesAller().size()+1;

            for(ArrayList<Voiture>Voie : R.getVoiesAller()){//parcourt les voie Aller
                counter--;
                for(Voiture Voit:Voie){

                    Circle cercle = new Circle();//on dessine un cercle pour chaque voiture dans le sens aller de la route
                    cercle.setCenterX(scaleX(Math.cos(roadangle)*Voit.getPositionActuelle()+R.getStart().x)+Math.sin(roadangle)*counter*2);
                    cercle.setCenterY(scaleY(-Math.sin(roadangle)*Voit.getPositionActuelle()+R.getStart().y)+Math.cos(roadangle)*counter*2);
                    cercle.setRadius(2);
                    cercle.setFill(Color.WHITE);
                    cercle.setStrokeWidth(1);
                    cercle.setStroke(Color.BLACK);
                    car.getChildren().add(cercle);
                }

            }
                counter=R.getVoiesRetour().size()+1;
            for(ArrayList<Voiture>Voie : R.getVoiesRetour()){//parcourt les voie Retour
                counter--;
                for(Voiture Voit:Voie){

                    Circle cercle = new Circle();//on dessine un cercle pour chaque voiture dans le sens retour
                    cercle.setCenterX(scaleX(Math.cos(roadangle+Math.PI)*Voit.getPositionActuelle()+R.getEnd().x) +Math.sin(roadangle+Math.PI)*counter*2);
                    cercle.setCenterY(scaleY(-Math.sin(roadangle+Math.PI)*Voit.getPositionActuelle()+R.getEnd().y)+Math.cos(roadangle+Math.PI)*counter*2);
                    cercle.setRadius(2);
                    cercle.setFill(Color.WHITE);
                    cercle.setStroke(Color.BLACK);
                    cercle.setStrokeWidth(1);
                    car.getChildren().add(cercle);
                }

            }
        }

    }


    public void removecar(){
        this.getChildren().remove(car);
    }

    public void removeall(){
        this.getChildren().clear();
    }

    public void drawwindow(){
        Rectangle r = new Rectangle();
        r.setX(800);
        r.setY(0);
        r.setWidth(scaleX(this.mapwidth));
        r.setHeight(scaleY(this.mapheight));
        r.setFill(Color.TRANSPARENT);
        r.setStroke(Color.BLACK);
       this.getChildren().add(r);

    }
    public void drawintter(NetWork map){
        Group cross = new Group();//Cration du groupe de dessin de ville
        this.getChildren().add(cross);
        for(Intersection i:map.getCross()){

            Circle cercle = new Circle();//les villes sont des cercles

            cercle.setCenterX(scaleX(i.getX()));
            cercle.setCenterY(scaleY(i.getY()));
            cercle.setRadius(5);
            cercle.setFill(Color.GRAY);
            cross.getChildren().add(cercle);


        }


    }

}