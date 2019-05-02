package generation;

public class Intersection extends Node {

    public Intersection (double x, double y){
        this.x = x;
        this.y = y;
    }

    @Override
    public void print(){
        System.out.println("Position of "+ this.getName() +" = ("+this.x+","+this.y+")");
        System.out.print("\t");
        for (Road r : this.getRoads()) {
            r.print();
        }
    }
}
