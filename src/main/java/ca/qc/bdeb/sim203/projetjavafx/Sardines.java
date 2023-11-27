package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.image.Image;

import java.util.*;

public class Sardines extends Projectile {
    private final static int CHARGE_Q = 200;
    private final static int K = 1000;
    private double forceEnX = 0;
    private double forceEnY = 0;
    private ArrayList<PoissonEnnemi> poissonsEnnemis;

    //TODO: Est-ce que la liste de poisson est la
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

    @Override
    public void mettreAJourPhysique(double deltaTemps) {
        calculerForceElectrique();
        ax = forceEnX;
        ay = forceEnY;

        ajusterRebondissement();
        super.mettreAJourPhysique(deltaTemps);

        System.out.println(this + " vy: " + vy);
    }

    private void ajusterRebondissement() {
        //TODO: Ajuster pour glitch quand ça sort de l'écran pendant un deltaTemps
        if (this.getYBas() >= Main.HAUTEUR || this.getYHaut() <= 0) {
            vy *= -1;
        }
    }
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

                forceElectrique += (K * poisson.getChargeQ() * this.CHARGE_Q) / (Math.pow(distance, 2));

                forceEnX += forceElectrique * proportionX;
                forceEnY += forceElectrique * proportionY;
            }
        }
    }

    private boolean verifierQuePoissonADroite(PoissonEnnemi poissonEnnemi) {
        return poissonEnnemi.x > this.x;
    }

    private double calculerDistance(double deltaX, double deltaY) {
        double carreDeltaX = Math.pow(deltaX, 2);
        double carreDeltaY = Math.pow(deltaY, 2);

        return Math.pow((carreDeltaX + carreDeltaY), 1.0/2.0);
    }

    public void updateListePoissons(ArrayList<PoissonEnnemi> poissonEnnemis) {
        this.poissonsEnnemis = poissonEnnemis;
    }

}
