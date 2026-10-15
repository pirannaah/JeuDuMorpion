package Morpion;

import java.util.List;
import java.util.Random;

public class Joueur {
    public final String motif;

    public Joueur(String motif) {
        this.motif = motif;
    }

    /*
    La version initiale de la méthode joue ne permet pas l'écriture de tests.
    En effet l'utilisation de la méthode Math.random() rend impossible la prévision
    des coordonnées choisi pour jouer.
    Une modification possible de la méthode est de passer par l'utilisation d'un
    objet de la classe Random et de le faire remonter comme paramètre de la méthode.
    On casse ainsi la dépendance entre le Joueur et le Random et on permet ainsi
    d'utiliser un random modifié dans les tests.
    public void joue(Grille grid) {
        List<Coordonnee> disponibles = grid.casesVides();
        grid.joue(this, disponibles.get((int) (Math.random() * disponibles.size())));
    }
    */
    public void joue(Grille grid, Random random) {
        List<Coordonnee> disponibles = grid.casesVides();
        grid.joue(this, disponibles.get(random.nextInt(disponibles.size())));
    }
}
