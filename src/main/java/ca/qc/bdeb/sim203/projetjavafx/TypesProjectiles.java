package ca.qc.bdeb.sim203.projetjavafx;

public enum TypesProjectiles {
    ETOILE(Assets.ETOILE.getEmplacement()),
    HIPPOCAMPES(Assets.HIPPOCAMPE.getEmplacement()),
    SARDINE(Assets.SARDINES.getEmplacement());

    private String emplacement;

    TypesProjectiles(String emplacement) {
        this.emplacement = emplacement;
    }

    public String getEmplacement() {
        return emplacement;
    }
}
