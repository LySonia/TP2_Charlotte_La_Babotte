package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.*;
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
    private TypesProjectiles typesProjectilesActuel = TypesProjectiles.HIPPOCAMPES;

    //Constructeur :
    public PartieJeu(double tempsActuel) {
        this.tempsActuel = tempsActuel;

        demarrerNiveau(tempsActuel);
    }

    //Méthodes :
    public void demarrerNiveau(double tempsActuel) {
        numNiveau++;
        tempsDebutNiveau = tempsActuel;

        //Vider les listes d'objets :
        objetsJeu.clear();
        poissonsEnnemis.clear();
        decors.clear();
        projectiles.clear();

        //Rajouter les objets de Jeu :
        objetsJeu.add(charlotte);
        charlotte.viderProjectiles();
        objetsJeu.add(barreVie);
        baril = new Baril(tempsDebutNiveau);
        objetsJeu.add(baril);

        preparerFondNiveau();
        Camera.getCamera().reinitialiserCamera();
        calculerNSecondes();
        replacerCharlotte();
        ajouterGroupePoissons();
        positionnerDecor();
    }

    private void replacerCharlotte() {
        charlotte.setX(Camera.getCamera().getXCamera());
    }

    //TOUT CE QUI EST "MISE À JOUR" :
    public void mettreAJourJeu(double tempsActuel) {
        charlotte.viderProjectiles(); //TODO: Bon endroit pour le placer?
        this.deltaTemps = tempsActuel - this.tempsActuel;
        this.tempsActuel = tempsActuel;

        //TODO: Vérifier ordre d'exécution
        mettreAJourObjets();
        gererGenerationPoissons();
        gererCollisionsPoissons();
        gererCollisionsProjectiles();
        gererCollisionBaril();
        enleverObjetsHorsEcran();
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
            charlotte.tirer(tempsActuel);

            projectiles.addAll(charlotte.getProjectile());
            objetsJeu.addAll(charlotte.getProjectile());
        }
    }

    private void enleverObjetsHorsEcran() {
        for (int i = 0; i < poissonsEnnemis.size(); i++) {
            if (!poissonsEnnemis.get(i).estDansEcran()){
                enleverPoissonDeListe(poissonsEnnemis.get(i));
            }
        }

        for (int i = 0; i < projectiles.size(); i++) {
            if (!projectiles.get(i).estDansEcran()){
                enleverProjectileDeListe(projectiles.get(i));
            }
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

    public void ajouterGroupePoissons() {
        tempsDerniersPoissons = tempsActuel;

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
            TypesProjectiles nouvelTypeProjectile = baril.donnerProjectile(charlotte.getTypeProjectileActuel());
            charlotte.setTypeProjectileActuel(nouvelTypeProjectile);
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

    private void calculerEstALaFin() {
        estALaFinNiveau = charlotte.getXDroite() >= Main.LARGEUR_MONDE;
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
        contexte.fillText(texteNbrPoissons, 10, 55);

        var texteNbrProjectiles = "NB Projectiles: " + getNbrProjectiles();
        contexte.fillText(texteNbrProjectiles, 10, 70);

        double pourcentagePosCharlotte = (charlotte.x/Main.LARGEUR_MONDE)*100; //TODO: why does charlotte.x work???
        var textePosCharlotte = "pourcentagePosCharlotte: " + pourcentagePosCharlotte + "%";
        contexte.fillText(textePosCharlotte, 10, 95);
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

    //SETTERS
    public void setEstDebug(boolean estDebug) {
        this.estDebug = estDebug;
    }
    public void donnerMaxVie() {
        charlotte.donnerMaxVie();
    }
}
