package generation;

import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        //test unitaire valides
        City lille = new City(2,2,"Lille");
        City tourcoing = new City(2,-2,"Tourcoing");
        City roubaix = new City (-2,2, "Roubaix");
        City halluin = new City (-2,-2,"Halluin");

        NetWork map = null;

        try {
            map = new NetWork(true , lille,tourcoing,roubaix,halluin);
        } catch (Exception e) {
            e.printStackTrace();
        }

        map.print();
    }
}
