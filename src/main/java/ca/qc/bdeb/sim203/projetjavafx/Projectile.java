package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.GraphicsContext;

public abstract class Projectile extends ObjetJeu {
private boolean estTirer = false;
private double xCharlotte;
private double yCharlotte;

    @Override
    public void dessiner(GraphicsContext contexte) {
        if(estTirer){
            super.dessiner(contexte);
        }

    }

    public void setEstTirer(boolean estTirer) {
        this.estTirer = estTirer;
    }


}
