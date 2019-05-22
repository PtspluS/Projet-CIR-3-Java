public class Voiture {
    // Les valeurs de vitesses seront estimees en km/h

    // Constantes
    private final double deccelerationMax; // Freinage Maximal /!\ NEGATIF
    private final double accelerationMax; // Acceleration Maximale km/h^2
    private final double vitesseMax; // Vitesse Maximale de cette voiture (Surlimitee par la vitesse maximale d'une route)

    // On peut trouver l'acceleration max en faisant 100*3600/x  ou x est le temps en seconde pour la voiture d'aller de 0 a 100km/h

    // Variables
    private double vitesseActuelle; // Vitesse actuelle de la voiture
    private double positionActuelle; // Position actuelle de la voiture sur sa route
    private double accelerationActuelle; // Acceleration actuelle de la voiture (Permet de savoir si on est arrive a une limitation de vitesse)

    // Getters
    public double getDeccelerationMax() {return deccelerationMax; }
    public double getAccelerationMax() { return accelerationMax; }
    public double getVitesseMax() { return vitesseMax; }

    public double getVitesseActuelle() {
        return vitesseActuelle;
    }
    public double getPositionActuelle() {
        return positionActuelle;
    }
    public double getAccelerationActuelle() {
        return accelerationActuelle;
    }

    // Setters
    public void setVitesseActuelle(double vitesseActuelle) {
        this.vitesseActuelle = (vitesseActuelle > this.vitesseMax) ? this.vitesseMax : vitesseActuelle;
    }
    public void setPositionActuelle(double positionActuelle) {
        this.positionActuelle = positionActuelle;
    }
    public void setAccelerationActuelle(double accelerationActuelle) { this.accelerationActuelle = accelerationActuelle; }

    // Methodes
    public Voiture(double zeroACent, double vitesseMax){
        this.deccelerationMax = -1 * 100*3600/(zeroACent*4); // Doit etre NEGATIF !
        this.accelerationMax = 100*3600/zeroACent;
        this.vitesseMax = vitesseMax;

        this.vitesseActuelle = 0;
        this.positionActuelle = 0;
        this.accelerationActuelle = 0;
    }

    public double distanceFreinMax(){ // Retourne la distance de freinage de la voiture selon sa vitesse actuelle et sa decceleration
        return -1 * (Math.pow(vitesseActuelle/this.deccelerationMax, 2) * (this.deccelerationMax/2) + Math.pow(vitesseActuelle, 2) / this.deccelerationMax);
    }

    public boolean capaciteMaximale(){ // La voiture peut-elle aller plus vite ?
        return this.vitesseActuelle >= this.vitesseMax;
    }
}
