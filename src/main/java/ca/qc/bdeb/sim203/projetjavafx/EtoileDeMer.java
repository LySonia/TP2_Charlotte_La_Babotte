package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.image.Image;

public class EtoileDeMer extends Projectile{
    public EtoileDeMer(Charlotte charlotte, double momentTire) {
        super(charlotte, momentTire);
        image = new Image(Assets.ETOILE.getEmplacement());
        w = 36;
        h = 35;
        vx = 800;
        vitesseMax = 800;
        calculerPosInitial();
    }
}
