package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.image.Image;

import java.util.*;

public class Sardines extends Projectile {
    private final static int CHARGE_Q = 200;
    private final static int K = 1000;

    private double forceEnX = 0;
    private double forceEnY = 0;
    private ArrayList<PoissonEnnemi> poissonsEnnemis;

    public Sardines(Charlotte charlotte, double tempsActuel, ArrayList<PoissonEnnemi> poissonEnnemis) {
        super(charlotte, tempsActuel);
        w = 35;
        h = 29;
        vx = 300;
        vy = 0;
        ay = 0;
        image = new Image(Assets.SARDINES.getEmplacement());
        calculerPosInitial();
        this.poissonsEnnemis = poissonEnnemis;
    }

    @Override
    public void mettreAJourPhysique(double deltaTemps) {
        System.out.println("does this run");
        calculerForceElectrique();
        ajusterRebondissement();
        ax = forceEnX;
        ay = forceEnY;
        super.mettreAJourPhysique(deltaTemps);
    }

    private void ajusterRebondissement() {
        if (this.getYBas() >= Main.HAUTEUR || this.getYHaut() <= 0) {
            vy *= -1;
        }
    }
    private double calculerForceElectrique() { //TODO: Faire séparer pour clarifier code
        double forceElectrique = 0;
        for (PoissonEnnemi poisson: poissonsEnnemis) {
            if (verifierQuePoissonADroite(poisson)) {
                double deltaX = x - poisson.x;
                double deltaY = y - poisson.y;

                double distance = calculerDistance(deltaX, deltaY);
                //distance minimum pour éviter bug:
                if (distance < 0.01) {
                    distance = 0.01;
                }

                double proportionX = deltaX/distance;
                double proportionY = deltaY/distance;

                forceElectrique += (K * poisson.getChargeQ() * this.CHARGE_Q)/Math.pow(distance, 2);

                forceEnX = forceElectrique * proportionX;
                forceEnY = forceElectrique * proportionY;
            }
        }
        return forceElectrique;
    }

    private boolean verifierQuePoissonADroite(PoissonEnnemi poissonEnnemi) {
        return poissonEnnemi.x > this.x;
    }

    private double calculerDistance(double deltaX, double deltaY) {
        double carreDeltaX = Math.pow(deltaX, 2);
        double carreDeltaY = Math.pow(deltaY, 2);

        return Math.pow((carreDeltaX + carreDeltaY), 1/2);
    }


}
