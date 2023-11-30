package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.*;
import javafx.scene.image.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;

import java.util.*;

import static ca.qc.bdeb.sim203.projetjavafx.Hasard.*;

public class PartieJeu {
    //Attributs objets de jeu:
    private Charlotte charlotte = new Charlotte();
    private BarreVie barreVie = new BarreVie(charlotte);
    private Baril baril;

    //Attributs listes d'objets :
    private ArrayList<ObjetJeu> objetsJeu = new ArrayList<>();
    private ArrayList<PoissonEnnemi> poissonsEnnemis = new ArrayList<>();
    private ArrayList<Projectile> projectilesTires = new ArrayList<>();
    private ArrayList<Decor> decors = new ArrayList<>();

    //Attributs de temps :
    private double tempsActuel = 0;
    private double deltaTemps = 0;
    private double nSecondes = 1;
    private double momentDebutNiveau = 0;
    private double momentFinNiveau = 0;
    private double momentDerniersPoissons = 0;
    private double momentDernierTir = 0;
    private static final double DUREE_AFFICHAGE_NIVEAU = 4;
    public static final double DUREE_AFFICHAGE_FIN_JEU = 3;
    private static final double DELAIS_TIR = 0.5;

    //Attributs boolean :
    private boolean estDebug = false;
    private boolean estALaFinNiveau = false;
    private boolean estFinPartie = false;

    //Attributs autres :
    private Color couleurFondNiveau;
    private int numNiveau = 0;
    private static final double NBR_HIPPOCAMPES_A_LA_FOIS = 3;


    /**
     * Contructeur de la classe PartieJeu
     *
     * @param tempsActuel le temps au moment de la création d'une instance de PartieJeu
     */
    public PartieJeu(double tempsActuel) {
        this.tempsActuel = tempsActuel;

        demarrerNiveau(tempsActuel);
    }

    //Méthodes :

    /**
     * Commencer un nouvel niveau
     *
     * @param tempsActuel le temps au moment d'apeller cette méthode
     */
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
        projectilesTires.clear();

        //Rajouter les objets de Jeu :
        positionnerDecor();

        charlotte.setX(Camera.getCamera().getXCamera()); //Replacer Charlotte à gauche de la caméra
        objetsJeu.add(charlotte);


        baril = new Baril(momentDebutNiveau);
        objetsJeu.add(baril);
        ajouterGroupePoissons();

        objetsJeu.add(barreVie);

    }

    //TOUT CE QUI EST "MISE À JOUR" :

    /**
     * Méthode qui sert à avacer l'état de l'objet
     *
     * @param tempsActuel le temps au moment de l'appel de cette méthode
     */
    public void mettreAJourJeu(double tempsActuel) {
        this.deltaTemps = tempsActuel - this.tempsActuel;
        this.tempsActuel = tempsActuel;

        Camera.getCamera().update(charlotte, deltaTemps); //Mettre à jour la caméra
        mettreAJourObjets();

        //Collisions :
        gererCollisionsPoissons();
        gererCollisionsProjectiles();
        gererCollisionBaril();

        //Autre :
        enleverObjetsHorsEcran();
        verifierCharlotteEstALaFinNiveau();

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

    /**
     * Mettre à jour l'état de chaque objet dans le jeu
     */
    private void mettreAJourObjets() {
        for (ObjetJeu objetJeu : objetsJeu) {
            objetJeu.mettreAJour(deltaTemps);
        }
    }

    /**
     * Vérifier les collisions entre chaque poisson et Charlotte
     */
    private void gererCollisionsPoissons() {
        for (PoissonEnnemi poissonEnnemi : poissonsEnnemis) {
            if (Collision.estEnCollision(poissonEnnemi, charlotte)) {
                charlotte.prendreDommage();
            }
            charlotte.gererImmortalite(tempsActuel);
        }
    }

    /**
     * Vérifier les collisions entre les projectiles et les poissons ennemis
     */
    private void gererCollisionsProjectiles() {
        for (int i = 0; i < projectilesTires.size(); i++) { //Analyser chaque projectile
            Projectile projectileAnalyse = projectilesTires.get(i);
            for (int j = 0; j < poissonsEnnemis.size(); j++) { //Analyser chaque poisson
                PoissonEnnemi poissonEnnemiAnalyse = poissonsEnnemis.get(j);
                if (Collision.estEnCollision(poissonEnnemiAnalyse, projectileAnalyse)) {
                    enleverPoissonDeListe(poissonEnnemiAnalyse);
                }
            }
        }
    }

    /**
     * Vérifier pour une collision entre le baril et Charlotte
     */
    private void gererCollisionBaril() {
        if (Collision.estEnCollision(baril, charlotte)) {
            ouvrirBaril();
        }
    }

    /**
     * Enlever les objets de jeu qui sont en-dehors de l'écran
     */
    private void enleverObjetsHorsEcran() {
        //Enlever poissons hors écran
        for (int i = 0; i < poissonsEnnemis.size(); i++) {
            if (!poissonsEnnemis.get(i).estDansEcran()) {
                enleverPoissonDeListe(poissonsEnnemis.get(i));
            }
        }

        //Enlever projectiles hors écran
        for (int i = 0; i < projectilesTires.size(); i++) {
            if (!projectilesTires.get(i).estDansEcran()) {
                enleverProjectileDeListe(projectilesTires.get(i));
            }
        }
    }

    /**
     * Vérifier si le jeu est terminé, c'est-à-dire si Charlotte est morte
     */
    private void verifierFinPartie() {
        if (!charlotte.estVivante()) {
            estFinPartie = true;
            momentFinNiveau = tempsActuel;
        }
    }

    /**
     * Permettre le joueur de tirer, si celui-ci respecte le delai de tire
     */
    public void gererTireProjectile() {
        if ((tempsActuel - momentDernierTir) > DELAIS_TIR) {
            momentDernierTir = tempsActuel;
            tirer();
        }
    }

    /**
     * Enlever un poisson des ArrayLists d'objets de jeu qui le contiennent
     *
     * @param poisson le poisson à enlever
     */
    public void enleverPoissonDeListe(PoissonEnnemi poisson) {
        poissonsEnnemis.remove(poisson);
        objetsJeu.remove(poisson);
    }

    /**
     * Enlever un projectile des ArrayLists d'objets de jeu qui le contiennent
     *
     * @param projectile le projectile à enlever
     */
    public void enleverProjectileDeListe(Projectile projectile) {
        projectilesTires.remove(projectile);
        objetsJeu.remove(projectile);
    }

    /**
     * Ajouter un groupe de poissons ennemis dans le jeu
     */
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

    /**
     * Vérifier si Charlotte est à la fin du niveau
     */
    private void verifierCharlotteEstALaFinNiveau() {
        estALaFinNiveau = charlotte.getXDroite() >= Main.LARGEUR_MONDE;
    }

    /**
     * Générer et positionner tous les morceaux de décor
     */
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

    /**
     * Ouvrir le baril
     */
    private void ouvrirBaril() {
        if (!baril.isEstOuvert()) {
            baril.setEstOuvert(true);
            Assets nouvelTypeProjectile = baril.donnerProjectile(charlotte.getTypeProjectileActuel());
            changerTypeProjectile(nouvelTypeProjectile);
        }
    }

    /**
     * Changer le type de projectile à Charlotte
     *
     * @param typeProjectile le nouvel type de projectile à Charlotte
     */
    public void changerTypeProjectile(Assets typeProjectile) {
        charlotte.setTypeProjectileActuel(typeProjectile);
    }

    /**
     * Tirer un projectile
     */
    private void tirer() {
        ArrayList<Projectile> nouveauxProjectiles = new ArrayList<>();

        switch (charlotte.getTypeProjectileActuel()) {
            case ETOILE -> nouveauxProjectiles.add(new EtoileDeMer(charlotte, tempsActuel));
            case HIPPOCAMPE -> {
                for (int i = 0; i < NBR_HIPPOCAMPES_A_LA_FOIS; i++) {
                    nouveauxProjectiles.add(new Hippocampes(charlotte, tempsActuel));
                }
            }
            case SARDINES -> nouveauxProjectiles.add(new Sardines(charlotte, tempsActuel, poissonsEnnemis));

        }
        projectilesTires.addAll(nouveauxProjectiles);
        objetsJeu.addAll(nouveauxProjectiles);
    }

    /**
     * Calculer nSecondes selon la formule donnée dans l'énoncé
     */
    private void calculerNSecondes() {
        nSecondes = 0.75 + 1 / Math.pow(numNiveau, 0.5);
    }

    //TOUT CE QUI EST "DESSIN" :

    /**
     * Méthode qui gère le dessin de tout ce qui doit être affiché sur l'écran de jeu à l'aide de JavaFX
     *
     * @param contexte le GraphicsContext sur lequel on dessine ce qu'on a à dessiner
     */
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

    /**
     * Dessine le type de projectile à droite de la barre de vie
     *
     * @param contexte le GraphicsContext sur lequel on dessine
     */
    private void dessinerProjectileDroiteBarre(GraphicsContext contexte) {
        contexte.drawImage(new Image(charlotte.getTypeProjectileActuel().getEmplacement()), 175, 8);
    }

    /**
     * Affiche le text qui indique le numéro du niveau
     *
     * @param contexte le GraphicsContexte sur lequel on dessine
     */
    private void afficherNumNiveau(GraphicsContext contexte) {
        String texteNiveau = ("NIVEAU " + numNiveau);
        contexte.setFont(Font.font(80));

        contexte.fillText(texteNiveau, 255, 277); //Valeurs de x et y choisies arbitrairement
    }

    /**
     * Affiche le texte de fin de partie
     *
     * @param contexte le GraphicsContext sur lequel on dessine
     */
    private void afficherFinPartie(GraphicsContext contexte) {
        String texteNiveau = ("FIN DE PARTIE ");
        contexte.setFill(Color.RED);
        contexte.setFont(Font.font(80));

        contexte.fillText(texteNiveau, 170, 277); //Valeurs de x et y choisies arbitrairement
    }

    /**
     * Afficher les informations à montrer dans le mode debug
     *
     * @param contexte le GraphicsContext sur lequel on dessine
     */
    private void afficherDebug(GraphicsContext contexte) {
        //Mettre un rectangle jaune autour de tous les objets de jeu
        for (ObjetJeu objet : objetsJeu) {
            objet.mettreContour(contexte);
        }

        contexte.setFont(Font.font(10));
        contexte.fillText("NB Poissons: " + getNbrPoissonsEnnemis(), 15, 55);
        contexte.fillText("NB Projectiles: " + getNbrProjectiles(), 15, 70);

        double pourcentagePosCharlotte = (charlotte.getXGauche() / Main.LARGEUR_MONDE) * 100;
        contexte.fillText("pourcentagePosCharlotte: " + pourcentagePosCharlotte + "%", 10, 85);

        contexte.fillText("Q: Étoiles de mer", Main.LARGEUR_ECRAN - 150, 15);
        contexte.fillText("W: Trios d'hippocampes", Main.LARGEUR_ECRAN - 150, 30);
        contexte.fillText("E: Boîtes de sardines", Main.LARGEUR_ECRAN - 150, 45);
        contexte.fillText("R: Donner max vie à Charlotte", Main.LARGEUR_ECRAN - 150, 60);
        contexte.fillText("T: Passer au prochain niveau", Main.LARGEUR_ECRAN - 150, 75);
        contexte.fillText("nSecondes:" + nSecondes, Main.LARGEUR_ECRAN - 150, 90);
    }

    /**
     * Générer la couleur de fond du niveau
     */
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
        return projectilesTires.size();
    }

    public double getMomentFinNiveau() {
        return momentFinNiveau;
    }

    public boolean estDebug() {
        return estDebug;
    }

    public boolean estFinPartie() {
        return estFinPartie;
    }

    //SETTERS
    public void setEstDebug(boolean estDebug) {
        this.estDebug = estDebug;
    }

    public void donnerMaxVie() {
        charlotte.donnerMaxVie();
    }
}
