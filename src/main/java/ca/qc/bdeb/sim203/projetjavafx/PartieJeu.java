package ca.qc.bdeb.sim203.projetjavafx;

import java.util.*;

import static ca.qc.bdeb.sim203.projetjavafx.Aleatoire.obtenirNombreAleatoire;

//PartieJeu est comme la classe controlleur
public class PartieJeu {
    //TODO: Pas encore certaine de comment utiliser cette classe
    private ArrayList<ObjetJeu> objetsJeu = new ArrayList<>();
    private Charlotte charlotte = new Charlotte();
    private ArrayList<PoissonEnnemi> poissonsEnnemis = new ArrayList<>();
    private BarreVie barreVie = new BarreVie(charlotte);
    private int nbrPoissonsEnnemisPresents = 0;
    private int niveau = 1;
    private final int NBR_NIVEAUX_TOTAL = 8;
    private final int LARGEUR_TOTAL_NIVEAU = 8*Main.LARGEUR;
    private double nSecondes = 1;

    //TOUT EN LIEN AVEC FOND -> VUE
    private double teinte;
    private double saturation = 0.84;
    private double luminosité = 1.0;

    public PartieJeu() {
        ArrayList<PoissonEnnemi> poissons = creerGroupePoissons();
        poissonsEnnemis.addAll(poissons);
        objetsJeu.add(charlotte);
        objetsJeu.addAll(poissonsEnnemis);
        objetsJeu.add(barreVie);
    }

    public void commencerJeu() {
        //Gerer les niveaux
    }

    private void demarrerNiveau() {
        creerGroupePoissons();
        calculerNSecondes();
    }


    private void preparerFondNiveau() {
        teinte = obtenirNombreAleatoire(190, 270);
    }

    public void ajouterGroupePoissons() { //TODO: modif
        poissonsEnnemis.addAll(creerGroupePoissons());
        System.out.println("Works2");
    }

    private ArrayList<PoissonEnnemi> creerGroupePoissons() {
        ArrayList<PoissonEnnemi> poissons= new ArrayList<>();
        int nbrPoissonDansGroupe = Aleatoire.obtenirNombreAleatoire(1, 5);
        for (int i = 0; i < nbrPoissonDansGroupe; i++) {
            poissons.add(new PoissonEnnemi(niveau));
        }
        poissonsEnnemis.addAll(poissons);
        return poissons;
    }

    private void sortirPoisson(PoissonEnnemi poisson) {
        poissonsEnnemis.remove(poisson);
    }

    private void calculerNSecondes() {
        nSecondes = 0.75 + 1/Math.pow(niveau, 0.5);
    }

    public ArrayList<ObjetJeu> getObjetsJeu() {
        return objetsJeu;
    }

    public Charlotte getCharlotte() {
        return charlotte;
    }

    public ArrayList<PoissonEnnemi> getPoissonsEnnemis() {
        return poissonsEnnemis;
    }

    public double getTeinte() {
        return teinte;
    }

    public double getSaturation() {
        return saturation;
    }

    public double getLuminosité() {
        return luminosité;
    }

    public double getNSecondes() {
        return nSecondes;
    }
}
