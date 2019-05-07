package generation;

import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        //test unitaire valides
        City lille = new City(1,1,"Lille");
        City tourcoing = new City(1,2,"Tourcoing");
        City roubaix = new City (2,2, "Roubaix");
        City halluin = new City (2,1,"Halluin");

        NetWork map = null;

        map = new NetWork(true , lille,tourcoing,roubaix,halluin);
        map.print();

    }
}
