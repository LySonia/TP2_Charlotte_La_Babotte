package ca.qc.bdeb.sim203.projetjavafx;

import java.util.*;

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


    private final int NBR_ENNEMIS = 5;

    Assets(String emplacement) {
        this.emplacement = emplacement;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public String choisirPoissonHasard(){
        String poissonChoisi = POISSON_1.emplacement; //Par défault

        String[] poissonsEnnemis = {
                POISSON_1.emplacement,
                POISSON_2.emplacement,
                POISSON_3.emplacement,
                POISSON_4.emplacement,
                POISSON_5.emplacement
        };

        Random aleatoire = new Random();
        int nbrAleatoire = aleatoire.nextInt(NBR_ENNEMIS);

        //TODO: Façon plus efficace de faire?
        for (int i = 0; i < poissonsEnnemis.length; i++) {
            if (nbrAleatoire == i)
                poissonChoisi = poissonsEnnemis[i];
        }
        return poissonChoisi;
    }

}
