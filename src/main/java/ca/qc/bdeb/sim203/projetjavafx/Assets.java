package ca.qc.bdeb.sim203.projetjavafx;

public enum Assets {
    BARIL("baril.png"),
    BARIL_OUVERT("baril-ouvert.png"),
    CHARLOTTE("charlotte.png"),
    CHARLOTTE_AVANT("charlotte-avant.png"),
    CHARLOTTE_OUTCH("charlotte-outch.png"),
    CHARLOTTE_OUTCH_TRANSPARENT("charlotte-outch-transparent.png"), //TODO: Change this to no image
    DECOR_1("decor1.png"),
    DECOR_2("decor2.png"),
    DECOR_3("decor3.png"),
    DECOR_4("decor4.png"),
    DECOR_5("decor5.png"),
    DECOR_6("decor6.png"),
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
}
