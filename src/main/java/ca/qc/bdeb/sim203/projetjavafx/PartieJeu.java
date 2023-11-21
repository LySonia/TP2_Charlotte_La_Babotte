package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.paint.*;

import java.util.*;

import static ca.qc.bdeb.sim203.projetjavafx.Aleatoire.obtenirNombreAleatoire;

//PartieJeu est comme la classe controlleur

//J'aurais les positions pour les coraux ici
public class PartieJeu {
    //TODO: Pas encore certaine de comment utiliser cette classe
    private ArrayList<ObjetJeu> objetsJeu = new ArrayList<>();
    private Charlotte charlotte = new Charlotte();
    private ArrayList<PoissonEnnemi> poissonsEnnemis = new ArrayList<>();
    private ArrayList<Corail> coraux = new ArrayList<>();
    private BarreVie barreVie = new BarreVie(charlotte);
    private int niveau = 1;
    private final int LARGEUR_NIVEAU = 8*Main.LARGEUR;
    private double nSecondes = 1;
    private double tempsDebutNiveau = 0;
    private Color couleurFondNiveau;
    private double tempsEcouleDepuisDebutNiveau = 0;
    private double teinte;
    private final double saturation = 0.84;
    private final double luminosité = 1.0;

    public PartieJeu() {
        objetsJeu.add(charlotte);
        objetsJeu.add(barreVie);
    }

    public void commencerJeu() {
        //Gerer les niveaux
    }

    public void demarrerNiveau(double tempsActuel) {
        tempsDebutNiveau = tempsActuel;

        //Calculer, pour une première fois, le temps écoulé depuis le début du niveau
        calculerTempsEcouleDepuisDebutNiveau(tempsActuel);

        //Trouver une teinte aléatoire pour la couleur de fond du niveau -> Trop de vue?!? /TODO
        preparerFondNiveau();

        //Ajouter une quantité initiale de poissons enemies
        ajouterGroupePoissons();

        //Calculer à quelle fréquence les poissons enemies devraient apparaître dans ce niveau
        calculerNSecondes();

        //Trouver le positionnement des coraux pour le niveau
        positionnerCoraux();
    }

    private void calculerTempsEcouleDepuisDebutNiveau(double tempsActuel) {
        tempsEcouleDepuisDebutNiveau = tempsActuel - tempsDebutNiveau;
    }

    private void preparerFondNiveau() {
        teinte = obtenirNombreAleatoire(190, 270);
        couleurFondNiveau = Color.hsb(teinte, saturation, luminosité);
    }

    public void ajouterGroupePoissons() {
        //Génération d'un nombre aléatoire de poissons:
        int nbrPoissonDansGroupe = Aleatoire.obtenirNombreAleatoire(1, 5);
        ArrayList<PoissonEnnemi> nouveauxPoissons = new ArrayList<>();

        //Ajouter le nombre aléatoire de poissons dans le ArrayList<>
        for (int i = 0; i < nbrPoissonDansGroupe; i++) {
            nouveauxPoissons.add(new PoissonEnnemi(niveau));
        }

       //Ajouter les nouveaux poissons dans le ArrayList de poissons ennemis
        poissonsEnnemis.addAll(nouveauxPoissons);

        //Ajouter les nouveaux poissons dans le ArrayList d'objet de jeu
        //TODO: Il y a sûrement un moyen plus efficace pour placer les nouveaux poissons dans les 2 ArrayLists
        objetsJeu.addAll(nouveauxPoissons);
    }

    //Créer des coraux pour le niveau
    private void positionnerCoraux() {
        //x = 0.0 pour le premier corail
        coraux.add(new Corail(0.0));
        double dernierePos = coraux.get(coraux.size() - 1).getXDroite();

        //Tant que la position (du x de droite) du dernier corail ne touche pas à la fin du niveau, ajouter un corail
        while (dernierePos < LARGEUR_NIVEAU) {
            dernierePos = coraux.get(coraux.size() - 1).getXDroite();
            double nouvellePos = dernierePos + genererDistanceEntreCoraux();
            coraux.add(new Corail(nouvellePos));
        }

        //Ajouter les coraux dans le ArrayList des objets de jeu
        objetsJeu.addAll(coraux);
    }

    private double genererDistanceEntreCoraux() {
        //TODO: Write cleaner code here
        return new Random().nextBoolean() ? 50.0 : 100.0;
    }

    public void sortirPoisson(PoissonEnnemi poisson) {
        poissonsEnnemis.remove(poisson);
        objetsJeu.remove(poisson);
    }

    private void calculerNSecondes() {
        nSecondes = 0.75 + 1/Math.pow(niveau, 0.5);
    }


    //GETTERS
    public ArrayList<ObjetJeu> getObjetsJeu() {
        return objetsJeu;
    }

    public Charlotte getCharlotte() {
        return charlotte;
    }

    public ArrayList<PoissonEnnemi> getPoissonsEnnemis() {
        return poissonsEnnemis;
    }
    public Color getCouleurFondNiveau() {
        return couleurFondNiveau;
    }
    public double getNSecondes() {
        return nSecondes;
    }

    public double getTempsDebutNiveau() {
        return tempsDebutNiveau;
    }

    public int getNiveau() {
        return niveau;
    }

    public int getNbrPoissonsEnnemis() {
        return poissonsEnnemis.size();
    }
}
