package tictactoe;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class IATest {

    @Test
    void testConstructeurEtToString() {
        IA ia = new IA("X", new Random());
        assertEquals("X", ia.toString());
    }

    @Test
    void testIAJoueSurGrilleVide() {
        IA ia = new IA("X", new Random(42));
        Joueur opponent = new Human("O", null);
        ia.setOpponent(opponent);
        Grille grille = new Grille();

        ia.joue(grille);

        assertEquals(8, grille.casesVides().size());
        assertEquals(1, grille.casesJoueur(ia).size());
    }

    @Test
    void testIACompleteLigneGagnante() throws CaseOccupeeException {
        IA ia = new IA("X", new Random(42));
        Joueur opponent = new Human("O", null);
        ia.setOpponent(opponent);
        opponent.setOpponent(ia);

        Grille grille = new Grille();
        // IA a déjà deux pions sur la ligne 0 (cases 0 et 1)
        grille.joue(0, ia);
        grille.joue(1, ia);
        grille.joue(3, opponent);
        grille.joue(4, opponent);

        ia.joue(grille);

        // L'IA doit avoir joué en case 2 pour gagner immédiatement
        assertTrue(grille.casesJoueur(ia).contains(2));
        assertTrue(grille.isGagnante());
        assertEquals(ia, grille.gagnant());
    }

    @Test
    void testIABloqueVictoireAdverse() throws CaseOccupeeException {
        IA ia = new IA("X", new Random(42));
        Joueur opponent = new Human("O", null);
        ia.setOpponent(opponent);
        opponent.setOpponent(ia);

        Grille grille = new Grille();
        // L'adversaire menace de gagner sur la colonne 0 (cases 0 et 3)
        grille.joue(0, opponent);
        grille.joue(3, opponent);
        grille.joue(1, ia);

        ia.joue(grille);

        // L'IA doit bloquer la case 6
        assertTrue(grille.casesJoueur(ia).contains(6));
    }
}
