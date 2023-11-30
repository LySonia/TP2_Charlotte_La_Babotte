package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.image.*;


public class EtoileDeMer extends Projectile {
    /**
     * Constructeur créant le projectile étoile de mer
     *
     * @param charlotte  charlotte pour trouver la position initiale du projectile (centre de Charlotte)
     * @param momentTire le moment exact où le projectile a été tirer
     */
    public EtoileDeMer(Charlotte charlotte, double momentTire) {
        super(charlotte, momentTire);
        image = new Image(Assets.ETOILE.getEmplacement());
        w = 36;
        h = 35;
        vx = 800;
        calculerPosInitial();
    }
}
