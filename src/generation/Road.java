package generation;

import java.util.ArrayList;

public class Road extends Infrastructure implements java.io.Serializable{
    private Node start;
    private Node end;
    private double[] matrixRepresentation ;
    private double [] equationCarthesienneReduite;//equation carthesienne du style ax+by+c = 0 => a/c x + b/c y = 0

    public Road (Node start, Node end, TypeRoute type) {
        this.start = start;
        this.end = end;
        this.matrixRepresentation = new double[]{end.x - start.x, end.y - start.y};
        double a = -end.x+start.x;
        double b = end.y - start.y;
        double c = -a*start.x - b*start.y;
        this.equationCarthesienneReduite = new double []{a,b,c};

        this.longueur = Math.sqrt(Math.pow((a),2)+Math.pow(b,2));
        this.type = type;

        if(type == TypeRoute.AUTOROUTE){ // Nous fournissons 3 voies pour une autoroute dans les deux sens
            this.limitationVitesse = 130;
            this.distanceSecurite = 0.100;

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
        }else if(type == TypeRoute.NATIONALE){ // Nous fournissons 2 voies pour une nationale dans les deux sens
            this.limitationVitesse = 110;
            this.distanceSecurite = 0.70;

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
            this.distanceSecurite = 0.55;

            ArrayList< Voiture > voie1A = new ArrayList<>();
            ArrayList< Voiture > voie1R = new ArrayList<>();

            this.voiesAller.add(voie1A);
            this.voiesRetour.add(voie1R);
        }
    }

    public Node getStart() {
        return start;
    }

    public void setStart(Node start) {
        this.start = start;
    }

    public Node getEnd() {
        return end;
    }

    public void setEnd(Node end) {
        this.end = end;
    }

    public double[] getMatrixRepresentation() {
        return matrixRepresentation;
    }

    public double equationCartesienneY (double x){// y = ...
        return (x-this.start.x)*this.matrixRepresentation[1]/this.matrixRepresentation[0]+this.start.y;
    }

    public double equationCartesienneX (double y){// x = ...
        return -((y-this.start.y)*this.matrixRepresentation[0])/this.matrixRepresentation[1]+this.start.x;
    }

    public void printMatrixRepresentation (){
        System.out.println("("+this.matrixRepresentation[0]+","+this.matrixRepresentation[1]+")");
    }

    @Override
    public void print(){
        System.out.println("This road called : "+this.name+" start at "+ this.start.name+" and end at "+ this.end.name);
        System.out.print("\t\t\t");
        this.start.print();
        System.out.print("\t\t\t");
        this.end.print();
        System.out.println("Matrix representation : ("+this.matrixRepresentation[0]+","+this.matrixRepresentation[1]+")");
    }

    public double[] getEquationCarthesienneReduite() {
        return equationCarthesienneReduite;
    }

    public void setEquationCarthesienneReduite(double[] equationCarthesienneReduite) {
        this.equationCarthesienneReduite = equationCarthesienneReduite;
    }


    //partie d'antoine
    //si ca merde c'est ici
    // Les valeurs de vitesses seront estimees en km/h

    // Enumeration des types de routes
    public enum TypeRoute {
        DEPARTEMENTALE, NATIONALE, AUTOROUTE
    }

    // Constantes
    private final double limitationVitesse; // Limitation de vitesse selon le type de route
    private final double distanceSecurite; // Distance a conserver entre les vehicules
    private final double longueur; // Longueur de la route (en km)

    public double getDistanceSecurite() {
        return distanceSecurite;
    }

    public double getLongueur() {
        return longueur;
    }



    private final TypeRoute type; // Type de route

    // Tableaux des voies contenant les voitures
    // Indexs : 0 - Voie Lente, 1 - Voie Moyenne, 2 - Voie Rapide
    private ArrayList<ArrayList< Voiture > > voiesAller = new ArrayList<>(); // Voies de la route dans le sens de l'aller
    private ArrayList< ArrayList< Voiture > > voiesRetour = new ArrayList<>(); // Voies de la route dans le sens du retour

    // Infrastructures de depart et d'arrive
    // private Infrastructure infraDepart;
    // private Infrastructure infraArrive;

    // Getters

    public TypeRoute getType() {
        return this.type;
    }

    public ArrayList< ArrayList< Voiture > > getVoiesAller() { return voiesAller; }
    public ArrayList< ArrayList< Voiture > > getVoiesRetour() { return voiesRetour; }


    public void ajouterVoitureAller(Voiture voiture, int voie){ // Entree d'une voiture dans le sens de l'aller
        this.voiesAller.get(voie).add(0,voiture);
    }

    public void ajouterVoitureRetour(Voiture voiture, int voie){ // Entree d'une voiture dans le sens du retour
        this.voiesRetour.get(voie).add(0,voiture);
    }

    public int[] voituresAlentoursPosition(ArrayList< Voiture > voie, double position){ // Retourne les indexs des voitures situes avant et apres la position donnee dans la voie demandee
        int[] vide = {-1, -1}; // -1 == Aucune voiture
        if(voie.size() == 0) return vide; // Si la voie est vide

        for(int i = 0; i < voie.size(); i++){
            if(voie.get(i).getPositionActuelle() > position){
                if(i == 0){ // Si la premiere voiture est devant la position
                    int[] retour = {-1, i};
                    return retour;
                }else{ // Si la position est entre 2 voitures differentes
                    int[] retour = {i-1, i};
                    return retour;
                }
            }
        }
        // else

        // if(voie.size() != 0) // Si la derniere voiture est derriere la position
        int[] retour = {voie.size()-1, -1};
        return retour;
    }

    public boolean depassement(int indexVoiture, int indexVoie, ArrayList< ArrayList< Voiture > > voies){ // Nous verifions si nous pouvons depasser et nous le faisons au possible
        Voiture voiture = voies.get(indexVoie).get(indexVoiture);
        int[] encadrement = voituresAlentoursPosition(voies.get(indexVoie + 1), voiture.getPositionActuelle());
        ArrayList< Voiture > nouvelleVoie = voies.get(indexVoie + 1);

        boolean placeArriere = false; // Si la voiture de derriere est assez loin pour pouvoir depasse (2 * distanceSecurite)
        boolean placeAvant = false; // Si la voiture de devant est assez loin pour nous permettre de passer (1 * distanceSecurite)

        if(encadrement[0] == -1){
            placeArriere = true;
        }else if(nouvelleVoie.get(encadrement[0]).getPositionActuelle() < voiture.getPositionActuelle() - 2 * this.distanceSecurite){
            placeArriere = true;
        }
        if(encadrement[1] == -1){
            placeAvant = true;
        }else if(nouvelleVoie.get(encadrement[1]).getPositionActuelle() > voiture.getPositionActuelle() + this.distanceSecurite){
            placeAvant = true;
        }

        if(placeArriere && placeAvant){
            voies.get(indexVoie).remove(indexVoiture);
            if(encadrement[1] == -1){ // Si aucune voiture plus loin dans la voie, nous ajoutons la voiture a la fin de celle-ci
                nouvelleVoie.add(voiture);
            }else{ // Sinon, juste avant celle de devant ( son ancienne position dans la liste donc)
                nouvelleVoie.add(encadrement[1], voiture);
            }

            return true; // Reussite de depassement
        }
        return false; // Echec de depassement
    }

    public boolean rabattage(int indexVoiture, int indexVoie, ArrayList< ArrayList< Voiture > > voies){ // Nous verifions si nous pouvons nous rabattre et nous le faisons au possible
        Voiture voiture = voies.get(indexVoie).get(indexVoiture);
        int[] encadrement = voituresAlentoursPosition(voies.get(indexVoie - 1), voiture.getPositionActuelle());

        ArrayList< Voiture > nouvelleVoie = voies.get(indexVoie - 1);

        boolean placeArriere = false; // Si la voiture de derriere est assez loin pour pouvoir se rabattre (1 * distanceSecurite) (Nous allons techniquement plus vite)
        boolean placeAvant = false; // Si la voiture de devant est assez loin pour que cela soit utile de se rabattre (4 * distanceSecurite) (Il serait peu utile de se rabattre pour depasser des la prochaine frame, malgre tout, cela peut quand meme arriver)

        if(encadrement[0] == -1){
            placeArriere = true;
        }else if(nouvelleVoie.get(encadrement[0]).getPositionActuelle() < voiture.getPositionActuelle() - this.distanceSecurite){
            placeArriere = true;
        }
        if(encadrement[1] == -1){
            placeAvant = true;
        }else if(nouvelleVoie.get(encadrement[1]).getPositionActuelle() - distanceSecurite > voiture.getPositionActuelle() + voiture.distanceFreinMax()){
            placeAvant = true;
        }

        if(placeArriere && placeAvant){
            voies.get(indexVoie).remove(indexVoiture);
            if(encadrement[1] == -1){ // Si aucune voiture plus loin dans la voie, nous ajoutons la voiture a la fin de celle-ci
                nouvelleVoie.add(voiture);
            }else{ // Sinon, juste avant celle de devant ( son ancienne position dans la liste donc)
                nouvelleVoie.add(encadrement[1], voiture);
            }

            return true; // Reussite de rabattage
        }
        return false; // Echec de rabattage
    }

    public void acceleration(double temps, boolean retour){ // Fait avancer les voiture sur une voie en fonction des obstacles devant elles (autres vehicules et/ou infrastructures) // Si retour est false, on avance la voie d'aller et inversement
        Intersection.TypeIntersection endType = null; // Type de l'intersection a venir
        boolean endIsCity = false; // Est-ce que nous allons nous confronter a une ville?
        boolean ourPriority = false; // Dans le cas ou le type de l'intersection est une priorite, est-ce notre route qui a la priorite?

        Road followingRoad = null;

        Node endNode = null; // Noeud de fin (ville ou intersection)

        ArrayList< ArrayList<Voiture > > voie = null;
        if(retour){ // On choisit la voie a modifier, et ainsi son point d'arrivée
            voie = this.voiesRetour;
            endNode = this.start;
        }else{
            voie = this.voiesAller;
            endNode = this.end;
        }

        // Nous appliquons nos parametres selon les informations precedentes
        if(endNode instanceof City){
            endIsCity = true;
        }else{
            endType = ( (Intersection) endNode).getTypeIntersection();
            ArrayList<Road> tab = endNode.getRoads(); // Tableau des routes de l'intersection
            int tabsize = tab.size();

            for(int i = 0; i < tabsize; i++){
                if(tab.get(i) == this){
                    followingRoad = tab.get( (i+tabsize/2) % tabsize ); // Sur une intersection de 2 routes la route suivante est toujours ( i+2 )%4
                }
            }

            if(endType == Intersection.TypeIntersection.PRIORITY){
                ourPriority = true; // Si nous sommes une autoroute, nous avons obligatoirement la priorite
                if(this.type == TypeRoute.DEPARTEMENTALE){ // Si nous sommes une departementale, nous n'avons obligatoirement pas la priorite
                    ourPriority = false;
                }else if(this.type == TypeRoute.NATIONALE){ // Si nous sommes une nationale, nous n'avons la priorite que si les autres routes la croisant sont des autoroutes

                    for(int i = 0; i < tab.size(); i++){
                        if(tab.get(i).getType() == TypeRoute.AUTOROUTE){
                            ourPriority = false;
                            break;
                        }
                    }
                }
            }
        }

        for(int i = 0; i < voie.size(); i++){
            for(int j = voie.get(i).size() - 1; j >= 0; j--){
                Voiture voituret = voie.get(i).get(j);

                // Voiture la plus avancee de la voie
                if(j == voie.get(i).size() - 1) {

                    // Dans le cas ou nous arrivons a une intersection de type priorite ou une ville, nous ne ralentissons pas a condition que nous ayons la priorite
                    if(endIsCity || !endIsCity && endType == Intersection.TypeIntersection.PRIORITY && ourPriority) {

                        // Nous arrivons en fin de route, nous passons la voiture a la nouvelle voie si il y a de la place ( ou a la ville)
                        if (voituret.getPositionActuelle() + voituret.getVitesseActuelle() * temps / 3600 + voituret.getAccelerationActuelle() * Math.pow(temps / 3600, 2) / 2 >= this.longueur) {
                            if (endIsCity) {
                                // Notre voiture entre dans la ville, on l'enleve de notre route
                                voie.get(i).remove(voituret);

                            } else if (!endIsCity && endType == Intersection.TypeIntersection.PRIORITY && ourPriority) { // C'est une priorite et nous avons la priorite, nous ne nous arretons pas sauf outremesures
                                double distanceSupp = voituret.getPositionActuelle() + voituret.getVitesseActuelle() * temps / 3600 + voituret.getAccelerationActuelle() * Math.pow(temps / 3600, 2) / 2 - this.longueur;

                                // Si la route suivante est dans le sens du retour, et qu'il y a de la place
                                if(endNode == followingRoad.getEnd() && followingRoad.getVoiesRetour().get(i).size() == 0) {
                                    // Il n'y a pas de voitures sur la voie donc nous pouvons ajouter la voiture
                                    voituret.setPositionActuelle(distanceSupp);
                                    followingRoad.ajouterVoitureRetour(voituret, i);
                                    voie.get(i).remove(voituret);
                                }else if (endNode == followingRoad.getEnd() && followingRoad.getVoiesRetour().get(i).get(0).getPositionActuelle() - this.distanceSecurite > 0) {
                                    voituret.setPositionActuelle(0);

                                    if (followingRoad.getVoiesRetour().get(i).get(0).getPositionActuelle() - this.distanceSecurite > distanceSupp) {
                                        voituret.setPositionActuelle(distanceSupp); // Si nous ne risquons pas de foncer dans une autre voiture
                                    }

                                    // Nous pouvons ajouter la voiture
                                    followingRoad.ajouterVoitureRetour(voituret, i);
                                    voie.get(i).remove(voituret);

                                    // Si la route est dans le sens de l'aller
                                } else if(endNode == followingRoad.getStart() && followingRoad.getVoiesAller().get(i).size() == 0) {
                                    // Il n'y a pas de voitures sur la voie donc nous pouvons ajouter la voiture
                                    voituret.setPositionActuelle(distanceSupp);
                                    followingRoad.ajouterVoitureAller(voituret, i);
                                    voie.get(i).remove(voituret);
                                }else if (endNode == followingRoad.getStart() && followingRoad.getVoiesAller().get(i).get(0).getPositionActuelle() - this.distanceSecurite > 0) {
                                    voituret.setPositionActuelle(0);

                                    if (followingRoad.getVoiesAller().get(i).get(0).getPositionActuelle() - this.distanceSecurite > distanceSupp) {
                                        voituret.setPositionActuelle(distanceSupp); // Si nous ne risquons pas de foncer dans une autre voiture
                                    }

                                    // Nous pouvons ajouter la voiture
                                    followingRoad.ajouterVoitureAller(voituret, i);
                                    voie.get(i).remove(voituret);

                                    // Dans ce cas, il nous est impossible d'ajouter la voiture car la voie suivante n'a pas assez de place
                                } else {
                                    voituret.setAccelerationActuelle(0);
                                    voituret.setVitesseActuelle(0);
                                    voituret.setPositionActuelle(this.longueur);
                                }

                                // Si nous arrivons a une voie prioritaire mais que nous n'avons pas la priorite
                            }
                        } else { // Si nous n'arrivons pas en fin de route a la prochaine frame
                            voituret.setAccelerationActuelle(voituret.getAccelerationMax());
                            voituret.setVitesseActuelle(voituret.getVitesseActuelle() + temps / 3600 * voituret.getAccelerationActuelle());

                            if (voituret.getVitesseActuelle() + temps / 3600 * voituret.getAccelerationActuelle() >= this.limitationVitesse || voituret.getVitesseActuelle() + temps / 3600 * voituret.getAccelerationActuelle() >= voituret.getVitesseMax()) {
                                voituret.setAccelerationActuelle(0);
                                voituret.setVitesseActuelle(this.limitationVitesse > voituret.getVitesseMax() ? voituret.getVitesseMax() : this.limitationVitesse); // Tronque a la vitesse maximale au besoin
                            }

                            voituret.setPositionActuelle(voituret.getPositionActuelle() + voituret.getVitesseActuelle() * temps / 3600 + voituret.getAccelerationActuelle() * Math.pow(temps / 3600, 2) / 2);

                        }

                        // Priorite a l'autre route
                    }else if(!endIsCity && endType == Intersection.TypeIntersection.PRIORITY && !ourPriority) {
                        // Arrive en fin de route
                        if (voituret.getPositionActuelle() + voituret.getVitesseActuelle() * temps / 3600 + voituret.getAccelerationActuelle() * Math.pow(temps / 3600, 2) / 2 >= this.longueur) {

                            // Nous verifions si nous pouvons nous inserer (Si les voitures de la voie prioritaire sont eloignees de plus d'une distance de securite(2)
                            ArrayList<Road> tabRoads = endNode.getRoads();
                            boolean voieLibre = true;
                            for (int k = 0; k < tabRoads.size(); k++) {
                                if (tabRoads.get(k) == this || tabRoads.get(k) == followingRoad) {
                                    continue; // on ne verifie pas la route sur laquelle nous sommes et ou nous allons
                                }

                                if (tabRoads.get(k).getEnd() == endNode) {
                                    ArrayList<ArrayList<Voiture>> voieTemp = tabRoads.get(k).getVoiesAller();
                                    for (int k2 = 0; k2 < voieTemp.size(); k2++) {
                                        if(voieTemp.get(k2).size() == 0){
                                            continue;
                                        }else if (voieTemp.get(k2).get(voieTemp.get(k2).size() - 1).getPositionActuelle() > followingRoad.getLongueur() - 2 * followingRoad.getDistanceSecurite()) {
                                            voieLibre = false;
                                        }
                                    }
                                } else if (tabRoads.get(k).getStart() == endNode) {
                                    ArrayList<ArrayList<Voiture>> voieTemp = tabRoads.get(k).getVoiesRetour();
                                    for (int k2 = 0; k2 < voieTemp.size(); k2++) {
                                        if(voieTemp.get(k2).size() == 0){
                                            continue;
                                        }else if (voieTemp.get(k2).get(voieTemp.get(k2).size() - 1).getPositionActuelle() > followingRoad.getLongueur() - 2 * followingRoad.getDistanceSecurite()) {
                                            voieLibre = false;
                                        }
                                    }
                                }
                            }

                            if (!voieLibre) { // Nous n'avons pas la voie libre, on s'arrete a la fin de la route et on attend la prochaine frame
                                voituret.setAccelerationActuelle(0);
                                voituret.setVitesseActuelle(0);
                                voituret.setPositionActuelle(this.longueur);

                            } else { // On a la voie libre

                                // Si la route suivante est dans le sens du retour, et qu'il y a de la place
                                if(endNode == followingRoad.getEnd() && followingRoad.getVoiesRetour().get(i).size() == 0) {
                                    // Il n'y a pas de voitures sur la voie donc nous pouvons ajouter la voiture
                                    voituret.setPositionActuelle(0);
                                    followingRoad.ajouterVoitureRetour(voituret, i);
                                    voie.get(i).remove(voituret);
                                }else if (endNode == followingRoad.getEnd() && followingRoad.getVoiesRetour().get(i).get(0).getPositionActuelle() - this.distanceSecurite > 0) {
                                    voituret.setPositionActuelle(0);

                                    // Nous pouvons ajouter la voiture
                                    followingRoad.ajouterVoitureRetour(voituret, i);
                                    voie.get(i).remove(voituret);

                                    // Si la route est dans le sens de l'aller
                                } else if(endNode == followingRoad.getStart() && followingRoad.getVoiesAller().get(i).size() == 0) {
                                    // Il n'y a pas de voitures sur la voie donc nous pouvons ajouter la voiture
                                    voituret.setPositionActuelle(0);
                                    followingRoad.ajouterVoitureAller(voituret, i);
                                    voie.get(i).remove(voituret);
                                }else if (endNode == followingRoad.getStart() && followingRoad.getVoiesAller().get(i).get(0).getPositionActuelle() - this.distanceSecurite > 0) {
                                    voituret.setPositionActuelle(0);

                                    // Nous pouvons ajouter la voiture
                                    followingRoad.ajouterVoitureAller(voituret, i);
                                    voie.get(i).remove(voituret);

                                    // Dans ce cas, il nous est impossible d'ajouter la voiture car la voie suivante n'a pas assez de place
                                } else {
                                    voituret.setAccelerationActuelle(0);
                                    voituret.setVitesseActuelle(0);
                                    voituret.setPositionActuelle(this.longueur);
                                }
                            }

                        } else { // Si nous n'arrivons pas en fin de route a la prochaine frame
                            voituret.setAccelerationActuelle(voituret.getAccelerationMax());
                            voituret.setVitesseActuelle(voituret.getVitesseActuelle() + temps / 3600 * voituret.getAccelerationActuelle());

                            if (voituret.getVitesseActuelle() + temps / 3600 * voituret.getAccelerationActuelle() >= this.limitationVitesse || voituret.getVitesseActuelle() + temps / 3600 * voituret.getAccelerationActuelle() >= voituret.getVitesseMax()) {
                                voituret.setAccelerationActuelle(0);
                                voituret.setVitesseActuelle(this.limitationVitesse > voituret.getVitesseMax() ? voituret.getVitesseMax() : this.limitationVitesse); // Tronque a la vitesse maximale au besoin
                            }

                            voituret.setPositionActuelle(voituret.getPositionActuelle() + voituret.getVitesseActuelle() * temps / 3600 + voituret.getAccelerationActuelle() * Math.pow(temps / 3600, 2) / 2);
                        }

                        // Nous arrivons sur une priorite a droite
                    }else if(!endIsCity && endType == Intersection.TypeIntersection.RIGHTPRIORITY) {
                        // Arrive en fin de route
                        if (voituret.getPositionActuelle() + voituret.getVitesseActuelle() * temps / 3600 + voituret.getAccelerationActuelle() * Math.pow(temps / 3600, 2) / 2 >= this.longueur) {

                            // Nous verifions si nous pouvons nous inserer (Si les voitures de droites sont eloignes d'une distance de securite)
                            ArrayList<Road> tabRoads = endNode.getRoads();
                            boolean voieLibre = true;
                            int indice = 0; // On conserve k (C'est l'indice de notre route)
                            for (int k = 0; k < tabRoads.size(); k++) {
                                if (tabRoads.get(k) == this) {
                                    indice = k;
                                }
                            }

                            Road routeDroite = tabRoads.get((indice + 1) % tabRoads.size());

                            if (routeDroite.getEnd() == endNode) {
                                ArrayList<ArrayList<Voiture>> voieTemp = routeDroite.getVoiesAller();
                                for (int k = 0; k < voieTemp.size(); k++) {
                                    if(voieTemp.get(k).size() == 0){
                                        continue;
                                    }else if (voieTemp.get(k).get(voieTemp.get(k).size() - 1).getPositionActuelle() > routeDroite.getLongueur() - this.distanceSecurite) {
                                        voieLibre = false;
                                        break;
                                    }
                                }
                            } else if (routeDroite.getStart() == endNode) {
                                ArrayList<ArrayList<Voiture>> voieTemp = routeDroite.getVoiesRetour();
                                for (int k = 0; k < voieTemp.size(); k++) {
                                    if(voieTemp.get(k).size() == 0){
                                        continue;
                                    }else if (voieTemp.get(k).get(voieTemp.get(k).size() - 1).getPositionActuelle() > routeDroite.getLongueur() - this.distanceSecurite) {
                                        voieLibre = false;
                                        break;
                                    }
                                }
                            }

                            if (!voieLibre) { // Nous ne pouvons pas nous inserer, Mais nous verifions que nous ne sommes pas tous bloque dans une impasse
                                int routeBloquees = 1;

                                for (int k = 0; k < tabRoads.size(); k++) {
                                    if (tabRoads.get(k) == this) {
                                        continue;
                                    }
                                    if (tabRoads.get(k).getEnd() == endNode) {
                                        ArrayList<ArrayList<Voiture>> voieTemp = tabRoads.get(k).getVoiesAller();
                                        for (int k2 = 0; k2 < voieTemp.size(); k2++) {
                                            if(voieTemp.get(k2).size() == 0){
                                                continue;
                                            }else if (voieTemp.get(k2).get(voieTemp.get(k2).size() - 1).getPositionActuelle() > tabRoads.get(k2).getLongueur() - this.distanceSecurite) {
                                                routeBloquees++;
                                                break;
                                            }
                                        }
                                    } else if (tabRoads.get(k).getStart() == endNode) {
                                        ArrayList<ArrayList<Voiture>> voieTemp = tabRoads.get(k).getVoiesRetour();
                                        for (int k2 = 0; k2 < voieTemp.size(); k2++) {
                                            if(voieTemp.get(k2).size() == 0){
                                                continue;
                                            }else if (voieTemp.get(k2).get(voieTemp.get(k2).size() - 1).getPositionActuelle() > tabRoads.get(k2).getLongueur() - this.distanceSecurite) {
                                                routeBloquees++;
                                                break;
                                            }
                                        }
                                    }
                                }

                                if (routeBloquees >= tabRoads.size()) { // C'est une impasse!! On s'autorise le passage
                                    voieLibre = true;
                                }
                            }

                            if (!voieLibre) { // Nous n'avons pas la voie libre, on s'arrete a la fin de la route et on attend la prochaine frame
                                voituret.setAccelerationActuelle(0);
                                voituret.setVitesseActuelle(0);
                                voituret.setPositionActuelle(this.longueur);

                            } else { // On a la voie libre

                                // Si la route suivante est dans le sens du retour, et qu'il y a de la place
                                if(endNode == followingRoad.getEnd() && followingRoad.getVoiesRetour().get(i).size() == 0) {
                                    // Il n'y a pas de voitures sur la voie donc nous pouvons ajouter la voiture
                                    voituret.setPositionActuelle(0);
                                    followingRoad.ajouterVoitureRetour(voituret, i);
                                    voie.get(i).remove(voituret);
                                }else if (endNode == followingRoad.getEnd() && followingRoad.getVoiesRetour().get(i).get(0).getPositionActuelle() - this.distanceSecurite > 0) {
                                    voituret.setPositionActuelle(0);

                                    // Nous pouvons ajouter la voiture
                                    followingRoad.ajouterVoitureRetour(voituret, i);
                                    voie.get(i).remove(voituret);

                                    // Si la route est dans le sens de l'aller
                                } else if(endNode == followingRoad.getStart() && followingRoad.getVoiesAller().get(i).size() == 0) {
                                    // Il n'y a pas de voitures sur la voie donc nous pouvons ajouter la voiture
                                    voituret.setPositionActuelle(0);
                                    followingRoad.ajouterVoitureAller(voituret, i);
                                    voie.get(i).remove(voituret);
                                }else if (endNode == followingRoad.getStart() && followingRoad.getVoiesAller().get(i).get(0).getPositionActuelle() - this.distanceSecurite > 0) {
                                    voituret.setPositionActuelle(0);

                                    // Nous pouvons ajouter la voiture
                                    followingRoad.ajouterVoitureAller(voituret, i);
                                    voie.get(i).remove(voituret);

                                    // Dans ce cas, il nous est impossible d'ajouter la voiture car la voie suivante n'a pas assez de place
                                } else {
                                    voituret.setAccelerationActuelle(0);
                                    voituret.setVitesseActuelle(0);
                                    voituret.setPositionActuelle(this.longueur);
                                }
                            }

                            // Si nous sommes dans la zone de ralentissement
                        } else { // Si nous n'arrivons pas en fin de route a la prochaine frame
                            voituret.setAccelerationActuelle(voituret.getAccelerationMax());
                            voituret.setVitesseActuelle(voituret.getVitesseActuelle() + temps / 3600 * voituret.getAccelerationActuelle());

                            if (voituret.getVitesseActuelle() + temps / 3600 * voituret.getAccelerationActuelle() >= this.limitationVitesse || voituret.getVitesseActuelle() + temps / 3600 * voituret.getAccelerationActuelle() >= voituret.getVitesseMax()) {
                                voituret.setAccelerationActuelle(0);
                                voituret.setVitesseActuelle(this.limitationVitesse > voituret.getVitesseMax() ? voituret.getVitesseMax() : this.limitationVitesse); // Tronque a la vitesse maximale au besoin
                            }

                            voituret.setPositionActuelle(voituret.getPositionActuelle() + voituret.getVitesseActuelle() * temps / 3600 + voituret.getAccelerationActuelle() * Math.pow(temps / 3600, 2) / 2);
                        }
                    }

                    // Si ce n'est pas la voiture la plus avancee de la voie
                } else {
                    Voiture voituret2 = voie.get(i).get(j+1); // Voiture de devant
                    // On calcule notre prochaine position theorique
                    double prochainePositionTheorique;
                    double prochaineVitesseTheorique;

                    if(voituret.getVitesseActuelle() + temps / 3600 * voituret.getAccelerationActuelle() >= this.limitationVitesse || voituret.getVitesseActuelle() + temps / 3600 * voituret.getAccelerationActuelle() >= voituret.getVitesseMax()) { // Si l'acceleration va nous faire depasser la limite de vitesse
                        prochainePositionTheorique = voituret.getPositionActuelle() + (this.limitationVitesse > voituret.getVitesseMax() ? voituret.getVitesseMax() : this.limitationVitesse) * temps / 3600;
                        prochaineVitesseTheorique = (this.limitationVitesse > voituret.getVitesseMax() ? voituret.getVitesseMax() : this.limitationVitesse);
                    } else { // Si on peut encore accelerer avant d'atteindre la limite de vitesse
                        prochainePositionTheorique = voituret.getPositionActuelle() + voituret.getVitesseActuelle() * temps / 3600 + voituret.getAccelerationActuelle() * Math.pow(temps / 3600, 2) / 2;
                        prochaineVitesseTheorique = voituret.getVitesseActuelle() + temps / 3600 * voituret.getAccelerationActuelle();
                    }
                    if(prochaineVitesseTheorique < 0) prochaineVitesseTheorique = 0;
                    if(prochainePositionTheorique < voituret.getPositionActuelle()) prochainePositionTheorique = voituret.getPositionActuelle();

                    // Si nous allons arriver derriere une voiture, nous nous mettons a sa vitesse et une position de securite (c'est bien de ne pas foncer dans les autres)
                    if(prochainePositionTheorique > voituret2.getPositionActuelle() - this.distanceSecurite){
                        voituret.setPositionActuelle(voituret2.getPositionActuelle() - this.distanceSecurite);
                        voituret.setVitesseActuelle(voituret2.getVitesseActuelle());
                        voituret.setAccelerationActuelle(0);

                        // Si nous arrivons derriere une voiture de plus loin, nous freinons pour arriver a son niveau a sa vitesse
                    }else if(prochainePositionTheorique + voituret.distanceFreinMax() >= voituret2.getPositionActuelle() - this.distanceSecurite){
                        // Nous n'avons pas un temps assez grand pour "depasser" (Techniquement rentrer dans la voiture comme au dessus) la voiture, nous agencons donc notre vehicule avec une acceleration lui permettant de ralentir
                        double diffVitesse = voituret2.getVitesseActuelle() - voituret.getVitesseActuelle(); // Difference de vitesse entre les deux voitures
                        double diffPosition = voituret2.getPositionActuelle() - voituret.getPositionActuelle(); // Difference de position entre les deux voitures

                        // On applique la decceleration
                        voituret.setAccelerationActuelle( (Math.pow(diffVitesse,2)/2 + voituret.getVitesseActuelle() * diffVitesse) / diffPosition );
                        // On avance avec la meme vitesse d'origine
                        voituret.setPositionActuelle(voituret.getPositionActuelle() + voituret.getAccelerationActuelle() * Math.pow(temps/3600,2) / 2 + voituret.getVitesseActuelle() * temps / 3600);
                        // On applique notre nouvelle vitesse
                        voituret.setVitesseActuelle(voituret.getVitesseActuelle() + temps / 3600 * voituret.getAccelerationActuelle());

                        // Si aucune voiture ne nous gene pour accelerer : On enclenche la vitesse supraluminique! (Surement limite a 130km/h mais c'est deja un bon debut)
                    }else{
                        if(voituret.getVitesseActuelle() < this.limitationVitesse && voituret.getVitesseActuelle() < voituret.getVitesseMax()){ // Acceleration
                            voituret.setAccelerationActuelle(voituret.getAccelerationMax());

                            voituret.setVitesseActuelle(prochaineVitesseTheorique);
                            voituret.setPositionActuelle(prochainePositionTheorique);

                        } else { // Nous avons deja atteint notre maximal
                            voituret.setAccelerationActuelle(0);
                            voituret.setVitesseActuelle(prochaineVitesseTheorique);
                            voituret.setPositionActuelle(prochainePositionTheorique);
                        }
                    }
                }
            }
        }
    }

    public void avancerFrame(double temps){ // Nous changeons l'etat des voitures pour arriver a la prochaine frame (le temps est le pas temporel entre chaque frame, si celui-ci est de 2 secondes, nos voitures aurons une nouvelle position de 2 secondes plus tard)
        if(this.type == TypeRoute.DEPARTEMENTALE){
            // Une seule voie, on avance tout le monde
            this.acceleration(temps,false);
            this.acceleration(temps,true);

        }else if(this.type == TypeRoute.NATIONALE){
            // Voie Aller Moyenne Rabattage
            for(int i = this.voiesAller.get(1).size() - 1; i >= 0; i--){ // On essaie toujours de se rabattre en premier
                this.rabattage(i, 1, this.voiesAller);
            }
            // Voie Aller Lente Depassement
            for(int i = this.voiesAller.get(0).size() - 2; i >= 0; i--){ // Si on peut aller plus vite, on depasse
                Voiture voituret = this.voiesAller.get(0).get(i);
                Voiture voituret2 = this.voiesAller.get(0).get(i+1);
                if(voituret.getVitesseActuelle() < this.limitationVitesse && !voituret.capaciteMaximale() && voituret.getPositionActuelle() + voituret.distanceFreinMax() >= voituret2.getPositionActuelle() - this.distanceSecurite && voituret.getPositionActuelle() < this.longueur - this.distanceSecurite * 3) {
                    /*
                    D'abord on verifie si nous allons moins vite que la limitation de vitesse
                    Ensuite si nous allons a notre vitesse maximale
                    Enfin si nous ne sommes pas simplement en train d'accelerer (si l'accelerationActuelle est inferieure 0 alors nous pouvons possiblement aller plus vite)
                    Si nous ne sommes pas dans la voie de ralentissement (3*distanceSecurite avant les infrastructures)
                     */
                    this.depassement(i, 0, this.voiesAller);
                }
            }


            // Voie Retour Moyenne Rabattage
            for(int i = this.voiesRetour.get(1).size() - 1; i >= 0; i--){ // On essaie toujours de se rabattre en premier
                this.rabattage(i, 1, this.voiesRetour);
            }
            // Voie Retour Lente Depassement
            for(int i = this.voiesRetour.get(0).size() - 2; i >= 0; i--){ // Si on peut aller plus vite, on depasse
                Voiture voituret = this.voiesRetour.get(0).get(i);
                Voiture voituret2 = this.voiesRetour.get(0).get(i+1);
                if(voituret.getVitesseActuelle() < this.limitationVitesse && !voituret.capaciteMaximale() && voituret.getPositionActuelle() + voituret.distanceFreinMax() >= voituret2.getPositionActuelle() - this.distanceSecurite && voituret.getPositionActuelle() < this.longueur - this.distanceSecurite * 3) {
                    /*
                    D'abord on verifie si nous allons moins vite que la limitation de vitesse
                    Ensuite si nous allons a notre vitesse maximale
                    Enfin si nous ne sommes pas simplement en train d'accelerer (si l'accelerationActuelle est inferieure 0 alors nous pouvons possiblement aller plus vite)
                    Si nous ne sommes pas dans la voie de ralentissement (3*distanceSecurite avant les infrastructures)
                     */
                    this.depassement(i, 0, this.voiesRetour);
                }
            }


            // Accelerations dans toutes les voies
            this.acceleration(temps,false);
            this.acceleration(temps,true);

        }else if(this.type == TypeRoute.AUTOROUTE){
            // Voie Aller Moyenne Rabattage // D'abord la moyenne pour eviter un rabattage de 2 voies ... d'un coup
            for(int i = this.voiesAller.get(1).size() - 1; i >= 0; i--){ // On essaie toujours de se rabattre en premier
                this.rabattage(i, 1, this.voiesAller);
            }
            // Voie Aller Rapide Rabattage
            for(int i = this.voiesAller.get(2).size() - 1; i >= 0; i--){ // On essaie toujours de se rabattre en premier
                this.rabattage(i, 2, this.voiesAller);
            }

            // Voie Aller Moyenne Depassement // Idem : D'abord la moyenne pour eviter un depassement de 2 voies d'un seul coup
            for(int i = this.voiesAller.get(1).size() - 2; i >= 0; i--){ // Si on peut aller plus vite, on depasse
                Voiture voituret = this.voiesAller.get(1).get(i);
                Voiture voituret2 = this.voiesAller.get(1).get(i+1);
                if(voituret.getVitesseActuelle() < this.limitationVitesse && !voituret.capaciteMaximale() && voituret.getPositionActuelle() + voituret.distanceFreinMax() >= voituret2.getPositionActuelle() - this.distanceSecurite && voituret.getPositionActuelle() < this.longueur - this.distanceSecurite * 3) {
                    /*
                    D'abord on verifie si nous allons moins vite que la limitation de vitesse
                    Ensuite si nous allons a notre vitesse maximale
                    Enfin si nous ne sommes pas simplement en train d'accelerer (si l'accelerationActuelle est inferieure 0 alors nous pouvons possiblement aller plus vite)
                    Si nous ne sommes pas dans la voie de ralentissement (3*distanceSecurite avant les infrastructures)
                     */
                    this.depassement(i, 1, this.voiesAller);
                }
            }
            // Voie Aller Lente Depassement
            for(int i = this.voiesAller.get(0).size() - 2; i >= 0; i--){ // Si on peut aller plus vite, on depasse
                Voiture voituret = this.voiesAller.get(0).get(i);
                Voiture voituret2 = this.voiesAller.get(0).get(i+1);
                if(voituret.getVitesseActuelle() < this.limitationVitesse && !voituret.capaciteMaximale() && voituret.getPositionActuelle() + voituret.distanceFreinMax() >= voituret2.getPositionActuelle() - this.distanceSecurite && voituret.getPositionActuelle() < this.longueur - this.distanceSecurite * 3) {
                    /*
                    D'abord on verifie si nous allons moins vite que la limitation de vitesse
                    Ensuite si nous allons a notre vitesse maximale
                    Enfin si nous ne sommes pas simplement en train d'accelerer (si l'accelerationActuelle est inferieure 0 alors nous pouvons possiblement aller plus vite)
                    Si nous ne sommes pas dans la voie de ralentissement (3*distanceSecurite avant les infrastructures)
                     */
                    this.depassement(i, 0, this.voiesAller);
                }
            }



            // Voie Retour Moyenne Rabattage // D'abord la moyenne pour eviter un rabattage de 2 voies ... d'un coup
            for(int i = this.voiesRetour.get(1).size() - 1; i >= 0; i--){ // On essaie toujours de se rabattre en premier
                this.rabattage(i, 1, this.voiesRetour);
            }
            // Voie Retour Rapide Rabattage
            for(int i = this.voiesRetour.get(2).size() - 1; i >= 0; i--){ // On essaie toujours de se rabattre en premier
                this.rabattage(i, 2, this.voiesRetour);
            }

            // Voie Retour Moyenne Depassement // Idem : D'abord la moyenne pour eviter un depassement de 2 voies d'un seul coup
            for(int i = this.voiesRetour.get(1).size() - 2; i >= 0; i--){ // Si on peut aller plus vite, on depasse
                Voiture voituret = this.voiesRetour.get(1).get(i);
                Voiture voituret2 = this.voiesRetour.get(1).get(i+1);
                if(voituret.getVitesseActuelle() < this.limitationVitesse && !voituret.capaciteMaximale() && voituret.getPositionActuelle() + voituret.distanceFreinMax() >= voituret2.getPositionActuelle() - this.distanceSecurite && voituret.getPositionActuelle() < this.longueur - this.distanceSecurite * 3) {
                    /*
                    D'abord on verifie si nous allons moins vite que la limitation de vitesse
                    Ensuite si nous allons a notre vitesse maximale
                    Enfin si nous ne sommes pas simplement en train d'accelerer (si l'accelerationActuelle est inferieure 0 alors nous pouvons possiblement aller plus vite)
                    Si nous ne sommes pas dans la voie de ralentissement (3*distanceSecurite avant les infrastructures)
                     */
                    this.depassement(i, 1, this.voiesRetour);
                }
            }
            // Voie Retour Lente Depassement
            for(int i = this.voiesRetour.get(0).size() - 2; i >= 0; i--){ // Si on peut aller plus vite, on depasse
                Voiture voituret = this.voiesRetour.get(0).get(i);
                Voiture voituret2 = this.voiesRetour.get(0).get(i+1);
                if(voituret.getVitesseActuelle() < this.limitationVitesse && !voituret.capaciteMaximale() && voituret.getPositionActuelle() + voituret.distanceFreinMax() >= voituret2.getPositionActuelle() - this.distanceSecurite && voituret.getPositionActuelle() < this.longueur - this.distanceSecurite * 3) {
                    /*
                    D'abord on verifie si nous allons moins vite que la limitation de vitesse
                    Ensuite si nous allons a notre vitesse maximale
                    Enfin si nous ne sommes pas simplement en train d'accelerer (si l'accelerationActuelle est inferieure 0 alors nous pouvons possiblement aller plus vite)
                    Si nous ne sommes pas dans la voie de ralentissement (3*distanceSecurite avant les infrastructures)
                     */
                    this.depassement(i, 0, this.voiesRetour);
                }
            }


            // Accelerations dans toutes les voies
            this.acceleration(temps,false);
            this.acceleration(temps,true);
        }
    }

    // ------------------------------------------------
    // FONCTIONS DEBUGS
    // ------------------------------------------------

    public void debugAjouterAller(Voiture voiture, int voie, double position){ // Placement d'une voiture dans une voie d'aller, et a une certaine position
        int[] indexs = voituresAlentoursPosition(this.voiesAller.get(voie), position);
        voiture.setPositionActuelle(position);
        if(indexs[1] == -1){
            this.voiesAller.get(voie).add(voiture);
        }else{
            this.voiesAller.get(voie).add(indexs[1], voiture);
        }
    }

    public void debugAjouterRetour(Voiture voiture, int voie, double position){ // Placement d'une voiture dans une voie de retour, et a une certaine position
        int[] indexs = voituresAlentoursPosition(this.voiesRetour.get(voie), position);
        voiture.setPositionActuelle(position);
        if(indexs[1] == -1){
            this.voiesRetour.get(voie).add(voiture);
        }else{
            this.voiesRetour.get(voie).add(indexs[1], voiture);
        }
    }
}
