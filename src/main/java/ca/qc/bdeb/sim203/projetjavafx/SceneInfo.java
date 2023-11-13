package ca.qc.bdeb.sim203.projetjavafx;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

import java.util.*;

public class SceneInfo extends Scenes {

    private static SceneInfo infos = null;

    public static SceneInfo getSceneInfo(){
        if(infos == null){
            infos = new SceneInfo();
        }
        return infos;
    }



    @Override
    public void construireScene() {
        var vbox = new VBox();
        vbox.setMaxWidth(Main.LARGEUR);
        vbox.setMaxHeight(Main.HAUTEUR);
        var titre = new Text("Charlotte la Barbotte");
        titre.setFont(Font.font(50));
        //TODO: Faire de sorte que c'est toujours un poisson au hasard qui est choisie
        var poissonEnnemiImage = new Image(choisirPoissonHasard());
        var poissonEnnemiImageView = new ImageView(poissonEnnemiImage);


        var par = new Text("Par");
        var camilleMarquis = new Text("Camille Marquis");
        camilleMarquis.setFont(Font.font(30));
        var hBoxCamille = new HBox();
        hBoxCamille.getChildren().addAll(
                par,
                camilleMarquis
        );
        hBoxCamille.setAlignment(Pos.BOTTOM_CENTER);
        hBoxCamille.setSpacing(5);

        var et = new Text("et");
        var soniaLy = new Text("Sonia Ly");
        soniaLy.setFont(Font.font(30));
        var hBoxSonia = new HBox();
        hBoxSonia.getChildren().addAll(
                et,
                soniaLy
        );
        hBoxSonia.setAlignment(Pos.BOTTOM_CENTER);
        hBoxSonia.setSpacing(5);


        var texteExplicatif = new Text("Travail remis à Nicolas Hurtubise et George Côté. " +
                "Graphismes adaptés de https://games-icons.net/ et de https://openclipart.org/. " +
                "Développé dans le cadre du cours 420-203-RE. " +
                "Développement de programmes dans un environnement graphique, au Collège de Bois-de-Boulogne.");
        var texteExplicatifMieux = new TextFlow(texteExplicatif);

        var retour = new Button("Retour");

        vbox.getChildren().addAll(
            titre,
            poissonEnnemiImageView,
            hBoxCamille,
            hBoxSonia,
            texteExplicatifMieux,
            retour
        );
        vbox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(vbox);
    }

    public String choisirPoissonHasard(){
        //TODO: Façon plus efficace de faire?
        String poissonChoisi = Assets.POISSON_1.getEmplacement(); //Par défault

        String[] poissonsEnnemis = {
                Assets.POISSON_1.getEmplacement(),
                Assets.POISSON_2.getEmplacement(),
                Assets.POISSON_3.getEmplacement(),
                Assets.POISSON_4.getEmplacement(),
                Assets.POISSON_5.getEmplacement()
        };

        Random aleatoire = new Random();
        int nbrAleatoire = aleatoire.nextInt(poissonsEnnemis.length);

        for (int i = 0; i < poissonsEnnemis.length; i++) {
            if (nbrAleatoire == i)
                poissonChoisi = poissonsEnnemis[i];
        }
        return poissonChoisi;
    }

    @Override
    public void escape() {

    }
}
