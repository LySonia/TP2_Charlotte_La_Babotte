package ca.qc.bdeb.sim203.projetjavafx;

import javafx.animation.*;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

import java.util.*;

import static ca.qc.bdeb.sim203.projetjavafx.Hasard.obtenirNombreAleatoire;

public class Scenes {
    public static final double NANOSECONDE = 1e-9; //Bon placement de la variable?
    private static final String NOM_JEU = "Charlotte la Barbotte";
    private Stage stage;

    public Scenes(Stage stage) {
        this.stage = stage;
        stage.setScene(getSceneAccueil()); //Par défault, c'est la scène d'accueil
        stage.setTitle(NOM_JEU);
    }

    public Scene getSceneJeu() {
        PartieJeu partieJeu = new PartieJeu(System.nanoTime() * NANOSECONDE);
        Charlotte charlotte = partieJeu.getCharlotte();

        var root = new Pane();

        var sceneJeu = new Scene(root, Main.LARGEUR_ECRAN, Main.HAUTEUR);
        Canvas canvas = new Canvas(Main.LARGEUR_ECRAN, Main.HAUTEUR);
        GraphicsContext contexte = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double tempsActuel = now * NANOSECONDE;

                //Ajuster la couleur de fond
                var couleurFond = partieJeu.getCouleurFondNiveau();
                root.setBackground(new Background(new BackgroundFill(couleurFond, null, null)));

                //Dessiner chaque objet de jeu
                partieJeu.mettreAJourJeu(tempsActuel);
                partieJeu.dessiner(contexte);

            }

        };
        timer.start();

        //Événements :
        sceneJeu.setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                timer.stop();

                stage.setScene(getSceneAccueil());
            } else {
                Input.setKeyPressed(e.getCode(), true);
                if (e.getCode() == KeyCode.D) {
                    partieJeu.setEstDebug(!partieJeu.estDebug());
                }

                if (e.getCode() == KeyCode.SPACE) {
                    partieJeu.gererTireProjectile();
                }

                if (partieJeu.estDebug()) {
                    gererKeyPressedDebug(charlotte, e);
                }
            }
        });

        sceneJeu.setOnKeyReleased((e) -> {
            gererKeyReleasedGenerale(e);
        });

        return sceneJeu;
    }

    private void gererKeyPressedDebug(Charlotte charlotte, KeyEvent e) {
        if (e.getCode() == KeyCode.Q) {
            //Code pour donner une étoile de mer comme projectile
        }

        if (e.getCode() == KeyCode.W) {
            //Code pour donner des hippocampes comme projectile
        }

        if (e.getCode() == KeyCode.E) {
            //Code pour donner une boîte de sardines comme projectile
        }

        if (e.getCode() == KeyCode.R) {
            charlotte.donnerMaxVie();
        }

        if (e.getCode() == KeyCode.T) {

        }
    }

    public Scene getSceneAccueil() {
        Pane root = creerRoot();
        var sceneAccueil = new Scene(root, Main.LARGEUR_ECRAN, Main.HAUTEUR);
        var logo = new Image(Assets.LOGO.getEmplacement());
        var logoImageView = new ImageView(logo);
        logoImageView.setPreserveRatio(true);
        logoImageView.setFitWidth(logo.getWidth() / 1.5);

        Button infos = new Button("Infos!");
        Button jouer = new Button("Jouer!");

        var vboxAccueil = new VBox();
        vboxAccueil.setPrefHeight(Main.HAUTEUR);
        vboxAccueil.setPrefWidth(Main.LARGEUR_ECRAN);

        var groupeBoutons = new HBox();
        groupeBoutons.getChildren().addAll(jouer, infos);
        groupeBoutons.setAlignment(Pos.CENTER);

        vboxAccueil.getChildren().addAll(
                logoImageView,
                groupeBoutons
        );
        vboxAccueil.setAlignment(Pos.CENTER);

        root.getChildren().add(vboxAccueil);

        //region ÉVÉNEMENTS
        sceneAccueil.setOnKeyPressed((e) -> {
            gererKeyPressedGenerale(e);
        });

        sceneAccueil.setOnKeyReleased((e) -> {
            gererKeyReleasedGenerale(e);
        });

        jouer.setOnAction((e) -> {
            stage.setScene(getSceneJeu());
        });

        infos.setOnAction((e) -> {
            stage.setScene(getSceneInfo());
        });
        //endregion

        return sceneAccueil;
    }

    public Scene getSceneInfo() {
        Pane root = creerRoot();
        var sceneInfo = new Scene(root, Main.LARGEUR_ECRAN, Main.HAUTEUR);

        var vbox = new VBox();
        vbox.setPrefWidth(Main.LARGEUR_ECRAN);
        vbox.setPrefHeight(Main.HAUTEUR);

        var titre = new Text("Charlotte la Barbotte");
        titre.setFont(Font.font(50));
        //TODO: Faire de sorte que c'est toujours un poisson au hasard qui est choisie
        var poissonEnnemiImage = new Image(Hasard.choisirPoissonHasard());
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
        vbox.setAlignment(Pos.TOP_CENTER);

        root.getChildren().add(vbox);

        //region ÉVÉNEMENTS
        sceneInfo.setOnKeyPressed((e) -> {
            gererKeyPressedGenerale(e);
        });

        sceneInfo.setOnKeyReleased((e) -> {
            gererKeyReleasedGenerale(e);
        });

        retour.setOnAction((e) -> {
            stage.setScene(getSceneAccueil());
        });
        //endregion

        return sceneInfo;
    }

    //region MÉTHODES GÉNÉRALES
    private void gererKeyPressedGenerale(KeyEvent e){
        Input.setKeyPressed(e.getCode(), true);

        if (e.getCode() == KeyCode.ESCAPE) {
            Platform.exit();
        }
    }

    private void gererKeyReleasedGenerale(KeyEvent e){
        Input.setKeyPressed(e.getCode(), false);

        if (e.getCode() == KeyCode.ESCAPE) {
            Platform.exit();
        }
    }

    private Pane creerRoot() {
        Pane root = new Pane();
        root.setStyle("-fx-background-color: #2A7FFF;"); // Pour faire de sorte que le fond est bleu
        return root;
    }
    //endregion
}
