package ca.qc.bdeb.sim203.projetjavafx;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class SceneInfo extends Scenes {
    @Override
    public void construireScene() {
        var vbox = new VBox();
        var titre = new Text("Charlotte la Barbotte");
        //TODO: Faire de sorte que c'est toujours un poisson au hasard qui est choisie
        var poissonEnnemiImage = new Image("poisson1.png");
        var poissonEnnemiImageView = new ImageView(poissonEnnemiImage);
        var par = new Text("Par");
        var camilleMarquis = new Text("Camille Marquis");
        var et = new Text("et");
        var soniaLy = new Text("Sonia Ly");

        var texteExplicatif = new Text("Travail remis à Nicolas Hurtubise et George Côté. " +
                "Graphismes adaptés de https://games-icons.net/ et de https://openclipart.org/. " +
                "Développé dans le cadre du cours 420-203-RE. " +
                "Développement de programmes dans un environnement graphique, au Collège de Bois-de-Boulogne.");


        var retour = new Button("Retour");

        vbox.getChildren().addAll(
            titre,
            poissonEnnemiImageView,
            par,
            camilleMarquis,
            et,
            soniaLy,
            texteExplicatif,
            retour
        );
        vbox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(vbox);
    }
}
