package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.image.Image;

public class Hyppocampes extends Projectile {

    private double amplitude;
    private double periode;

    public Hyppocampes() {
        w = 20;
        h = 36;
        image = new Image(Assets.HIPPOCAMPE.getEmplacement());
        vx = 500;

        //amplitude négative/positive aléatoire
        amplitude = Aleatoire.obtenirNombreAleatoire(30, 60);
        if (Aleatoire.getGenerateurAleatoire().nextBoolean()) {
            amplitude *= -1;
        }
        //periode aléatoire
        periode = Aleatoire.obtenirNombreAleatoire(1,3);
    }

    @Override
    public void update(double deltaTemps) {
        //TODO: aller chercher les bonnes valeurs
        double t =0;
        double yDeCentreCharlotte = 0;
        y = amplitude * Math.sin((2*Math.PI*periode*t)/2) +  yDeCentreCharlotte;
    }
}
