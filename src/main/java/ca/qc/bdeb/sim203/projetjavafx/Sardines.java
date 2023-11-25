package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.image.Image;

public class Sardines extends Projectile{
    private final static int CHARGE_Q = 200;
    private final static int K = 1000;

    public Sardines() {
        w=35;
        h=29;
        vx = 800;
        vy=0;
        ay=0;
        image = new Image(Assets.SARDINES.getEmplacement());
    }

    @Override
    public void mettreAJourPhysique(double deltaTemps) {
        vy += deltaTemps * ay;
        x += deltaTemps * vx;
        y += deltaTemps * vy;
    }
}
