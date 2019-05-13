package generation;

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

    public NetWork (boolean autoCompletRoads, City ... city){//genere une carte ou chaque ville est reliee a chaque autre ville
        double xMax = 0, yMax = 0;
        for (City c: city  ) {
            if(c.getX()>xMax){
                    xMax = c.getX();
                }
                if(c.getY()>yMax){
                    yMax = c.getY();
                }
            this.cities.add(c);
        }
        this.sizeX = xMax;
        this.sizeY = yMax;
        if(autoCompletRoads){
            int nbRoad = 0;
            for (City a : this.cities ) {
                for (City b : this.cities){
                    if(a!=b) {
                        if (!areLink(a,b)) {
                            int tmp=(int)(Math.random()*3);
                            Road r;
                           switch (tmp){
                               case 0:
                                   r= new Road(a, b, Road.TypeRoute.DEPARTEMENTALE );
                                   break;

                               case 1:
                                   r= new Road(a, b, Road.TypeRoute.NATIONALE );
                                   break;

                               default:
                                   r= new Road(a, b, Road.TypeRoute.AUTOROUTE );
                                   break;

                            }

                            r.setName("N" + nbRoad);
                            this.roads.add(r);
                            nbRoad++;
                        }
                    }
                }
            }
            for (Road a : this.roads){
                for ( Road b : this.roads) {
                    if (a != b) {
                        this.addNewIntersection(a, b);
                    }
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
            m1 = a.getEquationCarthesienneReduite()[0] / a.getEquationCarthesienneReduite()[1];
            c1 = a.getEquationCarthesienneReduite()[2]/a.getEquationCarthesienneReduite()[1];
        } else {
             m1 = a.getEquationCarthesienneReduite()[2]/ a.getEquationCarthesienneReduite()[0];
             c1 = a.getEquationCarthesienneReduite()[2];
        }
        if(b.getEquationCarthesienneReduite()[1] != 0) {
            m2 = b.getEquationCarthesienneReduite()[0] / b.getEquationCarthesienneReduite()[1];
            c2 = b.getEquationCarthesienneReduite()[2] / b.getEquationCarthesienneReduite()[1];
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
                    i.addRoad(a);
                    i.addRoad(b);
                    return i;
                }
            } else {
                return null;
            }
        }
        return null;
    }

    private void addNewIntersection (Road a, Road b){
        Intersection i;
        boolean inTab = false;
        if (this.possibleNewIntersection(a,b)!=null){
            i = this.possibleNewIntersection(a,b);
            i.setName("Intersection");
            if(i.getX() != NaN && i.getY() != NaN) {
                if(this.cross.size()==0){
                    this.cross.add(i);
                }
                for(Intersection elt : this.cross){
                    if(i.equals(elt)){//si les intersections sont confondus alors on rajoute juste les routes sur celle qui existe deja
                        elt.addRoad(a);
                        elt.addRoad(b);
                        inTab = true;
                    }
                }
                if(!inTab){
                    this.cross.add(i);
                }
            }
        }
    }

    private void addNewRoad(City a, City b, Road.TypeRoute t){//verifie que la route ne croise pas une autre route et si c'est le cas genere le croisement
        City start = this.cities.get(this.cities.indexOf(a));
        City end = this.cities.get(this.cities.indexOf(b));
        Road r = new Road(start,end,t);
        this.roads.add(r);
        this.cities.get(this.cities.indexOf(start)).addRoad(r);
        for (Road road : this.roads) {//check if there is a intersection between 2 roads
            this.addNewIntersection(r, road);
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
