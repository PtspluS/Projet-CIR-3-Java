package generation;
import java.util.ArrayList;

public class City extends Node implements java.io.Serializable{


    public City (){
        this.x = Math.random() * (750);
        this.y = Math.random() *(750);
        this.name = Double.toString(Math.random()*100);
    }

    public City (double x, double y){
        this.x = x;
        this.y = y;
        this.name = Double.toString(Math.random()*100);
    }

    public City (double x, double y, String name){
        this.x = x;
        this.y = y;
        this.name = name;
    }
}
