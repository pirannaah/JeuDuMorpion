package Morpion;

import java.util.Random;

public class Launcher {

    public static void main(String[] args) {
        Grille grid = new Grille();
        Joueur joueur1 = new Joueur("X");
        Joueur joueur2 = new Joueur("O");
        Joueur player =  joueur1;
        Joueur other = joueur2;
        while (!grid.estGagnee() && !grid.estPat()) {
            player.joue(grid, new Random());
            Joueur temp = player;
            player = other;
            other = temp;
            grid.display();
        }
        if (grid.estGagnee()) {
            System.out.println("Le gagnant est "+ grid.gagnant());
        }
        else
            System.out.println("Partie nulle");
    }

}
