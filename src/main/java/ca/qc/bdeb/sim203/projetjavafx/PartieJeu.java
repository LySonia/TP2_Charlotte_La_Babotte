package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.paint.*;

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
    private ArrayList<Decor> decors = new ArrayList<>();

    //Attributs de temps :
    private double tempsActuel = 0;
    private double deltaTemps = 0;
    private double nSecondes = 1;
    private double tempsDebutNiveau = 0;
    private double tempsDerniersPoissons = 0;

    //Attributs autres :
    private Color couleurFondNiveau;
    private boolean estDebug = false;
    private int numNiveau = 1;
    private boolean estALaFin = false;
    //Constructeur :
    public PartieJeu(double tempsActuel) {
        this.tempsActuel = tempsActuel;
        objetsJeu.add(charlotte);
        objetsJeu.add(barreVie);
        demarrerNiveau(tempsActuel);
    }

    //Méthodes :
    public void demarrerNiveau(double tempsActuel) {
        tempsDebutNiveau = tempsActuel;

        baril = new Baril(tempsDebutNiveau);
        objetsJeu.add(baril);
        preparerFondNiveau(); //TODO: Cette méthode -> trop de vue?
        replacerCharlotte();
        ajouterGroupePoissons();
        calculerNSecondes();
        positionnerDecor();
        calculerEstALaFin();

        if (estALaFin && charlotte.estVivante()) {
            demarrerNiveau(tempsActuel);
        }
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
    }



    private void enleverPoissonsHorsEcran() {
        for (int i = 0; i < poissonsEnnemis.size(); i++) {
            if (!poissonsEnnemis.get(i).estDansEcran()) {
                sortirPoisson(poissonsEnnemis.get(i));
            }
        }
    }
    private void gererCollisionBaril() {
        if(Collision.estEnCollision(baril, charlotte)){
            ouvrirBaril();
        }
    }

    private void gererGenerationPoissons() {
        if ((tempsActuel - tempsDerniersPoissons) > nSecondes){
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
        for (int i = poissonsEnnemis.size() - 1; i>=0; i--) {
            PoissonEnnemi temp = poissonsEnnemis.get(i);

            //si le poisson enemies est a droite de charlotte!
            if (temp.getXGauche() > charlotte.getXGauche()) {

                //si le projectile touche un poisson enemies
                if (Collision.estEnCollision(temp, charlotte.getProjectileActuel())) {
                    sortirPoisson(temp);
                }
            }
        }
    }

    private void preparerFondNiveau() {
        final double saturation = 0.84;
        final double luminosité = 1.0;
        final double teinte = obtenirNombreAleatoire(190, 270);
        couleurFondNiveau = Color.hsb(teinte, saturation, luminosité);
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
        //TODO: Il y a sûrement un moyen plus efficace pour placer les nouveaux poissons dans les 2 ArrayLists
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
            charlotte.setProjectileActuel(baril.donnerProjectile(charlotte.getProjectileActuel()));
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
        for (ObjetJeu objetJeu: objetsJeu){
            objetJeu.dessiner(contexte);
        }
        if (estDebug) {
            afficherDebug(contexte);
        }
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
        estALaFin = charlotte.getXDroite() >= Main.LARGEUR_MONDE;
    }

    //GETTERS

    public ArrayList<Projectile> getProjectilesCharlotte() {
        objetsJeu.addAll(charlotte.getProjectileArrayList());
        return charlotte.getProjectileArrayList();
    }

    public ArrayList<ObjetJeu> getObjetsJeu() {
        return objetsJeu;
    }

    public Color getCouleurFondNiveau() {
        return couleurFondNiveau;
    }

    public Charlotte getCharlotte() {
        return charlotte;
    }

    public ArrayList<PoissonEnnemi> getPoissonsEnnemis() {
        return poissonsEnnemis;
    }

    public double getTempsDebutNiveau() {
        return tempsDebutNiveau;
    }

    public int getNumNiveau() {
        return numNiveau;
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
