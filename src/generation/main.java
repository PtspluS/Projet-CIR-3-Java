package generation;

import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        //test unitaire validés
        City lille = new City(2,1,"Lille");
        City tourcoing = new City(3,-1,"Tourcoing");
        Road N1 = new Road(lille,tourcoing);

        lille.print();
        tourcoing.print();

        N1.print();
        N1.printMatrixRepresentation();

        City listCities []= {lille, tourcoing};

        NetWork map = new NetWork(listCities,true);
        map.print();
    }
}
