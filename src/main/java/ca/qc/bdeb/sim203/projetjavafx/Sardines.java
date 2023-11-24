package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.image.Image;

public class Sardines extends Projectile{
    private int chargeQ = 200;
    private final static int K = 1000;

    public Sardines() {
        w=35;
        h=29;
        vx = 800;
        image = new Image(Assets.SARDINES.getEmplacement());
    }
}
