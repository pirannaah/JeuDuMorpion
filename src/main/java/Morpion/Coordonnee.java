package Morpion;

public class Coordonnee {

    public final int ligne;
    public final int colonne;

    public Coordonnee(int ligne, int colonne) {
        this.colonne = colonne;
        this.ligne = ligne;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordonnee that = (Coordonnee) o;

        if (ligne != that.ligne) return false;
        return colonne == that.colonne;
    }
}
