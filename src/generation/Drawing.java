









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

public class Drawing extends Parent {
    private int windowweight;
    private int windoheight;
    private double mapweight;
    private double mapheight;
    private Group car;

    Drawing(int windowweight,int windoheight,double mapweight,double mapheight){
        this.windoheight=windoheight;
        this.windowweight=windowweight;
        this.mapheight=mapheight;
        this.mapweight=mapweight;

    }

    public double scaleX(double tmp){
        return tmp/(this.mapweight)*(this.windowweight-30)+15;
    }

    public double scaleY(double tmp){
        return tmp/(this.mapheight)*(this.windoheight-30)+15;
    }

    public void drawcity(NetWork map){
        Group city = new Group();
        this.getChildren().add(city);
        for(City c:map.getCities()){

            Circle cercle = new Circle();
            cercle.setCenterX(scaleX(c.getX()));
            cercle.setCenterY(scaleY(c.getY()));
            cercle.setRadius(10);
            cercle.setFill(Color.YELLOW);
            cercle.setStroke(Color.ORANGE);
            cercle.setStrokeWidth(5);
            city.getChildren().add(cercle);


        }

    }

    public void drawroad(NetWork map){
        Group road= new Group();
        this.getChildren().add(road);
        for(Road r:map.getRoads()){

            Line line = new Line();
            line.setStartX(scaleX(r.getStart().getX()));
            line.setStartY(scaleY(r.getStart().getY()));
            line.setEndX(scaleX(r.getEnd().getX()));
            line.setEndY(scaleY(r.getEnd().getY()));
            road.getChildren().add(line);

        }

    }
    public void drawcar(NetWork map){
        this.car= new Group();
        this.getChildren().add(car);
        for(Road R:map.getRoads()){
            double roadangle=Math.atan(-R.getMatrixRepresentation()[1]/R.getMatrixRepresentation()[0]);

            for(ArrayList<Voiture>Voie : R.getVoiesAller()){

                for(Voiture Voit:Voie){

                    Circle cercle = new Circle();
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