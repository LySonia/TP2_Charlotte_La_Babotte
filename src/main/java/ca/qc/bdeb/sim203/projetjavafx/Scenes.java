package ca.qc.bdeb.sim203.projetjavafx;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class Scenes  {

    protected Pane root = new Pane();
    private Scene scene = new Scene(root, LARGEUR, HAUTEUR);
    protected static final int LARGEUR = 900;
    protected static final int HAUTEUR = 520;

    public Scenes() {
        construireScene();
        root.setStyle("-fx-background-color: #2A7FFF;");
    }

    public abstract void construireScene();

    public Scene getScene() {
        return scene;
    }
}
