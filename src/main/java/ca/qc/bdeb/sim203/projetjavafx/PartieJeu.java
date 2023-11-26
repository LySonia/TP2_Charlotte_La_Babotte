package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.*;
import javafx.scene.image.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;

import java.util.*;

import static ca.qc.bdeb.sim203.projetjavafx.Hasard.nextInt;

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
    private double momentDebutNiveau = 0;
    private double momentFinNiveau = 0;
    private double momentDerniersPoissons = 0;
    private double momentDernierTir = 0;
    private final double DUREE_AFFICHAGE_NIVEAU = 4;
    public final double DUREE_AFFICHAGE_FIN_JEU = 3;
    private final double DELAIS_TIR = 0.5;

    //Attributs boolean :
    private boolean estDebug = false;
    private boolean estALaFinNiveau = false;
    private boolean estFinPartie = false;

    //Attributs autres :
    private Color couleurFondNiveau;
    private int numNiveau = 0;


    //Constructeur :
    public PartieJeu(double tempsActuel) {
        this.tempsActuel = tempsActuel;

        demarrerNiveau(tempsActuel);
    }

    //Méthodes :
    public void demarrerNiveau(double tempsActuel) {
        numNiveau++;
        momentDebutNiveau = tempsActuel;

        Camera.getCamera().reinitialiserCamera();
        preparerFondNiveau();
        calculerNSecondes();

        //Vider les listes d'objets :
        objetsJeu.clear();
        poissonsEnnemis.clear();
        decors.clear();
        projectiles.clear();

        //Rajouter les objets de Jeu :
        charlotte.viderProjectiles();
        charlotte.setX(Camera.getCamera().getXCamera()); //Replacer Charlotte à gauche de la caméra
        objetsJeu.add(charlotte);

        objetsJeu.add(barreVie);
        baril = new Baril(momentDebutNiveau);
        objetsJeu.add(baril);
        ajouterGroupePoissons();
        positionnerDecor();
    }

    //TOUT CE QUI EST "MISE À JOUR" :
    public void mettreAJourJeu(double tempsActuel) {
        this.deltaTemps = tempsActuel - this.tempsActuel;
        this.tempsActuel = tempsActuel;

        charlotte.viderProjectiles(); //TODO: Bon endroit pour le placer?
        Camera.getCamera().update(charlotte, deltaTemps); //Mettre à jour la caméra
        mettreAJourObjets();

        //Collisions :
        gererCollisionsPoissons();
        gererCollisionsProjectiles();
        gererCollisionBaril();

        //Autre :
        enleverObjetsHorsEcran();
        calculerEstALaFinNiveau();

        if ((tempsActuel - momentDerniersPoissons) > nSecondes) {
            ajouterGroupePoissons();
        }

        if (!estFinPartie) {
            verifierFinPartie();
        }

        if (estALaFinNiveau) {
            demarrerNiveau(tempsActuel);
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

    private void gererCollisionsProjectiles() {
        for (int i = 0; i < projectiles.size(); i++) { //Analyser chaque projectile
            Projectile projectileAnalyse = projectiles.get(i);
            for (int j = 0; j < poissonsEnnemis.size(); j++) { //Analyser chaque poisson
                PoissonEnnemi poissonEnnemiAnalyse = poissonsEnnemis.get(j);
                if (Collision.estEnCollision(poissonEnnemiAnalyse, projectileAnalyse)) {
                    enleverPoissonDeListe(poissonEnnemiAnalyse);
                }
            }
        }
    }

    private void gererCollisionBaril() {
        if (Collision.estEnCollision(baril, charlotte)) {
            ouvrirBaril();
        }
    }

    private void enleverObjetsHorsEcran() { //TODO: Code copié-collé?
        //Enlever poissons hors écran
        for (int i = 0; i < poissonsEnnemis.size(); i++) {
            if (!poissonsEnnemis.get(i).estDansEcran()){
                enleverPoissonDeListe(poissonsEnnemis.get(i));
            }
        }

        //Enlever projectiles hors écran
        for (int i = 0; i < projectiles.size(); i++) {
            if (!projectiles.get(i).estDansEcran()){
                enleverProjectileDeListe(projectiles.get(i));
            }
        }
    }

    private void verifierFinPartie() {
        if (!charlotte.estVivante()) {
            estFinPartie = true;
            momentFinNiveau = tempsActuel;
        }
    }

    public void gererTireProjectile(){
        if ((tempsActuel - momentDernierTir) > DELAIS_TIR) {
            momentDernierTir = tempsActuel;
            charlotte.tirer(tempsActuel);

            projectiles.addAll(charlotte.getProjectile());
            objetsJeu.addAll(charlotte.getProjectile());
        }
    }



    public void enleverPoissonDeListe(PoissonEnnemi poisson) {
        poissonsEnnemis.remove(poisson);
        objetsJeu.remove(poisson);
    }

    public void enleverProjectileDeListe(Projectile projectile) {
        projectiles.remove(projectile);
        objetsJeu.remove(projectile);
    }


    public void ajouterGroupePoissons() {
        momentDerniersPoissons = tempsActuel;

        //Génération d'un nombre aléatoire de poissons (entre 1 et 5):
        int nbrPoissonDansGroupe = Hasard.nextInt(1, 5);
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

    private void calculerEstALaFinNiveau() {
        estALaFinNiveau = charlotte.getXDroite() >= Main.LARGEUR_MONDE;
    }

    private void positionnerDecor() {
        //x = 0.0 pour le premier corail
        decors.add(new Decor(0.0));
        double dernierePos = decors.get(decors.size() - 1).getXDroite();

        //Tant que la position (du x de droite) du dernier corail ne touche pas à la fin du niveau, ajouter un corail
        while (dernierePos < Main.LARGEUR_MONDE) {
            dernierePos = decors.get(decors.size() - 1).getXDroite();
            double distanceEntre = new Random().nextBoolean() ? 50.0 : 100.0; //distance entre coraux
            double nouvellePos = dernierePos + distanceEntre;
            decors.add(new Decor(nouvellePos));
        }

        objetsJeu.addAll(decors);
    }

    public void ouvrirBaril() {
        if (!baril.isEstOuvert()) {
            baril.setEstOuvert(true);
            Assets nouvelTypeProjectile = baril.donnerProjectile(charlotte.getTypeProjectileActuel());
            changerTypeProjectile(nouvelTypeProjectile);
        }
    }

    public void changerTypeProjectile(Assets typeProjectile) {
        charlotte.setTypeProjectileActuel(typeProjectile);
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

        dessinerProjectileDroiteBarre(contexte);

        if (estFinPartie) {
            afficherFinPartie(contexte);
        }

        if (estDebug) {
            afficherDebug(contexte);
        }

        if (tempsActuel - momentDebutNiveau < DUREE_AFFICHAGE_NIVEAU) {
            afficherNumNiveau(contexte);
        }
    }

    private void dessinerProjectileDroiteBarre(GraphicsContext contexte) {
        contexte.drawImage(new Image(charlotte.getTypeProjectileActuel().getEmplacement()), 175, 8);
        ;
    }

    private void afficherNumNiveau(GraphicsContext contexte) {
        String texteNiveau = ("NIVEAU " + numNiveau);
        contexte.setFont(Font.font(80));

        contexte.fillText(texteNiveau, 255,  277); //Valeurs de x et y choisies arbitrairement
    }

    private void afficherFinPartie(GraphicsContext contexte) {
        String texteNiveau = ("FIN DE PARTIE ");
        contexte.setFill(Color.RED);
        contexte.setFont(Font.font( 80));

        contexte.fillText(texteNiveau, 170, 277); //Valeurs de x et y choisies arbitrairement
    }

    private void afficherDebug(GraphicsContext contexte) {
        //Mettre un rectangle jaune autour de tous les objets de jeu
        for (ObjetJeu objet : objetsJeu) {
            objet.mettreContour(contexte);
        }

        contexte.setFont(Font.font(10));
        contexte.fillText("NB Poissons: " + getNbrPoissonsEnnemis(), 15, 55);
        contexte.fillText("NB Projectiles: " + getNbrProjectiles(), 15, 70);

        double pourcentagePosCharlotte = (charlotte.getXGauche()/Main.LARGEUR_MONDE)*100;
        contexte.fillText("pourcentagePosCharlotte: " + pourcentagePosCharlotte + "%", 10, 85);

        contexte.fillText("Q: Étoiles de mer", Main.LARGEUR_ECRAN - 150, 15);
        contexte.fillText("W: Trios d'hippocampes", Main.LARGEUR_ECRAN - 150, 30);
        contexte.fillText("E: Boîtes de sardines", Main.LARGEUR_ECRAN - 150, 45);
        contexte.fillText("R: Donner max vie à Charlotte", Main.LARGEUR_ECRAN - 150, 60);
        contexte.fillText("T: Passer au prochain niveau", Main.LARGEUR_ECRAN - 150, 75);
        contexte.fillText("nSecondes:" + nSecondes, Main.LARGEUR_ECRAN - 150, 90);
    }

    private void preparerFondNiveau() {
        final double saturation = 0.84;
        final double luminosité = 1.0;
        final double teinte = nextInt(190, 270);
        couleurFondNiveau = Color.hsb(teinte, saturation, luminosité);
    }

    //GETTERS
    public Color getCouleurFondNiveau() {
        return couleurFondNiveau;
    }
    private int getNbrPoissonsEnnemis() {
        return poissonsEnnemis.size();
    }
    private int getNbrProjectiles() {
        return projectiles.size();
    }
    public boolean estDebug() {
        return estDebug;
    }

    public boolean estFinPartie() {
        return estFinPartie;
    }

    public double getMomentFinNiveau() {
        return momentFinNiveau;
    }

    //SETTERS
    public void setEstDebug(boolean estDebug) {
        this.estDebug = estDebug;
    }
    public void donnerMaxVie() {
        charlotte.donnerMaxVie();
    }
}
