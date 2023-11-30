package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.*;
import javafx.scene.image.*;
import javafx.scene.input.*;

public class Charlotte extends ObjetJeu {
    //Attributs constantes :
    private final static double W_CHARLOTTE = 102;
    private final static double H_CHARLOTTE = 90;
    private final static double ACCELERATION_X = 1000;
    private final static double ACCELERATION_Y = 1000;
    private final static double NBR_VIE_MAX = 4.0;
    private final static double VITESSE_MAX = 300;
    private final static double VITESSE_MIN = -300;

    //Attributs de temps:
    private final static int TEMPS_IMMORTALITE = 2;

    //Attributs boolean :
    private boolean estImmortel = false;
    private boolean estEndommagee = false;
    private boolean estVisible = true;
    private boolean estEnMouvement = false;

    //Attributs autres :
    protected double xCentre = 0, yCentre = 0;
    private double nbrVie = 4;
    private double momentDommage = 0;
    private double momentDernierClignotement = 0;
    private Assets typeProjectileActuel = Assets.ETOILE; //Par défault, le projectile est une étoile

    /**
     * Constructeur de la Classe Charlotte
     */
    public Charlotte() {
        image = new Image(Assets.CHARLOTTE.getEmplacement());
        y = Main.HAUTEUR / 2;
        w = W_CHARLOTTE;
        h = H_CHARLOTTE;
    }

    /**
     * Override de la méthode mettreAJourPhysique de objet de jeu qui prend en compte les touches utilisés par
     * l'utilisateur pour faire bouger charlotte
     *
     * @param deltaTemps la différence de temps
     */
    @Override
    public void mettreAJourPhysique(double deltaTemps) {
        mettreAJourAccelerationSelonSaisie(deltaTemps);
        super.mettreAJourPhysique(deltaTemps);
        assurerQueResteDansEcran();
    }

    /**
     * Mettre à jour la vitesse de Charlotte en considérant la vitesse maximale
     * @param deltaTemps la différence de temps
     */
    @Override
    protected void mettreAJourVitesse(double deltaTemps) {
        super.mettreAJourVitesse(deltaTemps);
        vx = assurerVitesseDansBornes(vx, VITESSE_MIN, VITESSE_MAX);
        vy = assurerVitesseDansBornes(vy, VITESSE_MIN, VITESSE_MAX);
    }

    /**
     * Ajuster l'accélération de Charlotte selon la saisie de l'utilisateur
     * @param deltaTemps différence de temps
     */
    private void mettreAJourAccelerationSelonSaisie(double deltaTemps){
        boolean gauche = Input.isKeyPressed(KeyCode.LEFT);
        boolean droite = Input.isKeyPressed(KeyCode.RIGHT);
        boolean haut = Input.isKeyPressed(KeyCode.UP);
        boolean bas = Input.isKeyPressed(KeyCode.DOWN);

        //pour ajuster l'image de Charlotte
        estEnMouvement = gauche || droite || haut || bas;

        xCentre = x + (w / 2);
        yCentre = y + (h / 2);

        //region -- MOUVEMENT --
        ax = trouverAcceleration(gauche, droite, ACCELERATION_X);
        ay = trouverAcceleration(haut, bas, ACCELERATION_Y);
        if (ax == 0) {
            vx = trouverVitesse(deltaTemps, ACCELERATION_X, vx);
        }
        if (ay == 0) {
            vy = trouverVitesse(deltaTemps, ACCELERATION_Y, vy);
        }

    }

    /**
     * Méthode servant à réduire le copier-coller lors du calculer des vitesses quand l'accélération est = à 0
     * @param deltaTemps la difféerence de temps
     * @param constanteAcceleration la constante (soit x ou y) de l'accélération de Charlotte
     * @param vitesse la vitesse initiale de charlotte (soit x ou y)
     * @return la nouvelle vitesse de Charlotte
     */
    private double trouverVitesse(double deltaTemps, double constanteAcceleration, double vitesse) {
        int signeVitesse = trouverSigneVitesse(vitesse);
        vitesse += deltaTemps * (-signeVitesse) * constanteAcceleration;
        int nouveauSigneVitesse = trouverSigneVitesse(vitesse);
        if (nouveauSigneVitesse != signeVitesse) {
            vitesse = 0;
        }
        return vitesse;
    }

    /**
     * Méthode qui sert à trouver l'accélération selon les touches appuyées par l'utilisateur
     * @param vaVersNegatif boolean qui dicte si l'utilisateur appuie une touche amenant charlotte vers les négatifs
     * @param vaVersPositif boolean qui dicte si l'utilisateur appuie une touche amenant charlotte vers les positifs
     * @param constanteAcceleration constante de l'accélération (soit x ou y)
     * @return la nouvelle accélération
     */
    private double trouverAcceleration(boolean vaVersNegatif, boolean vaVersPositif, double constanteAcceleration) {
        double acceleration;

        if (vaVersNegatif)
            acceleration = -constanteAcceleration;
        else if (vaVersPositif)
            acceleration = constanteAcceleration;
        else {
            acceleration = 0;
        }
        return acceleration;
    }


    /**
     * Méthode qui s'occupe de faire prendre du dommage à Charlotte lorsqu'elle est touché par un poisson ennemi
     */
    public void prendreDommage() {
        if (!estImmortel) {
            nbrVie--;
        }
        estEndommagee = true;
    }

    /**
     * Méthode qui gère le moment d'immortalité de charlotte après qu'elle aille prit du dommage
     *
     * @param tempsActuel utilisé pour savoir combien de temps charlotte reste immortelle
     */
    public void gererImmortalite(double tempsActuel) {
        if (!estImmortel && estEndommagee) {
            momentDommage = tempsActuel;
            estImmortel = true;
            momentDernierClignotement = tempsActuel;
        } else if ((tempsActuel - momentDommage) > TEMPS_IMMORTALITE) {
            estImmortel = false;
            estEndommagee = false;
        }
        gererVisibilite(tempsActuel);
    }

    //Va set le boolean estVisible à la bonne valeur

    /**
     * Méthode qui gère le clignotement de Charlotte en changeant la valeur du boolean estVisible selon le temps
     *
     * @param tempsActuel le temps actuel servant à trouver la différence de temps entre maintenant et le dernier
     *                    clignotement
     */
    private void gererVisibilite(double tempsActuel) {
        if (estEndommagee) {
            if (tempsActuel - momentDernierClignotement > 0.25) {
                estVisible = !estVisible;
                momentDernierClignotement = tempsActuel;
            }
        } else {
            estVisible = true;
        }
    }

    /**
     * Méthode trouvant le signe de la vitesse donner en paramètre
     *
     * @param vitesse vitesse de charlotte
     * @return 1 ou -1 selon le signe de sa vitesse
     */
    private int trouverSigneVitesse(double vitesse) {
        if (vitesse > 1) {
            return 1;
        }
        return -1;
    }

    /**
     * Méthode qui s'assure que Charlotte reste dans l'écran en changeant sa valeur de x ou de y si elle dépasse les
     * limites de l'écran
     */
    private void assurerQueResteDansEcran() {
        x = Math.max(Camera.getCamera().getXCamera(), x);
        x = Math.min(x, (Main.LARGEUR_MONDE - w));
        y = Math.max(0, y);
        y = Math.min(y, (Main.HAUTEUR - h));
    }


    //TOUT CE QUI EST "DESSIN" :
    /**
     * Override de la méthode dessiner de objetJeu qui appelle gérerImageCharlotte
     *
     * @param contexte pour dessiner
     */
    @Override
    public void dessiner(GraphicsContext contexte) {
        gererImageCharlotte();
        super.dessiner(contexte);
    }

    /**
     * Méthode qui, en vérifiant plusieurs booleans, set l'image de Charlotte selon son état (Statique, en mouvement ou
     * blessée)
     */
    private void gererImageCharlotte() {
        if (estVisible) {
            if (estEndommagee) {
                image = new Image(Assets.CHARLOTTE_OUTCH.getEmplacement());
            } else if (estEnMouvement) {
                image = new Image(Assets.CHARLOTTE_AVANT.getEmplacement());
            } else {
                image = new Image(Assets.CHARLOTTE.getEmplacement());
            }
        } else {
            image = null;
        }
    }


    //SETTERS :
    public void donnerMaxVie() {
        nbrVie = NBR_VIE_MAX;
    }

    public void setTypeProjectileActuel(Assets typeProjectileActuel) {
        this.typeProjectileActuel = typeProjectileActuel;
    }

    //GETTERS :
    public boolean estVivante() {
        return nbrVie > 0;
    }

    public double getNbrVie() {
        return nbrVie;
    }

    public double getNbrVieMax() {
        return NBR_VIE_MAX;
    }

    public Assets getTypeProjectileActuel() {
        return typeProjectileActuel;
    }
}
