package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.image.Image;

public class Hippocampes extends Projectile {
    private double amplitude;
    private double periode;

    public Hippocampes() {
        w = 20;
        h = 36;
        image = new Image(Assets.HIPPOCAMPE.getEmplacement());
        vx = 500;

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
        double tempsEcoule = System.nanoTime()*Scenes.NANOSECONDE - tempsDeTir;
        y = amplitude * Math.sin((2 * Math.PI * periode * tempsEcoule) / 2) + yDeCentreCharlotte;
        x += deltaTemps * vx;
    }
}
