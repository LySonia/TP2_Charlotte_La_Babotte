package ca.qc.bdeb.sim203.projetjavafx;


import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import static javafx.scene.paint.Color.BLUE;


public class SceneAccueil extends Scenes{




    @Override
    public void construireScene() {
        Image logo = new Image("logo.png");
        Button infos = new Button("Infos!");
        Button jouer = new Button("Jouer!");

        //section de boutons
        var groupeBoutons = new HBox();
        groupeBoutons.getChildren().add(jouer);
        groupeBoutons.getChildren().add(infos);
        groupeBoutons.setAlignment(Pos.CENTER);


        var imgvLogo = new ImageView(logo);
        imgvLogo.setPreserveRatio(true);
        imgvLogo.setFitWidth(logo.getWidth()/1.5);

        var vboxAccueil = new VBox();
        root.setStyle("-fx-background-color: #2A7FFF;");
        vboxAccueil.setMaxHeight(HAUTEUR);
        vboxAccueil.setMaxWidth(LARGEUR);

        vboxAccueil.getChildren().add(imgvLogo);
        vboxAccueil.getChildren().add(groupeBoutons);
        vboxAccueil.setAlignment(Pos.CENTER);


        root.getChildren().add(vboxAccueil);


    }
}
