package ca.qc.bdeb.sim203.projetjavafx;

import static ca.qc.bdeb.sim203.projetjavafx.GenerateurAleatoire.obtenirNombreAleatoire;

public enum Assets {
    BARIL("baril.png"),
    BARIL_OUVERT("baril-ouvert.png"),
    CHARLOTTE("charlotte.png"),
    CHARLOTTE_AVANT("charlotte-avant.png"),
    CHARLOTTE_OUTCH("charlotte-outch.png"),
    CORAL_1("decor1.png"),
    CORAL_2("decor2.png"),
    CORAL_3("decor3.png"),
    CORAL_4("decor4.png"),
    CORAL_5("decor5.png"),
    CORAL_6("decor6.png"),
    ETOILE("etoile.png"),
    HIPPOCAMPE("hippocampe.png"),
    LOGO("logo.png"),
    POISSON_1("poisson1.png"),
    POISSON_2("poisson2.png"),
    POISSON_3("poisson3.png"),
    POISSON_4("poisson4.png"),
    POISSON_5("poisson5.png"),
    SARDINES("sardines.png");

    private String emplacement;

    Assets(String emplacement) {
        this.emplacement = emplacement;
    }

    public String getEmplacement() {
        return emplacement;
    }

    //TODO Avant, stai dans la classe "Scenes", mais j'ai l'impression que ici est mieux
    //TODO J'aime pourtant pas comment il faut faire "Assets.[Poisson random}.ChoisirPoissonHasard()"
    public String choisirPoissonHasard(){
        //TODO Façon plus efficace de faire?
        String poissonChoisi = Assets.POISSON_1.getEmplacement(); //Par défault

        String[] poissonsEnnemis = {
                Assets.POISSON_1.getEmplacement(),
                Assets.POISSON_2.getEmplacement(),
                Assets.POISSON_3.getEmplacement(),
                Assets.POISSON_4.getEmplacement(),
                Assets.POISSON_5.getEmplacement()
        };

        int nbrAleatoire = obtenirNombreAleatoire(0, poissonsEnnemis.length - 1);

        for (int i = 0; i < poissonsEnnemis.length; i++) {
            if (nbrAleatoire == i)
                poissonChoisi = poissonsEnnemis[i];
        }
        return poissonChoisi;
    }

}
