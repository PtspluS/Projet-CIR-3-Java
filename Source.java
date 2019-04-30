import java.util.ArrayList;

public class Source {
    public static void main(String[] args){
        Route maRoute = new Route(Route.TypeRoute.AUTOROUTE, 1000);

        Voiture v1 = new Voiture(8.4,60);
        Voiture v2 = new Voiture(16.8,200);

        maRoute.debugAjouterAller(v1, 0, 500);
        maRoute.debugAjouterAller(v2, 0, 499);

        for(int i = 0; i < 500; i++){
            maRoute.avancerFrame(0.5);
            System.out.println("Frame: " + i);

            System.out.println("Pos: " + v1.getPositionActuelle() + " , Vit: " + v1.getVitesseActuelle() + " , Acc: " + v1.getAccelerationActuelle());
            System.out.println("Pos: " + v2.getPositionActuelle() + " , Vit: " + v2.getVitesseActuelle() + " , Acc: " + v2.getAccelerationActuelle());
        }
    }
}
