package generation;

import org.junit.Test;
import sun.nio.ch.Net;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class NetWorkTest {

    @Test
    public final void createCityTest(){
        double x = 1, y = 1;
        String name = "Lille";
        City lille = new City(x,y,name);

        if(lille == null){
            fail("City not created");
        }
    }

    @Test
    public final void createNetworkWithoutIntersectionTest(){
        double x = 1, y = -1;
        String name [] = {"Lille", "Tourcoing", "Roubaix"};
        ArrayList<City> cts = new ArrayList<>();

        for(String str : name){
            City c = new City(x--,y++,str);
            cts.add(c);
        }

        if(cts.size() != name.length){
            fail("Not all the city are implemented");
        } else {
            NetWork map = new NetWork(cts);
            if(map == null){
                fail("No network implemented");
            } else {
                for (City a : map.getCities()){
                    for (City b : map.getCities()){
                        if (a != b){
                            map.addRoad(a,b, Road.TypeRoute.NATIONALE);
                        }
                    }
                }
                if (map.getRoads().size() != cts.size()*(cts.size()-1)){
                    fail("Not all the roads are implemented");
                } else if (map.getCross().size() != 0) {
                    fail("False positive : Intersection detected");
                }
            }
        }

    }

    @Test
    public final void createNetworkWithAutoTemplateOneIntersectionTest(){
        City lille = new City(1,1,"Lille");
        City tourcoing = new City (1,2,"Tourcoing");
        City roubaix = new City(2,2,"Roubaix");
        City halluin = new City (2,1,"Halluin");

        final int nbCity = 4;

        NetWork map = new NetWork(true,lille,tourcoing,roubaix,halluin);

        if(map == null){
            fail("No network implemented");
        } else if (map.getRoads().size() != (nbCity-1)*2){
            fail("Not all the roads are implemented. "+map.getRoads().size()+" roads are implemented.");
        } else if (map.getCross().size() != 1){
            fail("False positive : not only one intersection detected ("+map.getCross().size()+" intersections detected)");
        }

        Intersection i = new Intersection(1.5,1.5);

        if(map.getCross().get(0).getX() != i.getX() && map.getCross().get(0).getY() != i.getY()){
            fail("Intersection not a the true position");
        }

    }

    @Test
    public final void exportFile(){
        City lille = new City(2,2,"Lille");
        City tourcoing = new City(2,1,"tourcoing");

        final String filePathName = "unitTest.ser";

        NetWork map = new NetWork(true, lille, tourcoing);

        Export.exportNetwork(map,filePathName);

        NetWork ret = Export.importNetwork(filePathName);

        if(ret == null){
            fail("ret is null");
        } else {
            for (int i = 0; i < map.getRoads().size(); i++) {
                if(!map.getRoads().get(i).equals(ret.getRoads().get(i))){
                    fail("ret not equals to map");
                }
            }
        }

        map.print();
        System.out.println("-------------------------");
        ret.print();
    }
}