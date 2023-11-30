package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.image.Image;

import java.util.*;

public class Sardines extends Projectile {
    private final static int CHARGE_Q = 200;
    private final static int K = 1000;
    private double forceEnX = 0;
    private double forceEnY = 0;
    private ArrayList<PoissonEnnemi> poissonsEnnemis;

    /**
     * Constructeur de l'objet Sardines
     * @param charlotte la Charlotte de la partie
     * @param tempsActuel le temps quand l'objet est crée
     * @param poissonEnnemis le ArrayList des poissons ennemis dans le jeu
     */
    public Sardines(Charlotte charlotte, double tempsActuel, ArrayList<PoissonEnnemi> poissonEnnemis) {
        super(charlotte, tempsActuel);
        calculerPosInitial();
        vitesseMax = 800;
        w = 35;
        h = 29;
        vx = 300;
        vy = 0;
        ay = 0;
        image = new Image(Assets.SARDINES.getEmplacement());
        this.poissonsEnnemis = poissonEnnemis;
    }

    /**
     * Fait les calculs de physiques en lien avec le mouvement des sardines
     * @param deltaTemps différence de temps
     */
    @Override
    public void mettreAJourPhysique(double deltaTemps) {
        calculerForceElectrique();
        ax = forceEnX;
        ay = forceEnY;

        ajusterRebondissement();
        super.mettreAJourPhysique(deltaTemps);
    }

    /**
     * Fait rebondir la canette de sardines si elle touche soit le haut, soit le bas de l'écran
     */
    private void ajusterRebondissement() {
        if (this.getYBas() >= Main.HAUTEUR || this.getYHaut() <= 0) {
            vy *= -1;
        }
    }

    /**
     * Calculer la force électrique total exercé sur la canette de sardines par les poissons ennemis
     */
    private void calculerForceElectrique() {
        double forceElectrique = 0;
        forceEnX = 0;
        forceEnY = 0;
        for (PoissonEnnemi poisson: poissonsEnnemis) {
            if (verifierQuePoissonADroite(poisson)) {
                double deltaX = x - poisson.x;
                double deltaY = y - poisson.y;

                double distance = calculerDistance(deltaX, deltaY);
                //distance minimum pour éviter bug:
                if (distance < 0.01) {
                    distance = 0.01;
                }

                double proportionX = deltaX / distance;
                double proportionY = deltaY / distance;

                forceElectrique += (K * poisson.CHARGE_Q * this.CHARGE_Q) / (Math.pow(distance, 2));

                forceEnX += forceElectrique * proportionX;
                forceEnY += forceElectrique * proportionY;
            }
        }
    }

    /**
     * Vérifier que le poisson ennemi à analyser se retrouve à droite de la canette de sardines
     * @param poissonEnnemi le poisson ennemi à analyser
     * @return un boolean qui est vrai si le poisson ennemi est à droite
     */
    private boolean verifierQuePoissonADroite(PoissonEnnemi poissonEnnemi) {
        return poissonEnnemi.x > this.x;
    }

    /**
     * Utiliser la formule de Pythagore pour trouver la distance entre la canette de sardines et le poisson
     * @param deltaX différence en x
     * @param deltaY différence en y
     * @return distance qui sépare la canette du poisson
     */
    private double calculerDistance(double deltaX, double deltaY) {
        double carreDeltaX = Math.pow(deltaX, 2);
        double carreDeltaY = Math.pow(deltaY, 2);

        return Math.pow((carreDeltaX + carreDeltaY), 1.0/2.0);
    }
}
