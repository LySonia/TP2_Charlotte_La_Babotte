package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.*;
import javafx.scene.image.Image;

import java.util.ArrayList;

import static ca.qc.bdeb.sim203.projetjavafx.Hasard.nextDouble;

public class Baril extends ObjetJeu {
    private static final int PERIODE = 3;
    private double tempsDebutNiveau;
    private boolean estOuvert = false;

    public Baril(double tempsDebutNiveau) {
        this.tempsDebutNiveau = tempsDebutNiveau;
        w = 70;
        h = 83;
        //valeur aléatoire entre 1/5 et 4/5 de l'écran
        trouverXInitial();
        x = trouverXInitial();
        image = new Image(Assets.BARIL.getEmplacement());
        y = 0;
    }

    private double trouverXInitial() {
        return nextDouble((1.0/5.0)* Main.LARGEUR_MONDE, (4.0/5.0)* Main.LARGEUR_MONDE);
    }

    @Override
    public void mettreAJourPhysique(double deltaTemps) {
        double t = (System.nanoTime() * Scenes.NANOSECONDE) - tempsDebutNiveau; //trouve le temps écoulé depuis le début du niveau
        y = (Main.HAUTEUR - h) / 2 * Math.sin((2 * Math.PI * t) / PERIODE) + (Main.HAUTEUR - h) / 2; // donne la position de y qui change selon le temps écouler depuis le debut du niveau

    }

    @Override
    public void dessiner(GraphicsContext contexte) {
        gererImage();
        super.dessiner(contexte);
    }

    public boolean isEstOuvert() {
        return estOuvert;
    }

    public void setEstOuvert(boolean estOuvert) {
        this.estOuvert = estOuvert;
    }

    private void gererImage() {
        if (estOuvert) {
            image = new Image(Assets.BARIL_OUVERT.getEmplacement());
        } else {
            image = new Image(Assets.BARIL.getEmplacement());
        }
    }

    public TypesProjectiles donnerProjectile(TypesProjectiles dernierType) {
        TypesProjectiles nouveauType = null;
        do {
            nouveauType = Hasard.choisirTypeProjectileHasard();

        } while (nouveauType.equals(dernierType));

        return nouveauType;
    }
}
