package generation;
import generation.Road;

import java.util.TreeMap;

public class Intersection extends Node {

    private TypeIntersection typeIntersection;

    public enum TypeIntersection{
        PRIORITY, TRAFICCIRCLE, LIGHT, RIGHTPRIORITY
    }

    public Intersection.TypeIntersection getTypeIntersection() {
        return typeIntersection;
    }
    //je ne suis pas dur de l'utilite du seter donc dans le doute je le met e commentaire, il sera enleve si on en a pas besoin
    /*
    public void setTypeIntersection(Intersection.typeIntersection typeIntersection) {
        this.typeIntersection = typeIntersection;
    }*/

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
        //this.sortList();
        this.typeIntersection = this.updateStatue();
    }

    private double angleRoad(Road r){//donne l'angle entre une route et une intersection
        double ima = r.getStart().getY()-this.y;
        double real = r.getStart().getX() - this.x;
        double abs = Math.sqrt(Math.pow(ima,2)+Math.pow(real,2));

        double theta = Math.atan(ima/(real+abs));

        return theta;
    }

    private void sortList(){
        TreeMap <Double, Road> map = new TreeMap<>();//map auto triee
        for (Road r: this.roads ) {//pour chaque route on calcule l'angle entre cette derniere et l'intersection
            Double theta = this.angleRoad(r);
            map.put(theta,r);
        }
        for( int i =0 ; i<this.roads.size(); i++){
            this.roads.set(i,(Road)map.values().toArray()[i]);//on vient entierement modifier la liste de routes pour qu'elles soient triee par ordre de prio a droite
        }
    }

    private TypeIntersection updateStatue (){//change le type d'intersection
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

    //travail ici Antoine
}
