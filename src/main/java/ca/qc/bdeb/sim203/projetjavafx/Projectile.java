package ca.qc.bdeb.sim203.projetjavafx;

public abstract class Projectile extends ObjetJeu {
    protected Charlotte charlotte;
    protected double momentTire;

    /**
     * Constructeur de la classe Projectile
     * @param charlotte la Charlotte de la Partie
     * @param momentTire le temps en secondes au moment de la tire
     */
    public Projectile(Charlotte charlotte, double momentTire) {
        this.charlotte = charlotte;
          this.momentTire = momentTire;
    }

    /**
     * Calculer la position initiale du projectile (centre de Charlotte)
     */
    protected void calculerPosInitial(){
        this.x = charlotte.xCentre - w/2;
        this.y = charlotte.yCentre - h/2;
    }

    /**
     * Vérifier si le projectile est dans l'écran
     * @return boolean qui est true si le projectile est dans l'écran
     */
    public boolean estDansEcran() {
        boolean estDansEcran = false;

        if ((getXGauche() > Camera.getCamera().getXCamera()) && //Vérifier gauche écran
                (getXGauche() < Camera.getCamera().getXCamera() + Main.LARGEUR_ECRAN) && //Vérifier droite écran
                (getYHaut() < Main.HAUTEUR) && //Vérifier bas écran
                (getYBas() > Camera.getCamera().getYCamera())) { //Vérifier haut écran

            estDansEcran = true;
        }
        return estDansEcran;
    }
}
