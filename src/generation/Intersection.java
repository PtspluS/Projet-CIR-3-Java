package generation;
import generation.Node;
import generation.Road;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;

public class Intersection extends Node {

    private TypeIntersection typeIntersection;

    private ArrayList<Double> angles;

    public enum TypeIntersection{
        PRIORITY, TRAFICCIRCLE, LIGHT, RIGHTPRIORITY
    }

    public Intersection.TypeIntersection getTypeIntersection() {
        return typeIntersection;
    }

    public Intersection (double x, double y){
        this.x = x;
        this.y = y;
    }

    @Override
    public void print(){
        System.out.println("Position of "+ this.getName() +" = ("+this.x+","+this.y+") => type of intersection = "+this.typeIntersection);
        System.out.print("\t");
        for (Road r : this.getRoads()) {
            r.print();
        }
    }

    @Override
    public void addRoad(Road r){
        super.addRoad(r);
        this.sortList();
        this.typeIntersection = this.updateStatus();
    }

    public double angleRoad(Road r){//donne l'angle entre une route et une intersection
        Node otherPoint = (r.getEnd().equals(this)) ? r.getStart() : r.getEnd();

        double ima = otherPoint.getY()-this.y;
        double real = otherPoint.getX() - this.x;

        if(real == 0 && ima > 0) return Math.PI/2;
        if(real == 0 && ima < 0) return 3*Math.PI/2;
        if(real > 0 && ima == 0) return 0;
        if(real < 0 && ima == 0) return Math.PI;

        if(real > 0 && ima > 0) return Math.atan(ima/real);
        if(real < 0 && ima > 0) return Math.PI - Math.atan(ima/(-1*real));
        if(real < 0 && ima < 0) return Math.PI + Math.atan(ima/real);
        if(real > 0 && ima < 0) return -1 * Math.atan((-1*ima)/real);

        return 0;
    }

    public void sortList(){
        for (int i = 0; i < this.roads.size(); i++) {//pour chaque route on calcule l'angle entre cette derniere et l'intersection et nous les trions par ce biais (des plus grands angles aux plus petits pour les priorites a droite)
            for(int j = 0; j < this.roads.size() - i - 1; j++){
                if(this.angleRoad(this.roads.get(j)) > this.angleRoad(this.roads.get(j+1))){
                    Collections.swap(this.roads, j, j+1);
                }
            }
        }
    }

    private TypeIntersection updateStatus (){//change le type d'intersection
        if(this.roads.size() == 4){
            if(this.roads.get(0).getType()==this.roads.get(1).getType()){//si les deux routes sont de meme type alors ca sera une priorite a droite
                return this.typeIntersection.RIGHTPRIORITY;
            }else {//si les deux routes sont de type diff alors ca sera une priorite
                return this.typeIntersection.PRIORITY;
            }
        } else if(this.roads.size()>2){
            for (int i = 0; i<this.roads.size()-1;i++){
                if(this.roads.get(i).getType()!=this.roads.get(i+1).getType()){//si il y a au moins deux types de routes diff alors ca sera un feu
                    return this.typeIntersection.LIGHT;
                }
            }
            return this.typeIntersection.TRAFICCIRCLE;//sinon ca sera un rond point
        }

        return this.typeIntersection.LIGHT;//par defaut ca sera des feux
    }
}
