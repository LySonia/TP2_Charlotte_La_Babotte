package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.image.Image;

public class Baril extends ObjetJeu {

    private static final int PERIODE = 3;
    private double tempsDebutNiveau;

    public Baril(double tempsDebutNiveau) {
        this.tempsDebutNiveau = tempsDebutNiveau;
        w = 70;
        h = 83;
        //valeur aléatoire entre 1/5 et 4/5 de l'écran
        x = Aleatoire.getGenerateurAleatoire().nextDouble(((double) Main.LARGEUR_MONDE / 5), ((double) (4 * Main.LARGEUR_MONDE) / 5));
        image = new Image(Assets.BARIL.getEmplacement());
        y = 0;

    }

    @Override
    public void update(double deltaTemps) {
        double t = (System.nanoTime() * Scenes.NANOSECONDE) - tempsDebutNiveau; //trouve le temps écoulé depuis le début du niveau
        y = (Main.HAUTEUR - h) / 2 * Math.sin((2 * Math.PI * t) / PERIODE) + (Main.HAUTEUR - h) / 2; // donne la position de y qui change selon le temps écouler depuis le debut du niveau

    }
}
