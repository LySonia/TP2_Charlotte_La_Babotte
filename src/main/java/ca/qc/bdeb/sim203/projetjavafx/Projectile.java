package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.GraphicsContext;

public abstract class Projectile extends ObjetJeu {
    protected Charlotte charlotte;
    protected double momentTire;
    public Projectile(Charlotte charlotte, double momentTire) {
        this.charlotte = charlotte;
        this.momentTire = momentTire;
    }

    //TODO: Not amazing coding practices
    protected void calculerPosInitial(){
        this.x = charlotte.xCentre - w/2;
        this.y = charlotte.yCentre - h/2;
    }

    public boolean estDansEcran() {
        boolean estDansEcran = false;

        if ((getXGauche() > Camera.getCamera().getXCamera()) &&
                (getXDroite() < Camera.getCamera().getXCamera() + Main.LARGEUR_ECRAN) &&
                (getYHaut() < Main.HAUTEUR) &&
                (getYBas() > Camera.getCamera().getYCamera())) {

            estDansEcran = true;
        }
        return estDansEcran;
    }
}
