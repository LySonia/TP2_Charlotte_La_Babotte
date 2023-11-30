package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.image.Image;

import static ca.qc.bdeb.sim203.projetjavafx.Hasard.generateurAleatoire;

public class Hippocampes extends Projectile {
    private double amplitude;
    private double periode;

    private double yInitial;

    /**
     * Constructeur de la classe Hippocampes
     * @param charlotte pour trouver la position initiale du projectile
     * @param momentTire le moment où le projectile est tiré
     */
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
        amplitude = Hasard.nextInt(30, 60);
        if (generateurAleatoire.nextBoolean()) {
            amplitude *= -1;
        }
        //periode aléatoire
        periode = Hasard.nextInt(1, 3);
    }

    /**
     * Override de la méthode mettreAJourPhysique de la classe objet de jeu pour prendre en compte le mouvement
     * particulier des hippocampes
     * @param deltaTemps différence de temps
     */
    @Override
    public void mettreAJourPhysique(double deltaTemps) {

        double tempsEcoule = System.nanoTime()*Scenes.NANOSECONDE - momentTire;
        y = amplitude * Math.sin((2 * Math.PI * periode * tempsEcoule) / 2) + yInitial;
        x += deltaTemps * vx;
    }
}
