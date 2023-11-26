package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.image.Image;

public class EtoileDeMer extends Projectile{
    public EtoileDeMer() {
        w = 36;
        h = 35;
        image = new Image(Assets.ETOILE.getEmplacement());
        vx = 800;
    }
}
