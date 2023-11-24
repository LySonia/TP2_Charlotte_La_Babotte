package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.image.*;
import javafx.scene.input.*;

public class Charlotte extends ObjetJeu {
    private final double W_CHARLOTTE = 102;
    private final double H_CHARLOTTE = 90;
    private final double ACCELERATION_X = 1000;
    private final double ACCELERATION_Y = 1000;
    private final static double NBR_VIE_MAX = 4.0;
    private final static int TEMPS_IMMORTALITE = 2;
    private int nbrVie = 4;
    private double momentDommage = 0;
    private double momentDernierClignotement = 0;
    private boolean estImmortel = false;

    private boolean estEnMouvement = false;
    private boolean estMorte = false;
    private boolean estEndommagee = false;
    private boolean estVisible = true;

    //partie sur les projectiles
    private Projectile projectileActuel;

    private double tempsDernierProjectile =0;

    private static final double DELAIS_DE_TIR = 0.5;

    public Charlotte() {
        x = 0;
        y = Main.HAUTEUR/2;
        w = W_CHARLOTTE;
        h = H_CHARLOTTE;
        vitesseMax = 300;
        image = new Image(Assets.CHARLOTTE.getEmplacement()); //TODO: Trop de mélange MVC?
        projectileActuel = new Hippocampes();

    }

    public void update(double deltaTemps) {
        super.update(deltaTemps);
        boolean gauche = Input.isKeyPressed(KeyCode.LEFT);
        boolean haut = Input.isKeyPressed(KeyCode.UP);
        boolean droite = Input.isKeyPressed(KeyCode.RIGHT);
        boolean bas = Input.isKeyPressed(KeyCode.DOWN);

        //TODO: Faire une méthode pour mouvement horizontale et verticale!
        //region -- MOUVEMENT HORIZONTAL --
        if (gauche)
            ax = -ACCELERATION_X;
        else if (droite)
            ax = ACCELERATION_X;
        else {
            // Code inspiré des NDC "Animations 5"
            ax = 0;

            int signeVitesse = trouverSigneVitesse(vx);

            vx += deltaTemps * (-signeVitesse) * ACCELERATION_X;

            int nouveauSigneVitesse = trouverSigneVitesse(vx);

            if (nouveauSigneVitesse != signeVitesse) {
                vx = 0;
            }
        }
        //endregion

        if (haut)
            ay = -ACCELERATION_Y;
        else if (bas)
            ay = ACCELERATION_Y;
        else {
            // Code inspiré des NDC "Animations 5"
            ay = 0;
            int signeVitesse = trouverSigneVitesse(vy);
            vy += deltaTemps * (-signeVitesse) * ACCELERATION_Y;
            int nouveauSigneVitesse = trouverSigneVitesse(vy);
            if (nouveauSigneVitesse != signeVitesse) {
                vy = 0;
            }
        }
    }
    public void prendreDommage() {
        if (!estImmortel) {
            nbrVie--;
        }
        estEndommagee = true;
    }

    public void utiliserProjectile(double temps){
        if(temps - tempsDernierProjectile> DELAIS_DE_TIR){
            tempsDernierProjectile = temps;
            projectileActuel.setTempsDeTir(temps);

            double yCentre = y+(h/2) - projectileActuel.getH()/2;
            double xCentre = x+(w/2)- projectileActuel.getW()/2;
            projectileActuel.setyDeCentreCharlotte(yCentre);
            projectileActuel.setEstTirer(true);
            projectileActuel.setX(xCentre); //pour que le projectile sorte du centre de charlotte
            projectileActuel.setY(yCentre);
        }


    }

    public void gererImmortalite(double tempsActuel) {
        //TODO: Est-ce qu'il y a un cas que j'ai oublié de prendre en compte?
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
    private void gererVisibilite (double tempsActuel) {
        if (estEndommagee) {
            if (tempsActuel - momentDernierClignotement > 0.25) {
                estVisible = !estVisible;
                momentDernierClignotement = tempsActuel;
            }
        } else {
            estVisible = true;
        }
    }

    @Override
    public void mettreAJourPhysique(double deltaTemps) {
        super.mettreAJourPhysique(deltaTemps);
        assurerQueResteDansEcran();
    }

    private int trouverSigneVitesse(double vitesse) {
        if (vitesse > 1) {
            return 1;
        }
        return -1;
    }

    private void assurerQueResteDansEcran() {
        x = Math.max(Camera.getCamera().getPositionX(), x); //position en X de la cam est la limite
        x = Math.min(x, (Main.LARGEUR_MONDE - w));
        y = Math.max(0, y);
        y = Math.min(y, (Main.HAUTEUR - h));


    }

    public int getNbrVie(){
        return nbrVie;
    }

    public void setNbrVie(int nbrVie) {
        this.nbrVie = nbrVie;
    }

    public double getNbrVieMax() {
        return NBR_VIE_MAX;
    }

    public boolean estEndommagee() {
        return estEndommagee;
    }


    public boolean estEnMouvement() {
        if (vx != 0 && vy != 0) {
            return false;
        }
        return true;
    }

    public boolean estVisible() {
        return estVisible;
    }

    public void setImage(String emplacement) {
        image = new Image(emplacement);
    }

    public Projectile getProjectileActuel() {
        return projectileActuel;
    }
}
