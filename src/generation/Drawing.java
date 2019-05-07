package generation;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import generation.Road;
import generation.Voiture;

import java.util.ArrayList;

public class Drawing extends Parent {// Classe qui rassemble tout es objets a dessiner
    private int windowweight;
    private int windoheight;
    private double mapweight;
    private double mapheight;
    private Group car;

    Drawing(int windowweight,int windoheight,double mapweight,double mapheight){//Constructeur, initialisation des vaviable de taille de fenetre
        this.windoheight=windoheight;
        this.windowweight=windowweight;
        this.mapheight=mapheight;
        this.mapweight=mapweight;

    }

    public double scaleX(double tmp){//Une fonction qui met a l'echelle
        return tmp/(this.mapweight)*(this.windowweight-30)+15;
    }

    public double scaleY(double tmp){//Une fonction qui met a l'echelle
        return tmp/(this.mapheight)*(this.windoheight-30)+15;
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
            road.getChildren().add(line);

        }

    }
    public void drawcar(NetWork map){// Fonction de dessin des voitures
        this.car= new Group();//groupe des voitures
        this.getChildren().add(car);
        for(Road R:map.getRoads()){//On parcourt les route
            double roadangle=Math.atan(-R.getMatrixRepresentation()[1]/R.getMatrixRepresentation()[0]);//calcule angle de la route

            for(ArrayList<Voiture>Voie : R.getVoiesAller()){//parcourt les voie

                for(Voiture Voit:Voie){

                    Circle cercle = new Circle();//on dessine un cercle pour chaque voiture
                    cercle.setCenterX(scaleX(Math.cos(roadangle)*Voit.getPositionActuelle()+R.getStart().x));
                    cercle.setCenterY(scaleY(-Math.sin(roadangle)*Voit.getPositionActuelle()+R.getStart().y));
                    cercle.setRadius(4);
                    cercle.setFill(Color.BLUE);
                    car.getChildren().add(cercle);
                }

            }
        }

    }


    public void removecar(){
        this.getChildren().remove(car);
    }


}