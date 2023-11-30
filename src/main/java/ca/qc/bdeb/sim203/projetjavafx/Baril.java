package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.*;
import javafx.scene.image.Image;



import static ca.qc.bdeb.sim203.projetjavafx.Hasard.nextDouble;

public class Baril extends ObjetJeu {
    private static final int PERIODE = 3;
    private double tempsDebutNiveau;
    private boolean estOuvert = false;

    /**
     * Constructeur de l'objet baril
     * @param tempsDebutNiveau le temps de début de niveau qui servira à trouver le temps écoulé depuis le début du
     *                         niveau
     */
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

    /**
     * méthode qui appelle un Random pour trouver aléatoirement la position initiale en X du baril
     * @return la position initiale du baril
     */
    private double trouverXInitial() {
        return nextDouble((1.0 / 5.0) * Main.LARGEUR_MONDE, (4.0 / 5.0) * Main.LARGEUR_MONDE);
    }

    /**
     * Override de la methode mettreAJourPhysique de ObjetDeJeu, elle ajuste le mouvement du baril selon le temps
     * @param deltaTemps difference de temps (pas utilisé dans cet override)
     */
    @Override
    public void mettreAJourPhysique(double deltaTemps) {
        //trouve le temps écoulé depuis le début du niveau
        double t = (System.nanoTime() * Main.NANOSECONDE) - tempsDebutNiveau;

        // donne la position de y qui change selon le temps écouler depuis le debut du niveau
        y = (Main.HAUTEUR - h) / 2 * Math.sin((2 * Math.PI * t) / PERIODE) + (Main.HAUTEUR - h) / 2;

    }

    /**
     * Override de dessiner pour inclure une méthode changeant l'image si le baril est ouvert
     * @param contexte pour dessiner
     */
    @Override
    public void dessiner(GraphicsContext contexte) {
        gererImage();
        super.dessiner(contexte);
    }

    /**
     * Vérifie si le baril a été ouvert et ajuste l'image utilisé en conséquence
     */
    private void gererImage() {
        if (estOuvert) {
            image = new Image(Assets.BARIL_OUVERT.getEmplacement());
        } else {
            image = new Image(Assets.BARIL.getEmplacement());
        }
    }

    /**
     * Donne un nouveau projectile Random différent du type que Charlotte a déjà
     * @param dernierType le type de projectile que Charlotte a en ce moment
     * @return un nouveau type de projectile
     */
    public Assets donnerProjectile(Assets dernierType) {
        Assets nouveauType = null;
        do {
            nouveauType = Hasard.choisirTypeProjectileHasard();

        } while (nouveauType.equals(dernierType));

        return nouveauType;
    }

    //Getters et Setters
    public boolean isEstOuvert() {
        return estOuvert;
    }

    public void setEstOuvert(boolean estOuvert) {
        this.estOuvert = estOuvert;
    }
}
