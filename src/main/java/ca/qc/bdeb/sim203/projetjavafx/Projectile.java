package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.GraphicsContext;

public abstract class Projectile extends ObjetJeu {
    private boolean estTirer = false;
    protected double tempsDeTir;
    protected double yDeCentreCharlotte;

    @Override
    public void dessiner(GraphicsContext contexte) {
        if (estTirer) {
            super.dessiner(contexte);
        }
    }

    public void setEstTirer(boolean estTirer) {
        this.estTirer = estTirer;
    }

    public void setTempsDeTir(double tempsDeTir) {
        this.tempsDeTir = tempsDeTir;
    }

    public void setYDeCentreCharlotte(double yDeCentreCharlotte) {
        this.yDeCentreCharlotte = yDeCentreCharlotte;
    }
}
