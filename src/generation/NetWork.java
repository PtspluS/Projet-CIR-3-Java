package generation;

import java.util.ArrayList;

public class NetWork {
    private ArrayList<City> cities = new ArrayList<>();
    private ArrayList<Intersection> cross = new ArrayList<>();
    private ArrayList<Road> roads = new ArrayList<>();

    private double sizeX;
    private double sizeY;

    public NetWork (){
        this.sizeX = 0;
        this.sizeY = 0;
    }

    public NetWork(double x, double y){
        this.sizeX = x;
        this.sizeY = y;
    }

    public NetWork(City city){
        this.cities.add(city);
        this.sizeX = city.getX();
        this.sizeY = city.getY();
    }

    public NetWork(City city[]){
        for (City c: city  ) {
            this.cities.add(c);
        }
    }

    public NetWork (City city[], boolean autoCompletRoads){//genere une carte ou chaque ville est reliee a chaque autre ville
        for (City c: city  ) {
            this.cities.add(c);
        }
        if(autoCompletRoads){
            int nbRoad = 0;
            for (City a : this.cities ) {
                for (City b : this.cities){
                    if(a!=b){
                        Road r = new Road(a,b);
                        r.setName("N"+Integer.toString(nbRoad));
                        this.roads.add(r);
                        nbRoad++;
                    }
                }
            }
        }
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }

    public ArrayList<Intersection> getCross() {
        return cross;
    }

    public void setCross(ArrayList<Intersection> cross) {
        this.cross = cross;
    }

    public ArrayList<Road> getRoads() {
        return roads;
    }

    public void setRoads(ArrayList<Road> roads) {
        this.roads = roads;
    }

    public double getSizeX() {
        return sizeX;
    }

    public void setSizeX(double sizeX) {
        this.sizeX = sizeX;
    }

    public double getSizeY() {
        return sizeY;
    }

    public void setSizeY(double sizeY) {
        this.sizeY = sizeY;
    }

    public void addRoad(City a, City b){
        try{
            City start = this.cities.get(this.cities.indexOf(a));
            City end = this.cities.get(this.cities.indexOf(b));
            Road r = new Road(start,end);
            this.roads.add(r);
            this.cities.get(this.cities.indexOf(start)).addRoad(r);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addRoad(City a, City b,String name){
        try{
            City start = this.cities.get(this.cities.indexOf(a));
            City end = this.cities.get(this.cities.indexOf(b));
            Road r = new Road(start,end);
            r.setName(name);
            this.roads.add(r);
            this.cities.get(this.cities.indexOf(start)).addRoad(r);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addRoad(String a, String b, String name){
        try{
            City start = new City();
            City end = new City();
            for(City c : this.cities){
                if(c.getName()==a){
                    start.setName(c.getName());
                    start.setX(c.getX());
                    start.setY(c.getY());
                    start.setRoads(c.getRoads());
                }
                else if(c.getName()==b){
                    end.setName(c.getName());
                    end.setX(c.getX());
                    end.setY(c.getY());
                    end.setRoads(c.getRoads());
                }
            }
            Road r = new Road(start,end);
            this.roads.add(r);
            this.cities.get(this.cities.indexOf(start)).addRoad(r);
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    public void print(){
        System.out.println("This network is composed by "+this.cities.size()+" cities which are :");
        for (City c : this.cities){
            System.out.print("\t\t\t");
            c.print();
        }
        System.out.println("The network have "+this.roads.size()+" roads which are :");
        for(Road r : this.roads){
            System.out.print("\t");
            r.print();
        }
    }
}
