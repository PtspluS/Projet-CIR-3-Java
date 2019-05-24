package generation;
import generation.Road;

import java.util.ArrayList;

// Noeud de notre carte, ville ou intersection
public abstract class Node extends Infrastructure implements java.io.Serializable{
    protected ArrayList<Road> roads = new ArrayList<>();

    public ArrayList<Road> getRoads() {
        return roads;
    }

    public void setRoads(ArrayList<Road> roads) {
        this.roads = roads;
    }

    public void addRoad(Road road) {
        this.roads.add(road);
        System.out.print("New road added : ");
    }

    public void removeRoad(Road road) {
        try {
            this.roads.remove(road);
            System.out.println("Road perfectly remove");
        } catch (Exception e) {
            System.out.println("Impossible to remove the road from the list");
        }

    }
}
