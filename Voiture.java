public class Voiture {
    // Les valeurs de vitesses seront estimees en km/h

    // Constantes Globales
    private final static double deccelerationMax = 100; // Freinage Maximal

    // Constantes
    private final double acceleration; // Acceleration Maximale
    private final double vitesseMax; // Vitesse Maximale de cette voiture (Surlimitee par la vitesse maximale d'une route)


    // Variables
    private double vitesseActuelle; // Vitesse actuelle de la voiture
    private double positionActuelle; // Position actuelle de la voiture sur sa route

    // Getters
    public double getVitesseActuelle() {
        return vitesseActuelle;
    }
    public double getPositionActuelle() {
        return positionActuelle;
    }

    // Setters
    public void setVitesseActuelle(double vitesseActuelle) {
        this.vitesseActuelle = vitesseActuelle;
    }
    public void setPositionActuelle(double positionActuelle) {
        this.positionActuelle = positionActuelle;
    }

    // Methodes
    public Voiture(double acceleration, double vitesseMax){
        this.acceleration = acceleration;
        this.vitesseMax = vitesseMax;
    }

    public double distanceFreinMax(){ // Retourne la distance de freinage de la voiture selon sa vitesse actuelle et sa decceleration
        return -1 * Math.pow(vitesseActuelle/Voiture.deccelerationMax, 2) * (Voiture.deccelerationMax/2) + Math.pow(vitesseActuelle, 2) / Voiture.deccelerationMax;
    }
}
