package generation;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

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

    public void drawcity(NetWork map){
        for(City c:map.getCities()){
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

    public void drawroad(NetWork map){
        for(Road r:map.getRoads()){
            Line line = new Line();
            line.setStartX(scaleX(r.getStart().getX()));
            line.setStartY(scaleY(r.getStart().getY()));
            line.setEndX(scaleX(r.getEnd().getX()));
            line.setEndY(scaleY(r.getEnd().getY()));
            this.getChildren().add(line);

        }

    }
    public void drawcar(){

    }


}
