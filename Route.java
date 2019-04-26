import java.util.ArrayList;

public class Route {
    // Les valeurs de vitesses seront estimees en km/h

    // Constantes
    private final double limitationVitesse; // Limitation de vitesse selon le type de route
    private final double distanceSecurite; // Distance a conserver entre les vehicules
    private final double longueur; // Longueur de la route (en km)

    // Tableaux des voies contenant les voitures
    private ArrayList< ArrayList< Voiture > > voiesAller = new ArrayList<>(); // Voies de la route dans le sens de l'aller
    private ArrayList< ArrayList< Voiture > > voiesRetour = new ArrayList<>(); // Voies de la route dans le sens du retour

    public Route(String type, double longueur){
        this.longueur = longueur;

        if(type == "Autoroute"){ // Nous fournissons 3 voies pour une autoroute dans les deux sens
            this.limitationVitesse = 130;
            this.distanceSecurite = 80;

            ArrayList< Voiture > voie1A = new ArrayList<>();
            ArrayList< Voiture > voie2A = new ArrayList<>();
            ArrayList< Voiture > voie3A = new ArrayList<>();
            ArrayList< Voiture > voie1R = new ArrayList<>();
            ArrayList< Voiture > voie2R = new ArrayList<>();
            ArrayList< Voiture > voie3R = new ArrayList<>();

            this.voiesAller.add(voie1A);
            this.voiesAller.add(voie2A);
            this.voiesAller.add(voie3A);
            this.voiesRetour.add(voie1R);
            this.voiesRetour.add(voie2R);
            this.voiesRetour.add(voie3R);
        }else if(type == "Nationale"){ // Nous fournissons 2 voies pour une nationale dans les deux sens
            this.limitationVitesse = 110;
            this.distanceSecurite = 70;

            ArrayList< Voiture > voie1A = new ArrayList<>();
            ArrayList< Voiture > voie2A = new ArrayList<>();
            ArrayList< Voiture > voie1R = new ArrayList<>();
            ArrayList< Voiture > voie2R = new ArrayList<>();

            this.voiesAller.add(voie1A);
            this.voiesAller.add(voie2A);
            this.voiesRetour.add(voie1R);
            this.voiesRetour.add(voie2R);
        }else{ // Nous fournissons 1 voies pour une departementale dans les deux sens
            this.limitationVitesse = 80;
            this.distanceSecurite = 55;

            ArrayList< Voiture > voie1A = new ArrayList<>();
            ArrayList< Voiture > voie1R = new ArrayList<>();

            this.voiesAller.add(voie1A);
            this.voiesRetour.add(voie1R);
        }

    }

    public boolean ajouterVoitureAller(Voiture voiture, int voie){
        if(this.voiesAller.get(voie).get(voie).getPositionActuelle() > this.distanceSecurite){
            this.voiesAller.get(voie).add(voiture);
            return true;
        }
        return false;
    }

    public boolean ajouterVoitureRetour(Voiture voiture, int voie){
        if(this.voiesRetour.get(voie).get(voie).getPositionActuelle() > this.distanceSecurite){
            this.voiesRetour.get(voie).add(voiture);
            return true;
        }
        return false;
    }

    public void avancerFrame(double temps){

    }
}
