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
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.*;

import static ca.qc.bdeb.sim203.projetjavafx.Hasard.nextInt;

public class Scenes {
    public static final double NANOSECONDE = 1e-9; //Bon placement de la variable?
    private static final String NOM_JEU = "Charlotte la Barbotte";
    private Stage stage;
    private AnimationTimer timer;

    /**
     * Constructeur de la classe Scenes
     * @param stage le stage utilisé pour afficher le jeu
     */
    public Scenes(Stage stage) {
        this.stage = stage;
        stage.setScene(getSceneAccueil()); //Par défault, c'est la scène d'accueil
        stage.setTitle(NOM_JEU);
        stage.setResizable(false);
        stage.getIcons().add(new Image(Assets.CHARLOTTE.getEmplacement()));
    }

    /**
     * Méthode qui construit la scène de jeu
     * @return la scène de jeu
     */
    public Scene getSceneJeu() {
        PartieJeu partieJeu = new PartieJeu(System.nanoTime() * NANOSECONDE);

        var root = new Pane();

        var sceneJeu = new Scene(root, Main.LARGEUR_ECRAN, Main.HAUTEUR);
        Canvas canvas = new Canvas(Main.LARGEUR_ECRAN, Main.HAUTEUR);
        GraphicsContext contexte = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double tempsActuel = now * NANOSECONDE;

                //Ajuster la couleur de fond
                var couleurFond = partieJeu.getCouleurFondNiveau();
                root.setBackground(new Background(new BackgroundFill(couleurFond, null, null)));

                //Dessiner chaque objet de jeu
                partieJeu.mettreAJourJeu(tempsActuel);
                partieJeu.dessiner(contexte);

                if (partieJeu.estFinPartie()) {
                    if (tempsActuel - partieJeu.getMomentFinNiveau() > partieJeu.DUREE_AFFICHAGE_FIN_JEU) {
                        timer.stop();
                        stage.setScene(getSceneAccueil());
                    }
                }
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
                    gererKeyPressedDebug(partieJeu, e, System.nanoTime() * NANOSECONDE);
                }
            }
        });

        sceneJeu.setOnKeyReleased((e) -> {
            gererKeyReleasedGenerale(e);
        });

        return sceneJeu;
    }

    /**
     * Gérer ce que le programme fait selon la saisie de l'utilisateur quand le jeu est en mode debug
     * @param partieJeu un objet partieJeu
     * @param e le KeyEvent quand l'utilisateur touche un bouton
     * @param tempsActuel le temps actuel
     */
    private void gererKeyPressedDebug(PartieJeu partieJeu, KeyEvent e, double tempsActuel) {
        if (e.getCode() == KeyCode.Q) {
            partieJeu.changerTypeProjectile(Assets.ETOILE);
        }

        if (e.getCode() == KeyCode.W) {
            partieJeu.changerTypeProjectile(Assets.HIPPOCAMPE);
        }

        if (e.getCode() == KeyCode.E) {
            partieJeu.changerTypeProjectile(Assets.SARDINES);
        }

        if (e.getCode() == KeyCode.R) {
            partieJeu.donnerMaxVie();
        }

        if (e.getCode() == KeyCode.T) {
            partieJeu.demarrerNiveau(tempsActuel);
        }
    }

    /**
     * Méthode qui construit la scène d'accueil
     * @return la scène d'accueil
     */
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
        groupeBoutons.setSpacing(5);

        vboxAccueil.getChildren().addAll(
                logoImageView,
                groupeBoutons
        );
        vboxAccueil.setAlignment(Pos.CENTER);
        vboxAccueil.setSpacing(10);

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

    /**
     * Méthode qui construit la scène d'info
     * @return la scène de d'info
     */
    public Scene getSceneInfo() {
        Pane root = creerRoot();
        var sceneInfo = new Scene(root, Main.LARGEUR_ECRAN, Main.HAUTEUR);

        var vBox = new VBox();
        vBox.setPrefWidth(Main.LARGEUR_ECRAN);
        vBox.setPrefHeight(Main.HAUTEUR);

        var titre = new Text("Charlotte la Barbotte");
        titre.setFont(Font.font(50));

        var poissonEnnemiImage = new Image(Hasard.choisirPoissonHasard().getEmplacement());
        var poissonEnnemiImageView = new ImageView(poissonEnnemiImage);
        poissonEnnemiImageView.setPreserveRatio(true);
        poissonEnnemiImageView.setFitHeight(130);

        var fontNoms = new Font(40);

        var par = new Text("Par");
        par.setFont(Font.font(20));
        var camilleMarquis = new Text("Camille Marquis");
        camilleMarquis.setFont(fontNoms);
        var hBoxCamille = new HBox();
        hBoxCamille.getChildren().addAll(
                par,
                camilleMarquis
        );
        hBoxCamille.setAlignment(Pos.CENTER);
        hBoxCamille.setSpacing(5);

        var et = new Text("et");
        et.setFont(Font.font(20));
        var soniaLy = new Text("Sonia Ly");
        soniaLy.setFont(fontNoms);
        var hBoxSonia = new HBox();
        hBoxSonia.getChildren().addAll(
                et,
                soniaLy
        );
        hBoxSonia.setAlignment(Pos.CENTER);
        hBoxSonia.setSpacing(5);


        var texteExplicatif = new Text("Travail remis à Nicolas Hurtubise et George Côté. " +
                "Graphismes adaptés de https://games-icons.net/ et de https://openclipart.org/. " +
                "Développé dans le cadre du cours 420-203-RE - " +
                "Développement de programmes dans un environnement graphique, au Collège de Bois-de-Boulogne.");
        texteExplicatif.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 12));
        var texteExplicatifPlusieursLignes = new TextFlow(texteExplicatif);
        texteExplicatifPlusieursLignes.setMaxWidth(700);
        texteExplicatifPlusieursLignes.setTextAlignment(TextAlignment.JUSTIFY);

        var retour = new Button("Retour");

        vBox.getChildren().addAll(
                titre,
                poissonEnnemiImageView,
                hBoxCamille,
                hBoxSonia,
                texteExplicatifPlusieursLignes,
                retour
        );
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(10));

        root.getChildren().add(vBox);

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

    /**
     * Gérer ce que le programme fait selon la saisie de l'utilisateur quand on est dans la scène d'accueil ou d'info
     * @param e le KeyEvent quand l'utilisateur touche un bouton
     */
    private void gererKeyPressedGenerale(KeyEvent e){
        Input.setKeyPressed(e.getCode(), true);

        if (e.getCode() == KeyCode.ESCAPE) {
            Platform.exit();
        }
    }

    /**
     * Gérer ce que le programme fait quand l'utilisateur ne pèse plus sur un bouton.
     * @param e le Keyevent quand l'utilisateur ne pèse plus sur le bouton
     */
    private void gererKeyReleasedGenerale(KeyEvent e){
        Input.setKeyPressed(e.getCode(), false);
    }

    /**
     * Créer un root pou
     * @return le root au fond blue
     */
    private Pane creerRoot() {
        var root = new Pane();
        var bleuEnonce = Color.web("#2A7FFF");
        root.setBackground(new Background(new BackgroundFill(bleuEnonce, null, null)));
        return root;
    }
    //endregion
}
