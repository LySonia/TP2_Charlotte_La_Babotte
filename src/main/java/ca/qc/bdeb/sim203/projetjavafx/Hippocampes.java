package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.image.Image;

public class Hippocampes extends Projectile {
    private double amplitude;
    private double periode;

    private double yInitial;

    public Hippocampes(Charlotte charlotte, double momentTire) {
        super(charlotte, momentTire);
        image = new Image(Assets.HIPPOCAMPE.getEmplacement());
        w = 20;
        h = 36;
        vx = 500;
        vitesseMax = 500;
        calculerPosInitial();
        yInitial = y;

        //amplitude négative/positive aléatoire
        amplitude = Hasard.obtenirNombreAleatoire(30, 60);
        if (Hasard.getGenerateurAleatoire().nextBoolean()) {
            amplitude *= -1;
        }
        //periode aléatoire
        periode = Hasard.obtenirNombreAleatoire(1, 3);
    }

    @Override
    public void mettreAJourPhysique(double deltaTemps) {
        //TODO: aller chercher les bonnes valeurs
        double tempsEcoule = System.nanoTime()*Scenes.NANOSECONDE - momentTire;
        y = amplitude * Math.sin((2 * Math.PI * periode * tempsEcoule) / 2) + yInitial;
        x += deltaTemps * vx;
    }
}
