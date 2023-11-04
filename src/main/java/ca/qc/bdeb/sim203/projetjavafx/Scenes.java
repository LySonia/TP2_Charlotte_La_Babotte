package ca.qc.bdeb.sim203.projetjavafx;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class Scenes  {

    protected Pane root = new Pane();
    private Scene scene = new Scene(root, Main.LARGEUR, Main.HAUTEUR);


    public Scenes() {
        construireScene();
        root.setStyle("-fx-background-color: #2A7FFF;");

        //region ÉVÉNEMENTS
        scene.setOnKeyPressed((e) -> {
            Input.setKeyPressed(e.getCode(), true);
        });

        scene.setOnKeyReleased((e) -> {
            Input.setKeyPressed(e.getCode(), false);
        });
        //endregion
    }

    public abstract void construireScene();

    public abstract void escape();

    public Scene getScene() {
        return scene;
    }
}
