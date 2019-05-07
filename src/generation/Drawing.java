









package generation;

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

    public void drawcity(NetWork map, GraphicsContext gc){
        for(City c:map.getCities()){
            //gc.fillOval(scaleX(c.getX()),scaleY(c.getY()),10,10);
            Circle cercle = new Circle();
            cercle.setCenterX(scaleX(c.getX()));
            cercle.setCenterY(scaleY(c.getY()));
            cercle.setRadius(10);
            cercle.setFill(Color.YELLOW);
            cercle.setStroke(Color.ORANGE);
            cercle.setStrokeWidth(5);
            this.getChildren().add(cercle);


        }

    }

    public void drawroad(NetWork map, GraphicsContext gc){
        for(Road r:map.getRoads()){
            // gc.strokeLine(scaleX(r.getStart().getX()),scaleY(r.getStart().getY()),scaleX(r.getEnd().getX()),scaleY(r.getEnd().getY()));
            Line line = new Line();
            line.setStartX(scaleX(r.getStart().getX()));
            line.setStartY(scaleY(r.getStart().getY()));
            line.setEndX(scaleX(r.getEnd().getX()));
            line.setEndY(scaleY(r.getEnd().getY()));
            this.getChildren().add(line);

        }

    }
    public void drawcar(NetWork map, GraphicsContext gc){
        for(Road R:map.getRoads()){
            double roadangle=Math.atan(-R.getMatrixRepresentation()[1]/R.getMatrixRepresentation()[0]);

            for(ArrayList<Voiture>Voie : R.getVoiesAller()){

                for(Voiture Voit:Voie){
//gc.fillOval(scaleX(Math.cos(roadangle)*Voit.getPositionActuelle()),scaleY(-Math.sin(roadangle)*Voit.getPositionActuelle()),10,10);
                    Circle cercle = new Circle();
                    cercle.setCenterX(scaleX(Math.cos(roadangle)*Voit.getPositionActuelle()));
                    cercle.setCenterY(scaleY(-Math.sin(roadangle)*Voit.getPositionActuelle()));
                    cercle.setRadius(2);
                    cercle.setFill(Color.BLUE);
                    this.getChildren().add(cercle);
                }

            }
        }

    }


}