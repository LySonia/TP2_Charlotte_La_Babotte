package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.*;
import javafx.scene.image.*;

public class Charlotte {
    private double w = 102;
    private double h = 90;

    private double x, y;

    private Image image = new Image("charlotte.png");

    public Charlotte() {
    }

    public void update(double deltaTemps) {

    }

    public void draw(GraphicsContext context) {
        context.drawImage(image, w, h);
    }


}
