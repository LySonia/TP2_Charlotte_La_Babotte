package ca.qc.bdeb.sim203.projetjavafx;

public enum TypesProjectiles {
    ETOILES(new EtoileDeMer()),
    HIPPOCAMPES(new Hippocampes()),
    SARDINES(new Sardines());

    public Projectile projectile;

    TypesProjectiles(Projectile projectile){
        this.projectile = projectile;
    }

    public Projectile getProjectile() {
        return projectile;
    }
}
