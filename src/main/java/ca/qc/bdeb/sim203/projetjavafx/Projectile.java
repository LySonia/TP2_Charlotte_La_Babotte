package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.GraphicsContext;

public abstract class Projectile extends ObjetJeu {
    private Charlotte charlotte;
    public Projectile(Charlotte charlotte) {
        this.charlotte = charlotte;
    }

    //TODO: Not amazinc coding practices
    protected void calculerPosInitial(){
        this.x = charlotte.xCentre - w/2;
        this.y = charlotte.yCentre - h/2;
    }
}
