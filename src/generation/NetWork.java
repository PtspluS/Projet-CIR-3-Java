import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static java.lang.Double.NaN;

public class NetWork implements java.io.Serializable{
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

    public NetWork(City ... city){
        for (City c: city  ) {
            this.cities.add(c);
        }
    }

    public NetWork (ArrayList<City> city){
        this.cities.addAll(city);
    }

    public NetWork (boolean autCompleteRoads, ArrayList<City> cities) {
        double xMax = 0;
        double yMax = 0;
        double ratioDistance = 1000;//distance qui fait que l'on joint deux intersections
        int nbRoad = 0;
        this.cities = cities;
        for (City c : this.cities) {//permet de trouver la taille max de la carte en x et y
            if (c.getX() > xMax) {
                xMax = c.getX();
            }
            if (c.getY() > yMax) {
                yMax = c.getY();
            }
        }
        this.sizeX = xMax;
        this.sizeY = yMax;
        if (autCompleteRoads) {//on lit toutes les villes entre elles
            for (City a : this.cities) {
                for (City b : this.cities) {
                    if (a != b) {
                        if (!areLink(a, b)) {
                            int tmp = (int) (Math.random() * 3);
                            Road r;
                            switch (tmp) {
                                case 0:
                                    r = new Road(a, b, Road.TypeRoute.DEPARTEMENTALE);
                                    break;

                                case 1:
                                    r = new Road(a, b, Road.TypeRoute.NATIONALE);
                                    break;

                                default:
                                    r = new Road(a, b, Road.TypeRoute.AUTOROUTE);
                                    break;

                            }

                            r.setName("N" + nbRoad);
                            this.roads.add(r);
                            nbRoad++;
                        }
                    }
                }
            }
        }

        boolean stillCrossings = false;

        do {
            stillCrossings = false;
            for (int i = 0; i < this.roads.size(); i++) {//cree toutes les intersections sans optimisation
                Road a = this.roads.get(i);
                for (int j = 0; j < this.roads.size(); j++) {
                    Road b = this.roads.get(j);
                    if (a != b) {
                        if (this.possibleNewIntersection(a, b) != null) {
                            stillCrossings = true;
                            break;
                        }
                    }
                }
                if (stillCrossings) break;
            }
        }while(stillCrossings);


        //verifie si une route peut pointer vers une nouvelle inteersections plus proche pour limiter les intersections
        /*for (int a = 0; a<this.roads.size(); a++) {
            Road r = this.roads.get(a);
            for (Intersection i : this.cross){
                if(distancePointLine(r,i)<ratioDistance){
                    updateIntersection(r);
                    r.setEnd(i);
                    Road tps = new Road(i,r.getEnd(),r.getType());
                    tps.setName("N"+nbRoad);
                    this.roads.add(tps);
                    nbRoad++;
                }
            }
        }*/
    }

    private double distancePointLine (Road r, Intersection i){//donne la distance entre un point et une droite
        double a=0,b=0,c=0,x=0,y=0;
        //coeff de r
        a = r.getEquationCarthesienneReduite()[0];
        b = r.getEquationCarthesienneReduite()[1];
        c = r.getEquationCarthesienneReduite()[2];

        //coordonnee de i
        x = i.getX();
        y = i.getY();
        double equation = Math.abs(a*x+b*y+c)/Math.sqrt(Math.pow(a,2)+Math.pow(b,2));

        return equation;
    }

    private void updateIntersection(Road r){//check et supprime les routes qui sont dans des intersections, supprime l'intersection si elle ne contient qu'une route
        for(Intersection i : this.cross){
            if(i.roads.contains(r)){
                if(i.roads.size()==2){
                    this.cross.remove(i);
                } else {
                    i.roads.remove(r);
                }
            }
        }
    }

    private boolean areLink(Node a, Node b){
        for (Road r: this.roads ) {
            if ((r.getStart()==(a) || r.getEnd()==(a)) && (r.getStart()==(b) || r.getEnd()==(b))){
                return true;
            }
        }
        return false;
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

    private Intersection possibleNewIntersection (Road a, Road b) {
        double m1 =0, m2 = 0, c1 =0, c2=0;
        if(a.getEquationCarthesienneReduite()[1] != 0) {
            m1 = - a.getEquationCarthesienneReduite()[0] / a.getEquationCarthesienneReduite()[1];
            c1 = - a.getEquationCarthesienneReduite()[2] / a.getEquationCarthesienneReduite()[1] ;
        } else {
             m1 = a.getEquationCarthesienneReduite()[2]/ a.getEquationCarthesienneReduite()[0];
             c1 = a.getEquationCarthesienneReduite()[2];
        }
        if(b.getEquationCarthesienneReduite()[1] != 0) {
            m2 = - b.getEquationCarthesienneReduite()[0] / b.getEquationCarthesienneReduite()[1];
            c2 = - b.getEquationCarthesienneReduite()[2] / b.getEquationCarthesienneReduite()[1];
        } else {
             m2 = b.getEquationCarthesienneReduite()[2]/ b.getEquationCarthesienneReduite()[0];
             c2 = b.getEquationCarthesienneReduite()[2];
        }

        if(m1 == m2){
            return null;
        }
        else {
            double x = (c2-c1)/(m1-m2);
            if(m1-m2 == 0){
                x = c2-c1;
            }
            double y = m1*x+c1;
            x= -x;

            Intersection i = new Intersection(x, y);

            if(!i.equals(a.getStart()) && !i.equals(a.getEnd()) && !i.equals(b.getStart()) && !i.equals(b.getEnd())) {//on verifie que l'interection n'est pas un ville
                double [] xTab = {a.getStart().getX(),a.getEnd().getX(),b.getStart().getX(),b.getEnd().getX()};
                double [] yTab = {a.getStart().getY(),a.getEnd().getY(),b.getStart().getY(),b.getEnd().getY()};

                if(x> arrayMin(xTab) && x< arrayMax(xTab) && y>arrayMin(yTab) && y<arrayMax(yTab)) {//on verifie que l'intersection se trouve bien sur les routes entre les villes et pas sur les droites qui les portent
                    a.getEnd().removeRoad(a);
                    b.getEnd().removeRoad(b);
                    a.getStart().removeRoad(a);
                    b.getStart().removeRoad(b);

                    this.addNewRoad(a.getEnd(), i, a.getType());
                    this.addNewRoad(b.getEnd(), i, b.getType());
                    this.addNewRoad(a.getStart(), i, a.getType());
                    this.addNewRoad(b.getStart(), i, b.getType());

                    this.roads.remove(a);
                    this.roads.remove(b);

                    this.cross.add(i);

                    return i;
                }
            } else {
                return null;
            }
        }
        return null;
    }

    private void addNewRoad(Node a, Node b, Road.TypeRoute t){//verifie que la route ne croise pas une autre route et si c'est le cas genere le croisement
        Road r = new Road(a,b,t);
        this.roads.add(r);
        a.addRoad(r);
        b.addRoad(r);

        for (Road road : this.roads) {//check if there is a intersection between 2 roads
            this.possibleNewIntersection(r, road);
        }
    }

    public void addRoad(City a, City b,Road.TypeRoute t){
        try{
            this.addNewRoad(a,b,t);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void print(){
        System.out.println("This network is composed by "+this.cities.size()+" cities which are :");
        for (City c : this.cities){
            System.out.print("\t\t\t");
            c.print();
        }
        System.out.println("The network had "+this.roads.size()+" roads which are :");
        for(Road r : this.roads){
            System.out.print("\t");
            r.print();
        }

        System.out.println("This network had "+this.cross.size()+" intersection which are :");
        for (Intersection i : this.cross){
            System.out.print("\t");
            i.print();
        }
        System.out.println("This network had "+this.cross.size()+" intersection");
    }

    public static double arrayMin (double [] tab){
        double min = 0;
        boolean f = false;
        for (double elt: tab) {
            if(!f){
                min = elt;
                f = true;
            }else{
                min = Math.min(min,elt);
            }
        }
        return min;
    }

    public static double arrayMax (double [] tab){
        double max = 0;
        boolean f = false;
        for (double elt: tab) {
            if(!f){
                max = elt;
                f = true;
            }else{
                max = Math.max(max,elt);
            }
        }
        return max;
    }
}
