package tictactoe;

import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class HumanTest {

    @Test
    void testConstructeurEtGetters() {
        Human human = new Human("X", null);
        Human opponent = new Human("O", null);
        human.setOpponent(opponent);
        assertEquals("X", human.toString());
    }

    @Test
    void testJoueCoupValide() {
        Scanner scanner = new Scanner("2,2\n");
        Human human = new Human("X", scanner);
        Grille grille = new Grille();

        human.joue(grille);

        assertFalse(grille.estLibre(4)); // (2-1)*3 + (2-1) = 4
        assertTrue(grille.casesJoueur(human).contains(4));
    }

    @Test
    void testJoueAvecFormatInvalidePuisValide() {
        // "invalide" -> pas de virgule, puis "1,1" -> valide
        Scanner scanner = new Scanner("invalide\n1,1\n");
        Human human = new Human("X", scanner);
        Grille grille = new Grille();

        human.joue(grille);

        assertFalse(grille.estLibre(0));
    }

    @Test
    void testJoueHorsLimitesPuisValide() {
        // "4,4" -> hors limites, puis "3,3" -> valide
        Scanner scanner = new Scanner("4,4\n3,3\n");
        Human human = new Human("O", scanner);
        Grille grille = new Grille();

        human.joue(grille);

        assertFalse(grille.estLibre(8));
    }

    @Test
    void testJoueCaseOccupeePuisValide() throws CaseOccupeeException {
        Grille grille = new Grille();
        Human other = new Human("X", null);
        grille.joue(0, other);

        // Tente "1,1" (case 0 occupée), puis "1,2" (case 1 libre)
        Scanner scanner = new Scanner("1,1\n1,2\n");
        Human human = new Human("O", scanner);

        human.joue(grille);

        assertFalse(grille.estLibre(1));
        assertTrue(grille.casesJoueur(human).contains(1));
    }
}
