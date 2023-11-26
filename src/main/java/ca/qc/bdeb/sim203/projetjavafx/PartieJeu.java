package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.paint.*;
import javafx.scene.text.*;

import java.util.*;

import static ca.qc.bdeb.sim203.projetjavafx.Hasard.obtenirNombreAleatoire;

public class PartieJeu {
    //Attributs objets de jeu:
    private Charlotte charlotte = new Charlotte();
    private BarreVie barreVie = new BarreVie(charlotte);
    private Baril baril;

    //Attributs listes d'objets :
    private ArrayList<ObjetJeu> objetsJeu = new ArrayList<>();
    private ArrayList<PoissonEnnemi> poissonsEnnemis = new ArrayList<>();
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private ArrayList<Decor> decors = new ArrayList<>();

    //Attributs de temps :
    private double tempsActuel = 0;
    private double deltaTemps = 0;
    private double nSecondes = 1;
    private double tempsDebutNiveau = 0;
    private double tempsDerniersPoissons = 0;
    private double tempsDernierTir = 0;
    private final double TEMPS_AFFICHAGE_NIVEAU = 4;
    private final double TEMPS_AFFICHAGE_FIN_JEU = 3;

    private final double DELAIS_TIR = 0.5;
    //Attributs autres :
    private Color couleurFondNiveau;
    private boolean estDebug = false;
    private int numNiveau = 0;
    private boolean estALaFinNiveau = false;

    //Constructeur :
    public PartieJeu(double tempsActuel) {
        this.tempsActuel = tempsActuel;

        demarrerNiveau(tempsActuel);
    }

    //Méthodes :
    public void demarrerNiveau(double tempsActuel) {
        numNiveau++;
        tempsDebutNiveau = tempsActuel;
        viderListesObjets();


        objetsJeu.add(charlotte);
        objetsJeu.add(barreVie);

        baril = new Baril(tempsDebutNiveau);
        objetsJeu.add(baril);

        Camera.getCamera().reinitialiserCamera();
        preparerFondNiveau(); //TODO: Cette méthode -> trop de vue?
        replacerCharlotte();
        ajouterGroupePoissons();
        calculerNSecondes();
        positionnerDecor();
        calculerEstALaFin();
    }

    private void viderListesObjets() {
        objetsJeu.clear();
        poissonsEnnemis.clear();
        decors.clear();
        projectiles.clear();
    }

    private void replacerCharlotte() {
        charlotte.setX(Camera.getCamera().getXCamera());
    }

    //TOUT CE QUI EST "MISE À JOUR" :
    public void mettreAJourJeu(double tempsActuel) {
        this.deltaTemps = tempsActuel - this.tempsActuel;
        this.tempsActuel = tempsActuel;

        //TODO: Vérifier ordre d'exécution
        gererCollisionsPoissons();
        gererCollisionsProjectiles();
        gererCollisionBaril();
        gererGenerationPoissons();
        mettreAJourObjets();
        enleverPoissonsHorsEcran();
        Camera.getCamera().update(charlotte, deltaTemps); //Mettre à jour la caméra
        calculerEstALaFin();

        if (!charlotte.estVivante()) {
            //Code for death
        }

        if (estALaFinNiveau && charlotte.estVivante()) {
            demarrerNiveau(tempsActuel);
        }
    }

    public void gererTireProjectile(){
        if ((tempsActuel - tempsDernierTir) > DELAIS_TIR) {
            tempsDernierTir = tempsActuel;
            Projectile projectile = charlotte.getTypeProjectileActuel().getProjectile();

            double yCentre = charlotte.getYCentre() - projectile.getH()/2;
            double xCentre = charlotte.getXCentre() - projectile.getW()/2;

            projectile.setX(xCentre);
            projectile.setY(yCentre);

            projectile.setYDeCentreCharlotte(yCentre);
            projectile.setEstTirer(true);

            objetsJeu.add(projectile);
        }
    }

    private void enleverPoissonsHorsEcran() {
        for (int i = 0; i < poissonsEnnemis.size(); i++) {
            if (!poissonsEnnemis.get(i).estDansEcran()) {
                sortirPoisson(poissonsEnnemis.get(i));
            }
        }
    }

    private void gererCollisionBaril() {
        if (Collision.estEnCollision(baril, charlotte)) {
            ouvrirBaril();
        }
    }

    private void gererGenerationPoissons() {
        if ((tempsActuel - tempsDerniersPoissons) > nSecondes) {
            ajouterGroupePoissons();
            tempsDerniersPoissons = tempsActuel;
        }
    }

    private void mettreAJourObjets() {
        for (ObjetJeu objetJeu : objetsJeu) {
            objetJeu.mettreAJour(deltaTemps);
        }
    }

    private void gererCollisionsPoissons() {
        for (PoissonEnnemi poissonEnnemi : poissonsEnnemis) {
            if (Collision.estEnCollision(poissonEnnemi, charlotte)) {
                charlotte.prendreDommage();
            }
            charlotte.gererImmortalite(tempsActuel);
        }
    }

    private void gererCollisionsProjectiles() { //Code à Camille
        for (int i = poissonsEnnemis.size() - 1; i >= 0; i--) {
            PoissonEnnemi temp = poissonsEnnemis.get(i);

            //si le poisson enemies est à droite de charlotte!
            if (temp.getXGauche() > charlotte.getXGauche()) {

                //si le projectile touche un poisson enemies
                if (Collision.estEnCollision(temp, charlotte.getTypeProjectileActuel().getProjectile())) {
                    sortirPoisson(temp);
                }
            }
        }
    }


    public void ajouterGroupePoissons() {
        tempsDerniersPoissons = tempsActuel;

        //Génération d'un nombre aléatoire de poissons (entre 1 et 5):
        int nbrPoissonDansGroupe = Hasard.obtenirNombreAleatoire(1, 5);
        ArrayList<PoissonEnnemi> nouveauxPoissons = new ArrayList<>();

        //Ajouter le nombre aléatoire de poissons dans le ArrayList<>
        for (int i = 0; i < nbrPoissonDansGroupe; i++) {
            nouveauxPoissons.add(new PoissonEnnemi(numNiveau));
        }

        //Ajouter les nouveaux poissons dans le ArrayList de poissons ennemis
        poissonsEnnemis.addAll(nouveauxPoissons);

        //Ajouter les nouveaux poissons dans le ArrayList d'objet de jeu
        objetsJeu.addAll(nouveauxPoissons);
    }

    private void positionnerDecor() {
        //x = 0.0 pour le premier corail
        decors.add(new Decor(0.0));
        double dernierePos = decors.get(decors.size() - 1).getXDroite();

        //Tant que la position (du x de droite) du dernier corail ne touche pas à la fin du niveau, ajouter un corail
        while (dernierePos < Main.LARGEUR_MONDE) {
            dernierePos = decors.get(decors.size() - 1).getXDroite();
            double nouvellePos = dernierePos + genererDistanceEntreCoraux();
            decors.add(new Decor(nouvellePos));
        }

        //Ajouter les coraux dans le ArrayList des objets de jeu
        objetsJeu.addAll(decors);
    }

    private double genererDistanceEntreCoraux() {
        //TODO: Write cleaner code here
        return new Random().nextBoolean() ? 50.0 : 100.0;
    }

    public void sortirPoisson(PoissonEnnemi poisson) {
        poissonsEnnemis.remove(poisson);
        objetsJeu.remove(poisson);
    }

    public void ouvrirBaril() {
        if (!baril.isEstOuvert()) {
            baril.setEstOuvert(true);
            baril.setImage(new Image(Assets.BARIL_OUVERT.getEmplacement()));
            TypesProjectiles dernierType = charlotte.getTypeProjectileActuel();
            charlotte.setTypeProjectile(baril.donnerProjectile(dernierType));
            //TODO: fix make it so the projectile gets added to array of objet de jeu
        }

    }

    public void trouverAccelerationSardine() { //TODO: Je pense pas que ça devrait être dans cette classe
        for (PoissonEnnemi poissonEnnemi : poissonsEnnemis) {
            if (poissonEnnemi.getXGauche() > charlotte.getXGauche()) { //si le poisson est à droite de charlotte
                //TODO: finish this

            }
        }

    }

    private void calculerNSecondes() {
        nSecondes = 0.75 + 1 / Math.pow(numNiveau, 0.5);
    }


    //TOUT CE QUI EST "DESSIN" :
    public void dessiner(GraphicsContext contexte) {
        contexte.clearRect(0, 0, Main.LARGEUR_ECRAN, Main.HAUTEUR);
        for (ObjetJeu objetJeu : objetsJeu) {
            objetJeu.dessiner(contexte);
        }
        if (estDebug) {
            afficherDebug(contexte);
        }

        if (tempsActuel - tempsDebutNiveau < TEMPS_AFFICHAGE_NIVEAU) {
            afficherNumNiveau(contexte);
        }
    }

    private void afficherNumNiveau(GraphicsContext contexte) {
        String texteNiveau = ("NIVEAU " + numNiveau);
        contexte.setFont(Font.font("Arial", 100));

        //TODO: Remplacer Main.Largeur/2 et Main.Hauteur/2 par la position du centre de la caméra
        contexte.fillText(texteNiveau, 200, Main.HAUTEUR / 2);

        //Remettre le font à la taille normale
        contexte.setFont(Font.font("Arial", 10));
    }

    private void afficherDebug(GraphicsContext contexte) {
        //Mettre un rectangle jaune autour de tous les objets de jeu
        for (ObjetJeu objet : objetsJeu) {
            objet.mettreContour(contexte);
        }

        //Afficher les nombre de poissons dans le jeu
        var texteNbrPoissons = "NB Poissons: " + getNbrPoissonsEnnemis();
        contexte.fillText(texteNbrPoissons, 10, 55); //TEST
    }

    private void calculerEstALaFin() {
        estALaFinNiveau = charlotte.getXDroite() >= Main.LARGEUR_MONDE;
    }

    private void preparerFondNiveau() {
        final double saturation = 0.84;
        final double luminosité = 1.0;
        final double teinte = obtenirNombreAleatoire(190, 270);
        couleurFondNiveau = Color.hsb(teinte, saturation, luminosité);
    }


    //GETTERS
    public Color getCouleurFondNiveau() {
        return couleurFondNiveau;
    }

    public Charlotte getCharlotte() {
        return charlotte;
    }

    public int getNbrPoissonsEnnemis() {
        return poissonsEnnemis.size();
    }

    public Baril getBaril() {
        return baril;
    }

    public boolean estDebug() {
        return estDebug;
    }

    //SETTERS
    public void setEstDebug(boolean estDebug) {
        this.estDebug = estDebug;
    }
}
