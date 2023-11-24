package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Baril extends ObjetJeu {

    private static final int PERIODE = 3;
    private double tempsDebutNiveau;
    private boolean estOuvert = false;
    private ArrayList<Projectile> listeProjectile = new ArrayList<>();

    public Baril(double tempsDebutNiveau) {
        this.tempsDebutNiveau = tempsDebutNiveau;
        w = 70;
        h = 83;
        //valeur aléatoire entre 1/5 et 4/5 de l'écran
        x = Aleatoire.getGenerateurAleatoire().nextDouble(((double) Main.LARGEUR_MONDE / 5), ((double) (4 * Main.LARGEUR_MONDE) / 5));
        image = new Image(Assets.BARIL.getEmplacement());
        y = 0;
        remplirArraylist();


    }

    private void remplirArraylist(){
        listeProjectile.add(new EtoileDeMer());
        listeProjectile.add(new Hippocampes());
        listeProjectile.add(new Sardines());
    }
    @Override
    public void update(double deltaTemps) {
        double t = (System.nanoTime() * Scenes.NANOSECONDE) - tempsDebutNiveau; //trouve le temps écoulé depuis le début du niveau
        y = (Main.HAUTEUR - h) / 2 * Math.sin((2 * Math.PI * t) / PERIODE) + (Main.HAUTEUR - h) / 2; // donne la position de y qui change selon le temps écouler depuis le debut du niveau

    }

    public boolean isEstOuvert() {
        return estOuvert;
    }

    public void setEstOuvert(boolean estOuvert) {
        this.estOuvert = estOuvert;
    }
    public Projectile donnerProjectile(Projectile projectileActuel){
        Projectile projectileChoisit = null;
        do{
            int choix = Aleatoire.obtenirNombreAleatoire(listeProjectile.size());
            projectileChoisit = listeProjectile.get(choix);

        }while(projectileChoisit.getClass().equals(projectileActuel.getClass()));

        return  projectileChoisit;
    }
}
