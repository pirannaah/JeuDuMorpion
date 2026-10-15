package Morpion;

import java.util.ArrayList;
import java.util.List;

public class Grille {

    private Joueur[] grid = new Joueur[9];  // plutôt que d'utiliser un tableau de contenant que des caractères 'X' et 'O'
                                            // on met l'objet joueur dans le tableau, il est ainsi plus facile de savoir qui
                                            // a joué dans la case

     boolean estGagnee() {
        return (GrilleTools.ligneGagnante(grid) || GrilleTools.colonneGagnante(grid) || GrilleTools.diagonaleGagnante(grid));
    }

    public boolean estPat() {
        for (Joueur joueur : grid) {
            if (joueur == null) return false;
        }
        return !estGagnee();
    }

    public List<Coordonnee> casesVides() {
        List<Coordonnee> placeLibres = new ArrayList<Coordonnee>();
        for (int i = 0; i < grid.length; i++) {
            if (grid[i] == null) {
                placeLibres.add(new Coordonnee(i / 3, i % 3));
            }
        }
        return placeLibres;
    }

    public void joue(Joueur joueur, Coordonnee coordonnee) {
        grid[coordonnee.ligne * 3 + coordonnee.colonne] = joueur;
    }

    public Joueur gagnant() {
        if (GrilleTools.ligneGagnante(0, grid) || GrilleTools.colonneGagnante(0, grid)) return grid[0];
        if (GrilleTools.ligneGagnante(1, grid)) return grid[3];
        if (GrilleTools.ligneGagnante(2, grid)) return grid[6];
        if (GrilleTools.colonneGagnante(1, grid)) return grid[1];
        if (GrilleTools.colonneGagnante(2, grid)) return grid[2];
        if (GrilleTools.diagonaleGagnante(grid)) return grid[4];
        return null;
    }

    public void display() {
        for (int li = 0; li < 3; li++) {
            for (int co = 0; co < 3; co++) {
                System.out.print(" " + (grid[li * 3 + co] == null ? "." : grid[li * 3 + co].motif));
            }
            System.out.println("");
        }
        System.out.println("");
    }

}
